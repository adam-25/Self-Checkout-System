package seng300.software;

import java.math.BigDecimal;
import java.util.Currency;

import org.lsmr.selfcheckout.Banknote;
import org.lsmr.selfcheckout.Coin;
import org.lsmr.selfcheckout.SimulationException;
import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.Keyboard;
import org.lsmr.selfcheckout.devices.OverloadException;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.SupervisionStation;
import org.lsmr.selfcheckout.devices.observers.AbstractDeviceObserver;
import org.lsmr.selfcheckout.devices.observers.KeyboardObserver;

public class AttendantLogic implements KeyboardObserver {

	private SupervisionStation ss;
	private static boolean loggedIn;
	private static String inputtedPassword;
	private static String attendantCode;
	
	public AttendantLogic(SupervisionStation supervisionStation)
	{
		this.ss = supervisionStation;
		loggedIn = false;
		inputtedPassword = "";
		attendantCode = "12345678";
	}
	
	public void emptyCoinStorageUnit(SelfCheckoutStation sc)
	{
		
	}
	
	public void emptyBanknoteStorageUnit(SelfCheckoutStation sc)
	{
		
	}
	
	//Refills each coin dispenser of each coin denomination to its maximum capacity.
	public void refillsCoinDispenser(SelfCheckoutStation sc) throws SimulationException, OverloadException
	{
	
	if (loggedIn) {
		for (int i = 0; i <= sc.coinDenominations.size(); i++) {
			
			int loadedCoins = sc.coinDispensers.get(sc.coinDenominations.get(i)).size();
			int dispenserCapacity = sc.coinDispensers.get(sc.coinDenominations.get(i)).getCapacity();
			int coinsToAdd = dispenserCapacity - loadedCoins;
			
			for (int j = 0; j <= coinsToAdd; j++) {	
				sc.coinDispensers.get(sc.coinDenominations.get(j)).load(new Coin(Currency.getInstance("CAD"), sc.coinDenominations.get(j)));
			}
		}
	}
	}
	
	//Refills each banknote dispenser of each banknote denomination to its maximum capacity.
	public void refillsBanknoteDispenser(SelfCheckoutStation sc) throws OverloadException
	{
		if (loggedIn) {
			for (int i = 0; i <= sc.banknoteDenominations.length; i++) {
				
				int loadedBanknotes = sc.banknoteDispensers.get(sc.banknoteDenominations[i]).size();
				int dispenserCapacity = sc.banknoteDispensers.get(sc.banknoteDenominations[i]).getCapacity();
				int banknotesToAdd = dispenserCapacity - loadedBanknotes;
				
				for (int j = 0; j <= banknotesToAdd; j++) {
					sc.banknoteDispensers.get(sc.banknoteDenominations[j]).load(new Banknote(Currency.getInstance("CAD"), sc.banknoteDenominations[j]));
				}
			}
		}
	}
	@Override
	public void enabled(AbstractDevice<? extends AbstractDeviceObserver> device) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disabled(AbstractDevice<? extends AbstractDeviceObserver> device) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(Keyboard k, char c) {
		// TODO Auto-generated method stub
		inputtedPassword += c;
	}
	
	public static boolean wantsToLogin() {
		if(attendantCode.equals(inputtedPassword)) {
			loggedIn = true;
		}
		inputtedPassword = "";
		return loggedIn;
	}
	
	public static void wantsToLogout() {
		loggedIn = false;
	}
}
