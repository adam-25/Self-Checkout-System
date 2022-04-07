package seng300.software;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;

public class WeightDiscrepancyPopup extends JFrame implements ActionListener {
	private SelfCheckoutSystemLogic logic;
	private JFrame weightDiscPopup;
	private JPanel weightDiscPanel;
	private JLabel weightDisc;
	private JLabel overrideBlock;
	private JButton approve;

	/**
	 * The popup frame
	 */
	public WeightDiscrepancyPopup(SelfCheckoutSystemLogic logic) {
		weightDiscPopup = new JFrame();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		weightDiscPanel = new JPanel();
		weightDiscPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(weightDiscPanel);
		weightDiscPanel.setLayout(new GridBagLayout());
		
		weightDisc = new JLabel("Station detects weight discrepancy!");
		weightDisc.setForeground(new Color(220, 20, 60));
		weightDisc.setFont(new Font("Tahoma", Font.BOLD, 20));
		weightDiscPanel.add(weightDisc, "cell 7 1,alignx center");
		
		overrideBlock = new JLabel("Click button to override system block.");
		overrideBlock.setFont(new Font("Tahoma", Font.PLAIN, 15));
		weightDiscPanel.add(overrideBlock, "cell 7 2,alignx center");
		
		approve = new JButton("Approve");
		approve.setPreferredSize(new Dimension(20, 5));
		approve.setFont(new Font("Tahoma", Font.BOLD, 18));
		approve.setForeground(new Color(0, 0, 0));
		approve.addActionListener(this);
		weightDiscPanel.add(approve, "cell 7 3,grow");
		setLocationRelativeTo(null);
		
		weightDiscPopup.setVisible(true);
		this.logic = logic;
	}

	@Override	// When approve btn is pressed, unlbock
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		logic.unblock();
	}

}
