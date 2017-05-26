package POS_v5.domainLayer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import POS_v5.domainLayer.tax.ITaxCalculatorAdapter;
import POS_v5.domainLayer.tax.factory.PricingStrategyFactory;
import POS_v5.domainLayer.tax.factory.ServicesFactory;
import POS_v5.presentationLayer.PropertyListener;

public class Sale {

	public List<PropertyListener> propertyListeners = new ArrayList();

	// saleslineItem �� ������ ������ �ִ� �÷���
	private List<SalesLineItem> lineItems = new ArrayList<SalesLineItem>();

	// ���α׷� ���۽�, ������ �ð��� ��Ÿ���� date��ü
	private Date date = new Date();
	private boolean isComplete = false;
	private Payment payment;
	private Money total;
	private ITaxCalculatorAdapter taxCalculatorAdapter;
	private PricingStrategyFactory factory;
	
	public Sale(){
		factory = PricingStrategyFactory.getInstance();
	}
	
	// �ܾ��� ���ϴ� �޼ҵ�
	public Money getBalance(){
		return payment.getAmount().minus(total);
	}

	public void becomeComplete(){
		isComplete = true;
	}
	public boolean isComplete(){
		return isComplete;
	}

	public void makeLineItem(ProductDescription desc, int quantity){
		lineItems.add(new SalesLineItem(desc, quantity));
	}

	// �Ѿ��� ����ϴ� �޼ҵ� 
	public void getTotal(){
		Money total = new Money();
		Money subtotal = null;

		for(SalesLineItem lineItem : lineItems){
			subtotal = lineItem.getSubtotal();
			total.add(subtotal);
		}
		setTotal(total, "currentTotal");
	}

	public Money getPreCalculate(){
		return total;
	}

	// ��� 5. Factory ���� 
	public void calculateTax(){
		// Register�� �������� ���̱� ���ؼ� Factory ������ �̿��Ͽ� TaxAdapter�� ����Ѵ�.
		// TaxAdapter�� ���� ���ϴ� Tax������ �����Ͽ� ���� ���� ���� ������ UI������ �����Ѵ�.
		taxCalculatorAdapter = ServicesFactory.getInstance().getTaxCalculatorAdapter();
		taxCalculatorAdapter.getTotal(this);
	}
	
	// ��� 6. Composite & Factory ����
	public void applyDiscount(){
		// �������� ������ ���´�. 
		factory.getSalePricingStrategy().getTotal(this);
	}
	
	// ��� 6. ���� ������ ���Ѵ�. 
	public void setPricingStrategyType(int strategyType) {
		factory.setPricingStrategyType(strategyType);
	}
	
	public void makePayment(Money cashTendered){
		payment = new Payment(cashTendered);
	}
	
	// ������ ���� : ��ϸ޼ҵ� 
	public void addPropertyListener(PropertyListener lis){
		propertyListeners.add(lis);
	}

	// ������ ���� : �����޼ҵ� 
	public void publishPropertyEvent(String name, String value){
		for(PropertyListener pl : propertyListeners){
			pl.onPropertyEvent(this, name, value);
		}
	}

	// �ٲ�� total���� �缳���Ѵ�. �Ӽ����� �ٲ�� �����޼ҵ带 ȣ���Ͽ� GUI�� �����Ѵ�. 
	public void setTotal(Money newTotal, String name){
		this.total = newTotal;
		if(name.equals("currentTotal")){
			publishPropertyEvent("sale." + name, total.toString());
		}else if(name.equals("tax")){
			publishPropertyEvent("sale." + name, total.toString());
		}else if(name.equals("discount")){
			publishPropertyEvent("sale." + name, total.toString());
		}
	}
}
