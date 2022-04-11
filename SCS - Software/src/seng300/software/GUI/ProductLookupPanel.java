package seng300.software.GUI;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JLabel;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import org.lsmr.selfcheckout.products.PLUCodedProduct;
import org.lsmr.selfcheckout.products.Product;
import javax.swing.SwingConstants;

public class ProductLookupPanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6313899205929114140L;
	
	public final JButton returnButton;
	public final ArrayList<KeyboardButton> keyboardBtns = new ArrayList<>();
	
	private JTextField searchField;
	private String searchText;
	private JPanel resultsPanel;

	/**
	 * Create the panel.
	 */
	public ProductLookupPanel() {
		setBorder(new EmptyBorder(10, 10, 10, 10));
		setBackground(new Color(255, 255, 255));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 25, 10, 200, 25, 50, 25, 50, 50, 50, 50, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel resultsLabel = new JLabel("Results");
		resultsLabel.setForeground(new Color(25, 25, 112));
		resultsLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		GridBagConstraints gbc_resultsLabel = new GridBagConstraints();
		gbc_resultsLabel.anchor = GridBagConstraints.WEST;
		gbc_resultsLabel.insets = new Insets(0, 0, 5, 5);
		gbc_resultsLabel.gridx = 1;
		gbc_resultsLabel.gridy = 1;
		add(resultsLabel, gbc_resultsLabel);
		
		returnButton = new JButton("Return");
		returnButton.setForeground(new Color(139, 0, 0));
		returnButton.setBackground(new Color(255, 228, 225));
		returnButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		GridBagConstraints gbc_returnButton = new GridBagConstraints();
		gbc_returnButton.fill = GridBagConstraints.BOTH;
		gbc_returnButton.insets = new Insets(0, 0, 5, 5);
		gbc_returnButton.gridx = 2;
		gbc_returnButton.gridy = 1;
		add(returnButton, gbc_returnButton);

		resultsPanel = new JPanel();
		resultsPanel.setLayout(new GridLayout(0, 3, 5, 5));
		resultsPanel.setBackground(new Color(240, 248, 255));
		GridBagConstraints gbc_resultsPanel = new GridBagConstraints();
		gbc_resultsPanel.gridwidth = 2;
		gbc_resultsPanel.fill = GridBagConstraints.BOTH;
		gbc_resultsPanel.insets = new Insets(0, 0, 5, 5);
		gbc_resultsPanel.gridx = 1;
		gbc_resultsPanel.gridy = 3;
		add(resultsPanel, gbc_resultsPanel);
		resultsPanel.setPreferredSize(new Dimension(3, 100));
		resultsPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
			
		searchField = new JTextField();
		searchField.setForeground(new Color(0, 0, 128));
		searchField.setFont(new Font("Tahoma", Font.BOLD, 24));
		searchField.setEnabled(false);
		searchField.setBackground(new Color(255, 255, 255));
		GridBagConstraints gbc_searchField = new GridBagConstraints();
		gbc_searchField.gridwidth = 2;
		gbc_searchField.insets = new Insets(0, 0, 5, 5);
		gbc_searchField.fill = GridBagConstraints.BOTH;
		gbc_searchField.gridx = 1;
		gbc_searchField.gridy = 5;
		add(searchField, gbc_searchField);
		searchField.setColumns(10);
		searchText = searchField.getText();
		
		JPanel topRow = new JPanel();
		GridBagConstraints gbc_topRow = new GridBagConstraints();
		gbc_topRow.gridwidth = 2;
		gbc_topRow.insets = new Insets(0, 0, 5, 5);
		gbc_topRow.fill = GridBagConstraints.VERTICAL;
		gbc_topRow.gridx = 1;
		gbc_topRow.gridy = 7;
		add(topRow, gbc_topRow);
		GridBagLayout gbl_topRow = new GridBagLayout();
		gbl_topRow.columnWidths = new int[]{65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 0, 0};
		gbl_topRow.rowHeights = new int[]{59, 0};
		gbl_topRow.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_topRow.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		topRow.setLayout(gbl_topRow);
		
		KeyboardButton qBtn = new KeyboardButton(KeyboardKey.Q);
		keyboardBtns.add(qBtn);
		GridBagConstraints gbc_qBtn = new GridBagConstraints();
		gbc_qBtn.fill = GridBagConstraints.BOTH;
		gbc_qBtn.insets = new Insets(0, 0, 0, 5);
		gbc_qBtn.gridx = 0;
		gbc_qBtn.gridy = 0;
		topRow.add(qBtn, gbc_qBtn);
		
		KeyboardButton wBtn = new KeyboardButton(KeyboardKey.W);
		keyboardBtns.add(wBtn);
		GridBagConstraints gbc_wBtn = new GridBagConstraints();
		gbc_wBtn.fill = GridBagConstraints.BOTH;
		gbc_wBtn.insets = new Insets(0, 0, 0, 5);
		gbc_wBtn.gridx = 1;
		gbc_wBtn.gridy = 0;
		topRow.add(wBtn, gbc_wBtn);
		
		KeyboardButton eBtn = new KeyboardButton(KeyboardKey.E);
		keyboardBtns.add(eBtn);
		GridBagConstraints gbc_eBtn = new GridBagConstraints();
		gbc_eBtn.fill = GridBagConstraints.BOTH;
		gbc_eBtn.insets = new Insets(0, 0, 0, 5);
		gbc_eBtn.gridx = 2;
		gbc_eBtn.gridy = 0;
		topRow.add(eBtn, gbc_eBtn);
		
		KeyboardButton rBtn = new KeyboardButton(KeyboardKey.R);
		keyboardBtns.add(rBtn);
		GridBagConstraints gbc_rBtn = new GridBagConstraints();
		gbc_rBtn.fill = GridBagConstraints.BOTH;
		gbc_rBtn.insets = new Insets(0, 0, 0, 5);
		gbc_rBtn.gridx = 3;
		gbc_rBtn.gridy = 0;
		topRow.add(rBtn, gbc_rBtn);
		
		KeyboardButton tBtn = new KeyboardButton(KeyboardKey.T);
		keyboardBtns.add(tBtn);
		GridBagConstraints gbc_tBtn = new GridBagConstraints();
		gbc_tBtn.fill = GridBagConstraints.BOTH;
		gbc_tBtn.insets = new Insets(0, 0, 0, 5);
		gbc_tBtn.gridx = 4;
		gbc_tBtn.gridy = 0;
		topRow.add(tBtn, gbc_tBtn);
		
		KeyboardButton yBtn = new KeyboardButton(KeyboardKey.Y);
		keyboardBtns.add(yBtn);
		GridBagConstraints gbc_yBtn = new GridBagConstraints();
		gbc_yBtn.fill = GridBagConstraints.BOTH;
		gbc_yBtn.insets = new Insets(0, 0, 0, 5);
		gbc_yBtn.gridx = 5;
		gbc_yBtn.gridy = 0;
		topRow.add(yBtn, gbc_yBtn);
		
		KeyboardButton uBtn = new KeyboardButton(KeyboardKey.U);
		keyboardBtns.add(uBtn);
		GridBagConstraints gbc_uBtn = new GridBagConstraints();
		gbc_uBtn.fill = GridBagConstraints.BOTH;
		gbc_uBtn.insets = new Insets(0, 0, 0, 5);
		gbc_uBtn.gridx = 6;
		gbc_uBtn.gridy = 0;
		topRow.add(uBtn, gbc_uBtn);
		
		KeyboardButton iBtn = new KeyboardButton(KeyboardKey.I);
		keyboardBtns.add(iBtn);
		GridBagConstraints gbc_iBtn = new GridBagConstraints();
		gbc_iBtn.fill = GridBagConstraints.BOTH;
		gbc_iBtn.insets = new Insets(0, 0, 0, 5);
		gbc_iBtn.gridx = 7;
		gbc_iBtn.gridy = 0;
		topRow.add(iBtn, gbc_iBtn);
		
		KeyboardButton oBtn = new KeyboardButton(KeyboardKey.O);
		keyboardBtns.add(oBtn);
		GridBagConstraints gbc_oBtn = new GridBagConstraints();
		gbc_oBtn.fill = GridBagConstraints.BOTH;
		gbc_oBtn.insets = new Insets(0, 0, 0, 5);
		gbc_oBtn.gridx = 8;
		gbc_oBtn.gridy = 0;
		topRow.add(oBtn, gbc_oBtn);
		
		KeyboardButton pBtn = new KeyboardButton(KeyboardKey.P);
		keyboardBtns.add(pBtn);
		GridBagConstraints gbc_pBtn = new GridBagConstraints();
		gbc_pBtn.insets = new Insets(0, 0, 0, 5);
		gbc_pBtn.fill = GridBagConstraints.BOTH;
		gbc_pBtn.gridx = 9;
		gbc_pBtn.gridy = 0;
		topRow.add(pBtn, gbc_pBtn);
		
		KeyboardButton backBtn = new KeyboardButton(KeyboardKey.BACK);
		keyboardBtns.add(backBtn);
		GridBagConstraints gbc_backBtn = new GridBagConstraints();
		gbc_backBtn.fill = GridBagConstraints.BOTH;
		gbc_backBtn.gridx = 10;
		gbc_backBtn.gridy = 0;
		topRow.add(backBtn, gbc_backBtn);
		
		JPanel middleRow = new JPanel();
		GridBagConstraints gbc_middleRow = new GridBagConstraints();
		gbc_middleRow.gridwidth = 2;
		gbc_middleRow.insets = new Insets(0, 0, 5, 5);
		gbc_middleRow.fill = GridBagConstraints.VERTICAL;
		gbc_middleRow.gridx = 1;
		gbc_middleRow.gridy = 8;
		add(middleRow, gbc_middleRow);
		GridBagLayout gbl_middleRow = new GridBagLayout();
		gbl_middleRow.columnWidths = new int[]{65, 65, 65, 65, 65, 65, 65, 65, 65, 0, 0};
		gbl_middleRow.rowHeights = new int[]{59, 0};
		gbl_middleRow.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_middleRow.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		middleRow.setLayout(gbl_middleRow);
		
		KeyboardButton aBtn = new KeyboardButton(KeyboardKey.A);
		keyboardBtns.add(aBtn);
		GridBagConstraints gbc_aBtn = new GridBagConstraints();
		gbc_aBtn.fill = GridBagConstraints.BOTH;
		gbc_aBtn.insets = new Insets(0, 0, 0, 5);
		gbc_aBtn.gridx = 0;
		gbc_aBtn.gridy = 0;
		middleRow.add(aBtn, gbc_aBtn);
		
		KeyboardButton sBtn = new KeyboardButton(KeyboardKey.S);
		keyboardBtns.add(sBtn);
		GridBagConstraints gbc_sBtn = new GridBagConstraints();
		gbc_sBtn.fill = GridBagConstraints.BOTH;
		gbc_sBtn.insets = new Insets(0, 0, 0, 5);
		gbc_sBtn.gridx = 1;
		gbc_sBtn.gridy = 0;
		middleRow.add(sBtn, gbc_sBtn);
		
		KeyboardButton dBtn = new KeyboardButton(KeyboardKey.D);
		keyboardBtns.add(dBtn);
		GridBagConstraints gbc_dBtn = new GridBagConstraints();
		gbc_dBtn.fill = GridBagConstraints.BOTH;
		gbc_dBtn.insets = new Insets(0, 0, 0, 5);
		gbc_dBtn.gridx = 2;
		gbc_dBtn.gridy = 0;
		middleRow.add(dBtn, gbc_dBtn);
		
		KeyboardButton fBtn = new KeyboardButton(KeyboardKey.F);
		keyboardBtns.add(fBtn);
		GridBagConstraints gbc_fBtn = new GridBagConstraints();
		gbc_fBtn.fill = GridBagConstraints.BOTH;
		gbc_fBtn.insets = new Insets(0, 0, 0, 5);
		gbc_fBtn.gridx = 3;
		gbc_fBtn.gridy = 0;
		middleRow.add(fBtn, gbc_fBtn);
		
		KeyboardButton gBtn = new KeyboardButton(KeyboardKey.G);
		keyboardBtns.add(gBtn);
		GridBagConstraints gbc_gBtn = new GridBagConstraints();
		gbc_gBtn.fill = GridBagConstraints.BOTH;
		gbc_gBtn.insets = new Insets(0, 0, 0, 5);
		gbc_gBtn.gridx = 4;
		gbc_gBtn.gridy = 0;
		middleRow.add(gBtn, gbc_gBtn);
		
		KeyboardButton hBtn = new KeyboardButton(KeyboardKey.H);
		keyboardBtns.add(hBtn);
		GridBagConstraints gbc_hBtn = new GridBagConstraints();
		gbc_hBtn.fill = GridBagConstraints.BOTH;
		gbc_hBtn.insets = new Insets(0, 0, 0, 5);
		gbc_hBtn.gridx = 5;
		gbc_hBtn.gridy = 0;
		middleRow.add(hBtn, gbc_hBtn);
		
		KeyboardButton jBtn = new KeyboardButton(KeyboardKey.J);
		keyboardBtns.add(jBtn);
		GridBagConstraints gbc_jBtn = new GridBagConstraints();
		gbc_jBtn.fill = GridBagConstraints.BOTH;
		gbc_jBtn.insets = new Insets(0, 0, 0, 5);
		gbc_jBtn.gridx = 6;
		gbc_jBtn.gridy = 0;
		middleRow.add(jBtn, gbc_jBtn);
		
		KeyboardButton kBtn = new KeyboardButton(KeyboardKey.K);
		keyboardBtns.add(kBtn);
		GridBagConstraints gbc_kBtn = new GridBagConstraints();
		gbc_kBtn.fill = GridBagConstraints.BOTH;
		gbc_kBtn.insets = new Insets(0, 0, 0, 5);
		gbc_kBtn.gridx = 7;
		gbc_kBtn.gridy = 0;
		middleRow.add(kBtn, gbc_kBtn);
		
		KeyboardButton lBtn = new KeyboardButton(KeyboardKey.L);
		keyboardBtns.add(lBtn);
		GridBagConstraints gbc_lBtn = new GridBagConstraints();
		gbc_lBtn.insets = new Insets(0, 0, 0, 5);
		gbc_lBtn.fill = GridBagConstraints.BOTH;
		gbc_lBtn.gridx = 8;
		gbc_lBtn.gridy = 0;
		middleRow.add(lBtn, gbc_lBtn);
		
		KeyboardButton enterBtn = new KeyboardButton(KeyboardKey.ENTER);
		keyboardBtns.add(enterBtn);
		GridBagConstraints gbc_enterBtn = new GridBagConstraints();
		gbc_enterBtn.fill = GridBagConstraints.BOTH;
		gbc_enterBtn.gridx = 9;
		gbc_enterBtn.gridy = 0;
		middleRow.add(enterBtn, gbc_enterBtn);
		
		JPanel bottomRow = new JPanel();
		GridBagConstraints gbc_bottomRow = new GridBagConstraints();
		gbc_bottomRow.gridwidth = 2;
		gbc_bottomRow.insets = new Insets(0, 0, 5, 5);
		gbc_bottomRow.fill = GridBagConstraints.VERTICAL;
		gbc_bottomRow.gridx = 1;
		gbc_bottomRow.gridy = 9;
		add(bottomRow, gbc_bottomRow);
		GridBagLayout gbl_bottomRow = new GridBagLayout();
		gbl_bottomRow.columnWidths = new int[]{65, 65, 65, 65, 65, 65, 65, 0, 0};
		gbl_bottomRow.rowHeights = new int[]{59, 0};
		gbl_bottomRow.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_bottomRow.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		bottomRow.setLayout(gbl_bottomRow);
		
		KeyboardButton zBtn = new KeyboardButton(KeyboardKey.Z);
		keyboardBtns.add(zBtn);
		GridBagConstraints gbc_zBtn = new GridBagConstraints();
		gbc_zBtn.fill = GridBagConstraints.BOTH;
		gbc_zBtn.insets = new Insets(0, 0, 0, 5);
		gbc_zBtn.gridx = 0;
		gbc_zBtn.gridy = 0;
		bottomRow.add(zBtn, gbc_zBtn);
		
		KeyboardButton xBtn = new KeyboardButton(KeyboardKey.X);
		keyboardBtns.add(xBtn);
		GridBagConstraints gbc_xBtn = new GridBagConstraints();
		gbc_xBtn.fill = GridBagConstraints.BOTH;
		gbc_xBtn.insets = new Insets(0, 0, 0, 5);
		gbc_xBtn.gridx = 1;
		gbc_xBtn.gridy = 0;
		bottomRow.add(xBtn, gbc_xBtn);
		
		KeyboardButton cBtn = new KeyboardButton(KeyboardKey.C);
		keyboardBtns.add(cBtn);
		GridBagConstraints gbc_cBtn = new GridBagConstraints();
		gbc_cBtn.fill = GridBagConstraints.BOTH;
		gbc_cBtn.insets = new Insets(0, 0, 0, 5);
		gbc_cBtn.gridx = 2;
		gbc_cBtn.gridy = 0;
		bottomRow.add(cBtn, gbc_cBtn);
		
		KeyboardButton vBtn = new KeyboardButton(KeyboardKey.V);
		keyboardBtns.add(vBtn);
		GridBagConstraints gbc_vBtn = new GridBagConstraints();
		gbc_vBtn.fill = GridBagConstraints.BOTH;
		gbc_vBtn.insets = new Insets(0, 0, 0, 5);
		gbc_vBtn.gridx = 3;
		gbc_vBtn.gridy = 0;
		bottomRow.add(vBtn, gbc_vBtn);
		
		KeyboardButton bBtn = new KeyboardButton(KeyboardKey.B);
		keyboardBtns.add(bBtn);
		GridBagConstraints gbc_bBtn = new GridBagConstraints();
		gbc_bBtn.fill = GridBagConstraints.BOTH;
		gbc_bBtn.insets = new Insets(0, 0, 0, 5);
		gbc_bBtn.gridx = 4;
		gbc_bBtn.gridy = 0;
		bottomRow.add(bBtn, gbc_bBtn);
		
		KeyboardButton nBtn = new KeyboardButton(KeyboardKey.N);
		keyboardBtns.add(nBtn);
		GridBagConstraints gbc_nBtn = new GridBagConstraints();
		gbc_nBtn.fill = GridBagConstraints.BOTH;
		gbc_nBtn.insets = new Insets(0, 0, 0, 5);
		gbc_nBtn.gridx = 5;
		gbc_nBtn.gridy = 0;
		bottomRow.add(nBtn, gbc_nBtn);
		
		KeyboardButton mBtn = new KeyboardButton(KeyboardKey.M);
		keyboardBtns.add(mBtn);
		GridBagConstraints gbc_mBtn = new GridBagConstraints();
		gbc_mBtn.insets = new Insets(0, 0, 0, 5);
		gbc_mBtn.fill = GridBagConstraints.BOTH;
		gbc_mBtn.gridx = 6;
		gbc_mBtn.gridy = 0;
		bottomRow.add(mBtn, gbc_mBtn);
		
		KeyboardButton clearBtn = new KeyboardButton(KeyboardKey.CLEAR);
		keyboardBtns.add(clearBtn);
		GridBagConstraints gbc_clearBtn = new GridBagConstraints();
		gbc_clearBtn.fill = GridBagConstraints.BOTH;
		gbc_clearBtn.gridx = 7;
		gbc_clearBtn.gridy = 0;
		bottomRow.add(clearBtn, gbc_clearBtn);
		
		KeyboardButton spacebarBtn = new KeyboardButton(KeyboardKey.SPACE);
		keyboardBtns.add(spacebarBtn);
		spacebarBtn.setPreferredSize(new Dimension(400, 50));
		GridBagConstraints gbc_spacebarBtn = new GridBagConstraints();
		gbc_spacebarBtn.gridwidth = 2;
		gbc_spacebarBtn.fill = GridBagConstraints.VERTICAL;
		gbc_spacebarBtn.insets = new Insets(0, 0, 5, 5);
		gbc_spacebarBtn.gridx = 1;
		gbc_spacebarBtn.gridy = 10;
		add(spacebarBtn, gbc_spacebarBtn);
	}

	public String getSearchText()
	{
		return this.searchText;
	}
	
	public void setSearchText(String newText)
	{
		searchText = newText;
		searchField.setText(newText);
	}
	
	class EmptyResultButton extends JButton
	{
		
		public EmptyResultButton()
		{
			super();
			setPreferredSize(new Dimension(75, 75));
			setMinimumSize(new Dimension(75, 75));
			setMaximumSize(new Dimension(75, 75));
			setVisible(false);
			setEnabled(false);
		}
	}

	public void displayProducts(List<LookupResultButton> res)
	{
		resultsPanel.removeAll();
		resultsPanel.validate(); 
		resultsPanel.repaint();
		if (res.isEmpty())
		{
			JLabel lbl = new JLabel("Product Not Found");
			lbl.setForeground(new Color(25, 25, 112));
			lbl.setHorizontalAlignment(SwingConstants.CENTER);
			lbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
			resultsPanel.add(new JLabel(""));
			resultsPanel.add(lbl);
		}
		else
		{	// display two rows of buttons
			int numCols = 3;
			int numRows = 2;
			for (int i = 0; i < (numCols * numRows); i++)
			{
				if (i < res.size())
				{
					resultsPanel.add(res.get(i));
				}
				else
				{
					resultsPanel.add(new EmptyResultButton());
				}
			}
		}
		resultsPanel.validate();
		resultsPanel.repaint();
	}
	
	public void reset()
	{
		searchText = "";
		searchField.setText(searchText);
		resultsPanel.removeAll();
		resultsPanel.validate();
		resultsPanel.repaint();
		validate();
	}
}
