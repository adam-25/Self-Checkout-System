package seng300.testing;

import java.math.BigDecimal;
import java.util.Currency;

import org.junit.Before;
import org.junit.Test;
import org.lsmr.selfcheckout.SimulationException;
import org.lsmr.selfcheckout.devices.OverloadException;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.SupervisionStation;

import org.junit.Assert;
import seng300.software.AttendantLogic;

public class AttendantLogicTests {

	private SelfCheckoutStation sc;
	private Currency currency;
	private int[] bankNoteDenominations = {1, 5, 10, 20, 50, 100};
	
	BigDecimal coin1 = new BigDecimal("0.05");
	BigDecimal coin2 = new BigDecimal("0.10");
	BigDecimal coin3 = new BigDecimal("0.25");
	BigDecimal coin4 = new BigDecimal("1.00");
	BigDecimal coin5 = new BigDecimal("2.00");
	
	private BigDecimal[] coinDenominations = {coin1, coin2, coin3, coin4, coin5};
	
	private int scaleMaxWeight = 15;
	private int scaleSensitivity = 3;
	private SupervisionStation attendantStation;
	private AttendantLogic attendantLogic;
	
	@Before
	public void setUp()
	{
		attendantStation = new SupervisionStation();
		currency = Currency.getInstance("CAD");
		sc = new SelfCheckoutStation(currency, bankNoteDenominations, coinDenominations, scaleMaxWeight, scaleSensitivity);
		attendantStation.add(sc);
		attendantLogic = new AttendantLogic(attendantStation);
	}
	
	@Test
	public void refillCoinDispenserTest() throws SimulationException, OverloadException
	{
		Assert.assertEquals(sc.coinDispensers.get(sc.coinDenominations.get(0)).size(), 0);
		
//		attendantStation.keyboard.type("12345678");
		
		AttendantLogic.wantsToLogin();
		
		attendantLogic.refillsCoinDispenser(sc);
		
		Assert.assertEquals(sc.coinDispensers.get(sc.coinDenominations.get(0)).size(), sc.coinDispensers.get(sc.coinDenominations.get(0)).getCapacity());
	}
	
	@Test
	public void refillBanknoteDispenserTest() throws SimulationException, OverloadException
	{
		Assert.assertEquals(sc.banknoteDispensers.get(sc.banknoteDenominations[0]).size(), 0);
		
//		attendantStation.keyboard.type("12345678");
		
		AttendantLogic.wantsToLogin();
		
		attendantLogic.refillsBanknoteDispenser(sc);
		
		Assert.assertEquals(sc.banknoteDispensers.get(sc.banknoteDenominations[0]).size(), sc.banknoteDispensers.get(sc.banknoteDenominations[0]).getCapacity());
	}

}
