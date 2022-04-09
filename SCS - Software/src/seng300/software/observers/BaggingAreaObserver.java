package seng300.software.observers;

import java.util.ArrayList;
import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.ElectronicScale;
import org.lsmr.selfcheckout.devices.observers.AbstractDeviceObserver;
import org.lsmr.selfcheckout.devices.observers.ElectronicScaleObserver;
import org.lsmr.selfcheckout.products.BarcodedProduct;

import seng300.software.SelfCheckoutSystemLogic;

public class BaggingAreaObserver implements ElectronicScaleObserver
{
	private SelfCheckoutSystemLogic logic;
	private double weightAtLastEvent;
	private boolean currentItemBagged = true;
	
	private Thread checkProductBagggedby5Thread;
	private BarcodedProduct currentScannedProduct;
	private ArrayList<BarcodedProduct> scannedProducts = new ArrayList<>();
	private ArrayList<BarcodedProduct> baggedProducts = new ArrayList<>();
	private boolean timedOut = false;
	
	
	public boolean isTimedOut() {
		return timedOut;
	}

	public void setTimedOut(boolean timedOut) {
		this.timedOut = timedOut;
	}

	public ArrayList<BarcodedProduct> getScannedProducts() {
		return scannedProducts;
	}

	public ArrayList<BarcodedProduct> getBaggedProducts() {
		return baggedProducts;
	}

	public BaggingAreaObserver(SelfCheckoutSystemLogic logic)
	{
		this.logic = logic;
		weightAtLastEvent = 0;
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
	public void weightChanged(ElectronicScale scale, double weightInGrams) {
		
		if(weightAtLastEvent < weightInGrams)	
		{
			if(currentItemBagged == true) {
				// there is no scanned item waiting to be bagged so
				blockScs();	
			}else {
				double itemWeight = (weightInGrams - weightAtLastEvent );
				
				weightAtLastEvent = weightInGrams;
				
				double difference =  Math.abs(currentScannedProduct.getExpectedWeight() - itemWeight);
				
				//double sensitivity = scale.getSensitivity();
				
				if (difference < 1E-10)  {
					
					baggedProducts.add(currentScannedProduct);
					currentItemBagged = true;
					
					if(weightAtLastEvent <= scale.getWeightLimit()) {
						// if scale is not overloaded enable scanners again 
						logic.station.mainScanner.enable();
						logic.station.handheldScanner.enable();
					}
					
				}else {
					// unknown item placed in bagging area
					blockScs();

				}
			}	
			
		}
		else if (weightAtLastEvent > weightInGrams) {
			
			// item has been removed from bagging area
			blockScs();
		} 
		

		
	}

	@Override
	public void overload(ElectronicScale scale) {
		// weight on scale has exceeded limit
		blockScs();

	}

	@Override
	public void outOfOverload(ElectronicScale scale) {
		// TODO Auto-generated method stub

	}
	
	public void notifiedItemAdded(BarcodedProduct scannedProduct)
	{

		// wait 5 seconds -- Threads
		// if not notified weight change, block system
					
		if (checkProductBagggedby5Thread != null && checkProductBagggedby5Thread.isAlive()) {
			checkProductBagggedby5Thread.interrupt();
		}

		if(scannedProduct.getExpectedWeight() > logic.getBaggingAreaSensitivity()) {
			// disable scanners until item placed in bagging area
			logic.station.mainScanner.disable();
			logic.station.handheldScanner.disable();
			
			currentScannedProduct = scannedProduct;
			scannedProducts.add(scannedProduct);
			currentItemBagged = false;
			
			Runnable  checkProductBaggged = new CheckBaggedProduct(scannedProduct, this);
			checkProductBagggedby5Thread = new Thread(checkProductBaggged);
			checkProductBagggedby5Thread.setDaemon(true);
			checkProductBagggedby5Thread.start();	
			
			
		}else {				
			// if the item weighs less than the scale's sensitivity, it is ignored
			// does not need to be placed in the bagging area
		}		
		
	}
	
	public boolean isCurrentItemBagged() {
		return currentItemBagged;
	}

	public void blockScs() {
		logic.block();
		
	}
}
