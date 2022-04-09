package seng300.software.GUI;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.lsmr.selfcheckout.InvalidArgumentSimulationException;
import org.lsmr.selfcheckout.PLUCodedItem;
import org.lsmr.selfcheckout.PriceLookupCode;
import org.lsmr.selfcheckout.devices.OverloadException;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.external.ProductDatabases;
import org.lsmr.selfcheckout.products.PLUCodedProduct;
import org.lsmr.selfcheckout.products.Product;

import seng300.software.SelfCheckoutSystemLogic;
import seng300.software.GUI.ProductLookupPanel.ResultsPanel;
import seng300.software.exceptions.ProductNotFoundException;

import java.awt.CardLayout;
import java.awt.EventQueue;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
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
	private PaymentFailedPanel paymentFailedPanel;
	
	private SelfCheckoutSystemLogic logic;
	
	/**
	 * Create the panel.
	 */
	public CustomerGui(SelfCheckoutSystemLogic logic) 
	{
		
		setLayout(new CardLayout(0, 0));
		
		unavailablePanel = new StationUnavailablePanel();
		
		readyPanel = new StationReadyPanel();
		readyPanel.startButton.addActionListener(e -> displayCheckoutPanel());
		
		checkoutPanel = new CustomerCheckoutPanel();
		checkoutPanel.searchProductBtn.addActionListener(e -> displayProductLookupPanel());
		checkoutPanel.checkoutBtn.addActionListener(e -> displayPaymentPanel());
		checkoutPanel.pluEntryPinPad.padEnterBtn.addActionListener(e -> getPluCode());
		
		lookupPanel = new ProductLookupPanel();
		lookupPanel.returnButton.addActionListener(e -> displayCheckoutPanel());
		for (KeyboardButton btn : lookupPanel.keyboardBtns)
		{
			btn.addActionListener(e -> push(btn));
		}
		
		paymentPanel = new CustomerPaymentPanel();
		paymentPanel.returnToCheckoutBtn.addActionListener(e -> displayCheckoutPanel());
		paymentPanel.addMembershipBtn.addActionListener(e -> displayMembershipPanel());
		paymentPanel.payWithCoinBtn.addActionListener(e -> displayPayCoinPanel());
		paymentPanel.payWithCashBtn.addActionListener(e -> displayPayCashPanel());
		
		payCoinPanel = new CoinPaymentPanel();
		payCoinPanel.doneBtn.addActionListener(e -> displayPaymentPanel());
		
		payBanknotePanel = new BanknotePaymentPanel();
		payBanknotePanel.doneBtn.addActionListener(e -> displayPaymentPanel());
		
		membershipPanel = new EnterMembershipPanel();
		membershipPanel.cancelBtn.addActionListener(e -> displayPaymentPanel());
		
		this.logic = logic;
		
		add(unavailablePanel);
		add(readyPanel);
		add(checkoutPanel);
		add(lookupPanel);
		add(paymentPanel);
		add(membershipPanel);
		add(payCoinPanel);
		add(payBanknotePanel);
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
	}
	
	public void startup()
	{
		readyPanel.setVisible(true);
		unavailablePanel.setVisible(false);
	}
	
	public void shutdown()
	{
		logic.block();
		unavailablePanel.setVisible(true);
		readyPanel.setVisible(false);
		checkoutPanel.setVisible(false);
		lookupPanel.setVisible(false);
		paymentPanel.setVisible(false);
		membershipPanel.setVisible(false);
		payCoinPanel.setVisible(false);
		payBanknotePanel.setVisible(false);
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
			// add product to cart (no exception should ever be thrown)
			logic.getCart().addPLUCodedProductToCart(code, rand.nextDouble() * maxScaleWeight);
			displayCheckoutPanel();
		}
		else
		{
			throw new ProductNotFoundException();
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
	
	
	
	
	/**
	 * Launch the application. TO BE USED FOR TESTING ONLY!
	 */
	public static void main(String[] args) {
		
		SelfCheckoutStation scs = new SelfCheckoutStation(
				Currency.getInstance("CAD"),
				new int[] {5, 10, 15, 20, 50, 100},
				new BigDecimal[] {new BigDecimal("0.25"), new BigDecimal("0.10"), 
						new BigDecimal("0.05"), new BigDecimal("1.00"), new BigDecimal("2.00")},
				15,
				3
				);
		
		SelfCheckoutSystemLogic testlogic = new SelfCheckoutSystemLogic(scs);
		
		PriceLookupCode c1 = new PriceLookupCode("11111");
		PriceLookupCode c2 = new PriceLookupCode("22222");
		PriceLookupCode c3 = new PriceLookupCode("33333");
		PriceLookupCode c4 = new PriceLookupCode("44444");
		
		PLUCodedProduct p1 = new PLUCodedProduct(c1, "bananas (smol)", new BigDecimal("700.00"));
		PLUCodedProduct p4 = new PLUCodedProduct(c4, "bananas plantain", new BigDecimal("0.99"));
		PLUCodedProduct p2 = new PLUCodedProduct(c2, "car", new BigDecimal("2.00"));
		PLUCodedProduct p3 = new PLUCodedProduct(c3, "monke (fren)", new BigDecimal("0.01"));
		
		ProductDatabases.PLU_PRODUCT_DATABASE.put(c1, p1);
		ProductDatabases.PLU_PRODUCT_DATABASE.put(c2, p2);
		ProductDatabases.PLU_PRODUCT_DATABASE.put(c3, p3);
		ProductDatabases.PLU_PRODUCT_DATABASE.put(c4, p4);
		
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
