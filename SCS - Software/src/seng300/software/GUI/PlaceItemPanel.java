package seng300.software.GUI;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Color;

public class PlaceItemPanel extends JPanel {

	public final JLabel itemDescriptionLabel;
	public final JButton placeItemBtn;
	/**
	 * Create the panel.
	 */
	public PlaceItemPanel() {
		setBackground(new Color(248, 248, 255));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 10, 0, 10, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel placeItemLabel = new JLabel("Place Item in Bagging Area");
		placeItemLabel.setFont(new Font("Tahoma", Font.BOLD, 28));
		GridBagConstraints gbc_placeItemLabel = new GridBagConstraints();
		gbc_placeItemLabel.insets = new Insets(0, 0, 5, 5);
		gbc_placeItemLabel.gridx = 1;
		gbc_placeItemLabel.gridy = 1;
		add(placeItemLabel, gbc_placeItemLabel);
		
		itemDescriptionLabel = new JLabel("");
		itemDescriptionLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		GridBagConstraints gbc_itemDescriptionLabel = new GridBagConstraints();
		gbc_itemDescriptionLabel.insets = new Insets(0, 0, 5, 5);
		gbc_itemDescriptionLabel.gridx = 1;
		gbc_itemDescriptionLabel.gridy = 3;
		add(itemDescriptionLabel, gbc_itemDescriptionLabel);
		
		placeItemBtn = new JButton("Place Item");
		placeItemBtn.setForeground(new Color(248, 248, 255));
		placeItemBtn.setBackground(new Color(0, 0, 128));
		placeItemBtn.setFont(new Font("Tahoma", Font.BOLD, 20));
		GridBagConstraints gbc_placeItemBtn = new GridBagConstraints();
		gbc_placeItemBtn.fill = GridBagConstraints.BOTH;
		gbc_placeItemBtn.insets = new Insets(0, 0, 5, 5);
		gbc_placeItemBtn.gridx = 1;
		gbc_placeItemBtn.gridy = 5;
		add(placeItemBtn, gbc_placeItemBtn);

	}

}
