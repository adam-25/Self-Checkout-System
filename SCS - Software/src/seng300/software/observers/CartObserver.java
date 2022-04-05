package seng300.software.observers;

import org.lsmr.selfcheckout.products.BarcodedProduct;
import org.lsmr.selfcheckout.products.PLUCodedProduct;

import seng300.software.Cart;

public class CartObserver
{
	private BaggingAreaObserver baggingAreaObserver;
	
	public CartObserver(BaggingAreaObserver bao)
	{
		this.baggingAreaObserver = bao;
	}
	
	public void notifyProductAdded(Cart cart, BarcodedProduct p)
	{
		this.baggingAreaObserver.notifiedItemAdded(p);
	}
	
	public void notifyPLUProductAdded(Cart cart, PLUCodedProduct PLUProduct)
	{
		this.baggingAreaObserver.notifiedItemAdded(PLUProduct);
	}
}
