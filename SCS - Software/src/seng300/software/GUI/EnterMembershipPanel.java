package seng300.software.GUI;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.Insets;
import java.awt.Font;
import java.awt.Color;
import java.awt.EventQueue;

public class EnterMembershipPanel extends JPanel {

	PinPad pinPad;
	/**
	 * Create the panel.
	 */
	public EnterMembershipPanel() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 281, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 202, 25, 50, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		pinPad = new PinPad();
		GridBagConstraints gbc_pinPad = new GridBagConstraints();
		gbc_pinPad.fill = GridBagConstraints.HORIZONTAL;
		gbc_pinPad.insets = new Insets(0, 0, 5, 5);
		gbc_pinPad.anchor = GridBagConstraints.NORTH;
		gbc_pinPad.gridx = 1;
		gbc_pinPad.gridy = 1;
		add(pinPad, gbc_pinPad);
		
		JButton btnNewButton = new JButton("Cancel");
		btnNewButton.setForeground(new Color(255, 0, 0));
		btnNewButton.setBackground(new Color(255, 228, 225));
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 16));
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton.gridx = 1;
		gbc_btnNewButton.gridy = 3;
		add(btnNewButton, gbc_btnNewButton);
	}
	
	/**
	 * Launch the application. TO BE USED FOR TESTING ONLY!
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JFrame frame = new JFrame();
					frame.getContentPane().add(new EnterMembershipPanel());
					frame.setBounds(100, 100, 450, 450);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
