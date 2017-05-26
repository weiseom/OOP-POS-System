package POS_v5.domainLayer.tax.factory;
import POS_v5.domainLayer.tax.ITaxCalculatorAdapter;


// ��ü ���� ���� Ŭ����
// �����ΰ�ü�� �δ��ϴ� ���� �ƴ�, ��ü ������ ������ �и��Ͽ���. 
// Singleton pattern : ��ü�� �ϳ��� �����ؾ� �ϴ� ��.
public class ServicesFactory {

	// 3. �̸� �ڽ��� ��ü�� ����� ����
	// (������ �ʱ�ȭ)
	static private ServicesFactory instance = new ServicesFactory();
	
	// 1. �����ڸ� private���� �� ��
	private ServicesFactory() {
	}
	
	static public ServicesFactory getInstance() {
		return instance;
		//2.  Ŭ������ Ʋ�� ��ü�� �����ǹǷ�, Ŭ������ ���� �����ȴ�. 
		// ���� static�޼ҵ�� �׻� static�� ������ �� �ִ�. 
	}
	
	// �θ�Ÿ������ ����� �ڽ��� ����ų �� �ִ�. 
	// ���� ���� ����͸� �����ؼ� ��ȯ�ϴ� �޼ҵ�
	public ITaxCalculatorAdapter getTaxCalculatorAdapter(){
		// ������ ���ݰ��� ����� Ŭ���� �̸��� �ٸ� ������ �����ߴٸ�
		// �ý��� �Ӽ��� �̿��ؼ� ���ݰ��� ����͸� ���� ���� . 
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
