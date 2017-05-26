package ATMGUI;

// run: java ATM sun.jdbc.odbc.JdbcOdbcDriver jdbc:odbc:ATM
// Tutorial 26: ATM.java
// ATM application allows users to access an account, 
// view the balance and withdraw money from the account.

import java.awt.*;
import java.awt.event.*;
import java.text.*;
import javax.swing.*;
import javax.swing.event.*;
import java.sql.*; //SMS: DB1) �����ͺ��̽��� ����ϱ� ���� import 

public class ATM
{
	// instance variables used to store PIN and
	// firstName from database (DB�κ��� �о���� ���¹�ȣ�� �̸�)
	private String pin, firstName;
	// SMS: ������ �־�ΰ� �Է½� ����� ������.

	// instance variable used to store account balance
	// SMS: ������� �ܾ� ���� ����.
	private double balance;

	// SMS: DB2) �ʿ��� ������ �����ؾ��Ѵ�.
	private Connection myConnection;
	private Statement myStatement;
	private ResultSet myResultSet;


	// constructor
	// SMS: Ŭ���� �̸��� ���� ATM
	// SMS: ���ڰ� �� ��
	// SMS: String databaseDriver, String databaseURL
	// SMS: �����ͺ��̽� ����̹��� �����ͺ��̽� �̸�(��ġ) : sun.jdbc.odbc.JdbcOdbcDriver
	// jdbc:odbc:ATM
	// => main�� args[]�� ���µ� args[0], args[1]�� ���ڸ� �����´�.
	// SMS: �Լ� �̸� ������ Ŭ�� -> Open hierarchy �ϸ� �Լ��� ȣ���ϴ� ������ �����ֹǷ� �ڵ��ؼ��� �����ϴ�.

	private ATM_GUI atmgui;

	// SMS : ATM �������� ���ڷ� ATM_GUI�� �޾ƿ´�.
	public ATM(String databaseDriver, String databaseURL, ATM_GUI atm_gui) {
		// SMS: DB3) ���� DB ����.
		try {
			// SMS: load current DB driver
			// databaseDriver �� : �Ѱ���� ���ڰ���.
			Class.forName(databaseDriver);

			// connect to database
			// DriverManager Ŭ���� : the basic sevice for managing a set
			// databaseURL �� : "jdbc:odbc:ATM"
			myConnection = DriverManager.getConnection(databaseURL);
			// Statement����
			myStatement = myConnection.createStatement();

			atmgui = atm_gui;

			// exception�߻��� ��µǵ���.
		} catch (SQLException exception) {
			exception.printStackTrace();

		} catch (ClassNotFoundException exception) {
			exception.printStackTrace();
		}

	} // end constructor

	
	
	// SMS : withdraw �޼ҵ忡���� ������ �Ǵ��Ͽ� T/F�� ��ȯ�Ͽ� GUI���� UI�� ó���Ѵ�.
	// withdraw amount from account
	// ��ݽ�, �ݾ� �Է� �� "Enter" ��ư�� ������ �� ȣ��Ǵ� �޼ҵ�
	public boolean withdraw(double withdrawAmount) {
		// determine if amount can be withdrawn
		
		// SMS : ó���� �����ϸ� True�� ��ȯ�Ѵ�.
		if (withdrawAmount <= balance) {
			balance -= withdrawAmount; // calculate new balance
			// SMS: ���⿡�� ����� �ݾ��� ����.
			updateBalance(); // update record in database

			return true;
		// SMS : �����ϸ� False�� ��ȯ�Ѵ�.	
		} else // amount cannot be withdrawn
		{
			return false;
		}

	} // end method withdraw

	// load account numbers to accountNumberJComboBox

	// SMS: �����غ���
	// SMS: �Է¹޴� ���¹�ȣ Ŭ���� ����.
	// SMS: DB0) �����ͺ��̽��� ���¹�ȣ�� �޺��ڽ��� ä�����ϴµ� Connection, Statement, ResultSet
	// Ŭ�������� �ʿ���., ���� import�ʿ�.
	// SMS: DB4) ���� �޺��ڽ��� ���¹�ȣ���� ä���.

	
	// load account numbers to accountNumberJComboBox
	protected ResultSet loadAccountNumbers() {
		
		try {
			myResultSet = myStatement.executeQuery(
					"SELECT accountNumber FROM accountInformation");
						
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return myResultSet;

	} // end method loadAccountNumbers
	
	
	// get account information from database
	// SMS : ***
	// SMS : ����ڰ� ������ ���¹�ȣ�� ������ DB���� �о�´�. ��й�ȣPin�� �´��� Ȯ���ؾ��ϱ� �����̴�.
	public void retrieveAccountInformation() {
		// SMS : SQL
		/*
		 * SELECT pin, firstName, balanceAmount FROM accountInformation WHERE
		 * accountNumber = ����ڰ� ������ ���¹�ȣ (�� ���� Ű�� ������ DB) userAccountNumber����
		 * �̷��� ������ ���� ��ü�� �Ѿ�Ƿ�(string���� �ν�) ���� �ȿ� �ִ� ���� �Ѱ��־�� �Ѵ�. ��, string ������
		 * �� ���־���Ѵ�.
		 */

		// P : SQL�� ����
		try { // SMS : try - catch������ �ٲ�����Ѵ�.
			myResultSet = myStatement.executeQuery("SELECT pin, firstName, balanceAmount " + "FROM accountInformation "
					+ "WHERE accountNumber = '" + atmgui.getUserAccountNumber() + "'" // SMS:
																						// ***
																			// ���Ѽ�
																						// �־���Ѵ�.
			// SMS : *** �״�� �־������ ��Ʈ������ �پ�����⶧���� ���Ⱑ �ʿ��ϴ�.
			// SMS : ** ���¹�ȣ�� ���� ��Ʈ������ �νĵǾ�����⶧����, ''�� �ٿ����Ѵ�. '" +
			// userAccountNumber +"'" �̷���!
			);

			// P : ������ó��
			if (myResultSet.next()) { // Ŀ���� ��������
				pin = myResultSet.getString("pin");
				System.out.println("pin : " + pin);
				firstName = myResultSet.getString("firstName");
				balance = myResultSet.getDouble("balanceAmount");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	} // end method retrieveAccountInformation

	// update database after withdrawing
	public void updateBalance() {
		// P : DB���� ��ݾ��� ����
		// P : sql
		// UPDATE accountInformation
		// SET balanceAmount = �����ܾ� (balance ����)
		// WHERE accountNumber = ������¹�ȣ (userAccount����)

		// sql�� ����
		try {
			myStatement.executeUpdate("UPDATE accountInformation " + "SET balanceAmount = " + balance
					+ " WHERE accountNumber = '" + atmgui.getUserAccountNumber() + "'");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} // end method updateBalance

	// close statement and database connection
	public void frameWindowClosing(WindowEvent event) {

	} // end method frameWindowClosing

	
	// SMS : getter �޼ҵ带 ���� ATMGUI�� �����Ѵ�. 
	public String getPin() {
		return pin;
	}

	public String getFirstName() {
		return firstName;
	}

	public double getBalance() {
		return balance;
	}

} // end class ATM
