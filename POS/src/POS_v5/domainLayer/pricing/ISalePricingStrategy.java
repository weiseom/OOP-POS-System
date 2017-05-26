package POS_v5.domainLayer.pricing;
import POS_v5.domainLayer.Money;
import POS_v5.domainLayer.Sale;
// 할인 전략을 대표하는 인터페이스
public interface ISalePricingStrategy {
	public Money getTotal(Sale s);
}
