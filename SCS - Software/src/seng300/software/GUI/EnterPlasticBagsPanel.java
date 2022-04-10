package seng300.software.GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Color;

public class EnterPlasticBagsPanel extends JPanel {

	public final PinPad pinPad;
	/**
	 * Create the panel.
	 */
	public EnterPlasticBagsPanel()
	{
		setBackground(new Color(255, 255, 255));
		setBorder(new EmptyBorder(25, 25, 25, 25));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 400, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 10, 250, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel enterNumPlasticBagsLabel = new JLabel("How many plastic bags did you use?");
		enterNumPlasticBagsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		enterNumPlasticBagsLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		GridBagConstraints gbc_enterNumPlasticBagsLabel = new GridBagConstraints();
		gbc_enterNumPlasticBagsLabel.fill = GridBagConstraints.BOTH;
		gbc_enterNumPlasticBagsLabel.insets = new Insets(0, 0, 5, 5);
		gbc_enterNumPlasticBagsLabel.gridx = 1;
		gbc_enterNumPlasticBagsLabel.gridy = 1;
		add(enterNumPlasticBagsLabel, gbc_enterNumPlasticBagsLabel);
		pinPad = new PinPad();
		pinPad.setBackground(new Color(248, 248, 255));
		GridBagConstraints gbc_pinPad = new GridBagConstraints();
		gbc_pinPad.insets = new Insets(0, 0, 5, 5);
		gbc_pinPad.fill = GridBagConstraints.BOTH;
		gbc_pinPad.gridx = 1;
		gbc_pinPad.gridy = 3;
		add(pinPad, gbc_pinPad);
	}
	
	/**
	 * Launch the application. TO BE USED FOR TESTING ONLY!
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JFrame frame = new JFrame();
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.getContentPane().add(new EnterPlasticBagsPanel());
					frame.pack();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
