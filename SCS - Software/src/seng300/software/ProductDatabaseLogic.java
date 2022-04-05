package seng300.software;
/**
 * Simulates a product database
 * to be used by the self checkout system
 * for testing purposes.
 */

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import org.lsmr.selfcheckout.Item;
import org.lsmr.selfcheckout.Numeral;
import org.lsmr.selfcheckout.Barcode;
import org.lsmr.selfcheckout.PriceLookupCode;
import org.lsmr.selfcheckout.external.ProductDatabases;
import org.lsmr.selfcheckout.BarcodedItem;
import org.lsmr.selfcheckout.PLUCodedItem;
import org.lsmr.selfcheckout.products.BarcodedProduct;
import org.lsmr.selfcheckout.products.PLUCodedProduct;
import org.lsmr.selfcheckout.products.Product;

import seng300.software.exceptions.ProductNotFoundException;

public class ProductDatabaseLogic
{
	//forcing this to work with the new ProductDatabases class
	public BarcodedProduct getProduct(Barcode barcode) throws ProductNotFoundException
	{
		if (!ProductDatabases.BARCODED_PRODUCT_DATABASE.containsKey(barcode))
			throw new ProductNotFoundException();
		return ProductDatabases.BARCODED_PRODUCT_DATABASE.get(barcode);
	}
	
	public PLUCodedProduct getProduct(PriceLookupCode plu) throws ProductNotFoundException
	{
		if (!ProductDatabases.PLU_PRODUCT_DATABASE.containsKey(plu))
			throw new ProductNotFoundException();
		return ProductDatabases.PLU_PRODUCT_DATABASE.get(plu);
	}
	
	//everything below is old code
	
//	private Map<Barcode, BarcodedProduct> barcodedProducts = new HashMap<>();
//	
//	public ProductDatabase() {}
//
//	public ProductDatabase(int numBarcodedProducts, double maxScaleWeight)
//	{
//		for (int i = 0; i < numBarcodedProducts; i++)
//		{
//			Barcode barcode;
//			while (barcodedProducts.containsKey(barcode = randomBarcode()));
//			BarcodedProduct barcodedProduct = new BarcodedProduct(barcode, "", 
//						randomPrice(50.0), randomWeightInGrams(maxScaleWeight));
//			barcodedProducts.put(barcode,  barcodedProduct);
//		}
//	}
//	/**
//	 * 
//	 * @return
//	 */
//	public Map<Barcode, BarcodedProduct> getProducts()
//	{
//		return barcodedProducts;
//	}
//
//	/**
//	 * Finds and returns the BarcodedProduct with the specified barcode.
//	 * 
//	 * @param barcode
//	 * 			The barcode for the desired product. 
//	 * 
//	 * @return Returns corresponding BarcodedProduct, if exits.
//	 */
//	public BarcodedProduct getProduct(Barcode barcode) throws ProductNotFoundException
//	{
//		if (!barcodedProducts.containsKey(barcode))
//			throw new ProductNotFoundException();
//		
//		return barcodedProducts.get(barcode);
//	}
//	
//	private Barcode randomBarcode()
//	{
//		Numeral[] 	code = new Numeral[7];
//		Random 		rand = new Random();
//		for (int i = 0; i < 7; i++)
//			code[i] = Numeral.valueOf((byte)rand.nextInt(10));
//		return new Barcode(code);
//	}
//	
//	private BigDecimal randomPrice(double max)
//	{
//		Random rand = new Random();
//		return new BigDecimal(((max-1.99) * rand.nextDouble()) + 1.99);
//	}
//	
//	private double randomWeightInGrams(double max)
//	{
//		Random rand = new Random();
//		return max * rand.nextDouble();
//	}
}
