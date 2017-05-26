package POS_v5.domainLayer.pricing;

import POS_v5.domainLayer.Money;
import POS_v5.domainLayer.Sale;
// �������� 2 : ���� �ݾ� �̻� ���� ��, ����.
public class AbsoluteDiscountOverThresholdPricingStrategy implements ISalePricingStrategy {
	// ���� ���� �ݾ� 
	private Money threshold;
	// ����
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
