package POS_v5.domainLayer.tax;

import POS_v5.domainLayer.Money;
import POS_v5.domainLayer.Sale;

// ���� ���� ��� 1
public class TaxMaster {

	// ���ݰ��޼ҵ�
	// sale �޾Ƽ� saleLineItem �� �޾Ƽ� �о�´�.
	Money calcTax(Sale s) {
		// �Ѿ��� 10%�� ���ȴ�. 
		double percentage = 10;
		Money preTaxTotal = s.getPreCalculate();		
		Money totalWithTax = preTaxTotal.applyPercentage(100 + percentage);
		s.setTotal(totalWithTax, "tax"); 
		
		return totalWithTax;
	}
	
}
