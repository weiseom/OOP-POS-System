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
	
	// ��� 6. PricingStrategy Ÿ���� ���� ��� ����
	static final int BestForCustomer = 1;
	static final int BestForStore = 2;
	
	// GUI ������Ʈ ����
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
	private JButton jButton_enterItem = new JButton("2. EnterItem (�ݺ�) ");

	// 3. for endSale()
	private JButton jButton_endSale = new JButton("3. EndSale()");

	// ���� �հ踦 ������ ������Ʈ
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

	// ���� �ݾ� 
	private JLabel jLabel_cash = new JLabel("Amount : ");
	private JTextField jTextField_cash = new JTextField();

	// 6. for makePayment()
	private JButton jbutton_makePayment = new JButton("6. MakePayment()");
	private JLabel jLabel_balance = new JLabel("Balance : ");
	private JTextField jTextField_balance = new JTextField();

	// ������
	public ProcessSaleJFrame(Register register){

		super("POS System (�й� : 20141123  �̸� : ���μ�)");
		this.register = register;
		
		// ��� 3. ��ư �ʱ�ȭ
		checkCurrentState();
		
		GridBagLayoutInit();
		pack(); // GUI ������Ʈ ����
		setSize(700,700);
		setVisible(true); //GUI �����ϰ� �����ֱ�
	}

	private void GridBagLayoutInit(){

		setLayout(grid);

		con.fill = GridBagConstraints.BOTH;
		con.insets = new Insets(2,2,2,2);

		// 0�� 1��, 2�� 
		addCom(jTextArea_log, 2, 0, 1, 17);

		// 1�� 12�� 
		addCom(jButton_makeNewSale, 0, 1, 2, 1);
		jButton_makeNewSale.setBackground(Color.darkGray);

		// 2�� 1��, 2�� 
		addCom(jLabel_itemId, 0, 2, 1, 1);
		addCom(jComboBox_itemID, 1, 2, 1, 1);

		// 3�� 1��, 2��  
		addCom(jLabel_quantity, 0, 3, 1, 1);
		addCom(jTextField_quantity, 1, 3, 1, 1);

		// 4�� 1, 2�� 
		addCom(jLabel_description, 0,4,1,1);
		addCom(jTextField_description, 1,4,1,1);
		
		// 5�� 12��
		addCom(jButton_enterItem, 0,5,2,1);
		jButton_enterItem.setBackground(Color.darkGray);

		// 6�� 1��, 2�� 
		addCom(jLabel_CurrentTotal, 0,6,1,1);
		addCom(jTextField_CurrentTotal, 1,6,1,1);
		
		// 7�� 12
		addCom(jButton_endSale, 0,7,2,1);
		jButton_endSale.setBackground(Color.darkGray);
		
		// 8�� 1��, 2��
		ButtonGroup tax = new ButtonGroup();
		tax.add(jRadioButton_taxMaster);
		tax.add(jRadioButton_goodAsGoldTaxPro);
		addCom(jRadioButton_taxMaster, 0,8,1,1);
		addCom(jRadioButton_goodAsGoldTaxPro, 1,8,1,1);

		// 9�� 12�� 
		addCom(jButton_calculateTax, 0,9,2,1);
		jButton_calculateTax.setBackground(Color.darkGray);

		// 10�� 1��, 2�� 
		addCom(jLabel_calculateTax, 0,10,1,1);
		addCom(jTextField_calculateTax, 1,10,1,1);
		
		// 11�� 1��, 2�� 
		ButtonGroup strategy = new ButtonGroup();
		strategy.add(jRadioButton_bestForCustomer);
		strategy.add(jRadioButton_bestForStore);
		addCom(jRadioButton_bestForCustomer, 0,11,1,1);
		addCom(jRadioButton_bestForStore, 1,11,1,1);
		
		// 12�� 12�� 
		addCom(jButton_applyDiscount, 0,12,2,1);
		jButton_applyDiscount.setBackground(Color.darkGray);
		
		// 13�� 1��, 2��  
		addCom(jLabel_applyDiscount, 0,13,1,1);
		addCom(jTextField_applyDiscount, 1,13,1,1);
		
		// 14�� 1��, 2�� 
		addCom(jLabel_cash, 0,14,1,1);
		addCom(jTextField_cash, 1,14,1,1);

		// 15�� 12�� 
		addCom(jbutton_makePayment, 0,15,2,1);
		jbutton_makePayment.setBackground(Color.darkGray);

		// 16�� 1��, 2�� 
		addCom(jLabel_balance, 0,16,1,1);
		addCom(jTextField_balance, 1,16,1,1);
		
		// Dimension �� �̿��Ͽ� ũ�⸦ �����ϰ� �����Ѵ�. 
		// GUI ������Ʈ �Ӽ� ����
		jTextField_quantity.setPreferredSize(new Dimension(60,20));
		jTextField_CurrentTotal.setPreferredSize(new Dimension(140,20));
		jTextField_cash.setPreferredSize(new Dimension(140,20));
		jTextField_balance.setPreferredSize(new Dimension(140,20));

		// ������ ���
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

	
	// ���� ��ư �Ӽ� ��ȭ üũ 
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
		// e.getSource : � ��ü����, �̺�Ʈ�� �߻��ߴ��� �� �� ����
		
		// 1. for makeNewSale()
		// �Ǹ� ���� 
		if(e.getSource() == jButton_makeNewSale) {
			// ��Ʈ�ѷ����� �޽��� ����
			sale = register.makeNewSale();
			// ��� 6-4
			// DB�κ��� �ε��� ID���� ���� �޾� ComboBox�� �߰��Ѵ�. 
			String[] id = register.loadProdID();
			int i=0;
			for(i = 0; i < id.length; i++){
				jComboBox_itemID.addItem(id[i]);	
			}
			// ��� 7. ����������  
			sale.addPropertyListener(this);
			currentLog("�� �ǸŰ� ���۵Ǿ����ϴ�.");
			checkCurrentState();
		}

		// 2. for enterItem()
		// ����ڰ� ������ item��, quantity�� ���� item��ü�� �����Ѵ�.
		else if(e.getSource() == jButton_enterItem){
			final JPanel panel = new JPanel();
			
		// ��� 1. Quantity �Է� �� ���� ó��
			// 1) ������ �Է����� �ʰ� enterItem��ư�� ������ ���,
			if(jTextField_quantity.getText().isEmpty()){
				JOptionPane.showMessageDialog(panel, "������ �Է����ּ���.", "Empty Warning", JOptionPane.WARNING_MESSAGE);}
			
			// 2) ������ ������ �˸°� �ԷµǾ�����,  
			else if(isNumber(jTextField_quantity.getText()) == true){
				ItemID itemID = new ItemID(Integer.parseInt((String) jComboBox_itemID.getSelectedItem()));
				int quantity = Integer.parseInt(jTextField_quantity.getText());
				// ����ڰ� ������ item��, quantity�� ���� item��ü�� �����Ѵ�.
				register.enterItem(itemID, quantity);
				String desc = register.getDescription(itemID);
				jTextField_description.setText(desc);
				
				currentLog("Itemid : " + jComboBox_itemID.getSelectedItem() + "��(��) " 
						+ jTextField_quantity.getText() + "�� �����ϼ̽��ϴ�. ");
				currentLog("Description : " + desc);
				
			// 3) ������ ������ �ƴ� ���, ���â�� ����.	
			}else{
				JOptionPane.showMessageDialog(panel, "���ڸ� �Է����ּ���.", "Type Warning", JOptionPane.WARNING_MESSAGE);
			}
			jTextField_quantity.setText("");			
			checkCurrentState();
		}
		
		// 3. for endSale()
		// �Ǹ� ���� 
		else if(e.getSource() == jButton_endSale){
			register.endSale();
			currentLog("�ǸŸ� �����Ͽ����ϴ�.");
			checkCurrentState();
		}
		
		// 4. for calculateTax()
		// ��� 5. ���ݰ�� ��� ��
		else if(e.getSource() == jButton_calculateTax){
			if(jRadioButton_taxMaster.isSelected()){
				System.setProperty("taxcalculator.class.name", "POS_v5.domainLayer.tax.TaxMasterAdapter");
				register.calculateTax();
				currentLog("taxMaster�� ���õǾ����ϴ�.");	
				
			}else if(jRadioButton_goodAsGoldTaxPro.isSelected()){
				System.setProperty("taxcalculator.class.name", "POS_v5.domainLayer.tax.GoodAsGoldTaxProAdapter");		
				register.calculateTax();
				currentLog("goodAsGoldTaxPro�� ���õǾ����ϴ�.");	
			}
			checkCurrentState();
		}
		
		// 5. for applyDiscount()
		// ��� 6. �پ��� ���� ���� ���� ����  
		else if(e.getSource() == jButton_applyDiscount){
			if(jRadioButton_bestForCustomer.isSelected()){
				register.applyDiscount();
				currentLog("������ ���� ������ ���õǾ����ϴ�.");
			}else if(jRadioButton_bestForStore.isSelected()){
				register.applyDiscount();
				currentLog("�ְ� ���� ������ ���õǾ����ϴ�.");	
			}
			checkCurrentState();			
		}
		
		// 6. for makePayment()
		else if(e.getSource() == jbutton_makePayment){
			// ���� �� ���� �Է¹޾� ��Ʈ�ѷ��� �����Ѵ�. 
			register.makePayment(new Money(Integer.parseInt(jTextField_cash.getText())));
			currentLog("���� " + jTextField_cash.getText() + "won �� �����Ͽ����ϴ�. ");
			
			// �ܾ��� ����Ѵ�. 
			jTextField_balance.setText(String.valueOf(register.getBalance()));
			currentLog("�ܾ��� " + register.getBalance() + " �Դϴ�. ");
			checkCurrentState();
		}
	}
	
	// ��� 2 : ��ǰ ���� �� ��Ȳ ���� �޽��� ���
	public void currentLog(String str){
		jTextArea_log.append(str + "\n");
	}
	
	// ��� 3 : UI Disable with CurrentState
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

	// ��� 7. ������ �����̿��Ͽ� �����ڿ��� �����ϴ� �޼ҵ� 
	@Override
	public void onPropertyEvent(Object source, String name, String value) {
		if(name.equals("sale.currentTotal")){
			jTextField_CurrentTotal.setText(value);
			currentLog("���� �� �ݾ��� " + value + " �Դϴ�. ");
		}else if(name.equals("sale.tax")){
			jTextField_calculateTax.setText(value.toString());
			currentLog("���� ���� ���� �� �ݾ��� " + value.toString() + " �Դϴ�. ");
		}else if(name.equals("sale.discount")){
			jTextField_applyDiscount.setText(value.toString());
			currentLog("���� ���� ���� �� �ݾ��� " + value.toString() + " �Դϴ�. ");
		}
	}


	// �������� �Ǻ��ϴ� �޼ҵ�
	public boolean isNumber(String str){
		boolean result = false;  
		try{
			Integer.parseInt(str) ;
			result = true ;
		}catch(Exception e){}
		return result ;
	}

}






