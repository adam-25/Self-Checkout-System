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

public class AttendantLogin extends JFrame {

	private JPanel contentPane;
	
	private JTextField txtEnterAttendantLogin;
	private JPasswordField passwordField;
	private JTextPane txtpnEnterPass;
	private JTextPane txtpnEnterAttendentLogin;
	private JLabel lblNewLabel;

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
		contentPane.setLayout(new MigLayout("", "[][][grow][][]", "[][grow][][grow][][][][][]"));
		
		txtpnEnterAttendentLogin = new JTextPane();
		txtpnEnterAttendentLogin.setBackground(SystemColor.textHighlight);
		txtpnEnterAttendentLogin.setText("Enter Attendent Login Code:");
		contentPane.add(txtpnEnterAttendentLogin, "cell 2 1,grow");
		
		txtEnterAttendantLogin = new JTextField();
		txtEnterAttendantLogin.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(txtEnterAttendantLogin, "cell 2 2,growx");
		txtEnterAttendantLogin.setColumns(10);
		
		txtpnEnterPass = new JTextPane();
		txtpnEnterPass.setBackground(SystemColor.textHighlight);
		txtpnEnterPass.setText("Enter Password:");
		contentPane.add(txtpnEnterPass, "cell 2 3,grow");
		
		passwordField = new JPasswordField();
		passwordField.setToolTipText("");
		passwordField.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(passwordField, "cell 2 4,growx");
		
		JButton btnNewButton = new JButton("Login");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		lblNewLabel = new JLabel("Incorrect login code. Try Again.");
		lblNewLabel.setForeground(Color.RED);
		contentPane.add(lblNewLabel, "cell 2 6");
		contentPane.add(btnNewButton, "cell 2 7,growx");
		
	}

}
