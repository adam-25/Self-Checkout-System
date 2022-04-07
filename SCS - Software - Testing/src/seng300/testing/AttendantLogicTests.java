package seng300.testing;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.lsmr.selfcheckout.Banknote;
import org.lsmr.selfcheckout.Coin;
import org.lsmr.selfcheckout.SimulationException;
import org.lsmr.selfcheckout.devices.OverloadException;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.SupervisionStation;

import org.junit.Assert;
import seng300.software.AttendantLogic;
import seng300.software.exceptions.ValidationException;

public class AttendantLogicTests {

	private SelfCheckoutStation sc;
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

	private int[] bankNoteDenominations = {note1.getValue(), note2.getValue(), note3.getValue(), note4.getValue(), note5.getValue(), note6.getValue()};
	private Banknote[] banknoteArray = {note1, note2, note3, note4, note5, note6};
	
	private BigDecimal[] coinDenominations = {nickle.getValue(), dime.getValue(), quarter.getValue(), loonie.getValue(), twoonie.getValue()};
	private Coin[] coinArray = {nickle, dime, quarter, loonie, twoonie};
	
	private int scaleMaxWeight = 15;
	private int scaleSensitivity = 3;
	private SupervisionStation attendantStation;
	private AttendantLogic attendantLogic;
	
	@Before
	public void setUp()
	{
		attendantStation = new SupervisionStation();
		sc = new SelfCheckoutStation(currency, bankNoteDenominations, coinDenominations, scaleMaxWeight, scaleSensitivity);
		attendantStation.add(sc);
		attendantLogic = AttendantLogic.getInstance();
		attendantStation.keyboard.attach(attendantLogic);
	}
	
	@Test
	public void refillCoinDispenserTest() throws SimulationException, OverloadException
	{
		Assert.assertEquals(sc.coinDispensers.get(sc.coinDenominations.get(0)).size(), 0);
		Assert.assertEquals(sc.coinDispensers.get(sc.coinDenominations.get(1)).size(), 0);
		Assert.assertEquals(sc.coinDispensers.get(sc.coinDenominations.get(2)).size(), 0);
		Assert.assertEquals(sc.coinDispensers.get(sc.coinDenominations.get(3)).size(), 0);
		Assert.assertEquals(sc.coinDispensers.get(sc.coinDenominations.get(4)).size(), 0);
		
		attendantStation.keyboard.type("87654321");
		attendantStation.keyboard.type("12345678");
		
		AttendantLogic.wantsToLogin();
		
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
		
		attendantStation.keyboard.type("87654321");
		attendantStation.keyboard.type("12345678");
		
		AttendantLogic.wantsToLogin();
		
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
		
		attendantStation.keyboard.type("87654321");
		attendantStation.keyboard.type("12345678");
		
		AttendantLogic.wantsToLogin();
		
		attendantLogic.emptyCoinStorageUnit(sc);
		
		Assert.assertEquals(0, sc.coinStorage.getCoinCount());
	}
	
	@Test
	public void emptyBanknoteStorageUnitTest() throws SimulationException, OverloadException, ValidationException
	{
		Assert.assertEquals(0, sc.banknoteStorage.getBanknoteCount());
		Random randomNumber = new Random();
		for (int i = 0; i < sc.banknoteStorage.getCapacity(); i++) {
			int random = randomNumber.nextInt(5);
			sc.banknoteStorage.load(banknoteArray[random]);
		}
		
		Assert.assertEquals(sc.banknoteStorage.getCapacity(), sc.banknoteStorage.getBanknoteCount());
		
		attendantStation.keyboard.type("87654321");
		attendantStation.keyboard.type("12345678");
		
		AttendantLogic.wantsToLogin();
		
		attendantLogic.emptyBanknoteStorageUnit(sc);
		
		Assert.assertEquals(0, sc.banknoteStorage.getBanknoteCount());
	}
	
	@Test (expected = ValidationException.class)
	public void notLoggedInEmptyBanknoteStorageUnitTest() throws SimulationException, OverloadException, ValidationException
	{
		Assert.assertEquals(0, sc.banknoteStorage.getBanknoteCount());
		Random randomNumber = new Random();
		for (int i = 0; i < sc.banknoteStorage.getCapacity(); i++) {
			int random = randomNumber.nextInt(5);
			sc.banknoteStorage.load(banknoteArray[random]);
		}
		
		Assert.assertEquals(sc.banknoteStorage.getCapacity(), sc.banknoteStorage.getBanknoteCount());
			
		attendantLogic.emptyBanknoteStorageUnit(sc);	
	}

	@Test (expected = ValidationException.class)
	public void notLoggedInEmptyCoinStorageUnitTest() throws SimulationException, OverloadException, ValidationException
	{
		Assert.assertEquals(0, sc.coinStorage.getCoinCount());
		Random randomNumber = new Random();
		for (int i = 0; i < sc.coinStorage.getCapacity(); i++) {
			int random = randomNumber.nextInt(5);
			sc.coinStorage.load(coinArray[random]);
		}
		
		Assert.assertEquals(sc.coinStorage.getCapacity(), sc.coinStorage.getCoinCount());
			
		attendantLogic.emptyCoinStorageUnit(sc);	
	}
	
	@Test
	public void attendantLogout()
	{
		AttendantLogic.wantsToLogout();
		
		Assert.assertFalse(AttendantLogic.loggedIn);
	}
	
	@Test
	public void attendantLogIn()
	{
		attendantStation.keyboard.type("87654321");
		attendantStation.keyboard.type("12345678");
		
		AttendantLogic.wantsToLogin();
		
		Assert.assertTrue(AttendantLogic.loggedIn);
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
}
