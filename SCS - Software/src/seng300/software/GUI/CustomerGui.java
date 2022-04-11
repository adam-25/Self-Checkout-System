package seng300.software.GUI;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

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
import org.lsmr.selfcheckout.devices.ReceiptPrinter;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.external.ProductDatabases;
import org.lsmr.selfcheckout.products.BarcodedProduct;
import org.lsmr.selfcheckout.products.PLUCodedProduct;
import org.lsmr.selfcheckout.products.Product;

import seng300.software.BankStub;
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
	private ThankYouPanel thankYouPanel;
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
		paymentPanel.payWithCoinBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				logic.checkout.chooseCoin();
				displayPayCoinPanel();
			}
		});
		paymentPanel.payWithCashBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				logic.checkout.chooseBanknote();
				displayPayCashPanel();
			}
		});
		paymentPanel.payWithCreditBtn.addActionListener(e -> payWithCredit());
		paymentPanel.payWithDebitBtn.addActionListener(e -> payWithDebit());
//		paymentPanel.payWithGiftCardBtn.addActionListener(e -> payWithGift()); TODO
		
		payCoinPanel = new CoinPaymentPanel();
		payCoinPanel.doneBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				logic.checkout.completeCurrentPaymentMethod();
				displayPaymentPanel();
			}
		});
		Coin.DEFAULT_CURRENCY = Currency.getInstance("CAD");
		payCoinPanel.dimeBtn.addActionListener(e -> payDime());
		payCoinPanel.loonieBtn.addActionListener(e -> payLoonie());
		payCoinPanel.nickelBtn.addActionListener(e -> payNickel());
		payCoinPanel.quarterBtn.addActionListener(e -> payQuarter());
		payCoinPanel.toonieBtn.addActionListener(e -> payToonie());
		
		payBanknotePanel = new BanknotePaymentPanel();
		payBanknotePanel.doneBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				logic.checkout.completeCurrentPaymentMethod();
				displayPaymentPanel();
			}
		});
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
		
		thankYouPanel = new ThankYouPanel();
		
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
		add(thankYouPanel);
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
//		logic.ownBagBlock(); TODO
//		checkoutPanel.showLogoPanel(); TODO
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
	
	public void displayThankYouPanel()
	{
		thankYouPanel.setChangeDueLabel(logic.checkout.getTotalchange());
		thankYouPanel.setVisible(true);
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
		// https://stackoverflow.com/a/22997093
		ActionListener task = new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	        	reset();
	        }
        };
	    Timer timer = new Timer(3000, task);
	    timer.setRepeats(false);
	    timer.start();
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
		BigDecimal cartTotal = logic.cart.getCartTotal();
		BigDecimal amountPaid = logic.checkout.getTotalAmountPaid();
		if (cartTotal.compareTo(amountPaid) > 0)
		{
			paymentPanel.setBillTotal(cartTotal);
			paymentPanel.setTotalPaid(amountPaid);
			paymentPanel.setAmountOwing(cartTotal.subtract(amountPaid));
			
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
		else
		{
			if (cartTotal.compareTo(BigDecimal.ZERO) > 0)
			{
				logic.checkout.completeCurrentPaymentMethod();
			}
			displayThankYouPanel();
		}
		
		
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
								if (logic.getBaggedProducts().contains(temp))
								{
									logic.getCart().removeFromCart(new PLUCodedWeightProduct((PLUCodedProduct)temp, baggedItems.get(i).getWeight()));
								}
								else
								{
									logic.getCart().removeFromCart(new PLUCodedWeightProduct((PLUCodedProduct)temp, 0));
								}
								
							} catch (ProductNotFoundException e1) {
								// Should never execute
							}
						}
//						removeItemfromBaggingArea(i);
						if (logic.getBaggedProducts().contains(temp))
						{
							displayRemoveFromBaggingPanel();
						}
						
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
	
	/* PAYMENT METHODS
	 * @author Simon
	 */
	
	BigDecimal amountPaidWithCoin = new BigDecimal("0.00");
	BigDecimal amountPaidWithBanknote = new BigDecimal("0.00");
	
	private void payNickel()
	{
		BigDecimal value = new BigDecimal("0.05");
		Coin coin = new Coin(value);
		try {
			logic.station.coinSlot.accept(coin);
			amountPaidWithCoin = amountPaidWithCoin.add(coin.getValue());
			payCoinPanel.setTotalPayWithCoin(amountPaidWithCoin);
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
			amountPaidWithCoin = amountPaidWithCoin.add(coin.getValue());
			payCoinPanel.setTotalPayWithCoin(amountPaidWithCoin);
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
			amountPaidWithCoin = amountPaidWithCoin.add(coin.getValue());
			payCoinPanel.setTotalPayWithCoin(amountPaidWithCoin);
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
			amountPaidWithCoin = amountPaidWithCoin.add(coin.getValue());
			payCoinPanel.setTotalPayWithCoin(amountPaidWithCoin);
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
			amountPaidWithCoin = amountPaidWithCoin.add(coin.getValue());
			payCoinPanel.setTotalPayWithCoin(amountPaidWithCoin);
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
			amountPaidWithBanknote = amountPaidWithBanknote.add(BigDecimal.valueOf(banknote.getValue()));
			payBanknotePanel.setTotalPayWithBanknote(amountPaidWithBanknote);
			
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
			amountPaidWithBanknote = amountPaidWithBanknote.add(BigDecimal.valueOf(banknote.getValue()));
			payBanknotePanel.setTotalPayWithBanknote(amountPaidWithBanknote);
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
			amountPaidWithBanknote = amountPaidWithBanknote.add(BigDecimal.valueOf(banknote.getValue()));
			payBanknotePanel.setTotalPayWithBanknote(amountPaidWithBanknote);
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
			amountPaidWithBanknote = amountPaidWithBanknote.add(BigDecimal.valueOf(banknote.getValue()));
			payBanknotePanel.setTotalPayWithBanknote(amountPaidWithBanknote);
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
			amountPaidWithBanknote = amountPaidWithBanknote.add(BigDecimal.valueOf(banknote.getValue()));
			payBanknotePanel.setTotalPayWithBanknote(amountPaidWithBanknote);
		} catch (DisabledException e) {
			e.printStackTrace();
		} catch (OverloadException e) {
			e.printStackTrace();
		}
	}
  
  /*
   * PAYMENT ALY
   */
  Card credit1;
	private void payWithCredit()
	{
		credit1 = new Card("Credit", "11111", "Customer", null, null, false, false);
		BankStub stub = new BankStub();
		logic.checkout.chooseCredit(stub, logic.cart.getCartTotal());
		boolean swiped = false;
		while (!swiped) {
			try {
				logic.station.cardReader.swipe(credit1);
				swiped = true;
			} catch (IOException e) {
				//Ignore
			}
		}
		logic.checkout.completeCurrentPaymentMethod();
		logic.checkout.finishPayment();
		displayThankYouPanel();
	}
	
	Card debit1;
	private void payWithDebit()
	{
		debit1 = new Card("Debit", "11111", "Customer", null, null, false, false);
		BankStub stub = new BankStub();
		logic.checkout.chooseDebit(stub, logic.cart.getCartTotal());
		boolean swiped = false;
		while (!swiped) {
			try {
				logic.station.cardReader.swipe(debit1);
				swiped = true;
			} catch (IOException e) {
				//Ignore
			}
		}
		logic.checkout.completeCurrentPaymentMethod();
		logic.checkout.finishPayment();
		displayThankYouPanel();
	}
	
	/*
	Card gift1;
	private void payWithGift()
	{
		gift1 = new Card("Gift", "11111", "Customer", null, null, false, false);
		BankStub stub = new BankStub();
		logic.checkout.chooseGift(stub, logic.cart.getCartTotal());
		boolean swiped = false;
		while (!swiped) {
			try {
				logic.station.cardReader.swipe(gift1);
				swiped = true;
			} catch (IOException e) {
				//Ignore
			}
		}
		logic.checkout.completeCurrentPaymentMethod();
		logic.checkout.finishPayment();
		displayCheckoutCompletePanel();
	}
	*/
  
	/**
	 * Launch the application. TO BE USED FOR TESTING ONLY!
	 * @throws OverloadException 
	 */
	public static void main(String[] args) throws OverloadException {
		
		SelfCheckoutStation scs = new SelfCheckoutStation(
				Currency.getInstance("CAD"),
				new int[] {5, 10, 15, 20, 50, 100},
				new BigDecimal[] {new BigDecimal("0.25"), new BigDecimal("0.10"), 
						new BigDecimal("0.05"), new BigDecimal("1.00"), new BigDecimal("2.00")},
				5000,
				1
				);
		scs.printer.addInk(ReceiptPrinter.MAXIMUM_INK);
		scs.printer.addPaper(ReceiptPrinter.MAXIMUM_PAPER);
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
