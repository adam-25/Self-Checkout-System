package seng300.software.GUI;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.lsmr.selfcheckout.Banknote;
import org.lsmr.selfcheckout.Barcode;
import org.lsmr.selfcheckout.BarcodedItem;
import org.lsmr.selfcheckout.Card;
import org.lsmr.selfcheckout.Coin;
import org.lsmr.selfcheckout.InvalidArgumentSimulationException;
import org.lsmr.selfcheckout.Item;
import org.lsmr.selfcheckout.Numeral;
import org.lsmr.selfcheckout.PLUCodedItem;
import org.lsmr.selfcheckout.PriceLookupCode;
import org.lsmr.selfcheckout.devices.DisabledException;
import org.lsmr.selfcheckout.devices.OverloadException;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.external.ProductDatabases;
import org.lsmr.selfcheckout.products.BarcodedProduct;
import org.lsmr.selfcheckout.products.PLUCodedProduct;
import org.lsmr.selfcheckout.products.Product;

import seng300.software.MembersProgramStub;
import seng300.software.MembershipCard;
import seng300.software.PLUCodedWeightProduct;
import seng300.software.PayWithCoin;
import seng300.software.SelfCheckoutSystemLogic;
import seng300.software.GUI.ProductLookupPanel.ResultsPanel;
import seng300.software.exceptions.ProductNotFoundException;

import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class CustomerGui extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7637105711156584933L;
	
	private StationUnavailablePanel unavailablePanel;
	private StationReadyPanel readyPanel;
	private CustomerCheckoutPanel checkoutPanel;
	private ProductLookupPanel lookupPanel;
	private CustomerPaymentPanel paymentPanel;
	private EnterMembershipPanel membershipPanel;
	private CoinPaymentPanel payCoinPanel;
	private BanknotePaymentPanel payBanknotePanel;
	private CheckoutCompletePanel checkoutCompletePanel;
//	private PaymentFailedPanel paymentFailedPanel;
	private BaggingAreaPanel baggingAreaPanel = null;
	private EnterPlasticBagsPanel plasticBagsPanel;
	private RemoveItemLog removeItemLog;
	private PlaceItemPanel placeItemPanel;
	private RemoveFromBaggingAreaPanel rmFromBaggingPanel;
	
	private SelfCheckoutSystemLogic logic;
	private boolean weightChecking = true;
	private Item lastAddedItem = null;
	private String lastItemDescription = "";
	private String itemToRemoveDescription = "";
	private int itemToRemoveIndex = -1;
	
	/**
	 * Create the panel.
	 */
	public CustomerGui(SelfCheckoutSystemLogic logic) 
	{
		setLayout(new CardLayout(0, 0));
		
		this.logic = logic;
		
		unavailablePanel = new StationUnavailablePanel();
		readyPanel = new StationReadyPanel();
		readyPanel.startButton.addActionListener(e -> displayCheckoutPanel());
		
		checkoutPanel = new CustomerCheckoutPanel();
		checkoutPanel.searchProductBtn.addActionListener(e -> displayProductLookupPanel());
		checkoutPanel.useOwnBagsBtn.addActionListener(e -> useOwnBagsClicked());
		checkoutPanel.checkoutBtn.addActionListener(e -> displayPlasticBagsPanel());
		checkoutPanel.pluEntryPinPad.padEnterBtn.addActionListener(e -> getPluCode());
		checkoutPanel.viewBaggingAreaBtn.addActionListener(e -> displayBaggingAreaPanel());
		checkoutPanel.removeItemBtn.addActionListener(e -> removeItemFromCart());
		checkoutPanel.scanItemBtn.addActionListener(e -> scanRandomItem());
		checkoutPanel.doNotBagBtn.addActionListener(e -> doNotBagNextAddedItem());
		
		lookupPanel = new ProductLookupPanel();
		lookupPanel.returnButton.addActionListener(e -> displayCheckoutPanel());
		for (KeyboardButton btn : lookupPanel.keyboardBtns)
		{
			btn.addActionListener(e -> push(btn));
		}
		
		paymentPanel = new CustomerPaymentPanel();
		paymentPanel.returnToCheckoutBtn.addActionListener(e -> returnToCheckoutClicked());
		paymentPanel.addMembershipBtn.addActionListener(e -> displayMembershipPanel());
		paymentPanel.payWithCoinBtn.addActionListener(e -> displayPayCoinPanel());
		paymentPanel.payWithCashBtn.addActionListener(e -> displayPayCashPanel());
		
		payCoinPanel = new CoinPaymentPanel();
		payCoinPanel.doneBtn.addActionListener(e -> displayPaymentPanel());
		payCoinPanel.dimeBtn.addActionListener(e -> payDime());
		payCoinPanel.loonieBtn.addActionListener(e -> payLoonie());
		payCoinPanel.nickelBtn.addActionListener(e -> payNickel());
		payCoinPanel.quarterBtn.addActionListener(e -> payQuarter());
		payCoinPanel.toonieBtn.addActionListener(e -> payToonie());
		
		payBanknotePanel = new BanknotePaymentPanel();
		payBanknotePanel.doneBtn.addActionListener(e -> displayPaymentPanel());
		payBanknotePanel.fiveBtn.addActionListener(e -> payFive());
		payBanknotePanel.hundredBtn.addActionListener(e -> payHundred());
		payBanknotePanel.fiftyBtn.addActionListener(e -> payFifty());
		payBanknotePanel.twentyBtn.addActionListener(e -> payTwenty());
		payBanknotePanel.tenBtn.addActionListener(e -> payTen());
		
		membershipPanel = new EnterMembershipPanel();
		membershipPanel.cancelBtn.addActionListener(e -> displayPaymentPanel());
		membershipPanel.pinPad.padEnterBtn.addActionListener(e -> addMembershipToCheckout(membershipPanel.pinPad.getValue()));

		plasticBagsPanel = new EnterPlasticBagsPanel();
		plasticBagsPanel.pinPad.padEnterBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				String value = plasticBagsPanel.pinPad.getValue();
				int num = value == "" ? 0 : Integer.parseInt(value);
				enterNumPlasticBags(num);
			}
		});
		
		checkoutCompletePanel = new CheckoutCompletePanel();
		
		placeItemPanel = new PlaceItemPanel();
		placeItemPanel.placeItemBtn.addActionListener(e -> placeItem());
		
		rmFromBaggingPanel = new RemoveFromBaggingAreaPanel();
		rmFromBaggingPanel.rmItemBtn.addActionListener(e -> removeFromBaggingAfterRemoveFromCart());
    
		add(unavailablePanel);
		add(readyPanel);
		add(checkoutPanel);
		add(lookupPanel);
		add(paymentPanel);
		add(membershipPanel);
		add(payCoinPanel);
		add(payBanknotePanel);
		add(plasticBagsPanel);
		add(placeItemPanel);
		add(rmFromBaggingPanel);
		shutdown();
	}
	
	public void reset() // called between customers at end of checkout
	{
		unavailablePanel.setVisible(false);
		readyPanel.setVisible(true);
		checkoutPanel.setVisible(false);
		lookupPanel.setVisible(false);
		paymentPanel.setVisible(false);
		membershipPanel.setVisible(false);
		payCoinPanel.setVisible(false);
		payBanknotePanel.setVisible(false);
		if(baggingAreaPanel != null)
		{
			baggingAreaPanel.setVisible(false);
			remove(baggingAreaPanel);
			validate();
			baggingAreaPanel = null;
		}
		plasticBagsPanel.setVisible(false);
		rmFromBaggingPanel.setVisible(false);
		hideRemoveItemPanel();
	}
	
	public void startup()
	{
		logic.turnOnStation();
		readyPanel.setVisible(true);
		unavailablePanel.setVisible(false);
	}
	
	public void shutdown()
	{
		unavailablePanel.setVisible(true);
		readyPanel.setVisible(false);
		checkoutPanel.setVisible(false);
		lookupPanel.setVisible(false);
		paymentPanel.setVisible(false);
		membershipPanel.setVisible(false);
		payCoinPanel.setVisible(false);
		payBanknotePanel.setVisible(false);
		if(baggingAreaPanel != null)
		{
			baggingAreaPanel.setVisible(false);
			remove(baggingAreaPanel);
			validate();
			baggingAreaPanel = null;
		}
		plasticBagsPanel.setVisible(false);
		rmFromBaggingPanel.setVisible(false);
		hideRemoveItemPanel();
		validate();
		logic.turnOffStation();
	}
	
	public void useOwnBagsClicked()
	{
		checkoutPanel.showAttendantNotifiedPanel();
//		logic.ownBagBlock();
//		checkoutPanel.showLogoPanel();
	}
	
	public void displayCheckoutPanel()
	{
		readyPanel.setVisible(false);
		checkoutPanel.setVisible(true);
		lookupPanel.setVisible(false);
		paymentPanel.setVisible(false);
		membershipPanel.setVisible(false);
		payCoinPanel.setVisible(false);
		payBanknotePanel.setVisible(false);
		if(baggingAreaPanel != null)
		{
			baggingAreaPanel.setVisible(false);
			remove(baggingAreaPanel);
			validate();
			baggingAreaPanel = null;
		}
		plasticBagsPanel.setVisible(false);
		placeItemPanel.setVisible(false);
		rmFromBaggingPanel.setVisible(false);
		hideRemoveItemPanel();
		validate();
	}
	
	public void displayCheckoutCompletePanel()
	{
		readyPanel.setVisible(false);
		checkoutPanel.setVisible(true);
		lookupPanel.setVisible(false);
		paymentPanel.setVisible(false);
		membershipPanel.setVisible(false);
		payCoinPanel.setVisible(false);
		payBanknotePanel.setVisible(false);
		if(baggingAreaPanel != null)
		{
			baggingAreaPanel.setVisible(false);
			remove(baggingAreaPanel);
			validate();
			baggingAreaPanel = null;
		}
		plasticBagsPanel.setVisible(false);
		rmFromBaggingPanel.setVisible(false);
		hideRemoveItemPanel();
		validate();
	}
	
	public void displayPlaceItemPanel()
	{
		placeItemPanel.itemDescriptionLabel.setText(lastItemDescription);
		placeItemPanel.validate();
		placeItemPanel.setVisible(true);

		readyPanel.setVisible(false);
		checkoutPanel.setVisible(false);
		lookupPanel.setVisible(false);
		paymentPanel.setVisible(false);
		membershipPanel.setVisible(false);
		payCoinPanel.setVisible(false);
		payBanknotePanel.setVisible(false);
		if(baggingAreaPanel != null)
		{
			baggingAreaPanel.setVisible(false);
			remove(baggingAreaPanel);
			validate();
			baggingAreaPanel = null;
		}
		plasticBagsPanel.setVisible(false);
		rmFromBaggingPanel.setVisible(false);
		hideRemoveItemPanel();
		validate();
	}
	
	public void displayRemoveFromBaggingPanel()
	{
		placeItemPanel.setVisible(false);
		readyPanel.setVisible(false);
		checkoutPanel.setVisible(false);
		lookupPanel.setVisible(false);
		paymentPanel.setVisible(false);
		membershipPanel.setVisible(false);
		payCoinPanel.setVisible(false);
		payBanknotePanel.setVisible(false);
		if(baggingAreaPanel != null)
		{
			baggingAreaPanel.setVisible(false);
			remove(baggingAreaPanel);
			validate();
			baggingAreaPanel = null;
		}
		plasticBagsPanel.setVisible(false);
		rmFromBaggingPanel.itemDescriptionLabel.setText(itemToRemoveDescription);
		rmFromBaggingPanel.setVisible(true);
	}
	
	public void displayBaggingAreaPanel()
	{
		ArrayList<String> descriptions = new ArrayList<>();
		for (Product i : logic.getBaggedProducts())
		{
			if (i instanceof BarcodedProduct)
			{
				descriptions.add(((BarcodedProduct)i).getDescription());
			}
			else if (i instanceof PLUCodedWeightProduct)
			{
				descriptions.add(((PLUCodedWeightProduct)i).getDescription());
			}
		}
		baggingAreaPanel = new BaggingAreaPanel(descriptions);
		baggingAreaPanel.returnButton.addActionListener(e -> displayCheckoutPanel());
		baggingAreaPanel.deleteButton.addActionListener(e -> removeItemfromBaggingArea(baggingAreaPanel.getCurrentSelectedIndex()));
		add(baggingAreaPanel);
		
		readyPanel.setVisible(false);
		checkoutPanel.setVisible(false);
		lookupPanel.setVisible(false);
		paymentPanel.setVisible(false);
		membershipPanel.setVisible(false);
		payCoinPanel.setVisible(false);
		payBanknotePanel.setVisible(false);
		baggingAreaPanel.setVisible(true);
		plasticBagsPanel.setVisible(false);
		placeItemPanel.setVisible(false);
		rmFromBaggingPanel.setVisible(false);
		hideRemoveItemPanel();
		validate();
	}
	
	public void displayProductLookupPanel()
	{
		readyPanel.setVisible(false);
		checkoutPanel.setVisible(false);
		lookupPanel.setVisible(true);
		paymentPanel.setVisible(false);
		membershipPanel.setVisible(false);
		payCoinPanel.setVisible(false);
		payBanknotePanel.setVisible(false);
		if(baggingAreaPanel != null)
		{
			baggingAreaPanel.setVisible(false);
			remove(baggingAreaPanel);
			validate();
			baggingAreaPanel = null;
		}
		plasticBagsPanel.setVisible(false);
		placeItemPanel.setVisible(false);
		rmFromBaggingPanel.setVisible(false);
		hideRemoveItemPanel();
		validate();
	}
	
	public void displayPlasticBagsPanel()
	{
		readyPanel.setVisible(false);
		checkoutPanel.setVisible(false);
		lookupPanel.setVisible(false);
		paymentPanel.setVisible(false);
		membershipPanel.setVisible(false);
		payCoinPanel.setVisible(false);
		payBanknotePanel.setVisible(false);
		if(baggingAreaPanel != null)
		{
			baggingAreaPanel.setVisible(false);
			remove(baggingAreaPanel);
			validate();
			baggingAreaPanel = null;
		}
		plasticBagsPanel.setVisible(true);
		placeItemPanel.setVisible(false);
		rmFromBaggingPanel.setVisible(false);
		hideRemoveItemPanel();
		validate();
	}
	
	public void displayPaymentPanel()
	{
		readyPanel.setVisible(false);
		checkoutPanel.setVisible(false);
		lookupPanel.setVisible(false);
		paymentPanel.setVisible(true);
		membershipPanel.setVisible(false);
		payCoinPanel.setVisible(false);
		payBanknotePanel.setVisible(false);
		if(baggingAreaPanel != null)
		{
			baggingAreaPanel.setVisible(false);
			remove(baggingAreaPanel);
			validate();
			baggingAreaPanel = null;
		}
		plasticBagsPanel.setVisible(false);
		placeItemPanel.setVisible(false);
	}
	
	public void displayMembershipPanel()
	{
		readyPanel.setVisible(false);
		checkoutPanel.setVisible(false);
		lookupPanel.setVisible(false);
		paymentPanel.setVisible(false);
		membershipPanel.setVisible(true);
		payCoinPanel.setVisible(false);
		payBanknotePanel.setVisible(false);
		if(baggingAreaPanel != null)
		{
			baggingAreaPanel.setVisible(false);
			remove(baggingAreaPanel);
			validate();
			baggingAreaPanel = null;
		}
		plasticBagsPanel.setVisible(false);
		placeItemPanel.setVisible(false);
	}
	
	public void displayPayCoinPanel()
	{
		readyPanel.setVisible(false);
		checkoutPanel.setVisible(false);
		lookupPanel.setVisible(false);
		paymentPanel.setVisible(false);
		membershipPanel.setVisible(false);
		payCoinPanel.setVisible(true);
		payBanknotePanel.setVisible(false);
		if(baggingAreaPanel != null)
		{
			baggingAreaPanel.setVisible(false);
			remove(baggingAreaPanel);
			validate();
			baggingAreaPanel = null;
		}
		plasticBagsPanel.setVisible(false);
		placeItemPanel.setVisible(false);
	}
	
	public void displayPayCashPanel()
	{
		readyPanel.setVisible(false);
		checkoutPanel.setVisible(false);
		lookupPanel.setVisible(false);
		paymentPanel.setVisible(false);
		membershipPanel.setVisible(false);
		payCoinPanel.setVisible(false);
		payBanknotePanel.setVisible(true);
		if(baggingAreaPanel != null)
		{
			baggingAreaPanel.setVisible(false);
			remove(baggingAreaPanel);
			validate();
			baggingAreaPanel = null;
		}
		plasticBagsPanel.setVisible(false);
		placeItemPanel.setVisible(false);
	}
	
	public void lookupProduct(String searchText)
	{
		if (!searchText.isEmpty())
		{
			List<PLUCodedProduct> results = logic.productLookUp(searchText);
			List<LookupResultButton> btns = new ArrayList<>();
			for (PLUCodedProduct p : results)
			{
				LookupResultButton btn = new LookupResultButton(p);
				btn.addActionListener(e -> {
					try {
						addPluProductToCart(btn.getProduct().getPLUCode());
					} catch (ProductNotFoundException ex) {
						// This should never execute.
					}
				});
				btns.add(btn);
			}
			lookupPanel.displayProducts(btns);
		}
		// ignore empty searches
	}

	private void addPluProductToCart(PriceLookupCode code) throws ProductNotFoundException
	{
		if (ProductDatabases.PLU_PRODUCT_DATABASE.containsKey(code))
		{
			// Create random plucoded product for testing
			double maxScaleWeight = logic.station.scanningArea.getWeightLimit();
			Random rand = new Random();
			double weight = (rand.nextDouble() * maxScaleWeight) + logic.getBaggingAreaSensitivity();
			PLUCodedItem item = new PLUCodedItem(code, weight);
			// add product to cart (no exception should ever be thrown)
			if (weightChecking)
			{
				logic.getCart().addPLUCodedProductToCart(code, item.getWeight());
				lastAddedItem = item;
				lastItemDescription = ProductDatabases.PLU_PRODUCT_DATABASE.get(code).getDescription();
				BigDecimal pricePerKilo = ProductDatabases.PLU_PRODUCT_DATABASE.get(code).getPrice();
				checkoutPanel.itemLogPanel.addItem(lastItemDescription, pricePerKilo.multiply(new BigDecimal(item.getWeight() / 1000.0)));
				checkoutPanel.itemLogPanel.setBillTotalValue(logic.getCart().getCartTotal());
				displayPlaceItemPanel();
			}
			else
			{
				logic.getCart().addPLUCodedProductToCartNoWeight(code, item.getWeight());
				lastAddedItem = item;
				lastItemDescription = ProductDatabases.PLU_PRODUCT_DATABASE.get(code).getDescription();
				logic.checkBagging();
				weightChecking = true;
				BigDecimal pricePerKilo = ProductDatabases.PLU_PRODUCT_DATABASE.get(code).getPrice();
				checkoutPanel.itemLogPanel.addItem(lastItemDescription, pricePerKilo.multiply(new BigDecimal(item.getWeight() / 1000.0)));
				checkoutPanel.itemLogPanel.setBillTotalValue(logic.getCart().getCartTotal());
				displayCheckoutPanel();
			}
		}
		else
		{
			throw new ProductNotFoundException();
		}
	}
	
	
	
	private void doNotBagNextAddedItem()
	{
		weightChecking = false;
		logic.ignoreBagging();
	}
	
	
	
	public void scanRandomItem()
	{
		// get random barcode from product database
		Random rand = new Random();
		int index = rand.nextInt(ProductDatabases.BARCODED_PRODUCT_DATABASE.size());
		Barcode code = (Barcode)ProductDatabases.BARCODED_PRODUCT_DATABASE.keySet().toArray()[index];
		BarcodedProduct p = ProductDatabases.BARCODED_PRODUCT_DATABASE.get(code);
		BarcodedItem item = new BarcodedItem(code, p.getExpectedWeight());
		// scan until product added successfully
		int oldSize = this.logic.getCart().getProducts().size();
		while(this.logic.getCart().getProducts().size() <= oldSize)
		{
			this.logic.station.mainScanner.scan(item);
		}
		lastAddedItem = item;
		lastItemDescription = ProductDatabases.BARCODED_PRODUCT_DATABASE.get(code).getDescription();
		checkoutPanel.itemLogPanel.addItem(lastItemDescription, ProductDatabases.BARCODED_PRODUCT_DATABASE.get(code).getPrice());
		checkoutPanel.itemLogPanel.setBillTotalValue(logic.getCart().getCartTotal());
		if (weightChecking)
		{
			displayPlaceItemPanel();
		}
	}
	
	private void placeLastAddedItem()
	{
		if (lastAddedItem != null)
		{
			this.logic.station.baggingArea.add(lastAddedItem);
			baggedItems.add(lastAddedItem);
		}
	}	
	
	private void push(KeyboardButton btn)
	{
		KeyboardKey key = btn.getKey();
		String searchText = lookupPanel.getSearchText();
		if (key == KeyboardKey.BACK)
		{
			if (!searchText.isEmpty())
			{
				searchText = searchText.substring(0, searchText.length() - 1);
				lookupPanel.setSearchText(searchText);
			}
			// ignore attempts to backspace when search field empty
		}
		else if (key == KeyboardKey.CLEAR)
		{
			if (!searchText.isEmpty())
			{
				lookupPanel.reset();
			}
			// ignore attempts to clear when search field empty
		}
		else if (key != KeyboardKey.ENTER)
		{
			searchText += key.getValue();
			lookupPanel.setSearchText(searchText);
		}
		lookupProduct(searchText);
	}
	
	private void getPluCode()
	{
		String value = checkoutPanel.pluEntryPinPad.getValue();
		if(!value.isEmpty())
		{
			try
			{
				PriceLookupCode code = new PriceLookupCode(value);
				checkoutPanel.hidePluEntryPanelErrorMsg();
				addPluProductToCart(code);
				checkoutPanel.pluEntryPinPad.clear();
				checkoutPanel.showLogoPanel();
			}
			catch(Exception e)
			{
				checkoutPanel.showPluEntryPanelErrorMsg();
				checkoutPanel.pluEntryPinPad.clear();
			}
		}
		// ignore empty searches
	}
	
	private ArrayList<Item> baggedItems = new ArrayList<>();
	
	private void removeItemfromBaggingArea(int index)
	{
		Product p = this.logic.getBaggedProducts().get(index);
		this.logic.selectItemToRemove(p); //should work for barcoded and plu coded products
//		double weight;
//		if (p instanceof BarcodedProduct){
//		    weight = ((BarcodedProduct) p).getExpectedWeight();
//		    // TODO Method throwing exception because creating new Item which has different pointer/object reference
//		    // then the item that was actually added to the bagging area
//		    // Need to store a list of bagged items (like in the electronic scale) and then get the correct item to remove
//		    this.logic.station.baggingArea.remove(new BarcodedItem(((BarcodedProduct)p).getBarcode(), weight));
//		}
//		else if (p instanceof PLUCodedWeightProduct){
//		    weight = ((PLUCodedWeightProduct)p).getWeight();
//		    // TODO Method throwing exception because creating new Item which has different pointer/object reference
//		    // then the item that was actually added to the bagging area
//		    // Need to store a list of bagged items and then get the correct item to remove
//		    this.logic.station.baggingArea.remove(new PLUCodedItem(((PLUCodedWeightProduct)p).getPLUCode(), weight));
//		}
		this.logic.station.baggingArea.remove(baggedItems.get(index)); // hack fix
	    baggedItems.remove(index);
		//maybe a sleep?
		this.logic.returnToNormalBaggingOperation();
	}
	
	private void addMembershipToCheckout(String input)
	{
		//TODO
	}
		
	private void enterNumPlasticBags(int numPlasticBags)
	{
		this.logic.getCart().addPlasticBags(numPlasticBags);
		this.logic.wantsToCheckout();
		displayPaymentPanel();
	}
	
	private void returnToCheckoutClicked()
	{
		this.logic.addItemAfterCheckoutStart();
		displayCheckoutPanel();
	}
	
	private JPanel removeItemPanel = null;
	private JButton removeItemFromCartBtn = null;
	private JCheckBox[] productsInLog = null;
	private Map<JCheckBox, Product> removableProducts = null;
	
	private void removeItemFromCart()
	{
		removeItemLog = new RemoveItemLog(this.logic.getCart().getProducts());
		removeItemPanel = (JPanel)removeItemLog.getContentPane();
		removeItemFromCartBtn = removeItemLog.remove;
		productsInLog = removeItemLog.productsInLog;
		removableProducts = removeItemLog.removable;
		removeItemFromCartBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) // remove from cart and from bagging area
			{
				Product temp;
				for (int i = 0; i < productsInLog.length; i++) {
					if (productsInLog[i].isSelected()) {
						itemToRemoveIndex = i;
						temp = removableProducts.get(productsInLog[i]);
						// need a way to remove a speciifc product from the cart?
						if (temp instanceof BarcodedProduct)
						{
							try {
								itemToRemoveDescription = ((BarcodedProduct)temp).getDescription();
								logic.getCart().removeFromCart((BarcodedProduct)temp);
							} catch (ProductNotFoundException e1) {
								// Should never execute
							}
						}
						else
						{
							try {
								itemToRemoveDescription = ((PLUCodedProduct)temp).getDescription();
								logic.getCart().removeFromCart(new PLUCodedWeightProduct((PLUCodedProduct)temp, baggedItems.get(i).getWeight()));
							} catch (ProductNotFoundException e1) {
								// Should never execute
							}
						}
//						removeItemfromBaggingArea(i);
						displayRemoveFromBaggingPanel();
					}
				}
			}
		});
		removeItemLog.remove(removeItemPanel);
		removeItemLog.dispose();
		checkoutPanel.setLeftPanel(removeItemPanel);
	}
	
	private void hideRemoveItemPanel()
	{
		if (removeItemPanel != null)
		{
			checkoutPanel.removeFromLeftPanel(removeItemPanel);
			validate();
			removeItemPanel = null;
			removeItemFromCartBtn = null;
			productsInLog = null;
			removableProducts = null;
		}
	}
	
	private void placeItem()
	{
		placeLastAddedItem();
		displayCheckoutPanel();
	}

	private void removeFromBaggingAfterRemoveFromCart()
	{
		if (itemToRemoveIndex >= 0)
		{
			removeItemfromBaggingArea(itemToRemoveIndex);
			checkoutPanel.itemLogPanel.removeLogItem(itemToRemoveIndex);
			itemToRemoveIndex = -1;
			checkoutPanel.showLogoPanel();
			hideRemoveItemPanel();
			validate();
			displayCheckoutPanel();
		}
	}

	private void payNickel()
	{
		BigDecimal value = new BigDecimal("0.05");
		Coin coin = new Coin(value);
		try {
			logic.station.coinSlot.accept(coin);
			logic.amountPaid = logic.amountPaid + coin.getValue().intValue();
		} catch (DisabledException e) {
			
			e.printStackTrace();
		} catch (OverloadException e) {
			
			e.printStackTrace();
		}
		
	}
	
	private void payDime()
	{
		BigDecimal value = new BigDecimal("0.10");
		Coin coin = new Coin(value);
		try {
			logic.station.coinSlot.accept(coin);
			logic.amountPaid = logic.amountPaid + coin.getValue().intValue();
		} catch (DisabledException e) {
			
			e.printStackTrace();
		} catch (OverloadException e) {
			
			e.printStackTrace();
		}
	}
	
	private void payQuarter()
	{
		BigDecimal value = new BigDecimal("0.25");
		Coin coin = new Coin(value);
		try {
			logic.station.coinSlot.accept(coin);
			logic.amountPaid = logic.amountPaid + coin.getValue().intValue();
		} catch (DisabledException e) {
			
			e.printStackTrace();
		} catch (OverloadException e) {
			
			e.printStackTrace();
		}
	}
	
	private void payLoonie()
	{
		BigDecimal value = new BigDecimal("1.00");
		Coin coin = new Coin(value);
		try {
			logic.station.coinSlot.accept(coin);
			logic.amountPaid = logic.amountPaid + coin.getValue().intValue();
		} catch (DisabledException e) {
			
			e.printStackTrace();
		} catch (OverloadException e) {
			
			e.printStackTrace();
		}
	}
	
	private void payToonie()
	{
		BigDecimal value = new BigDecimal("2.00");
		Coin coin = new Coin(value);
		try {
			logic.station.coinSlot.accept(coin);
			logic.amountPaid = logic.amountPaid + coin.getValue().intValue();
		} catch (DisabledException e) {
			
			e.printStackTrace();
		} catch (OverloadException e) {
			
			e.printStackTrace();
		}
	}
	
	private void payFive()
	{
		Currency currency = Currency.getInstance("CAD");
		Banknote banknote = new Banknote(currency, 5);
		try {
			logic.station.banknoteInput.accept(banknote);
			logic.amountPaid = logic.amountPaid + banknote.getValue();
		} catch (DisabledException e) {
			e.printStackTrace();
		} catch (OverloadException e) {
			e.printStackTrace();
		}
	}
	
	private void payTen()
	{
		Currency currency = Currency.getInstance("CAD");
		Banknote banknote = new Banknote(currency, 10);
		try {
			logic.station.banknoteInput.accept(banknote);
			logic.amountPaid = logic.amountPaid + banknote.getValue();
		} catch (DisabledException e) {
			e.printStackTrace();
		} catch (OverloadException e) {
			e.printStackTrace();
		}
	}
	
	private void payTwenty()
	{
		Currency currency = Currency.getInstance("CAD");
		Banknote banknote = new Banknote(currency, 20);
		try {
			logic.station.banknoteInput.accept(banknote);
			logic.amountPaid = logic.amountPaid + banknote.getValue();
		} catch (DisabledException e) {
			e.printStackTrace();
		} catch (OverloadException e) {
			e.printStackTrace();
		}
	}
	
	private void payFifty()
	{
		Currency currency = Currency.getInstance("CAD");
		Banknote banknote = new Banknote(currency, 50);
		try {
			logic.station.banknoteInput.accept(banknote);
			logic.amountPaid = logic.amountPaid + banknote.getValue();
		} catch (DisabledException e) {
			e.printStackTrace();
		} catch (OverloadException e) {
			e.printStackTrace();
		}
	}
	
	private void payHundred() 
	{
		Currency currency = Currency.getInstance("CAD");
		Banknote banknote = new Banknote(currency, 100);
		try {
			logic.station.banknoteInput.accept(banknote);
			logic.amountPaid = logic.amountPaid + banknote.getValue();
		} catch (DisabledException e) {
			e.printStackTrace();
		} catch (OverloadException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Launch the application. TO BE USED FOR TESTING ONLY!
	 */
	public static void main(String[] args) {
		
		SelfCheckoutStation scs = new SelfCheckoutStation(
				Currency.getInstance("CAD"),
				new int[] {5, 10, 15, 20, 50, 100},
				new BigDecimal[] {new BigDecimal("0.25"), new BigDecimal("0.10"), 
						new BigDecimal("0.05"), new BigDecimal("1.00"), new BigDecimal("2.00")},
				5000,
				1
				);
		
		SelfCheckoutSystemLogic testlogic = new SelfCheckoutSystemLogic(scs);
		
		PriceLookupCode plu1 = new PriceLookupCode("11111");
		PriceLookupCode plu2 = new PriceLookupCode("22222");
		PriceLookupCode plu3 = new PriceLookupCode("33333");
		PriceLookupCode plu4 = new PriceLookupCode("44444");
		PLUCodedProduct p1 = new PLUCodedProduct(plu1, "Bananas smol", new BigDecimal("700.00"));
		PLUCodedProduct p2 = new PLUCodedProduct(plu2, "Car", new BigDecimal("2.00"));
		PLUCodedProduct p3 = new PLUCodedProduct(plu3, "Monke fren", new BigDecimal("0.01"));
		PLUCodedProduct p4 = new PLUCodedProduct(plu4, "Bananas Plantain", new BigDecimal("0.99"));
		ProductDatabases.PLU_PRODUCT_DATABASE.put(plu1, p1);
		ProductDatabases.PLU_PRODUCT_DATABASE.put(plu2, p2);
		ProductDatabases.PLU_PRODUCT_DATABASE.put(plu3, p3);
		ProductDatabases.PLU_PRODUCT_DATABASE.put(plu4, p4);
		
		Barcode code1 = new Barcode(new Numeral[] {Numeral.one});
		Barcode code2 = new Barcode(new Numeral[] {Numeral.two});
		Barcode code3 = new Barcode(new Numeral[] {Numeral.three});	
		Barcode code4 = new Barcode(new Numeral[] {Numeral.four});
		BarcodedProduct p5 = new BarcodedProduct(code1, "Rice Jasmine 2kg", new BigDecimal("7.99"), 2000);
		BarcodedProduct p6 = new BarcodedProduct(code2, "Sugar Brown 2kg", new BigDecimal("8.99"), 2000);
		BarcodedProduct p7 = new BarcodedProduct(code3, "Chips Potato", new BigDecimal("0.99"), 200);
		BarcodedProduct p8 = new BarcodedProduct(code4, "Spaghetti", new BigDecimal("1.99"), 500);
		ProductDatabases.BARCODED_PRODUCT_DATABASE.put(code1, p5);
		ProductDatabases.BARCODED_PRODUCT_DATABASE.put(code2, p6);
		ProductDatabases.BARCODED_PRODUCT_DATABASE.put(code3, p7);
		ProductDatabases.BARCODED_PRODUCT_DATABASE.put(code4, p8);
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CustomerGui gui = new CustomerGui(testlogic);
					JFrame frame = new JFrame();
					frame.setContentPane(gui);
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.pack();
					frame.setVisible(true);
					gui.startup();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	

}
