package POS_v5.domainLayer.pricing;
import POS_v5.domainLayer.Money;
import POS_v5.domainLayer.Sale;
// ���� ������ ��ǥ�ϴ� �������̽�
public interface ISalePricingStrategy {
	public Money getTotal(Sale s);
}
