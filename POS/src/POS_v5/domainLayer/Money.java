package POS_v5.domainLayer;

public class Money {
	
	// ���� �� 
	private int amount;
	// ���� ���� 
	private String unit = "won";
	// ������
	public Money(int amount){
		this.amount = amount;
	}
	// ���� ���� ������
	public Money(){
		// this.amount = 0;
		// => better
		// ������ �ȿ����� this�� �����ڸ� ��Ÿ����. 0�� �־��־����� amount 0 ���� ����.
		this(0);
	}
	
	// ������ �����ϴ� ���ø�  
	public String toString(){
		return amount + " " + unit;
	}
	
	public Money times(int number){
		return new Money(amount * number);
	}

	public void add(Money money){
		// ���ڷ� ���� money�� ��� amount�� ���δ�.
		this.amount += money.getAmount();	
	}
	
	public Money minus(Money money){
		// ���ڷ� ���� money�� ���̸� ����Ѵ�. 
		return new Money(this.amount - money.getAmount());
	}
	
	// �ۼ�Ʈ�� ����ϴ� �޼ҵ� 
	public Money applyPercentage(double percent){
		double money = this.amount * (percent/100);
		return new Money((int)money);
	}

	// ���� ���� �Ǻ��ϴ� �޼ҵ� 
	public Money min(Money money){
		if(this.getAmount() < money.getAmount()){
			return this;
		}else{
			return money;
		}
	}
	
	// ū ���� �Ǻ��ϴ� �޼ҵ� 
	public Money max(Money money){
		if(this.getAmount() > money.getAmount()){
			return this;
		}else{
			return money;
		}
	}
	
	private int getAmount(){
		return amount;
	}

}
