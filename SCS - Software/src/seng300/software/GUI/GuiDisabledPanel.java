package seng300.software.GUI;

import javax.swing.JPanel;

import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Font;
import java.awt.Color;

public class GuiDisabledPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public GuiDisabledPanel() {
		setBackground(new Color(255, 250, 250));
		setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel stationDisabledLbl = new JLabel("Station has been disabled.");
		stationDisabledLbl.setFont(new Font("Tahoma", Font.BOLD, 20));
		stationDisabledLbl.setVerticalAlignment(SwingConstants.BOTTOM);
		stationDisabledLbl.setHorizontalAlignment(SwingConstants.CENTER);
		add(stationDisabledLbl);
		
		JLabel lblWaitForThe = new JLabel("Wait for the attendant to enable it again.");
		lblWaitForThe.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblWaitForThe.setHorizontalAlignment(SwingConstants.CENTER);
		lblWaitForThe.setVerticalAlignment(SwingConstants.TOP);
		add(lblWaitForThe);

	}

}
