package POS_v5.domainLayer.tax.factory;

import POS_v5.domainLayer.pricing.CompositeBestForCustomerPricingStrategy;
import POS_v5.domainLayer.pricing.CompositeBestForStorePricingStategy;
import POS_v5.domainLayer.pricing.ISalePricingStrategy;

// ������������ ��ü�� �����ϴ� Ŭ����
public class PricingStrategyFactory {

	static final int BestForCustomer = 1;
	static final int BestForStore = 2;
	private int currentStategyType;
	
	// �̸� �ڽ��� ��ü�� ����� ���� ����.
	// (�ұ��� �ʱ�ȭ ���)
	private static PricingStrategyFactory instance = null;

	// �����ڸ� private���� �� ��
	private PricingStrategyFactory() {
	}

	static public synchronized PricingStrategyFactory getInstance() {
		if(instance == null){
			// ��ü ���� 
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
