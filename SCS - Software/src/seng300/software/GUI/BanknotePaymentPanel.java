package seng300.software.GUI;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class BanknotePaymentPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public BanknotePaymentPanel() {
		setBorder(new EmptyBorder(25, 25, 25, 25));
		setBackground(new Color(255, 255, 255));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 5, 10, 200, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 5, 25, 2, 25, 30, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 0.0, 0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 250, 250));
		panel.setBorder(new EmptyBorder(10, 10, 10, 10));
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridwidth = 3;
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 1;
		add(panel, gbc_panel);
		panel.setLayout(new GridLayout(6, 1, 10, 10));
		
		JLabel selectCoinLabel = new JLabel("Select Banknote");
		selectCoinLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(selectCoinLabel);
		selectCoinLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		
		JButton toonieBtn = new JButton("$100");
		panel.add(toonieBtn);
		toonieBtn.setBackground(new Color(255, 248, 220));
		toonieBtn.setForeground(new Color(184, 134, 11));
		toonieBtn.setFont(new Font("Tahoma", Font.BOLD, 24));
		
		JButton loonieBtn = new JButton("$50");
		panel.add(loonieBtn);
		loonieBtn.setBackground(new Color(255, 248, 220));
		loonieBtn.setForeground(new Color(184, 134, 11));
		loonieBtn.setFont(new Font("Tahoma", Font.BOLD, 24));
		
		JButton quarterBtn = new JButton("$20");
		panel.add(quarterBtn);
		quarterBtn.setBackground(new Color(255, 248, 220));
		quarterBtn.setForeground(new Color(184, 134, 11));
		quarterBtn.setFont(new Font("Tahoma", Font.BOLD, 24));
		
		JButton dimeBtn = new JButton("$10");
		panel.add(dimeBtn);
		dimeBtn.setBackground(new Color(255, 248, 220));
		dimeBtn.setForeground(new Color(184, 134, 11));
		dimeBtn.setFont(new Font("Tahoma", Font.BOLD, 24));
		
		JButton nickelBtn = new JButton("$5");
		panel.add(nickelBtn);
		nickelBtn.setBackground(new Color(255, 248, 220));
		nickelBtn.setForeground(new Color(184, 134, 11));
		nickelBtn.setFont(new Font("Tahoma", Font.BOLD, 24));
		
		JLabel totalCoinsLabel = new JLabel("Total Banknotes");
		totalCoinsLabel.setBackground(new Color(255, 255, 255));
		totalCoinsLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		GridBagConstraints gbc_totalCoinsLabel = new GridBagConstraints();
		gbc_totalCoinsLabel.anchor = GridBagConstraints.WEST;
		gbc_totalCoinsLabel.insets = new Insets(0, 0, 5, 5);
		gbc_totalCoinsLabel.gridx = 1;
		gbc_totalCoinsLabel.gridy = 3;
		add(totalCoinsLabel, gbc_totalCoinsLabel);
		
		JLabel totalCoinsValue = new JLabel("$ 000.00");
		totalCoinsValue.setBackground(new Color(255, 255, 255));
		totalCoinsValue.setFont(new Font("Tahoma", Font.BOLD, 24));
		GridBagConstraints gbc_totalCoinsValue = new GridBagConstraints();
		gbc_totalCoinsValue.anchor = GridBagConstraints.EAST;
		gbc_totalCoinsValue.insets = new Insets(0, 0, 5, 5);
		gbc_totalCoinsValue.gridx = 3;
		gbc_totalCoinsValue.gridy = 3;
		add(totalCoinsValue, gbc_totalCoinsValue);
		
		JButton doneBtn = new JButton("Done");
		doneBtn.setBackground(new Color(240, 255, 240));
		doneBtn.setForeground(new Color(0, 128, 0));
		doneBtn.setFont(new Font("Tahoma", Font.BOLD, 16));
		GridBagConstraints gbc_doneBtn = new GridBagConstraints();
		gbc_doneBtn.insets = new Insets(0, 0, 5, 5);
		gbc_doneBtn.fill = GridBagConstraints.BOTH;
		gbc_doneBtn.gridwidth = 3;
		gbc_doneBtn.gridx = 1;
		gbc_doneBtn.gridy = 5;
		add(doneBtn, gbc_doneBtn);

	}
	
	/**
	 * Launch the application. TO BE USED FOR TESTING ONLY!
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JFrame frame = new JFrame();
					frame.getContentPane().add(new BanknotePaymentPanel());
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
