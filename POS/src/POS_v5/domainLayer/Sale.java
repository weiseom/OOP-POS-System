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

	// saleslineItem 을 여러개 가지고 있는 컬렉션
	private List<SalesLineItem> lineItems = new ArrayList<SalesLineItem>();

	// 프로그램 시작시, 현재의 시각을 나타내는 date객체
	private Date date = new Date();
	private boolean isComplete = false;
	private Payment payment;
	private Money total;
	private ITaxCalculatorAdapter taxCalculatorAdapter;
	private PricingStrategyFactory factory;
	
	public Sale(){
		factory = PricingStrategyFactory.getInstance();
	}
	
	// 잔액을 구하는 메소드
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

	// 총액을 계산하는 메소드 
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

	// 기능 5. Factory 패턴 
	public void calculateTax(){
		// Register의 응집도를 높이기 위해서 Factory 패턴을 이용하여 TaxAdapter를 사용한다.
		// TaxAdapter를 통해 원하는 Tax설정을 적용하여 최종 세금 포함 가격을 UI계층에 리턴한다.
		taxCalculatorAdapter = ServicesFactory.getInstance().getTaxCalculatorAdapter();
		taxCalculatorAdapter.getTotal(this);
	}
	
	// 기능 6. Composite & Factory 패턴
	public void applyDiscount(){
		// 가격할인 전략을 얻어온다. 
		factory.getSalePricingStrategy().getTotal(this);
	}
	
	// 기능 6. 할인 전략을 정한다. 
	public void setPricingStrategyType(int strategyType) {
		factory.setPricingStrategyType(strategyType);
	}
	
	public void makePayment(Money cashTendered){
		payment = new Payment(cashTendered);
	}
	
	// 옵저버 패턴 : 등록메소드 
	public void addPropertyListener(PropertyListener lis){
		propertyListeners.add(lis);
	}

	// 옵저버 패턴 : 통지메소드 
	public void publishPropertyEvent(String name, String value){
		for(PropertyListener pl : propertyListeners){
			pl.onPropertyEvent(this, name, value);
		}
	}

	// 바뀌는 total값을 재설정한다. 속성값이 바뀌면 통지메소드를 호출하여 GUI를 변경한다. 
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
