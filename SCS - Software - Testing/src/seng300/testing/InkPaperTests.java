package seng300.testing;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.Currency;

import org.junit.*;
import org.lsmr.selfcheckout.devices.EmptyException;
import org.lsmr.selfcheckout.devices.OverloadException;
import org.lsmr.selfcheckout.devices.ReceiptPrinter;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import seng300.software.SelfCheckoutSystemLogic;

public class InkPaperTests {
	
	SelfCheckoutStation scs;
	SelfCheckoutSystemLogic scsLogic;
	
	Currency currency = Currency.getInstance("CAD");
	int[] banknoteDenominations = {1,5,10,15,20,50,100};
	BigDecimal[] coinDenominations = {new BigDecimal("0.05"),new BigDecimal("0.10"),new BigDecimal("0.25"),new BigDecimal("0.50")};
	int scaleMaximumWeight = 1000;
	int scaleSensitivity = 1;
	
	
	@Before 
	public void setup() {
		
		scs = new SelfCheckoutStation(currency, banknoteDenominations, coinDenominations, scaleMaximumWeight, scaleSensitivity);
		scsLogic = new SelfCheckoutSystemLogic(scs);
		scsLogic.attachGUI();
		SelfCheckoutSystemLogic.attachBlockNotifiableGui(new AttendantGuiStub());
		this.scsLogic.testMode();
	}
	
	@After
	public void teardown() {
		
	}
	
	
	@Test 
	public void testPrinterNopaper() throws EmptyException, OverloadException {
		
		scsLogic.station.printer.addInk(ReceiptPrinter.MAXIMUM_INK);	
		
		// should notify station of ink added
		// should disable printer because no paper added
		
		boolean expected = true;
		boolean actual = scsLogic.station.printer.isDisabled();	
		
		assertEquals("Printer should be disabled", expected, actual);
	}
	
	@Test 
	public void testPrinterNoInk() throws EmptyException, OverloadException {
		
		scsLogic.station.printer.addPaper(ReceiptPrinter.MAXIMUM_PAPER);	
		
		// should notify station of paper added
		// should disable printer because no ink added
		
		boolean expected = true;
		boolean actual = scsLogic.station.printer.isDisabled();
		assertEquals("Printer should be disabled", expected, actual);
	}
	
	@Test
	public void testOutOfPaper() throws EmptyException, OverloadException {
		
		scsLogic.station.printer.addInk(ReceiptPrinter.MAXIMUM_INK);	
		scsLogic.station.printer.addPaper(1);
	
		boolean expected = false;
		boolean actual = scsLogic.station.printer.isDisabled();
		assertEquals("Printer should be enabled", expected, actual);
		
		scsLogic.station.printer.print('\n');
		
		// runs out of paper, should notify station and disable printer
		
		expected = true;
		actual = scsLogic.station.printer.isDisabled();
		assertEquals("Printer should be disabled", expected, actual);
		
		
		
	}
	
	@Test
	public void testOutOfInk() throws EmptyException, OverloadException {
		
		scsLogic.station.printer.addPaper(ReceiptPrinter.MAXIMUM_PAPER);	
		scsLogic.station.printer.addInk(1);
		
		boolean expected = false;
		boolean actual = scsLogic.station.printer.isDisabled();
		assertEquals("Printer should be enabled", expected, actual);
		
		scsLogic.station.printer.print('c');
		
		// runs out of ink, should notify station and disable printer
		
		expected = true;
		actual = scsLogic.station.printer.isDisabled();
		assertEquals("Printer should be disabled", expected, actual);
		
		
	}
	
	
}

