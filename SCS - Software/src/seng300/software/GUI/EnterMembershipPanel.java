package seng300.software.GUI;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.Insets;
import java.awt.Font;
import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class EnterMembershipPanel extends JPanel {

	public final JButton cancelBtn;
	public final PinPad pinPad;
	
	private JLabel errorMsgLabel;
	/**
	 * Create the panel.
	 */
	public EnterMembershipPanel() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 281, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 202, 25, 50, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		errorMsgLabel = new JLabel("Could not find member <number>. Please try again.");
		errorMsgLabel.setHorizontalAlignment(SwingConstants.CENTER);
		errorMsgLabel.setForeground(new Color(255, 0, 0));
		errorMsgLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		GridBagConstraints gbc_errorMsgLabel = new GridBagConstraints();
		gbc_errorMsgLabel.insets = new Insets(0, 0, 5, 5);
		gbc_errorMsgLabel.gridx = 1;
		gbc_errorMsgLabel.gridy = 1;
		add(errorMsgLabel, gbc_errorMsgLabel);
		pinPad = new PinPad();
		GridBagConstraints gbc_pinPad = new GridBagConstraints();
		gbc_pinPad.fill = GridBagConstraints.HORIZONTAL;
		gbc_pinPad.insets = new Insets(0, 0, 5, 5);
		gbc_pinPad.anchor = GridBagConstraints.NORTH;
		gbc_pinPad.gridx = 1;
		gbc_pinPad.gridy = 2;
		add(pinPad, gbc_pinPad);
		errorMsgLabel.setVisible(false);
		
		cancelBtn = new JButton("Cancel");
		cancelBtn.setForeground(new Color(255, 0, 0));
		cancelBtn.setBackground(new Color(255, 228, 225));
		cancelBtn.setFont(new Font("Tahoma", Font.BOLD, 16));
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton.gridx = 1;
		gbc_btnNewButton.gridy = 4;
		add(cancelBtn, gbc_btnNewButton);
	}
	
	/**
	 * Launch the application. TO BE USED FOR TESTING ONLY!
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JFrame frame = new JFrame();
					frame.getContentPane().add(new EnterMembershipPanel());
					frame.setBounds(100, 100, 450, 450);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void showErrorMsg()
	{
		if (!errorMsgLabel.isVisible())
		{
			errorMsgLabel.setVisible(true);
		}
	}
	
	public void hideErrorMsg()
	{
		if (errorMsgLabel.isVisible())
		{
			errorMsgLabel.setVisible(false);
		}
	}

}
