package seng300.software.observers;

import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.ReceiptPrinter;
import org.lsmr.selfcheckout.devices.observers.AbstractDeviceObserver;
import org.lsmr.selfcheckout.devices.observers.ReceiptPrinterObserver;

import seng300.software.SelfCheckoutSystemLogic;

public class PrinterObserver implements ReceiptPrinterObserver
{
	SelfCheckoutSystemLogic logic;
	boolean paperLow = false;
	boolean inkLow = false;
	
	public PrinterObserver(SelfCheckoutSystemLogic logic)
	{
		this.logic = logic;
	}

	@Override
	public void enabled(AbstractDevice<? extends AbstractDeviceObserver> device) 
	{
		System.out.println("Receipt printer is enabled.");
	}

	@Override
	public void disabled(AbstractDevice<? extends AbstractDeviceObserver> device)
	{
		System.out.println("Receipt printer is disabled.");
	}

	@Override
	public void outOfPaper(ReceiptPrinter printer)
	{
		printer.disable();

		paperLow = true;

		this.logic.manualBlock();

		System.out.println("Receipt printer out of paper.");
		logic.printerOutofPaper();
		// notify attendant station
		// block system until printer refilled?
	}

	@Override
	public void outOfInk(ReceiptPrinter printer)
	{
		printer.disable();

		inkLow = true;

		this.logic.manualBlock();

		System.out.println("Receipt printer out of ink.");
		
		logic.printerOutofInk();
		// notify attendant station
		// block system until printer refilled?
	}

	@Override
	public void paperAdded(ReceiptPrinter printer)
	{
		System.out.println("Paper added to receipt printer.");
		paperLow = false;
		printer.enable();
		logic.unblock();
	}

	@Override
	public void inkAdded(ReceiptPrinter printer)
	{
		System.out.println("Ink added to receipt printer.");
		inkLow = false;
		printer.enable();
		logic.unblock();
	}

}
