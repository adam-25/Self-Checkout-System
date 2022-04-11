package seng300.software.GUI;

import org.lsmr.selfcheckout.products.Product;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;

import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import javax.swing.JButton;

public class CustomerRemoveItemPanel extends JPanel
{
	public final ArrayList<RemoveLogItem> logItems = new ArrayList<>();
	public final ArrayList<JButton> logItemRemoveBtns = new ArrayList<>();
	private JPanel itemDisplayPanel;
	

	class RemoveLogItem extends JPanel
	{
		Product product;
		GridBagLayout gbl_logItem;
		JLabel itemDescriptionLabel;
		JLabel itemPriceLabel;
		JButton itemRemoveBtn;
		
		RemoveLogItem(Product product, String description, BigDecimal price)
		{
			setBackground(new Color(248, 248, 255));
			setBorder(new EmptyBorder(10, 10, 10, 10));
			
			setBorder(new EmptyBorder(10, 10, 10, 10));
			setBackground(new Color(248, 248, 255));
			GridBagLayout gbl_panel = new GridBagLayout();
			gbl_panel.columnWidths = new int[]{0, 0, 10, 0, 0};
			gbl_panel.rowHeights = new int[]{0, 0};
			gbl_panel.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
			gbl_panel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
			setLayout(gbl_panel);
			
			itemDescriptionLabel = new JLabel(description);
			itemDescriptionLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
			GridBagConstraints gbc_itemDescriptionLabel = new GridBagConstraints();
			gbc_itemDescriptionLabel.anchor = GridBagConstraints.WEST;
			gbc_itemDescriptionLabel.insets = new Insets(0, 0, 0, 5);
			gbc_itemDescriptionLabel.gridx = 0;
			gbc_itemDescriptionLabel.gridy = 0;
			add(itemDescriptionLabel, gbc_itemDescriptionLabel);
			
			BigDecimal value = price == null ? new BigDecimal("0.00") : price;
			itemPriceLabel = new JLabel("$ " + value.setScale(2, RoundingMode.HALF_EVEN).toPlainString());
			itemPriceLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
			GridBagConstraints gbc_priceValueLabel = new GridBagConstraints();
			gbc_priceValueLabel.anchor = GridBagConstraints.EAST;
			gbc_priceValueLabel.insets = new Insets(0, 0, 0, 5);
			gbc_priceValueLabel.gridx = 1;
			gbc_priceValueLabel.gridy = 0;
			add(itemPriceLabel, gbc_priceValueLabel);
			
			itemRemoveBtn = new JButton("Remove");
			itemRemoveBtn.setForeground(new Color(139, 0, 0));
			itemRemoveBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
			itemRemoveBtn.setBackground(new Color(255, 228, 225));
			GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
			gbc_btnNewButton.gridx = 3;
			gbc_btnNewButton.gridy = 0;
			add(itemRemoveBtn, gbc_btnNewButton);
		}
		
		public JButton getRemoveBtn()
		{
			return itemRemoveBtn;
		}
	}
	
	/**
	 * Create the panel.
	 */
	public CustomerRemoveItemPanel()
	{
		setBackground(new Color(255, 255, 255));
		setBorder(new EmptyBorder(10, 10, 10, 10));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{816, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		itemDisplayPanel = new JPanel();
		itemDisplayPanel.setBackground(new Color(248, 248, 255));
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.anchor = GridBagConstraints.SOUTH;
		gbc_panel.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		add(itemDisplayPanel, gbc_panel);
		itemDisplayPanel.setLayout(new GridLayout(0, 1, 0, 0));
	}
	
	public void addItem(Product p, String description, BigDecimal price)
	{
		RemoveLogItem item = new RemoveLogItem(p, description, price);
		itemDisplayPanel.add(item);
		itemDisplayPanel.validate();
		logItems.add(item);
		logItemRemoveBtns.add(item.getRemoveBtn());
	}
}
