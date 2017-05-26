package POS_v5.domainLayer;

public class Store {

	private ProductCatalog catalog;
	private Register register;
	
	public Register getRegister(){
		return register;
	}
	public Store(String databaseDriver, String databaseURL){
		catalog = new ProductCatalog(databaseDriver, databaseURL);
		register = new Register(catalog);
	}
}
