package seng300.software.GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JScrollPane;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JSplitPane;

import org.lsmr.selfcheckout.products.BarcodedProduct;
import org.lsmr.selfcheckout.products.PLUCodedProduct;
import org.lsmr.selfcheckout.products.Product;
import java.awt.Insets;
import javax.swing.JTextArea;
import java.awt.Font;
import javax.swing.JTextField;

public class RemoveItemLog extends JFrame implements ActionListener{

	private JPanel contentPane;
	private JPanel scrollPanel;
	private JPanel wrapper;
	private JSplitPane splitPane;
	private GridBagConstraints gbc_txtrPleaseSelectAll;
	private GridBagConstraints gbc_btnNewButton;
	private GridBagConstraints gbc_printTotalprice;
	private Dimension max;
	private JTextArea txtrPleaseSelectAll;
	private JButton remove;
	private JCheckBox [] productsInLog;
	private Map<JCheckBox, Product> removable;
	private ArrayList<Product> allProducts;
	private JTextField printTotalPrice;

	/**
	 * Create the frame.
	 */
	public RemoveItemLog(ArrayList<Product> list) {
		this.allProducts = list;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));

		scrollPanel = new JPanel();
		scrollPanel.setLayout(new BoxLayout(scrollPanel, BoxLayout.Y_AXIS));
		JScrollPane itemLog = new JScrollPane(scrollPanel);
		itemLog.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		JPanel removeOrReturn = new JPanel();
		GridBagLayout layout = new GridBagLayout();
		layout.rowWeights = new double[]{0.0, 1.0, 0.0};
		layout.columnWeights = new double[]{1.0};
		removeOrReturn.setLayout(layout);
		
		wrapper = new JPanel();
		wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
		txtrPleaseSelectAll = new JTextArea();
		txtrPleaseSelectAll.setText(
				"   Please select all" + "\n" 
				+ "the items you would" 
				+ "\n" + "    like to remove," 
				+ "\n" + "then press remove");
		gbc_txtrPleaseSelectAll = new GridBagConstraints();
		gbc_txtrPleaseSelectAll.insets = new Insets(0, 0, 5, 0);
		gbc_txtrPleaseSelectAll.anchor = GridBagConstraints.NORTH;
		gbc_txtrPleaseSelectAll.gridx = 0;
		gbc_txtrPleaseSelectAll.gridy = 1;
		max = txtrPleaseSelectAll.getPreferredSize();
		max.height = 75;
		txtrPleaseSelectAll.setMaximumSize(max);
		txtrPleaseSelectAll.setEditable(false);
		wrapper.add(txtrPleaseSelectAll);
		removeOrReturn.add(wrapper, gbc_txtrPleaseSelectAll);
		
		remove = new JButton("Remove");
		remove.setFont(new Font("Tahoma", Font.BOLD, 12));
		gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 3;
		remove.addActionListener(this);
		remove.setBackground(Color.LIGHT_GRAY);
		remove.setOpaque(true);
		remove.setBorderPainted(false);
		removeOrReturn.add(remove, gbc_btnNewButton);
		
		productsInLog = new JCheckBox [allProducts.size()];
		
		Product currentProduct;
		BarcodedProduct bProduct;
		PLUCodedProduct pProduct;
		
		BigDecimal productPrice;
		double price;
		double total = 0;
		for (int i = 0; i < allProducts.size(); i++) {
			currentProduct = allProducts.get(i);
			
			productPrice = currentProduct.getPrice();
			price = productPrice.doubleValue();
			total += price;
			if (currentProduct instanceof BarcodedProduct) {
				bProduct = (BarcodedProduct) currentProduct;
				productsInLog[i] = new JCheckBox(bProduct.getDescription() + "\t" + "$ " + price);
			} else {
				pProduct = (PLUCodedProduct) currentProduct;
				productsInLog[i] = new JCheckBox(pProduct.getDescription()+ "\t" + "$ " + price);
			}
			removable.put(productsInLog[i], currentProduct);
			
			productsInLog[i].setFont(new Font("Tahoma", Font.PLAIN, 14));
			scrollPanel.add(productsInLog[i]);
			scrollPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		}
		
		printTotalPrice = new JTextField("Total:" + "\t$ " + total);
		printTotalPrice.setFont(new Font("Tahoma", Font.BOLD, 14));
		gbc_printTotalprice = new GridBagConstraints();
		gbc_printTotalprice.insets = new Insets(0, 0, 5, 0);
		gbc_printTotalprice.fill = GridBagConstraints.HORIZONTAL;
		gbc_printTotalprice.gridx = 0;
		gbc_printTotalprice.gridy = 2;
		printTotalPrice.setEditable(false);
		removeOrReturn.add(printTotalPrice, gbc_printTotalprice);
		printTotalPrice.setColumns(10);
		
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, itemLog, removeOrReturn);
		splitPane.setDividerLocation(250);
		splitPane.setResizeWeight(0.5);
		
		contentPane.add(splitPane);
		
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		AreYouSure check = new AreYouSure();
		
		if (check.secondCheck()) {
			Product temp;
			for (int i = 0; i < productsInLog.length; i++) {
				if (productsInLog[i].isSelected()) {
					temp = removable.get(productsInLog[i]);
					// selectItemToRemove method
				}
			}
			setVisible(false);
		}
	}
	
}
