package POS_v5.domainLayer.tax.factory;

import POS_v5.domainLayer.pricing.CompositeBestForCustomerPricingStrategy;
import POS_v5.domainLayer.pricing.CompositeBestForStorePricingStategy;
import POS_v5.domainLayer.pricing.ISalePricingStrategy;

// 가격할인전략 객체를 생성하는 클래스
public class PricingStrategyFactory {

	static final int BestForCustomer = 1;
	static final int BestForStore = 2;
	private int currentStategyType;
	
	// 미리 자신의 객체를 만들어 놓지 않음.
	// (소극적 초기화 방법)
	private static PricingStrategyFactory instance = null;

	// 생성자를 private으로 해 줌
	private PricingStrategyFactory() {
	}

	static public synchronized PricingStrategyFactory getInstance() {
		if(instance == null){
			// 객체 생성 
			instance = new PricingStrategyFactory();
		}
		return instance; 
	}
	
	public ISalePricingStrategy getSalePricingStrategy(){
		ISalePricingStrategy strategy = null;
		if(currentStategyType == BestForCustomer){
			strategy = (ISalePricingStrategy) new CompositeBestForCustomerPricingStrategy();
		}else if(currentStategyType == BestForStore){
			strategy = (ISalePricingStrategy) new CompositeBestForStorePricingStategy();
		}
		return strategy;
	}

	public void setPricingStrategyType(int strategyType) {
		this.currentStategyType = strategyType;
	}

}
