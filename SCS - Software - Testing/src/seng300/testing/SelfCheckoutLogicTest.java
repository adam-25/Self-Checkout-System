package seng300.testing;

import static org.junit.Assert.*;
import org.junit.*;
import org.lsmr.selfcheckout.*;
import org.lsmr.selfcheckout.devices.*;
import org.lsmr.selfcheckout.devices.observers.*;
import org.lsmr.selfcheckout.products.BarcodedProduct;

import seng300.software.ProductDatabase;
import seng300.software.SelfCheckoutSystemLogic;
import seng300.software.exceptions.BadCardException;
import seng300.software.exceptions.ProductNotFoundException;

import seng300.software.Checkout;
import seng300.software.PayWithCoin;
import seng300.software.BankStub;

import java.math.*;
import java.util.*;

import java.io.IOException;

public class SelfCheckoutLogicTest {
	//declare testing variables and objects	
	SelfCheckoutStation scs;
	int b5 = 5;
	int b10 = 10;
	int b20 = 20;
	int b50 = 50;
	int b100 = 100;
	int[] bdenom_array = {b5, b10, b20, b50, b100};
	
	MathContext mc = new MathContext(4);
	BigDecimal cval1 = new BigDecimal("0.25");
	BigDecimal cval2 = new BigDecimal("0.10");
	BigDecimal cval3 = new BigDecimal("0.05");
	BigDecimal cval4 = new BigDecimal("1.00");
	BigDecimal cval5 = new BigDecimal("2.00");
	BigDecimal[] cdenom_array = {cval1, cval2, cval3, cval4, cval5};
	
	Currency defcur = Currency.getInstance("CAD");

	int scaleMaximumWeight = 15;
	int scaleSensitivity = 3;
	
	BigDecimal pval1 = new BigDecimal("1.25");
	BigDecimal pval2 = new BigDecimal("3.00");
	BigDecimal pval3 = new BigDecimal("10.00");
	BigDecimal pval4 = new BigDecimal("2.00");
	BigDecimal pval5 = new BigDecimal("60.00");
	BigDecimal pval6 = new BigDecimal("5.10");
	BigDecimal pval7 = new BigDecimal("4.99");
	
	BarcodedItem it1;
	BarcodedItem it2;
	BarcodedItem it3;
	BarcodedItem it4;
	BarcodedItem it5;
	BarcodedItem it6;
	BarcodedItem it7;
	
	//values
	boolean expected = true;
	boolean actual = true;

	Map<Barcode, BarcodedProduct> bprods;
	Map<Barcode, BarcodedItem> bitems;

	ProductDatabase db;
	SelfCheckoutSystemLogic checkoutControl;
	BankStub bank;
	@Before
	public void setUp() throws ProductNotFoundException, OverloadException {

	
		//this is taken from the selfcheckout class. just setting everything up
		//scs = new SelfCheckoutStation(defcur, bdenom_array, cdenom_array, scaleMaximumWeight, scaleSensitivity);
		db = new ProductDatabase(7, scaleMaximumWeight);
		bank = new BankStub();
		
		int counter = 1;
		double changedWeight = 0;
		String changedName = "";
		BigDecimal changedPrice = BigDecimal.ZERO;
		Random rand = new Random();
		for (Barcode b : this.db.getProducts().keySet()) {
			switch(counter) {
				case 1:
					changedWeight = rand.nextDouble() + 1;
					it1 = new BarcodedItem(b, changedWeight);
					changedPrice = pval1;
					changedName = "Item 1";
					break;
				case 2:
					changedWeight = scaleSensitivity;
					it2 = new BarcodedItem(b, changedWeight);
					changedPrice = pval2;
					changedName = "Item 2";
					break;
				case 3:
					changedWeight = rand.nextDouble() + 5;
					it3 = new BarcodedItem(b, changedWeight);
					changedPrice = pval3;
					changedName = "Item 3";
					break;
				case 4:
					changedWeight = rand.nextDouble() + 10;
					it4 = new BarcodedItem(b, changedWeight);
					changedPrice = pval4;
					changedName = "Item 4";
					break;
				case 5:
					changedWeight = scaleMaximumWeight;
					it5 = new BarcodedItem(b, changedWeight);
					changedPrice = pval5;
					changedName = "Item 5";
					break;
				case 6:
					changedWeight = rand.nextDouble() + scaleMaximumWeight;
					it6 = new BarcodedItem(b, changedWeight);
					changedPrice = pval6;
					changedName = "Item 6";
					break;
				case 7:
					changedWeight = rand.nextDouble() + 6;
					it7 = new BarcodedItem(b, changedWeight);
					changedPrice = pval7;
					changedName = "Item 7";
					break;
			}
			BarcodedProduct changingTheProduct = new BarcodedProduct (b, changedName, 
			changedPrice, changedWeight);
			
			this.db.getProducts().replace(b, this.db.getProduct(b), changingTheProduct);
			
			counter++;
		}
		
		scs = new SelfCheckoutStation(defcur, bdenom_array, cdenom_array, scaleMaximumWeight, scaleSensitivity);
		scs.printer.addInk(ReceiptPrinter.MAXIMUM_INK);
		scs.printer.addPaper(ReceiptPrinter.MAXIMUM_PAPER);
		
		checkoutControl = new SelfCheckoutSystemLogic(scs, db);
					
	}
	
	@After
	public void tearDown() {
		// 
	}
	
	//Scans an item and pays for it with cash
	@Test 
	public void ScanAndCheckoutTest() throws DisabledException, OverloadException {
		int previousNumOfProducts = 0;
		do {
			scs.mainScanner.scan(it3); 
		} while(checkoutControl.getCart().getProducts().size() == previousNumOfProducts);
		
		scs.baggingArea.add(it3);
		expected = false;
		actual = checkoutControl.isBlocked();
		assertEquals("item was less than sensitivity.",
				expected, actual);
		checkoutControl.wantsToCheckout();
		checkoutControl.checkout.update(new BigDecimal("10.00"));
		checkoutControl.checkout.chooseBanknote();
		//input ten dollar bill
		scs.banknoteInput.accept(new Banknote(defcur, b10));
		checkoutControl.checkout.completeCurrentPaymentMethod();
		//expect that transaction completes successfully idk what that looks like yet
		checkoutControl.finishCheckout();
		String rec = scs.printer.removeReceipt();
		System.out.println(rec);
		//check that the receipt is correct
		assertEquals("expected transaction to finish successfully.",
				"\nItem 3 $10.00\n"
				+ "\n"
				+ "Total: $10.00\n"
				+ "\n"
				+ "Paid: $10.00\n"
				+ "\n"
				+ "Paid $10.00 with: Banknotes\n"
				+ "\n"
				+ "Change: $0.00\n",rec);
	}

	//Scans an item and pays partially with cash, return to adding items, 
	//scans another item, pays fully with debit card, checks out, no change
	
	@Test 
	public void addItemAfterPartialPayment() throws DisabledException, OverloadException {
		Card debit = new Card("Debit", "11111", "Customer", "111", "1111", false, false);
		// keep scanning until item is scanned
		int previousNumOfProducts = 0;
		do {
			scs.mainScanner.scan(it3); 
		} while(checkoutControl.getCart().getProducts().size() == previousNumOfProducts);
		
		scs.baggingArea.add(it3);
		expected = false;
		actual = checkoutControl.isBlocked();
		assertEquals("item was less than sensitivity.",
				expected, actual);
		checkoutControl.wantsToCheckout();
		checkoutControl.checkout.chooseBanknote();
		//input five dollar bill
		scs.banknoteInput.accept(new Banknote(defcur, b5));
		checkoutControl.checkout.completeCurrentPaymentMethod();
		//go back to scanning mode
		checkoutControl.addItemAfterCheckoutStart();
		//scan until successful
		previousNumOfProducts = checkoutControl.getCart().getProducts().size();
		do {
			scs.mainScanner.scan(it6); 
		} while(checkoutControl.getCart().getProducts().size() == previousNumOfProducts);
		
		scs.baggingArea.add(it6);
		
		checkoutControl.wantsToCheckout();
		checkoutControl.checkout.chooseDebit(bank, new BigDecimal("10.10"));
		boolean swiped = false;
		while (!swiped) {
			try {
				scs.cardReader.swipe(debit);
				swiped = true;
			} catch (IOException e) {

			}
		}
		checkoutControl.checkout.completeCurrentPaymentMethod();
		
		//expect that transaction completes successfully, $15.10 payed in total
		checkoutControl.finishCheckout();
		String rec = scs.printer.removeReceipt();
		System.out.println(rec);
		//check that the receipt is correct
		assertEquals("expected transaction to finish successfully.",
				"\nItem 3 $10.00\n"
				+ "\n"
				+ "Item 6 $5.10\n"
				+ "\n"
				+ "Total: $15.10\n"
				+ "\n"
				+ "Paid: $15.10\n"
				+ "\n"
				+ "Paid $5.00 with: Banknotes\n"
				+ "\n"
				+ "Paid $10.10 with: debit\n"
				+ "\n"
				+ "Change: $0.00\n",rec);
	}
	
	@Test //remove known item
    public void removeItemFromBaggingArea() throws InterruptedException {
        int expectedItemSize = checkoutControl.getBaggedProducts().size();
        int previousNumOfProducts = checkoutControl.getCart().getProducts().size();
        do {
            scs.mainScanner.scan(it3); 
        } while(checkoutControl.getCart().getProducts().size() == previousNumOfProducts);

        //bagging area should know/care
        scs.baggingArea.add(it3);
        Thread.sleep(500); // Used so check bag thread can pick up results
        //expected weight
        
        BarcodedProduct someProduct = new BarcodedProduct(it3.getBarcode(), "", new BigDecimal(it3.getWeight()), previousNumOfProducts);
        
        checkoutControl.selectItemToRemove(someProduct);
        
        scs.baggingArea.remove(it3);
        
        checkoutControl.returnToNormalBaggingOperation();
        
        Thread.sleep(500); // Used so check bag thread can pick up results
        
        expected = false;
        actual = checkoutControl.isBlocked();
        assertEquals("Self checkout is in bagging state",
                expected, actual);    
        
        int actualItemSize = checkoutControl.getBaggedProducts().size();
        
        assertEquals("Item indeed removed", expectedItemSize, actualItemSize);
    }
	
	@Test
    public void removeWrongProductFromBaggingArea() throws InterruptedException {
        int expectedItemSize = checkoutControl.getBaggedProducts().size();
        int previousNumOfProducts = checkoutControl.getCart().getProducts().size();
        do {
            scs.mainScanner.scan(it7); 
        } while(checkoutControl.getCart().getProducts().size() == previousNumOfProducts);

        //bagging area should know/care
        scs.baggingArea.add(it7);
        Thread.sleep(500); // Used so check bag thread can pick up results
        //expected weight
        
        BarcodedProduct someProduct = new BarcodedProduct(it3.getBarcode(), "", new BigDecimal(it3.getWeight()), previousNumOfProducts);
        
        checkoutControl.selectItemToRemove(someProduct);
        
        scs.baggingArea.remove(it7);
        
        Thread.sleep(500); // Used so check bag thread can pick up results
        
        expected = true;
        actual = checkoutControl.isBlocked();
        assertEquals("System block",expected, actual);    
    }
}
