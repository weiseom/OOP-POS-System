package POS_v5;

import POS_v5.domainLayer.Register;
import POS_v5.domainLayer.Store;
import POS_v5.domainLayer.pricing.CompositeBestForCustomerPricingStrategy;
import POS_v5.domainLayer.pricing.CompositeBestForStorePricingStategy;
import POS_v5.presentationLayer.ProcessSaleJFrame;

public class Main {

	public static void main(String[] args) {

		if (args.length == 2) // 인자 두개가 있는지 확인해야한다.
		{
			// get command-line arguments
			String databaseDriver = args[0];
			String databaseURL = args[1];
			
			Store store = new Store(databaseDriver, databaseURL);
			Register register = store.getRegister();
			
			new CompositeBestForStorePricingStategy();
			new CompositeBestForCustomerPricingStrategy();
		
			new ProcessSaleJFrame(register);
		} else // invalid command-line arguments
		{
			System.out.println("Usage: java ATM databaseDriver databaseURL");
		}
		
	}
}
































