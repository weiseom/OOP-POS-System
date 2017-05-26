
package POS_v5.domainLayer.tax;

import POS_v5.domainLayer.Money;
import POS_v5.domainLayer.Sale;

// 1번 세금계산기를 위한 어댑터
public class TaxMasterAdapter implements ITaxCalculatorAdapter{

	TaxMaster tm = new TaxMaster();

	// 각 어댑터마다 해당 메소드를 호출해서 사용한다. 
	// 하위 클래스에서 인터페이스 구현.
	@Override
	public Money getTotal(Sale s) {		
		return tm.calcTax(s);
	}
	
}
