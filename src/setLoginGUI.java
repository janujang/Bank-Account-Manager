import javax.swing.*;//Importing packages needed for the GUI j components
import java.awt.*;
import java.awt.event.*;
import java.io.*;
//Author Justin Hans
//Date Dec, 4
//Desc. GUI that is a admin function GUI that closes all other main GUIS to aloow the user to change the login, changing the login will require a user to relogin
/**Method List
 * setLoginGUI ()
 * void actionPerformed (ActionEvent e)
 * static void main(String[] args)
 * @author Justin
 *
 */
public class setLoginGUI extends JFrame implements ActionListener
{
	JTextField username, password;//Declaring variables for components
	JLabel user, pass;
	JButton btnaccept;
	public setLoginGUI ()
	{
		super ("Change Login");//calling constructor

		setLayout (null);//absolute layout
		setSize (400, 250);//set size of window
		setResizable(false);//not resizable by user
		setLocationRelativeTo(null);//center it to the screen
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//on exit the program closes
		getContentPane().setBackground(Color.decode("#7197D9"));//background colour

		user = new JLabel("Username:");//initailizing all variable
		pass = new JLabel("Password:");
		username = new JTextField(LoginGUI.readlogin());//settting the text in the fields as the current login
		password = new JTextField(LoginGUI.readpass());
		btnaccept = new JButton("Change");
		user.setBounds(20,25,100,30);//set size and placement of components
		pass.setBounds(20,90,100,30);
		btnaccept.setBounds(140,150, 100, 30);
		username.setBounds(90,20,250,35);
		password.setBounds(90,85,250,35);

		add(username);//add them to the window
		add(user);
		add(pass);
		add(password);
		add(btnaccept);
		btnaccept.addActionListener (this);//action lisener for the ok button
		setVisible (true);//visible

	}
	public void actionPerformed (ActionEvent e){//when the ok butotn is clicked
		try{
			FileWriter fw = new FileWriter ("login.txt");//create a new fielwriter for the login.txt
			PrintWriter outputFile = new PrintWriter (fw); 

			outputFile.println(username.getText());//print on one line the user and the next line the pass
			outputFile.println(password.getText());
			outputFile.close(); //close input file
			LoginGUI LoginGUI = new LoginGUI();//create the login GUI(after a admin function you must relogin)
			dispose();//close this window
		}
		catch(Exception b){

		}
	}
	public static void main(String[] args) {//selftesting that creactes the GUI
		setLoginGUI setloginGUI = new setLoginGUI();
	}
}