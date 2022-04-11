package seng300.testing;

import seng300.software.GUI.DisableableGui;

public class DisableableGuiStub implements DisableableGui {

	public boolean started = true;
	public boolean stopped = false;
	
	
	@Override
	public void startup() {
		// TODO Auto-generated method stub

	}

	@Override
	public void shutdown() {
		// TODO Auto-generated method stub

	}

	@Override
	public void disableGui() {
		// TODO Auto-generated method stub

	}

	@Override
	public void enableGui() {
		// TODO Auto-generated method stub

	}

}
