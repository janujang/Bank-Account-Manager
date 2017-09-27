
import javax.swing.*;//All Packages required for components
import java.awt.*;
import java.awt.event.*;
import java.io.*;


//Author Janujan
//Date Dec, 11
//Desc. GUI to change minimum balance
/**method List
 * setMinimumBalanceGUI ()
 * void keyTyped(KeyEvent e)
 * void actionPerformed (ActionEvent e)
 * static Double readMinBalance()
 * static void main(String[] args)
 */

public class setMinimumBalanceGUI extends JFrame implements ActionListener{

	JTextField txtMinBalance;//Declaring variables
	JLabel lblMinBalance;
	JButton btnAccept;
	public setMinimumBalanceGUI ()
	{
		super ("Change Minimum Balance");//calling constructor

		setLayout (null);//absolute layout
		setSize (400, 250);//setting window size
		setResizable(false);//not resizable by the user
		setLocationRelativeTo(null);//center window to screen
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//exit on close of window(admin function)
		getContentPane().setBackground(Color.decode("#7197D9"));

		lblMinBalance = new JLabel("Minimum Balance:");//Initailizing variables
		txtMinBalance = new JTextField(readMinBalance() + "");//Field displays the old min balance for user convenience
		txtMinBalance.addKeyListener(new KeyAdapter() {//When a key is typed in the field, check if the length has exceeded the limit, if so consume it
	        public void keyTyped(KeyEvent e) {
	            if (txtMinBalance.getText().length() >= 14 )
	                e.consume();
	        }
	    });
		btnAccept = new JButton("Change");
		lblMinBalance.setBounds(20,50,200,30);//set size and placement of components
		txtMinBalance.setBounds(150,45,200,35);
		btnAccept.setBounds(140,150, 100, 30);

		add(lblMinBalance);//add to window
		add(txtMinBalance);

		add(btnAccept);
		btnAccept.addActionListener (this);//action listener for when the button is clicked
		setVisible (true);//visible

	}
	public void actionPerformed (ActionEvent e){//when the accept button is clicked
		try{
			FileWriter fw = new FileWriter ("MinimumBalance.txt");//new filewriter to the Minimumbalance text file
			PrintWriter outputFile = new PrintWriter (fw); 
			double minBalance = Double.parseDouble(txtMinBalance.getText());//Parse string into a double
			outputFile.println(minBalance);//print the double onto the file
			outputFile.close(); //close input file
			System.out.println("New Balance: " +  readMinBalance());
			LoginGUI LoginGUI = new LoginGUI();//reopen the login, admin function requires relogin
			dispose();//dispose of window
		}
		catch(Exception b){

		}
	}
	public static double readMinBalance(){//method that reads the old minimum balance off the text file
		try{
			FileReader reader = null;
			reader = new FileReader ("MinimumBalance.txt");//new file reader for the text file
			//Creating another new file reader for the file the user named
			BufferedReader input = new BufferedReader (reader);
			double minBalance = 0;
			minBalance = Double.parseDouble(input.readLine());//parse the string into a double
			input.close();//close reader
			System.out.println(minBalance);
			return minBalance;//return read balance
		}
		catch (Exception e){
			return (double)0;
		}
	}
	public static void main(String[] args) {//self testing main to check if program runs
		setMinimumBalanceGUI setloginGUI = new setMinimumBalanceGUI();
	}
}