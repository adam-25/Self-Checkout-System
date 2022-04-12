package seng300.software.GUI;

import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Currency;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.WindowConstants;

import org.lsmr.selfcheckout.Banknote;
import org.lsmr.selfcheckout.Barcode;
import org.lsmr.selfcheckout.BarcodedItem;
import org.lsmr.selfcheckout.Card;
import org.lsmr.selfcheckout.Coin;
import org.lsmr.selfcheckout.Item;
import org.lsmr.selfcheckout.Numeral;
import org.lsmr.selfcheckout.PLUCodedItem;
import org.lsmr.selfcheckout.PriceLookupCode;
import org.lsmr.selfcheckout.devices.DisabledException;
import org.lsmr.selfcheckout.devices.OverloadException;
import org.lsmr.selfcheckout.devices.ReceiptPrinter;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.external.CardIssuer;
import org.lsmr.selfcheckout.external.ProductDatabases;
import org.lsmr.selfcheckout.products.BarcodedProduct;
import org.lsmr.selfcheckout.products.PLUCodedProduct;
import org.lsmr.selfcheckout.products.Product;

import seng300.software.PLUCodedWeightProduct;
import seng300.software.SelfCheckoutSystemLogic;
import seng300.software.exceptions.ProductNotFoundException;

public class CustomerGui extends JPanel implements DisableableGui {

	/**
	 *
	 */
	private static final long serialVersionUID = 7637105711156584933L;

	private GuiDisabledPanel disabledPanel;
	private StationUnavailablePanel unavailablePanel;
	private StationReadyPanel readyPanel;
	private CustomerCheckoutPanel checkoutPanel;
	public CustomerCheckoutPanel getCheckoutPanel() {
		return checkoutPanel;
	}

	private ProductLookupPanel lookupPanel;
	private CustomerPaymentPanel paymentPanel;
	private EnterMembershipPanel membershipPanel;
	private CoinPaymentPanel payCoinPanel;
	private BanknotePaymentPanel payBanknotePanel;
	private ThankYouPanel thankYouPanel;
	private BaggingAreaPanel baggingAreaPanel = null;
	private EnterPlasticBagsPanel plasticBagsPanel;
	private PlaceItemPanel placeItemPopup;
	private RemoveFromBaggingAreaPanel rmFromBaggingPopup;

	private SelfCheckoutSystemLogic logic;
	private boolean weightChecking = true;
	private boolean paymentStarted = false;
	private Item lastAddedItem = null;
	private String lastItemDescription = "";
	private Map<Product, Item> itemToRemove = null;
	private String itemToRemoveDescription = "";
	
	// Add membership simulation
	private Card membershipCard = null;
	private CardIssuer stub = null;

	/**
	 * Create the panel.
	 */
	public CustomerGui(SelfCheckoutSystemLogic logic) {
		setLayout(new CardLayout(0, 0));

		this.logic = logic;

		unavailablePanel = new StationUnavailablePanel();
		readyPanel = new StationReadyPanel();
		readyPanel.startButton.addActionListener(e -> displayCheckoutPanel());

		checkoutPanel = new CustomerCheckoutPanel();
		checkoutPanel.searchProductBtn.addActionListener(e -> displayProductLookupPanel());
		checkoutPanel.useOwnBagsBtn.addActionListener(e -> useOwnBagsClicked());
		checkoutPanel.payBtn.addActionListener(e -> displayPlasticBagsPanel());
		checkoutPanel.pluEntryPinPad.padEnterBtn.addActionListener(e -> getPluCode());
		checkoutPanel.pluEntryPinPad.setPlaceholder(-1);
		checkoutPanel.viewBaggingAreaBtn.addActionListener(e -> displayBaggingAreaPanel());
		checkoutPanel.removeItemBtn.addActionListener(e -> displayCustRemoveItemPanel());
		checkoutPanel.scanItemBtn.addActionListener(e -> scanRandomItem());
		checkoutPanel.doNotBagBtn.addActionListener(e -> doNotBagNextAddedItem());

		lookupPanel = new ProductLookupPanel();
		lookupPanel.returnButton.addActionListener(e -> displayCheckoutPanel());
		for (KeyboardButton btn : lookupPanel.keyboardBtns) {
			btn.addActionListener(e -> push(btn));
		}

		paymentPanel = new CustomerPaymentPanel();
		paymentPanel.returnToCheckoutBtn.addActionListener(e -> returnToCheckoutClicked());
		paymentPanel.addMembershipBtn.addActionListener(e -> displayMembershipPanel());
		paymentPanel.payWithCoinBtn.addActionListener(e -> {
			logic.checkout.chooseCoin();
			displayPayCoinPanel();
		});
		paymentPanel.payWithCashBtn.addActionListener(e -> {
			logic.checkout.chooseBanknote();
			displayPayCashPanel();
		});
		paymentPanel.payWithCreditBtn.addActionListener(e -> payWithCredit());
		paymentPanel.payWithDebitBtn.addActionListener(e -> payWithDebit());
		paymentPanel.payWithGiftCardBtn.addActionListener(e -> payWithGift());

		payCoinPanel = new CoinPaymentPanel();
		payCoinPanel.doneBtn.addActionListener(e -> {
			logic.checkout.completeCurrentPaymentMethod();
			displayPaymentPanel();
		});
		Coin.DEFAULT_CURRENCY = Currency.getInstance("CAD");
		payCoinPanel.dimeBtn.addActionListener(e -> payDime());
		payCoinPanel.loonieBtn.addActionListener(e -> payLoonie());
		payCoinPanel.nickelBtn.addActionListener(e -> payNickel());
		payCoinPanel.quarterBtn.addActionListener(e -> payQuarter());
		payCoinPanel.toonieBtn.addActionListener(e -> payToonie());

		payBanknotePanel = new BanknotePaymentPanel();
		payBanknotePanel.doneBtn.addActionListener(e -> {
			logic.checkout.completeCurrentPaymentMethod();
			displayPaymentPanel();
		});
		payBanknotePanel.fiveBtn.addActionListener(e -> payFive());
		payBanknotePanel.hundredBtn.addActionListener(e -> payHundred());
		payBanknotePanel.fiftyBtn.addActionListener(e -> payFifty());
		payBanknotePanel.twentyBtn.addActionListener(e -> payTwenty());
		payBanknotePanel.tenBtn.addActionListener(e -> payTen());

		membershipPanel = new EnterMembershipPanel();
		membershipPanel.cancelBtn.addActionListener(e -> displayPaymentPanel());
		membershipPanel.pinPad.padEnterBtn
				.addActionListener(e -> addMembershipToCheckout(membershipPanel.pinPad.getValue()));

		plasticBagsPanel = new EnterPlasticBagsPanel();
		plasticBagsPanel.pinPad.padEnterBtn.addActionListener(e -> {
			String value = plasticBagsPanel.pinPad.getValue();
			int num = value == "" ? 0 : Integer.parseInt(value);
			enterNumPlasticBags(num);
			paymentStarted = true;
		});
		plasticBagsPanel.returnToCheckoutBtn.addActionListener(e -> displayCheckoutPanel());

		thankYouPanel = new ThankYouPanel();
		placeItemPopup = new PlaceItemPanel();
		placeItemPopup.placeItemBtn.addActionListener(e -> placeItem());
		rmFromBaggingPopup = new RemoveFromBaggingAreaPanel();
		disabledPanel = new GuiDisabledPanel();
		
		add(unavailablePanel);
		add(readyPanel);
		add(checkoutPanel);
		add(lookupPanel);
		add(paymentPanel);
		add(membershipPanel);
		add(payCoinPanel);
		add(payBanknotePanel);
		add(plasticBagsPanel);
		add(placeItemPopup);
		add(rmFromBaggingPopup);
		add(thankYouPanel);
		add(disabledPanel);
		disabledPanel.setVisible(false);
		shutdown();
	}
	
	private boolean disabled = false;
	
	public void disableGui()
	{
		if (!disabled)
		{
			disabledPanel.setVisible(true);
			disabled = true;
		}
	}
	
	public void enableGui()
	{
		if (disabled)
		{
			disabledPanel.setVisible(false);
			disabled = false;
		}
	}

	/**
	 * Wrapper method to turn on the station after it is shutdown.
	 */
	public void startup() {
//		logic.turnOnStation();
		readyPanel.setVisible(true);
		unavailablePanel.setVisible(false);
	}
	
	/**
	 * Wrapper method to shutdown the station.
	 */
	public void shutdown() {
		unavailablePanel.setVisible(true);
		readyPanel.setVisible(false);
		checkoutPanel.setVisible(false);
		lookupPanel.setVisible(false);
		paymentPanel.setVisible(false);
		membershipPanel.setVisible(false);
		payCoinPanel.setVisible(false);
		payBanknotePanel.setVisible(false);		
		plasticBagsPanel.setVisible(false);
		rmFromBaggingPopup.setVisible(false);
		// Destroy any dynamically-generated panels
		hideBaggingAreaPanel();
		hideRemoveItemPanel();
		validate();
		
//		logic.turnOffStation();
	}

	/**
	 * Resets system between customers after successful payment.
	 */
	public void reset()
	{
		logic.reset();
		weightChecking = true;
		paymentStarted = false;
		lastAddedItem = null;
		lastItemDescription = "";
		itemToRemove = null;
		itemToRemoveDescription = "";
		itemToRemoveIndexInLog = -1;
		membershipCard = null;
		stub = null;
		
		checkoutPanel.setVisible(false);
		checkoutPanel.itemLogPanel.reset();
		readyPanel.setVisible(true);
		hideBaggingAreaPanel();
	}

	/**
	 * Displays the main checkout (scan and bag) panel.
	 */
	private void displayCheckoutPanel() {
		if (paymentStarted)
		{
			logic.addItemAfterCheckoutStart();
		}
		logic.checkout.update(logic.cart.getCartTotal());
		checkoutPanel.showLogoPanel();
		checkoutPanel.setVisible(true);
		readyPanel.setVisible(false);
		lookupPanel.reset();
		lookupPanel.setVisible(false);
		paymentPanel.setVisible(false);
		plasticBagsPanel.setVisible(false);
		placeItemPopup.setVisible(false);
		rmFromBaggingPopup.setVisible(false);
		hideBaggingAreaPanel();
		hideRemoveItemPanel();
		validate();
	}
	
	/**
	 * Displays product lookup panel.
	 */
	private void displayProductLookupPanel() {
		lookupPanel.setVisible(true);
		checkoutPanel.setVisible(false);
	}
	
	/**
	 * Displays the bagging area panel.
	 */
	private void displayBaggingAreaPanel() {
		ArrayList<String> descriptions = new ArrayList<>();
		for (Product i : logic.getBaggedProducts()) {
			if (i instanceof BarcodedProduct) {
				descriptions.add(((BarcodedProduct) i).getDescription());
			} else if (i instanceof PLUCodedWeightProduct) {
				descriptions.add(((PLUCodedWeightProduct) i).getDescription());
			}
		}
		baggingAreaPanel = new BaggingAreaPanel(descriptions);
		baggingAreaPanel.returnButton.addActionListener(e -> displayCheckoutPanel());
		baggingAreaPanel.deleteButton.addActionListener(e -> {
			itemToRemove = new HashMap<>();
			int i = baggingAreaPanel.getCurrentSelectedIndex();
			Product p = logic.getBaggedProducts().get(i);
			itemToRemove.put(p, logic.getBaggingArea().get(i));
			removeItemFromBaggingArea(itemToRemove);
			itemToRemove = null;
			displayCheckoutPanel();
		});
		add(baggingAreaPanel);
		baggingAreaPanel.setVisible(true);
		checkoutPanel.setVisible(false);
	}
	
	/**
	 * Displays panel for customer to enter number of 
	 * plastic bags used.
	 */
	private void displayPlasticBagsPanel() {
		if (paymentStarted) {
			plasticBagsPanel.promptLbl.setText("Enter additional plastic bags used.");
		}
		plasticBagsPanel.pinPad.clear();
		plasticBagsPanel.setVisible(true);
		checkoutPanel.setVisible(false);
	}
	
	/**
	 * Displays panel with various payment options and information.
	 */
	private void displayPaymentPanel() {
		BigDecimal cartTotal = logic.cart.getCartTotal();
		BigDecimal amountPaid = logic.checkout.getTotalAmountPaid();
		if (cartTotal.compareTo(amountPaid) > 0) {
			paymentPanel.setBillTotal(cartTotal);
			paymentPanel.setTotalPaid(amountPaid);
			paymentPanel.setAmountOwing(cartTotal.subtract(amountPaid));
			paymentPanel.setVisible(true);
			plasticBagsPanel.setVisible(false);
			membershipPanel.setVisible(false);
			payCoinPanel.setVisible(false);
			payBanknotePanel.setVisible(false);
		} else {
			if (cartTotal.compareTo(BigDecimal.ZERO) > 0) {
				logic.checkout.completeCurrentPaymentMethod();
			}
			displayThankYouPanel();
		}

	}
	
	/**
	 * Displays panel that has options to simulate customer
	 * entering different coins.
	 */
	private void displayPayCoinPanel() {
		payCoinPanel.setVisible(true);
		paymentPanel.setVisible(false);
	}
	
	/**
	 * Displays panel that has options to simulate customer
	 * entering different banknotes.
	 */
	private void displayPayCashPanel() {
		payBanknotePanel.setVisible(true);
		paymentPanel.setVisible(false);
	}

	/**
	 * Displays panel for customer input their membership number.
	 */
	private void displayMembershipPanel() {
		membershipPanel.setVisible(true);
		paymentPanel.setVisible(false);
	}

	/**
	 * Displays thank you panel after payment completed.
	 */
	private void displayThankYouPanel() {
		BigDecimal cartTotal = logic.cart.getCartTotal();
		BigDecimal amountPaid = logic.checkout.getTotalAmountPaid();
		thankYouPanel.setChangeDueLabel(amountPaid.subtract(cartTotal));
		thankYouPanel.validate();
		thankYouPanel.setVisible(true);
		paymentPanel.setVisible(false);
		payCoinPanel.setVisible(false);
		payBanknotePanel.setVisible(false);
		// https://stackoverflow.com/a/22997093
		ActionListener task = e -> reset();
		Timer timer = new Timer(3000, task);
		timer.setRepeats(false);
		timer.start();
	}

	/**
	 * Called after a customer adds an item
	 * from the cart; forces them to also place 
	 * that item in the bagging area (unless they 
	 * indicated they do not want to place the item
	 * in the bagging area **before** scanning that item).
	 */
	private void displayPlaceItemPopup() {
		placeItemPopup.itemDescriptionLabel.setText(lastItemDescription);
		placeItemPopup.validate();
		placeItemPopup.setVisible(true);
		checkoutPanel.setVisible(false);
		lookupPanel.setVisible(false);
		validate();
	}

	/**
	 * Called after a customer removes an item
	 * from the cart; forces them to also remove 
	 * that item from the bagging area.
	 */
	private void displayRemoveFromBaggingPopup(Map<Product, Item> itemToRemove) {
		rmFromBaggingPopup.itemDescriptionLabel.setText(itemToRemoveDescription);
		for(ActionListener l : rmFromBaggingPopup.rmItemBtn.getActionListeners() ) {
			rmFromBaggingPopup.rmItemBtn.removeActionListener(l);
	    }
		rmFromBaggingPopup.rmItemBtn.addActionListener(e -> removeFromBaggingAfterRemoveFromCart(itemToRemove));
		rmFromBaggingPopup.setVisible(true);
		checkoutPanel.setVisible(false);		
	}
	
	/**
	 * Destroys dynamically-generated bagging area panel.
	 */
	private void hideBaggingAreaPanel()
	{
		if (baggingAreaPanel != null) {
			baggingAreaPanel.setVisible(false);
			remove(baggingAreaPanel);
			validate();
			baggingAreaPanel = null;
		}
	}
	
	/**
	 * Destroys dynamically generated remove item panel.
	 */
	private void hideRemoveItemPanel() {
		if (removeItemPanel != null) {
			checkoutPanel.removeFromLeftPanel(removeItemPanel);
			validate();
			removeItemPanel = null;
		}
	}
	
	/* ************************************************************* */
	/* *********** LOGIC RELATED FUNCTIONS ************************* */
	/* ************************************************************* */

	private void useOwnBagsClicked() {
		checkoutPanel.showAttendantNotifiedPanel();
		logic.ownBagBlock();
		checkoutPanel.showLogoPanel();
	}
	
	private void scanRandomItem() {
		// get random barcode from product database
		Random rand = new Random();
		int index = rand.nextInt(ProductDatabases.BARCODED_PRODUCT_DATABASE.size());
		Barcode code = (Barcode) ProductDatabases.BARCODED_PRODUCT_DATABASE.keySet().toArray()[index];
		BarcodedProduct p = ProductDatabases.BARCODED_PRODUCT_DATABASE.get(code);
		BarcodedItem item = new BarcodedItem(code, p.getExpectedWeight());
		// scan until product added successfully
		int oldSize = logic.getCart().getProducts().size();
		while (logic.getCart().getProducts().size() <= oldSize) {
			logic.station.mainScanner.scan(item);
		}
		lastAddedItem = item;
		lastItemDescription = ProductDatabases.BARCODED_PRODUCT_DATABASE.get(code).getDescription();
		checkoutPanel.itemLogPanel.addItem(lastItemDescription,
				ProductDatabases.BARCODED_PRODUCT_DATABASE.get(code).getPrice());
		BigDecimal cartTotal = logic.getCart().getCartTotal();
		if (paymentStarted)
		{
			cartTotal = cartTotal.subtract(new BigDecimal(0.05 * logic.cart.getBags()));
		}
		checkoutPanel.itemLogPanel.setBillTotalValue(cartTotal);
		if (weightChecking) {
			displayPlaceItemPopup();
		}
	}

	private void placeItem() { //changed! Ibrahim 1030
		if (lastAddedItem != null) {
			logic.station.baggingArea.add(lastAddedItem);
			if (lastAddedItem instanceof BarcodedItem)
			{
				logic.getBaggingArea().add((BarcodedItem)lastAddedItem);
			}
			else
			{
				logic.getBaggingArea().add((PLUCodedItem)lastAddedItem);
			}
		}
		displayCheckoutPanel();
	}
	
	private void getPluCode() {
		String value = checkoutPanel.pluEntryPinPad.getValue();
		if (!value.isEmpty()) {
			try {
				PriceLookupCode code = new PriceLookupCode(value);
				checkoutPanel.hidePluEntryPanelErrorMsg();
				addPluProductToCart(code);
				checkoutPanel.pluEntryPinPad.clear();
				checkoutPanel.showLogoPanel();
			} catch (Exception e) {
				checkoutPanel.showPluEntryPanelErrorMsg();
				checkoutPanel.pluEntryPinPad.clear();
			}
		}
		// ignore empty searches
	}

	private void addPluProductToCart(PriceLookupCode code) throws ProductNotFoundException {
		if (ProductDatabases.PLU_PRODUCT_DATABASE.containsKey(code)) {
			// Create random plucoded product for testing
			double maxScaleWeight = logic.station.scanningArea.getWeightLimit();
			Random rand = new Random();
			double weight = rand.nextDouble() * maxScaleWeight + logic.getBaggingAreaSensitivity();
			PLUCodedItem item = new PLUCodedItem(code, weight);
			// add product to cart (no exception should ever be thrown)
			if (weightChecking) {
				logic.getCart().addPLUCodedProductToCart(code, item.getWeight());
				lastAddedItem = item;
				lastItemDescription = ProductDatabases.PLU_PRODUCT_DATABASE.get(code).getDescription();
				BigDecimal pricePerKilo = ProductDatabases.PLU_PRODUCT_DATABASE.get(code).getPrice();
				checkoutPanel.itemLogPanel.addItem(lastItemDescription,
						pricePerKilo.multiply(new BigDecimal(item.getWeight() / 1000.0)));
				BigDecimal cartTotal = logic.getCart().getCartTotal();
				if (paymentStarted)
				{
					cartTotal = cartTotal.subtract(new BigDecimal(0.05 * logic.cart.getBags()));
				}
				checkoutPanel.itemLogPanel.setBillTotalValue(cartTotal);
				displayPlaceItemPopup();
			} else {
				logic.getCart().addPLUCodedProductToCartNoWeight(code, item.getWeight());
				lastAddedItem = item;
				lastItemDescription = ProductDatabases.PLU_PRODUCT_DATABASE.get(code).getDescription();
				logic.checkBagging();
				weightChecking = true;
				BigDecimal pricePerKilo = ProductDatabases.PLU_PRODUCT_DATABASE.get(code).getPrice();
				checkoutPanel.itemLogPanel.addItem(lastItemDescription,
						pricePerKilo.multiply(new BigDecimal(item.getWeight() / 1000.0)));
				BigDecimal cartTotal = logic.getCart().getCartTotal();
				if (paymentStarted)
				{
					cartTotal = cartTotal.subtract(new BigDecimal(0.05 * logic.cart.getBags()));
				}
				checkoutPanel.itemLogPanel.setBillTotalValue(cartTotal);
				displayCheckoutPanel();
			}
		} else {
			throw new ProductNotFoundException();
		}
	}
	
	private void push(KeyboardButton btn) {
		KeyboardKey key = btn.getKey();
		String searchText = lookupPanel.getSearchText();
		if (key == KeyboardKey.BACK) {
			if (!searchText.isEmpty()) {
				searchText = searchText.substring(0, searchText.length() - 1);
				lookupPanel.setSearchText(searchText);
				lookupProduct(searchText);
			}
			// ignore attempts to backspace when search field empty
		} else if (key == KeyboardKey.CLEAR) {
			lookupPanel.reset();
		} else if (key != KeyboardKey.ENTER) {
			searchText += key.getValue();
			lookupPanel.setSearchText(searchText);
			lookupProduct(searchText);
		}
	}
	
	private void lookupProduct(String searchText) {
		if (!searchText.isEmpty()) {
			List<PLUCodedProduct> results = logic.productLookUp(searchText);
			List<LookupResultButton> btns = new ArrayList<>();
			for (PLUCodedProduct p : results) {
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

	private void doNotBagNextAddedItem() {
		weightChecking = false;
		logic.ignoreBagging();
	}

	private void removeItemFromBaggingArea(Map<Product, Item> itemToRemove) //changed Ibrahim 1030
	{
		Product p = ((Product)itemToRemove.keySet().toArray()[0]);
		logic.selectItemToRemove(p); // should work for barcoded and plu coded products
		logic.station.baggingArea.remove(itemToRemove.get(p));
		logic.getBaggingArea().remove(itemToRemove.get(p));
		logic.returnToNormalBaggingOperation();
	}
	
	private void enterNumPlasticBags(int numPlasticBags) {
		logic.getCart().addPlasticBags(numPlasticBags);
		logic.wantsToCheckout();
		displayPaymentPanel();
	}
	
	private void addMembershipToCheckout(String input) {
		membershipCard = new Card("Membership", input, "Customer Name", null, null, false, false);
		stub = new CardIssuer("Our company");
		
		Calendar c = Calendar.getInstance(); //gets the next day for expiry
		c.setTime(new Date()); 
		c.add(Calendar.DATE, 1);
		
		stub.addCardData(input, "Customer Name", c, "666", new BigDecimal("1"));
		
		logic.checkout.chooseMembership(stub);
		// swipe until data is read
		boolean swiped = false;
		while (!swiped) {
			try {
				logic.station.cardReader.swipe(membershipCard);
				swiped = true;
			} catch (IOException e) {

			}
		}
		logic.checkout.completeMembershipRecognition();
	}

	private void returnToCheckoutClicked() {
		logic.addItemAfterCheckoutStart();
		displayCheckoutPanel();
	}

	private CustomerRemoveItemPanel removeItemPanel = null;
	private int itemToRemoveIndexInLog = -1;

	private void displayCustRemoveItemPanel() {
		removeItemPanel = new CustomerRemoveItemPanel();
		ArrayList<String> descriptions = new ArrayList<>();
		for (int i = 0; i < logic.getCart().getProducts().size(); i++) {
			Product p = logic.getCart().getProducts().get(i);
			if (p instanceof BarcodedProduct) {
				descriptions.add(((BarcodedProduct) p).getDescription());
				removeItemPanel.addItem(p, descriptions.get(descriptions.size() - 1), p.getPrice());
				final int index = i;
				removeItemPanel.logItemRemoveBtns.get(i).addActionListener(e -> {
					itemToRemove = new HashMap<>();
					itemToRemoveIndexInLog = index;
					if (logic.getBaggedProducts().contains(p))
					{
						int j = logic.getBaggedProducts().indexOf(p);
						itemToRemove.put(p, logic.getBaggingArea().get(j));
					}
					else
					{
						itemToRemove.put(p, null);
					}
					itemToRemoveDescription = descriptions.get(index);
					removeItemFromCart(itemToRemove);
					itemToRemove = null;
				});
			} else {
				descriptions.add(((PLUCodedProduct) p).getDescription());
				removeItemPanel.addItem(p, descriptions.get(descriptions.size() - 1), p.getPrice());
				final int index = i;
				removeItemPanel.logItemRemoveBtns.get(i).addActionListener(e -> {
					itemToRemove = new HashMap<>();
					itemToRemoveIndexInLog = index;
					if (logic.getBaggedProducts().contains(p))
					{
						int j = logic.getBaggedProducts().indexOf(p);
						itemToRemove.put(p, logic.getBaggingArea().get(j));
					}
					else
					{
						itemToRemove.put(p, null);
					}
					itemToRemoveDescription = descriptions.get(index);
					removeItemFromCart(itemToRemove);
					itemToRemove = null;
				});
			}
			
		}
		checkoutPanel.setLeftPanel(removeItemPanel);
	}

	public void removeItemFromCart(Map<Product, Item> itemToRemove) {
		Product tmp = ((Product)itemToRemove.keySet().toArray()[0]);
		if (tmp instanceof BarcodedProduct) {
			try {
				logic.getCart().removeFromCart((BarcodedProduct)tmp);
			} catch (ProductNotFoundException e1) {
				// Should never execute
			}
		} else {
			try {
				if (itemToRemove.get(tmp) != null) {
					logic.getCart().removeFromCart(new PLUCodedWeightProduct((PLUCodedProduct)tmp,
							itemToRemove.get(tmp).getWeight()));
				} else {
					logic.getCart().removeFromCart(new PLUCodedWeightProduct((PLUCodedProduct) tmp, 0));
				}

			} catch (ProductNotFoundException e1) {
				// Should never execute
			}
		}
		if (itemToRemove.get(tmp) != null) {
			displayRemoveFromBaggingPopup(itemToRemove);
		} else {
			updateGuiCart();
		}
	}

	private void removeFromBaggingAfterRemoveFromCart(Map<Product, Item> itemToRemove) {
		removeItemFromBaggingArea(itemToRemove);
		updateGuiCart();
	}

	private void updateGuiCart() {
		if (itemToRemoveIndexInLog >= 0)
		{
			checkoutPanel.itemLogPanel.removeLogItem(itemToRemoveIndexInLog);
			BigDecimal cartTotal = logic.getCart().getCartTotal();
			if (paymentStarted)
			{
				cartTotal = cartTotal.subtract(BigDecimal.valueOf(0.05 * logic.cart.getBags()));
			}
			checkoutPanel.itemLogPanel.setBillTotalValue(cartTotal);
			itemToRemove = null;
			checkoutPanel.showLogoPanel();
			hideRemoveItemPanel();
			validate();
			displayCheckoutPanel();
		}
	}

	/*
	 * PAYMENT METHODS
	 *
	 * @author Simon
	 */

	BigDecimal amountPaidWithCoin = new BigDecimal("0.00");
	BigDecimal amountPaidWithBanknote = new BigDecimal("0.00");

	private void payNickel() {
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

	private void payDime() {
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

	private void payQuarter() {
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

	private void payLoonie() {
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

	private void payToonie() {
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

	private void payFive() {
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

	private void payTen() {
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

	private void payTwenty() {
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

	private void payFifty() {
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

	private void payHundred() {
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

	private void payWithCredit() { //changed ----------------
		credit1 = new Card("Credit", "11111", "Customer", "123", null, false, false);
		CardIssuer stub = new CardIssuer("The Bank");
		
		Calendar c = Calendar.getInstance(); //gets the next day for expiry
		c.setTime(new Date()); 
		c.add(Calendar.DATE, 1);
		
		stub.addCardData("11111", "Customer", c, "123", logic.cart.getCartTotal());
		
		logic.checkout.chooseCredit(stub, logic.cart.getCartTotal());
		boolean swiped = false;
		while (!swiped) {
			try {
				logic.station.cardReader.swipe(credit1);
				swiped = true;
			} catch (IOException e) {
				// Ignore
			}
		}
		logic.checkout.completeCurrentPaymentMethod();
		logic.checkout.finishPayment();
		displayThankYouPanel();
	}

	Card debit1;

	private void payWithDebit() { //changed ----------------
		debit1 = new Card("Debit", "11111", "Customer", "123", null, false, false);
		CardIssuer stub = new CardIssuer("The Bank");
		
		Calendar c = Calendar.getInstance(); //gets the next day for expiry
		c.setTime(new Date()); 
		c.add(Calendar.DATE, 1);
		
		stub.addCardData("11111", "Customer", c, "123", logic.cart.getCartTotal());
		
		logic.checkout.chooseDebit(stub, logic.cart.getCartTotal());
		boolean swiped = false;
		while (!swiped) {
			try {
				logic.station.cardReader.swipe(debit1);
				swiped = true;
			} catch (IOException e) {
				// Ignore
			}
		}
		logic.checkout.completeCurrentPaymentMethod();
		logic.checkout.finishPayment();
		displayThankYouPanel();
	}

	private void payWithGift() {
		// generate random gift card number
		String giftCardNumber = "123456789012";
		logic.checkout.setGiftNumber(giftCardNumber);
		logic.checkout.finishPayment();
		displayThankYouPanel();
	}
}
