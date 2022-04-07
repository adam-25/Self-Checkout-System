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

public class AttendantLogin extends JPanel {

//	private JPanel contentPane;
	
	private JTextField loginCodeInput;
	private JPasswordField loginPswdInput;
	private JLabel loginErrorMsgLabel;
	private JLabel loginCodeLabel;
	private JLabel loginPswdLabel;
	private JButton loginBtn;

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
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		setBounds(100, 100, 450, 300);
//		contentPane = new JPanel();
		setBorder(new EmptyBorder(5, 5, 5, 5));
//		setContentPane(contentPane);
		setLayout(new MigLayout("", "[grow][grow][grow]", "[grow][][][][][][][grow]"));
		
		loginCodeLabel = new JLabel("Attendant Code");
		loginCodeLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		add(loginCodeLabel, "cell 1 1,alignx left,aligny bottom");
		loginCodeLabel.setLabelFor(loginCodeInput);
		
		loginCodeInput = new JTextField();
		loginCodeInput.setFont(new Font("Tahoma", Font.PLAIN, 16));
		loginCodeInput.setForeground(Color.GRAY);
		loginCodeInput.setText("Enter Attendant Code");
		loginCodeInput.setHorizontalAlignment(SwingConstants.LEFT);
		add(loginCodeInput, "cell 1 2,growx");
		loginCodeInput.setColumns(10);
		
		loginPswdLabel = new JLabel("Password");
		loginPswdLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		add(loginPswdLabel, "cell 1 3,aligny bottom");
		
		loginPswdInput = new JPasswordField();
		loginPswdInput.setFont(new Font("Tahoma", Font.PLAIN, 16));
		loginPswdInput.setToolTipText("");
		loginPswdInput.setHorizontalAlignment(SwingConstants.CENTER);
		add(loginPswdInput, "cell 1 4,growx");
		
		loginErrorMsgLabel = new JLabel("Incorrect login code/password. Try Again.");
		loginErrorMsgLabel.setHorizontalAlignment(SwingConstants.CENTER);
		loginErrorMsgLabel.setForeground(Color.RED);
		add(loginErrorMsgLabel, "cell 1 5,alignx center,aligny center");
		
		loginBtn = new JButton("Login");
		loginBtn.setFont(new Font("Tahoma", Font.BOLD, 16));
		loginBtn.setBackground(Color.WHITE);
		add(loginBtn, "cell 1 6,growx");
		
	}

}
