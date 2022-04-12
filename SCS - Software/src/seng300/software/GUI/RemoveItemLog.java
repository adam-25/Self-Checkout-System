package seng300.software.GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
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

import org.lsmr.selfcheckout.Item;
import org.lsmr.selfcheckout.products.BarcodedProduct;
import org.lsmr.selfcheckout.products.PLUCodedProduct;
import org.lsmr.selfcheckout.products.Product;

import seng300.software.AttendantLogic;
import seng300.software.PLUCodedWeightProduct;
import seng300.software.SelfCheckoutSystemLogic;
import seng300.software.exceptions.ProductNotFoundException;

import java.awt.Insets;
import javax.swing.JTextArea;
import java.awt.Font;
import javax.swing.JTextField;

public class RemoveItemLog extends JFrame implements ActionListener{

	public final JButton remove;
	public final JCheckBox[] productsInLog;
	public final Map<JCheckBox, Product> removable = new HashMap<>();
	private JPanel contentPane;
	private JPanel scrollPanel;
	private JPanel wrapper;
	private JSplitPane splitPane;
	private GridBagConstraints gbc_txtrPleaseSelectAll;
	private GridBagConstraints gbc_btnNewButton;
	private GridBagConstraints gbc_printTotalprice;
	private Dimension max;
	private JTextArea txtrPleaseSelectAll;
	private ArrayList<Product> allProducts;
	private JTextField printTotalPrice;
	private int cSystem;

	/**
	 * Create the frame.
	 */
	public RemoveItemLog(ArrayList<Product> list) {
		this.allProducts = list;
		
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);	// Changed from EXIT_ONCLOSE to HIDE_ON_CLOSE
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
				productsInLog[i] = new JCheckBox(bProduct.getDescription() + "    " + "$ " + price);
			} else {
				pProduct = (PLUCodedProduct) currentProduct;
				productsInLog[i] = new JCheckBox(pProduct.getDescription()+ "    " + "$ " + price);
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
//		setVisible(true);
	}

	public RemoveItemLog() {
		productsInLog = null;
		remove = new JButton();
		
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
		contentPane.add(Box.createRigidArea(new Dimension(115, 30)));
		
		JLabel noItemsToRemove = new JLabel("There are no items to be removed");
		contentPane.add(noItemsToRemove);
		
		setLocationRelativeTo(null);
		setResizable(false);
//		setVisible(true);
	}

	/**
	 * Method does not work as it should.
	 * Does not communicate with customer gui as planned.
	 * No time to debug. :(
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
//		AreYouSure check = new AreYouSure();
		
		SelfCheckoutSystemLogic logic = AttendantLogic.getInstance().getSCSLogic(cSystem);
		int indexToRemove = -1;
		for (int i = 0; i < productsInLog.length; i++)
		{
			if (productsInLog[i].isSelected())
			{
				indexToRemove = i;
				break;
			}
		}
		if (indexToRemove >= 0) {
			if (cSystem == 1 ||cSystem == 2 ||cSystem == 3 ||cSystem == 4 ||cSystem == 5 ||cSystem == 6) {
				
				Product temp;
//				for (int i = 0; i < productsInLog.length; i++) {
//					if (productsInLog[index].isSelected()) {
						temp = removable.get(productsInLog[indexToRemove]);
						Map<Product, Item> itemToRemove = new HashMap<>();
						if (logic.getBaggedProducts().contains(temp))
						{
							int j = logic.getBaggedProducts().indexOf(temp);
							itemToRemove.put(temp, logic.getBaggingArea().get(j));
						}
						else
						{
							itemToRemove.put(temp, null);
						}
						logic.attendantRemovesItem(itemToRemove);
						
//					}
				}
			}
//		}
//			setVisible(false);
			dispose();
	}
	
	public RemoveItemLog replaceList(ArrayList<Product> list, int system) {
		this.allProducts = list;
		this.cSystem = system;
		RemoveItemLog anotherLog = new RemoveItemLog(list);
		return anotherLog;
	}
}
