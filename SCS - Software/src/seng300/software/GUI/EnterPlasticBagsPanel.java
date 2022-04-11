package seng300.software.GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.JButton;

public class EnterPlasticBagsPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5692563178090620530L;
	
	public final PinPad pinPad;
	public final JLabel promptLbl;
	public final JButton returnToCheckoutBtn;
	
	/**
	 * Create the panel.
	 */
	public EnterPlasticBagsPanel()
	{
		setBackground(new Color(255, 255, 255));
		setBorder(new EmptyBorder(25, 25, 25, 25));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 400, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 10, 250, 10, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		promptLbl = new JLabel("How many plastic bags did you use?");
		promptLbl.setHorizontalAlignment(SwingConstants.CENTER);
		promptLbl.setFont(new Font("Tahoma", Font.BOLD, 24));
		GridBagConstraints gbc_enterNumPlasticBagsLabel = new GridBagConstraints();
		gbc_enterNumPlasticBagsLabel.fill = GridBagConstraints.BOTH;
		gbc_enterNumPlasticBagsLabel.insets = new Insets(0, 0, 5, 5);
		gbc_enterNumPlasticBagsLabel.gridx = 1;
		gbc_enterNumPlasticBagsLabel.gridy = 1;
		add(promptLbl, gbc_enterNumPlasticBagsLabel);
		pinPad = new PinPad();
		pinPad.setBackground(new Color(248, 248, 255));
		GridBagConstraints gbc_pinPad = new GridBagConstraints();
		gbc_pinPad.insets = new Insets(0, 0, 5, 5);
		gbc_pinPad.fill = GridBagConstraints.BOTH;
		gbc_pinPad.gridx = 1;
		gbc_pinPad.gridy = 3;
		add(pinPad, gbc_pinPad);
		
		returnToCheckoutBtn = new JButton("Return to Checkout");
		returnToCheckoutBtn.setBackground(new Color(255, 245, 238));
		returnToCheckoutBtn.setForeground(new Color(205, 133, 63));
		returnToCheckoutBtn.setFont(new Font("Tahoma", Font.BOLD, 18));
		GridBagConstraints gbc_returnToCheckoutBtn = new GridBagConstraints();
		gbc_returnToCheckoutBtn.fill = GridBagConstraints.HORIZONTAL;
		gbc_returnToCheckoutBtn.insets = new Insets(0, 0, 5, 5);
		gbc_returnToCheckoutBtn.gridx = 1;
		gbc_returnToCheckoutBtn.gridy = 5;
		add(returnToCheckoutBtn, gbc_returnToCheckoutBtn);
	}
}
