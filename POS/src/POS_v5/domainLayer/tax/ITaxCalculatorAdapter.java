package POS_v5.domainLayer.tax;


import POS_v5.domainLayer.Money;
import POS_v5.domainLayer.Sale;

// ���ݰ��⸦ ���� �ϰ��� �������̽�
public interface ITaxCalculatorAdapter {

	// 5V�� �ش��Ѵ�.
	Money getTotal(Sale s);

}
