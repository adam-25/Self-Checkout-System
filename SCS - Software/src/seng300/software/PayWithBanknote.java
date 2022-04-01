package seng300.software;
import java.math.BigDecimal;
import java.util.Currency;

import org.lsmr.selfcheckout.Banknote;
import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.BanknoteDispenser;
import org.lsmr.selfcheckout.devices.BanknoteStorageUnit;
import org.lsmr.selfcheckout.devices.BanknoteValidator;
import org.lsmr.selfcheckout.devices.DisabledException;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.observers.AbstractDeviceObserver;
import org.lsmr.selfcheckout.devices.observers.BanknoteDispenserObserver;
import org.lsmr.selfcheckout.devices.observers.BanknoteStorageUnitObserver;
import org.lsmr.selfcheckout.devices.observers.BanknoteValidatorObserver;

//code base from alexanna's IT1 group 11
//updated by alexanna

public class PayWithBanknote implements BanknoteValidatorObserver {
	
	private BigDecimal totalAmountPaid = new BigDecimal(0.0); 
	
	SelfCheckoutStation scs;

	public PayWithBanknote(SelfCheckoutStation scs) {
		this.scs = scs;
		scs.banknoteValidator.attach(this);
	}
	
	
	public BigDecimal amountPaid() {
		return totalAmountPaid;
	}
	

	@Override
	public void validBanknoteDetected(BanknoteValidator validator, Currency currency, int value) {
		
		if(scs.banknoteStorage.hasSpace()) {

			// update the total amount paid with the value of the banknote
			totalAmountPaid = totalAmountPaid.add(BigDecimal.valueOf(value));		 
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
	public void invalidBanknoteDetected(BanknoteValidator validator) {
		// TODO Auto-generated method stub
		
	}

}
