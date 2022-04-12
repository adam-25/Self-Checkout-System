package seng300.software.GUI;

import seng300.software.SelfCheckoutSystemLogic;

public interface BlockNotifiableGui {

	
	public void notifyOwnBagBlock(SelfCheckoutSystemLogic stationOfConcern);
	
	public void notifyWeightDiscBlock(SelfCheckoutSystemLogic stationOfConcern);
	
//	public void notifyRemoveProductBlock(SelfCheckoutSystemLogic selfCheckoutSystemLogic);

	public void notifyPrinterOutPaper(SelfCheckoutSystemLogic selfCheckoutSystemLogic);

	public void notifyPrinterOutInk(SelfCheckoutSystemLogic selfCheckoutSystemLogic);
}
