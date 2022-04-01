package seng300.software;

import java.math.BigDecimal;

import org.lsmr.selfcheckout.Card.CardData;
import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.CardReader;
import org.lsmr.selfcheckout.devices.observers.AbstractDeviceObserver;
import org.lsmr.selfcheckout.devices.observers.CardReaderObserver;

import seng300.software.exceptions.BadCardException;
import seng300.software.exceptions.ValidationException;

//made by ibrahim

public class CardHandler implements CardReaderObserver{
	
	/**
	 * CardHandler class
	 * Instructions for use:
	 *   Instantiate as CardHandler(String, CardReader) if you intend to use this to test .readMemberCard();
	 *   Instantiate as CardHandler(String, Bigdecimal CardReader, BankStub) if you intend to use this to test .readDebitCard() or .readCreditCard;
	 *   
	 *In order to instance the lastDataRead field, the private .notifyCardDataRead() method in CardReader must be called on the reader this observer is
	 *attached too. 
	 */
	
	private BankStub bank = null;
	private MembersProgramStub members;
	private CardData lastDataRead = null;
	private String expectedType = "";  //credit, debit, or membership
	private BigDecimal totalDue = new BigDecimal(0);
	private BigDecimal totalPaid = new BigDecimal(0);
	
	public CardHandler(String expectedType, CardReader reader, MembersProgramStub members) { //constructor to call if intended operation is to scan the membership card
		this.expectedType = expectedType;
		this.members = members;
		reader.attach(this);
	}
	
	public CardHandler(String expectedType, BigDecimal total, CardReader reader, BankStub bank) { //constructor to call for payment
		this.expectedType = expectedType;
		setTotal(total);
		this.totalPaid = BigDecimal.ZERO;
		this.bank = bank;
		reader.attach(this);
	}
	
	public void enabled(AbstractDevice<? extends AbstractDeviceObserver> device) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disabled(AbstractDevice<? extends AbstractDeviceObserver> device) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cardInserted(CardReader reader) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cardRemoved(CardReader reader) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cardTapped(CardReader reader) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cardSwiped(CardReader reader) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cardDataRead(CardReader reader, CardData data) {
		this.lastDataRead = data;
	}
	
	public CardData getLastDataRead() {
		return lastDataRead;
	}

	public void reset() {
		this.lastDataRead = null;
	}
	
	
	public void readDebitCard() throws BadCardException  {
		if (lastDataRead.getType().toLowerCase().equals(expectedType)){
			if (!bank.validateDebitTransaction(lastDataRead.getNumber())) {
				//nothing happens, check if total has changed in order to determine if the validation succeeded.
			}
			else {
				bank.pay(lastDataRead.getNumber(), totalDue);
				totalPaid = totalDue;
				setTotal(BigDecimal.ZERO);
			}
		}
		else {
			throw new BadCardException();
		}
	}
	
	public void readCreditCard() throws BadCardException  {
		if (lastDataRead.getType().toLowerCase().equals(expectedType)){
			if (!bank.validateCreditTransaction(lastDataRead.getNumber())) {
				//nothing happens, check if total has changed in order to determine if the validation succeeded.
			}
			else {
				bank.pay(lastDataRead.getNumber(), totalDue);
				totalPaid = totalDue;
				setTotal(BigDecimal.ZERO);
			}
		}
		else {
			throw new BadCardException();
		}
	}
	
	public String readMemberCard() throws BadCardException, ValidationException  {
		if (lastDataRead.getType().toLowerCase().equals(expectedType)){
			if (!members.validateMemebership()) {
				throw new ValidationException(); //only way to inform failure at this point,
			}
			else {
				return lastDataRead.getNumber();
				//might add code here to add stuff to the rewards program, details currently unknown
			}
		}
		else {
			throw new BadCardException();
		}
	}
	
	public BigDecimal getTotal() {
		return totalDue;
	}

	public void setTotal(BigDecimal total) {
		this.totalDue = total;
	}

	public BigDecimal amountPaid() {
		return totalPaid;
	}
	
	public String paymentMethod() {
		return expectedType;
	}

	
}
