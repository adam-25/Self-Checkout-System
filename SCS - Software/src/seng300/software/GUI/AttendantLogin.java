package seng300.software.GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import org.lsmr.selfcheckout.devices.TouchScreen;
import seng300.software.AttendantLogic;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JPasswordField;
import javax.swing.JTextPane;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.UIManager;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Dimension;

public class AttendantLogin extends JPanel implements ActionListener {

//	private JPanel contentPane;
	private JTextField loginCodeInput;
	private JPasswordField loginPswdInput;
	private JLabel loginErrorMsgLabel;
	private JLabel loginCodeLabel;
	private JLabel loginPswdLabel;
	private JButton loginBtn;
	
	private AttendantLogic aLogic;
	private AttendantGUI gui;

	/**
	 * Launch the application.
	 */

//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					JFrame frame = new JFrame();
//					//AttendantLogin frame = new AttendantLogin();
//					frame.getContentPane().add(new AttendantLogin(AttendantLogic.getInstance()));
	//              frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
//					frame.pack();
////					loginBtn.requestFocusInWindow();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}
	/**
	 * Create the frame.
	 */
	public AttendantLogin(AttendantLogic logic, AttendantGUI gui) {
		this.aLogic = logic;
		this.gui = gui;
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		setBounds(100, 100, 450, 300);
//		contentPane = new JPanel();
		setBorder(new EmptyBorder(5, 5, 5, 5));
//		setContentPane(contentPane);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{1, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{1, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		loginCodeLabel = new JLabel("Attendant Code");
		loginCodeLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		GridBagConstraints gbc_loginCodeLabel = new GridBagConstraints();
		gbc_loginCodeLabel.insets = new Insets(0, 0, 5, 5);
		gbc_loginCodeLabel.fill = GridBagConstraints.BOTH;
		gbc_loginCodeLabel.gridx = 1;
		gbc_loginCodeLabel.gridy = 1;
		add(loginCodeLabel, gbc_loginCodeLabel);
		loginCodeLabel.setLabelFor(loginCodeInput);
		
		loginCodeInput = new JTextField();
		loginCodeInput.setSize(new Dimension(200, 0));
		loginCodeInput.setFont(new Font("Tahoma", Font.PLAIN, 16));
		loginCodeInput.setForeground(Color.GRAY);
		loginCodeInput.setText("Enter Attendant Code");
		loginCodeInput.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_loginCodeInput = new GridBagConstraints();
		gbc_loginCodeInput.insets = new Insets(0, 0, 5, 5);
		gbc_loginCodeInput.fill = GridBagConstraints.BOTH;
		gbc_loginCodeInput.gridx = 1;
		gbc_loginCodeInput.gridy = 2;
		add(loginCodeInput, gbc_loginCodeInput);
		loginCodeInput.setColumns(10);
		
//		loginCodeInput.addFocusListener(new FocusAdapter() {
//			public void focusGained(FocusEvent e) {
//				JTextField source = (JTextField)e.getComponent();
//				source.setText("");
//				source.removeFocusListener(this);
//			}
//		});
		
		loginPswdLabel = new JLabel("Password");
		loginPswdLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		GridBagConstraints gbc_loginPswdLabel = new GridBagConstraints();
		gbc_loginPswdLabel.insets = new Insets(0, 0, 5, 5);
		gbc_loginPswdLabel.fill = GridBagConstraints.BOTH;
		gbc_loginPswdLabel.gridx = 1;
		gbc_loginPswdLabel.gridy = 3;
		add(loginPswdLabel, gbc_loginPswdLabel);
		
		loginPswdInput = new JPasswordField();
		loginPswdInput.setFont(new Font("Tahoma", Font.PLAIN, 16));
		loginPswdInput.setToolTipText("");
		loginPswdInput.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_loginPswdInput = new GridBagConstraints();
		gbc_loginPswdInput.insets = new Insets(0, 0, 5, 5);
		gbc_loginPswdInput.fill = GridBagConstraints.BOTH;
		gbc_loginPswdInput.gridx = 1;
		gbc_loginPswdInput.gridy = 4;
		loginPswdInput.addActionListener(this);

		add(loginPswdInput, gbc_loginPswdInput);
		
		loginErrorMsgLabel = new JLabel("Incorrect login code/password. Try Again.");
		loginErrorMsgLabel.setHorizontalAlignment(SwingConstants.CENTER);
		loginErrorMsgLabel.setForeground(Color.RED);
		loginErrorMsgLabel.setVisible(false);
		GridBagConstraints gbc_loginErrorMsgLabel = new GridBagConstraints();
		gbc_loginErrorMsgLabel.insets = new Insets(0, 0, 5, 5);
		gbc_loginErrorMsgLabel.fill = GridBagConstraints.BOTH;
		gbc_loginErrorMsgLabel.gridx = 1;
		gbc_loginErrorMsgLabel.gridy = 6;
		add(loginErrorMsgLabel, gbc_loginErrorMsgLabel);
		
		loginBtn = new JButton("     Login     ");
		loginBtn.addActionListener(this);
//		new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				
//			}
//		});

		loginBtn.setFont(new Font("Tahoma", Font.BOLD, 16));
		loginBtn.setBackground(Color.WHITE);
		GridBagConstraints gbc_loginBtn = new GridBagConstraints();
		gbc_loginBtn.insets = new Insets(0, 0, 5, 5);
		gbc_loginBtn.fill = GridBagConstraints.BOTH;
		gbc_loginBtn.gridx = 1;
		gbc_loginBtn.gridy = 7;
		add(loginBtn, gbc_loginBtn);
	}
	
	public void showErrorMsg()
	{
		if (!loginErrorMsgLabel.isVisible())
		{
			loginErrorMsgLabel.setVisible(true);
		}
	}
	
	public void hideErrorMsg()
	{
		if (loginErrorMsgLabel.isVisible())
		{
			loginErrorMsgLabel.setVisible(false);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		aLogic.ss.keyboard.type(loginCodeInput.getText());
		
		String passwordString = new String(loginPswdInput.getPassword());
		aLogic.ss.keyboard.type(passwordString);
		
		aLogic.wantsToLogin();
		if (aLogic.loggedIn) {
//			setVisible(false);
			hideErrorMsg();
			gui.openAttendantMain();	
		} else {
			showErrorMsg();
		}
	}
}
