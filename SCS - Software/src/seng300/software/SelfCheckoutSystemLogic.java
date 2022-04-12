package seng300.software;


import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.swing.Timer;
import javax.swing.WindowConstants;

import org.lsmr.selfcheckout.PriceLookupCode;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.observers.ReceiptPrinterObserver;
import org.lsmr.selfcheckout.external.ProductDatabases;

import org.lsmr.selfcheckout.devices.BanknoteDispenser;
import org.lsmr.selfcheckout.devices.CoinDispenser;
import java.util.ArrayList;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.observers.ReceiptPrinterObserver;
import org.lsmr.selfcheckout.PLUCodedItem;
import org.lsmr.selfcheckout.BarcodedItem;
import org.lsmr.selfcheckout.Item;
import org.lsmr.selfcheckout.products.BarcodedProduct;
import org.lsmr.selfcheckout.products.PLUCodedProduct;
import org.lsmr.selfcheckout.products.Product;
import javax.swing.Timer;


import seng300.software.ProductDatabaseLogic;
import seng300.software.Cart;
import seng300.software.GUI.AttendantGUI;
import seng300.software.GUI.BlockNotifiableGui;
import seng300.software.GUI.CustomerGui;
import seng300.software.GUI.DisableableGui;
import seng300.software.observers.BaggingAreaObserver;
import seng300.software.observers.CartObserver;
import seng300.software.observers.PrinterObserver;
import seng300.software.observers.ScannerObserver;

/**
 * Central logic for self checkout station functionalities.
 * Handles scanning an item, updating the total bill,
 * and some inter-device communication.
 * 
 *
 */
public class SelfCheckoutSystemLogic
{
	public final ProductDatabaseLogic		productDatabase; 	// products sold in store
	public static BlockNotifiableGui AttendantInstance = AttendantLogic.getInstance();
	public final SelfCheckoutStation	station;			// station hardware
	public final Checkout 				checkout;			// checkout functionality
	// Checkout made 'public final' so that the payment methods can be easily accessed
	// instead of having to make wrapper methods for all of them.

	// Attached observers -- handle communication between hardware devices and logic
	private ScannerObserver				mainScannerObserver, handheldScannerObserver;
	private ReceiptPrinterObserver		printerObserver;
	private BaggingAreaObserver			baggingAreaObserver;
	private double 						baggingAreaSensitivity;
	private boolean blocked			= false; // used to simulate blocking the system
	private boolean isCheckingOut	= false;
	// Cart to track items scanned and observer to pass messages
	public Cart			cart;
	private CartObserver	cartObserver;

	private ArrayList<Item> baggingAreaItems = new ArrayList<Item>();
	private CustomerGui cGui;
	private boolean isTurnedOn;
		
	
	public boolean testing = false;
	/**
	 * Basic constructor
	 * 
	 * @param scs
	 * 			Self checkout station to install logic on.
	 * @param database
	 * 			Connection to database of products in available in store.
	 */

	public SelfCheckoutSystemLogic(SelfCheckoutStation scs) // take pin to unblock station as input?
			throws NullPointerException
	{
		if (scs == null)
			throw new NullPointerException("arguments cannot be null");
		this.productDatabase = new ProductDatabaseLogic();
		
		this.station = scs;

		this.printerObserver = new PrinterObserver(this);
		this.station.printer.attach(printerObserver);
		
		this.baggingAreaSensitivity	= this.station.baggingArea.getSensitivity();
		this.baggingAreaObserver	= new BaggingAreaObserver(this);
		this.station.baggingArea.attach(baggingAreaObserver);
		
		this.cartObserver = new CartObserver(this.baggingAreaObserver);
		this.cart = new Cart();
		this.cart.attach(cartObserver);
		
		this.mainScannerObserver = new ScannerObserver(this.cart);
		this.station.mainScanner.attach(mainScannerObserver);
		
		this.handheldScannerObserver = new ScannerObserver(this.cart);
		this.station.handheldScanner.attach(handheldScannerObserver);
		
		this.checkout = new Checkout(station, this.cart.getProducts(), this.cart.getCartTotal());
	}
	
	/**
	 * Starts the checkout process. Called when customer
	 * indicates they want to checkout (e.g. by pressing a checkout button).
	 * 
	 */
	public void wantsToCheckout()
	{
		// disable scanners
		this.station.mainScanner.disable();
		this.station.handheldScanner.disable();
		this.station.cardReader.enable();
		this.station.banknoteInput.enable();
		this.station.coinSlot.enable();
		isCheckingOut = true;
		// update cart and price
		checkout.update(this.cart.getCartTotal());
	}
	
	/**
	 * Lets customer add item after partial payment
	 * mid-way through checkout process.
	 * May only be called before 
	 * checkout.finishPayment() is called.
	 * To return to checkout, call wantToCheckout();
	 */
	public void addItemAfterCheckoutStart()
	{
		// enable scanners again
		this.station.mainScanner.enable();
		this.station.handheldScanner.enable();
		this.station.cardReader.disable();
		this.station.banknoteInput.disable();
		this.station.coinSlot.disable();
		isCheckingOut = false;
	}
	
	/**
	 * Wrapper for this.checkout.finishPayment() method.
	 */
	public void finishCheckout()
	{
		if (isCheckingOut)
			this.checkout.finishPayment();
		// ignore attempts to finish checking out
		// if checkout has not been started
		// you wouldn't realistically be able to get the the 
		// 'Finish Checkout' button if you do not click the 
		// 'Start Checkout' button.
	}
	
//	public void returnToCheckout()
//	{
//		this.station.mainScanner.disable();
//		this.station.handheldScanner.disable();
//		// update cart and price
//		checkout.update();
//	}
	
	/**
	 * Getter for bagging area scale sensitivity.
	 * 
	 * @return sensitivity of bagging area scale
	 */
	public double getBaggingAreaSensitivity()
	{
		return baggingAreaSensitivity;
	}
	
	/**
	 * Simulates process taken when user indicates they
	 * want to use their own bags during checkout.
	 */
	public void useOwnBags()
	{
		ownBagBlock();
		// attendant station will unblock system...
	}
	
	//fully turns off the self checkout station (disables all devices in scs)
	public void turnOffStation()
	{
		this.station.baggingArea.disable();
		this.station.scanningArea.disable();
		this.station.screen.disable();
		this.station.printer.disable();
		this.station.cardReader.disable();
		this.station.mainScanner.disable();
		this.station.handheldScanner.disable();
		this.station.banknoteInput.disable();
		this.station.banknoteOutput.disable();
		this.station.banknoteValidator.disable();
		this.station.banknoteStorage.disable();
		this.station.coinSlot.disable();
		this.station.coinValidator.disable();
		this.station.coinStorage.disable();
		for(CoinDispenser coinDispenser : this.station.coinDispensers.values())
			coinDispenser.disable();
		
		for(BanknoteDispenser dispenser : this.station.banknoteDispensers.values())
			dispenser.disable();
		
		cGui.shutdown();
	}
	
	//fully turns on the self checkout station (enables all devices in scs)
	public void turnOnStation()
	{
		this.station.baggingArea.enable();
		this.station.scanningArea.enable();
		this.station.screen.enable();
		this.station.printer.enable();
		//this.station.cardReader.enable();
		this.station.mainScanner.enable();
		this.station.handheldScanner.enable();
		//this.station.banknoteInput.enable();
		this.station.banknoteOutput.enable();
		this.station.banknoteValidator.enable();
		this.station.banknoteStorage.enable();
//		this.station.coinSlot.enable();
		this.station.coinValidator.enable();
		this.station.coinStorage.enable();
		for(CoinDispenser coinDispenser : this.station.coinDispensers.values())
			coinDispenser.enable();
		
		for(BanknoteDispenser dispenser : this.station.banknoteDispensers.values())
			dispenser.enable();
		
		ActionListener task = e -> cGui.startup();
		Timer timer = new Timer(2500, task);
		timer.setRepeats(false);
		timer.start();
	}
	
	
	/**
	 * Returns whether the system is currently blocked.
	 * 
	 * @return true if system is blocked; else, false.
	 */
	public boolean isBlocked()
	{
		return blocked;
	}
	
	/**
	 * Blocks the system so customers cannot continue scanning or checking out.
	 */
	private void block()
	{
		blocked = true;
		// disable the scanners
		this.station.mainScanner.disable();
		this.station.handheldScanner.disable();
		this.station.cardReader.disable();
		this.station.banknoteInput.disable();
		this.station.coinSlot.disable();
		
		// TODO: The scales should remain enabled but do we need to disable any other devices?
		// a GUI would probably show up a really annoying error
	}

	public void ownBagBlock() {
		this.block();
		if (!testing){
			AttendantLogic.getInstance().notifyOwnBagBlock(this);
		}
	}
	
	public void weightDiscBlock() {
		this.block();
		if (!testing){
		AttendantLogic.getInstance().notifyWeightDiscBlock(this);
		}
	}
	
//	public void removeProductBlock() {
//		this.block();
//		AttendantInstance.notifyRemoveProductBlock(this);
//	}
	
	public void manualBlock() {
		this.block();
		cGui.disableGui();
	}
	
	public void quietItemInputBlock() { 
		this.block();
	}
	
	/**
	 * Unblocks the system so customer can continue scanning/checkout.
	 */
	public void unblock() // take pin as parameter?
	{
		//notify attendant that station has been unblocked
		if(isCheckingOut)
		{
			this.station.cardReader.enable();
			this.station.banknoteInput.enable();
			this.station.coinSlot.enable();
		}
		else {
			this.station.mainScanner.enable();
			this.station.handheldScanner.enable();
		}
		cGui.enableGui();
//		
//		// validate pin?
		blocked = false;
//		// enable the scanners
//		this.station.mainScanner.enable();
//		this.station.handheldScanner.enable();
//		this.station.cardReader.enable();
	}
	
	public Cart getCart() {
		return this.cart;
	}
	
	public void ignoreBagging() {
		this.handheldScannerObserver.setWeightChecking(false);
		this.mainScannerObserver.setWeightChecking(false);
	}
	
	public void checkBagging() {
		this.handheldScannerObserver.setWeightChecking(true);
		this.mainScannerObserver.setWeightChecking(true);
	}
	
	
	public List<PLUCodedProduct> productLookUp(String Description) {
		
		List<PLUCodedProduct> foundItem = new ArrayList<PLUCodedProduct>();
		List<String> foundItemDescrip = new ArrayList<String>();
		List<PLUCodedProduct> sortFoundItem = new ArrayList<PLUCodedProduct>();
		
		String lowDescription = Description.toLowerCase();
		
		for(Map.Entry<PriceLookupCode, PLUCodedProduct> entry : ProductDatabases.PLU_PRODUCT_DATABASE.entrySet()) {
			String pluLowDescription = entry.getValue().getDescription().toLowerCase();
			if(pluLowDescription.startsWith(lowDescription) == true) {
				foundItem.add(entry.getValue());
				foundItemDescrip.add(pluLowDescription);
			}
		}
		
		Collections.sort(foundItemDescrip);
		
		for (int i = 0; i < foundItem.size(); i++) {
			for (int j = 0; j < foundItemDescrip.size(); j++) {
				  if(foundItem.get(i).getDescription().equalsIgnoreCase(foundItemDescrip.get(j))) {
					  sortFoundItem.add(foundItem.get(j));
				  }
			}
		}
		
		
		return sortFoundItem;
	}

	public void printerOutofPaper() {
		// it must notify attendant
		if (!testing){
			AttendantLogic.getInstance().notifyPrinterOutPaper(this);
		}
	}
	
	public void printerOutofInk() {
		// must notify attendant
		if (!testing){
			AttendantLogic.getInstance().notifyPrinterOutInk(this);
		}
	}
	
	public void attendantRemovesItem(Map<Product, Item> itemToRemove)
	{
		cGui.removeItemFromCart(itemToRemove);
	}

	
	/**
	 * Gets the items on the bagging area.
	 * 
	 * @return An ArrayList of the items in the bagging area.
	 */
	public ArrayList<Item> getBaggingArea() { return this.baggingAreaItems; }
	

	
	
	public ArrayList<Product> getBaggedProducts(){
    	return this.baggingAreaObserver.getBaggedProducts();
	}
	
	/**
	 * Simulates going back to normal operation after removing
	 * an item from the bagging area. 
	 */
	public void returnToNormalBaggingOperation() {
		this.baggingAreaObserver.setBaggingItems(true);
	}

	public void selectItemToRemove(Product someProduct) {
		this.baggingAreaObserver.setBaggingItems(false);
		if (someProduct instanceof PLUCodedWeightProduct) {
			this.baggingAreaObserver.notifiedItemRemoved((PLUCodedWeightProduct)someProduct);
		}
		else if (someProduct instanceof BarcodedProduct) {
			this.baggingAreaObserver.notifiedItemRemoved((BarcodedProduct)someProduct);
		}
		
	}

	public double getBaggingAreaWeight() {
		return this.baggingAreaObserver.getWeightAtLastEvent();
	}
	
	public void resetWeightOnScale() {
		this.baggingAreaObserver.resetToOldWeight();
	}

	//ONLY CALL FOR TESTING, NEVER CALL ELSEWHERE!!!!
	public static void attachBlockNotifiableGui(BlockNotifiableGui gui) {
		AttendantInstance = gui;
	}

	public void reset() {
		this.cart.reset();
		this.baggingAreaObserver.setResetting(true);
		for (int i = 0; i<this.baggingAreaItems.size();i++) {
			this.station.baggingArea.remove(baggingAreaItems.get(i)); 
		}
//		
		this.baggingAreaObserver.reset();
		baggingAreaItems = new ArrayList<Item>();
		this.checkout.reset();
		this.isCheckingOut = false;
		this.unblock();
		this.blocked = false;
	}
	
	public CustomerGui attachGUI() {
		this.cGui = new CustomerGui(this);
		station.screen.getFrame().setContentPane(cGui);
		station.screen.getFrame().setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		station.screen.getFrame().pack();
		return cGui;
	}
	
	public CustomerGui getAttachedGui() { //dirty fix
		return cGui;
	}
	
	public void setGui(CustomerGui gui) {
		cGui = gui;
	}
	
	public boolean systemState() {
		return isTurnedOn;
	}
	//ONLY CALL FOR TESTING, NEVER CALL ELSEWHERE!!!!
	public void testMode() {
		this.testing = true;
	}
}

