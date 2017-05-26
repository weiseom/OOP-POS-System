package POS_v5.domainLayer.pricing;

import java.util.Iterator;

import POS_v5.domainLayer.Money;
import POS_v5.domainLayer.Sale;

// 최고가 할인 전략
// 가지고 있는 단일 가격 결정 전략들 중 최고 가격을 반환한다.
public class CompositeBestForStorePricingStategy extends CompositePricingStrategy{

	public CompositeBestForStorePricingStategy(){
		ISalePricingStrategy percentDiscount01 = (ISalePricingStrategy) new PercentDiscountPricingStrategy(10);
		ISalePricingStrategy percentDiscount02 = (ISalePricingStrategy) new PercentDiscountPricingStrategy(20);		
		ISalePricingStrategy absoluteDiscount01 = (ISalePricingStrategy) new AbsoluteDiscountOverThresholdPricingStrategy(new Money(20000),new Money(1000));
		ISalePricingStrategy absoluteDiscount02 = (ISalePricingStrategy) new AbsoluteDiscountOverThresholdPricingStrategy(new Money(15000),new Money(500));

		pricingStrategies.add(percentDiscount01);
		pricingStrategies.add(percentDiscount02);
		pricingStrategies.add(absoluteDiscount01);
		pricingStrategies.add(absoluteDiscount02);
	}
	@Override
	public Money getTotal(Sale s) {	

		Money highestTotal = new Money(Integer.MIN_VALUE);

		for( Iterator i = pricingStrategies.iterator(); i.hasNext();){
			ISalePricingStrategy strategy = (ISalePricingStrategy)i.next();
			Money total = strategy.getTotal(s);
			highestTotal = total.max(highestTotal);
		}
		s.setTotal(highestTotal, "discount");
		return highestTotal;
	}

}
