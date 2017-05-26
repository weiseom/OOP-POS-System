package POS_v5.domainLayer;

public class Register {
	
	// 기능 3. 각 작업을 나타내는 상수, 변수 선언
	public static final int BEFORE_START = 0;
	public static final int MAKE_NEW_SALE = 1;
	public static final int ENTER_ITEM = 2;
	public static final int END_SALE = 3;
	public static final int CALCULATE_TAX = 4;
	public static final int APPLY_DISCOUNT = 5;
	public static final int MAKE_PAYMENT = 6;	
	
	private ProductCatalog catalog;
	private Sale currentSale;
	private int currentState = BEFORE_START;
	
	public Register(ProductCatalog catalog){
		this.catalog = catalog;
	}
	// 판매 개시 메소드 
	public Sale makeNewSale(){
		currentSale = new Sale();
		currentState = MAKE_NEW_SALE;
		return currentSale;
	}
	// 아이템 입력 메소드 
	public void enterItem(ItemID id, int quantity){
		ProductDescription desc = catalog.getProductDescription(id);
		currentSale.makeLineItem(desc, quantity);
		currentSale.getTotal();
		currentState = ENTER_ITEM;
	}
	// 판매 종료 메소드 
	public void endSale(){
		currentSale.becomeComplete();
		currentState = END_SALE;
	}
	// 기능 5. 세금을 계산한다. 
	public void calculateTax(){
		currentSale.calculateTax();
		currentState = CALCULATE_TAX;
	}
	// 기능 6. 할인을 정한다. 
	public void applyDiscount(){
		currentSale.applyDiscount();
		currentState = APPLY_DISCOUNT;
	}
	// 기능 6. 할인 전략을 정한다. 
	public void setPricingStrategyType(int strategyType){
		currentSale.setPricingStrategyType(strategyType);
	}
	
	public void makePayment(Money cashTendered){
		currentSale.makePayment(cashTendered);
		currentState = MAKE_PAYMENT;
	}
	
	public Money getBalance() {
		return currentSale.getBalance();
	}
	public String getDescription(ItemID id){
		return catalog.getProductDescription(id).getDecription();
	}
	
	// 현재의 State를 조절한다. 
	public int getCurrentState(){
		return currentState;
	}
	public void setCurrentState(int state){
		currentState = state;
	}
	// itemId값을 로드한다.  
	public String[] loadProdID(){
		return catalog.loadProdId();
	}
}
