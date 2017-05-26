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
import java.sql.*; //SMS: DB1) 데이터베이스를 사용하기 위한 import 

public class ATM
{
	// instance variables used to store PIN and
	// firstName from database (DB로부터 읽어들인 계좌번호와 이름)
	private String pin, firstName;
	// SMS: 변수에 넣어두고 입력시 사용할 변수들.

	// instance variable used to store account balance
	// SMS: 사용자의 잔액 담을 변수.
	private double balance;

	// SMS: DB2) 필요한 변수를 선언해야한다.
	private Connection myConnection;
	private Statement myStatement;
	private ResultSet myResultSet;


	// constructor
	// SMS: 클래스 이름과 같이 ATM
	// SMS: 인자가 두 개
	// SMS: String databaseDriver, String databaseURL
	// SMS: 데이터베이스 드라이버와 데이터베이스 이름(위치) : sun.jdbc.odbc.JdbcOdbcDriver
	// jdbc:odbc:ATM
	// => main의 args[]에 들어가는데 args[0], args[1]로 인자를 꺼내온다.
	// SMS: 함수 이름 오른쪽 클릭 -> Open hierarchy 하면 함수를 호출하는 구조를 보여주므로 코드해석에 유용하다.

	private ATM_GUI atmgui;

	// SMS : ATM 생성자의 인자로 ATM_GUI를 받아온다.
	public ATM(String databaseDriver, String databaseURL, ATM_GUI atm_gui) {
		// SMS: DB3) 이제 DB 연결.
		try {
			// SMS: load current DB driver
			// databaseDriver 값 : 넘겨줬던 인자값들.
			Class.forName(databaseDriver);

			// connect to database
			// DriverManager 클래스 : the basic sevice for managing a set
			// databaseURL 값 : "jdbc:odbc:ATM"
			myConnection = DriverManager.getConnection(databaseURL);
			// Statement생성
			myStatement = myConnection.createStatement();

			atmgui = atm_gui;

			// exception발생시 출력되도록.
		} catch (SQLException exception) {
			exception.printStackTrace();

		} catch (ClassNotFoundException exception) {
			exception.printStackTrace();
		}

	} // end constructor

	
	
	// SMS : withdraw 메소드에서는 로직만 판단하여 T/F로 반환하여 GUI에서 UI를 처리한다.
	// withdraw amount from account
	// 출금시, 금액 입력 후 "Enter" 버튼이 눌러질 때 호출되는 메소드
	public boolean withdraw(double withdrawAmount) {
		// determine if amount can be withdrawn
		
		// SMS : 처리가 성공하면 True를 반환한다.
		if (withdrawAmount <= balance) {
			balance -= withdrawAmount; // calculate new balance
			// SMS: 여기에서 디비의 금액을 뺀다.
			updateBalance(); // update record in database

			return true;
		// SMS : 실패하면 False를 반환한다.	
		} else // amount cannot be withdrawn
		{
			return false;
		}

	} // end method withdraw

	// load account numbers to accountNumberJComboBox

	// SMS: 수정해보쟈
	// SMS: 입력받는 계좌번호 클래스 구현.
	// SMS: DB0) 데이터베이스의 계좌번호를 콤보박스에 채워야하는데 Connection, Statement, ResultSet
	// 클래스들이 필요함., 따라서 import필요.
	// SMS: DB4) 이제 콤보박스에 계좌번호들을 채운다.

	
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
	// SMS : 사용자가 선택한 계좌번호의 정보를 DB에서 읽어온다. 비밀번호Pin과 맞는지 확인해야하기 때문이다.
	public void retrieveAccountInformation() {
		// SMS : SQL
		/*
		 * SELECT pin, firstName, balanceAmount FROM accountInformation WHERE
		 * accountNumber = 사용자가 선택한 계좌번호 (이 것을 키로 가지고 DB) userAccountNumber변수
		 * 이렇게 넣으면 변수 자체가 넘어가므로(string으로 인식) 변수 안에 있는 것을 넘겨주어야 한다. 즉, string 연결을
		 * 잘 해주어야한다.
		 */

		// P : SQL문 실행
		try { // SMS : try - catch문으로 바꿔줘야한다.
			myResultSet = myStatement.executeQuery("SELECT pin, firstName, balanceAmount " + "FROM accountInformation "
					+ "WHERE accountNumber = '" + atmgui.getUserAccountNumber() + "'" // SMS:
																						// ***
																			// 시켜서
																						// 넣어야한다.
			// SMS : *** 그대로 넣어버리면 스트링끼리 붙어버리기때문에 띄어쓰기가 필요하다.
			// SMS : ** 계좌번호는 현재 스트링으로 인식되어버리기때문에, ''를 붙여야한다. '" +
			// userAccountNumber +"'" 이렇게!
			);

			// P : 데이터처리
			if (myResultSet.next()) { // 커서가 다음으로
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
		// P : DB에서 출금액을 빼기
		// P : sql
		// UPDATE accountInformation
		// SET balanceAmount = 현재잔액 (balance 변수)
		// WHERE accountNumber = 현재계좌번호 (userAccount변수)

		// sql문 실행
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

	
	// SMS : getter 메소드를 통해 ATMGUI를 참조한다. 
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
