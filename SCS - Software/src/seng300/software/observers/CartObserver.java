package seng300.software.observers;

import org.lsmr.selfcheckout.products.BarcodedProduct;

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
	

	public void notifyPLUProductAdded(Cart cart, PLUCodedProduct PLUProduct, double Weight)
	{
		this.baggingAreaObserver.notifiedPLUCodedItemAdded(PLUProduct, Weight);
	}

	public void notifyProductRemoved(Cart cart, BarcodedProduct p)
	{
		this.baggingAreaObserver.notifiedItemRemoved(p);

	}
}
