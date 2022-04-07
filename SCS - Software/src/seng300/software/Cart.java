package seng300.software;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.lsmr.selfcheckout.Barcode;
import org.lsmr.selfcheckout.InvalidArgumentSimulationException;
import org.lsmr.selfcheckout.PriceLookupCode;
import org.lsmr.selfcheckout.external.ProductDatabases;
import org.lsmr.selfcheckout.products.BarcodedProduct;
import org.lsmr.selfcheckout.products.PLUCodedProduct;
import org.lsmr.selfcheckout.products.Product;

import seng300.software.exceptions.ProductNotFoundException;
import seng300.software.observers.CartObserver;

public class Cart
{
	private ProductDatabaseLogic productDatabaseLogic;

	private List<Product> cart;


	private BigDecimal cartTotal;
	private List<CartObserver> observers;
	private double pluItemWeight; 
	
	
	public Cart()
	{
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
	public ArrayList<Product> getProducts()
	{
		return (ArrayList<Product>)this.cart;
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
		if (!ProductDatabases.BARCODED_PRODUCT_DATABASE.containsKey(barcode))
			throw new ProductNotFoundException();
		
		cart.add(ProductDatabases.BARCODED_PRODUCT_DATABASE.get(barcode)); // add product to cart
		this.cartTotal = this.cartTotal.add(ProductDatabases.BARCODED_PRODUCT_DATABASE.get(barcode).getPrice()); // update cart total
		// notify baggingAreaPbservers the barcode was scanned
		// and product was successfully added to the cart -- expect weight change
		notifyProductAdded(ProductDatabases.BARCODED_PRODUCT_DATABASE.get(barcode));
//		this.baggingAreaObserver.notifiedItemAdded(p);
	}
	
	
	public void addPLUCodedProductToCart(PriceLookupCode PLUCode, double Weight) throws ProductNotFoundException
	{

		boolean databaseChecker = ProductDatabases.PLU_PRODUCT_DATABASE.containsKey(PLUCode);
		if (databaseChecker = false) {
			throw new ProductNotFoundException();
		}
		
		cart.add(ProductDatabases.PLU_PRODUCT_DATABASE.get(PLUCode)); // add product to cart
		this.cartTotal = this.cartTotal.add(ProductDatabases.PLU_PRODUCT_DATABASE.get(PLUCode).getPrice()); // update cart total
		pluItemWeight = Weight;
		// notify baggingAreaPbservers the barcode was scanned
		// and product was successfully added to the cart -- expect weight change
		notifyPLUProductAdded(ProductDatabases.PLU_PRODUCT_DATABASE.get(PLUCode), Weight);
//		this.baggingAreaObserver.notifiedItemAdded(p);
	}
	
	
	private void notifyProductAdded(BarcodedProduct p)
	{
		for (CartObserver obs : observers)
			obs.notifyProductAdded(this, p);
	}
	
	private void notifyPLUProductAdded(PLUCodedProduct PLUProduct, double weight)
	{
		for (CartObserver obs : observers)
			obs.notifyPLUProductAdded(this, PLUProduct, weight);
	}
	
	
	public double getPLUWeight() {
		return pluItemWeight;
	}

}
