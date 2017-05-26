package POS_v5.domainLayer.tax;


import POS_v5.domainLayer.Money;
import POS_v5.domainLayer.Sale;

// 세금계산기를 위한 일관된 인터페이스
public interface ITaxCalculatorAdapter {

	// 5V에 해당한다.
	Money getTotal(Sale s);

}
