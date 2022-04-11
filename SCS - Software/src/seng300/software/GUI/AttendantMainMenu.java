package seng300.software.GUI;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.EventQueue;
import java.awt.Font;

public class AttendantMainMenu extends JPanel {

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
	public AttendantMainMenu() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel currentStationLabel = new JLabel("Current Station:");
		GridBagConstraints gbc_currentStationLabel = new GridBagConstraints();
		gbc_currentStationLabel.insets = new Insets(0, 0, 5, 5);
		gbc_currentStationLabel.gridx = 3;
		gbc_currentStationLabel.gridy = 1;
		add(currentStationLabel, gbc_currentStationLabel);
		
		JButton logoutBtn = new JButton("Logout");
		logoutBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		GridBagConstraints gbc_logoutBtn = new GridBagConstraints();
		gbc_logoutBtn.insets = new Insets(0, 0, 5, 5);
		gbc_logoutBtn.gridx = 6;
		gbc_logoutBtn.gridy = 1;
		add(logoutBtn, gbc_logoutBtn);
		
		JButton station1Btn = new JButton("Station 1");
		station1Btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		GridBagConstraints gbc_station1Btn = new GridBagConstraints();
		gbc_station1Btn.insets = new Insets(0, 0, 5, 5);
		gbc_station1Btn.gridx = 1;
		gbc_station1Btn.gridy = 3;
		add(station1Btn, gbc_station1Btn);
		
		JButton station2Btn = new JButton("Station 2");
		station2Btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		GridBagConstraints gbc_station2Btn = new GridBagConstraints();
		gbc_station2Btn.insets = new Insets(0, 0, 5, 5);
		gbc_station2Btn.gridx = 2;
		gbc_station2Btn.gridy = 3;
		add(station2Btn, gbc_station2Btn);
		
		JButton station3Btn = new JButton("Station 3");
		station3Btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		GridBagConstraints gbc_station3Btn = new GridBagConstraints();
		gbc_station3Btn.insets = new Insets(0, 0, 5, 5);
		gbc_station3Btn.gridx = 3;
		gbc_station3Btn.gridy = 3;
		add(station3Btn, gbc_station3Btn);
		
		JButton station4Btn = new JButton("Station 4");
		GridBagConstraints gbc_station4Btn = new GridBagConstraints();
		gbc_station4Btn.insets = new Insets(0, 0, 5, 5);
		gbc_station4Btn.gridx = 4;
		gbc_station4Btn.gridy = 3;
		add(station4Btn, gbc_station4Btn);
		
		JButton station5Btn = new JButton("Station 5");
		station5Btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		GridBagConstraints gbc_station5Btn = new GridBagConstraints();
		gbc_station5Btn.insets = new Insets(0, 0, 5, 5);
		gbc_station5Btn.gridx = 5;
		gbc_station5Btn.gridy = 3;
		add(station5Btn, gbc_station5Btn);
		
		JButton station6Btn = new JButton("Station 6");
		station6Btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		GridBagConstraints gbc_station6Btn = new GridBagConstraints();
		gbc_station6Btn.insets = new Insets(0, 0, 5, 5);
		gbc_station6Btn.gridx = 6;
		gbc_station6Btn.gridy = 3;
		add(station6Btn, gbc_station6Btn);
		
		JButton startupBtn = new JButton("Startup");
		startupBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JLabel stationControlLabel = new JLabel("Station Controls");
		GridBagConstraints gbc_stationControlLabel = new GridBagConstraints();
		gbc_stationControlLabel.insets = new Insets(0, 0, 5, 5);
		gbc_stationControlLabel.gridx = 3;
		gbc_stationControlLabel.gridy = 5;
		add(stationControlLabel, gbc_stationControlLabel);
		GridBagConstraints gbc_startupBtn = new GridBagConstraints();
		gbc_startupBtn.insets = new Insets(0, 0, 5, 5);
		gbc_startupBtn.gridx = 2;
		gbc_startupBtn.gridy = 6;
		add(startupBtn, gbc_startupBtn);
		
		JButton blockBtn = new JButton("Block");
		blockBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		GridBagConstraints gbc_blockBtn = new GridBagConstraints();
		gbc_blockBtn.insets = new Insets(0, 0, 5, 5);
		gbc_blockBtn.gridx = 3;
		gbc_blockBtn.gridy = 6;
		add(blockBtn, gbc_blockBtn);
		
		JButton unblockBtn = new JButton("Unblock");
		unblockBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		GridBagConstraints gbc_unblockBtn = new GridBagConstraints();
		gbc_unblockBtn.insets = new Insets(0, 0, 5, 5);
		gbc_unblockBtn.gridx = 4;
		gbc_unblockBtn.gridy = 6;
		add(unblockBtn, gbc_unblockBtn);
		
		JButton shutDownBtn = new JButton("Shut Down");
		GridBagConstraints gbc_shutDownBtn = new GridBagConstraints();
		gbc_shutDownBtn.insets = new Insets(0, 0, 5, 5);
		gbc_shutDownBtn.gridx = 5;
		gbc_shutDownBtn.gridy = 6;
		add(shutDownBtn, gbc_shutDownBtn);
		
		JLabel hardwareFuncLabel = new JLabel("Hardware Functionalities");
		GridBagConstraints gbc_hardwareFuncLabel = new GridBagConstraints();
		gbc_hardwareFuncLabel.gridwidth = 2;
		gbc_hardwareFuncLabel.insets = new Insets(0, 0, 5, 5);
		gbc_hardwareFuncLabel.gridx = 2;
		gbc_hardwareFuncLabel.gridy = 9;
		add(hardwareFuncLabel, gbc_hardwareFuncLabel);
		
		JLabel customerAssistLebel = new JLabel("Customer Assistance");
		GridBagConstraints gbc_customerAssistLebel = new GridBagConstraints();
		gbc_customerAssistLebel.gridwidth = 2;
		gbc_customerAssistLebel.insets = new Insets(0, 0, 5, 5);
		gbc_customerAssistLebel.gridx = 5;
		gbc_customerAssistLebel.gridy = 9;
		add(customerAssistLebel, gbc_customerAssistLebel);
		
		JButton refillCoinBtn = new JButton("Refill Coins");
		refillCoinBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		GridBagConstraints gbc_refillCoinBtn = new GridBagConstraints();
		gbc_refillCoinBtn.fill = GridBagConstraints.HORIZONTAL;
		gbc_refillCoinBtn.gridwidth = 2;
		gbc_refillCoinBtn.insets = new Insets(0, 0, 5, 5);
		gbc_refillCoinBtn.gridx = 1;
		gbc_refillCoinBtn.gridy = 10;
		add(refillCoinBtn, gbc_refillCoinBtn);
		
		JButton emptyCoinsBtn = new JButton("Empty Coins");
		emptyCoinsBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		GridBagConstraints gbc_emptyCoinsBtn = new GridBagConstraints();
		gbc_emptyCoinsBtn.fill = GridBagConstraints.HORIZONTAL;
		gbc_emptyCoinsBtn.gridwidth = 2;
		gbc_emptyCoinsBtn.insets = new Insets(0, 0, 5, 5);
		gbc_emptyCoinsBtn.gridx = 3;
		gbc_emptyCoinsBtn.gridy = 10;
		add(emptyCoinsBtn, gbc_emptyCoinsBtn);
		
		JButton lookupProductBtn = new JButton("Lookup Product");
		lookupProductBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		GridBagConstraints gbc_lookupProductBtn = new GridBagConstraints();
		gbc_lookupProductBtn.fill = GridBagConstraints.HORIZONTAL;
		gbc_lookupProductBtn.gridwidth = 2;
		gbc_lookupProductBtn.insets = new Insets(0, 0, 5, 5);
		gbc_lookupProductBtn.gridx = 5;
		gbc_lookupProductBtn.gridy = 10;
		add(lookupProductBtn, gbc_lookupProductBtn);
		
		JButton refillBanknoteBtn = new JButton("Refill Banknotes");
		refillBanknoteBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		refillBanknoteBtn.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		GridBagConstraints gbc_refillBanknoteBtn = new GridBagConstraints();
		gbc_refillBanknoteBtn.fill = GridBagConstraints.HORIZONTAL;
		gbc_refillBanknoteBtn.gridwidth = 2;
		gbc_refillBanknoteBtn.insets = new Insets(0, 0, 5, 5);
		gbc_refillBanknoteBtn.gridx = 1;
		gbc_refillBanknoteBtn.gridy = 11;
		add(refillBanknoteBtn, gbc_refillBanknoteBtn);
		
		JButton emptyBanknotesBtn = new JButton("Empty Banknotes");
		emptyBanknotesBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		GridBagConstraints gbc_emptyBanknotesBtn = new GridBagConstraints();
		gbc_emptyBanknotesBtn.fill = GridBagConstraints.HORIZONTAL;
		gbc_emptyBanknotesBtn.gridwidth = 2;
		gbc_emptyBanknotesBtn.insets = new Insets(0, 0, 5, 5);
		gbc_emptyBanknotesBtn.gridx = 3;
		gbc_emptyBanknotesBtn.gridy = 11;
		add(emptyBanknotesBtn, gbc_emptyBanknotesBtn);
		
		JButton removeProductBtn = new JButton("Remove Product");
		removeProductBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		GridBagConstraints gbc_removeProductBtn = new GridBagConstraints();
		gbc_removeProductBtn.fill = GridBagConstraints.HORIZONTAL;
		gbc_removeProductBtn.gridwidth = 2;
		gbc_removeProductBtn.insets = new Insets(0, 0, 5, 5);
		gbc_removeProductBtn.gridx = 5;
		gbc_removeProductBtn.gridy = 11;
		add(removeProductBtn, gbc_removeProductBtn);
		
		JButton addReceiptPaperBtn = new JButton("Add Reciept Paper");
		addReceiptPaperBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		GridBagConstraints gbc_addReceiptPaperBtn = new GridBagConstraints();
		gbc_addReceiptPaperBtn.fill = GridBagConstraints.HORIZONTAL;
		gbc_addReceiptPaperBtn.gridwidth = 2;
		gbc_addReceiptPaperBtn.insets = new Insets(0, 0, 5, 5);
		gbc_addReceiptPaperBtn.gridx = 1;
		gbc_addReceiptPaperBtn.gridy = 12;
		add(addReceiptPaperBtn, gbc_addReceiptPaperBtn);
		
		JButton addRecieptInkBtn = new JButton("Add Reciept Ink");
		addRecieptInkBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		GridBagConstraints gbc_addRecieptInkBtn = new GridBagConstraints();
		gbc_addRecieptInkBtn.fill = GridBagConstraints.HORIZONTAL;
		gbc_addRecieptInkBtn.gridwidth = 2;
		gbc_addRecieptInkBtn.insets = new Insets(0, 0, 5, 5);
		gbc_addRecieptInkBtn.gridx = 3;
		gbc_addRecieptInkBtn.gridy = 12;
		add(addRecieptInkBtn, gbc_addRecieptInkBtn);

	}

}
