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

public class NeedToAddPaperPopup extends JDialog implements ActionListener {

	private final JPanel addPaperContentPanel = new JPanel();
	private JLabel outOfPaper;
	private JButton ok;
	private int systemNum;
	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		try {
//			NeedToAddPaperPopup dialog = new NeedToAddPaperPopup();
//			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//			dialog.setVisible(true);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * Create the dialog.
	 */
	public NeedToAddPaperPopup(int systemNum) {
		this.systemNum = systemNum;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		addPaperContentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(addPaperContentPanel);
		addPaperContentPanel.setLayout(new BoxLayout(addPaperContentPanel, BoxLayout.Y_AXIS));
		
		addPaperContentPanel.add(Box.createRigidArea(new Dimension(0, 90)));
				
		outOfPaper = new JLabel("Customer at Station " + systemNum + " has run out of ink!");
		outOfPaper.setFont(new Font("Tahoma", Font.PLAIN, 16));
		outOfPaper.setHorizontalAlignment(JLabel.CENTER);
		outOfPaper.setAlignmentX(CENTER_ALIGNMENT);
		addPaperContentPanel.add(outOfPaper);
		addPaperContentPanel.add(Box.createRigidArea(new Dimension(0, 30)));

		ok = new JButton("      ok     ");
		ok.setFont(new Font("Tahoma", Font.BOLD, 15));
		ok.setAlignmentX(CENTER_ALIGNMENT);
		ok.setPreferredSize(new Dimension(100, 10));
		ok.addActionListener(this);
		ok.setBackground(Color.LIGHT_GRAY);
		ok.setOpaque(true);
		ok.setBorderPainted(false);
		addPaperContentPanel.add(ok);
		
		setLocationRelativeTo(null);
		setResizable(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		setVisible(false);
	}

}
