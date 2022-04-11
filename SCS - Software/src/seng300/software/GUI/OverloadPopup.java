package seng300.software.GUI;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class OverloadPopup extends JPanel {

	
	private boolean isOverload;
	
	/**
	 * Create the panel.
	 */
	public OverloadPopup() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel overloadLabel = new JLabel("This dispenser has been overloaded!");
		GridBagConstraints gbc_overloadLabel = new GridBagConstraints();
		gbc_overloadLabel.insets = new Insets(0, 0, 5, 5);
		gbc_overloadLabel.gridx = 1;
		gbc_overloadLabel.gridy = 2;
		add(overloadLabel, gbc_overloadLabel);
		
		JButton confirmBtn = new JButton("Confirm");
		confirmBtn.setOpaque(true);
		confirmBtn.setBorderPainted(false);
		confirmBtn.setBackground(Color.LIGHT_GRAY);
		confirmBtn.setForeground(new Color(0, 0, 0));
		GridBagConstraints gbc_confirmBtn = new GridBagConstraints();
		gbc_confirmBtn.insets = new Insets(0, 0, 5, 5);
		gbc_confirmBtn.gridx = 1;
		gbc_confirmBtn.gridy = 5;
		add(confirmBtn, gbc_confirmBtn);

		
		confirmBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isOverload = true;
				setVisible(false);
			}
		});
		
	}
	
	public boolean getIsOverload() {
		return isOverload;
	}

}
