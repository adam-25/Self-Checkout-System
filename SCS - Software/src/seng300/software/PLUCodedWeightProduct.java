package seng300.software;

import java.math.BigDecimal;

import org.lsmr.selfcheckout.PriceLookupCode;
import org.lsmr.selfcheckout.products.PLUCodedProduct;

public class PLUCodedWeightProduct extends PLUCodedProduct {

	private double weight =0.0;
	
	public PLUCodedWeightProduct(PriceLookupCode pluCode, String description, BigDecimal price, double weight) {
		super(pluCode, description, price);
		this.weight = weight;
		// TODO Auto-generated constructor stub
	}
	
	public PLUCodedWeightProduct(PLUCodedProduct p, double weight) {
		super(p.getPLUCode(), p.getDescription(), p.getPrice());
		this.weight = weight;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

}
