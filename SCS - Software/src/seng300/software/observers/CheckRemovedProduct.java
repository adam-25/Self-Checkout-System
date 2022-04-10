package seng300.software.observers;

import java.time.Duration;
import java.time.Instant;

import org.lsmr.selfcheckout.products.BarcodedProduct;
import org.lsmr.selfcheckout.products.Product;

public class CheckRemovedProduct implements Runnable {
	
	
	private BaggingAreaObserver baggingAreaObserver;

	public CheckRemovedProduct(BaggingAreaObserver bao) {
		baggingAreaObserver = bao;
	}
	
	@Override
	public void run() {
		try {
			
			Instant start = Instant.now();
			Instant end = Instant.now();
			Duration elapsedTime = Duration.between(start, end);
			while(!baggingAreaObserver.isCurrentItemRemoved() && elapsedTime.getSeconds() < 5) {
				end = Instant.now();
				elapsedTime = Duration.between(start, end);
			}
			
			if(baggingAreaObserver.isCurrentItemRemoved() == false) {
				baggingAreaObserver.setTimedOut(true);
				baggingAreaObserver.blockScs();
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
			
		
	}

	
	
	
	

}
