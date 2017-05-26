package POS_v5.domainLayer.tax;

import POS_v5.domainLayer.Money;
import POS_v5.domainLayer.Sale;

// 세금 계산 방법 2 
public class GoodAsGoldTaxPro {

	// 세금계산메소드
	Money calculateTaxes(Sale s) {
		// 총액의 20% tax가 계산된다. 
		double percentage = 20;
		Money preTaxTotal = s.getPreCalculate();
		Money totalWithTax = preTaxTotal.applyPercentage(100 + percentage);
		s.setTotal(totalWithTax, "tax"); 
		return totalWithTax;
	}
}