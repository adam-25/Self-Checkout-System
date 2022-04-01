package seng300.software;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.lsmr.selfcheckout.Barcode;
import org.lsmr.selfcheckout.devices.SimulationException;
import org.lsmr.selfcheckout.products.BarcodedProduct;

import seng300.software.exceptions.ProductNotFoundException;
import seng300.software.observers.CartObserver;

public class Cart
{
	private ProductDatabase productDatabase;
	private List<BarcodedProduct> cart;
	private BigDecimal cartTotal;
	private List<CartObserver> observers;
	
	public Cart(ProductDatabase productDatabase)
	{
		this.productDatabase = productDatabase;
		this.cart = new ArrayList<>();
		this.cartTotal = new BigDecimal("0.00");
		this.observers = new ArrayList<>();
	}
	
	/**
	 * Getter for the cart total. 
	 * 
	 * @return the current total price of all scanned items
	 */
	public BigDecimal getCartTotal()
	{
		return this.cartTotal;
	}
	
	/**
	 * 
	 * @return
	 */
	public ArrayList<BarcodedProduct> getProducts()
	{
		return (ArrayList<BarcodedProduct>)this.cart;
	}
	
	/**
	 * 
	 */
	public void attach(CartObserver observer)
	{
		if (observer == null)
			throw new SimulationException("arguments cannot be null");
		observers.add(observer);
	}
	
	/**
	 * Adds a scanned (barcoded) item to the cart.
	 * 
	 * @param barcode
	 * 			The barcode of the scanned item.
	 * 
	 * @throws ProductNotFoundException
	 * 			Thrown when product cannto be found in database.
	 */
	public void addToCart(Barcode barcode) throws ProductNotFoundException
	{
		BarcodedProduct p = productDatabase.getProduct(barcode);
		cart.add(p); // add product to cart
		this.cartTotal = this.cartTotal.add(p.getPrice()); // update cart total
		// notify baggingAreaPbservers the barcode was scanned
		// and product was successfully added to the cart -- expect weight change
		notifyProductAdded(p);
//		this.baggingAreaObserver.notifiedItemAdded(p);
	}
	
	private void notifyProductAdded(BarcodedProduct p)
	{
		for (CartObserver obs : observers)
			obs.notifyProductAdded(this, p);
	}

}
