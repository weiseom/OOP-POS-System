package POS_v5.domainLayer;

// id를 갖고 있는 클래스
public class ItemID {

	// 속성 중에는  id가 있어야함
	private String id = null; 
	
	// 만약 id를 int로 받는다면, String으로 변환시켜주어야 한다.
	public ItemID(int id){
		this.id = String.valueOf(id);
	}
	
	public ItemID(String id){
		this.id = id;
	}
	
	public String toString(){
		return id;
	}
	
}
