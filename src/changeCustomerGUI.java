import javax.swing.*;//importing packages needed for the Gui elements
import java.awt.*;
import java.awt.event.*;
/*
 * Author: Janujan
 * Date: December 4, 2016
 * Description: GUI that allows the user to change the customers parameters such as name, address, phone etc
* Method List
 * changeCustomerGUI ()
 * void keyTyped(KeyEvent e)
 * void actionPerformed (ActionEvent e)
 * void main(String[] args)
 */
public class changeCustomerGUI extends JFrame implements ActionListener
{
	JTextField nameField, addressField, phoneField, phoneField2, phoneField3, savingsField, chequingsField;//declaring all the variables for the interface
	JLabel name, address,phone, savings, chequings;
	JButton btnAccept;
	public changeCustomerGUI ()
	{
		super ("Change Customer");//Calling constructor
		
		setLayout (null);//absolute layout
		setSize (400, 450);//set window size
		setResizable(false);//not resizable by user
		setLocationRelativeTo(null);//center window to screen
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//dispose of window when its closed
		getContentPane().setBackground(Color.decode("#7197D9"));//set background colour

		name = new JLabel("Full Name:");//Initailizing all the variables
		address = new JLabel("Address:");
		phone = new JLabel("Phone Number:");
		savings = new JLabel("Savings Balance:");
		chequings = new JLabel("Chequings Balance:");
		nameField= new JTextField(AccountsGUI.getAccountInfo(1));//adding old information to the Jfields, so user knows what they are changing
		addressField= new JTextField(AccountsGUI.getAccountInfo(2));
		phoneField= new JTextField(AccountsGUI.getAccountInfo(3).substring(0,3));
		phoneField.addKeyListener(new KeyAdapter() {//When a key is pressed, check if the limit is reached of keys, if so consume it
	        public void keyTyped(KeyEvent e) {
	            if (phoneField.getText().length() >= 3 )
	                e.consume();//consume the key entered over the limit
	        }
	    });
		phoneField2= new JTextField(AccountsGUI.getAccountInfo(3).substring(4,7));
		phoneField2.addKeyListener(new KeyAdapter() {
	        public void keyTyped(KeyEvent e) {//When a key is pressed, check if the limit is reached of keys, if so consume it
	            if (phoneField2.getText().length() >= 3 )
	                e.consume();//consume the key entered over the limit
	        }
	    });
		phoneField3= new JTextField(AccountsGUI.getAccountInfo(3).substring(8,12));
		phoneField3.addKeyListener(new KeyAdapter() {
	        public void keyTyped(KeyEvent e) {//When a key is pressed, check if the limit is reached of keys, if so consume it
	            if (phoneField3.getText().length() >= 4 )
	                e.consume();//consume the key entered over the limit
	        }
	    });
		savingsField= new JTextField(AccountsGUI.getAccountInfo(5));
		savingsField.addKeyListener(new KeyAdapter() {
	        public void keyTyped(KeyEvent e) {//When a key is pressed, check if the limit is reached of keys, if so consume it
	            if (savingsField.getText().length() >= 16 )
	                e.consume();//consume the key entered over the limit
	        }
	    });
		chequingsField= new JTextField(AccountsGUI.getAccountInfo(4));
		chequingsField.addKeyListener(new KeyAdapter() {
	        public void keyTyped(KeyEvent e) {//When a key is pressed, check if the limit is reached of keys, if so consume it
	            if (chequingsField.getText().length() >= 16 )
	                e.consume();//consume the key entered over the limit
	        }
	    });
		btnAccept = new JButton("Change");
		name.setBounds(25,20,100,30);//set bounds of all components
		address.setBounds(35,85,100,30);
		phone.setBounds(30,150,100,30);
		savings.setBounds(35,215,100,30);
		chequings.setBounds(25,280,120,30);
		btnAccept.setBounds(140,350, 100, 30);
		nameField.setBounds(90,20,250,30);
		addressField.setBounds(90,80,250,30);
		phoneField.setBounds(120,150,50,30);
		phoneField2.setBounds(180,150,50,30);
		phoneField3.setBounds(240,150,50,30);
		savingsField.setBounds(140,215,150,30);
		chequingsField.setBounds(140,280,150,30);

		add(phoneField);//add all to the window
		add(phoneField2);
		add(phoneField3);
		add(savingsField);
		add(chequingsField);
		add(nameField);
		add(name);
		add(address);
		add(phone);
		add(savings);
		add(chequings);
		add(addressField);
		add(btnAccept);
		btnAccept.addActionListener (this);//actionlistener for whent he button is pressed
		setVisible (true);//visible

	}
	public void actionPerformed (ActionEvent e){//when the accept button is pressed
		String record = (nameField.getText()+","+addressField.getText()+","+phoneField.getText()+"-"+phoneField2.getText()+"-"+phoneField3.getText()+";"+chequingsField.getText()+";"+savingsField.getText());//string for the new record is the values from the text fields
		record += ";0;0";//add ;0;0 to say that there are no account numbers yet
		System.out.println(record);
		AccountsGUI.changeCustomer(record);//change the record with the old one
		dispose();//dispose of the window
	}
	public static void main(String[] args) {//main self testing to check if window runs(throws error unless list it set)
		changeCustomerGUI changecustomerGUI = new changeCustomerGUI();
	}
}