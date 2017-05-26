package POS_v5.domainLayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ProductCatalog {

	private Map<String, ProductDescription> descriptions = new HashMap<String, ProductDescription>();

	//DB load�� �ʿ��� ������. 
	private Connection myConnection;
	private Statement myStatement;
	private ResultSet myResultSet;

	public ProductDescription getProductDescription(ItemID id){
		return descriptions.get(id.toString()); // (key)
	}

	public ProductCatalog(String databaseDriver, String databaseURL){
		try {
			// load current DB driver
			Class.forName(databaseDriver);

			// connect to database
			// �����ͺ��̽� ����̹��� �����ͺ��̽� �̸�(��ġ) : sun.jdbc.odbc.JdbcOdbcDriver
			// databaseURL �� : "jdbc:odbc:ATM"
			myConnection = DriverManager.getConnection(databaseURL);
			myStatement = myConnection.createStatement();

			loadProdSpecs();
			
		} catch (SQLException exception) {
			exception.printStackTrace();

		} catch (ClassNotFoundException exception) {
			exception.printStackTrace();
		}

	}

	// DB�κ��� ���ǿ� �ش��ϴ� �����͸� �ε��Ͽ� decriptions�� �߰��ϴ� �޼ҵ�  
	public void loadProdSpecs( ) {

		try {
			myResultSet = myStatement.executeQuery(
					"SELECT itemId, description, price FROM ProductDescriptions");	
			
			while(myResultSet.next()){
				ItemID id = new ItemID(myResultSet.getString("itemId"));
				Money price = new Money((int)myResultSet.getDouble("price"));
				String description = myResultSet.getString("description");
				ProductDescription desc = new ProductDescription(id, price, description);
				
				descriptions.put(id.toString(), desc);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// description�� id�� �迭�� �������ִ� �޼ҵ� 
	public String[] loadProdId(){
		String[] itemID = new String[descriptions.keySet().size()];
		int i=0;
		for(Iterator iterator = descriptions.keySet().iterator(); iterator.hasNext();i++){
			itemID[i]=(String) iterator.next();
		}
		return itemID;
	}


}
