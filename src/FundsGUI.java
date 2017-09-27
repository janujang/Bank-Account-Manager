import javax.swing.*;//Importing packages funds needs to create GUI
import java.awt.*;
import java.awt.event.*;
//Author Justin Hans
//Date Dec, 4
//Desc. GUI that accoutns GUI opens to manage funds of various accounts
/**method List
 * FundsGUI ()
 * void keyTyped(KeyEvent e)
 * boolean onlyContainsNumbers(String text)
 * void actionPerformed (ActionEvent e)
 * static void main(String[] args)
 * @author Justin
 *
 */
public class FundsGUI extends JFrame implements ActionListener
{
	private boolean state;//private boolean for the state, if on deposit or withdraw mode
	JTextField balance;//declaring Jcomponents for the GUI including radio buttons for the account you wish to change
	JLabel balanceField, header, instruction;
	JButton btnAccept;
	JRadioButton chequingsRadio, savingsRadio;
	public FundsGUI ()
	{
		super ();//Calling constructor
		
		if (AccountsGUI.isState()){
			setTitle("Deposit");
			instruction = new JLabel("Enter an amount to deposit");
		}
		else{
			setTitle("Withdraw");
			instruction = new JLabel("Enter an amount to withdraw");
		}
		setLayout (null);//absolute layout
		setSize (400, 250);//set size of window
		setResizable(false);//user cannot resize window
		setLocationRelativeTo(null);//center window to middle of screen
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//on close of window, dispose it as accountsGUi will likely be running
		getContentPane().setBackground(Color.decode("#7197D9"));//change background colour
		chequingsRadio = new JRadioButton("Chequings", true);//new radio buttons, chequings is defaul selected
		savingsRadio = new JRadioButton("Savings", false);
		ButtonGroup group = new ButtonGroup();//new button group that allows only 1 to be used at a time
		group.add(chequingsRadio);//add both radio buttons to the group
		group.add(savingsRadio);
		chequingsRadio.setBounds(95,95,100,40);//set the bounds of both
		savingsRadio.setBounds(200,95,100,40);
		savingsRadio.setBackground(Color.decode("#7197D9"));
		chequingsRadio.setBackground(Color.decode("#7197D9"));
		balance = new JTextField();
		balance.addKeyListener(new KeyAdapter() {//new key listener that checks when a key is typed if the length will be equal to or over the limit and if so it gets rid of the types key from the field
			public void keyTyped(KeyEvent e) {
				if (balance.getText().length() >= 16 )
					e.consume();//consume the keyentry
			}
		});
		btnAccept = new JButton("OK");
		btnAccept.setBounds(140,150, 100, 30);
		balance.setBounds(65,40,250,35);
		instruction.setBounds(0,10,400,35);
		instruction.setHorizontalAlignment(SwingConstants.CENTER);
		add(instruction);
		add(savingsRadio);//add all components to frame
		add(chequingsRadio);
		add(balance);
		add(btnAccept);
		btnAccept.addActionListener (this);//action listener for when the ok button is pressed
		setVisible (true);

		System.out.println("state of the program " + AccountsGUI.isState());//error purposed, if its on deposit or withdraw mode
	}
	public boolean onlyContainsNumbers(String text) {//parse the text field and if it can be parsed at a double then  return true and if not then false
		try {
			Double.parseDouble(text);
			return true;
		} catch (NumberFormatException ex) {
			return false;
		}
	} 

	public void actionPerformed (ActionEvent e){// if ok is clicked
		if (onlyContainsNumbers(balance.getText())) {// if it only has numbers in the text field

			double amount = Double.parseDouble(balance.getText());
			System.out.println(amount);//create amount and based on if its on deposit or withdraw then do so to the account
			if(AccountsGUI.isState()==true){
				if(savingsRadio.isSelected()){//if the savings button is selected
					AccountsGUI.deposit(amount, false);
					System.out.println("State of Radio Buttons: savings");
					dispose();//dispose of window when done
				}
				else if(chequingsRadio.isSelected()){//if chequings radio is selected
					AccountsGUI.deposit(amount, true);
					System.out.println("State of Radio Buttons: chequings");
					dispose();//dispose of window when done
				}
			}
			else if(AccountsGUI.isState()==false){
				if(savingsRadio.isSelected()){
					AccountsGUI.withdraw(amount, false);
					System.out.println("State of Radio Buttons: savings");
					dispose();
				}
				else if(chequingsRadio.isSelected()){
					AccountsGUI.withdraw(amount, true);
					System.out.println("State of Radio Buttons: chequings");
					dispose();
				}
			}
		}
		else{
			balance.setText("Enter Numerical Value");//if the value entered is not a double
		}
	}
	public static void main(String[] args) {//selftesting main to checjk if its runs
		FundsGUI fundsGUI = new FundsGUI();
	}
}