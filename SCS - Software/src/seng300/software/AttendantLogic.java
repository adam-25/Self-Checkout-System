package seng300.software;

import java.math.BigDecimal;
import java.util.Currency;

import org.lsmr.selfcheckout.Coin;
import org.lsmr.selfcheckout.SimulationException;
import org.lsmr.selfcheckout.devices.OverloadException;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.SupervisionStation;

public class AttendantLogic {

	private SupervisionStation ss;
	private boolean loggedIn;
	
	public AttendantLogic(SupervisionStation supervisionStation)
	{
		this.ss = supervisionStation;
	}
	
	public void emptyCoinStorageUnit(SelfCheckoutStation sc)
	{
		
	}
	
	public void emptyBanknoteStorageUnit(SelfCheckoutStation sc)
	{
		
	}
	
	public void refillsCoinDispenser(SelfCheckoutStation sc) throws SimulationException, OverloadException
	{
	
	if (loggedIn) {
		for (int i = 0; i <= sc.coinDenominations.size(); i++) {
			
		int loadedCoins = sc.coinDispensers.get(sc.coinDenominations.get(i)).size();
		int dispenserCapacity = sc.coinDispensers.get(sc.coinDenominations.get(i)).getCapacity();
		int coinsToAdd = dispenserCapacity - loadedCoins;
			
		for (int j = 0; j <= coinsToAdd; i++) {	
			sc.coinDispensers.get(sc.coinDenominations.get(i)).load(new Coin(Currency.getInstance("CAD"), sc.coinDenominations.get(i)));
			}
		}
	}
	}
	
	public void refillsBanknoteDispenser(SelfCheckoutStation sc)
	{
		
	}
	//MAKE LOGIN METHOD
	
}
