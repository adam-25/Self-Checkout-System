package seng300.software.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
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

public class NeedToAddInkPopup extends JDialog implements ActionListener {

	private final JPanel addInkContentPanel = new JPanel();
	private JLabel outOfInk;
	private JButton ok;
	private int systemNum;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		try {
//			NeedToAddInkPopup dialog = new NeedToAddInkPopup();
//			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//			dialog.setVisible(true);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * Create the dialog.
	 */
	public NeedToAddInkPopup(int systemNum) {
		this.systemNum = systemNum;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		addInkContentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(addInkContentPanel);
		addInkContentPanel.setLayout(new BoxLayout(addInkContentPanel, BoxLayout.Y_AXIS));
		
		addInkContentPanel.add(Box.createRigidArea(new Dimension(0, 90)));
				
		outOfInk = new JLabel("Customer at Station " + systemNum + " has run out of ink!");
		outOfInk.setFont(new Font("Tahoma", Font.PLAIN, 16));
		outOfInk.setHorizontalAlignment(JLabel.CENTER);
		outOfInk.setAlignmentX(CENTER_ALIGNMENT);
		addInkContentPanel.add(outOfInk);
		addInkContentPanel.add(Box.createRigidArea(new Dimension(0, 30)));

		ok = new JButton("      ok     ");
		ok.setFont(new Font("Tahoma", Font.BOLD, 15));
		ok.setAlignmentX(CENTER_ALIGNMENT);
		ok.setPreferredSize(new Dimension(100, 10));
		ok.addActionListener(this);
		ok.setBackground(Color.LIGHT_GRAY);
		ok.setOpaque(true);
		ok.setBorderPainted(false);
		addInkContentPanel.add(ok);
		
		setLocationRelativeTo(null);
		setResizable(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		setVisible(false);
	}

}
