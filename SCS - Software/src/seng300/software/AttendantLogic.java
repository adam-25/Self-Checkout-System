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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.lsmr.selfcheckout.BarcodedItem;
import org.lsmr.selfcheckout.Item;
import org.lsmr.selfcheckout.PLUCodedItem;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.SupervisionStation;
import org.lsmr.selfcheckout.external.ProductDatabases;
import org.lsmr.selfcheckout.products.BarcodedProduct;

import seng300.software.exceptions.ValidationException;

import seng300.software.exceptions.ProductNotFoundException;

public class AttendantLogic implements KeyboardObserver {

	public static boolean loggedIn;
	private static String userInput;
	private static String inputtedID;
	private static String inputtedPassword;
	private static String attendantPassword;
	private static String attendantID;
	
	public static boolean isIDEntered = false;
	public boolean enabledTrue = false;
	public boolean disabledTrue = false;
  
  private SupervisionStation ss;
  
  
  //retrieves the selfcheckoutSystemLogic to a selfCheckoutStation
	HashMap<SelfCheckoutStation, SelfCheckoutSystemLogic> enterNameHere = new HashMap<SelfCheckoutStation, SelfCheckoutSystemLogic>();
	List<SelfCheckoutStation> StationNames = ss.supervisedStations();
  
  
	
	public AttendantLogic(SupervisionStation supervisionStation)
	{
		this.ss = supervisionStation;

		loggedIn = false;
		userInput = "";
		attendantPassword = "12345678";
		attendantID = "87654321";
	}
	
	// Removes all coins from the CoinStorageUnit
	public void emptyCoinStorageUnit(SelfCheckoutStation sc) throws ValidationException
	{
		if(loggedIn && ss.supervisedStations().contains(sc)) {
			sc.coinStorage.unload();
		} else {
			throw new ValidationException();
		}
	}
	
	// Removes all banknotes from the BanknoteStorageUnit
	public void emptyBanknoteStorageUnit(SelfCheckoutStation sc) throws ValidationException
	{
		if(loggedIn && ss.supervisedStations().contains(sc)) {
			sc.banknoteStorage.unload();
		} else {
			throw new ValidationException();
		}
	}
	
	//Refills each coin dispenser of each coin denomination to its maximum capacity.
	public void refillsCoinDispenser(SelfCheckoutStation sc) throws SimulationException, OverloadException
	{
		if (loggedIn && ss.supervisedStations().contains(sc)) {
			// Iterates over different denominations in the hashmap; 1 dispenser per denomination.
			for (int i = 0; i < sc.coinDenominations.size(); i++) {
				
				int loadedCoins = sc.coinDispensers.get(sc.coinDenominations.get(i)).size();
				int dispenserCapacity = sc.coinDispensers.get(sc.coinDenominations.get(i)).getCapacity();
				int coinsToAdd = dispenserCapacity - loadedCoins;
				// Adds coins 1 by 1 in the software, until the maximum capacity is reached.
				for (int j = 0; j < coinsToAdd; j++) {	
					sc.coinDispensers.get(sc.coinDenominations.get(i)).load(new Coin(Currency.getInstance("CAD"), sc.coinDenominations.get(i)));
				}
			}
		}
	}
	
	//Refills each banknote dispenser of each banknote denomination to its maximum capacity.
	public void refillsBanknoteDispenser(SelfCheckoutStation sc) throws OverloadException
	{
		if (loggedIn && ss.supervisedStations().contains(sc)) {
			// Iterates over different denominations in the hashmap; 1 dispenser per denomination.
			for (int i = 0; i < sc.banknoteDenominations.length; i++) {
				
				int loadedBanknotes = sc.banknoteDispensers.get(sc.banknoteDenominations[i]).size();
				int dispenserCapacity = sc.banknoteDispensers.get(sc.banknoteDenominations[i]).getCapacity();
				int banknotesToAdd = dispenserCapacity - loadedBanknotes;
				// Adds coins 1 by 1 in the software, until the maximum capacity is reached.
				for (int j = 0; j < banknotesToAdd; j++) {
					sc.banknoteDispensers.get(sc.banknoteDenominations[i]).load(new Banknote(Currency.getInstance("CAD"), sc.banknoteDenominations[i]));
				}
			}
		}
	}
	@Override
	public void enabled(AbstractDevice<? extends AbstractDeviceObserver> device) {
		enabledTrue = true;
		
	}

	@Override
	public void disabled(AbstractDevice<? extends AbstractDeviceObserver> device) {
		disabledTrue = true;
		
	}

	@Override
	public void keyPressed(Keyboard k, char c) {
		// TODO Auto-generated method stub
		userInput += c;
		
		if (userInput.length() == attendantID.length() && isIDEntered == false) {
			inputtedID = userInput;
			userInput = "";
			isIDEntered = true;
		} 
	}
	
	// Limitation: Attendant have to insert his attendantID first, only then he can enter his password.
	public static void wantsToLogin() {
		inputtedPassword = userInput;
		userInput = "";
		if(attendantPassword.equals(inputtedPassword) && attendantID.equals(inputtedID)) {
			loggedIn = true;
		}
		inputtedPassword = "";
		inputtedID = "";
		isIDEntered = false;
	}
	
	public static void wantsToLogout() {
		loggedIn = false;
	}

		SelfCheckoutStation sc = null;	
		ProductDatabases pd;
	}
	
	public void emptyCoinStorageUnit(SelfCheckoutStation sc)
	{
		
	}
	
	public void emptyBanknoteStorageUnit(SelfCheckoutStation sc)
	{
		
	}
	
	public void refillsCoinDispenser(SelfCheckoutStation sc)
	{
		
	}
	
	public void refillsBanknoteDispenser(SelfCheckoutStation sc)
	{
		
	}
	
	//this method could end up being a button observer
	public void attedndantBlock(SelfCheckoutStation sc)
	{
		SelfCheckoutSystemLogic s = enterNameHere.get(sc);
		s.block();
	}
	
	//this method could end up being a button observer
	public void startUpStation(SelfCheckoutStation sc)
	{
		SelfCheckoutSystemLogic s = enterNameHere.get(sc);
		s.turnOnStation();
	}
	
	//this method could end up being a button observer
	public void shutDownStation(SelfCheckoutStation sc)
	{
		SelfCheckoutSystemLogic s = enterNameHere.get(sc);
		s.turnOffStation();
	}
	
	public void attendantAddInk(SelfCheckoutStation sc)
	{
		SelfCheckoutSystemLogic s = enterNameHere.get(sc);
		s.block();
		sc.printer.disable();
		
		//attendant physically adds ink
		
	}
	
	public void attendantAddPaper(SelfCheckoutStation sc)
	{
		SelfCheckoutSystemLogic s = enterNameHere.get(sc);
		s.block();
		sc.printer.disable();
		
		//attendant physically adds paper

	}
	
	/**
	/**
	 * Attendant removes purchased items from bagging area.
	 */
	public void AttendantRemovePurchasedItem(BarcodedProduct x, SelfCheckoutStation sc) {
	
		SelfCheckoutSystemLogic s = enterNameHere.get(sc);
		try {
			s.cart.removeFromCart(x);
		} catch (ProductNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("product was not found!"); //this should be implemented in the GUI
		}
		
		
//		for (BarcodedItem item : this.baggingAreaItems)
//			removeItemBaggingArea(item);
//		for (PLUCodedItem item : this.baggingAreaPluItems)
//			removePluItemBaggingArea(item);
//		return true;
	}

	
	
	
	
	

}
