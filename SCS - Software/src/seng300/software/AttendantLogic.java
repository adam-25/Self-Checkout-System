package seng300.software;

import java.util.HashMap;
import java.util.List;

import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.SupervisionStation;
import org.lsmr.selfcheckout.external.ProductDatabases;

public class AttendantLogic {

	private SupervisionStation ss;
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
		
	}
	
	//this method could end up being a button observer
	public void shutDownStation(SelfCheckoutStation sc)
	{
		
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
	
	
	
	
	
}
