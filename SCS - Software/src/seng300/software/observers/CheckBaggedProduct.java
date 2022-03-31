package seng300.software.observers;

import java.time.Duration;
import java.time.Instant;

import org.lsmr.selfcheckout.products.BarcodedProduct;

public class CheckBaggedProduct implements Runnable {
	
	
	private BaggingAreaObserver baggingAreaObserver;

	public CheckBaggedProduct(BarcodedProduct product, BaggingAreaObserver bao) {
		baggingAreaObserver = bao;
	}
	
	@Override
	public void run() {
		try {
			
			Instant start = Instant.now();
			Instant end = Instant.now();
			Duration elapsedTime = Duration.between(start, end);
			while(!baggingAreaObserver.isCurrentItemBagged() && elapsedTime.getSeconds() < 5) {
				end = Instant.now();
				elapsedTime = Duration.between(start, end);
			}
			
			if(baggingAreaObserver.isCurrentItemBagged() == false) {
				baggingAreaObserver.setTimedOut(true);
				baggingAreaObserver.blockScs();
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
			
		
	}

	
	
	
	

}
