package seng300.testing;

import static org.junit.Assert.*;
import org.junit.*;
import org.lsmr.selfcheckout.*;
import org.lsmr.selfcheckout.devices.*;
import org.lsmr.selfcheckout.external.CardIssuer;
import org.lsmr.selfcheckout.products.BarcodedProduct;
import org.lsmr.selfcheckout.products.*;

import seng300.software.Checkout;
import seng300.software.PayWithCoin;

import java.io.IOException;

//import wishtocheckout.*;

import java.math.*;
import java.util.*;

// Based on testing suite from Group 11 Iteration 1
// updated by John

public class CheckoutTests {
	
	//declare testing variables and objects	
	
	SelfCheckoutStation scs;
	
	int bval1 = 1;
	int bval2 = 5;
	int bval3 = 10;
	int bval4 = 20;
	int bval5 = 50;
	int bval6 = 100;
	int[] bdenom_array = {bval1, bval2, bval3, bval4, bval5, bval6};
	
	MathContext mc = new MathContext(4);
	BigDecimal cval1 = new BigDecimal("0.25");
	BigDecimal cval2 = new BigDecimal("0.10");
	BigDecimal cval3 = new BigDecimal("0.05");
	BigDecimal cval4 = new BigDecimal("1.00");
	BigDecimal cval5 = new BigDecimal("2.00");
	BigDecimal[] cdenom_array = {cval1, cval2, cval3, cval4, cval5};
	
	int scaleMaximumWeight = 15;
	int scaleSensitivity = 3;
	
	//default currency set as canadian
	Currency defcur = Currency.getInstance("CAD");
	
	//these are the valid coin names we're going to test with
	Coin quarter1;
	Coin quarter2;
	Coin dime;
	Coin nickle;
	Coin loonie;
	Coin yen;
	
	//these are the valid banknotes
	Banknote dollar_bill;
	Banknote fiver1;
	Banknote fiver2;
	Banknote ten_dollars;
	Banknote twenty_bucks;
	
	//invalid currency set to usd
	Currency invcur = Currency.getInstance("USD");
	
	//invalid coins
	Coin invalid1C;
	Coin invalid2C;
	BigDecimal invalC = new BigDecimal(0.33, mc);
	//invalid bankenotes
	Banknote invalid1B;
	Banknote invalid2B;
	int invalB = 15;
	
	//cards
	Card credit1;
	Card debit1;
	Card credit2;
	Card member1;

	Numeral[] n1 = {Numeral.one,Numeral.one,Numeral.one};
	Numeral[] n2 = {Numeral.two,Numeral.one,Numeral.one};
	Numeral[] n3 = {Numeral.three,Numeral.one,Numeral.one};
	Numeral[] n4 = {Numeral.four,Numeral.one,Numeral.one};
	Numeral[] n5 = {Numeral.five,Numeral.one,Numeral.one};
	
	Barcode b1 = new Barcode(n1);
	Barcode b2 = new Barcode(n2);
	Barcode b3 = new Barcode(n3);
	Barcode b4 = new Barcode(n4);
	Barcode b5 = new Barcode(n5);
	
	BigDecimal pval1 = new BigDecimal("1.25");
	BigDecimal pval2 = new BigDecimal("3.00");
	BigDecimal pval3 = new BigDecimal("10.00");
	BigDecimal pval4 = new BigDecimal("2.00");
	BigDecimal pval5 = new BigDecimal("60.00");
	
	// Product weights
	double val1 = 1;
	double val2 = 3;
	double val3 = 5;
	double val4 = 10;
	double val5 = 12;
	
	BarcodedProduct p1 = new BarcodedProduct(b1, "p1", pval1, val1); // @ TESTING-TEAM need to add 'double expectedWeight' to the constructor. - Kevin
	BarcodedProduct p2 = new BarcodedProduct(b2, "p2", pval2, val2);
	BarcodedProduct p3 = new BarcodedProduct(b3, "p3", pval3, val3);
	BarcodedProduct p4 = new BarcodedProduct(b4, "p4", pval4, val4);
	BarcodedProduct p5 = new BarcodedProduct(b5, "p5", pval5, val5);
	
	List<Product> products;
	
	Checkout test;

	PayWithCoin paycoin;
	
	CardIssuer bank = new CardIssuer("Big bank"); //default valid cards
	CardIssuer membersStub = new CardIssuer("Big Company members");
	
	@Before
	//runs before each test
	public void setUp() throws OverloadException {
		scs = new SelfCheckoutStation(defcur, bdenom_array, cdenom_array, scaleMaximumWeight, scaleSensitivity);
		scs.printer.addInk(ReceiptPrinter.MAXIMUM_INK - 1);
		scs.printer.addPaper(ReceiptPrinter.MAXIMUM_PAPER - 1);
		
		products = new ArrayList<Product>();
		

		//coins
		Coin.DEFAULT_CURRENCY = defcur;
		
		quarter1 = new Coin(cval1);
		quarter2 = new Coin(cval1);
		dime = new Coin(cval2);
		nickle = new Coin(cval3);
		loonie = new Coin(cval4);
		
		invalid1C = new Coin(invcur, cval1);
		invalid2C = new Coin(invalC);
		
		//banknotes
		dollar_bill = new Banknote(defcur, bval1);
		fiver1 = new Banknote(defcur, bval2);
		fiver2 = new Banknote(defcur, bval2);
		ten_dollars = new Banknote(defcur, bval3);
		twenty_bucks = new Banknote(defcur, bval4);
		
		invalid1B = new Banknote(invcur, bval1);
		invalid2B = new Banknote(defcur, invalB);
		
		//cards
		credit1 = new Card("Credit", "11111", "Customer", "111", "1111", true, true);
		debit1 = new Card("Debit", "11112", "Customer", "111", "1111", true, true);
		credit2 = new Card("Credit", "123456", "Customer", "111", "1111", false, false);
		member1 = new Card("Membership", "11113", "Customer", "111", "1111", false, false);
		
		Calendar c = Calendar.getInstance(); //gets the next day for expiry
		c.setTime(new Date()); 
		c.add(Calendar.DATE, 1);
		
		bank.addCardData("11111", "Customer", c, "112", new BigDecimal("1000000"));//credit1
		bank.addCardData("123456", "Customer", c, "113", new BigDecimal("1000000"));//credit1
		bank.addCardData("11112", "Customer", c, "114", new BigDecimal("1000000")); //debit1
		membersStub.addCardData("11113", "Customer", c, "231", new BigDecimal("1"));
	}

	@After
	public void tearDown() {

	}
	
//	First few tests are for basic functionality: Paying with banknotes, coins, 
//	debit cards, credit cards, membership cards, and returning change.
	
	
	//=================================================
	// Testing a transaction with only inserting coins and not returning change
	//=================================================
	@Test
	public void testFinishTransCoinOnlyNoChange() throws DisabledException, OverloadException {
		products.add(p1);
		test = new Checkout(scs, (ArrayList<Product>) products, new BigDecimal("1.25"));
		test.chooseCoin();
		//input quarter and loonie (value equal to total owed
		scs.coinSlot.accept(quarter1);
		scs.coinSlot.accept(loonie);
		test.completeCurrentPaymentMethod();
		//expect that transaction completes successfully idk what that looks like yet
		test.finishPayment();
		String rec = scs.printer.removeReceipt();
		System.out.println(rec);
		//check that the receipt is correct
		assertEquals("expected transaction to finish successfully.",
				"\np1 $1.25\n"
				+ "\n"
				+ "Total: $1.25\n"
				+ "\n"
				+ "Paid: $1.25\n"
				+ "\n"
				+ "Paid $1.25 with: Coins\n"
				+ "\n"
				+ "Change: $0.00\n",rec);
	}
	//=================================================
	// Testing a transaction with only inserting coins and returning coins
	//=================================================
	@Test
	public void testFinishTransCoinOnlyCoinChange() throws DisabledException, SimulationException, OverloadException, EmptyException {
		products.add(p1);
		test = new Checkout(scs, (ArrayList<Product>) products, new BigDecimal("1.25"));
		scs.coinDispensers.get(cval1).load(new Coin(cval1)); //load a quarter
		
		test.chooseCoin();
		//input quarter and loonie (value equal to total owed
		scs.coinSlot.accept(quarter1);
		scs.coinSlot.accept(quarter2);
		scs.coinSlot.accept(loonie);
		//expect that transaction completes successfully idk what that looks like yet
		test.completeCurrentPaymentMethod();
		test.finishPayment();
		String rec = "";
		while (true) {
			try {
				rec = scs.printer.removeReceipt();
				break;
			} catch (InvalidArgumentSimulationException e) {
				
			}
		}
		//check that the receipt is correct
		assertEquals("expected transaction to finish successfully.",
				"\np1 $1.25\n"
				+ "\n"
				+ "Total: $1.25\n"
				+ "\n"
				+ "Paid: $1.50\n"
				+ "\n"
				+ "Paid $1.50 with: Coins\n"
				+ "\n"
				+ "Change: $0.25\n",rec);
		BigDecimal totalChange = BigDecimal.ZERO;
		boolean hasCoins = true;
		while (hasCoins) {
			hasCoins = !test.returnCoinChange(); //returns true once the last batch coin change is returned
			List<Coin> coinChange = scs.coinTray.collectCoins();
			for (int i = 0; i < coinChange.size() && coinChange.get(i)!= null; i++) { //add all coin values together	
				totalChange = totalChange.add(coinChange.get(i).getValue());
			}
		}
		test.returnBanknoteChange();
		while (!scs.banknoteOutput.hasSpace()) {
			int value;
			Banknote[] bankNote = scs.banknoteOutput.removeDanglingBanknotes();
			for (int i = 0; i < bankNote.length; i++)
			{
				value = bankNote[i].getValue(); 
				totalChange = totalChange.add(new BigDecimal(value));
			}
		}
		BigDecimal expectedChange = new BigDecimal("0.25");
		assertEquals("wrong amount of change, expected 0.25", 0, totalChange.compareTo(expectedChange));
	}
	
	//=================================================
	// Testing a transaction with only inserting banknotes and not returning change
	//=================================================
	@Test
	public void testFinishTransBankoteOnlyNoChange() throws DisabledException, OverloadException {
		products.add(p4);
		products.add(p2);
		products.add(p3);
		test = new Checkout(scs, (ArrayList<Product>) products, new BigDecimal("15.00"));
		test.chooseBanknote();
		//input fiver and ten dollars
		scs.banknoteInput.accept(fiver1);
		scs.banknoteInput.accept(ten_dollars);
		test.completeCurrentPaymentMethod();
		//expect that transaction completes successfully idk what that looks like yet
		test.finishPayment();
		String rec = scs.printer.removeReceipt();
		//check that the receipt is correct?
		assertEquals("expected transaction to finish successfully.",
				"\np4 $2.00\n"
				+ "\n"
				+ "p2 $3.00\n"
				+ "\n"
				+ "p3 $10.00\n"
				+ "\n"
				+ "Total: $15.00\n"
				+ "\n"
				+ "Paid: $15.00\n"
				+ "\n"
				+ "Paid $15.00 with: Banknotes\n"
				+ "\n"
				+ "Change: $0.00\n",rec);
	}
	
	//=================================================
	// Testing a transaction with only inserting banknotes and returning change in banknotes
	//=================================================
	@Test
	public void testFinishTransBankoteOnlyBanknoteChange() throws DisabledException, OverloadException, EmptyException {
		products.add(p4);
		products.add(p2);
		products.add(p3);
		test = new Checkout(scs, (ArrayList<Product>) products, new BigDecimal("15.00"));
		scs.banknoteDispensers.get(bval2).load(new Banknote(defcur, bval2)); //load a five dollar bill
		
		test.chooseBanknote();
		//input twenty bucks
		scs.banknoteInput.accept(twenty_bucks);
		//expect that transaction completes successfully idk what that looks like yet
		test.completeCurrentPaymentMethod();
		test.finishPayment();
		String rec = scs.printer.removeReceipt();
		System.out.println(rec);
		//check that the receipt is correct?
		assertEquals("expected transaction to finish successfully.",
				"\np4 $2.00\n"
				+ "\n"
				+ "p2 $3.00\n"
				+ "\n"
				+ "p3 $10.00\n"
				+ "\n"
				+ "Total: $15.00\n"
				+ "\n"
				+ "Paid: $20.00\n"
				+ "\n"
				+ "Paid $20.00 with: Banknotes\n"
				+ "\n"
				+ "Change: $5.00\n",rec);
		BigDecimal totalChange = BigDecimal.ZERO;
		boolean hasCoins = true;
		while (hasCoins) {
			hasCoins = !test.returnCoinChange(); //returns true once the last batch coin change is returned
			List<Coin> coinChange = scs.coinTray.collectCoins();
			for (int i = 0; i < coinChange.size() && coinChange.get(i)!= null; i++) { //add all coin values together	
				totalChange = totalChange.add(coinChange.get(i).getValue());
			}
		}
		test.returnBanknoteChange();
		while (!scs.banknoteOutput.hasSpace()) {
			int value;
			Banknote[] bankNote = scs.banknoteOutput.removeDanglingBanknotes();
			for (int i = 0; i < bankNote.length; i++) {
				value = bankNote[i].getValue();
				totalChange = totalChange.add(new BigDecimal(value));
			}
		}
		BigDecimal expectedChange = new BigDecimal("5");
		assertEquals("wrong amount of change, expected 5", 0, totalChange.compareTo(expectedChange));
	}
	
	//=================================================
	// Testing a transaction with debit card, approved by bank, magnetic swipe
	//=================================================
	@Test
	public void testFinishTransDebitSwipeNoChange() {
		products.add(p4);
		products.add(p2);
		products.add(p3);
		test = new Checkout(scs, (ArrayList<Product>) products, new BigDecimal("15.00"));
		Card card = new Card("debit", "11111", "Customer", "111", "1111", false, false);
		
		//select the payment method: Debit, and amount: $15.00
		BigDecimal amount = new BigDecimal("15.00");
		test.chooseDebit(bank, amount);
		
		//swipe until data is read
		boolean swiped = false;
		while (!swiped) {
			try {
				scs.cardReader.swipe(card);
				swiped = true;
			} catch (IOException e) {

			}
		}
		
		//expect that transaction completes successfully
		test.completeCurrentPaymentMethod();
		test.finishPayment();
		String rec = scs.printer.removeReceipt();
		System.out.println(rec);
		//check that the receipt is correct?
		assertEquals("expected transaction to finish successfully.",
				"\np4 $2.00\n"
				+ "\n"
				+ "p2 $3.00\n"
				+ "\n"
				+ "p3 $10.00\n"
				+ "\n"
				+ "Total: $15.00\n"
				+ "\n"
				+ "Paid: $15.00\n"
				+ "\n"
				+ "Paid $15.00 with: debit\n"
				+ "\n"
				+ "Change: $0.00\n",rec);
	}
	
	//=================================================
	// Testing a transaction with credit card, approved by bank, tap
	//=================================================
	@Test
	public void testFinishTransCreditTapNoChange() {
		products.add(p4);
		products.add(p2);
		products.add(p3);
		test = new Checkout(scs, (ArrayList<Product>) products, new BigDecimal("15.00"));
		Card card = new Card("credit", "11111", "Customer", "111", "1111", true, false);
		
		//select the payment method: Credit, and amount: $15.00
		BigDecimal amount = new BigDecimal("15.00");
		test.chooseCredit(bank, amount);
		
		//tap until data is read
		boolean tapped = false;
		while (!tapped) {
			try {
				while(scs.cardReader.tap(card) == null) {
					
				};
				tapped = true;
			} catch (IOException e) {
	
			}
		}
		
		//expect that transaction completes successfully
		test.completeCurrentPaymentMethod();
		test.finishPayment();
		String rec = scs.printer.removeReceipt();
		System.out.println(rec);
		//check that the receipt is correct?
		assertEquals("expected transaction to finish successfully.",
				"\np4 $2.00\n"
				+ "\n"
				+ "p2 $3.00\n"
				+ "\n"
				+ "p3 $10.00\n"
				+ "\n"
				+ "Total: $15.00\n"
				+ "\n"
				+ "Paid: $15.00\n"
				+ "\n"
				+ "Paid $15.00 with: credit\n"
				+ "\n"
				+ "Change: $0.00\n",rec);
	}

	//=================================================
	// Testing a transaction with credit card, approved by bank, insert, correct pin
	//=================================================
	@Test
	public void testFinishTransCreditInsertNoChange() {
		products.add(p4);
		products.add(p2);
		products.add(p3);
		test = new Checkout(scs, (ArrayList<Product>) products, new BigDecimal("15.00"));
		Card card = new Card("credit", "11111", "Customer", "000", "1111", false, true);
		
		//select the payment method: Credit, and amount: $15.00
		BigDecimal amount = new BigDecimal("15.00");
		test.chooseCredit(bank, amount);
		
		//insert and enter pin until data is read
		boolean inserted = false;
		while (!inserted) {
			try {
				scs.cardReader.insert(card, "1111"); //this PIN is correct
				inserted = true;
			} catch (ChipFailureException e) {
				scs.cardReader.remove();
			} catch (IOException e) {

			}
		}
		
		//expect that transaction completes successfully
		test.completeCurrentPaymentMethod();
		test.finishPayment();
		String rec = scs.printer.removeReceipt();
		System.out.println(rec);
		//check that the receipt is correct?
		assertEquals("expected transaction to finish successfully.",
				"\np4 $2.00\n"
				+ "\n"
				+ "p2 $3.00\n"
				+ "\n"
				+ "p3 $10.00\n"
				+ "\n"
				+ "Total: $15.00\n"
				+ "\n"
				+ "Paid: $15.00\n"
				+ "\n"
				+ "Paid $15.00 with: credit\n"
				+ "\n"
				+ "Change: $0.00\n",rec);
	}

	//=================================================
	// Testing a transaction with inserting banknotes and swiping a membership card
	//=================================================
	@Test
	public void testFinishTransBankoteMembershipNoChange() throws DisabledException, OverloadException {
		products.add(p4);
		products.add(p2);
		products.add(p3);
		test = new Checkout(scs, (ArrayList<Product>) products, new BigDecimal("15.00"));
		
		test.chooseBanknote();
		//input fiver and ten dollars
		scs.banknoteInput.accept(fiver1);
		scs.banknoteInput.accept(ten_dollars);
		test.completeCurrentPaymentMethod();
		
		//select Membership Card
		test.chooseMembership(membersStub);
		
		//swipe until data is read
		boolean swiped = false;
		while (!swiped) {
			try {
				scs.cardReader.swipe(member1);
				swiped = true;
			} catch (IOException e) {

			}
		}
		test.completeMembershipRecognition();
		
		//expect that transaction completes successfully idk what that looks like yet
		test.finishPayment();
		String rec = scs.printer.removeReceipt();
		//check that the receipt is correct?
		assertEquals("expected transaction to finish successfully.",
				"\np4 $2.00\n"
				+ "\n"
				+ "p2 $3.00\n"
				+ "\n"
				+ "p3 $10.00\n"
				+ "\n"
				+ "Total: $15.00\n"
				+ "\n"
				+ "Paid: $15.00\n"
				+ "\n"
				+ "Paid $15.00 with: Banknotes\n"
				+ "\n"
				+ "Change: $0.00\n"
				+ "\n"
				+ "Member number: 11113\n",rec);
	}

	
//	These next tests are for more advanced functionality: Mixing payment methods and
//	returning change under different circumstances 

	//=================================================
	// Testing a transaction with inserting banknotes, coins, credit, debit, and swiping a membership card
	// also returns mixed change
	//=================================================	
	@Test
	public void testFinishTransMixedPaymentMixedChange() throws DisabledException, OverloadException, EmptyException {		
		products.add(p4);
		products.add(p2);
		products.add(p3);
		products.add(p5);
		test = new Checkout(scs, (ArrayList<Product>) products, new BigDecimal("75.00"));
		scs.banknoteDispensers.get(bval2).load(new Banknote(defcur, bval2), new Banknote(defcur, bval2)); //load two five dollar bills
		scs.banknoteDispensers.get(bval4).load(new Banknote(defcur, bval4)); //load a twenty dollar bill
		scs.coinDispensers.get(cval1).load(new Coin(cval1), new Coin(cval1), new Coin(cval1)); //load 3 quarters
		scs.coinDispensers.get(cval2).load(new Coin(cval2), new Coin(cval2), new Coin(cval2)); //load 3 dimes
		scs.coinDispensers.get(cval3).load(new Coin(cval3), new Coin(cval3), new Coin(cval3)); //load 3 nickels
		scs.coinDispensers.get(cval4).load(new Coin(cval4), new Coin(cval4), new Coin(cval4)); //load 3 loonies
		
		test.chooseBanknote();
		//input fiver
		scs.banknoteInput.accept(fiver1);
		test.completeCurrentPaymentMethod();
		
		test.chooseCoin();
		//input quarter and loonie 
		scs.coinSlot.accept(quarter1);
		scs.coinSlot.accept(loonie);
		test.completeCurrentPaymentMethod();
		
		test.chooseBanknote();
		//input tenner
		scs.banknoteInput.accept(ten_dollars);		
		test.completeCurrentPaymentMethod();
		
		//select Membership Card
		test.chooseMembership(membersStub);
		//swipe until data is read
		boolean swiped = false;
		while (!swiped) {
			try {
				scs.cardReader.swipe(member1);
				swiped = true;
			} catch (IOException e) {

			}
		}
		test.completeMembershipRecognition();
		
		//select credit card, pay 30.00
		test.chooseCredit(bank, new BigDecimal("30.00"));
		//tap until data is read
		boolean tapped = false;
		while (!tapped) {
			try {
				while(scs.cardReader.tap(credit1) == null) {
					
				};
				tapped = true;
			} catch (IOException e) {
	
			}
		}
		test.completeCurrentPaymentMethod();
		
		//select debit card, pay 40.00
		test.chooseDebit(bank, new BigDecimal("40.00"));
		//tap until data is read
		tapped = false;
		while (!tapped) {
			try {
				while(scs.cardReader.tap(debit1) == null) {
					
				};
				tapped = true;
			} catch (IOException e) {
	
			}
		}
		test.completeCurrentPaymentMethod();		
		//we have now paid 5 + 1.25 + 10 + 30 + 40.00 = $86.25
		
		//expect that transaction completes successfully
		test.finishPayment();
		String rec = scs.printer.removeReceipt();
		//check that the receipt is correct?
		assertEquals("expected transaction to finish successfully.",
				"\np4 $2.00\n"
				+ "\n"
				+ "p2 $3.00\n"
				+ "\n"
				+ "p3 $10.00\n"
				+ "\n"
				+ "p5 $60.00\n"
				+ "\n"
				+ "Total: $75.00\n"
				+ "\n"
				+ "Paid: $86.25\n"
				+ "\n"
				+ "Paid $5.00 with: Banknotes\n"
				+ "\n"
				+ "Paid $1.25 with: Coins\n"
				+ "\n"
				+ "Paid $10.00 with: Banknotes\n"
				+ "\n"
				+ "Paid $30.00 with: credit\n"
				+ "\n"
				+ "Paid $40.00 with: debit\n"
				+ "\n"
				+ "Change: $11.25\n"
				+ "\n"
				+ "Member number: 11113\n",rec);
		
		//collect all the change
		BigDecimal totalChange = BigDecimal.ZERO;
		boolean hasCoins = true;
		while (hasCoins) {
			hasCoins = !test.returnCoinChange(); //returns true once the last batch coin change is returned
			List<Coin> coinChange = scs.coinTray.collectCoins();
			for (int i = 0; i < coinChange.size() && coinChange.get(i)!= null; i++) { //add all coin values together	
				totalChange = totalChange.add(coinChange.get(i).getValue());
			}
		}
		test.returnBanknoteChange();
		while (!scs.banknoteOutput.hasSpace()) {
			int value;
			Banknote[] bankNote = scs.banknoteOutput.removeDanglingBanknotes();
			for (int i = 0; i < bankNote.length; i++)
			{
				value = bankNote[i].getValue();
				totalChange = totalChange.add(new BigDecimal(value));
			}
		}
		BigDecimal expectedChange = new BigDecimal("11.25");
		assertEquals("wrong amount of change, expected 11.25", 0, totalChange.compareTo(expectedChange));
	}
	
	//=================================================
	// Testing another transaction with inserting banknotes, coins, credit, debit, and swiping a membership card
	// also returns mixed change
	//=================================================	
	@Test
	public void testFinishTransMixedPaymentMixedChange2() throws DisabledException, OverloadException, EmptyException {		
		products.add(p4);
		products.add(p2);
		products.add(p3);
		products.add(p5);
		test = new Checkout(scs, (ArrayList<Product>) products, new BigDecimal("75.00"));
		scs.banknoteDispensers.get(bval2).load(new Banknote(defcur, bval2), new Banknote(defcur, bval2)); //load two five dollar bills
		scs.banknoteDispensers.get(bval4).load(new Banknote(defcur, bval4)); //load a twenty dollar bill
		scs.coinDispensers.get(cval1).load(new Coin(cval1), new Coin(cval1), new Coin(cval1)); //load 3 quarters
		scs.coinDispensers.get(cval2).load(new Coin(cval2), new Coin(cval2), new Coin(cval2)); //load 3 dimes
		scs.coinDispensers.get(cval3).load(new Coin(cval3), new Coin(cval3), new Coin(cval3)); //load 3 nickels
		scs.coinDispensers.get(cval4).load(new Coin(cval4), new Coin(cval4), new Coin(cval4)); //load 3 loonies
		
		test.chooseBanknote();
		//input fiver
		scs.banknoteInput.accept(fiver1);
		test.completeCurrentPaymentMethod();
		
		test.chooseCoin();
		//input quarter and loonie 
		scs.coinSlot.accept(quarter1);
		scs.coinSlot.accept(loonie);
		test.completeCurrentPaymentMethod();
		
		test.chooseBanknote();
		//input tenner
		scs.banknoteInput.accept(ten_dollars);		
		test.completeCurrentPaymentMethod();
		
		//select Membership Card
		test.chooseMembership(membersStub);
		//swipe until data is read
		boolean swiped = false;
		while (!swiped) {
			try {
				scs.cardReader.swipe(member1);
				swiped = true;
			} catch (IOException e) {

			}
		}
		test.completeMembershipRecognition();
		
		//select credit card, pay 30.00
		test.chooseCredit(bank, new BigDecimal("30.00"));
		//tap until data is read
		boolean tapped = false;
		while (!tapped) {
			try {
				while(scs.cardReader.tap(credit1) == null) {
					
				};
				tapped = true;
			} catch (IOException e) {
	
			}
		}
		test.completeCurrentPaymentMethod();
		
		//select debit card, pay 40.10
		test.chooseDebit(bank, new BigDecimal("40.10"));
		//tap until data is read
		tapped = false;
		while (!tapped) {
			try {
				while(scs.cardReader.tap(debit1) == null) {
					
				};
				tapped = true;
			} catch (IOException e) {
	
			}
		}
		test.completeCurrentPaymentMethod();		
		//we have now paid 5 + 1.25 + 10 + 30 + 40.00 = $86.25
		
		//expect that transaction completes successfully
		test.finishPayment();
		String rec = scs.printer.removeReceipt();
		//check that the receipt is correct?
		assertEquals("expected transaction to finish successfully.",
				"\np4 $2.00\n"
				+ "\n"
				+ "p2 $3.00\n"
				+ "\n"
				+ "p3 $10.00\n"
				+ "\n"
				+ "p5 $60.00\n"
				+ "\n"
				+ "Total: $75.00\n"
				+ "\n"
				+ "Paid: $86.35\n"
				+ "\n"
				+ "Paid $5.00 with: Banknotes\n"
				+ "\n"
				+ "Paid $1.25 with: Coins\n"
				+ "\n"
				+ "Paid $10.00 with: Banknotes\n"
				+ "\n"
				+ "Paid $30.00 with: credit\n"
				+ "\n"
				+ "Paid $40.10 with: debit\n"
				+ "\n"
				+ "Change: $11.35\n"
				+ "\n"
				+ "Member number: 11113\n",rec);
		
		//collect all the change
		BigDecimal totalChange = BigDecimal.ZERO;
		boolean hasCoins = true;
		while (hasCoins) {
			hasCoins = !test.returnCoinChange(); //returns true once the last batch coin change is returned
			List<Coin> coinChange = scs.coinTray.collectCoins();
			for (int i = 0; i < coinChange.size() && coinChange.get(i)!= null; i++) { //add all coin values together	
				totalChange = totalChange.add(coinChange.get(i).getValue());
			}
		}
		test.returnBanknoteChange();
		while (!scs.banknoteOutput.hasSpace()) {
			int value;
			Banknote[] bankNote = scs.banknoteOutput.removeDanglingBanknotes();
			for (int i = 0; i < bankNote.length; i++)
			{
				value = bankNote[i].getValue();
				totalChange = totalChange.add(new BigDecimal(value));
			}
		}
		BigDecimal expectedChange = new BigDecimal("11.35");
		assertEquals("wrong amount of change, expected 11.35", 0, totalChange.compareTo(expectedChange));
	}

	//=================================================
	// Testing another transaction with so many coins returned as change that the coin tray 
	// is filled multiple times, and must be emptied multiple times
	//=================================================	
	@Test
	public void testFinishTransManyCoinsChange() throws DisabledException, OverloadException, EmptyException {
		products.add(p2);
		products.add(p3);
		test = new Checkout(scs, (ArrayList<Product>) products, new BigDecimal("13.00"));
		for (int i = 0; i < 60; i++) {
				scs.coinDispensers.get(cval1).load(new Coin(cval1)); //load 60 quarters in total
		}
		test.chooseBanknote();
		//input twenty bucks
		scs.banknoteInput.accept(twenty_bucks);
		//expect that transaction completes successfully idk what that looks like yet
		test.completeCurrentPaymentMethod();
		test.finishPayment();
		String rec = scs.printer.removeReceipt();
		System.out.println(rec);
		//check that the receipt is correct?
		assertEquals("expected transaction to finish successfully.",
				"\np2 $3.00\n"
				+ "\n"
				+ "p3 $10.00\n"
				+ "\n"
				+ "Total: $13.00\n"
				+ "\n"
				+ "Paid: $20.00\n"
				+ "\n"
				+ "Paid $20.00 with: Banknotes\n"
				+ "\n"
				+ "Change: $7.00\n",rec);
		BigDecimal totalChange = BigDecimal.ZERO;
		boolean hasCoins = true;
		while (hasCoins) {
			hasCoins = !test.returnCoinChange(); //returns true once the last batch coin change is returned
			List<Coin> coinChange = scs.coinTray.collectCoins();
			for (int i = 0; i < coinChange.size() && coinChange.get(i)!= null; i++) { //add all coin values together	
				totalChange = totalChange.add(coinChange.get(i).getValue());
			}
		}
		// we expect 28 dimes to be returned, which means that the coin tray must be emptied
		// twice times since it only holds 20 coins
		
		test.returnBanknoteChange();
		while (!scs.banknoteOutput.hasSpace()) {
			int value;
			Banknote[] bankNote = scs.banknoteOutput.removeDanglingBanknotes();
			for (int i = 0; i < bankNote.length; i++)
			{
				value = bankNote[i].getValue();
				totalChange = totalChange.add(new BigDecimal(value));
			}
		}
		BigDecimal expectedChange = new BigDecimal("7.00");
		assertEquals("wrong amount of change, expected 7", 0, totalChange.compareTo(expectedChange));
	}
	
	//=================================================
	// Testing a transaction where the exact change cannot be made
	//=================================================	
	@Test
	public void testFinishTransCannotMakeChange() throws DisabledException, SimulationException, OverloadException, EmptyException {
		products.add(p1);
		test = new Checkout(scs, (ArrayList<Product>) products, new BigDecimal("1.25"));
		scs.banknoteDispensers.get(bval2).load(new Banknote(defcur, bval2)); //load a fiver
		
		test.chooseBanknote();
		//input quarter and loonie (value equal to total owed
		scs.banknoteInput.accept(ten_dollars);
		//expect that transaction completes successfully idk what that looks like yet
		test.completeCurrentPaymentMethod();
		test.finishPayment();
		String rec = "";
		while (true) {
			try {
				rec = scs.printer.removeReceipt();
				break;
			} catch (InvalidArgumentSimulationException e) {
				
			}
		}
		//check that the receipt is correct
		assertEquals("expected transaction to finish successfully.",
				"\np1 $1.25\n"
				+ "\n"
				+ "Total: $1.25\n"
				+ "\n"
				+ "Paid: $10.00\n"
				+ "\n"
				+ "Paid $10.00 with: Banknotes\n"
				+ "\n"
				+ "Change: $8.75\n",rec);
		
		//now we collect change, expect to not get any
		BigDecimal totalChange = BigDecimal.ZERO;
		boolean hasCoins = true;
		while (hasCoins) {
			hasCoins = !test.returnCoinChange(); //returns true once the last batch coin change is returned
			List<Coin> coinChange = scs.coinTray.collectCoins();
			for (int i = 0; i < coinChange.size() && coinChange.get(i)!= null; i++) { //add all coin values together	
				totalChange = totalChange.add(coinChange.get(i).getValue());
			}
		}
		test.returnBanknoteChange();
		while (!scs.banknoteOutput.hasSpace()) {
			int value;
			Banknote[] bankNote = scs.banknoteOutput.removeDanglingBanknotes();
			for (int i = 0; i < bankNote.length; i++)
			{
				value = bankNote[i].getValue();
				totalChange = totalChange.add(new BigDecimal(value));
			}
		}
		BigDecimal expectedChange = new BigDecimal("0.00"); //the machine cannot give out enough change
		assertEquals("wrong amount of change, expected 0.00", 0, totalChange.compareTo(expectedChange));
	}

	//=================================================
	// Testing a transaction where the banknote storage is filled and the banknote cannot be accepted
	//=================================================		
	@Test (expected = InvalidArgumentSimulationException.class)
	public void testBankoteStorageFull() throws DisabledException, OverloadException {
		products.add(p3);
		//load 1000 five dollar bills
		for (int i= 0; i < 1000; i++) {
			scs.banknoteStorage.load(new Banknote(defcur, bval2));
		}
		
		test = new Checkout(scs, (ArrayList<Product>) products, new BigDecimal("10.00"));
		test.chooseBanknote();
		//input ten dollars
		scs.banknoteInput.accept(ten_dollars);
		//storage is full , take ten dollars back out
		
		scs.banknoteInput.removeDanglingBanknotes();
		test.completeCurrentPaymentMethod();
		//expect that the ten dollars has not been paid, cannot print the receipt
		test.finishPayment();
		//expect a simulation exception since there is no receipt to take
		String rec = scs.printer.removeReceipt();
	}
	
//	These next tests are for cases where an error is made by the user, including blocked cards,
//	invalid cards, bad banknotes, and bad coins
	
	//=================================================
	// Testing the customer not paying enough. Expected receipt not to print and simulation exception thrown.
	//=================================================	
	@Test (expected = InvalidArgumentSimulationException.class)
	public void testFinishTransUnpaid() throws DisabledException, OverloadException {
		products.add(p1);
		test = new Checkout(scs, (ArrayList<Product>) products, pval1);
		test.chooseCoin();
		//input 2 quarters
		scs.coinSlot.accept(quarter1);
		scs.coinSlot.accept(quarter2);
		test.completeCurrentPaymentMethod();
		test.finishPayment();
		//expect that the receipt has not been printed
		scs.printer.removeReceipt();
	}

	//=================================================
	// Testing the customer paying with a bad coin and bad banknote.
	//=================================================	
	@Test (expected = InvalidArgumentSimulationException.class)
	public void testFinishTransFakeCash() throws DisabledException, OverloadException {
		products.add(p1);
		test = new Checkout(scs, (ArrayList<Product>) products, pval1);
		test.chooseCoin();
		//input invalid coin
		scs.coinSlot.accept(invalid1C);
		test.completeCurrentPaymentMethod();
		
		test.chooseBanknote();
		//input invalid banknote
		scs.banknoteInput.accept(invalid1B);
		test.completeCurrentPaymentMethod();
		
		test.finishPayment();
		//expect that the receipt has not been printed
		scs.printer.removeReceipt();
	}
	
	//=================================================
	// Testing the customer trying to pay with some bad cards, before finally paying with a good one. 
	//=================================================	
	@Test
	public void testBadCards() throws DisabledException, OverloadException, EmptyException {		
		products.add(p4);
		products.add(p2);
		products.add(p3);
		test = new Checkout(scs, (ArrayList<Product>) products, new BigDecimal("15.00"));
		//set up the bank to reject cards
		Card badCredit = new Card("Credit", "00", "Customer", "111", "1111", true, true);
		Card badDebit = new Card("Debit", "00", "Customer", "111", "1111", true, true);
		
		//select credit card, pay 10.00
		test.chooseCredit(bank, new BigDecimal("10.00"));
		//tap until data is read, using a debit card which should not work
		boolean tapped = false;
		while (!tapped) {
			try {
				while(scs.cardReader.tap(badDebit) == null) {
					
				};
				tapped = true;
			} catch (IOException e) {
	
			}
		}
		//expect the payment to not go through
		test.completeCurrentPaymentMethod();

		//now we use an actual credit card, but bank does not accept it
		test.chooseCredit(bank, new BigDecimal("11.00"));
		tapped = false;
		while (!tapped) {
			try {
				while(scs.cardReader.tap(badCredit) == null) {
					
				};
				tapped = true;
			} catch (IOException e) {
	
			}
		}
		//expect nothing to be paid because the bank has invalidated this transaction
		test.completeCurrentPaymentMethod();
		
		//select debit card, pay 12.00, try with a credit card
		test.chooseDebit(bank, new BigDecimal("12.00"));
		//tap until data is read, using a credit card which should not work
		tapped = false;
		while (!tapped) {
			try {
				while(scs.cardReader.tap(credit1) == null) {
					
				};
				tapped = true;
			} catch (IOException e) {
	
			}
		}
		//expect the payment to not go through
		test.completeCurrentPaymentMethod();
		
		//now we use an actual debit card, but bank does not accept it
		test.chooseDebit(bank, new BigDecimal("13.00"));
		//tap until data is read
		tapped = false;
		while (!tapped) {
			try {
				while(scs.cardReader.tap(badDebit) == null) {
					
				};
				tapped = true;
			} catch (IOException e) {
	
			}
		}
		//expect nothing to be paid because the bank has invalidated this transaction
		test.completeCurrentPaymentMethod();		
		
		//now the bank will take the other credit card
	
		test.chooseCredit(bank, new BigDecimal("15.00"));
		//tap until data is read, using a debit card which should not work
		boolean swiped = false;
		while (!swiped) {
			try {
				while(scs.cardReader.swipe(credit2) == null) {
					
				};
				swiped = true;
			} catch (IOException e) {
	
			}
		}
		//expect the transaction to finish successfully
		test.completeCurrentPaymentMethod();	
		
		//now we try to scan a credit card as a membership card
		test.chooseMembership(membersStub);
		//swipe until data is read
		swiped = false;
		while (!swiped) {
			try {
				scs.cardReader.swipe(credit1);
				swiped = true;
			} catch (IOException e) {

			}
		}
		//this should not work, membership number is not added to the receipt
		test.completeMembershipRecognition();

		test.finishPayment();
		String rec = scs.printer.removeReceipt();
		//check that the receipt is correct?
		assertEquals("expected transaction to finish successfully.",
				"\np4 $2.00\n"
				+ "\n"
				+ "p2 $3.00\n"
				+ "\n"
				+ "p3 $10.00\n"
				+ "\n"
				+ "Total: $15.00\n"
				+ "\n"
				+ "Paid: $15.00\n"
				+ "\n"
				+ "Paid $15.00 with: credit\n"
				+ "\n"
				+ "Change: $0.00\n",rec);
		
	}
	
	//=================================================
	// Testing a transaction with wrong credit card pin, causing blockage
	//=================================================
	@Test
	public void testWrongPINCredit() {
		products.add(p4);
		products.add(p2);
		products.add(p3);
		test = new Checkout(scs, (ArrayList<Product>) products, new BigDecimal("15.00"));
		
		//select the payment method: Credit, and amount: $15.00
		BigDecimal amount = new BigDecimal("15.00");
		test.chooseCredit(bank, amount);
		
		//insert and enter pin until three bad tries
		boolean inserted = false;
		int tries = 0;
		while (!inserted && tries < 3) {
			try {
				scs.cardReader.insert(credit1, "0000"); //this PIN is incorrect
				inserted = true;
			} catch (ChipFailureException e) {
				scs.cardReader.remove();
			} catch (InvalidPINException e) {
				scs.cardReader.remove();
				tries += 1;
			} catch (IOException e) {

			}
		}
		
		inserted = false;
		boolean blocked = false;
		while (!inserted) {
			try {
				scs.cardReader.insert(credit1, "1111"); //this PIN is correct, but card is blocked
				inserted = true;
			} catch (ChipFailureException e) {
				scs.cardReader.remove();
			} catch (BlockedCardException e) {
				scs.cardReader.remove();
				blocked = true;
				inserted = true;
			} catch (IOException e) {

			}
		}
		assertTrue(blocked);
	}
	
	//=================================================
	// Testing a transaction with bad membership card
	//=================================================
	@Test
	public void testBadMembership() throws DisabledException, OverloadException {
		products.add(p4);
		products.add(p2);
		products.add(p3);
		test = new Checkout(scs, (ArrayList<Product>) products, new BigDecimal("15.00"));
		Card card = new Card("Membership", "0000", "Customer", "111", "1111", false, false);

		
		test.chooseBanknote();
		//input fiver and ten dollars
		scs.banknoteInput.accept(fiver1);
		scs.banknoteInput.accept(ten_dollars);
		test.completeCurrentPaymentMethod();
		
		//select Membership Card
		test.chooseMembership(membersStub);
		
		//swipe until data is read
		boolean swiped = false;
		while (!swiped) {
			try {
				scs.cardReader.swipe(card);
				swiped = true;
			} catch (IOException e) {

			}
		}
		test.completeMembershipRecognition();
		//membership not added because the card was invalid
		
		//expect that transaction completes successfully idk what that looks like yet
		test.finishPayment();
		String rec = scs.printer.removeReceipt();
		//check that the receipt is correct?
		assertEquals("expected transaction to finish successfully.",
				"\np4 $2.00\n"
				+ "\n"
				+ "p2 $3.00\n"
				+ "\n"
				+ "p3 $10.00\n"
				+ "\n"
				+ "Total: $15.00\n"
				+ "\n"
				+ "Paid: $15.00\n"
				+ "\n"
				+ "Paid $15.00 with: Banknotes\n"
				+ "\n"
				+ "Change: $0.00\n",rec);
	}

}
