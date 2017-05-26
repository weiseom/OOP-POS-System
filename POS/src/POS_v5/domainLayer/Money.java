package POS_v5.domainLayer;

public class Money {
	
	// 돈을 양 
	private int amount;
	// 돈의 단위 
	private String unit = "won";
	// 생성자
	public Money(int amount){
		this.amount = amount;
	}
	// 인자 없는 생성자
	public Money(){
		// this.amount = 0;
		// => better
		// 생성자 안에서의 this는 생성자를 나타낸다. 0을 넣어주었으니 amount 0 으로 들어간다.
		this(0);
	}
	
	// 단위를 포함하는 템플릿  
	public String toString(){
		return amount + " " + unit;
	}
	
	public Money times(int number){
		return new Money(amount * number);
	}

	public void add(Money money){
		// 인자로 들어온 money가 계속 amount에 쌓인다.
		this.amount += money.getAmount();	
	}
	
	public Money minus(Money money){
		// 인자로 들어온 money의 차이를 계산한다. 
		return new Money(this.amount - money.getAmount());
	}
	
	// 퍼센트를 계산하는 메소드 
	public Money applyPercentage(double percent){
		double money = this.amount * (percent/100);
		return new Money((int)money);
	}

	// 작은 돈을 판별하는 메소드 
	public Money min(Money money){
		if(this.getAmount() < money.getAmount()){
			return this;
		}else{
			return money;
		}
	}
	
	// 큰 돈을 판별하는 메소드 
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
