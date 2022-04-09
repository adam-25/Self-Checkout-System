package seng300.software;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.lsmr.selfcheckout.Barcode;
import org.lsmr.selfcheckout.InvalidArgumentSimulationException;
import org.lsmr.selfcheckout.SimulationException;
import org.lsmr.selfcheckout.products.BarcodedProduct;

import seng300.software.exceptions.ProductNotFoundException;
import seng300.software.observers.CartObserver;

public class Cart
{
	private ProductDatabase productDatabase;
	private List<BarcodedProduct> cart;
	private BigDecimal cartTotal;
	private List<CartObserver> observers;

	private int plasticBagsUsed=0; 
	
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
			throw new InvalidArgumentSimulationException("arguments cannot be null");
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


	/**
	 * Adds a scanned (barcoded) item to the cart, without the weight check.
	 * It is literally the same as addToCart, except it calls the attendant for verification, and doesnt ping the bagging area observer
	 * 
	 * @param barcode
	 * 			The barcode of the scanned item.
	 * 
	 * @throws ProductNotFoundException
	 * 			Thrown when product cannto be found in database.
	 */

	

	
	public void addPLUCodedProductToCart(PriceLookupCode PLUCode, double Weight) throws ProductNotFoundException
	{
		PLUCodedProduct pluProduct = databaseLogic.getPLUCodedProduct(PLUCode);
		cart.add(pluProduct); // add product to cart
		
		BigDecimal weightBD = BigDecimal.valueOf(Weight);
		BigDecimal conversion = new BigDecimal("1000");
		BigDecimal convWeight = weightBD.divide(conversion);
		BigDecimal pluAddPrice = pluProduct.getPrice().multiply(convWeight);
	
		this.cartTotal = this.cartTotal.add(pluAddPrice); // update cart total
		pluItemWeight = Weight;
    
		notifyPLUProductAdded(pluProduct, Weight);

		//Attendant call to verify the user is adding the right item 


		cart.add(p); // add product to cart
		this.cartTotal = this.cartTotal.add(p.getPrice()); // update cart total
		// notify baggingAreaPbservers the barcode was scanned
		// and product was successfully added to the cart -- expect weight change
		//notifyProductAdded(p);
//		this.baggingAreaObserver.notifiedItemAdded(p);
	}

	public void removeFromCart(BarcodedProduct product) throws ProductNotFoundException
	{
		cart.remove(product); // Remove product to cart
		this.cartTotal = this.cartTotal.subtract(product.getPrice()); // update cart total
		notifyProductRemoved(product);

		// Might need to change method to remove both barcoded and plu coded items.

	}
	

	private void notifyProductAdded(BarcodedProduct p)
	{
		for (CartObserver obs : observers)
			obs.notifyProductAdded(this, p);
	}


	public int getBags(){
		return this.plasticBagsUsed;

	private void notifyPLUProductAdded(PLUCodedProduct PLUProduct, double Weight)
	{
		for (CartObserver obs : observers)
			obs.notifyPLUProductAdded(this, PLUProduct, Weight);
	}

	public void setBags(int numberOfBags){
		this.plasticBagsUsed= numberOfBags;
	}

	public void addPlasticBags( int numberOfBags){
		this.setBags( this.getBags()+numberOfBags);
		BigDecimal value= new BigDecimal(numberOfBags*0.1);
		this.cartTotal.add(value);
	}

	private void notifyProductRemoved(BarcodedProduct p)
	{
		for (CartObserver obs : observers)
			obs.notifyProductRemoved(this, p);

	}

}
