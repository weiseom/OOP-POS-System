
package POS_v5.domainLayer.tax;

import POS_v5.domainLayer.Money;
import POS_v5.domainLayer.Sale;

// 1�� ���ݰ��⸦ ���� �����
public class TaxMasterAdapter implements ITaxCalculatorAdapter{

	TaxMaster tm = new TaxMaster();

	// �� ����͸��� �ش� �޼ҵ带 ȣ���ؼ� ����Ѵ�. 
	// ���� Ŭ�������� �������̽� ����.
	@Override
	public Money getTotal(Sale s) {		
		return tm.calcTax(s);
	}
	
}
