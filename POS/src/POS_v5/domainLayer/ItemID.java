package POS_v5.domainLayer;

// id�� ���� �ִ� Ŭ����
public class ItemID {

	// �Ӽ� �߿���  id�� �־����
	private String id = null; 
	
	// ���� id�� int�� �޴´ٸ�, String���� ��ȯ�����־�� �Ѵ�.
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
