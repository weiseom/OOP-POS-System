package POS_v5.domainLayer;

public class Register {
	
	// ��� 3. �� �۾��� ��Ÿ���� ���, ���� ����
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
	// �Ǹ� ���� �޼ҵ� 
	public Sale makeNewSale(){
		currentSale = new Sale();
		currentState = MAKE_NEW_SALE;
		return currentSale;
	}
	// ������ �Է� �޼ҵ� 
	public void enterItem(ItemID id, int quantity){
		ProductDescription desc = catalog.getProductDescription(id);
		currentSale.makeLineItem(desc, quantity);
		currentSale.getTotal();
		currentState = ENTER_ITEM;
	}
	// �Ǹ� ���� �޼ҵ� 
	public void endSale(){
		currentSale.becomeComplete();
		currentState = END_SALE;
	}
	// ��� 5. ������ ����Ѵ�. 
	public void calculateTax(){
		currentSale.calculateTax();
		currentState = CALCULATE_TAX;
	}
	// ��� 6. ������ ���Ѵ�. 
	public void applyDiscount(){
		currentSale.applyDiscount();
		currentState = APPLY_DISCOUNT;
	}
	// ��� 6. ���� ������ ���Ѵ�. 
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
	
	// ������ State�� �����Ѵ�. 
	public int getCurrentState(){
		return currentState;
	}
	public void setCurrentState(int state){
		currentState = state;
	}
	// itemId���� �ε��Ѵ�.  
	public String[] loadProdID(){
		return catalog.loadProdId();
	}
}
