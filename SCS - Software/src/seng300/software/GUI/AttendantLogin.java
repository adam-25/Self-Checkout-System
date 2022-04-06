package seng300.software.GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JPasswordField;
import javax.swing.JTextPane;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.UIManager;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;

public class AttendantLogin extends JFrame {

	private JPanel contentPane;
	
	private JTextField attendantCodeInput;
	private JPasswordField passwordField;
	private JLabel lblNewLabel;
	private JLabel attendantCodeLabel;
	private JLabel lblNewLabel_1;
	private JButton attendantLoginBtn;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AttendantLogin frame = new AttendantLogin();
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
	public AttendantLogin() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[grow][grow][grow]", "[grow][][][][][][][grow]"));
		
		attendantCodeLabel = new JLabel("Attendant Code");
		attendantCodeLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		contentPane.add(attendantCodeLabel, "cell 1 1,alignx left,aligny bottom");
		attendantCodeLabel.setLabelFor(attendantCodeInput);
		
		attendantCodeInput = new JTextField();
		attendantCodeInput.setFont(new Font("Tahoma", Font.PLAIN, 16));
		attendantCodeInput.setForeground(Color.GRAY);
		attendantCodeInput.setText("Enter Attendant Code");
		attendantCodeInput.setHorizontalAlignment(SwingConstants.LEFT);
		contentPane.add(attendantCodeInput, "cell 1 2,growx");
		attendantCodeInput.setColumns(10);
		
		lblNewLabel_1 = new JLabel("Password");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		contentPane.add(lblNewLabel_1, "cell 1 3,aligny bottom");
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		passwordField.setToolTipText("");
		passwordField.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(passwordField, "cell 1 4,growx");
		
		lblNewLabel = new JLabel("Incorrect login code/password. Try Again.");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(Color.RED);
		contentPane.add(lblNewLabel, "cell 1 5,alignx center,aligny center");
		
		attendantLoginBtn = new JButton("Login");
		attendantLoginBtn.setFont(new Font("Tahoma", Font.BOLD, 16));
		attendantLoginBtn.setBackground(Color.WHITE);
		contentPane.add(attendantLoginBtn, "cell 1 6,growx");
		
	}

}
