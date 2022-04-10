package seng300.software.GUI;

import java.awt.CardLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import seng300.software.AttendantLogic;

public class AttendantGUI extends JPanel {
	AttendantLogic aLogic;
	
	private AttendantLogin loginPanel;
	private AttendantMainMenu attendantMainPanel;
	/**
	 * Create the panel.
	 */
	public AttendantGUI(AttendantLogic aLogic) {
		this.aLogic = aLogic;
		setLayout(new CardLayout(0, 0));
		
		loginPanel = new AttendantLogin(aLogic, this);
		attendantMainPanel = new AttendantMainMenu(aLogic, this);	// Need to discuss on main and mainMenuy
		
		add(loginPanel);
		add(attendantMainPanel);
		openAttendantLogin();
	}
	
	public void openAttendantMain() {
		loginPanel.setVisible(false);
		attendantMainPanel.setVisible(true);
	}
	
	public void openAttendantLogin() {
		loginPanel.setVisible(true);
		attendantMainPanel.setVisible(false);
	}
	
	public void openProductLookUp() {
		
	}
	
	public void openRemoveItemLog() {
		
	}
	
	public void blockFromOutside() {
		// Deal with colour change in station buttons
		// also communicate with AttendantMainMenu
	}
	
	public void unblockFromOutside() {
		// ??
	}
	/**
	 * Launch the application. TO BE USED FOR TESTING ONLY!
	 */
	public static void main(String[] args) {
		AttendantLogic aLogic = AttendantLogic.getInstance();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JFrame frame = new JFrame();
					
					AttendantGUI gui = aLogic.attachGUI();
//					AttendantGUI gui = new AttendantGUI(aLogic);
					frame.getContentPane().add(gui);
					frame.pack();
					frame.setVisible(true);
//					gui.openAttendantLogin();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
