package POS_v5.domainLayer;

public class SalesLineItem {
	
	private int quantity;
	private ProductDescription descrption;
	
	public SalesLineItem(ProductDescription desc, int quantity){
		this.descrption = desc;
		this.quantity = quantity;
	}
	public Money getSubtotal(){
		// 물건의 가격 * 양
		return descrption.getPrice().times(quantity);
	}
}
