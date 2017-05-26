package POS_v5.domainLayer.pricing;

import java.util.List;
import java.util.ArrayList;

import POS_v5.domainLayer.Money;
import POS_v5.domainLayer.Sale;

// 각각의 할인 전략을 리스트로 관리하는 클래스
public abstract class CompositePricingStrategy implements ISalePricingStrategy {

	// 할인 전략 리스트
	// protected로 자식들이 쓸 수 있도록 한다. 
	protected List<ISalePricingStrategy> pricingStrategies = new ArrayList();
	// 각 할인 전략을 추가하는 메소드
	//각 단일 가격 결정 전략을 추가할 때 호출하는 메소드	
	//두 복합 가격 결정 전략 클래스는 이 add( ) 메소드를 상속받는다.
	public void add(ISalePricingStrategy s) {
		pricingStrategies.add(s);
	}
	
	// 할인 후 가격을 계산하는 메소드
	// 부모가 할 수 없으므로 추상메소드로 남겨둔다.
	@Override
	public abstract Money getTotal(Sale s);
	
}



