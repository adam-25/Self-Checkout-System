package seng300.software;

import javax.swing.JFrame;

public class WeightDiscrepancyPopup {
	private SelfCheckoutSystemLogic logic;
	public WeightDiscrepancyPopup(SelfCheckoutSystemLogic logic) {
		// Need to implement an actionlistener that calls unblock
		// from a specified checkoutstation after approve button is pressed
		JFrame weightDiscPop = new JFrame();
		this.logic = logic;
	}
}
