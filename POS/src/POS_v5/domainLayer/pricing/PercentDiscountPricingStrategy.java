package POS_v5.domainLayer.pricing;

import POS_v5.domainLayer.Money;
import POS_v5.domainLayer.Sale;

// 할인전략 1 : 몇 퍼센트 할인해주는가 
public class PercentDiscountPricingStrategy implements ISalePricingStrategy {
	// 할인율
	private double percentage = 0;
	
	public PercentDiscountPricingStrategy(double d){
		this.percentage = d;
	}
	
	@Override
	public Money getTotal(Sale s) {
		Money preDiscountTotal = s.getPreCalculate();
		return preDiscountTotal.applyPercentage(100 - percentage);
	}
}
