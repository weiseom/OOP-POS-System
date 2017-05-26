package POS_v5.domainLayer;

public class ProductDescription {

	private ItemID id;
	private Money price;
	private String decription;
	
	public ProductDescription(ItemID id, Money price, String decription){
		this.id = id;
		this.price = price;
		this.decription = decription;
	}

	public ItemID getId() {
		return id;
	}

	public Money getPrice() {
		return price;
	}

	public String getDecription() {
		return decription;
	}
	
	
}
