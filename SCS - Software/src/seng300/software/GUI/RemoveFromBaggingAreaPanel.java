package seng300.software.GUI;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Color;

public class RemoveFromBaggingAreaPanel extends JPanel {

	public final JLabel itemDescriptionLabel;
	public final JButton rmItemBtn;
	private JLabel lblNewLabel;
	/**
	 * Create the panel.
	 */
	public RemoveFromBaggingAreaPanel() {
		setBackground(new Color(248, 248, 255));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 10, 0, 10, 0, 10, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel itemRemoveFromCartLabel = new JLabel("Item Removed from Cart");
		itemRemoveFromCartLabel.setFont(new Font("Tahoma", Font.BOLD, 28));
		GridBagConstraints gbc_itemRemoveFromCartLabel = new GridBagConstraints();
		gbc_itemRemoveFromCartLabel.insets = new Insets(0, 0, 5, 5);
		gbc_itemRemoveFromCartLabel.gridx = 1;
		gbc_itemRemoveFromCartLabel.gridy = 2;
		add(itemRemoveFromCartLabel, gbc_itemRemoveFromCartLabel);
		
		itemDescriptionLabel = new JLabel("");
		itemDescriptionLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		GridBagConstraints gbc_itemDescriptionLabel = new GridBagConstraints();
		gbc_itemDescriptionLabel.insets = new Insets(0, 0, 5, 5);
		gbc_itemDescriptionLabel.gridx = 1;
		gbc_itemDescriptionLabel.gridy = 4;
		add(itemDescriptionLabel, gbc_itemDescriptionLabel);
		
		lblNewLabel = new JLabel("Remove this item from the bagging area to continue checkout.");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 6;
		add(lblNewLabel, gbc_lblNewLabel);
		
		rmItemBtn = new JButton("Remove from Bagging Area");
		rmItemBtn.setForeground(new Color(248, 248, 255));
		rmItemBtn.setBackground(new Color(0, 0, 128));
		rmItemBtn.setFont(new Font("Tahoma", Font.BOLD, 20));
		GridBagConstraints gbc_rmItemBtn = new GridBagConstraints();
		gbc_rmItemBtn.fill = GridBagConstraints.BOTH;
		gbc_rmItemBtn.insets = new Insets(0, 0, 5, 5);
		gbc_rmItemBtn.gridx = 1;
		gbc_rmItemBtn.gridy = 8;
		add(rmItemBtn, gbc_rmItemBtn);

	}

}
