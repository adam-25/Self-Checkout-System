package seng300.software.GUI;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import javax.swing.border.EmptyBorder;

import org.lsmr.selfcheckout.InvalidArgumentSimulationException;
import org.lsmr.selfcheckout.PriceLookupCode;

import javax.swing.JLabel;
import java.awt.GridLayout;
import java.awt.CardLayout;
import javax.swing.JToggleButton;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import javax.swing.SwingConstants;

public class CustomerCheckoutPanel extends JPanel
{
	public final ItemLogPanel itemLogPanel;
	public final JButton useOwnBagsBtn;
	public final JButton scanItemBtn;
	public final JButton searchProductBtn;
	public final JButton removeItemBtn;
	public final JButton doNotBagBtn;
	public final JButton payBtn;
	public final PinPad pluEntryPinPad;
	public final JButton viewBaggingAreaBtn;
	
	private JPanel rightPanel;
	private JPanel leftPanel;
	private JPanel logoPanel;
	private JPanel pluEntryPanel;
	private JLabel pluEntryErrorMsgLabel;
	private JPanel attendantNotifiedPanel;
	private JLabel attendantNotifiedLabel;
	private JLabel waitingOnAttendantLabel;

	/**
	 * Create the panel.
	 */
	public CustomerCheckoutPanel()
	{
		setBorder(new EmptyBorder(20, 20, 20, 20));
		setBackground(new Color(255, 255, 255));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 350, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		rightPanel = new JPanel();
		rightPanel.setPreferredSize(new Dimension(350, 550));
		rightPanel.setBackground(Color.WHITE);
		rightPanel.setBorder(new EmptyBorder(0, 5, 0, 15));
		GridBagConstraints gbc_rightPanel = new GridBagConstraints();
		gbc_rightPanel.gridheight = 5;
		gbc_rightPanel.insets = new Insets(0, 0, 5, 5);
		gbc_rightPanel.fill = GridBagConstraints.BOTH;
		gbc_rightPanel.gridx = 1;
		gbc_rightPanel.gridy = 1;
		add(rightPanel, gbc_rightPanel);
		rightPanel.setLayout(new CardLayout(0, 0));
		
		itemLogPanel = new ItemLogPanel();
		GridBagLayout gridBagLayout_1 = (GridBagLayout) itemLogPanel.getLayout();
		gridBagLayout_1.columnWeights = new double[]{1.0};
		gridBagLayout_1.columnWidths = new int[]{450};
		itemLogPanel.setPreferredSize(new Dimension(250, 450));
		rightPanel.add(itemLogPanel);
		
		JPanel mainBtnGroup = new JPanel();
		mainBtnGroup.setBackground(new Color(255, 255, 255));
		mainBtnGroup.setBorder(new EmptyBorder(0, 0, 5, 5));
		GridBagConstraints gbc_mainBtnGroup = new GridBagConstraints();
		gbc_mainBtnGroup.gridheight = 4;
		gbc_mainBtnGroup.insets = new Insets(0, 0, 5, 5);
		gbc_mainBtnGroup.fill = GridBagConstraints.BOTH;
		gbc_mainBtnGroup.gridx = 2;
		gbc_mainBtnGroup.gridy = 1;
		add(mainBtnGroup, gbc_mainBtnGroup);
		mainBtnGroup.setLayout(new GridLayout(0, 1, 10, 10));
		
		useOwnBagsBtn = new JButton("Use Own Bags");
		useOwnBagsBtn.setPreferredSize(new Dimension(175, 25));
		useOwnBagsBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
		useOwnBagsBtn.setBackground(new Color(245, 245, 245));
		useOwnBagsBtn.setForeground(new Color(0, 0, 0));
		mainBtnGroup.add(useOwnBagsBtn);
		
		scanItemBtn = new JButton("Scan Item");
		scanItemBtn.setPreferredSize(new Dimension(175, 25));
		scanItemBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
		scanItemBtn.setBackground(new Color(245, 245, 245));
		scanItemBtn.setForeground(new Color(0, 0, 0));
		mainBtnGroup.add(scanItemBtn);
		
		JButton enterPLUCodeBtn = new JButton("Enter PLU Code");
		enterPLUCodeBtn.setPreferredSize(new Dimension(175, 25));
		enterPLUCodeBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
		enterPLUCodeBtn.setBackground(new Color(245, 245, 245));
		enterPLUCodeBtn.setForeground(new Color(0, 0, 0));
		enterPLUCodeBtn.addActionListener(e -> showPluEntryPanel());
		mainBtnGroup.add(enterPLUCodeBtn);		
		
		searchProductBtn = new JButton("Search Product");
		searchProductBtn.setPreferredSize(new Dimension(175, 25));
		searchProductBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
		searchProductBtn.setBackground(new Color(245, 245, 245));
		searchProductBtn.setForeground(new Color(0, 0, 0));
		mainBtnGroup.add(searchProductBtn);
		
		// TODO: ActionListner needs to call method to remove item from bagging
		removeItemBtn = new JButton("Remove Item");
		removeItemBtn.setPreferredSize(new Dimension(175, 25));
		removeItemBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
		removeItemBtn.setBackground(new Color(245, 245, 245));
		removeItemBtn.setForeground(new Color(0, 0, 0));
		mainBtnGroup.add(removeItemBtn);
		
		// TODO: ActionListner needs to notify attendant? Not sure how this was implemented
		doNotBagBtn = new JButton("Do Not Bag");
		doNotBagBtn.setPreferredSize(new Dimension(175, 25));
		doNotBagBtn.setForeground(Color.BLACK);
		doNotBagBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
		doNotBagBtn.setBackground(new Color(245, 245, 245));
		mainBtnGroup.add(doNotBagBtn);
		
		viewBaggingAreaBtn = new JButton("View Bagging Area");
		viewBaggingAreaBtn.setPreferredSize(new Dimension(175, 25));
		viewBaggingAreaBtn.setForeground(Color.BLACK);
		viewBaggingAreaBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
		viewBaggingAreaBtn.setBackground(new Color(245, 245, 245));
		mainBtnGroup.add(viewBaggingAreaBtn);
		
		leftPanel = new JPanel();
		leftPanel.setPreferredSize(new Dimension(300, 475));
		leftPanel.setBorder(new EmptyBorder(0, 10, 10, 10));
		leftPanel.setBackground(new Color(255, 255, 255));
		GridBagConstraints gbc_leftPanel = new GridBagConstraints();
		gbc_leftPanel.gridheight = 4;
		gbc_leftPanel.insets = new Insets(0, 0, 5, 5);
		gbc_leftPanel.fill = GridBagConstraints.BOTH;
		gbc_leftPanel.gridx = 3;
		gbc_leftPanel.gridy = 1;
		add(leftPanel, gbc_leftPanel);
		leftPanel.setLayout(new CardLayout(0, 0));
		
		logoPanel = new JPanel();
		logoPanel.setBackground(new Color(255, 255, 255));
		logoPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		GridBagLayout gbl_logoPanel = new GridBagLayout();
		gbl_logoPanel.columnWidths = new int[]{57, 86, 0, 0};
		gbl_logoPanel.rowHeights = new int[]{17, 0, 0, 0};
		gbl_logoPanel.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_logoPanel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		logoPanel.setLayout(gbl_logoPanel);
		leftPanel.add(logoPanel);
		logoPanel.setVisible(true);
		
		// TODO: Setup company logo to use.
		JLabel lblNewLabel = new JLabel("logo goes here");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 0;
		logoPanel.add(lblNewLabel, gbc_lblNewLabel);
		
		pluEntryPanel = new JPanel();
		pluEntryPanel.setBackground(new Color(255, 255, 255));
		pluEntryPanel.setBorder(new EmptyBorder(25, 0, 25, 0));
		leftPanel.add(pluEntryPanel);
		pluEntryPanel.setVisible(false);

		GridBagLayout gbl_pluEntryPanel = new GridBagLayout();
		gbl_pluEntryPanel.columnWidths = new int[]{57};
		gbl_pluEntryPanel.rowHeights = new int[]{17, 0};
		gbl_pluEntryPanel.columnWeights = new double[]{1.0};
		gbl_pluEntryPanel.rowWeights = new double[]{0.0, 1.0};
		pluEntryPanel.setLayout(gbl_pluEntryPanel);
		
		pluEntryErrorMsgLabel = new JLabel("Product not found. Please try again.");
		pluEntryErrorMsgLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		pluEntryErrorMsgLabel.setForeground(new Color(255, 0, 0));
		GridBagConstraints gbc_pluEntryErrorMsgLabel = new GridBagConstraints();
		gbc_pluEntryErrorMsgLabel.insets = new Insets(0, 0, 5, 0);
		gbc_pluEntryErrorMsgLabel.gridx = 0;
		gbc_pluEntryErrorMsgLabel.gridy = 0;
		pluEntryPanel.add(pluEntryErrorMsgLabel, gbc_pluEntryErrorMsgLabel);
		pluEntryErrorMsgLabel.setVisible(false);
				
		pluEntryPinPad = new PinPad();
		pluEntryPinPad.setBackground(Color.WHITE);
		pluEntryPinPad.setBorder(new EmptyBorder(10, 10, 10, 10));
		GridBagConstraints gbc_pluEntryPad = new GridBagConstraints();
		gbc_pluEntryPad.fill = GridBagConstraints.BOTH;
		gbc_pluEntryPad.gridx = 0;
		gbc_pluEntryPad.gridy = 1;
		pluEntryPanel.add(pluEntryPinPad, gbc_pluEntryPad);
		
		attendantNotifiedPanel = new JPanel();
		attendantNotifiedPanel.setBackground(new Color(248, 248, 255));
		leftPanel.add(attendantNotifiedPanel);
		GridBagLayout gbl_attendantNotifiedPanel = new GridBagLayout();
		gbl_attendantNotifiedPanel.columnWidths = new int[]{0, 0};
		gbl_attendantNotifiedPanel.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_attendantNotifiedPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_attendantNotifiedPanel.rowWeights = new double[]{1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		attendantNotifiedPanel.setLayout(gbl_attendantNotifiedPanel);
		
		attendantNotifiedLabel = new JLabel("Attendant Notified");
		attendantNotifiedLabel.setFont(new Font("Tahoma", Font.BOLD, 32));
		GridBagConstraints gbc_attendantNotifiedLabel = new GridBagConstraints();
		gbc_attendantNotifiedLabel.insets = new Insets(0, 0, 5, 0);
		gbc_attendantNotifiedLabel.gridx = 0;
		gbc_attendantNotifiedLabel.gridy = 1;
		attendantNotifiedPanel.add(attendantNotifiedLabel, gbc_attendantNotifiedLabel);
		
		waitingOnAttendantLabel = new JLabel("Waiting on attendant approval.");
		waitingOnAttendantLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_waitingOnAttendantLabel = new GridBagConstraints();
		gbc_waitingOnAttendantLabel.insets = new Insets(0, 0, 5, 0);
		gbc_waitingOnAttendantLabel.gridx = 0;
		gbc_waitingOnAttendantLabel.gridy = 2;
		attendantNotifiedPanel.add(waitingOnAttendantLabel, gbc_waitingOnAttendantLabel);
		
		payBtn = new JButton("Pay");
		payBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		payBtn.setMargin(new Insets(10, 14, 10, 14));
		payBtn.setBackground(new Color(240, 255, 240));
		payBtn.setFont(new Font("Tahoma", Font.BOLD, 20));
		payBtn.setForeground(new Color(0, 100, 0));
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton.gridwidth = 2;
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton.gridx = 2;
		gbc_btnNewButton.gridy = 5;
		add(payBtn, gbc_btnNewButton);

	}
	
	public void setLeftPanel(JPanel panel)
	{
		leftPanel.add(panel);
		hidePluEntryPanel();
		hideLogoPanel();
		hideAttendantNotifiedPanel();
		panel.setVisible(true);
	}
	
	public void setRightPanel(JPanel panel)
	{
		rightPanel.add(panel);
		hidePluEntryPanel();
		hideLogoPanel();
		hideAttendantNotifiedPanel();
		panel.setVisible(true);
	}
		
	public void removeFromLeftPanel(JPanel panel)
	{
		leftPanel.remove(panel);
		leftPanel.validate();
		showLogoPanel();
	}
	
	public void showPluEntryPanel()
	{
		hideLogoPanel();
		hideAttendantNotifiedPanel();
		pluEntryPanel.setVisible(true);
	}
	
	public void showPluEntryPanelErrorMsg()
	{
		pluEntryErrorMsgLabel.setVisible(true);
	}
	
	public void hidePluEntryPanelErrorMsg()
	{
		pluEntryErrorMsgLabel.setVisible(false);
	}
	
	public void showLogoPanel()
	{
		hidePluEntryPanel();
		hideAttendantNotifiedPanel();
		logoPanel.setVisible(true);
	}
	
	public void showAttendantNotifiedPanel()
	{
		hidePluEntryPanel();
		hideLogoPanel();
		attendantNotifiedPanel.setVisible(true);
	}
	
	private void hideAttendantNotifiedPanel()
	{
		if (attendantNotifiedPanel.isVisible())
		{
			attendantNotifiedPanel.setVisible(false);
		}
	}
	
	private void hidePluEntryPanel()
	{
		if (pluEntryPanel.isVisible())
		{
			pluEntryPanel.setVisible(false);
		}
	}
	
	private void hideLogoPanel()
	{
		if (logoPanel.isVisible())
		{
			logoPanel.setVisible(false);
		}
	}
	
	/**
	 * Launch the application. TO BE USED FOR TESTING ONLY!
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JFrame frame = new JFrame();
					frame.getContentPane().add(new CustomerCheckoutPanel());
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.pack();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
