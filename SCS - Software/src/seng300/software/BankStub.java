package seng300.software;

import java.math.BigDecimal;

//made by ibrahim

public class BankStub {

	
	boolean validCredit = true;
	
	boolean validDebit = true;
	

	public void setValidCredit(boolean validCredit) {
		this.validCredit = validCredit;
	}

	public void setValidDebit(boolean validDebit) {
		this.validDebit = validDebit;
	}

	public boolean validateDebitTransaction(String number) {
		return validDebit;
	}
	
	public boolean validateCreditTransaction(String number) {
		return validCredit;
	}
	
	public void pay(String number, BigDecimal cost) {
		
	}
}
