package seng300.testing;

import seng300.software.SelfCheckoutSystemLogic;
import seng300.software.GUI.CustomerCheckoutPanel;
import seng300.software.GUI.CustomerGui;
public class GuiStub extends CustomerGui  {

	public GuiStub(SelfCheckoutSystemLogic logic) {
		super(logic);
		// TODO Auto-generated constructor stub
	}

	@Override
	public CustomerCheckoutPanel getCheckoutPanel() {
		return null;
	}

	@Override
	public void disableGui() {
		// TODO Auto-generated method stub
	}

	@Override
	public void enableGui() {
		// TODO Auto-generated method stub
	}

	@Override
	public void startup() {
		// TODO Auto-generated method stub
	}

	@Override
	public void shutdown() {
		// TODO Auto-generated method stub
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
	}
	
	
	

}
