package POS_v5.domainLayer.pricing;

import java.util.List;
import java.util.ArrayList;

import POS_v5.domainLayer.Money;
import POS_v5.domainLayer.Sale;

// ������ ���� ������ ����Ʈ�� �����ϴ� Ŭ����
public abstract class CompositePricingStrategy implements ISalePricingStrategy {

	// ���� ���� ����Ʈ
	// protected�� �ڽĵ��� �� �� �ֵ��� �Ѵ�. 
	protected List<ISalePricingStrategy> pricingStrategies = new ArrayList();
	// �� ���� ������ �߰��ϴ� �޼ҵ�
	//�� ���� ���� ���� ������ �߰��� �� ȣ���ϴ� �޼ҵ�	
	//�� ���� ���� ���� ���� Ŭ������ �� add( ) �޼ҵ带 ��ӹ޴´�.
	public void add(ISalePricingStrategy s) {
		pricingStrategies.add(s);
	}
	
	// ���� �� ������ ����ϴ� �޼ҵ�
	// �θ� �� �� �����Ƿ� �߻�޼ҵ�� ���ܵд�.
	@Override
	public abstract Money getTotal(Sale s);
	
}



