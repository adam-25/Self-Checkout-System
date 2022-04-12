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

import seng300.software.SelfCheckoutSystemLogic;

public class OverrideSystemBlock extends JDialog implements ActionListener {
	private SelfCheckoutSystemLogic logic;
	private JPanel overridePanel;
	private JLabel discDetected;
	private JLabel overrideBlock;
	private JButton approve;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JFrame frame = new JFrame();
					OverrideSystemBlock popup = new OverrideSystemBlock();
					frame.add(popup);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public OverrideSystemBlock(/*SelfCheckoutSystemLogic logic*/) {
		this.logic = logic;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		overridePanel = new JPanel();
		overridePanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(overridePanel);
		overridePanel.setLayout(new BoxLayout(overridePanel, BoxLayout.Y_AXIS));
		
		overridePanel.add(Box.createRigidArea(new Dimension(0, 90)));
		
		overrideBlock = new JLabel("Click button to override system block");
		overrideBlock.setFont(new Font("Tahoma", Font.PLAIN, 16));
		overrideBlock.setHorizontalAlignment(JLabel.CENTER);
		overrideBlock.setAlignmentX(CENTER_ALIGNMENT);
		overridePanel.add(overrideBlock);
		overridePanel.add(Box.createRigidArea(new Dimension(0, 30)));

		approve = new JButton("      Approve     ");
		approve.setFont(new Font("Tahoma", Font.BOLD, 15));
		approve.setAlignmentX(CENTER_ALIGNMENT);
		approve.setPreferredSize(new Dimension(100, 10));
		approve.addActionListener(this);
		approve.setBackground(Color.LIGHT_GRAY);
		approve.setOpaque(true);
		approve.setBorderPainted(false);
		overridePanel.add(approve);
		
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		logic.unblock();
		setVisible(false);
	}

}
