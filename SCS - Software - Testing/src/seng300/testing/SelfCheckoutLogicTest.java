package seng300.testing;

import static org.junit.Assert.*;
import org.junit.*;
import org.lsmr.selfcheckout.*;
import org.lsmr.selfcheckout.devices.*;
import org.lsmr.selfcheckout.external.CardIssuer;
import org.lsmr.selfcheckout.external.ProductDatabases;
import org.lsmr.selfcheckout.products.BarcodedProduct;
import org.lsmr.selfcheckout.products.PLUCodedProduct;
import org.lsmr.selfcheckout.products.Product;


import seng300.software.ProductDatabaseLogic;
import seng300.software.SelfCheckoutSystemLogic;
import seng300.software.exceptions.ProductNotFoundException;


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
	
	
	Numeral[] n1 = {Numeral.one,Numeral.one,Numeral.one};
	Numeral[] n2 = {Numeral.two,Numeral.one,Numeral.one};
	Numeral[] n3 = {Numeral.three,Numeral.one,Numeral.one};
	Numeral[] n4 = {Numeral.four,Numeral.one,Numeral.one};
	Numeral[] n5 = {Numeral.five,Numeral.one,Numeral.one};
	Numeral[] n6 = {Numeral.five,Numeral.three,Numeral.one};
	Numeral[] n7 = {Numeral.five,Numeral.one,Numeral.three};
	
	Barcode b1 = new Barcode(n1);
	Barcode b2 = new Barcode(n2);
	Barcode b3 = new Barcode(n3);
	Barcode b4 = new Barcode(n4);
	Barcode b52 = new Barcode(n5);
	Barcode b6 = new Barcode(n6);
	Barcode b7 = new Barcode(n7);
	
	double val1 = 1;
	double val2 = 3;
	double val3 = 5;
	double val4 = 10;
	double val5 = 12;
	double val6 = 14;
	double val7 = 15;
	
	BarcodedProduct p1 = new BarcodedProduct(b1, "p1", pval1, val1); // @ TESTING-TEAM need to add 'double expectedWeight' to the constructor. - Kevin
	BarcodedProduct p2 = new BarcodedProduct(b2, "p2", pval2, val2);
	BarcodedProduct p3 = new BarcodedProduct(b3, "p3", pval3, val3);
	BarcodedProduct p4 = new BarcodedProduct(b4, "p4", pval4, val4);
	BarcodedProduct p5 = new BarcodedProduct(b52, "p5", pval5, val5);
	BarcodedProduct p6 = new BarcodedProduct(b6, "p6", pval6, val6);
	BarcodedProduct p7 = new BarcodedProduct(b7, "p7", pval7, val7);
	
	
	
	PriceLookupCode plu1 = new PriceLookupCode("11111");
	PriceLookupCode plu2 = new PriceLookupCode("22222");
	PriceLookupCode plu3 = new PriceLookupCode("33333");
	PriceLookupCode plu4 = new PriceLookupCode("44444");

	BigDecimal pva1 = new BigDecimal("1.25");
	BigDecimal pva2 = new BigDecimal("21.25");
	BigDecimal pva3 = new BigDecimal("31.25");
	BigDecimal pva4 = new BigDecimal("41.25");
	



	PLUCodedProduct pluProduct1 = new PLUCodedProduct(plu1, "Theme", pva1);
	PLUCodedProduct pluProduct2 = new PLUCodedProduct(plu2, "those", pva2);
	PLUCodedProduct pluProduct3 = new PLUCodedProduct(plu3, "tHe", pva3);
	PLUCodedProduct pluProduct4 = new PLUCodedProduct(plu4, "thEre", pva4);
	
	Card testCard = new Card("Debit", "11111", "Customer", "111", "1111", false, false);
	
	
	
	//values
	boolean expected = true;
	boolean actual = true;

	Map<Barcode, BarcodedProduct> bprods;
	Map<Barcode, BarcodedItem> bitems;

	ProductDatabaseLogic db;
	SelfCheckoutSystemLogic checkoutControl;
	CardIssuer bank = new CardIssuer("The Bank");
	@Before
	public void setUp() throws ProductNotFoundException, OverloadException {

	
		//this is taken from the selfcheckout class. just setting everything up
		//scs = new SelfCheckoutStation(defcur, bdenom_array, cdenom_array, scaleMaximumWeight, scaleSensitivity);
		db = new ProductDatabaseLogic();
		
		Calendar c = Calendar.getInstance(); //gets the next day for expiry
		c.setTime(new Date()); 
		c.add(Calendar.DATE, 1);
		
		bank.addCardData("11111", "Customer", c, "123", new BigDecimal("1000000000"));
		
		
		ProductDatabases.BARCODED_PRODUCT_DATABASE.put(b1, p1);
		ProductDatabases.BARCODED_PRODUCT_DATABASE.put(b2, p2);
		ProductDatabases.BARCODED_PRODUCT_DATABASE.put(b3, p3);
		ProductDatabases.BARCODED_PRODUCT_DATABASE.put(b4, p4);
		ProductDatabases.BARCODED_PRODUCT_DATABASE.put(b52, p5);
		ProductDatabases.BARCODED_PRODUCT_DATABASE.put(b6, p6);
		ProductDatabases.BARCODED_PRODUCT_DATABASE.put(b7, p7);
		
		ProductDatabases.PLU_PRODUCT_DATABASE.put(plu1, pluProduct1);
		ProductDatabases.PLU_PRODUCT_DATABASE.put(plu2, pluProduct2);
		ProductDatabases.PLU_PRODUCT_DATABASE.put(plu3, pluProduct3);
		ProductDatabases.PLU_PRODUCT_DATABASE.put(plu4, pluProduct4);
		
		
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
		
		checkoutControl = new SelfCheckoutSystemLogic(scs);
		this.checkoutControl.attachGUI();
		this.checkoutControl.setGui(new GuiStub(checkoutControl));
		SelfCheckoutSystemLogic.attachBlockNotifiableGui(new AttendantGuiStub());
		this.checkoutControl.testMode();
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
		scs.banknoteInput.enable();
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
		scs.banknoteInput.enable();
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

	
	// Test customer looks up PLU coded product by description
	@Test 
	public void lookUpPLUCodedProducttest() {
		ProductDatabases.PLU_PRODUCT_DATABASE.put(plu1, pluProduct1);
				
		List<PLUCodedProduct> list1 = checkoutControl.productLookUp("THe");
		List<PLUCodedProduct> list2 = new ArrayList<PLUCodedProduct>();
		list2.add(pluProduct1);
		list2.add(pluProduct3);
		list2.add(pluProduct4);		
				
		boolean productListContains1 = false;
		boolean productListContains2 = false;
				
		if(list1.contains(pluProduct1) && list1.contains(pluProduct3) && list1.contains(pluProduct4)) {
			productListContains1 = true;
		}
				
		if(list2.contains(pluProduct1) && list2.contains(pluProduct3) && list2.contains(pluProduct4)) {
			productListContains2 = true;
		}
				
		Assert.assertEquals(productListContains1, productListContains2);
			
	}
			
	
}

