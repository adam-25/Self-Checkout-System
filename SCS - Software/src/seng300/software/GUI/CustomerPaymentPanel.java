package seng300.software.GUI;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Font;
import java.awt.Insets;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import javax.swing.JSeparator;

public class CustomerPaymentPanel extends JPanel
{
	public final JButton payWithDebitBtn;
	public final JButton payWithCreditBtn;
	public final JButton payWithCoinBtn;
	public final JButton payWithCashBtn;
	public final JButton addMembershipBtn;
	public final JButton returnToCheckoutBtn;

	/**
	 * Create the panel.
	 */
	public CustomerPaymentPanel()
	{
		setBackground(new Color(255, 255, 255));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 120, 200, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 38, 50, 50, 10, 20, 25, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel billTotalLabel = new JLabel("Bill Total");
		billTotalLabel.setHorizontalAlignment(SwingConstants.LEFT);
		billTotalLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_billTotalLabel = new GridBagConstraints();
		gbc_billTotalLabel.fill = GridBagConstraints.VERTICAL;
		gbc_billTotalLabel.anchor = GridBagConstraints.WEST;
		gbc_billTotalLabel.insets = new Insets(0, 0, 5, 5);
		gbc_billTotalLabel.gridx = 1;
		gbc_billTotalLabel.gridy = 1;
		add(billTotalLabel, gbc_billTotalLabel);
		
		JLabel billTotalValue = new JLabel("$ 000.00");
		billTotalValue.setFont(new Font("Tahoma", Font.BOLD, 16));
		GridBagConstraints gbc_billTotalValue = new GridBagConstraints();
		gbc_billTotalValue.fill = GridBagConstraints.VERTICAL;
		gbc_billTotalValue.anchor = GridBagConstraints.EAST;
		gbc_billTotalValue.insets = new Insets(0, 0, 5, 5);
		gbc_billTotalValue.gridx = 2;
		gbc_billTotalValue.gridy = 1;
		add(billTotalValue, gbc_billTotalValue);
		
		JLabel totalPaidLabel = new JLabel("Total Paid");
		totalPaidLabel.setHorizontalAlignment(SwingConstants.LEFT);
		totalPaidLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_totalPaidLabel = new GridBagConstraints();
		gbc_totalPaidLabel.anchor = GridBagConstraints.WEST;
		gbc_totalPaidLabel.insets = new Insets(0, 0, 5, 5);
		gbc_totalPaidLabel.gridx = 1;
		gbc_totalPaidLabel.gridy = 2;
		add(totalPaidLabel, gbc_totalPaidLabel);
		
		JLabel totalPaidValue = new JLabel("$ 000.00");
		totalPaidValue.setFont(new Font("Tahoma", Font.BOLD, 16));
		GridBagConstraints gbc_totalPaidValue = new GridBagConstraints();
		gbc_totalPaidValue.anchor = GridBagConstraints.EAST;
		gbc_totalPaidValue.insets = new Insets(0, 0, 5, 5);
		gbc_totalPaidValue.gridx = 2;
		gbc_totalPaidValue.gridy = 2;
		add(totalPaidValue, gbc_totalPaidValue);
		
		JLabel amountOwingLabel = new JLabel("Amount Owing");
		amountOwingLabel.setForeground(Color.RED);
		amountOwingLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_amountOwingLabel = new GridBagConstraints();
		gbc_amountOwingLabel.anchor = GridBagConstraints.WEST;
		gbc_amountOwingLabel.insets = new Insets(0, 0, 5, 5);
		gbc_amountOwingLabel.gridx = 1;
		gbc_amountOwingLabel.gridy = 3;
		add(amountOwingLabel, gbc_amountOwingLabel);

		JLabel amountOwingValue = new JLabel("$ 000.00");
		amountOwingValue.setForeground(new Color(255, 0, 0));
		amountOwingValue.setFont(new Font("Tahoma", Font.BOLD, 16));
		GridBagConstraints gbc_amountOwingValue = new GridBagConstraints();
		gbc_amountOwingValue.anchor = GridBagConstraints.EAST;
		gbc_amountOwingValue.insets = new Insets(0, 0, 5, 5);
		gbc_amountOwingValue.gridx = 2;
		gbc_amountOwingValue.gridy = 3;
		add(amountOwingValue, gbc_amountOwingValue);
		
		JLabel changeDueLabel = new JLabel("Change Due");
		changeDueLabel.setHorizontalAlignment(SwingConstants.LEFT);
		changeDueLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_changeDueLabel = new GridBagConstraints();
		gbc_changeDueLabel.anchor = GridBagConstraints.WEST;
		gbc_changeDueLabel.insets = new Insets(0, 0, 5, 5);
		gbc_changeDueLabel.gridx = 1;
		gbc_changeDueLabel.gridy = 4;
		add(changeDueLabel, gbc_changeDueLabel);
		
		JLabel changeDueValue = new JLabel("$ 000.00");
		changeDueValue.setFont(new Font("Tahoma", Font.BOLD, 16));
		GridBagConstraints gbc_changeDueValue = new GridBagConstraints();
		gbc_changeDueValue.anchor = GridBagConstraints.EAST;
		gbc_changeDueValue.insets = new Insets(0, 0, 5, 5);
		gbc_changeDueValue.gridx = 2;
		gbc_changeDueValue.gridy = 4;
		add(changeDueValue, gbc_changeDueValue);
		
		JLabel selectPaymentLabel = new JLabel("Select a Payment Method");
		selectPaymentLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.gridwidth = 2;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 5;
		add(selectPaymentLabel, gbc_lblNewLabel);
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridwidth = 2;
		gbc_panel.gridheight = 2;
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 6;
		add(panel, gbc_panel);
		panel.setLayout(new GridLayout(2, 2, 5, 5));
		
		payWithDebitBtn = new JButton("Debit");
		payWithDebitBtn.setForeground(new Color(25, 25, 112));
		payWithDebitBtn.setBackground(new Color(176, 196, 222));
		payWithDebitBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel.add(payWithDebitBtn);
		
		payWithCreditBtn = new JButton("Credit");
		payWithCreditBtn.setForeground(new Color(128, 0, 0));
		payWithCreditBtn.setBackground(new Color(255, 192, 203));
		payWithCreditBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel.add(payWithCreditBtn);
		
		payWithCoinBtn = new JButton("Coin");
		payWithCoinBtn.setForeground(new Color(184, 134, 11));
		payWithCoinBtn.setBackground(new Color(250, 250, 210));
		payWithCoinBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel.add(payWithCoinBtn);
		
		payWithCashBtn = new JButton("Cash");
		payWithCashBtn.setForeground(new Color(210, 105, 30));
		payWithCashBtn.setBackground(new Color(255, 228, 196));
		payWithCashBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel.add(payWithCashBtn);
		
		addMembershipBtn = new JButton("Add Membership");
		addMembershipBtn.setForeground(new Color(139, 0, 139));
		addMembershipBtn.setBackground(new Color(230, 230, 250));
		addMembershipBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
		GridBagConstraints gbc_addMembershipBtn = new GridBagConstraints();
		gbc_addMembershipBtn.gridwidth = 2;
		gbc_addMembershipBtn.insets = new Insets(0, 0, 5, 5);
		gbc_addMembershipBtn.fill = GridBagConstraints.BOTH;
		gbc_addMembershipBtn.gridx = 1;
		gbc_addMembershipBtn.gridy = 9;
		add(addMembershipBtn, gbc_addMembershipBtn);
		
		returnToCheckoutBtn = new JButton("Return to Checkout");
		returnToCheckoutBtn.setForeground(new Color(255, 0, 0));
		returnToCheckoutBtn.setBackground(new Color(255, 228, 225));
		returnToCheckoutBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
		GridBagConstraints gbc_finishPaymentBtn = new GridBagConstraints();
		gbc_finishPaymentBtn.gridwidth = 2;
		gbc_finishPaymentBtn.fill = GridBagConstraints.BOTH;
		gbc_finishPaymentBtn.insets = new Insets(0, 0, 5, 5);
		gbc_finishPaymentBtn.gridx = 1;
		gbc_finishPaymentBtn.gridy = 10;
		add(returnToCheckoutBtn, gbc_finishPaymentBtn);
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JFrame frame = new JFrame();
					frame.getContentPane().add(new CustomerPaymentPanel());
					frame.pack();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
