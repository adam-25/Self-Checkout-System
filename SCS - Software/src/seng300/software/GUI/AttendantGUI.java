package seng300.software.GUI;

import java.awt.CardLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import seng300.software.AttendantLogic;
import seng300.software.SelfCheckoutSystemLogic;

public class AttendantGUI extends JPanel {
	AttendantLogic aLogic;
	
	private AttendantLogin loginPanel;
	private AttendantMainMenu attendantMainPanel;
	private ProductLookupPanel attendantLookup;
	private RemoveItemLog removeItems;
	private UseOwnBagPopup bagPopup;
	private AreYouSure doubleCheckPopup;
	/**
	 * Create the panel.
	 */
	public AttendantGUI(AttendantLogic aLogic) {
		this.aLogic = aLogic;
		setLayout(new CardLayout(0, 0));
		
		loginPanel = new AttendantLogin(aLogic, this);
		
		attendantMainPanel = new AttendantMainMenu(aLogic, this);	// Need to discuss on main and mainMenuy
		
		attendantLookup = new ProductLookupPanel();	
		attendantLookup.returnButton.addActionListener(e -> openAttendantMain());
		
		removeItems = new RemoveItemLog();

		add(loginPanel);
		add(attendantMainPanel);
		add(attendantLookup);
		openAttendantLogin();
	}
	
	public void openAttendantMain() {
		loginPanel.setVisible(false);
		attendantLookup.setVisible(false);
		removeItems.setVisible(false);
		attendantMainPanel.setVisible(true);
	}
	
	public void openAttendantLogin() {
		loginPanel.setVisible(true);
		attendantLookup.setVisible(false);
		removeItems.setVisible(false);
		attendantMainPanel.setVisible(false);
	}
	
	public void openProductLookUp() {
		loginPanel.setVisible(false);
		attendantLookup.setVisible(true);
		removeItems.setVisible(false);
		attendantMainPanel.setVisible(false);
	}
	
	public void openRemoveItemLog(int systemNum) {
		removeItems = removeItems.replaceList(aLogic.getSCSLogic(systemNum).cart.getProducts(), systemNum);
		loginPanel.setVisible(false);
		attendantLookup.setVisible(false);
		removeItems.setVisible(true);
//		attendantMainPanel.setVisible(false);
	}
	
	
	public void blockFromOutside() {
		// Deal with colour change in station buttons
		// also communicate with AttendantMainMenu
	}
	
	public boolean areYouSurePopupCall(SelfCheckoutSystemLogic systemAffected) {
		doubleCheckPopup = new AreYouSure();
		return doubleCheckPopup.secondCheck();
	}
	public void ownBagBlock(SelfCheckoutSystemLogic sadSystemBeingBlocked) {
		int stationNum = 0;
		if (sadSystemBeingBlocked.equals(aLogic.getSCSLogic(1))) {
			stationNum = 1;
		} else if (sadSystemBeingBlocked.equals(aLogic.getSCSLogic(2))) {
			stationNum = 2;
		} else if (sadSystemBeingBlocked.equals(aLogic.getSCSLogic(3))) {
			stationNum = 3;
		} else if (sadSystemBeingBlocked.equals(aLogic.getSCSLogic(4))) {
			stationNum = 4;
		} else if (sadSystemBeingBlocked.equals(aLogic.getSCSLogic(5))) {
			stationNum = 5;
		} else if (sadSystemBeingBlocked.equals(aLogic.getSCSLogic(6))) {
			stationNum = 6;
		}
		bagPopup = new UseOwnBagPopup(sadSystemBeingBlocked, stationNum);
	}
	
	public void weightDiscBlock(SelfCheckoutSystemLogic sadSystemBeingBlocked) {
		
	}
	
	public void printerOutOfPaperBlock(SelfCheckoutSystemLogic sadSystemBeingBlocked) {
		
	}
	
	public void printerOutOfInkBlock(SelfCheckoutSystemLogic sadSystemBeingBlocked) {
		
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
