package seng300.software.GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;
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

public class AttendantMain extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AttendantMain frame = new AttendantMain();
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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 960, 540);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "40[]10[]10[]10[]50[]50[]30[]", "[]20[]20[]20[]20[]30[]30[]20[]"));
		
		JLabel lblNewLabel = new JLabel("Current Station:");
		contentPane.add(lblNewLabel, "cell 3 0 2 1,alignx center");
		
		JButton LogoutBtn = new JButton("Logout");
		LogoutBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		contentPane.add(LogoutBtn, "cell 6 0");
		
		
		JButton station1btn = new JButton("Station 1");
		station1btn.setBackground(Color.GRAY);
		station1btn.setOpaque(true);
		station1btn.setBorderPainted(false);
		station1btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		contentPane.add(station1btn, "cell 1 3");
		
		
		JButton station2btn = new JButton("Station 2");
		station2btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		station2btn.setBackground(Color.GRAY);
		station2btn.setOpaque(true);
		station2btn.setBorderPainted(false);
		contentPane.add(station2btn, "cell 2 3");
		
		
		JButton station3btn = new JButton("Station 3");
		station3btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		station3btn.setBackground(Color.GRAY);
		station3btn.setOpaque(true);
		station3btn.setBorderPainted(false);
		contentPane.add(station3btn, "cell 3 3");
		
		
		JButton station4btn = new JButton("Station 4");
		station4btn.setBackground(Color.GRAY);
		station4btn.setOpaque(true);
		station4btn.setBorderPainted(false);
		station4btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		contentPane.add(station4btn, "cell 4 3");
		
		JButton station5btn = new JButton("Station 5");
		station5btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		station5btn.setBackground(Color.GRAY);
		station5btn.setOpaque(true);
		station5btn.setBorderPainted(false);
		contentPane.add(station5btn, "cell 5 3");
		
		JButton station6btn = new JButton("Station 6");
		station6btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		station6btn.setBackground(Color.GRAY);
		station6btn.setOpaque(true);
		station6btn.setBorderPainted(false);
		contentPane.add(station6btn, "cell 6 3");
		
		JLabel labelSC = new JLabel("Station Controls");
		contentPane.add(labelSC, "cell 3 4 2 1,alignx center");
		
		
		JButton startupBtn = new JButton("Startup");
		startupBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		startupBtn.setBackground(Color.LIGHT_GRAY);
		startupBtn.setOpaque(true);
		startupBtn.setBorderPainted(false);
		contentPane.add(startupBtn, "cell 2 6,alignx center");
		
		JButton blockBtn = new JButton("Block");
		blockBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		blockBtn.setBackground(Color.LIGHT_GRAY);
		blockBtn.setOpaque(true);
		blockBtn.setBorderPainted(false);
		contentPane.add(blockBtn, "cell 3 6,growx");
		
		JButton unblockBtn = new JButton("Unblock");
		unblockBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		unblockBtn.setBackground(Color.LIGHT_GRAY);
		unblockBtn.setOpaque(true);
		unblockBtn.setBorderPainted(false);
		contentPane.add(unblockBtn, "cell 4 6,growx");
		
		JButton shutdownBtn = new JButton("Shut Down");
		shutdownBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		shutdownBtn.setBackground(Color.LIGHT_GRAY);
		shutdownBtn.setOpaque(true);
		shutdownBtn.setBorderPainted(false);
		contentPane.add(shutdownBtn, "cell 5 6,alignx center");
		
		JLabel hardwareFuncLabel = new JLabel("Hardware Functionalities");
		contentPane.add(hardwareFuncLabel, "cell 1 10, span 2,alignx center");

		JButton refillCoinsBtn = new JButton("Refill Coins");
		refillCoinsBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		refillCoinsBtn.setBackground(Color.LIGHT_GRAY);
		refillCoinsBtn.setOpaque(true);
		refillCoinsBtn.setBorderPainted(false);
		contentPane.add(refillCoinsBtn, "cell 1 11,growx");
		
		JButton emptyCoinsBtn = new JButton("Empty Coins");
		emptyCoinsBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		emptyCoinsBtn.setBackground(Color.LIGHT_GRAY);
		emptyCoinsBtn.setOpaque(true);
		emptyCoinsBtn.setBorderPainted(false);
		contentPane.add(emptyCoinsBtn, "cell 2 11,growx");
		
		JButton refillBanknoteBtn = new JButton("Refill Banknotes");
		refillBanknoteBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		refillBanknoteBtn.setBackground(Color.LIGHT_GRAY);
		refillBanknoteBtn.setOpaque(true);
		refillBanknoteBtn.setBorderPainted(false);
		refillBanknoteBtn.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		contentPane.add(refillBanknoteBtn, "cell 1 12,growx");
		
		JButton emptyBanknoteBtn = new JButton("Empty Banknotes");
		emptyBanknoteBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		emptyBanknoteBtn.setBackground(Color.LIGHT_GRAY);
		emptyBanknoteBtn.setOpaque(true);
		emptyBanknoteBtn.setBorderPainted(false);
		emptyBanknoteBtn.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		contentPane.add(emptyBanknoteBtn, "cell 2 12,growx");
		
		JButton addReceiptPaperBtn = new JButton("Add Receipt Paper");
		addReceiptPaperBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		addReceiptPaperBtn.setBackground(Color.LIGHT_GRAY);
		addReceiptPaperBtn.setOpaque(true);
		addReceiptPaperBtn.setBorderPainted(false);
		addReceiptPaperBtn.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		contentPane.add(addReceiptPaperBtn, "cell 1 13,growx");
		
		JButton addReceiptInkBtn = new JButton("Add Receipt Ink");
		addReceiptInkBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		addReceiptInkBtn.setBackground(Color.LIGHT_GRAY);
		addReceiptInkBtn.setOpaque(true);
		addReceiptInkBtn.setBorderPainted(false);
		addReceiptInkBtn.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		contentPane.add(addReceiptInkBtn, "cell 2 13,growx");
		
		
		JLabel customerAssistLabel = new JLabel("Customer Assistance");
		contentPane.add(customerAssistLabel, "cell 4 10, span 2,alignx center");
		
		JButton lookupProductBtn = new JButton("Lookup Product");
		lookupProductBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		lookupProductBtn.setBackground(Color.LIGHT_GRAY);
		lookupProductBtn.setOpaque(true);
		lookupProductBtn.setBorderPainted(false);
		contentPane.add(lookupProductBtn, "cell 4 11 2 1,growx");
		
		JButton removeProductBtn = new JButton("Remove Product");
		removeProductBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		removeProductBtn.setBackground(Color.LIGHT_GRAY);
		removeProductBtn.setOpaque(true);
		removeProductBtn.setBorderPainted(false);
		contentPane.add(removeProductBtn, "cell 4 12 2 1,growx");
		
	}
	
}
