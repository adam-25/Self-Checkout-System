package seng300.software.GUI;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import seng300.software.SelfCheckoutSystemLogic;
import java.awt.CardLayout;
import java.awt.EventQueue;

public class CustomerGui extends JPanel {

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
	public CustomerGui() // SelfCheckoutSystemLogic logic
	{
//		this.logic = logic;
		setLayout(new CardLayout(0, 0));
		
		unavailablePanel = new StationUnavailablePanel();
		
		readyPanel = new StationReadyPanel();
		readyPanel.startButton.addActionListener(e -> displayCheckoutPanel());
		
		checkoutPanel = new CustomerCheckoutPanel();
		checkoutPanel.searchProductBtn.addActionListener(e -> displayProductLookupPanel());
		checkoutPanel.checkoutBtn.addActionListener(e -> displayPaymentPanel());
		
		lookupPanel = new ProductLookupPanel();
		lookupPanel.returnButton.addActionListener(e -> displayCheckoutPanel());
		
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
		block();
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
	
	public void block()
	{
//		logic.block();
//		logic.station.screen.disable();
	}
	
	/**
	 * Launch the application. TO BE USED FOR TESTING ONLY!
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JFrame frame = new JFrame();
					CustomerGui gui = new CustomerGui();
					frame.getContentPane().add(gui);
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
