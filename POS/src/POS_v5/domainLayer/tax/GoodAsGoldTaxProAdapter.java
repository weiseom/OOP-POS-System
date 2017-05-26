package POS_v5.domainLayer.tax;

import POS_v5.domainLayer.Money;
import POS_v5.domainLayer.Sale;

// 2�� ���� ���⸦ ���� ����� 
public class GoodAsGoldTaxProAdapter implements ITaxCalculatorAdapter {

	GoodAsGoldTaxPro gagtp = new GoodAsGoldTaxPro();
	
	// �� ����͸��� �ش� �޼ҵ带 ȣ���ؼ� ����Ѵ�. 
	// ���� Ŭ�������� �������̽� ����.
	@Override
	public Money getTotal(Sale s) {
		return gagtp.calculateTaxes(s);
	}
}
