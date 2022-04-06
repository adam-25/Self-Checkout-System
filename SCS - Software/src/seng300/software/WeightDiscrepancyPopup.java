package seng300.software;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class WeightDiscrepancyPopup implements ActionListener {
	private SelfCheckoutSystemLogic logic;
	JFrame weightDiscPop;
	JPanel panelDiscPop;
	JLabel discDetected;
	JLabel clickToOverried;
	JButton approve;
	
	public WeightDiscrepancyPopup(SelfCheckoutSystemLogic logic) {
		// Need to implement an actionlistener that calls unblock
		// from a specified checkoutstation after approve button is pressed
		weightDiscPop = new JFrame();
		weightDiscPop.setTitle(null);
		
		panelDiscPop = new JPanel();
		panelDiscPop.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
		panelDiscPop.setLayout(new GridLayout(3,0));
		
		discDetected = new JLabel("Station # detects weight discrepancy!");
		discDetected.setForeground(Color.red);
		
		clickToOverried = new JLabel("Click button to override system block.");
		
		approve = new JButton("Aprrove");
		approve.addActionListener(this);
		
		panelDiscPop.add(discDetected);
		panelDiscPop.add(clickToOverried);
		panelDiscPop.add(approve);
		
		weightDiscPop.add(panelDiscPop, BorderLayout.CENTER);
		
		weightDiscPop.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		weightDiscPop.pack();
		weightDiscPop.setVisible(true);
		
		this.logic = logic;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		logic.unblock();
	}
}
