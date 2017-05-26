package POS_v5.domainLayer.tax;

import POS_v5.domainLayer.Money;
import POS_v5.domainLayer.Sale;

// ���� ��� ��� 2 
public class GoodAsGoldTaxPro {

	// ���ݰ��޼ҵ�
	Money calculateTaxes(Sale s) {
		// �Ѿ��� 20% tax�� ���ȴ�. 
		double percentage = 20;
		Money preTaxTotal = s.getPreCalculate();
		Money totalWithTax = preTaxTotal.applyPercentage(100 + percentage);
		s.setTotal(totalWithTax, "tax"); 
		return totalWithTax;
	}
}