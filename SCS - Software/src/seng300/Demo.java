package seng300;

import java.awt.EventQueue;
import java.math.BigDecimal;
import java.util.Currency;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.lsmr.selfcheckout.Barcode;
import org.lsmr.selfcheckout.Numeral;
import org.lsmr.selfcheckout.PriceLookupCode;
import org.lsmr.selfcheckout.devices.OverloadException;
import org.lsmr.selfcheckout.devices.ReceiptPrinter;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.SupervisionStation;
import org.lsmr.selfcheckout.external.ProductDatabases;
import org.lsmr.selfcheckout.products.BarcodedProduct;
import org.lsmr.selfcheckout.products.PLUCodedProduct;
import seng300.software.AttendantLogic;
import seng300.software.SelfCheckoutSystemLogic;
import seng300.software.GUI.AttendantGUI;
import seng300.software.SelfCheckoutSystemLogic;
import seng300.software.GUI.CustomerGui;

/**
 * Class to be used for creating the demo application for the system.

 *
 */
public class Demo
{	
	public static final Currency CAD_CURRENCY = Currency.getInstance("CAD");
	public static final int[] CAD_BANKNOTES = new int[] { 5, 10, 15, 20, 50, 100 };
	public static final BigDecimal CAD_TOONIE = new BigDecimal("2.00");
	public static final BigDecimal CAD_LOONIE = new BigDecimal("1.00");
	public static final BigDecimal CAD_QUARTER = new BigDecimal("0.25");
	public static final BigDecimal CAD_DIME = new BigDecimal("0.10");
	public static final BigDecimal CAD_NICKEL = new BigDecimal("0.05");
	
	public static final BigDecimal[] CAD_COINS = new BigDecimal[] { CAD_NICKEL, CAD_DIME, CAD_QUARTER, CAD_LOONIE, CAD_TOONIE };

	/**
	 * Launch the application. 
	 */
	public static void main(String[] args) throws OverloadException {

		// Create and add products to the database
		initProductDatabase();

		// TODO create stations and attach them to the attendant logic supervision station
		
		int stationCount = AttendantLogic.ss.supervisedStationCount();
		System.out.println(stationCount);
		for (int i = 1; i <= stationCount; i++)
		{
			AttendantLogic.getInstance().getSCSLogic(i).attachGUI();
		}

		EventQueue.invokeLater(() -> {
			try {
				AttendantLogic.getInstance().attachGUI();
				AttendantLogic.ss.screen.getFrame().setContentPane(AttendantLogic.getInstance().getGuiInstance());
				AttendantLogic.ss.screen.getFrame().setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
				AttendantLogic.ss.screen.getFrame().pack();
				AttendantLogic.ss.screen.getFrame().setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	// This is just a sample of everything that has to be done to initialise a 
	// station (except connection to attendant station).
	// I think we should init the scs stations in the main method of the demo
	// and then attach them to the AttendantLogic via a getter
//	private static SelfCheckoutStation GetStation()
//	{
//		SelfCheckoutStation scs = new SelfCheckoutStation(CAD_CURRENCY, CAD_BANKNOTES, CAD_COINS, 20000, 1);
//		try {
//			scs.printer.addInk(ReceiptPrinter.MAXIMUM_INK);
//			scs.printer.addPaper(ReceiptPrinter.MAXIMUM_PAPER);
//		} catch (OverloadException e) {
//			// should never be thrown
//		}
//		SelfCheckoutSystemLogic logic = new SelfCheckoutSystemLogic(scs);
//		CustomerGui gui = new CustomerGui(logic);
////		logic.attachDisableableGui(gui);
//		logic.station.screen.getFrame().setContentPane(gui);
//		return scs;
//	}

	private static void initProductDatabase()
	{
		PriceLookupCode plu1 = new PriceLookupCode("00000");
		PLUCodedProduct pluProduct1 = new PLUCodedProduct(plu1, "Banana", new BigDecimal("1.74"));
		ProductDatabases.PLU_PRODUCT_DATABASE.put(plu1, pluProduct1);
		
		PriceLookupCode plu2 = new PriceLookupCode("00001");
		PLUCodedProduct pluProduct2 = new PLUCodedProduct(plu2, "Banana Plantain Ripe", new BigDecimal("2.18"));
		ProductDatabases.PLU_PRODUCT_DATABASE.put(plu2, pluProduct2);
		
		PriceLookupCode plu3 = new PriceLookupCode("00002");
		PLUCodedProduct pluProduct3 = new PLUCodedProduct(plu3, "Cabbage Green", new BigDecimal("1.52"));
		ProductDatabases.PLU_PRODUCT_DATABASE.put(plu3, pluProduct3);
		
		PriceLookupCode plu4 = new PriceLookupCode("00003");
		PLUCodedProduct pluProduct4 = new PLUCodedProduct(plu4, "Black Beans", new BigDecimal("4.16"));
		ProductDatabases.PLU_PRODUCT_DATABASE.put(plu4, pluProduct4);
		
		PriceLookupCode plu5 = new PriceLookupCode("00004");
		PLUCodedProduct pluProduct5 = new PLUCodedProduct(plu5, "Pear D'Anjou", new BigDecimal("5.49"));
		ProductDatabases.PLU_PRODUCT_DATABASE.put(plu5, pluProduct5);
		
		PriceLookupCode plu6 = new PriceLookupCode("00005");
		PLUCodedProduct pluProduct6 = new PLUCodedProduct(plu6, "Pepper Green Sweet", new BigDecimal("6.59"));
		ProductDatabases.PLU_PRODUCT_DATABASE.put(plu6, pluProduct6);
		
		PriceLookupCode plu7 = new PriceLookupCode("00006");
		PLUCodedProduct pluProduct7 = new PLUCodedProduct(plu7, "Peppers Jalepeno", new BigDecimal("3.28"));
		ProductDatabases.PLU_PRODUCT_DATABASE.put(plu7, pluProduct7);
		
		PriceLookupCode plu8 = new PriceLookupCode("00007");
		PLUCodedProduct pluProduct8 = new PLUCodedProduct(plu8, "Pepper Yellow Sweet", new BigDecimal("6.59"));
		ProductDatabases.PLU_PRODUCT_DATABASE.put(plu8, pluProduct8);
		
		PriceLookupCode plu9 = new PriceLookupCode("00008");
		PLUCodedProduct pluProduct9 = new PLUCodedProduct(plu9, "Pepper Red Sweet", new BigDecimal("6.59"));
		ProductDatabases.PLU_PRODUCT_DATABASE.put(plu9, pluProduct9);
		
		PriceLookupCode plu10 = new PriceLookupCode("00009");
		PLUCodedProduct pluProduct10 = new PLUCodedProduct(plu10, "Tomato Roma", new BigDecimal("5.45"));
		ProductDatabases.PLU_PRODUCT_DATABASE.put(plu10, pluProduct10);
		
		Barcode b1 = new Barcode(new Numeral[] { Numeral.zero });
		BarcodedProduct barcodeProduct1 = new BarcodedProduct(b1, "Spaghetti", new BigDecimal("4.97"), 500);
		ProductDatabases.BARCODED_PRODUCT_DATABASE.put(b1, barcodeProduct1);
		
		Barcode b2 = new Barcode(new Numeral[] { Numeral.one });
		BarcodedProduct barcodeProduct2 = new BarcodedProduct(b2, "Rice Jasmine 1kg", new BigDecimal("4.99"), 1000);
		ProductDatabases.BARCODED_PRODUCT_DATABASE.put(b2, barcodeProduct2);
		
		Barcode b3 = new Barcode(new Numeral[] { Numeral.two });
		BarcodedProduct barcodeProduct3 = new BarcodedProduct(b3, "Sugar Brown 1kg", new BigDecimal("6.99"), 1000);
		ProductDatabases.BARCODED_PRODUCT_DATABASE.put(b3, barcodeProduct3);
		
		Barcode b4 = new Barcode(new Numeral[] { Numeral.three });
		BarcodedProduct barcodeProduct4 = new BarcodedProduct(b4, "Kidney Beans Dark Red", new BigDecimal("11.99"), 3630);
		ProductDatabases.BARCODED_PRODUCT_DATABASE.put(b4, barcodeProduct4);
		
		Barcode b5 = new Barcode(new Numeral[] { Numeral.four });
		BarcodedProduct barcodeProduct5 = new BarcodedProduct(b5, "Pistacchios", new BigDecimal("19.99"), 1360);
		ProductDatabases.BARCODED_PRODUCT_DATABASE.put(b5, barcodeProduct5);
	}
}
