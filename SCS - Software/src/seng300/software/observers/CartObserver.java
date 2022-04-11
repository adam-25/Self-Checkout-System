package seng300.software.observers;

import org.lsmr.selfcheckout.products.BarcodedProduct;
import org.lsmr.selfcheckout.products.PLUCodedProduct;

import seng300.software.Cart;
import seng300.software.PLUCodedWeightProduct;

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
		if (this.baggingAreaObserver.isProductBagged(p)){
			this.baggingAreaObserver.notifiedItemRemoved(p);
		}
	}
	
	public void notifyPLUProductRemoved(Cart cart, PLUCodedWeightProduct p)
	{
		if (this.baggingAreaObserver.isProductBagged(p)){
			this.baggingAreaObserver.notifiedItemRemoved(p);
		}
		

	}
	
	public void notifyProductNoWeightCheck(){
		this.baggingAreaObserver.noWeightCheck();
	}

}
