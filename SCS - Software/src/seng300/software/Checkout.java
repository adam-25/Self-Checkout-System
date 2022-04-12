package seng300.software;


import org.lsmr.selfcheckout.products.*;

import seng300.software.exceptions.BadCardException;
import seng300.software.exceptions.ValidationException;

import org.lsmr.selfcheckout.devices.*;
import org.lsmr.selfcheckout.external.CardIssuer;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

// code base from alexanna's IT1 group 11
// updated by ibrahim and alexanna

public class Checkout {
	
	private SelfCheckoutStation scs;
	private ArrayList<Product> products;
	private String membershipNumber = ""; //Should never be null as it always gets printed!
	private String giftNumber = ""; //hotfix, until paywithGift is better

	private BigDecimal totalcost;
	private BigDecimal totalchange;

	private BigDecimal totalAmountPaid = new BigDecimal(0.0); 
	private ArrayList<String> payments = new ArrayList<String>();
	
	private PayWithCoin paycoin;
	private PayWithBanknote paybanknote;
	private CardHandler cardHandler = null;
	
	private ReturnChange returnChange;
	
	
	
	//Constructor: this class is assuming a test file will create an instance of it
	//and give the total cost of the cart from ScanItem along with the relevant scs
	//to simulate a customer pressing a checkout button. When implemented, the list of
	//items should be passed as well

	public Checkout (SelfCheckoutStation scs, ArrayList<Product> products, BigDecimal cost) {
		
		this.scs = scs;
		
		this.totalcost = cost;
		
		this.products = products;
		
	}
	
	public BigDecimal getTotalchange() {
		return totalchange;
	}

	public BigDecimal getTotalAmountPaid() {
		return totalAmountPaid;
	}

	//the customer has changed the items they want to purchase
	public void update( BigDecimal newCost) {
		
		totalcost = newCost;
		
	}
	
	//should be called by test file to simulate customer choosing two enter a membership
	public void chooseMembership(CardIssuer membership) {
		
		cardHandler = new CardHandler("membership", scs.cardReader, membership);
	}
	
	//should be called by test file to simulate customer paying with credit
	public void chooseCredit(CardIssuer bank, BigDecimal amountToPay) {
		
		cardHandler = new CardHandler("credit", amountToPay, scs.cardReader, bank);
	}
	
	//should be called by test file to simulate customer paying with debit
	public void chooseDebit(CardIssuer bank, BigDecimal amountToPay) {
		
		cardHandler = new CardHandler("debit", amountToPay, scs.cardReader, bank);
	}
	
	//should be called by test file to simulate customer choosing to pay with banknotes
	public void chooseBanknote() {
		
		paybanknote = new PayWithBanknote(scs);
	}
	
	//should be called by the test file to simulate customer choosing to pay with coin
	public void chooseCoin() {
		
		paycoin = new PayWithCoin(scs);
	}
	
	//should be called after simulating the scanning of a membership card. 
	public void completeMembershipRecognition() {
		
		try { //checks for standard card errors
			this.membershipNumber = this.cardHandler.readMemberCard();
		} catch (BadCardException e) {
			//System.out.println("Sorry, that wasn't a membership card");
		} catch (ValidationException e) {
			//System.out.println("Your card could not be validated, please try scanning it again");
		}
		
	}
	
	//In case the customer chooses to not want to use a membership card
	public void resetMembershipNumber() {
		this.membershipNumber = ""; //recall, membershipNumber should never be null!
	}
	
	public void completeCurrentPaymentMethod() {
		
		//if they chose coin
		if (paycoin != null) {
			
			BigDecimal thispayment = paycoin.amountPaid();
			
			//make sure it like worked and stuff
			if(thispayment.compareTo(BigDecimal.ZERO) > 0) {

				BigDecimal payment = thispayment.setScale(2, RoundingMode.HALF_EVEN);
				
				payments.add("Paid $" + payment.toPlainString() + " with: Coins");

				totalAmountPaid = totalAmountPaid.add(thispayment);
			}
			
			paycoin = null;
		}
		
		//if they chose banknote
		if (paybanknote != null) {
			
			BigDecimal thispayment = paybanknote.amountPaid();
			
			//make sure it like worked and stuff
			if(thispayment.compareTo(BigDecimal.ZERO) > 0) {

				BigDecimal payment = thispayment.setScale(2, RoundingMode.HALF_EVEN);
				
				payments.add("Paid $" + payment.toPlainString() + " with: Banknotes");

				totalAmountPaid = totalAmountPaid.add(thispayment);
			}
			
			paybanknote = null;
		}
		
		//if they chose a card payment method
		if (cardHandler != null) {
			
			//see what payment method we are expecting
			String paymentMethod = cardHandler.paymentMethod();
			
			//if we are expecting credit call the credit method
			if (paymentMethod.toLowerCase().equals("credit")) {
				
				try {
					//see if there was an oopsie woopsie
					cardHandler.readCreditCard();
					
				} catch (BadCardException e) {
					
					System.out.println("Card read failed. Please Try again");
					cardHandler = null;
					return;

				}
			}
			
			//if we are expecting debit call the debit method
			else if (paymentMethod.toLowerCase().equals("debit")) {
				
				try {
					//see if there was an oopsie woopsie
					cardHandler.readDebitCard();
					
				} catch (BadCardException e) {
					
					System.out.println("Card read failed. Please Try again");
					cardHandler = null;
					return;
				}
			}
			
			else {
				//can't pay with a memebership card sadge
				System.out.println("Invalid card payment method.");
				cardHandler = null;
				return;
			}
			
			//get the value of the payment
			BigDecimal thispayment = cardHandler.amountPaid();
			
			//make sure it like worked and stuff
			if(thispayment.compareTo(BigDecimal.ZERO) > 0) {

				BigDecimal payment = thispayment.setScale(2, RoundingMode.HALF_EVEN);
				
				payments.add("Paid $" + payment.toPlainString() + " with: " + paymentMethod);
				
				totalAmountPaid = totalAmountPaid.add(thispayment);
			}
			else {
				
				System.out.println("Invalid transaction.");

			}
			
			cardHandler = null;
		}
		
		
	}
	
	//should be called by the test file to simulate customer wanting to finish payment
	public void finishPayment() {
		
		BigDecimal changeDue = totalcost.subtract(totalAmountPaid);
		
		if (changeDue.compareTo(BigDecimal.ZERO) <= 0) {
			
			totalchange = changeDue.abs();
			
			returnChange = new ReturnChange(scs, totalchange);

			printReceipt();
			
		} else {
			
			System.out.println("you have not paid enough yet!");
		}
			
	}

	//	wrapper for the ReturnChange method ejectBanknote()
	//	usage:
	//	checkout.returnBanknoteChange();
	//	*larp to remove the banknote*
	//	*larp to remove the banknote*
	//	*larp to remove the banknote*
	//	*larp to remove the banknote*
	public void returnBanknoteChange() throws EmptyException, DisabledException, OverloadException {
		
		returnChange.ejectBanknote();
	}
	

	//  wrapper for the ReturnChange method ejectCoin()
	//	usage:
	//	checkout.returnCoinChange();
	//  <check return value>
	//	if it returned false the coin tray is full but there is more change to be returned:
	//	*larp to remove the coins*
	//	checkout.returnCoinChange();
	//  <check return value>
	//	if it returned true all coin change has been returned:
	//	*larp to remove the coins*
	public boolean returnCoinChange() {
		
		return returnChange.ejectCoin();
	}
	
	//receipt printing method. Does not handle receipt hardware-related problems i.e paper/ink shortage
	private void printReceipt () {


		ArrayList<String> items = new ArrayList<String>();
		
		for (int i = 0; i < products.size(); i++) {
			
			Product p = products.get(i);
			String desc;
			if (p instanceof BarcodedProduct)
			{
			    desc = ((BarcodedProduct)p).getDescription();
			}
			else // p instanceof PLUCodedProduct
			{
			    desc = ((PLUCodedProduct)p).getDescription();
			}
			
			if(desc.length() > 50) {
				//chop off characters from desc so it's < 50 chars
				desc.substring(0, 49);
			}
			BigDecimal price = (products.get(i).getPrice()).setScale(2, RoundingMode.HALF_EVEN);
			
			items.add( desc + " $" + price.toPlainString());
			
		}
		
		BigDecimal value = totalcost.setScale(2, RoundingMode.HALF_EVEN);
		BigDecimal paid = totalAmountPaid.setScale(2, RoundingMode.HALF_EVEN);
		BigDecimal change = totalchange.setScale(2, RoundingMode.HALF_EVEN);
		
		items.add("Total: $" + value.toPlainString());
		items.add("Paid: $" + paid.toPlainString());
		items.addAll(payments);
		items.add("Change: $" + change.toPlainString());
		
		if (!giftNumber.equals("")) {
			items.add("Paid with giftCard: " + giftNumber);
		}
		
		
		//don't need to print membership if the membership is "" 
		if (!membershipNumber.equals("")) { 
			
			//but if they scanned one then print membership number at the end.
			items.add("Member number: "+this.membershipNumber);
		}
		
		for (int i = 0; i < items.size(); i++) {
			
			try {
				scs.printer.print('\n');
			} catch (EmptyException | OverloadException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			for (int j = 0; j < items.get(i).length(); j++) {
				
				//check if printer is disabled
				if(scs.printer.isDisabled()) {
					//cut paper
					scs.printer.cutPaper();
					//return
					return;
				}
				
				//otherwise carry on printing :)
				
				try {
					scs.printer.print(items.get(i).charAt(j));
				} catch (EmptyException | OverloadException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			try {
				scs.printer.print('\n');
			} catch (EmptyException | OverloadException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		scs.printer.cutPaper();
	}

	public void setGiftNumber(String giftNumber) { //hotfix until giftcard is fixed
		this.giftNumber = giftNumber;
		totalAmountPaid = totalcost;
	}
	
	public void reset() {
		ArrayList<Product> removal = new ArrayList<Product>();
		removal.addAll(this.products);
		products.removeAll(removal);
		
		membershipNumber = ""; //Should never be null as it always gets printed!
		giftNumber = ""; //hotfix, until paywithGift is better
		totalcost = new BigDecimal("0.00");
		totalchange = new BigDecimal("0.00");;
		totalAmountPaid = new BigDecimal(0.0); 
		payments = new ArrayList<String>();
	}
	
}
