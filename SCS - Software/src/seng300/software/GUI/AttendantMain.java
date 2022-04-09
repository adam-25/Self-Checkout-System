package seng300.software.GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Panel;
import java.awt.Canvas;
import javax.swing.JButton;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class AttendantMain extends JPanel {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JFrame frame = new JFrame();
					frame.getContentPane().add(new AttendantMain());
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
	 * Create the frame.
	 */
	public AttendantMain() {
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setBounds(100, 100, 960, 540);
		
		//setContentPane(contentPane);
		
//		contentPane = new JPanel();
		//contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
		gbl_contentPane.rowHeights = new int[]{1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gbl_contentPane);	// Had contentPane
		
		JButton blockBtn = new JButton("Block");
		blockBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JLabel lblNewLabel = new JLabel("Current Station:");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 5;
		gbc_lblNewLabel.gridy = 1;
		add(lblNewLabel, gbc_lblNewLabel);	// Had Content Pane
		
		
		JButton station1btn = new JButton("Station 1");
		station1btn.setBackground(Color.GRAY);
		station1btn.setOpaque(true);
		station1btn.setBorderPainted(false);
		station1btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JButton LogoutBtn = new JButton("Logout");
		LogoutBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		GridBagConstraints gbc_LogoutBtn = new GridBagConstraints();
		gbc_LogoutBtn.anchor = GridBagConstraints.NORTHWEST;
		gbc_LogoutBtn.insets = new Insets(0, 0, 5, 5);
		gbc_LogoutBtn.gridx = 9;
		gbc_LogoutBtn.gridy = 1;
		add(LogoutBtn, gbc_LogoutBtn);// Had contentPane
		GridBagConstraints gbc_station1btn = new GridBagConstraints();
		gbc_station1btn.anchor = GridBagConstraints.NORTH;
		gbc_station1btn.insets = new Insets(0, 0, 5, 5);
		gbc_station1btn.gridx = 3;
		gbc_station1btn.gridy = 2;
		add(station1btn, gbc_station1btn);	// Had contentPane
		
		
		JButton station2btn = new JButton("Station 2");
		station2btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		station2btn.setBackground(Color.GRAY);
		station2btn.setOpaque(true);
		station2btn.setBorderPainted(false);
		GridBagConstraints gbc_station2btn = new GridBagConstraints();
		gbc_station2btn.anchor = GridBagConstraints.NORTH;
		gbc_station2btn.insets = new Insets(0, 0, 5, 5);
		gbc_station2btn.gridx = 4;
		gbc_station2btn.gridy = 2;
		add(station2btn, gbc_station2btn);	// Had contentPane
		
		
		JButton station3btn = new JButton("Station 3");
		station3btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		station3btn.setBackground(Color.GRAY);
		station3btn.setOpaque(true);
		station3btn.setBorderPainted(false);
		GridBagConstraints gbc_station3btn = new GridBagConstraints();
		gbc_station3btn.anchor = GridBagConstraints.NORTHWEST;
		gbc_station3btn.insets = new Insets(0, 0, 5, 5);
		gbc_station3btn.gridx = 5;
		gbc_station3btn.gridy = 2;
		add(station3btn, gbc_station3btn);	// Had contentPane
		
		
		JButton startupBtn = new JButton("Startup");
		startupBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JButton station5btn = new JButton("Station 5");
		station5btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		station5btn.setBackground(Color.GRAY);
		station5btn.setOpaque(true);
		station5btn.setBorderPainted(false);
		GridBagConstraints gbc_station5btn = new GridBagConstraints();
		gbc_station5btn.anchor = GridBagConstraints.NORTHWEST;
		gbc_station5btn.insets = new Insets(0, 0, 5, 5);
		gbc_station5btn.gridx = 6;
		gbc_station5btn.gridy = 2;
		add(station5btn, gbc_station5btn);	// Had contentPane
		
		
		JButton station4btn = new JButton("Station 4");
		station4btn.setBackground(Color.GRAY);
		station4btn.setOpaque(true);
		station4btn.setBorderPainted(false);
		station4btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		GridBagConstraints gbc_station4btn = new GridBagConstraints();
		gbc_station4btn.anchor = GridBagConstraints.NORTH;
		gbc_station4btn.insets = new Insets(0, 0, 5, 5);
		gbc_station4btn.gridx = 7;
		gbc_station4btn.gridy = 2;
		add(station4btn, gbc_station4btn);	// Had contentPane
		
		JButton station6btn = new JButton("Station 6");
		station6btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		station6btn.setBackground(Color.GRAY);
		station6btn.setOpaque(true);
		station6btn.setBorderPainted(false);
		GridBagConstraints gbc_station6btn = new GridBagConstraints();
		gbc_station6btn.anchor = GridBagConstraints.NORTHWEST;
		gbc_station6btn.insets = new Insets(0, 0, 5, 5);
		gbc_station6btn.gridx = 8;
		gbc_station6btn.gridy = 2;
		add(station6btn, gbc_station6btn);	// Had contentPane
		
		JLabel labelSC = new JLabel("Station Controls");
		GridBagConstraints gbc_labelSC = new GridBagConstraints();
		gbc_labelSC.anchor = GridBagConstraints.NORTH;
		gbc_labelSC.insets = new Insets(0, 0, 5, 5);
		gbc_labelSC.gridx = 4;
		gbc_labelSC.gridy = 5;
		add(labelSC, gbc_labelSC);	// Had contentPane
		startupBtn.setBackground(Color.LIGHT_GRAY);
		startupBtn.setOpaque(true);
		startupBtn.setBorderPainted(false);
		GridBagConstraints gbc_startupBtn = new GridBagConstraints();
		gbc_startupBtn.anchor = GridBagConstraints.NORTH;
		gbc_startupBtn.insets = new Insets(0, 0, 5, 5);
		gbc_startupBtn.gridx = 3;
		gbc_startupBtn.gridy = 6;
		add(startupBtn, gbc_startupBtn);	// Had contentPane
		blockBtn.setBackground(Color.LIGHT_GRAY);
		blockBtn.setOpaque(true);
		blockBtn.setBorderPainted(false);
		GridBagConstraints gbc_blockBtn = new GridBagConstraints();
		gbc_blockBtn.anchor = GridBagConstraints.NORTH;
		gbc_blockBtn.fill = GridBagConstraints.HORIZONTAL;
		gbc_blockBtn.insets = new Insets(0, 0, 5, 5);
		gbc_blockBtn.gridx = 4;
		gbc_blockBtn.gridy = 6;
		add(blockBtn, gbc_blockBtn);	// Had contentPane
		
		JButton unblockBtn = new JButton("Unblock");
		unblockBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JButton shutdownBtn = new JButton("Shut Down");
		shutdownBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		shutdownBtn.setBackground(Color.LIGHT_GRAY);
		shutdownBtn.setOpaque(true);
		shutdownBtn.setBorderPainted(false);
		GridBagConstraints gbc_shutdownBtn = new GridBagConstraints();
		gbc_shutdownBtn.anchor = GridBagConstraints.NORTH;
		gbc_shutdownBtn.insets = new Insets(0, 0, 5, 5);
		gbc_shutdownBtn.gridx = 7;
		gbc_shutdownBtn.gridy = 6;
		add(shutdownBtn, gbc_shutdownBtn);	// Had contentPane
		unblockBtn.setBackground(Color.LIGHT_GRAY);
		unblockBtn.setOpaque(true);
		unblockBtn.setBorderPainted(false);
		GridBagConstraints gbc_unblockBtn = new GridBagConstraints();
		gbc_unblockBtn.anchor = GridBagConstraints.NORTH;
		gbc_unblockBtn.fill = GridBagConstraints.HORIZONTAL;
		gbc_unblockBtn.insets = new Insets(0, 0, 5, 5);
		gbc_unblockBtn.gridx = 6;
		gbc_unblockBtn.gridy = 7;
		add(unblockBtn, gbc_unblockBtn);	// Had contentPane
		
		JLabel hardwareFuncLabel = new JLabel("Hardware Functionalities");
		GridBagConstraints gbc_hardwareFuncLabel = new GridBagConstraints();
		gbc_hardwareFuncLabel.anchor = GridBagConstraints.NORTH;
		gbc_hardwareFuncLabel.insets = new Insets(0, 0, 5, 5);
		gbc_hardwareFuncLabel.gridwidth = 2;
		gbc_hardwareFuncLabel.gridx = 3;
		gbc_hardwareFuncLabel.gridy = 10;
		add(hardwareFuncLabel, gbc_hardwareFuncLabel);	// Had contentPane
		
		JButton emptyCoinsBtn = new JButton("Empty Coins");
		emptyCoinsBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
				JButton refillCoinsBtn = new JButton("Refill Coins");
				refillCoinsBtn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
					}
				});
				
				
				JLabel customerAssistLabel = new JLabel("Customer Assistance");
				GridBagConstraints gbc_customerAssistLabel = new GridBagConstraints();
				gbc_customerAssistLabel.anchor = GridBagConstraints.NORTH;
				gbc_customerAssistLabel.insets = new Insets(0, 0, 5, 5);
				gbc_customerAssistLabel.gridx = 7;
				gbc_customerAssistLabel.gridy = 10;
				add(customerAssistLabel, gbc_customerAssistLabel);	// Had contentPane
				refillCoinsBtn.setBackground(Color.LIGHT_GRAY);
				refillCoinsBtn.setOpaque(true);
				refillCoinsBtn.setBorderPainted(false);
				GridBagConstraints gbc_refillCoinsBtn = new GridBagConstraints();
				gbc_refillCoinsBtn.anchor = GridBagConstraints.NORTH;
				gbc_refillCoinsBtn.fill = GridBagConstraints.HORIZONTAL;
				gbc_refillCoinsBtn.insets = new Insets(0, 0, 5, 5);
				gbc_refillCoinsBtn.gridx = 3;
				gbc_refillCoinsBtn.gridy = 11;
				add(refillCoinsBtn, gbc_refillCoinsBtn);	// Had contentPane
		emptyCoinsBtn.setBackground(Color.LIGHT_GRAY);
		emptyCoinsBtn.setOpaque(true);
		emptyCoinsBtn.setBorderPainted(false);
		GridBagConstraints gbc_emptyCoinsBtn = new GridBagConstraints();
		gbc_emptyCoinsBtn.anchor = GridBagConstraints.NORTH;
		gbc_emptyCoinsBtn.fill = GridBagConstraints.HORIZONTAL;
		gbc_emptyCoinsBtn.insets = new Insets(0, 0, 5, 5);
		gbc_emptyCoinsBtn.gridx = 4;
		gbc_emptyCoinsBtn.gridy = 11;
		add(emptyCoinsBtn, gbc_emptyCoinsBtn);	// Had contentPane
		
		JButton emptyBanknoteBtn = new JButton("Empty Banknotes");
		emptyBanknoteBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JButton refillBanknoteBtn = new JButton("Refill Banknotes");
		refillBanknoteBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JButton lookupProductBtn = new JButton("Lookup Product");
		lookupProductBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		lookupProductBtn.setBackground(Color.LIGHT_GRAY);
		lookupProductBtn.setOpaque(true);
		lookupProductBtn.setBorderPainted(false);
		GridBagConstraints gbc_lookupProductBtn = new GridBagConstraints();
		gbc_lookupProductBtn.anchor = GridBagConstraints.NORTH;
		gbc_lookupProductBtn.fill = GridBagConstraints.HORIZONTAL;
		gbc_lookupProductBtn.insets = new Insets(0, 0, 5, 5);
		gbc_lookupProductBtn.gridx = 7;
		gbc_lookupProductBtn.gridy = 11;
		add(lookupProductBtn, gbc_lookupProductBtn);	// Had contentPane
		refillBanknoteBtn.setBackground(Color.LIGHT_GRAY);
		refillBanknoteBtn.setOpaque(true);
		refillBanknoteBtn.setBorderPainted(false);
		refillBanknoteBtn.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		GridBagConstraints gbc_refillBanknoteBtn = new GridBagConstraints();
		gbc_refillBanknoteBtn.anchor = GridBagConstraints.NORTH;
		gbc_refillBanknoteBtn.fill = GridBagConstraints.HORIZONTAL;
		gbc_refillBanknoteBtn.insets = new Insets(0, 0, 5, 5);
		gbc_refillBanknoteBtn.gridx = 3;
		gbc_refillBanknoteBtn.gridy = 12;
		add(refillBanknoteBtn, gbc_refillBanknoteBtn);	// Had contentPane
		emptyBanknoteBtn.setBackground(Color.LIGHT_GRAY);
		emptyBanknoteBtn.setOpaque(true);
		emptyBanknoteBtn.setBorderPainted(false);
		emptyBanknoteBtn.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		GridBagConstraints gbc_emptyBanknoteBtn = new GridBagConstraints();
		gbc_emptyBanknoteBtn.anchor = GridBagConstraints.NORTH;
		gbc_emptyBanknoteBtn.fill = GridBagConstraints.HORIZONTAL;
		gbc_emptyBanknoteBtn.insets = new Insets(0, 0, 5, 5);
		gbc_emptyBanknoteBtn.gridx = 4;
		gbc_emptyBanknoteBtn.gridy = 12;
		add(emptyBanknoteBtn, gbc_emptyBanknoteBtn);	// Had contentPane
		
		JButton addReceiptInkBtn = new JButton("Add Receipt Ink");
		addReceiptInkBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JButton addReceiptPaperBtn = new JButton("Add Receipt Paper");
		addReceiptPaperBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JButton removeProductBtn = new JButton("Remove Product");
		removeProductBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		removeProductBtn.setBackground(Color.LIGHT_GRAY);
		removeProductBtn.setOpaque(true);
		removeProductBtn.setBorderPainted(false);
		GridBagConstraints gbc_removeProductBtn = new GridBagConstraints();
		gbc_removeProductBtn.anchor = GridBagConstraints.NORTH;
		gbc_removeProductBtn.fill = GridBagConstraints.HORIZONTAL;
		gbc_removeProductBtn.insets = new Insets(0, 0, 5, 5);
		gbc_removeProductBtn.gridx = 7;
		gbc_removeProductBtn.gridy = 12;
		add(removeProductBtn, gbc_removeProductBtn);	// Had contentPane
		addReceiptPaperBtn.setBackground(Color.LIGHT_GRAY);
		addReceiptPaperBtn.setOpaque(true);
		addReceiptPaperBtn.setBorderPainted(false);
		addReceiptPaperBtn.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		GridBagConstraints gbc_addReceiptPaperBtn = new GridBagConstraints();
		gbc_addReceiptPaperBtn.anchor = GridBagConstraints.SOUTH;
		gbc_addReceiptPaperBtn.fill = GridBagConstraints.HORIZONTAL;
		gbc_addReceiptPaperBtn.insets = new Insets(0, 0, 0, 5);
		gbc_addReceiptPaperBtn.gridx = 3;
		gbc_addReceiptPaperBtn.gridy = 13;
		add(addReceiptPaperBtn, gbc_addReceiptPaperBtn);	// Had contentPane
		addReceiptInkBtn.setBackground(Color.LIGHT_GRAY);
		addReceiptInkBtn.setOpaque(true);
		addReceiptInkBtn.setBorderPainted(false);
		addReceiptInkBtn.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		GridBagConstraints gbc_addReceiptInkBtn = new GridBagConstraints();
		gbc_addReceiptInkBtn.anchor = GridBagConstraints.NORTH;
		gbc_addReceiptInkBtn.fill = GridBagConstraints.HORIZONTAL;
		gbc_addReceiptInkBtn.insets = new Insets(0, 0, 0, 5);
		gbc_addReceiptInkBtn.gridx = 4;
		gbc_addReceiptInkBtn.gridy = 13;
		add(addReceiptInkBtn, gbc_addReceiptInkBtn);	// Had contentPane
		
	}
	
}
