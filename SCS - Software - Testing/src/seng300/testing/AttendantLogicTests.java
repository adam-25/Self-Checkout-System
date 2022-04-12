package seng300.testing;

import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.lsmr.selfcheckout.Banknote;
import org.lsmr.selfcheckout.Coin;
import org.lsmr.selfcheckout.SimulationException;
import org.lsmr.selfcheckout.devices.OverloadException;
import org.lsmr.selfcheckout.devices.ReceiptPrinter;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.SupervisionStation;

import org.junit.Assert;
import seng300.software.AttendantLogic;
import seng300.software.SelfCheckoutSystemLogic;
import seng300.software.exceptions.ValidationException;

public class AttendantLogicTests {

	private SelfCheckoutStation sc;
	private SelfCheckoutSystemLogic sl;
	private Currency currency = Currency.getInstance("CAD");
	
	BigDecimal coin1 = new BigDecimal("0.05");
	BigDecimal coin2 = new BigDecimal("0.10");
	BigDecimal coin3 = new BigDecimal("0.25");
	BigDecimal coin4 = new BigDecimal("1.00");
	BigDecimal coin5 = new BigDecimal("2.00");
	
	Coin dime = new Coin(currency, coin2);
	Coin loonie = new Coin(currency, coin4);
	Coin nickle = new Coin(currency, coin1);
	Coin quarter = new Coin(currency, coin3);
	Coin twoonie = new Coin(currency, coin5);
	
	Banknote note1 = new Banknote(currency, 1);
	Banknote note2 = new Banknote(currency, 5);
	Banknote note3 = new Banknote(currency, 10);
	Banknote note4 = new Banknote(currency, 20);
	Banknote note5 = new Banknote(currency, 50);
	Banknote note6 = new Banknote(currency, 100);

	private Banknote[] banknoteArray = {note1, note2, note3, note4, note5, note6};

	private Coin[] coinArray = {nickle, dime, quarter, loonie, twoonie};
	
	private SupervisionStation attendantStation;
	private AttendantLogic attendantLogic;

	@Before
	public void setUp()
	{
		attendantLogic = (AttendantLogic)SelfCheckoutSystemLogic.AttendantInstance;
		List<SelfCheckoutStation> scStation = AttendantLogic.ss.supervisedStations();
		sc = scStation.get(0);
		attendantStation = AttendantLogic.ss;
//		attendantStation.keyboard.attach(attendantLogic);
		sl = new SelfCheckoutSystemLogic(sc);
		//sl.attachGui(new DisableableGuiStub()) ;
		attendantStation.keyboard.type("87654321");
		attendantStation.keyboard.type("12345678");
		
		AttendantLogic.wantsToLogin();
	}
	
	@Test
	public void refillCoinDispenserTest() throws SimulationException, OverloadException
	{
		Assert.assertEquals(sc.coinDispensers.get(sc.coinDenominations.get(0)).size(), 0);
		Assert.assertEquals(sc.coinDispensers.get(sc.coinDenominations.get(1)).size(), 0);
		Assert.assertEquals(sc.coinDispensers.get(sc.coinDenominations.get(2)).size(), 0);
		Assert.assertEquals(sc.coinDispensers.get(sc.coinDenominations.get(3)).size(), 0);
		Assert.assertEquals(sc.coinDispensers.get(sc.coinDenominations.get(4)).size(), 0);
		
		attendantLogic.refillsCoinDispenser(sc);
		
		Assert.assertEquals(sc.coinDispensers.get(sc.coinDenominations.get(0)).size(), sc.coinDispensers.get(sc.coinDenominations.get(0)).getCapacity());
		Assert.assertEquals(sc.coinDispensers.get(sc.coinDenominations.get(1)).size(), sc.coinDispensers.get(sc.coinDenominations.get(1)).getCapacity());
		Assert.assertEquals(sc.coinDispensers.get(sc.coinDenominations.get(2)).size(), sc.coinDispensers.get(sc.coinDenominations.get(2)).getCapacity());
		Assert.assertEquals(sc.coinDispensers.get(sc.coinDenominations.get(3)).size(), sc.coinDispensers.get(sc.coinDenominations.get(3)).getCapacity());
		Assert.assertEquals(sc.coinDispensers.get(sc.coinDenominations.get(4)).size(), sc.coinDispensers.get(sc.coinDenominations.get(4)).getCapacity());

	}
	
	@Test
	public void refillBanknoteDispenserTest() throws SimulationException, OverloadException
	{
		Assert.assertEquals(sc.banknoteDispensers.get(sc.banknoteDenominations[0]).size(), 0);
		Assert.assertEquals(sc.banknoteDispensers.get(sc.banknoteDenominations[1]).size(), 0);
		Assert.assertEquals(sc.banknoteDispensers.get(sc.banknoteDenominations[2]).size(), 0);
		Assert.assertEquals(sc.banknoteDispensers.get(sc.banknoteDenominations[3]).size(), 0);
		Assert.assertEquals(sc.banknoteDispensers.get(sc.banknoteDenominations[4]).size(), 0);
		Assert.assertEquals(sc.banknoteDispensers.get(sc.banknoteDenominations[5]).size(), 0);
		
		attendantLogic.refillsBanknoteDispenser(sc);
		
		Assert.assertEquals(sc.banknoteDispensers.get(sc.banknoteDenominations[0]).size(), sc.banknoteDispensers.get(sc.banknoteDenominations[0]).getCapacity());
		Assert.assertEquals(sc.banknoteDispensers.get(sc.banknoteDenominations[1]).size(), sc.banknoteDispensers.get(sc.banknoteDenominations[1]).getCapacity());
		Assert.assertEquals(sc.banknoteDispensers.get(sc.banknoteDenominations[2]).size(), sc.banknoteDispensers.get(sc.banknoteDenominations[2]).getCapacity());
		Assert.assertEquals(sc.banknoteDispensers.get(sc.banknoteDenominations[3]).size(), sc.banknoteDispensers.get(sc.banknoteDenominations[3]).getCapacity());
		Assert.assertEquals(sc.banknoteDispensers.get(sc.banknoteDenominations[4]).size(), sc.banknoteDispensers.get(sc.banknoteDenominations[4]).getCapacity());
		Assert.assertEquals(sc.banknoteDispensers.get(sc.banknoteDenominations[5]).size(), sc.banknoteDispensers.get(sc.banknoteDenominations[5]).getCapacity());

	}
	
	@Test
	public void emptyCoinStorageUnitTest() throws SimulationException, OverloadException, ValidationException
	{
		Assert.assertEquals(0, sc.coinStorage.getCoinCount());
		Random randomNumber = new Random();
		for (int i = 0; i < sc.coinStorage.getCapacity(); i++) {
			int random = randomNumber.nextInt(5);
			sc.coinStorage.load(coinArray[random]);
		}
		
		Assert.assertEquals(sc.coinStorage.getCapacity(), sc.coinStorage.getCoinCount());
		
		attendantLogic.emptyCoinStorageUnit(sc);
		
		Assert.assertEquals(0, sc.coinStorage.getCoinCount());
	}
	
	@Test
	public void emptyBanknoteStorageUnitTest() throws SimulationException, OverloadException, ValidationException
	{
		
		attendantLogic.emptyBanknoteStorageUnit(sc);
		
		Assert.assertEquals(0, sc.banknoteStorage.getBanknoteCount());
	}
	
	@Test
	public void notLoggedInEmptyBanknoteStorageUnitTest() throws SimulationException, OverloadException, ValidationException
	{
		Assert.assertEquals(0, sc.banknoteStorage.getBanknoteCount());
		Random randomNumber = new Random();
		for (int i = 0; i < sc.banknoteStorage.getCapacity(); i++) {
			int random = randomNumber.nextInt(5);
			sc.banknoteStorage.load(banknoteArray[random]);
		}
		
		AttendantLogic.wantsToLogout();
		
		try {
			attendantLogic.emptyBanknoteStorageUnit(sc);
			fail();
		}
		catch (ValidationException e)
		{
			Assert.assertTrue(e instanceof ValidationException);
		}
		
	}

	@Test
	public void notLoggedInEmptyCoinStorageUnitTest() throws SimulationException, OverloadException, ValidationException
	{
		Assert.assertEquals(0, sc.coinStorage.getCoinCount());
		Random randomNumber = new Random();
		for (int i = 0; i < sc.coinStorage.getCapacity(); i++) {
			int random = randomNumber.nextInt(5);
			sc.coinStorage.load(coinArray[random]);
		}
		

		AttendantLogic.wantsToLogout();
		
		try {
			attendantLogic.emptyCoinStorageUnit(sc);
			fail();
		}
		catch (ValidationException e)
		{
			Assert.assertTrue(e instanceof ValidationException);
		}
	}
	@Test
	public void getSCSLogicTest()
	{
	boolean notNull = false;
		for (int i = 1; i <= 6; i++) {
			attendantLogic.getSCSLogic(i);
			notNull = true;
		}
		Assert.assertTrue(notNull);
	}
	
	
	@Test
	public void attendantLogout()
	{
		AttendantLogic.wantsToLogout();
		
		Assert.assertFalse(AttendantLogic.loggedIn);
	}
	
	@Test
	public void enableTest()
	{
		attendantLogic.enabled(null);
		Assert.assertTrue(attendantLogic.enabledTrue);
	}
	
	@Test
	public void disableTest()
	{
		attendantLogic.disabled(null);
		Assert.assertTrue(attendantLogic.disabledTrue);
	}
	
	@Test
	public void refillInkandPaperTest() throws OverloadException
	{
		attendantLogic.attendantAddInk(sl);
		Assert.assertTrue(!sc.printer.isDisabled());
		//sc.printer.addInk(ReceiptPrinter.MAXIMUM_INK);
		sc.printer.addPaper(ReceiptPrinter.MAXIMUM_PAPER);
		attendantLogic.attendantAddPaper(sl);
		Assert.assertFalse(sc.printer.isDisabled());
	}
}
