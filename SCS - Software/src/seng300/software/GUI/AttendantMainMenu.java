package seng300.software.GUI;

import javax.swing.JPanel;

import org.lsmr.selfcheckout.SimulationException;
import org.lsmr.selfcheckout.devices.OverloadException;

import seng300.software.AttendantLogic;
import seng300.software.SelfCheckoutSystemLogic;
import seng300.software.exceptions.ValidationException;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

public class AttendantMainMenu extends JPanel {
	private GridBagLayout gridBagLayout;
	
	private JLabel currentStationLabel;
	private GridBagConstraints gbc_currentStationLabel;
	
	private JButton logoutBtn;
	private GridBagConstraints gbc_logoutBtn;
	
	private JButton station1Btn;
	private GridBagConstraints gbc_station1Btn;
	
	private JButton station2Btn;
	private GridBagConstraints gbc_station2Btn;
	
	private JButton station3Btn;
	private GridBagConstraints gbc_station3Btn;
	
	private JButton station4Btn;
	private GridBagConstraints gbc_station4Btn;
	
	private JButton station5Btn;
	private GridBagConstraints gbc_station5Btn;
	
	private JButton station6Btn;
	private GridBagConstraints gbc_station6Btn;
	
	private JButton startupBtn;
	private GridBagConstraints gbc_startupBtn;
	
	private JLabel stationControlLabel;
	private GridBagConstraints gbc_stationControlLabel;
	
	private JButton blockBtn;
	private GridBagConstraints gbc_blockBtn;
	
	private JButton unblockBtn;
	private GridBagConstraints gbc_unblockBtn;
	
	private JButton shutDownBtn;
	private GridBagConstraints gbc_shutDownBtn;
	
	private JLabel hardwareFuncLabel;
	private GridBagConstraints gbc_hardwareFuncLabel;
	
	private JLabel customerAssistLabel;
	private GridBagConstraints gbc_customerAssistLabel;
	
	private JButton refillCoinBtn;
	private GridBagConstraints gbc_refillCoinBtn;
	
	private JButton emptyCoinsBtn;
	private GridBagConstraints gbc_emptyCoinsBtn;
	
	private JButton lookupProductBtn;
	private GridBagConstraints gbc_lookupProductBtn;
	
	private JButton refillBanknoteBtn;
	private GridBagConstraints gbc_refillBanknoteBtn;
	
	private JButton emptyBanknotesBtn;
	private GridBagConstraints gbc_emptyBanknotesBtn;
	
	private JButton removeProductBtn;
	private GridBagConstraints gbc_removeProductBtn;
	
	private JButton addReceiptPaperBtn;
	private GridBagConstraints gbc_addReceiptPaperBtn;
	
	private JButton addRecieptInkBtn;
	private GridBagConstraints gbc_addRecieptInkBtn;
	
	private AttendantGUI gui;
	private AttendantLogic attendantSystem;
	private SelfCheckoutSystemLogic currentSystem;
	private SelfCheckoutSystemLogic[] allSystems;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//AttendantMainMenu frame = new AttendantMainMenu();
					//frame.setVisible(true);
					JFrame frame = new JFrame();
					//AttendantLogin frame = new AttendantLogin();
					frame.getContentPane().add(new AttendantMainMenu());
					frame.pack();
					frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Create the panel.
	 */
	public AttendantMainMenu(AttendantLogic attendantSystem, AttendantGUI gui) {
		this.attendantSystem = attendantSystem;
		this.gui = gui;
		
		gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		currentStationLabel = new JLabel("Current Station:");
		gbc_currentStationLabel = new GridBagConstraints();
		gbc_currentStationLabel.insets = new Insets(0, 0, 5, 5);
		gbc_currentStationLabel.gridx = 3;
		gbc_currentStationLabel.gridy = 1;
		add(currentStationLabel, gbc_currentStationLabel);
		
		logoutBtn = new JButton("Logout");
		logoutBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// set login In attendantlogic to false
				// setVisible(false);
				attendantSystem.wantsToLogout();
				gui.openAttendantLogin();		
			}
		});
		
		gbc_logoutBtn = new GridBagConstraints();
		gbc_logoutBtn.insets = new Insets(0, 0, 5, 5);
		gbc_logoutBtn.gridx = 6;
		gbc_logoutBtn.gridy = 1;
		logoutBtn.setBackground(Color.LIGHT_GRAY);
		logoutBtn.setOpaque(true);
		logoutBtn.setBorderPainted(false);
		add(logoutBtn, gbc_logoutBtn);
		
		station1Btn = new JButton("Station 1");
		station1Btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Set colour of button to darken
				station1Btn.setBackground(Color.DARK_GRAY);
				station2Btn.setBackground(Color.GRAY);
				station3Btn.setBackground(Color.GRAY);
				station4Btn.setBackground(Color.GRAY);
				station5Btn.setBackground(Color.GRAY);
				station6Btn.setBackground(Color.GRAY);
				
				currentStationLabel.setText("Current Station: 1");
				currentSystem = allSystems[1];
			}
		});
		
		gbc_station1Btn = new GridBagConstraints();
		gbc_station1Btn.insets = new Insets(0, 0, 5, 5);
		gbc_station1Btn.gridx = 1;
		gbc_station1Btn.gridy = 3;
		station1Btn.setBackground(Color.GRAY);
		station1Btn.setOpaque(true);
		station1Btn.setBorderPainted(false);
		add(station1Btn, gbc_station1Btn);
		
		station2Btn = new JButton("Station 2");
		station2Btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				station1Btn.setBackground(Color.GRAY);
				station2Btn.setBackground(Color.DARK_GRAY);
				station3Btn.setBackground(Color.GRAY);
				station4Btn.setBackground(Color.GRAY);
				station5Btn.setBackground(Color.GRAY);
				station6Btn.setBackground(Color.GRAY);
				
				currentStationLabel.setText("Current Station: 2");
				currentSystem = allSystems[2];
			}
		});
		gbc_station2Btn = new GridBagConstraints();
		gbc_station2Btn.insets = new Insets(0, 0, 5, 5);
		gbc_station2Btn.gridx = 2;
		gbc_station2Btn.gridy = 3;
		station2Btn.setBackground(Color.GRAY);
		station2Btn.setOpaque(true);
		station2Btn.setBorderPainted(false);
		add(station2Btn, gbc_station2Btn);
		
		station3Btn = new JButton("Station 3");
		station3Btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				station1Btn.setBackground(Color.GRAY);
				station2Btn.setBackground(Color.GRAY);
				station3Btn.setBackground(Color.DARK_GRAY);
				station4Btn.setBackground(Color.GRAY);
				station5Btn.setBackground(Color.GRAY);
				station6Btn.setBackground(Color.GRAY);
				
				currentStationLabel.setText("Current Station: 3");
				currentSystem = allSystems[3];
			}
		});
		
		gbc_station3Btn = new GridBagConstraints();
		gbc_station3Btn.insets = new Insets(0, 0, 5, 5);
		gbc_station3Btn.gridx = 3;
		gbc_station3Btn.gridy = 3;
		station3Btn.setBackground(Color.GRAY);
		station3Btn.setOpaque(true);
		station3Btn.setBorderPainted(false);
		add(station3Btn, gbc_station3Btn);
		
		station4Btn = new JButton("Station 4");
		station4Btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				station1Btn.setBackground(Color.GRAY);
				station2Btn.setBackground(Color.GRAY);
				station3Btn.setBackground(Color.GRAY);
				station4Btn.setBackground(Color.DARK_GRAY);
				station5Btn.setBackground(Color.GRAY);
				station6Btn.setBackground(Color.GRAY);
				
				currentStationLabel.setText("Current Station: 4");
				currentSystem = allSystems[4];
			}
		});
		gbc_station4Btn = new GridBagConstraints();
		gbc_station4Btn.insets = new Insets(0, 0, 5, 5);
		gbc_station4Btn.gridx = 4;
		gbc_station4Btn.gridy = 3;
		station4Btn.setBackground(Color.GRAY);
		station4Btn.setOpaque(true);
		station4Btn.setBorderPainted(false);
		add(station4Btn, gbc_station4Btn);
		
		station5Btn = new JButton("Station 5");
		station5Btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				station1Btn.setBackground(Color.GRAY);
				station2Btn.setBackground(Color.GRAY);
				station3Btn.setBackground(Color.GRAY);
				station4Btn.setBackground(Color.GRAY);
				station5Btn.setBackground(Color.DARK_GRAY);
				station6Btn.setBackground(Color.GRAY);
				
				currentStationLabel.setText("Current Station: 5");
				currentSystem = allSystems[5];
			}
		});
		gbc_station5Btn = new GridBagConstraints();
		gbc_station5Btn.insets = new Insets(0, 0, 5, 5);
		gbc_station5Btn.gridx = 5;
		gbc_station5Btn.gridy = 3;
		station5Btn.setBackground(Color.GRAY);
		station5Btn.setOpaque(true);
		station5Btn.setBorderPainted(false);
		add(station5Btn, gbc_station5Btn);
		
		station6Btn = new JButton("Station 6");
		station6Btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				station1Btn.setBackground(Color.GRAY);
				station2Btn.setBackground(Color.GRAY);
				station3Btn.setBackground(Color.GRAY);
				station4Btn.setBackground(Color.GRAY);
				station5Btn.setBackground(Color.GRAY);
				station6Btn.setBackground(Color.DARK_GRAY);
				
				currentStationLabel.setText("Current Station: 6");
				currentSystem = allSystems[6];
			}
		});
		
		gbc_station6Btn = new GridBagConstraints();
		gbc_station6Btn.insets = new Insets(0, 0, 5, 5);
		gbc_station6Btn.gridx = 6;
		gbc_station6Btn.gridy = 3;
		station6Btn.setBackground(Color.GRAY);
		station6Btn.setOpaque(true);
		station6Btn.setBorderPainted(false);
		add(station6Btn, gbc_station6Btn);
		
		startupBtn = new JButton("Startup");
		startupBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// turn green
				// set a bool?
				attendantSystem.startUpStation(currentSystem);
			}
		});
		
		stationControlLabel = new JLabel("Station Controls");
		gbc_stationControlLabel = new GridBagConstraints();
		gbc_stationControlLabel.insets = new Insets(0, 0, 5, 5);
		gbc_stationControlLabel.gridx = 3;
		gbc_stationControlLabel.gridy = 5;
		add(stationControlLabel, gbc_stationControlLabel);
		
		gbc_startupBtn = new GridBagConstraints();
		gbc_startupBtn.insets = new Insets(0, 0, 5, 5);
		gbc_startupBtn.gridx = 2;
		gbc_startupBtn.gridy = 6;
		startupBtn.setBackground(Color.LIGHT_GRAY);
		startupBtn.setOpaque(true);
		startupBtn.setBorderPainted(false);
		add(startupBtn, gbc_startupBtn);
		
		blockBtn = new JButton("Block");
		blockBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Change colour
				attendantSystem.attendantBlock(currentSystem);
			}
		});
		
		gbc_blockBtn = new GridBagConstraints();
		gbc_blockBtn.insets = new Insets(0, 0, 5, 5);
		gbc_blockBtn.gridx = 3;
		gbc_blockBtn.gridy = 6;
		blockBtn.setBackground(Color.LIGHT_GRAY);
		blockBtn.setOpaque(true);
		blockBtn.setBorderPainted(false);
		add(blockBtn, gbc_blockBtn);
		
		unblockBtn = new JButton("Unblock");
		unblockBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// OverrideSystemBlock
				currentSystem.unblock();
			}
		});
		
		gbc_unblockBtn = new GridBagConstraints();
		gbc_unblockBtn.insets = new Insets(0, 0, 5, 5);
		gbc_unblockBtn.gridx = 4;
		gbc_unblockBtn.gridy = 6;
		unblockBtn.setBackground(Color.LIGHT_GRAY);
		unblockBtn.setOpaque(true);
		unblockBtn.setBorderPainted(false);
		add(unblockBtn, gbc_unblockBtn);
		
		shutDownBtn = new JButton("Shut Down");
		shutDownBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				attendantSystem.shutDownStation(currentSystem);
			}
		});
		gbc_shutDownBtn = new GridBagConstraints();
		gbc_shutDownBtn.insets = new Insets(0, 0, 5, 5);
		gbc_shutDownBtn.gridx = 5;
		gbc_shutDownBtn.gridy = 6;
		shutDownBtn.setBackground(Color.LIGHT_GRAY);
		shutDownBtn.setOpaque(true);
		shutDownBtn.setBorderPainted(false);
		add(shutDownBtn, gbc_shutDownBtn);
		
		hardwareFuncLabel = new JLabel("Hardware Functionalities");
		gbc_hardwareFuncLabel = new GridBagConstraints();
		gbc_hardwareFuncLabel.gridwidth = 2;
		gbc_hardwareFuncLabel.insets = new Insets(0, 0, 5, 5);
		gbc_hardwareFuncLabel.gridx = 2;
		gbc_hardwareFuncLabel.gridy = 9;
		add(hardwareFuncLabel, gbc_hardwareFuncLabel);
		
		customerAssistLabel = new JLabel("Customer Assistance");
		gbc_customerAssistLabel = new GridBagConstraints();
		gbc_customerAssistLabel.gridwidth = 2;
		gbc_customerAssistLabel.insets = new Insets(0, 0, 5, 5);
		gbc_customerAssistLabel.gridx = 5;
		gbc_customerAssistLabel.gridy = 9;
		add(customerAssistLabel, gbc_customerAssistLabel);
		
		refillCoinBtn = new JButton("Refill Coins");
		refillCoinBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					attendantSystem.refillsCoinDispenser(currentSystem.station);
				} catch (SimulationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (OverloadException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					// Calls a popup
				}
			}
		});
		
		gbc_refillCoinBtn = new GridBagConstraints();
		gbc_refillCoinBtn.fill = GridBagConstraints.HORIZONTAL;
		gbc_refillCoinBtn.gridwidth = 2;
		gbc_refillCoinBtn.insets = new Insets(0, 0, 5, 5);
		gbc_refillCoinBtn.gridx = 1;
		gbc_refillCoinBtn.gridy = 10;
		refillCoinBtn.setBackground(Color.LIGHT_GRAY);
		refillCoinBtn.setOpaque(true);
		refillCoinBtn.setBorderPainted(false);
		add(refillCoinBtn, gbc_refillCoinBtn);
		
		emptyCoinsBtn = new JButton("Empty Coins");
		emptyCoinsBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					attendantSystem.emptyCoinStorageUnit(currentSystem.station);
				} catch (ValidationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					// ??
				}
			}
		});
		
		gbc_emptyCoinsBtn = new GridBagConstraints();
		gbc_emptyCoinsBtn.fill = GridBagConstraints.HORIZONTAL;
		gbc_emptyCoinsBtn.gridwidth = 2;
		gbc_emptyCoinsBtn.insets = new Insets(0, 0, 5, 5);
		gbc_emptyCoinsBtn.gridx = 3;
		gbc_emptyCoinsBtn.gridy = 10;
		emptyCoinsBtn.setBackground(Color.LIGHT_GRAY);
		emptyCoinsBtn.setOpaque(true);
		emptyCoinsBtn.setBorderPainted(false);
		add(emptyCoinsBtn, gbc_emptyCoinsBtn);
		
		lookupProductBtn = new JButton("Lookup Product");
		lookupProductBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		gbc_lookupProductBtn = new GridBagConstraints();
		gbc_lookupProductBtn.fill = GridBagConstraints.HORIZONTAL;
		gbc_lookupProductBtn.gridwidth = 2;
		gbc_lookupProductBtn.insets = new Insets(0, 0, 5, 5);
		gbc_lookupProductBtn.gridx = 5;
		gbc_lookupProductBtn.gridy = 10;
		lookupProductBtn.setBackground(Color.LIGHT_GRAY);
		lookupProductBtn.setOpaque(true);
		lookupProductBtn.setBorderPainted(false);
		add(lookupProductBtn, gbc_lookupProductBtn);
		
		refillBanknoteBtn = new JButton("Refill Banknotes");
		refillBanknoteBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		refillBanknoteBtn.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		gbc_refillBanknoteBtn = new GridBagConstraints();
		gbc_refillBanknoteBtn.fill = GridBagConstraints.HORIZONTAL;
		gbc_refillBanknoteBtn.gridwidth = 2;
		gbc_refillBanknoteBtn.insets = new Insets(0, 0, 5, 5);
		gbc_refillBanknoteBtn.gridx = 1;
		gbc_refillBanknoteBtn.gridy = 11;
		refillBanknoteBtn.setBackground(Color.LIGHT_GRAY);
		refillBanknoteBtn.setOpaque(true);
		refillBanknoteBtn.setBorderPainted(false);
		add(refillBanknoteBtn, gbc_refillBanknoteBtn);
		
		emptyBanknotesBtn = new JButton("Empty Banknotes");
		emptyBanknotesBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		gbc_emptyBanknotesBtn = new GridBagConstraints();
		gbc_emptyBanknotesBtn.fill = GridBagConstraints.HORIZONTAL;
		gbc_emptyBanknotesBtn.gridwidth = 2;
		gbc_emptyBanknotesBtn.insets = new Insets(0, 0, 5, 5);
		gbc_emptyBanknotesBtn.gridx = 3;
		gbc_emptyBanknotesBtn.gridy = 11;
		emptyBanknotesBtn.setBackground(Color.LIGHT_GRAY);
		emptyBanknotesBtn.setOpaque(true);
		emptyBanknotesBtn.setBorderPainted(false);
		add(emptyBanknotesBtn, gbc_emptyBanknotesBtn);
		
		removeProductBtn = new JButton("Remove Product");
		removeProductBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		gbc_removeProductBtn = new GridBagConstraints();
		gbc_removeProductBtn.fill = GridBagConstraints.HORIZONTAL;
		gbc_removeProductBtn.gridwidth = 2;
		gbc_removeProductBtn.insets = new Insets(0, 0, 5, 5);
		gbc_removeProductBtn.gridx = 5;
		gbc_removeProductBtn.gridy = 11;
		removeProductBtn.setBackground(Color.LIGHT_GRAY);
		removeProductBtn.setOpaque(true);
		removeProductBtn.setBorderPainted(false);
		add(removeProductBtn, gbc_removeProductBtn);
		
		addReceiptPaperBtn = new JButton("Add Reciept Paper");
		addReceiptPaperBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		gbc_addReceiptPaperBtn = new GridBagConstraints();
		gbc_addReceiptPaperBtn.fill = GridBagConstraints.HORIZONTAL;
		gbc_addReceiptPaperBtn.gridwidth = 2;
		gbc_addReceiptPaperBtn.insets = new Insets(0, 0, 5, 5);
		gbc_addReceiptPaperBtn.gridx = 1;
		gbc_addReceiptPaperBtn.gridy = 12;
		addReceiptPaperBtn.setBackground(Color.LIGHT_GRAY);
		addReceiptPaperBtn.setOpaque(true);
		addReceiptPaperBtn.setBorderPainted(false);
		add(addReceiptPaperBtn, gbc_addReceiptPaperBtn);
		
		addRecieptInkBtn = new JButton("Add Reciept Ink");
		addRecieptInkBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		gbc_addRecieptInkBtn = new GridBagConstraints();
		gbc_addRecieptInkBtn.fill = GridBagConstraints.HORIZONTAL;
		gbc_addRecieptInkBtn.gridwidth = 2;
		gbc_addRecieptInkBtn.insets = new Insets(0, 0, 5, 5);
		gbc_addRecieptInkBtn.gridx = 3;
		gbc_addRecieptInkBtn.gridy = 12;
		addRecieptInkBtn.setBackground(Color.LIGHT_GRAY);
		addRecieptInkBtn.setOpaque(true);
		addRecieptInkBtn.setBorderPainted(false);
		add(addRecieptInkBtn, gbc_addRecieptInkBtn);

	}

}
