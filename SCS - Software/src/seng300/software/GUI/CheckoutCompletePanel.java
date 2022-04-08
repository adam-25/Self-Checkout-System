package seng300.software.GUI;

import javax.swing.JPanel;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Insets;
import javax.swing.JButton;

public class CheckoutCompletePanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public CheckoutCompletePanel() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel paymentAcceptedLabel = new JLabel("We have accepted your payment.");
		paymentAcceptedLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_paymentAcceptedLabel = new GridBagConstraints();
		gbc_paymentAcceptedLabel.insets = new Insets(0, 0, 5, 5);
		gbc_paymentAcceptedLabel.gridx = 1;
		gbc_paymentAcceptedLabel.gridy = 1;
		add(paymentAcceptedLabel, gbc_paymentAcceptedLabel);
		
		JLabel greatDayLabel = new JLabel("Have a great day!");
		greatDayLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		GridBagConstraints gbc_greatDayLabel = new GridBagConstraints();
		gbc_greatDayLabel.insets = new Insets(0, 0, 5, 5);
		gbc_greatDayLabel.gridx = 1;
		gbc_greatDayLabel.gridy = 2;
		add(greatDayLabel, gbc_greatDayLabel);

	}
	
	/**
	 * Launch the application. TO BE USED FOR TESTING ONLY!
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JFrame frame = new JFrame();
					frame.getContentPane().add(new CheckoutCompletePanel());
					frame.setBounds(100, 100, 450, 450);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
