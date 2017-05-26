package ATM_OOP;

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

public class ATM extends JFrame // SMS: main class는 JFrame 을 상속한다.
{
	
// GUI 컴포넌트들 선언함.
  
	// JTextArea to display message
   private JTextArea messageJTextArea; // SMS: 변수명 정할 때 길게 정확하게 적는 것이 좋다. 

   // JTextField to enter PIN or withdrawal amount
   private JTextField numberJTextField;

   // JPanel for number JButtons
   private JPanel buttonsJPanel;

   // JButtons for input of PIN or withdrawal amount
   private JButton oneJButton;
   private JButton twoJButton;
   private JButton threeJButton;
   private JButton fourJButton;
   private JButton fiveJButton;
   private JButton sixJButton;
   private JButton sevenJButton;
   private JButton eightJButton;
   private JButton nineJButton;
   private JButton zeroJButton;

   //SMS: 버튼이 네 개
   // JButton to submit PIN or withdrawal amount
   private JButton enterJButton;

   // JButton to view balance
   private JButton balanceJButton;

   // JButton to withdraw from account
   private JButton withdrawJButton;

   // JButton to close the transaction
   private JButton doneJButton;

   
   // JPanel to get account numbers
   private JPanel accountNumberJPanel;

   // JLabel and JComboBox for account numbers
   private JLabel accountNumberJLabel;
   private JComboBox accountNumberJComboBox;

   // constants for user action
   // SMS: 숫자 키를 누를 때 인출키인지, 비밀번호 입력키인지 구분해야하므로 상수로 선언한다.
   private final static int ENTER_PIN = 1;
   private final static int WITHDRAWAL = 2;
   // SMS:상수의 변수 이름은 대문자다.

   // instance variables used to store PIN and
   // firstName from database (DB로부터 읽어들인 계좌번호와 이름)
   private String pin, firstName;
   //SMS: 변수에 넣어두고 입력시 사용할 변수들.
   
   // instance variable used to distinguish user action
   private int action;

   // instance variables used to store user selected account number
   // and PIN (사용자가 입력한 계좌번호와 PIN)
   //SMS : 사용자가 선택한 계좌번호는 userAccountNumber. 여기에 담겨서 쿼리를 통해서 비밀번호 알아올 것이다.
   private String userAccountNumber, userPIN;
   //SMS: 변수 이름 앞에 user 붙으면 사용자가 입력한 input값 받을 변수임. 
   
   // instance variable used to store account balance
   //SMS: 사용자의 잔액 담을 변수.
   private double balance;

   
   //SMS: DB2) 필요한 변수를 선언해야한다.
   private Connection myConnection;
   private Statement myStatement;
   private ResultSet myResultSet;
   
   
   // constructor
   //SMS: 클래스 이름과 같이 ATM 
   //SMS: 인자가 두 개
   //SMS: String databaseDriver, String databaseURL
   //SMS: 데이터베이스 드라이버와 데이터베이스 이름(위치) : sun.jdbc.odbc.JdbcOdbcDriver jdbc:odbc:ATM
   //     => main의 args[]에 들어가는데 args[0], args[1]로 인자를 꺼내온다.
   //SMS: 함수 이름 오른쪽 클릭 -> Open hierarchy 하면 함수를 호출하는 구조를 보여주므로 코드해석에 유용하다.
   public ATM( String databaseDriver, String databaseURL )
   {
	   //SMS: DB3) 이제 DB 연결.
	   try{
		   //SMS: load current DB driver
		   // databaseDriver 값 : 넘겨줬던 인자값들.
		   Class.forName(databaseDriver);
		   
		   // connect to database
		   // DriverManager 클래스 : the basic sevice for managing a set
		   // databaseURL 값 : "jdbc:odbc:ATM"  
		   myConnection = DriverManager.getConnection(databaseURL);
		   // Statement생성
		   myStatement = myConnection.createStatement();
		
		   // exception발생시 출력되도록.
	   }catch(SQLException exception){
		   exception.printStackTrace();
		   
	   }catch(ClassNotFoundException exception){
		   exception.printStackTrace();
	   }
	   
	   
      createUserInterface(); // set up GUI
      
   } // end constructor

   // create and position GUI components; register event handler
   private void createUserInterface()
   {
      // get content pane for attaching GUI components
	  // JFrame에 GUI 컴포넌트를 붙이기 위해서는 content pane을 얻어와야 한다. 
	   //SMS: getContentPane으로 불러와야한다. 컨테이너 안에 넣는것. 
	   //SMS: 붙이는 방식은 그리드 동서남북 등 레이아웃매니저가 있지만, 좌표로 여기서는 한다. 
      Container contentPane = getContentPane();

      //SMS: 따라서 setLayout(null)을 주어서 배치관리자를 설정하고 좌표로 값을 줄 것이다.
      // enable explicit positioning of GUI components
      contentPane.setLayout( null );

      // set up messageJTextArea  
      messageJTextArea = new JTextArea();
      messageJTextArea.setBounds( 40, 16, 288, 88 ); // (왼쪽 모서리 x, y, 폭, 높이) //SMS: setBounds로 좌표와 크기  
      messageJTextArea.setText(
         "Please select your account number." );
      messageJTextArea.setBorder( //SMS: 경계선 지정
         BorderFactory.createLoweredBevelBorder() );
         //SMS: 보더가 하나 만들어져서 이것을 setBorder에 넣어준다. 
      messageJTextArea.setEditable( false ); ////SMS:  편집불가능하게 함
      contentPane.add( messageJTextArea );//SMS: 붙이기
      //SMS:  이렇게 설정해서 붙이는 방식이라고 생각하면 된다.
      
      // set up numberJTextField 
      numberJTextField = new JTextField();
      numberJTextField.setBounds( 110, 120, 128, 21 );
      numberJTextField.setBorder( 
         BorderFactory.createLoweredBevelBorder() );
      numberJTextField.setEditable( false );
      contentPane.add( numberJTextField );
      //SMS:  또 설정해서 붙이고~
      
      
      // set up buttonsJPanel
      buttonsJPanel = new JPanel();//SMS: 겉을 둘러싸고 있는 판넬. 다른 컴포넌트들을 그룹으로 묶어주는 역할을 한다. 판넬 크기 먼저 정하고 여기에 다른 컴포넌트들을 붙여주남
      buttonsJPanel.setBounds( 44, 160, 276, 150 );
      buttonsJPanel.setBorder( BorderFactory.createEtchedBorder() ); //SMS: createEtchedBorder홈을 파듯 경계선을 준다. 
      buttonsJPanel.setLayout( null ); //SMS:판넬 안에 좌표를 줄 것이야
      contentPane.add( buttonsJPanel );

      // set up oneJButton
      oneJButton = new JButton();//SMS: 버튼 하나 만들고
      oneJButton.setBounds( 53, 28, 24, 24 ); //SMS: 53 28을 주었는데 이 버튼을 판넬 안에 붙일거니까, contentPane이 아닌 그 판넬을 기준으로 좌표를 형성하면 된다.
      oneJButton.setText( "1" );
      oneJButton.setBorder( 
         BorderFactory.createRaisedBevelBorder() ); //SMS: 이번에는 raisedBevel이넹
      buttonsJPanel.add( oneJButton );
      oneJButton.addActionListener( //SMS: 버튼을 누르면 발생하는 액션이벤트. 액션이벤트 발생시 액션 리스너가 해결
         // ActionListener는 인터페이스이다.
    	 // ActionListener 인터페이스에는 actionPerformed()가 선언되어 있다.
         // ActionListener 인터페이스를 구현한 익명의 클래스의 객체를 생성함
    		//SMS: ***
         new ActionListener() // anonymous inner class		
       //SMS: 액션리스너는 인터페이스므로 인스턴스를 만들 수 없다. 그런데 여기서 new를 했네? 가능한 경우는 어떤경우지/ 액션리스너 안에 인터페이스의 메소드들을 오버라이드하여 구현해주면 된다.
         //SMS: new ActionListener() 는 원래는 안되지롱
         {
            // event handler called when oneJButton is clicked
        	//SMS: 액션 리스너 안의 메소드가 액션퍼폼드 하나 있는데 이고 구현해주면 된다. 아니라면 새롭게 클래스 만들어서 다시 뉴 해야하니까 익명의 내부클래스를 이용함으로써 효율성이 커짐.
            public void actionPerformed( ActionEvent event ) //SMS: 내부적으로 이름은 없는 클래스가 만들어지고 그것의 인스턴스가 만들어진다고 생각하면된다. 
            {  // "1" 버튼이 눌려지면 아래 문장이 실행된다.
               oneJButtonActionPerformed( event );  //SMS: 코드는 이렇게 수행되도록 작성함.
            }

         } // end anonymous inner class

      ); // end call to addActionListener

      // set up twoJButton
      twoJButton = new JButton();
      twoJButton.setBounds( 77, 28, 24, 24 );
      twoJButton.setText( "2" );
      twoJButton.setBorder( 
         BorderFactory.createRaisedBevelBorder() );
      buttonsJPanel.add( twoJButton );
      twoJButton.addActionListener(

         new ActionListener() // anonymous inner class
         {
            // event handler called when twoJButton is clicked
            public void actionPerformed( ActionEvent event )
            {
               twoJButtonActionPerformed( event );
            }

         } // end anonymous inner class

      ); // end call to addActionListener

      // set up threeJButton
      threeJButton = new JButton();
      threeJButton.setBounds( 101, 28, 24, 24 );
      threeJButton.setText( "3" );
      threeJButton.setBorder( 
         BorderFactory.createRaisedBevelBorder() );
      buttonsJPanel.add( threeJButton );
      threeJButton.addActionListener(

         new ActionListener() // anonymous inner class
         {
            // event handler called when threeJButton is clicked
            public void actionPerformed( ActionEvent event )
            {
               threeJButtonActionPerformed( event );
            }

         } // end anonymous inner class

      ); // end call to addActionListener

      // set up fourJButton
      fourJButton = new JButton();
      fourJButton.setBounds( 53, 52, 24, 24 );
      fourJButton.setText( "4" );
      fourJButton.setBorder( 
         BorderFactory.createRaisedBevelBorder() );
      buttonsJPanel.add( fourJButton );
      fourJButton.addActionListener(

         new ActionListener() // anonymous inner class
         {
            // event handler called when fourJButton is clicked
            public void actionPerformed( ActionEvent event )
            {
               fourJButtonActionPerformed( event );
            }

         } // end anonymous inner class

      ); // end call to addActionListener
      
      // set up fiveJButton
      fiveJButton = new JButton();
      fiveJButton.setBounds( 77, 52, 24, 24 );
      fiveJButton.setText( "5" );
      fiveJButton.setBorder( 
         BorderFactory.createRaisedBevelBorder() );
      buttonsJPanel.add( fiveJButton );
      fiveJButton.addActionListener(

         new ActionListener() // anonymous inner class
         {
            // event handler called when fiveJButton is clicked
            public void actionPerformed( ActionEvent event )
            {
               fiveJButtonActionPerformed( event );
            }

         } // end anonymous inner class

      ); // end call to addActionListener

      // set up sixJButton
      sixJButton = new JButton();
      sixJButton.setBounds( 101, 52, 24, 24 );
      sixJButton.setText( "6" );
      sixJButton.setBorder( 
         BorderFactory.createRaisedBevelBorder() );
      buttonsJPanel.add( sixJButton );
      sixJButton.addActionListener(

         new ActionListener() // anonymous inner class
         {
            // event handler called when sixJButton is clicked
            public void actionPerformed( ActionEvent event )
            {
               sixJButtonActionPerformed( event );
            }

         } // end anonymous inner class

      ); // end call to addActionListener

      // set up sevenJButton
      sevenJButton = new JButton();
      sevenJButton.setBounds( 53, 76, 24, 24 );
      sevenJButton.setText( "7" );
      sevenJButton.setBorder( 
         BorderFactory.createRaisedBevelBorder() );
      buttonsJPanel.add( sevenJButton );
      sevenJButton.addActionListener(

         new ActionListener() // anonymous inner class
         {
            // event handler called when sevenJButton is clicked
            public void actionPerformed( ActionEvent event )
            {
               sevenJButtonActionPerformed( event );
            }

         } // end anonymous inner class

      ); // end call to addActionListener

      // set up eightJButton
      eightJButton = new JButton();
      eightJButton.setBounds( 77, 76, 24, 24 );
      eightJButton.setText( "8" );
      eightJButton.setBorder( 
         BorderFactory.createRaisedBevelBorder() );
      buttonsJPanel.add( eightJButton );
      eightJButton.addActionListener(

         new ActionListener() // anonymous inner class
         {
            // event handler called when eightJButton is clicked
            public void actionPerformed( ActionEvent event )
            {
               eightJButtonActionPerformed( event );
            }

         } // end anonymous inner class

      ); // end call to addActionListener

      // set up nineJButton
      nineJButton = new JButton();
      nineJButton.setBounds( 101, 76, 24, 24 );
      nineJButton.setText( "9" );
      nineJButton.setBorder( 
         BorderFactory.createRaisedBevelBorder() );
      buttonsJPanel.add( nineJButton );
      nineJButton.addActionListener(

         new ActionListener() // anonymous inner class
         {
            // event handler called when nineJButton is clicked
            public void actionPerformed( ActionEvent event )
            {
               nineJButtonActionPerformed( event );
            }

         } // end anonymous inner class

      ); // end call to addActionListener

      // set up zeroJButton
      zeroJButton = new JButton();
      zeroJButton.setBounds( 77, 100, 24, 24 );
      zeroJButton.setText( "0" );
      zeroJButton.setBorder(
         BorderFactory.createRaisedBevelBorder() );
      buttonsJPanel.add( zeroJButton );
      zeroJButton.addActionListener(

         new ActionListener() // anonymous inner class
         {
            // event handler called when zeroJButton is clicked
            public void actionPerformed( ActionEvent event )
            {
               zeroJButtonActionPerformed( event );
            }

         } // end anonymous inner class

      ); // end call to addActionListener

      disableKeyPad(); // disable numeric JButtons

      // set up enterJButton
      enterJButton = new JButton();
      enterJButton.setBounds( 149, 17, 72, 24 );
      enterJButton.setText( "Enter" );
      enterJButton.setBorder( 
         BorderFactory.createRaisedBevelBorder() );
      buttonsJPanel.add( enterJButton );
      enterJButton.setEnabled( false );
      enterJButton.addActionListener(

         new ActionListener() // anonymous inner class
         {
            // event handler called when enterJButton is clicked
            public void actionPerformed( ActionEvent event )
            {
               enterJButtonActionPerformed( event );
            }

         } // end anonymous inner class

      ); // end call to addActionListener

      // set up balanceJButton
      balanceJButton = new JButton();
      balanceJButton.setBounds( 149, 49, 72, 24 );
      balanceJButton.setText( "Balance" );
      balanceJButton.setBorder( 
         BorderFactory.createRaisedBevelBorder() );
      buttonsJPanel.add( balanceJButton );
      balanceJButton.setEnabled( false );
      balanceJButton.addActionListener(

         new ActionListener() // anonymous inner class
         {
            // event handler called when balanceJButton is clicked
            public void actionPerformed( ActionEvent event )
            {
               balanceJButtonActionPerformed( event );
            }

         } // end anonymous inner class

      ); // end call to addActionListener

      // set up withdrawJButton
      withdrawJButton = new JButton();
      withdrawJButton.setBounds( 149, 81, 72, 24 );
      withdrawJButton.setText( "Withdraw" );
      withdrawJButton.setBorder(
         BorderFactory.createRaisedBevelBorder() );
      withdrawJButton.setEnabled( false );
      buttonsJPanel.add( withdrawJButton );
      withdrawJButton.addActionListener(

         new ActionListener() // anonymous inner class
         {
            // event handler called when withdrawJButton is clicked
            public void actionPerformed( ActionEvent event )
            {
               withdrawJButtonActionPerformed( event );
            }

         } // end anonymous inner class

      ); // end call to addActionListener

      // set up doneJButton
      doneJButton = new JButton();
      doneJButton.setBounds( 149, 113, 72, 24 );
      doneJButton.setText( "Done" );
      doneJButton.setBorder( 
         BorderFactory.createRaisedBevelBorder() );
      doneJButton.setEnabled( false );
      buttonsJPanel.add( doneJButton );
      doneJButton.addActionListener(

         new ActionListener() // anonymous inner class
         {
            // event handler called when doneJButton is clicked
            public void actionPerformed( ActionEvent event )
            {
               doneJButtonActionPerformed( event );
            }

         } // end anonymous inner class

      ); // end call to addActionListener

    //SMS: 계속 같은 형식으로 버튼을 생성하여 붙이고 버튼이 눌리면 액션이벤트 발생하여 리스너 안의 메소드를 호출하게된다.
      
      
      // set up accountNumberJPanel
      accountNumberJPanel = new JPanel();
      accountNumberJPanel.setBounds( 44, 320, 276, 48 );
      accountNumberJPanel.setBorder( 
         BorderFactory.createEtchedBorder() );
      accountNumberJPanel.setLayout( null );
      contentPane.add( accountNumberJPanel );

      // set up accountNumberJLabel
      accountNumberJLabel = new JLabel();
      accountNumberJLabel.setBounds( 25, 15, 100, 20 );
      accountNumberJLabel.setText( "Account Number:" );
      accountNumberJPanel.add( accountNumberJLabel );

      // set up accountNumberJComboBox
      accountNumberJComboBox = new JComboBox();
      accountNumberJComboBox.setBounds( 150, 12, 96, 25 );
      accountNumberJComboBox.addItem( "" );
      accountNumberJComboBox.setSelectedIndex( 0 );
      accountNumberJPanel.add( accountNumberJComboBox );
      accountNumberJComboBox.addItemListener(

    		//SMS: 콤보박스는 아이템이벤트 발생하여 아이템리스너를 사용한다.
    		  //SMS : 계좌번호를 사용자가 선택하면 아이템이벤트가 발생한다. (콤보박스)
         new ItemListener() // anonymous inner class
         {
            // event handler called when account number is chosen
            public void itemStateChanged( ItemEvent event )
            {
                accountNumberJComboBoxItemStateChanged( event );
            }

         } // end anonymous inner class

      ); // end call to addItemListener

      // read account numbers from database and 
      // place them in accountNumberJComboBox
      loadAccountNumbers(); //SMS: 계좌번호 입력하는 것 

      // set properties of application's window
      setTitle( "ATM" );   // set title bar string
      setSize( 375, 410 ); // set window size
      setVisible( true );  // display window//SMS: 화면에 보임

      // ensure database connection is closed
      // when user closes application window
      addWindowListener(  ////SMS:  최소화, 확대, 종료 등이 클릭될 때 이 리스너를 참조하는데, 여기에서는 종료버튼에 대해서만 구현한다.

         new WindowAdapter() // anonymous inner class
         {
            public void windowClosing( WindowEvent event )
            {
               frameWindowClosing( event );
            }

         } // end anonymous inner class

      ); // end addWindowListener

   } // end method createUserInterface

   
   // process oneJButton click
   private void oneJButtonActionPerformed( ActionEvent event )
   {
	   //SMS: 숫자버튼을 누를 때, 이 함수를 호출한다. 인자로 1을 넘겨준다.
      zeroToNineJButtonActionPerformed( "1" );

   } // end method oneJButtonActionPerformed

   // process twoJButton click
   private void twoJButtonActionPerformed( ActionEvent event )
   {
      zeroToNineJButtonActionPerformed( "2" );

   } // end method twoJButtonActionPerformed

   // process threeJButton click
   private void threeJButtonActionPerformed( ActionEvent event )
   {
      zeroToNineJButtonActionPerformed( "3" );

   } // end method threeJButtonActionPerformed

   // process fourJButton click
   private void fourJButtonActionPerformed( ActionEvent event )
   {
      zeroToNineJButtonActionPerformed( "4" );

   } // end method fourJButtonActionPerformed

   // process fiveJButton click
   private void fiveJButtonActionPerformed( ActionEvent event )
   {
      zeroToNineJButtonActionPerformed( "5" );

   } // end method fiveJButtonActionPerformed

   // process sixJButton click
   private void sixJButtonActionPerformed( ActionEvent event )
   {
      zeroToNineJButtonActionPerformed( "6" );

   } // end method sixJButtonActionPerformed

   // process sevenJButton click
   private void sevenJButtonActionPerformed( ActionEvent event )
   {
      zeroToNineJButtonActionPerformed( "7" );

   } // end method sevenJButtonActionPerformed

   // process eightJButton click
   private void eightJButtonActionPerformed( ActionEvent event )
   {
      zeroToNineJButtonActionPerformed( "8" );

   } // end method eightJButtonActionPerformed

   // process nineJButton click
   private void nineJButtonActionPerformed( ActionEvent event )
   {
      zeroToNineJButtonActionPerformed( "9" );

   } // end method nineJButtonActionPerformed

   // process zeroJButton click
   private void zeroJButtonActionPerformed( ActionEvent event )
   {
      zeroToNineJButtonActionPerformed( "0" );

   } // end method zeroJButtonActionPerformed

   // process clicks of a numeric JButton
   //SMS: 버튼 눌렀을 때 인자만 바꾸어 호출하는 함수!
   private void zeroToNineJButtonActionPerformed( String number )
   {
	   //SMS: 비번 넣는것인지 출금금액 넣는지 구별하는 것.
      // enable enterJButton if it is disabled
      if ( !enterJButton.isEnabled() )
      {
   	   //SMS: 숫자버튼 누르면 엔터버튼은 활성화 되어야한다. 
         enterJButton.setEnabled( true ); 
      }
      
      // if user is entering PIN number display * to conceal PIN
      if ( action == ENTER_PIN )
      {
   	   //SMS: 내부적으로 숫자를 유저핀에 담아두고 아래코드는 유아이적으로 사용자에게 눌렀다고 알려주는 것. 
   	   //SMS: set으로 설정한 것을 userPIN에 담아둔다. 
         userPIN += number; // append number to current PIN
         numberJTextField.setText( 
            numberJTextField.getText() + "*" ); // 보여줄 땐 별표로 부여줌
  	   //SMS: 숫자를 넣을 때 별표를 하나씩 추가해줘야한다. 그래서 다시 setText로!
  	   //SMS: 현재 텍스트를 get하고 set으로 다시 설정.
      }

      else // otherwise display number of JButton user clicked
      {
         numberJTextField.setText( 
            numberJTextField.getText() + number );
      }

   } // end method zeroToNineJButtonsActionPerformed

   // verify PIN or withdraw from account
   private void enterJButtonActionPerformed( ActionEvent event )
   {
      if ( action == ENTER_PIN ) // checking PIN
      {
         // get pin, first name and balance for account number
         // selected in accountNumberJComboBox
   	   //SMS: 사용자가 입력한 비밀번호가 계좌번호와 같은지 DB에 가서 사용자가 선택한 정보를 빼와야한다. 
   	   //SMS: 현재 사용자가 선택한 계좌를 DB에서 꺼내온다. 아래는 그 함수임 
         retrieveAccountInformation(); 

         numberJTextField.setText( "" ); // clear numberJTextField

         // correct PIN number 
         if ( userPIN.equals( pin ) )
         {
            // disable enterJButton
            enterJButton.setEnabled( false ); 
            

            disableKeyPad(); // disable numeric JButtons
          //SMS: 
            // enable balanceJButton and withdrawJButton
            balanceJButton.setEnabled( true );
            withdrawJButton.setEnabled( true ); 

            // display status to user
            messageJTextArea.setText( 
               "Welcome " + firstName + ", select a transaction." );

         } // end if part of if...else
         
         //SMS : 비밀번호 잘못된 경우, Sorry하고 초기화시켜준다. 
         else // wrong PIN number
         {
            // indicate that incorrect PIN was provided
            messageJTextArea.setText( 
               "Sorry, PIN number is incorrect." +
               "\nPlease re-enter the PIN number." );

            userPIN = ""; // clear user's previous PIN entry

         } // end else part of if...else

      } // end if that processes PIN

      // 출금할 액수를 입력한 후, 엔터키를 쳤다면...
      else if ( action == WITHDRAWAL ) // process withdrawal
      {
         enterJButton.setEnabled( false ); // disable enterJButton

         disableKeyPad(); // disable numeric JButtons

         // process withdrawal
         // SMS : 실제 출금은 이 withdraw 메소드에서 실행이된다.
         withdraw( //SMS : 여기서도 디비 접근을 해야한다. 
            Double.parseDouble( numberJTextField.getText() ) ); // 문자열을 실수로 변환한다.

         numberJTextField.setText( "" ); // clear numberJTextField

         // enable balanceJButton and withdrawJButton
         balanceJButton.setEnabled( true );
         withdrawJButton.setEnabled( true ); 

      } // end if that processes withdrawal

   } // end method enterJButtonActionPerformed

   // display account balance
   private void balanceJButtonActionPerformed( ActionEvent event )
   {
      // define display format
	  // DecimalFormat: 숫자를 일정한 형식으로 표시하고자 할 때 사용하는 클래스
      DecimalFormat dollars = new DecimalFormat( "0.00" ); // 소수 둘째자리까지 나타내는 객체가 만들어지는 것이다. 
      //SMS : 형식을 나타낼 때 
      
      // display user's balance
      messageJTextArea.setText( "Your current balance is $" +
         dollars.format( balance ) + "." );

   } // end method balanceJButtonActionPerformed

   // display withdraw action
   private void withdrawJButtonActionPerformed( ActionEvent event )
   {
      // disable Balance and Withdraw JButtons
      balanceJButton.setEnabled( false );
      withdrawJButton.setEnabled( false );

      enableKeyPad(); // enable numeric JButtons

      // display message to user
      messageJTextArea.setText( 
         "Enter the amount you would like to withdraw" );

      // change action to indicate user will provide 
      // withdrawal amount
      action = WITHDRAWAL;

   } // end method withdrawJButtonActionPerformed

   // reset GUI
   private void doneJButtonActionPerformed( ActionEvent event )
   {
      userPIN = ""; // clear userPIN

      disableKeyPad(); // disable numeric JButtons

      // disable OK, Balance, Withdraw and Done JButtons
      enterJButton.setEnabled( false );
      balanceJButton.setEnabled( false );
      withdrawJButton.setEnabled( false );
      doneJButton.setEnabled( false );

      // enable and reset accountNumberJComboBox
      accountNumberJComboBox.setEnabled( true );
      accountNumberJComboBox.setSelectedIndex( 0 );

      // reset messageJTextArea 
      messageJTextArea.setText( 
         "Please select your account number." );

   } // end method doneJButtonActionPerformed

   //SMS: 콤보박스 선택했을 때 나타나는 아이템이벤트 리스너가 호출하는 메소드
   // get account number and enable OK and Done JButtons
   private void accountNumberJComboBoxItemStateChanged( 
      ItemEvent event )
   {
      // get user selected account number if no transaction is
      // in process
	   //SMS: 선택행위가 일어났는지, 변경되었는지 검사하고, 
	   //SMS: 0번 string이 아니어야지 제대로 선택한거니까.
      if ( ( event.getStateChange() == ItemEvent.SELECTED ) && // 선택 행위가 일어났고,
         ( accountNumberJComboBox.getSelectedIndex() != 0 ) ) // 무엇인가 선택했다면...
      {
         // disable accountNumberJComboBox
   	   //SMS: 그렇게되면 disable되고, 사용자가 선택한 계좌 얻어오는 방법은
         accountNumberJComboBox.setEnabled( false );

  	   //SMS: getselectedItem하면 문자열로 바뀌어서 userAccountNumber에 넣어두는데, PIN 넘버와 나중에 비교하기위해 담아둔다.
         // get selected account number
         userAccountNumber = 
            ( String ) accountNumberJComboBox.getSelectedItem();
      //SMS : getSelectedItem 하면 선택한 값을 넘겨준다. 
         
         // change action to indicate that user will provide
         // PIN number
  	   //SMS: 비밀번호 키를 누르는 이벤트랑 구벼하기 위해 이 변수에 엔터핀을 넣어둔다. 
  	   //SMS: 액션에 1을 넣는 것과 엔터핀을 넣는것과 같다. 상수 엔터핀에 1이 들어가있으니까. 2는 인출키 상수임. 헷갈리니까 상수를 정해서 둔 ㄱ서 뿐이야
         action = ENTER_PIN;
         //action = 1; 현재 액션이 무엇인지 명확하지 않다.
         userPIN = "";
         // 사용자 비번 안넣은 상태니까 초기화 함.

         // prompt user to enter PIN number
         messageJTextArea.setText(  	   //SMS: 이 메시지를 보내준다. 
            "Please enter your PIN number." );
 
  	   //SMS: 원래 뭐 있었으면  null로 바꾸어준다. 
         numberJTextField.setText( "" ); // clear numberJTextField
         enableKeyPad();                 // enable numeric JButtons
         doneJButton.setEnabled( true ); // enable doneJButton

      } // end if

   } // end method accountNumberJComboBoxItemStateChanged

   // enable numeric JButtons
   private void enableKeyPad()
   {
      oneJButton.setEnabled( true );   // enable oneJButton
      twoJButton.setEnabled( true );   // enable twoJButton
      threeJButton.setEnabled( true ); // enable threeJButton
      fourJButton.setEnabled( true );  // enable fourJButton
      fiveJButton.setEnabled( true );  // enable fiveJButton
      sixJButton.setEnabled( true );   // enable sixJButton
      sevenJButton.setEnabled( true ); // enable sevenJButton
      eightJButton.setEnabled( true ); // enable eightJButton
      nineJButton.setEnabled( true );  // enable nineJButton
      zeroJButton.setEnabled( true );  // enable zeroJButton

   } // end method enableKeyPad

   // disable numeric JButtons
   private void disableKeyPad()
   {
      oneJButton.setEnabled( false );   // disable oneJButton
      twoJButton.setEnabled( false );   // disable twoJButton
      threeJButton.setEnabled( false ); // disable threeJButton
      fourJButton.setEnabled( false );  // disable fourJButton
      fiveJButton.setEnabled( false );  // disable fiveJButton
      sixJButton.setEnabled( false );   // disable sixJButton
      sevenJButton.setEnabled( false ); // disable sevenJButton
      eightJButton.setEnabled( false ); // disable eightJButton
      nineJButton.setEnabled( false );  // disable nineJButton
      zeroJButton.setEnabled( false );  // disable zeroJButton

   } // end method disableKeyPad

   // withdraw amount from account
   // 출금시, 금액 입력 후 "Enter" 버튼이 눌러질 때 호출되는 메소드
   private void withdraw( double withdrawAmount )
   {
      // determine if amount can be withdrawn
      if ( withdrawAmount <= balance )
      {
         balance -= withdrawAmount; // calculate new balance

         // SMS: 여기에서 디비의 금액을 뺀다. 
         updateBalance(); // update record in database

         // define display format
         DecimalFormat dollars = new DecimalFormat( "0.00" );

         // display balance information to user
         messageJTextArea.setText( "The withdrawal amount is $" +
            dollars.format( withdrawAmount ) + "." );
      }
      else // amount cannot be withdrawn
      {
         messageJTextArea.setText(
            "The withdrawal amount is too large." +
            "\nSelect Withdraw and enter a different amount." );
      }

   } // end method withdraw

   // load account numbers to accountNumberJComboBox
   
   //SMS: 수정해보쟈
   //SMS: 입력받는 계좌번호 클래스 구현. 
   //SMS: DB0) 데이터베이스의 계좌번호를 콤보박스에 채워야하는데 Connection, Statement, ResultSet 클래스들이 필요함., 따라서 import필요.
   //SMS: DB4) 이제 콤보박스에 계좌번호들을 채운다.
   private void loadAccountNumbers()
   {
	   try{
	   //SMS:resultset에 담아준다. 
	   myResultSet = myStatement.executeQuery("SELECT accountNumber FROM accountInformation");
	   
	   //SMS: 계좌번호들을 다 얻어와서 resultset에 담아뒀으니 이제 콤봑스에 추가해야한다. 
	   while(myResultSet.next()){ 	   //SMS:커서의 위치를 다음으로 옮긴 후, 데이터가 더 있으면 true를 리턴함.
	   //SMS: 첫 데이터 이전을 가리키고 있으니 다음으로 옮겨줘야 한다. 그 때, 데이터가 있으면 true를 반환하므로 while문 안으로 들어오게 됨. 
		   //SMS: 계좌번호 타입은 현재 String이다. 
		  accountNumberJComboBox.addItem(
				   myResultSet.getString("accountNumber") 
				   //SMS: getString첫번째 문자열을 가져오는 것이다.
				   );
		   }
	   myResultSet.close();
	   
	   //SMS: 다 가져왔으면 데이터베이스도 종료를 해야한다.
	   }catch(SQLException e){
		   e.printStackTrace();
	   }
   } // end method loadAccountNumbers

   // get account information from database
   // SMS : ***
   // SMS : 사용자가 선택한 계좌번호의 정보를 DB에서 읽어온다. 비밀번호Pin과 맞는지 확인해야하기 때문이다. 
   private void retrieveAccountInformation()
   {
	   // SMS : SQL
	   /*
	   SELECT pin, firstName, balanceAmount 
	   FROM accountInformation
	   WHERE accountNumber = 사용자가 선택한 계좌번호 (이 것을 키로 가지고 DB) userAccountNumber변수 이렇게 넣으면 변수 자체가 넘어가므로(string으로 인식)
	변수 안에 있는 것을 넘겨주어야 한다. 즉, string 연결을 잘 해주어야한다.
	   */
	   
	   // P : SQL문 실행
	   try { //SMS : try - catch문으로 바꿔줘야한다.
		myResultSet = myStatement.executeQuery(
		   "SELECT pin, firstName, balanceAmount " +
		   "FROM accountInformation " +
		   "WHERE accountNumber = '" + userAccountNumber +"'" //SMS: *** 변수인 경우 분리를 시켜서 넣어야한다. 
		   //SMS : *** 그대로 넣어버리면 스트링끼리 붙어버리기때문에 띄어쓰기가 필요하다.
		   //SMS : ** 계좌번호는 현재 스트링으로 인식되어버리기때문에, ''를 붙여야한다. '" + userAccountNumber +"'" 이렇게! 
		   );
		
		// P : 데이터처리
		if(myResultSet.next()){ // 커서가 다음으로 
			pin = myResultSet.getString("pin");
			System.out.println("pin : " +pin);
			firstName = myResultSet.getString("firstName");
			balance = myResultSet.getDouble("balanceAmount");
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	   
	   
	   
   } // end method retrieveAccountInformation

   // update database after withdrawing
   private void updateBalance()
   {
	   // P : DB에서  출금액을 빼기
	   // P : sql
//	   UPDATE accountInformation
//	   SET balanceAmount = 현재잔액 (balance 변수)
//	   WHERE accountNumber = 현재계좌번호 (userAccount변수)

	   // sql문 실행
	   try {
		myStatement.executeUpdate(
			   "UPDATE accountInformation " +
			   "SET balanceAmount = " + balance +
			   " WHERE accountNumber = '" + userAccountNumber +"'"
		   );
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   } // end method updateBalance

   // close statement and database connection
   private void frameWindowClosing( WindowEvent event )
   {

   }  // end method frameWindowClosing

   // method main
   public static void main( String[] args ) 
   {
      // check command-line arguments
      if ( args.length == 2 ) //SMS: 인자 두개가 있는지 확인해야한다.
      {
         // get command-line arguments
    	//SMS: args에 담긴 인자 두개, DB 드라이버와 이름을 꺼내온다.
         String databaseDriver = args[ 0 ];
         String databaseURL = args[ 1 ];

         // create new ATM
         ATM atm = new ATM( databaseDriver, databaseURL );
      }
      else // invalid command-line arguments
      {
         System.out.println( 
            "Usage: java ATM databaseDriver databaseURL" );
      }

   } // end method main

} // end class ATM

/**************************************************************************
 * (C) Copyright 1992-2004 by Deitel & Associates, Inc. and               *
 * Pearson Education, Inc. All Rights Reserved.                           *
 *                                                                        *
 * DISCLAIMER: The authors and publisher of this book have used their     *
 * best efforts in preparing the book. These efforts include the          *
 * development, research, and testing of the theories and programs        *
 * to determine their effectiveness. The authors and publisher make       *
 * no warranty of any kind, expressed or implied, with regard to these    *
 * programs or to the documentation contained in these books. The authors *
 * and publisher shall not be liable in any event for incidental or       *
 * consequential damages in connection with, or arising out of, the       *
 * furnishing, performance, or use of these programs.                     *
 **************************************************************************/