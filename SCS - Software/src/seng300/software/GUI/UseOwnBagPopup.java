package seng300.software.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import seng300.software.AttendantLogic;
import seng300.software.SelfCheckoutSystemLogic;

public class UseOwnBagPopup extends JDialog implements ActionListener {
	private SelfCheckoutSystemLogic logic;
	private JPanel ownBagPanel;
	private JLabel discDetected;
	private JLabel overrideBlock;
	private JButton approve;
	private JButton buttonToChange;
	private int systemNum;
	/**
	 * Create the frame.
	 */
	public UseOwnBagPopup(SelfCheckoutSystemLogic logic, int systemNum, JButton buttonToChange) {
		this.logic = logic;
		this.systemNum = systemNum;
		this.buttonToChange = buttonToChange;
		
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		ownBagPanel = new JPanel();
		ownBagPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(ownBagPanel);
		ownBagPanel.setLayout(new BoxLayout(ownBagPanel, BoxLayout.Y_AXIS));
		
		ownBagPanel.add(Box.createRigidArea(new Dimension(0, 90)));
				
		overrideBlock = new JLabel("Customer at Station " + systemNum + " would like to use their own bags.");
		overrideBlock.setFont(new Font("Tahoma", Font.PLAIN, 16));
		overrideBlock.setHorizontalAlignment(JLabel.CENTER);
		overrideBlock.setAlignmentX(CENTER_ALIGNMENT);
		ownBagPanel.add(overrideBlock);
		ownBagPanel.add(Box.createRigidArea(new Dimension(0, 30)));

		approve = new JButton("      Approve     ");
		approve.setFont(new Font("Tahoma", Font.BOLD, 15));
		approve.setAlignmentX(CENTER_ALIGNMENT);
		approve.setPreferredSize(new Dimension(100, 10));
		approve.addActionListener(this);
		approve.setBackground(Color.LIGHT_GRAY);
		approve.setOpaque(true);
		approve.setBorderPainted(false);
		ownBagPanel.add(approve);
		
		setLocationRelativeTo(null);
		setResizable(false);
//		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		logic.unblock();
		buttonToChange.setBackground(Color.GRAY);
		setVisible(false);
	}

}
