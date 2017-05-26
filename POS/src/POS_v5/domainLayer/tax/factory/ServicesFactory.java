package POS_v5.domainLayer.tax.factory;
import POS_v5.domainLayer.tax.ITaxCalculatorAdapter;


// 객체 생성 전문 클래스
// 도메인객체가 부담하는 것이 아닌, 객체 생성의 역할을 분리하였다. 
// Singleton pattern : 객체를 하나만 생성해야 하는 것.
public class ServicesFactory {

	// 3. 미리 자신의 객체를 만들어 놓음
	// (적극적 초기화)
	static private ServicesFactory instance = new ServicesFactory();
	
	// 1. 생성자를 private으로 해 줌
	private ServicesFactory() {
	}
	
	static public ServicesFactory getInstance() {
		return instance;
		//2.  클래스를 틀로 객체가 생성되므로, 클래스가 먼저 생성된다. 
		// 따라서 static메소드는 항상 static만 접근할 수 있다. 
	}
	
	// 부모타입으로 만들면 자식을 가리킬 수 있다. 
	// 세금 계산기 어댑터를 생성해서 반환하는 메소드
	public ITaxCalculatorAdapter getTaxCalculatorAdapter(){
		// 생성할 세금계산기 어댑터 클래스 이름을 다른 곳에서 지정했다면
		// 시스템 속성을 이용해서 세금계산기 어댑터를 생성 가능 . 
		String className = System.getProperty("taxcalculator.class.name");

		ITaxCalculatorAdapter taxAdapter = null;
		try {
			taxAdapter = (ITaxCalculatorAdapter)Class.forName(className).newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return taxAdapter;
	}
}
