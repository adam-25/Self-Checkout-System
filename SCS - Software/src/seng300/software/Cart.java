package seng300.software;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lsmr.selfcheckout.Barcode;
import org.lsmr.selfcheckout.InvalidArgumentSimulationException;
import org.lsmr.selfcheckout.Item;
import org.lsmr.selfcheckout.PriceLookupCode;
import org.lsmr.selfcheckout.external.ProductDatabases;
import org.lsmr.selfcheckout.products.BarcodedProduct;
import org.lsmr.selfcheckout.products.PLUCodedProduct;
import org.lsmr.selfcheckout.products.Product;

import seng300.software.exceptions.ProductNotFoundException;
import seng300.software.observers.CartObserver;

public class Cart
{
	private ProductDatabaseLogic productDatabase;
	private List<Product> cart;
	private Map<Product, Item> cartMap;

	private int plasticBagsUsed=0; 
	


	private BigDecimal cartTotal;
	private List<CartObserver> observers;
	private double pluItemWeight; 
	
	public Cart()
	{
		this.productDatabase = new ProductDatabaseLogic();
		this.cart = new ArrayList<>();
		this.cartTotal = new BigDecimal("0.00");
		this.observers = new ArrayList<>();
		cartMap = new HashMap<>();
	}
	

	
	public void reset() {
		List<Product> removal = new ArrayList<Product>();
		removal.addAll(this.cart);
		this.cart.removeAll(removal);
		this.cartTotal = new BigDecimal("0.00");
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
		ArrayList<Product> returnVal = new ArrayList<Product>();
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
		BarcodedProduct p = productDatabase.getProduct(barcode);
		cart.add(p); // add product to cart
		this.cartTotal = this.cartTotal.add(p.getPrice()); // update cart total
		// notify baggingAreaPbservers the barcode was scanned
		// and product was successfully added to the cart -- expect weight change
		notifyProductAdded(p);
//		this.baggingAreaObserver.notifiedItemAdded(p);
	}
	
	
	/**
	 * Adds a scanned (barcoded) item to the cart, without bagging the item, calls the attendant.
	 * 
	 * @param barcode
	 * 			The barcode of the scanned item.
	 * 
	 * @throws ProductNotFoundException
	 * 			Thrown when product cannto be found in database.
	 */
	public void addToCartNoWeight(Barcode barcode) throws ProductNotFoundException
	{
		BarcodedProduct p = productDatabase.getProduct(barcode);
		cart.add(p); // add product to cart
		this.cartTotal = this.cartTotal.add(p.getPrice()); // update cart total
		// notify baggingAreaPbservers the barcode was scanned
		// and product was successfully added to the cart -- expect weight change
		//notifyProductAdded(p);
//		this.baggingAreaObserver.notifiedItemAdded(p);
		attendantCheck();
	}
	

	
	public void addPLUCodedProductToCart(PriceLookupCode PLUCode, double Weight) throws ProductNotFoundException
	{
		PLUCodedProduct pluProduct = productDatabase.getPLUCodedProduct(PLUCode);
		cart.add(pluProduct); // add product to cart
		
		BigDecimal weightBD = BigDecimal.valueOf(Weight);
		BigDecimal conversion = new BigDecimal("1000");
		BigDecimal convWeight = weightBD.divide(conversion);
		BigDecimal pluAddPrice = pluProduct.getPrice().multiply(convWeight);
	
		this.cartTotal = this.cartTotal.add(pluAddPrice); // update cart total
		pluItemWeight = Weight;
    
		notifyPLUProductAdded(pluProduct, Weight);

	}
	
	//Adds plu product without triggered weight changes
	public void addPLUCodedProductToCartNoWeight(PriceLookupCode PLUCode, double Weight) throws ProductNotFoundException
	{
		PLUCodedProduct pluProduct = productDatabase.getPLUCodedProduct(PLUCode);
		cart.add(pluProduct); // add product to cart
		
		BigDecimal weightBD = BigDecimal.valueOf(Weight);
		BigDecimal conversion = new BigDecimal("1000");
		BigDecimal convWeight = weightBD.divide(conversion);
		BigDecimal pluAddPrice = pluProduct.getPrice().multiply(convWeight);
	
		this.cartTotal = this.cartTotal.add(pluAddPrice); // update cart total
		pluItemWeight = Weight;
    
		//notifyPLUProductAdded(pluProduct, Weight);
		attendantCheck();

	}

	public void removeFromCart(BarcodedProduct product) throws ProductNotFoundException
	{
		cart.remove(product); // Remove product to cart
		this.cartTotal = this.cartTotal.subtract(product.getPrice()); // update cart total
		notifyProductRemoved(product);
		// Might need to change method to remove both barcoded and plu coded items.

	}
	
	public void removeFromCart(PLUCodedWeightProduct product) throws ProductNotFoundException
	{
		cart.remove(product); // Remove product to cart
		BigDecimal productWeight = new BigDecimal(product.getWeight());
		this.cartTotal = this.cartTotal.subtract(product.getPrice().multiply(productWeight)); // update cart total
		notifyPLUProductRemoved(product);
		// Might need to change method to remove both barcoded and plu coded items.

	}

	private void notifyProductAdded(BarcodedProduct p)
	{
		for (CartObserver obs : observers)
			obs.notifyProductAdded(this, p);
	}
	

	private void notifyPLUProductAdded(PLUCodedProduct PLUProduct, double Weight)
	{
		for (CartObserver obs : observers)
			obs.notifyPLUProductAdded(this, PLUProduct, Weight);
	}
	
	
	public double getPLUWeight() {
		return pluItemWeight;
	}

	private void notifyProductRemoved(BarcodedProduct p)
	{
		for (CartObserver obs : observers)
			obs.notifyProductRemoved(this, p);

	}
	
	private void notifyPLUProductRemoved(PLUCodedWeightProduct p)
	{
		for (CartObserver obs : observers)
			obs.notifyPLUProductRemoved(this, p);

	}

	public int getBags(){
		return this.plasticBagsUsed;
	}

	public void setBags(int numberOfBags){
		this.plasticBagsUsed= numberOfBags;
	}

	public void addPlasticBags( int numberOfBags){
		this.setBags( this.getBags()+numberOfBags);
		BigDecimal value= new BigDecimal(numberOfBags*0.05);
		cartTotal = cartTotal.add(value);
	}
	
	public void attendantCheck(){
		for (CartObserver obs : observers)
			obs.notifyProductNoWeightCheck();
		
	}

}
