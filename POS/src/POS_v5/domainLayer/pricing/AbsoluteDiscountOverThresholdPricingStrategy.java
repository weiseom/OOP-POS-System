package POS_v5.domainLayer.pricing;

import POS_v5.domainLayer.Money;
import POS_v5.domainLayer.Sale;
// 할인전략 2 : 일정 금액 이상 구매 시, 할인.
public class AbsoluteDiscountOverThresholdPricingStrategy implements ISalePricingStrategy {
	// 기준 구매 금액 
	private Money threshold;
	// 할인
	private Money discount;
	
	public AbsoluteDiscountOverThresholdPricingStrategy(Money threshold, Money discount){
		this.threshold = threshold;
		this.discount = discount;	
	}
	
	@Override
	public Money getTotal(Sale s) {
		Money preDiscountTotal = s.getPreCalculate();
		if(preDiscountTotal.min(threshold) == preDiscountTotal){
			return preDiscountTotal;
		}else{
			return preDiscountTotal.minus(discount);
		}
	}

}
