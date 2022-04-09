package seng300.software.GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.math.BigDecimal;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.lsmr.selfcheckout.products.BarcodedProduct;
import org.lsmr.selfcheckout.products.PLUCodedProduct;
import org.lsmr.selfcheckout.products.Product;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import javax.swing.JList;

public class ItemLog extends JPanel {

	private Dimension screenSize;
	private JScrollPane scrollPane;
	private JPanel panel;
	private JLabel[] listProducts;
	private JLabel displayTotal;
	/**
	 * Create the panel.
	 */
	public ItemLog(ArrayList<Product> list) {
		setBorder(new EmptyBorder(25, 25, 25, 25));
		setBackground(new Color(255, 255, 255));
		
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		setPreferredSize(new Dimension((int) (screenSize.getWidth() * 0.15), (int) (screenSize.getHeight() * 0.45)));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		scrollPane = new JScrollPane(panel);
		
//		JPanel panel = new JPanel();
//		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
//		scrollPane.setViewportView(panel);
		
		Product currentProduct;
		BarcodedProduct bProduct;
		PLUCodedProduct pProduct;
		
		BigDecimal productPrice;
		double price;
		double total = 0;
		for (int i = 0; i < list.size(); i++) {
			currentProduct = list.get(i);
			productPrice = currentProduct.getPrice();
			price = productPrice.doubleValue();
			total += price;
			if (currentProduct instanceof BarcodedProduct) {
				bProduct = (BarcodedProduct) currentProduct;
				
				listProducts[i] = new JLabel(bProduct.getDescription() + "\t" + "$ " + price);
			} else {
				pProduct = (PLUCodedProduct) currentProduct;
				listProducts[i] = new JLabel(pProduct.getDescription()+ "\t" + "$ " + price);
			}
			listProducts[i].setFont(new Font("Tahoma", Font.PLAIN, 14));
			panel.add(listProducts[i]);
			panel.add(Box.createRigidArea(new Dimension(0, 5)));
		}
		
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		add(scrollPane);
		
		displayTotal = new JLabel("Total: " + "\t" + "$ " + total);
		displayTotal.setFont(new Font("Tahoma", Font.BOLD, 16));
		displayTotal.setAlignmentX(CENTER_ALIGNMENT);
		add(displayTotal);
	}

}
