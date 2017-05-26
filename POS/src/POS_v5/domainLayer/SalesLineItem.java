package POS_v5.domainLayer;

public class SalesLineItem {
	
	private int quantity;
	private ProductDescription descrption;
	
	public SalesLineItem(ProductDescription desc, int quantity){
		this.descrption = desc;
		this.quantity = quantity;
	}
	public Money getSubtotal(){
		// ������ ���� * ��
		return descrption.getPrice().times(quantity);
	}
}
