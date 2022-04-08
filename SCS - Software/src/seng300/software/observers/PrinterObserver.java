package seng300.software.observers;

import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.ReceiptPrinter;
import org.lsmr.selfcheckout.devices.observers.AbstractDeviceObserver;
import org.lsmr.selfcheckout.devices.observers.ReceiptPrinterObserver;

import seng300.software.SelfCheckoutSystemLogic;

public class PrinterObserver implements ReceiptPrinterObserver
{
	SelfCheckoutSystemLogic logic;
	
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
		this.logic.block();
		System.out.println("Receipt printer out of paper.");
		// notify attendant station
		// block system until printer refilled?
	}

	@Override
	public void outOfInk(ReceiptPrinter printer)
	{
		printer.disable();
		this.logic.block();
		System.out.println("Receipt printer out of ink.");
		// notify attendant station
		// block system until printer refilled?
	}

	@Override
	public void paperAdded(ReceiptPrinter printer)
	{
		System.out.println("Paper added to receipt printer.");
		printer.enable();
	}

	@Override
	public void inkAdded(ReceiptPrinter printer)
	{
		System.out.println("Ink added to receipt printer.");
		printer.enable();
	}

}
