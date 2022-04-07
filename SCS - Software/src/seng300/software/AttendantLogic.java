package seng300.software;

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

import seng300.software.exceptions.ProductNotFoundException;

public class AttendantLogic {

	private SupervisionStation ss;
	
	//retrieves the selfcheckoutSystemLogic to a selfCheckoutStation
	HashMap<SelfCheckoutStation, SelfCheckoutSystemLogic> enterNameHere = new HashMap<SelfCheckoutStation, SelfCheckoutSystemLogic>();
	List<SelfCheckoutStation> StationNames = ss.supervisedStations();
	
	public AttendantLogic(SupervisionStation supervisionStation)
	{
		this.ss = supervisionStation;
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
