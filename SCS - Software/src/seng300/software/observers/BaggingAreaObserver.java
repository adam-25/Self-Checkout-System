package seng300.software.observers;

import java.util.ArrayList;
import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.ElectronicScale;
import org.lsmr.selfcheckout.devices.observers.AbstractDeviceObserver;
import org.lsmr.selfcheckout.devices.observers.ElectronicScaleObserver;
import org.lsmr.selfcheckout.products.BarcodedProduct;
import org.lsmr.selfcheckout.products.PLUCodedProduct;
import org.lsmr.selfcheckout.products.Product;

import seng300.software.Cart;
import seng300.software.SelfCheckoutSystemLogic;

public class BaggingAreaObserver implements ElectronicScaleObserver
{
	private SelfCheckoutSystemLogic logic;
	private Cart currentCart = new Cart();
	private double weightAtLastEvent;
	private boolean currentItemBagged = true;
	
	private Thread checkProductBagggedby5Thread;
	private Product currentScannedProduct;
	private ArrayList<Product> scannedProducts = new ArrayList<>();
	private ArrayList<Product> baggedProducts = new ArrayList<>();
	private boolean timedOut = false;
	
	
	public boolean isTimedOut() {
		return timedOut;
	}

	public void setTimedOut(boolean timedOut) {
		this.timedOut = timedOut;
	}

	public ArrayList<Product> getScannedProducts() {
		return scannedProducts;
	}

	public ArrayList<Product> getBaggedProducts() {
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
				
				double currentItemWeight;
				
				if (currentScannedProduct instanceof BarcodedProduct)
				{
				    currentItemWeight = ((BarcodedProduct)currentScannedProduct).getExpectedWeight();
				}
				else // p instanceof PLUCodedProduct
				{
				    currentItemWeight = currentCart.getPLUWeight(); // Expected weight is the same as the weight on electronic scale
				}
				
				double difference =  Math.abs(currentItemWeight - itemWeight);
				
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
	
	public void notifiedPLUCodedItemAdded(PLUCodedProduct scannedPLUProduct, double Weight)
	{

		// wait 5 seconds -- Threads
		// if not notified weight change, block system
					
		if (checkProductBagggedby5Thread != null && checkProductBagggedby5Thread.isAlive()) {
			checkProductBagggedby5Thread.interrupt();
		}

		if( Weight > logic.getBaggingAreaSensitivity()) {
			// disable scanners until item placed in bagging area
			logic.station.mainScanner.disable();
			logic.station.handheldScanner.disable();
			
			currentScannedProduct = scannedPLUProduct;
			scannedProducts.add(scannedPLUProduct);
			currentItemBagged = false;
			
			Runnable  checkProductBaggged = new CheckBaggedProduct(scannedPLUProduct, this);
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
