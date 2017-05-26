package POS_v5.domainLayer.pricing;

import java.util.Iterator;
import POS_v5.domainLayer.Money;
import POS_v5.domainLayer.Sale;
//최저가 할인 전략
//가지고 있는 단일 가격 결정 전략들 중 최저 가격을 반환한다. 
public class CompositeBestForCustomerPricingStrategy extends CompositePricingStrategy {

	public CompositeBestForCustomerPricingStrategy(){
		
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
		// 할인 전략 리스트에 있는 각각의 할인전략을 이용하여
		// 할인 가격을 구한 후 최저값을 선택한다.  
		Money lowestTotal = new Money(Integer.MAX_VALUE);
		for( Iterator<ISalePricingStrategy> i = pricingStrategies.iterator(); i.hasNext();){
			ISalePricingStrategy strategy = (ISalePricingStrategy)i.next();
			Money total = strategy.getTotal(s);
			lowestTotal = total.min(lowestTotal);
		}
		s.setTotal(lowestTotal, "discount");
		return lowestTotal;
	}
	
}
