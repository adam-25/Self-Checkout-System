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

	public final JButton startButton;
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
		
		startButton = new JButton("Start");
		startButton.setBackground(new Color(240, 255, 240));
		startButton.setFont(new Font("Tahoma", Font.BOLD, 28));
		startButton.setForeground(new Color(0, 100, 0));
		GridBagConstraints gbc_startButton = new GridBagConstraints();
		gbc_startButton.fill = GridBagConstraints.BOTH;
		gbc_startButton.insets = new Insets(0, 0, 5, 5);
		gbc_startButton.gridx = 1;
		gbc_startButton.gridy = 3;
		add(startButton, gbc_startButton);

	}

}
