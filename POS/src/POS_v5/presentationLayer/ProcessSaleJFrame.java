package POS_v5.presentationLayer;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import POS_v5.domainLayer.*;

public class ProcessSaleJFrame extends JFrame implements ActionListener, PropertyListener, ItemListener {

	private Register register;
	private Sale sale;

	GridBagLayout grid = new GridBagLayout();
	GridBagConstraints con = new GridBagConstraints(); 
	
	// 기능 6. PricingStrategy 타입을 위한 상수 선언
	static final int BestForCustomer = 1;
	static final int BestForStore = 2;
	
	// GUI 컴포넌트 선언
	private JTextArea jTextArea_log = new JTextArea("======== POS Sytem ======== \n", 20, 20);

	// 1. for makeNewSale()
	private JButton jButton_makeNewSale = new JButton("1. Make New Sale");

	// 2. for enterItem()
	private JLabel jLabel_itemId = new JLabel("Item Id : ");
	private JComboBox<String> jComboBox_itemID = new JComboBox<String>();
	private JLabel jLabel_quantity = new JLabel("Quantity: ");
	private JTextField jTextField_quantity = new JTextField();
	private JLabel jLabel_description = new JLabel("Description: ");
	private JTextField jTextField_description = new JTextField();
	private JButton jButton_enterItem = new JButton("2. EnterItem (반복) ");

	// 3. for endSale()
	private JButton jButton_endSale = new JButton("3. EndSale()");

	// 현재 합계를 보여줄 컴포넌트
	private JLabel jLabel_CurrentTotal = new JLabel("Current Total : ");
	private JTextField jTextField_CurrentTotal = new JTextField();

	// 4. for calculateTax()
	private JButton jButton_calculateTax = new JButton("4. calculateTax()");
	private JLabel jLabel_calculateTax = new JLabel("Total with Tax : ");
	private JTextField jTextField_calculateTax = new JTextField();
	private JRadioButton jRadioButton_taxMaster = new JRadioButton("TaxMaster");
	private JRadioButton jRadioButton_goodAsGoldTaxPro = new JRadioButton("GoodAsGoldTaxPro");
	
	// 5. for applyDiscount()
	private JButton jButton_applyDiscount = new JButton("5. applyDiscount()");
	private JLabel jLabel_applyDiscount = new JLabel("Total after Discount : ");
	private JTextField jTextField_applyDiscount = new JTextField();
	private JRadioButton jRadioButton_bestForCustomer = new JRadioButton("BestForCustomer");
	private JRadioButton jRadioButton_bestForStore = new JRadioButton("BestForStore");

	// 고객의 금액 
	private JLabel jLabel_cash = new JLabel("Amount : ");
	private JTextField jTextField_cash = new JTextField();

	// 6. for makePayment()
	private JButton jbutton_makePayment = new JButton("6. MakePayment()");
	private JLabel jLabel_balance = new JLabel("Balance : ");
	private JTextField jTextField_balance = new JTextField();

	// 생성자
	public ProcessSaleJFrame(Register register){

		super("POS System (학번 : 20141123  이름 : 서민서)");
		this.register = register;
		
		// 기능 3. 버튼 초기화
		checkCurrentState();
		
		GridBagLayoutInit();
		pack(); // GUI 컴포넌트 정리
		setSize(700,700);
		setVisible(true); //GUI 구성하고 보여주기
	}

	private void GridBagLayoutInit(){

		setLayout(grid);

		con.fill = GridBagConstraints.BOTH;
		con.insets = new Insets(2,2,2,2);

		// 0행 1열, 2열 
		addCom(jTextArea_log, 2, 0, 1, 17);

		// 1행 12열 
		addCom(jButton_makeNewSale, 0, 1, 2, 1);
		jButton_makeNewSale.setBackground(Color.darkGray);

		// 2행 1열, 2열 
		addCom(jLabel_itemId, 0, 2, 1, 1);
		addCom(jComboBox_itemID, 1, 2, 1, 1);

		// 3행 1열, 2열  
		addCom(jLabel_quantity, 0, 3, 1, 1);
		addCom(jTextField_quantity, 1, 3, 1, 1);

		// 4행 1, 2열 
		addCom(jLabel_description, 0,4,1,1);
		addCom(jTextField_description, 1,4,1,1);
		
		// 5행 12열
		addCom(jButton_enterItem, 0,5,2,1);
		jButton_enterItem.setBackground(Color.darkGray);

		// 6행 1열, 2열 
		addCom(jLabel_CurrentTotal, 0,6,1,1);
		addCom(jTextField_CurrentTotal, 1,6,1,1);
		
		// 7행 12
		addCom(jButton_endSale, 0,7,2,1);
		jButton_endSale.setBackground(Color.darkGray);
		
		// 8행 1열, 2열
		ButtonGroup tax = new ButtonGroup();
		tax.add(jRadioButton_taxMaster);
		tax.add(jRadioButton_goodAsGoldTaxPro);
		addCom(jRadioButton_taxMaster, 0,8,1,1);
		addCom(jRadioButton_goodAsGoldTaxPro, 1,8,1,1);

		// 9행 12열 
		addCom(jButton_calculateTax, 0,9,2,1);
		jButton_calculateTax.setBackground(Color.darkGray);

		// 10행 1열, 2열 
		addCom(jLabel_calculateTax, 0,10,1,1);
		addCom(jTextField_calculateTax, 1,10,1,1);
		
		// 11행 1열, 2열 
		ButtonGroup strategy = new ButtonGroup();
		strategy.add(jRadioButton_bestForCustomer);
		strategy.add(jRadioButton_bestForStore);
		addCom(jRadioButton_bestForCustomer, 0,11,1,1);
		addCom(jRadioButton_bestForStore, 1,11,1,1);
		
		// 12행 12열 
		addCom(jButton_applyDiscount, 0,12,2,1);
		jButton_applyDiscount.setBackground(Color.darkGray);
		
		// 13행 1열, 2열  
		addCom(jLabel_applyDiscount, 0,13,1,1);
		addCom(jTextField_applyDiscount, 1,13,1,1);
		
		// 14행 1열, 2열 
		addCom(jLabel_cash, 0,14,1,1);
		addCom(jTextField_cash, 1,14,1,1);

		// 15행 12열 
		addCom(jbutton_makePayment, 0,15,2,1);
		jbutton_makePayment.setBackground(Color.darkGray);

		// 16행 1열, 2열 
		addCom(jLabel_balance, 0,16,1,1);
		addCom(jTextField_balance, 1,16,1,1);
		
		// Dimension 을 이용하여 크기를 적절하게 지정한다. 
		// GUI 컴포넌트 속성 설정
		jTextField_quantity.setPreferredSize(new Dimension(60,20));
		jTextField_CurrentTotal.setPreferredSize(new Dimension(140,20));
		jTextField_cash.setPreferredSize(new Dimension(140,20));
		jTextField_balance.setPreferredSize(new Dimension(140,20));

		// 리스너 등록
		jButton_makeNewSale.addActionListener(this);
		jButton_enterItem.addActionListener(this);
		jButton_endSale.addActionListener(this);
		jButton_calculateTax.addActionListener(this);
		jButton_applyDiscount.addActionListener(this);
		jbutton_makePayment.addActionListener(this);
		jRadioButton_bestForCustomer.addItemListener(this);
		jRadioButton_bestForStore.addItemListener(this);
	}

	private void addCom(Component c, int x, int y, int w, int h){
		con.gridx = x;
		con.gridy = y;
		con.gridwidth = w;
		con.gridheight = h;
		grid.setConstraints(c, con);
		add(c);
	}

	
	// 라디오 버튼 속성 변화 체크 
	@Override
	public void itemStateChanged(ItemEvent e) {
		if(e.getSource() == jRadioButton_bestForCustomer){
			register.setPricingStrategyType(BestForCustomer);
		}
		else if(e.getSource() == jRadioButton_bestForStore){
			register.setPricingStrategyType(BestForStore);
		}

	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// e.getSource : 어떤 객체에서, 이벤트가 발생했는지 알 수 있음
		
		// 1. for makeNewSale()
		// 판매 개시 
		if(e.getSource() == jButton_makeNewSale) {
			// 컨트롤러에게 메시지 전달
			sale = register.makeNewSale();
			// 기능 6-4
			// DB로부터 로드한 ID값을 전달 받아 ComboBox에 추가한다. 
			String[] id = register.loadProdID();
			int i=0;
			for(i = 0; i < id.length; i++){
				jComboBox_itemID.addItem(id[i]);	
			}
			// 기능 7. 옵저버패턴  
			sale.addPropertyListener(this);
			currentLog("새 판매가 시작되었습니다.");
			checkCurrentState();
		}

		// 2. for enterItem()
		// 사용자가 선택한 item과, quantity를 얻어와 item객체를 생성한다.
		else if(e.getSource() == jButton_enterItem){
			final JPanel panel = new JPanel();
			
		// 기능 1. Quantity 입력 란 오류 처리
			// 1) 수량을 입력하지 않고 enterItem버튼을 눌렀을 경우,
			if(jTextField_quantity.getText().isEmpty()){
				JOptionPane.showMessageDialog(panel, "수량을 입력해주세요.", "Empty Warning", JOptionPane.WARNING_MESSAGE);}
			
			// 2) 수량이 정수로 알맞게 입력되었으면,  
			else if(isNumber(jTextField_quantity.getText()) == true){
				ItemID itemID = new ItemID(Integer.parseInt((String) jComboBox_itemID.getSelectedItem()));
				int quantity = Integer.parseInt(jTextField_quantity.getText());
				// 사용자가 선택한 item과, quantity를 얻어와 item객체를 생성한다.
				register.enterItem(itemID, quantity);
				String desc = register.getDescription(itemID);
				jTextField_description.setText(desc);
				
				currentLog("Itemid : " + jComboBox_itemID.getSelectedItem() + "을(를) " 
						+ jTextField_quantity.getText() + "개 선택하셨습니다. ");
				currentLog("Description : " + desc);
				
			// 3) 수량이 정수가 아닐 경우, 경고창을 띄운다.	
			}else{
				JOptionPane.showMessageDialog(panel, "숫자만 입력해주세요.", "Type Warning", JOptionPane.WARNING_MESSAGE);
			}
			jTextField_quantity.setText("");			
			checkCurrentState();
		}
		
		// 3. for endSale()
		// 판매 종료 
		else if(e.getSource() == jButton_endSale){
			register.endSale();
			currentLog("판매를 종료하였습니다.");
			checkCurrentState();
		}
		
		// 4. for calculateTax()
		// 기능 5. 세금계산 기능 지
		else if(e.getSource() == jButton_calculateTax){
			if(jRadioButton_taxMaster.isSelected()){
				System.setProperty("taxcalculator.class.name", "POS_v5.domainLayer.tax.TaxMasterAdapter");
				register.calculateTax();
				currentLog("taxMaster가 선택되었습니다.");	
				
			}else if(jRadioButton_goodAsGoldTaxPro.isSelected()){
				System.setProperty("taxcalculator.class.name", "POS_v5.domainLayer.tax.GoodAsGoldTaxProAdapter");		
				register.calculateTax();
				currentLog("goodAsGoldTaxPro가 선택되었습니다.");	
			}
			checkCurrentState();
		}
		
		// 5. for applyDiscount()
		// 기능 6. 다양한 가격 결정 전략 지원  
		else if(e.getSource() == jButton_applyDiscount){
			if(jRadioButton_bestForCustomer.isSelected()){
				register.applyDiscount();
				currentLog("최저가 할인 전략이 선택되었습니다.");
			}else if(jRadioButton_bestForStore.isSelected()){
				register.applyDiscount();
				currentLog("최고가 할인 전략이 선택되었습니다.");	
			}
			checkCurrentState();			
		}
		
		// 6. for makePayment()
		else if(e.getSource() == jbutton_makePayment){
			// 고객이 낸 돈을 입력받아 컨트롤러에 전달한다. 
			register.makePayment(new Money(Integer.parseInt(jTextField_cash.getText())));
			currentLog("고객이 " + jTextField_cash.getText() + "won 을 지불하였습니다. ");
			
			// 잔액을 출력한다. 
			jTextField_balance.setText(String.valueOf(register.getBalance()));
			currentLog("잔액은 " + register.getBalance() + " 입니다. ");
			checkCurrentState();
		}
	}
	
	// 기능 2 : 제품 설명 및 상황 관련 메시지 출력
	public void currentLog(String str){
		jTextArea_log.append(str + "\n");
	}
	
	// 기능 3 : UI Disable with CurrentState
	public void checkCurrentState(){
		if(register.getCurrentState() == Register.BEFORE_START){
			
			jButton_enterItem.setEnabled(false);
			jButton_endSale.setEnabled(false);
			jButton_calculateTax.setEnabled(false);
			jButton_applyDiscount.setEnabled(false);
			jbutton_makePayment.setEnabled(false);
			
			jComboBox_itemID.setEnabled(false);
			jTextField_quantity.setEnabled(false);
			jTextField_description.setEnabled(false);
			jButton_enterItem.setEnabled(false);
			jTextField_CurrentTotal.setEnabled(false);
			jTextField_applyDiscount.setEnabled(false);
			jTextField_cash.setEnabled(false);
			jTextField_balance.setEnabled(false);
			
			jRadioButton_taxMaster.setEnabled(false);
			jRadioButton_goodAsGoldTaxPro.setEnabled(false);
			jRadioButton_bestForCustomer.setEnabled(false);
			jRadioButton_bestForStore.setEnabled(false);
			
		}else if(register.getCurrentState() == Register.MAKE_NEW_SALE){
			
			jButton_enterItem.setEnabled(true);
			
			jComboBox_itemID.setEnabled(true);
			jTextField_quantity.setEnabled(true);
			
			jTextField_quantity.setText("");
			jTextField_description.setText("");
			jTextField_CurrentTotal.setText("");
			jTextField_applyDiscount.setText("");
			jTextField_cash.setText("");
			jTextField_balance.setText("");
			jTextArea_log.setText("======== POS Sytem ======== \n");
			
		}else if(register.getCurrentState() == Register.ENTER_ITEM){
	
			jButton_endSale.setEnabled(true);
			
		}else if(register.getCurrentState() == Register.END_SALE){
			
			jComboBox_itemID.setEnabled(false);
			jTextField_quantity.setEnabled(false);
		
			jButton_enterItem.setEnabled(false);
			jButton_endSale.setEnabled(false);
			jButton_calculateTax.setEnabled(true);
			
			jRadioButton_taxMaster.setEnabled(true);
			jRadioButton_goodAsGoldTaxPro.setEnabled(true);
			
			jRadioButton_taxMaster.setSelected(true);
			
		}else if(register.getCurrentState() == Register.CALCULATE_TAX){

			jTextField_calculateTax.setEnabled(false);
			
			jRadioButton_taxMaster.setEnabled(false);
			jRadioButton_goodAsGoldTaxPro.setEnabled(false);
			
			jButton_calculateTax.setEnabled(false);
			jButton_applyDiscount.setEnabled(true);
			
			jRadioButton_bestForCustomer.setEnabled(true);
			jRadioButton_bestForStore.setEnabled(true);

			jRadioButton_bestForCustomer.setSelected(true);
			
		}else if(register.getCurrentState() == Register.APPLY_DISCOUNT){

			jRadioButton_bestForCustomer.setEnabled(false);
			jRadioButton_bestForStore.setEnabled(false);
			
			jButton_applyDiscount.setEnabled(false);
			jbutton_makePayment.setEnabled(true);
			
			jTextField_cash.setEnabled(true);
			
		}else if(register.getCurrentState() == Register.MAKE_PAYMENT){
			jTextField_cash.setEnabled(false);
			jbutton_makePayment.setEnabled(false);	
		}
	}

	// 기능 7. 옵저버 패턴이용하여 관찰자에게 통지하는 메소드 
	@Override
	public void onPropertyEvent(Object source, String name, String value) {
		if(name.equals("sale.currentTotal")){
			jTextField_CurrentTotal.setText(value);
			currentLog("현재 총 금액은 " + value + " 입니다. ");
		}else if(name.equals("sale.tax")){
			jTextField_calculateTax.setText(value.toString());
			currentLog("현재 세금 포함 총 금액은 " + value.toString() + " 입니다. ");
		}else if(name.equals("sale.discount")){
			jTextField_applyDiscount.setText(value.toString());
			currentLog("현재 할인 포함 총 금액은 " + value.toString() + " 입니다. ");
		}
	}


	// 정수인지 판별하는 메소드
	public boolean isNumber(String str){
		boolean result = false;  
		try{
			Integer.parseInt(str) ;
			result = true ;
		}catch(Exception e){}
		return result ;
	}

}






