package seng300.software.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AreYouSure extends JDialog implements ActionListener{

	private JPanel contentPane;
	private JPanel buttonPlacement;
	private JLabel question;
	private JButton yes;
	private JButton no;
	
	private boolean yesOrNo = true;
	/**
	 * Create the frame.
	 */
	public AreYouSure() {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);	// EXIT_ON_CLOSE to HIDE_ON_CLOSE
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		
		getContentPane().add(Box.createRigidArea(new Dimension(0, 100)));
		
		
		question = new JLabel("Are you sure you would like to proceed with this action?");
		question.setFont(new Font("Tahoma", Font.PLAIN, 15));
		question.setHorizontalAlignment(JLabel.CENTER);
		question.setAlignmentX(CENTER_ALIGNMENT);
		contentPane.add(question);
		contentPane.add(Box.createRigidArea(new Dimension(0, 30)));
		
		buttonPlacement = new JPanel();
		buttonPlacement.setLayout(new BoxLayout(buttonPlacement, BoxLayout.X_AXIS));
		
		yes = new JButton("Yes");
		yes.setFont(new Font("Tahoma", Font.BOLD, 13));
		yes.addActionListener(this);
		yes.setBackground(Color.LIGHT_GRAY);
		yes.setOpaque(true);
		yes.setBorderPainted(false);
		buttonPlacement.add(yes);
		
		buttonPlacement.add(Box.createRigidArea(new Dimension(50, 0)));
		
		no = new JButton("No ");
		no.setFont(new Font("Tahoma", Font.PLAIN, 13));
		no.addActionListener(this);
		no.setBackground(Color.LIGHT_GRAY);
		no.setOpaque(true);
		no.setBorderPainted(false);
		buttonPlacement.add(no);

		buttonPlacement.setAlignmentX(CENTER_ALIGNMENT);
		contentPane.add(buttonPlacement);
		
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource().equals(yes) ) {
			yesOrNo = true;
		} else {
			yesOrNo = false;
		}
		setVisible(false);
	}
	
	public boolean secondCheck() {
		return yesOrNo;
	}
}
