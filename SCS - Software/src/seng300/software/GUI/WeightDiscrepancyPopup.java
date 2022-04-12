package seng300.software.GUI;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import seng300.software.SelfCheckoutSystemLogic;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;

public class WeightDiscrepancyPopup extends JDialog implements ActionListener {
	private SelfCheckoutSystemLogic logic;
	private JPanel weightDiscPanel;
	private JLabel discDetected;
	private JLabel overrideBlock;
	private JButton approve;
	private JButton buttonToChange;

	/**
	 * The popup frame
	 */
	public WeightDiscrepancyPopup(SelfCheckoutSystemLogic logic, JButton buttonToChange) {
		this.logic = logic;
		this.buttonToChange = buttonToChange;
		
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		weightDiscPanel = new JPanel();
		weightDiscPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(weightDiscPanel);
		weightDiscPanel.setLayout(new BoxLayout(weightDiscPanel, BoxLayout.Y_AXIS));
		
		weightDiscPanel.add(Box.createRigidArea(new Dimension(0, 30)));
		
		discDetected = new JLabel("Station detects weight discrepancy!");
		discDetected.setForeground(new Color(255, 0, 0));
		discDetected.setHorizontalAlignment(JLabel.CENTER);
		discDetected.setFont(new Font("Tahoma", Font.PLAIN, 20));
		discDetected.setAlignmentX(CENTER_ALIGNMENT);
		weightDiscPanel.add(discDetected);
		weightDiscPanel.add(Box.createRigidArea(new Dimension(0, 30)));
		
		overrideBlock = new JLabel("Click button to override system block.");
		overrideBlock.setFont(new Font("Tahoma", Font.PLAIN, 16));
		overrideBlock.setHorizontalAlignment(JLabel.CENTER);
		overrideBlock.setAlignmentX(CENTER_ALIGNMENT);
		weightDiscPanel.add(overrideBlock);
		weightDiscPanel.add(Box.createRigidArea(new Dimension(0, 60)));

		approve = new JButton("      Approve     ");
		approve.setFont(new Font("Tahoma", Font.BOLD, 15));
		approve.setAlignmentX(CENTER_ALIGNMENT);
		approve.setPreferredSize(new Dimension(100, 10));
		approve.addActionListener(this);
		approve.setBackground(Color.LIGHT_GRAY);
		approve.setOpaque(true);
		approve.setBorderPainted(false);
		weightDiscPanel.add(approve);
		
		setLocationRelativeTo(null);
		setResizable(false);
//		setVisible(true);
	}

	@Override	// When approve btn is pressed, unlbock
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		logic.resetWeightOnScale();
		logic.unblock();
		buttonToChange.setBackground(Color.GRAY);
		setVisible(false);
	}

}