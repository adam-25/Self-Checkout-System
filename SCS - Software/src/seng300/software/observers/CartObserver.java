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
}
