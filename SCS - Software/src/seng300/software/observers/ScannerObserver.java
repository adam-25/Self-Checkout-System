package seng300.software.observers;

import org.lsmr.selfcheckout.Barcode;
import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.BarcodeScanner;
import org.lsmr.selfcheckout.devices.observers.AbstractDeviceObserver;
import org.lsmr.selfcheckout.devices.observers.BarcodeScannerObserver;

import seng300.software.Cart;
import seng300.software.exceptions.ProductNotFoundException;

public class ScannerObserver implements BarcodeScannerObserver
{
	private Cart cart;
	
	private boolean isWeightChecking = true;
	
	/**
	 * Default constructor
	 * 
	 * @param cart
	 * 			(Digital representation of) customer's checkout cart
	 * 			that the observer should add scanned items to.
	 */
	public ScannerObserver(Cart cart)
	{
		this.cart = cart;
	}

	@Override
	public void enabled(AbstractDevice<? extends AbstractDeviceObserver> device)
	{
		System.out.println("Scanner has been enabled");
	}

	@Override
	public void disabled(AbstractDevice<? extends AbstractDeviceObserver> device)
	{
		System.out.println("Scanner has been disabled");
	}

	/**
	 * Validates the scanned barcode by ensuring it is mapped 
	 * to a product in the database. If valid, the corresponding product is 
	 * added to the cart and the bagging area is notified. Else, the 
	 * barcode scanner is disabled and the system is blocked.
	 * 
	 * @param barcodeScanner
	 * 			BarcodeScanner device that scanned the item.
	 * 
	 * @param barcode
	 * 			Barcode that was scanned.
	 */
	@Override
	public void barcodeScanned(BarcodeScanner barcodeScanner, Barcode barcode)
	{
		try
		{
			if (this.isWeightChecking) {
				this.cart.addToCart(barcode); // add barcoded product to cart		
			}
			else {
				this.cart.addToCartNoWeight(barcode);
			}
				
		}
		catch (ProductNotFoundException e)
		{
			// Product does not exist; disable scanner
			barcodeScanner.disable();
			// notify attendant?
		}
	}

	public boolean isWeightChecking() {
		return isWeightChecking;
	}

	public void setWeightChecking(boolean isWeightChecking) {
		this.isWeightChecking = isWeightChecking;
	}

}
