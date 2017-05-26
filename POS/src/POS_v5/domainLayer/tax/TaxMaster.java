package POS_v5.domainLayer.tax;

import POS_v5.domainLayer.Money;
import POS_v5.domainLayer.Sale;

// 세금 결정 방법 1
public class TaxMaster {

	// 세금계산메소드
	// sale 받아서 saleLineItem 들 받아서 읽어온다.
	Money calcTax(Sale s) {
		// 총액의 10%가 계산된다. 
		double percentage = 10;
		Money preTaxTotal = s.getPreCalculate();		
		Money totalWithTax = preTaxTotal.applyPercentage(100 + percentage);
		s.setTotal(totalWithTax, "tax"); 
		
		return totalWithTax;
	}
	
}
