package POS_v5.domainLayer.pricing;

import POS_v5.domainLayer.Money;
import POS_v5.domainLayer.Sale;

// �������� 1 : �� �ۼ�Ʈ �������ִ°� 
public class PercentDiscountPricingStrategy implements ISalePricingStrategy {
	// ������
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
