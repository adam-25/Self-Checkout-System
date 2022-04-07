package seng300.software.GUI;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Font;
import java.awt.Insets;
import javax.swing.JButton;
import java.awt.Color;

public class StationReadyPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public StationReadyPanel() {
		setBackground(new Color(255, 255, 255));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 25, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel logoPlaceholderLabel = new JLabel("Logo goes here.");
		GridBagConstraints gbc_logoPlaceholderLabel = new GridBagConstraints();
		gbc_logoPlaceholderLabel.insets = new Insets(0, 0, 5, 5);
		gbc_logoPlaceholderLabel.gridx = 1;
		gbc_logoPlaceholderLabel.gridy = 1;
		add(logoPlaceholderLabel, gbc_logoPlaceholderLabel);
		
		JButton btnNewButton = new JButton("Start");
		btnNewButton.setBackground(new Color(240, 255, 240));
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 28));
		btnNewButton.setForeground(new Color(0, 100, 0));
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton.gridx = 1;
		gbc_btnNewButton.gridy = 3;
		add(btnNewButton, gbc_btnNewButton);

	}

}
