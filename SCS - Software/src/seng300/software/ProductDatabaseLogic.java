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

import java.util.Map;
import org.lsmr.selfcheckout.Barcode;
import org.lsmr.selfcheckout.PriceLookupCode;
import org.lsmr.selfcheckout.external.ProductDatabases;
import org.lsmr.selfcheckout.products.BarcodedProduct;
import org.lsmr.selfcheckout.products.PLUCodedProduct;


import seng300.software.exceptions.ProductNotFoundException;


public class ProductDatabaseLogic{
	
	public ProductDatabaseLogic() {}

	/**
	 * 
	 * @return
	 */
	public Map<Barcode, BarcodedProduct> getProducts()
	{
		return ProductDatabases.BARCODED_PRODUCT_DATABASE;
	}

	/**
	 * Finds and returns the BarcodedProduct with the specified barcode.
	 * 
	 * @param barcode
	 * 			The barcode for the desired product. 
	 * 
	 * @return Returns corresponding BarcodedProduct, if exits.
	 */
	public BarcodedProduct getProduct(Barcode barcode) throws ProductNotFoundException
	{
		if (!ProductDatabases.BARCODED_PRODUCT_DATABASE.containsKey(barcode))
			throw new ProductNotFoundException();
    return ProductDatabases.BARCODED_PRODUCT_DATABASE.get(barcode);
	}
	
	
	/**
	 * Finds and returns the PLUCodedProduct with the specified PLUCode.
	 * 
	 * @param PriceLookupCode
	 * 			The PriceLookupCode for the desired product. 
	 * 
	 * @return Returns corresponding PLUCodedProduct, if exits.
	 */
	public PLUCodedProduct getPLUCodedProduct(PriceLookupCode PLUCode) throws ProductNotFoundException
	{
		if (!ProductDatabases.PLU_PRODUCT_DATABASE.containsKey(PLUCode))
			throw new ProductNotFoundException();
		
		return ProductDatabases.PLU_PRODUCT_DATABASE.get(PLUCode);
	}
	
}
