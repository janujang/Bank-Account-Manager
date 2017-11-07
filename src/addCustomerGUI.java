import javax.swing.*;//importing packages for the Jcomponents and window
import java.awt.*;
import java.awt.event.*;
/*
 * Author: Janujan
 * Date: December 4, 2016
 * Description: GUI that allows the user to add a new customer to the customer list
 * Method List
 * addCustomerGUI ()
 * void keyTyped(KeyEvent e)
 * void actionPerformed (ActionEvent e)
 * static void main(String[] args)
 *
 */
public class addCustomerGUI extends JFrame implements ActionListener
{
	JTextField nameField, addressField, phoneField, phoneField2, phoneField3, savingsField, chequingsField;//decalring all variables for the GUI 
	JLabel name, address,phone, savings, chequings;
	JButton btnAccept;
	public addCustomerGUI ()
	{
		super ("Add Customer");//calling constructor
		setResizable(false);//not resizable by user
		setLayout (null);//absolute layout
		setSize (400, 450);//setting size
		setLocationRelativeTo(null);//Center it to the screen
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//dispose the window when the program is closed
		getContentPane().setBackground(Color.decode("#7197D9"));//background colour

		name = new JLabel("Full Name:");//initailizing all variables
		address = new JLabel("Address:");
		phone = new JLabel("Phone Number:");
		savings = new JLabel("Savings Balance:");
		chequings = new JLabel("Chequings Balance:");
		nameField= new JTextField();
		addressField= new JTextField();
		phoneField= new JTextField();
		phoneField.addKeyListener(new KeyAdapter() {//keylistener for the text field so that when a key is entered, if the limit of characters it met it consumes the key entry
	        public void keyTyped(KeyEvent e) {
	            if (phoneField.getText().length() >= 3 )
	                e.consume();//consume the key if over limit
	        }
	    });
		phoneField2= new JTextField();
		phoneField2.addKeyListener(new KeyAdapter() {//keylistener for the text field so that when a key is entered, if the limit of characters it met it consumes the key entry
	        public void keyTyped(KeyEvent e) {
	            if (phoneField2.getText().length() >= 3 )
	                e.consume();//consume the key if over limit
	        }
	    });
		phoneField3= new JTextField();
		phoneField3.addKeyListener(new KeyAdapter() {//keylistener for the text field so that when a key is entered, if the limit of characters it met it consumes the key entry
	        public void keyTyped(KeyEvent e) {
	            if (phoneField3.getText().length() >= 4 )
	                e.consume();//consume the key if over limit
	        }
	    });
		savingsField= new JTextField();
		savingsField.addKeyListener(new KeyAdapter() {//keylistener for the text field so that when a key is entered, if the limit of characters it met it consumes the key entry
	        public void keyTyped(KeyEvent e) {
	            if (savingsField.getText().length() >= 16 )
	                e.consume();//consume the key if over limit
	        }
	    });
		chequingsField= new JTextField();
		chequingsField.addKeyListener(new KeyAdapter() {
	        public void keyTyped(KeyEvent e) {//keylistener for the text field so that when a key is entered, if the limit of characters it met it consumes the key entry
	            if (chequingsField.getText().length() >= 16 )
	                e.consume();//consume the key if over limit
	        }
	    });
		btnAccept = new JButton("Add");
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

		add(phoneField);//add them all to the window
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
		btnAccept.addActionListener (this);//action listener for the accept button
		setVisible (true);//visible

	}
	public void actionPerformed (ActionEvent e){//when the button is clicked
		String record = (nameField.getText()+","+addressField.getText()+","+phoneField.getText()+"-"+phoneField2.getText()+"-"+phoneField3.getText()+";"+chequingsField.getText()+";"+savingsField.getText());//turn all the field info into a record
		record += ";0;0";//add this to end meaning no acct numbers
		System.out.println(record);
		AccountsGUI.addCustomer(record);//add this customer to the accountlist
		dispose();//dispose of this window
	}
	public static void main(String[] args) {//selftesting main that creates the GUi
		addCustomerGUI addcustomerGUI = new addCustomerGUI();
	}
}