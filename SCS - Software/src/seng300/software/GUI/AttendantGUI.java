package seng300.software.GUI;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.lsmr.selfcheckout.PLUCodedItem;
import org.lsmr.selfcheckout.PriceLookupCode;
import org.lsmr.selfcheckout.external.ProductDatabases;
import org.lsmr.selfcheckout.products.PLUCodedProduct;

import seng300.software.AttendantLogic;
import seng300.software.SelfCheckoutSystemLogic;
import seng300.software.exceptions.ProductNotFoundException;

public class AttendantGUI extends JPanel {
	AttendantLogic aLogic;
	
	private AttendantLogin loginPanel;
	private AttendantMainMenu attendantMainPanel;
	private ProductLookupPanel attendantLookup;
	private SystemStarting systemStartUp;
	private RemoveItemLog removeItems;
	private WeightDiscrepancyPopup weightPopup;
	private UseOwnBagPopup bagPopup;
	private NeedToAddInkPopup inkPopup;
	private NeedToAddPaperPopup paperPopup;
	private AreYouSure doubleCheckPopup;
	private OverloadPopup overloadPopup;

	private PLUCodedItem lastAddedItem;

	private String lastItemDescription;
	/**
	 * Create the panel.
	 */
	public AttendantGUI(AttendantLogic aLogic) {
		this.aLogic = aLogic;
		setLayout(new CardLayout(0, 0));
		
		loginPanel = new AttendantLogin(aLogic, this);
		
		attendantMainPanel = new AttendantMainMenu(aLogic, this);	// Need to discuss on main and mainMenuy
		
		attendantLookup = new ProductLookupPanel();
		for (KeyboardButton btn : attendantLookup.keyboardBtns) {
			btn.addActionListener(e -> push(btn));
		}
		attendantLookup.returnButton.addActionListener(e -> openAttendantMain());
		
		removeItems = new RemoveItemLog();

		systemStartUp = new SystemStarting(0, this);
		
		add(loginPanel);
		add(attendantMainPanel);
		add(attendantLookup);
		add(systemStartUp);
		openAttendantLogin();
	}
	
	public void openAttendantMain() {
		loginPanel.setVisible(false);
		attendantLookup.setVisible(false);
		removeItems.setVisible(false);
		attendantMainPanel.setVisible(true);
		systemStartUp.setVisible(false);
	}
	
	public void openAttendantLogin() {
		loginPanel.setVisible(true);
		attendantLookup.setVisible(false);
		removeItems.setVisible(false);
		attendantMainPanel.setVisible(false);
		systemStartUp.setVisible(false);
	}
	
	public void openProductLookUp() {
		loginPanel.setVisible(false);
		attendantLookup.setVisible(true);
		removeItems.setVisible(false);
		attendantMainPanel.setVisible(false);
		systemStartUp.setVisible(false);
		
	}
	
	public void openRemoveItemLog(int systemNum) {
		removeItems = removeItems.replaceList(aLogic.getSCSLogic(systemNum).cart.getProducts(), systemNum);
		loginPanel.setVisible(false);
		attendantLookup.setVisible(false);
		removeItems.setVisible(true);
		systemStartUp.setVisible(false);
//		attendantMainPanel.setVisible(false);
	}
	
	
	public void blockFromOutside(SelfCheckoutSystemLogic systemAffected) {
		// Deal with colour change in station buttons
		// also communicate with AttendantMainMenu
		int stationNum = 0;
		if (systemAffected.equals(aLogic.getSCSLogic(1))) {
			attendantMainPanel.station1Btn.setBackground(Color.RED);
		} else if (systemAffected.equals(aLogic.getSCSLogic(2))) {
			attendantMainPanel.station2Btn.setBackground(Color.RED);
		} else if (systemAffected.equals(aLogic.getSCSLogic(3))) {
			attendantMainPanel.station3Btn.setBackground(Color.RED);
		} else if (systemAffected.equals(aLogic.getSCSLogic(4))) {
			attendantMainPanel.station4Btn.setBackground(Color.RED);
		} else if (systemAffected.equals(aLogic.getSCSLogic(5))) {
			attendantMainPanel.station5Btn.setBackground(Color.RED);
		} else if (systemAffected.equals(aLogic.getSCSLogic(6))) {
			attendantMainPanel.station6Btn.setBackground(Color.RED);
		}
	}
	
	public boolean areYouSurePopupCall(SelfCheckoutSystemLogic systemAffected) {
		doubleCheckPopup = new AreYouSure();
		return doubleCheckPopup.secondCheck();
	}
	
	public boolean overloadPopupCall(SelfCheckoutSystemLogic systemAffected) {
		overloadPopup = new OverloadPopup();
		return overloadPopup.getIsOverload();
	}
	
	
	public void ownBagBlock(SelfCheckoutSystemLogic systemAffected) {
		int stationNum = 0;
		JButton buttonUsed = null;
		if (systemAffected.equals(aLogic.getSCSLogic(1))) {
			stationNum = 1;
			buttonUsed = attendantMainPanel.station1Btn;
			attendantMainPanel.station1Btn.setBackground(Color.RED);
		} else if (systemAffected.equals(aLogic.getSCSLogic(2))) {
			stationNum = 2;
			buttonUsed = attendantMainPanel.station2Btn;
			attendantMainPanel.station2Btn.setBackground(Color.RED);
		} else if (systemAffected.equals(aLogic.getSCSLogic(3))) {
			stationNum = 3;
			buttonUsed = attendantMainPanel.station3Btn;
			attendantMainPanel.station3Btn.setBackground(Color.RED);
		} else if (systemAffected.equals(aLogic.getSCSLogic(4))) {
			stationNum = 4;
			buttonUsed = attendantMainPanel.station4Btn;
			attendantMainPanel.station4Btn.setBackground(Color.RED);
		} else if (systemAffected.equals(aLogic.getSCSLogic(5))) {
			stationNum = 5;
			buttonUsed = attendantMainPanel.station5Btn;
			attendantMainPanel.station5Btn.setBackground(Color.RED);
		} else if (systemAffected.equals(aLogic.getSCSLogic(6))) {
			stationNum = 6;
			buttonUsed = attendantMainPanel.station6Btn;
			attendantMainPanel.station6Btn.setBackground(Color.RED);
		}
		bagPopup = new UseOwnBagPopup(systemAffected, stationNum, buttonUsed);
		bagPopup.setVisible(true);
	}
	
	public void weightDiscBlock(SelfCheckoutSystemLogic systemAffected) {
		int stationNum = 0;
		JButton buttonUsed = null;
		if (systemAffected.equals(aLogic.getSCSLogic(1))) {
			stationNum = 1;
			buttonUsed = attendantMainPanel.station1Btn;
			attendantMainPanel.station1Btn.setBackground(Color.RED);
		} else if (systemAffected.equals(aLogic.getSCSLogic(2))) {
			stationNum = 2;
			buttonUsed = attendantMainPanel.station2Btn;
			attendantMainPanel.station2Btn.setBackground(Color.RED);
		} else if (systemAffected.equals(aLogic.getSCSLogic(3))) {
			stationNum = 3;
			buttonUsed = attendantMainPanel.station3Btn;
			attendantMainPanel.station3Btn.setBackground(Color.RED);
		} else if (systemAffected.equals(aLogic.getSCSLogic(4))) {
			stationNum = 4;
			buttonUsed = attendantMainPanel.station4Btn;
			attendantMainPanel.station4Btn.setBackground(Color.RED);
		} else if (systemAffected.equals(aLogic.getSCSLogic(5))) {
			stationNum = 5;
			buttonUsed = attendantMainPanel.station5Btn;
			attendantMainPanel.station5Btn.setBackground(Color.RED);
		} else if (systemAffected.equals(aLogic.getSCSLogic(6))) {
			stationNum = 6;
			buttonUsed = attendantMainPanel.station6Btn;
			attendantMainPanel.station6Btn.setBackground(Color.RED);
		}
		weightPopup = new WeightDiscrepancyPopup(systemAffected, buttonUsed);
		weightPopup.setVisible(true);
	}
	
	public void printerOutOfPaperBlock(SelfCheckoutSystemLogic systemAffected) {
		int stationNum = 0;
		if (systemAffected.equals(aLogic.getSCSLogic(1))) {
			stationNum = 1;
			attendantMainPanel.station1Btn.setBackground(Color.RED);
		} else if (systemAffected.equals(aLogic.getSCSLogic(2))) {
			stationNum = 2;
			attendantMainPanel.station2Btn.setBackground(Color.RED);
		} else if (systemAffected.equals(aLogic.getSCSLogic(3))) {
			stationNum = 3;
			attendantMainPanel.station3Btn.setBackground(Color.RED);
		} else if (systemAffected.equals(aLogic.getSCSLogic(4))) {
			stationNum = 4;
			attendantMainPanel.station4Btn.setBackground(Color.RED);
		} else if (systemAffected.equals(aLogic.getSCSLogic(5))) {
			stationNum = 5;
			attendantMainPanel.station5Btn.setBackground(Color.RED);
		} else if (systemAffected.equals(aLogic.getSCSLogic(6))) {
			stationNum = 6;
			attendantMainPanel.station6Btn.setBackground(Color.RED);
		}
		paperPopup = new NeedToAddPaperPopup(stationNum);
	}
	
	public void printerOutOfInkBlock(SelfCheckoutSystemLogic systemAffected) {
		int stationNum = 0;
		if (systemAffected.equals(aLogic.getSCSLogic(1))) {
			stationNum = 1;
			attendantMainPanel.station1Btn.setBackground(Color.RED);
		} else if (systemAffected.equals(aLogic.getSCSLogic(2))) {
			stationNum = 2;
			attendantMainPanel.station2Btn.setBackground(Color.RED);
		} else if (systemAffected.equals(aLogic.getSCSLogic(3))) {
			stationNum = 3;
			attendantMainPanel.station3Btn.setBackground(Color.RED);
		} else if (systemAffected.equals(aLogic.getSCSLogic(4))) {
			stationNum = 4;
			attendantMainPanel.station4Btn.setBackground(Color.RED);
		} else if (systemAffected.equals(aLogic.getSCSLogic(5))) {
			stationNum = 5;
			attendantMainPanel.station5Btn.setBackground(Color.RED);
		} else if (systemAffected.equals(aLogic.getSCSLogic(6))) {
			stationNum = 6;
			attendantMainPanel.station6Btn.setBackground(Color.RED);
		}
		inkPopup = new NeedToAddInkPopup(stationNum);
	}
	
	public void startUp(int systemNum) {
		loginPanel.setVisible(false);
		attendantLookup.setVisible(false);
		removeItems.setVisible(false);
		attendantMainPanel.setVisible(false);
		systemStartUp.setVisible(true);
		
		systemStartUp.currentSystem(systemNum);
		systemStartUp.shoutOff();
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
					frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
					frame.pack();
					frame.setVisible(true);
//					gui.openAttendantLogin();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private void push(KeyboardButton btn) {
		KeyboardKey key = btn.getKey();
		String searchText = attendantLookup.getSearchText();
		if (key == KeyboardKey.BACK) {
			if (!searchText.isEmpty()) {
				searchText = searchText.substring(0, searchText.length() - 1);
				attendantLookup.setSearchText(searchText);
				lookupProduct(searchText);
			}
			// ignore attempts to backspace when search field empty
		} else if (key == KeyboardKey.CLEAR) {
			attendantLookup.reset();
		} else if (key != KeyboardKey.ENTER) {
			searchText += key.getValue();
			attendantLookup.setSearchText(searchText);
			lookupProduct(searchText);
		}
	}
	
	private void lookupProduct(String searchText) {
		if (!searchText.isEmpty()) {
			List<PLUCodedProduct> results = this.attendantMainPanel.getCurrentSystemAccessed().productLookUp(searchText);
			List<LookupResultButton> btns = new ArrayList<>();
			for (PLUCodedProduct p : results) {
				LookupResultButton btn = new LookupResultButton(p);
				btn.addActionListener(e -> {
					try {
						System.out.println("sd");
						addPluProductToCart(btn.getProduct().getPLUCode());
					} catch (ProductNotFoundException ex) {
						// This should never execute.
					}
				});
				btns.add(btn);
			}
			attendantLookup.displayProducts(btns);
		}
		// ignore empty searches
	}
	
	private void addPluProductToCart(PriceLookupCode code) throws ProductNotFoundException { //always assume weight check
		SelfCheckoutSystemLogic logic = this.attendantMainPanel.getCurrentSystemAccessed();
		if (ProductDatabases.PLU_PRODUCT_DATABASE.containsKey(code)) {
			// Create random plucoded product for testing
			double maxScaleWeight = logic.station.scanningArea.getWeightLimit();
			Random rand = new Random();
			double weight = rand.nextDouble() * maxScaleWeight + logic.getBaggingAreaSensitivity();
			PLUCodedItem item = new PLUCodedItem(code, weight);
			// add product to cart (no exception should ever be thrown)
			logic.getCart().addPLUCodedProductToCart(code, item.getWeight());
			lastAddedItem = item;
			lastItemDescription = ProductDatabases.PLU_PRODUCT_DATABASE.get(code).getDescription();
			BigDecimal pricePerKilo = ProductDatabases.PLU_PRODUCT_DATABASE.get(code).getPrice();
			logic.getAttachedGui().getCheckoutPanel().itemLogPanel.addItem(lastItemDescription,
					pricePerKilo.multiply(new BigDecimal(item.getWeight() / 1000.0)));
			BigDecimal cartTotal = logic.getCart().getCartTotal();
			if (logic.station.mainScanner.isDisabled())
			{
				cartTotal = cartTotal.subtract(new BigDecimal(0.05 * logic.cart.getBags()));
			}
			logic.getAttachedGui().getCheckoutPanel().itemLogPanel.setBillTotalValue(cartTotal);
			//displayPlaceItemPopup();
		} else {
			throw new ProductNotFoundException();
		}
	}

}
