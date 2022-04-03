package seng300.software;

import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.SupervisionStation;

public class AttendantLogic {

	private SupervisionStation ss;
	private boolean loginAttendant = false;
	
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
	
	public void refillsCoinDispenser(SelfCheckoutStation sc)
	{
		
	}
	
	public void refillsBanknoteDispenser(SelfCheckoutStation sc)
	{
		
	}
	
	public void loginAttendant()
	{
		
	}
	
	public void logoutAttendant()
	{
		
	}
	
	public void attendantApproval(SelfCheckoutSystemLogic sc)
	{
		sc.unblock();
	}
	
	public boolean getAttendantLogin()
	{
		return loginAttendant;
	}
}
