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

	//DB load에 필요한 변수들. 
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
			// 데이터베이스 드라이버와 데이터베이스 이름(위치) : sun.jdbc.odbc.JdbcOdbcDriver
			// databaseURL 값 : "jdbc:odbc:ATM"
			myConnection = DriverManager.getConnection(databaseURL);
			myStatement = myConnection.createStatement();

			loadProdSpecs();
			
		} catch (SQLException exception) {
			exception.printStackTrace();

		} catch (ClassNotFoundException exception) {
			exception.printStackTrace();
		}

	}

	// DB로부터 조건에 해당하는 데이터를 로드하여 decriptions에 추가하는 메소드  
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

	// description의 id를 배열로 리턴해주는 메소드 
	public String[] loadProdId(){
		String[] itemID = new String[descriptions.keySet().size()];
		int i=0;
		for(Iterator iterator = descriptions.keySet().iterator(); iterator.hasNext();i++){
			itemID[i]=(String) iterator.next();
		}
		return itemID;
	}


}
