package seng300.software.GUI;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import java.awt.EventQueue;

public class CoinPaymentPanel extends JPanel {

	public final JButton toonieBtn;
	public final JButton doneBtn;
	public final JButton nickelBtn;
	public final JButton quarterBtn;
	public final JButton loonieBtn;
	public final JButton dimeBtn;

	private JLabel totalCoinsLabel;
	private JLabel totalCoinsValue;
	
	/**
	 * Create the panel.
	 */
	public CoinPaymentPanel()
	{
		setBackground(new Color(255, 255, 255));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{5, 200, 10, 200, 5, 0};
		gridBagLayout.rowHeights = new int[]{5, 0, 10, 0, 10, 100, 25, 0, 25, 50, 5, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel selectCoinLabel = new JLabel("Select Coin");
		selectCoinLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		GridBagConstraints gbc_selectCoinLabel = new GridBagConstraints();
		gbc_selectCoinLabel.gridwidth = 3;
		gbc_selectCoinLabel.insets = new Insets(0, 0, 5, 5);
		gbc_selectCoinLabel.gridx = 1;
		gbc_selectCoinLabel.gridy = 3;
		add(selectCoinLabel, gbc_selectCoinLabel);
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.gridwidth = 3;
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 5;
		add(panel, gbc_panel);
		panel.setLayout(new GridLayout(1, 5, 5, 5));
		
		toonieBtn = new JButton("$2");
		toonieBtn.setBackground(new Color(255, 248, 220));
		toonieBtn.setForeground(new Color(184, 134, 11));
		toonieBtn.setFont(new Font("Tahoma", Font.BOLD, 24));
		panel.add(toonieBtn);
		
		loonieBtn = new JButton("$1");
		loonieBtn.setBackground(new Color(255, 248, 220));
		loonieBtn.setForeground(new Color(184, 134, 11));
		loonieBtn.setFont(new Font("Tahoma", Font.BOLD, 24));
		panel.add(loonieBtn);
		
		quarterBtn = new JButton("$0.25");
		quarterBtn.setBackground(new Color(255, 248, 220));
		quarterBtn.setForeground(new Color(184, 134, 11));
		quarterBtn.setFont(new Font("Tahoma", Font.BOLD, 24));
		panel.add(quarterBtn);
		
		dimeBtn = new JButton("$0.10");
		dimeBtn.setBackground(new Color(255, 248, 220));
		dimeBtn.setForeground(new Color(184, 134, 11));
		dimeBtn.setFont(new Font("Tahoma", Font.BOLD, 24));
		panel.add(dimeBtn);
		
		nickelBtn = new JButton("$0.05");
		nickelBtn.setBackground(new Color(255, 248, 220));
		nickelBtn.setForeground(new Color(184, 134, 11));
		nickelBtn.setFont(new Font("Tahoma", Font.BOLD, 24));
		panel.add(nickelBtn);
		
		totalCoinsLabel = new JLabel("Total Coins");
		totalCoinsLabel.setBackground(new Color(255, 255, 255));
		totalCoinsLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		GridBagConstraints gbc_totalCoinsLabel = new GridBagConstraints();
		gbc_totalCoinsLabel.fill = GridBagConstraints.HORIZONTAL;
		gbc_totalCoinsLabel.insets = new Insets(0, 0, 5, 5);
		gbc_totalCoinsLabel.gridx = 1;
		gbc_totalCoinsLabel.gridy = 7;
		add(totalCoinsLabel, gbc_totalCoinsLabel);
		
		totalCoinsValue = new JLabel("$ 000.00");
		totalCoinsValue.setBackground(new Color(255, 255, 255));
		totalCoinsValue.setFont(new Font("Tahoma", Font.BOLD, 24));
		GridBagConstraints gbc_totalCoinsValue = new GridBagConstraints();
		gbc_totalCoinsValue.anchor = GridBagConstraints.EAST;
		gbc_totalCoinsValue.insets = new Insets(0, 0, 5, 5);
		gbc_totalCoinsValue.gridx = 3;
		gbc_totalCoinsValue.gridy = 7;
		add(totalCoinsValue, gbc_totalCoinsValue);
		
		doneBtn = new JButton("Done");
		doneBtn.setBackground(new Color(240, 255, 240));
		doneBtn.setForeground(new Color(0, 128, 0));
		doneBtn.setFont(new Font("Tahoma", Font.BOLD, 16));
		GridBagConstraints gbc_doneBtn = new GridBagConstraints();
		gbc_doneBtn.insets = new Insets(0, 0, 5, 5);
		gbc_doneBtn.fill = GridBagConstraints.BOTH;
		gbc_doneBtn.gridwidth = 3;
		gbc_doneBtn.gridx = 1;
		gbc_doneBtn.gridy = 9;
		add(doneBtn, gbc_doneBtn);

	}
	
	public void setTotalPayWithCoin(BigDecimal total)
	{
		totalCoinsValue.setText("$ " + total.setScale(2, RoundingMode.HALF_EVEN).toPlainString());
		totalCoinsValue.validate();
	}
	
	/**
	 * Launch the application. TO BE USED FOR TESTING ONLY!
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JFrame frame = new JFrame();
					frame.getContentPane().add(new CoinPaymentPanel());
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
