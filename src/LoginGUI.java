import javax.imageio.ImageIO;//Importing all elements needed for constructor and fields 
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.*;
/*
 * Author: Janujan
 * Date: December 4, 2016
 * Description: GUI To login into the account management program, once a valid login is entered it loads the AccountsGUI
/* Method List:
 * LoginGUI ()
 * Void ActionPerformed (ActionEvent e)
 * Void ItemStateChanged(ItemEvent e)
 * static String readpass()
 * static String readlogin()
 * static void main(String[] args) 
 * Getters and Setters:
 * String getLogin()
 * int getAttempts()
 * void setAttempt()
 * String getPass()
 *
 */
public class LoginGUI extends JFrame implements ActionListener
{
	private String user= readlogin();//Private variables for username, login and such that read from the file
	private String loginNum =readpass();
	private int loginAttempts = 4;//Login attempts, goes down after each wrong attempt, 5 in total
	JTextField loginUser;//declaring J components variables used to create the loginGUI
	JPasswordField loginPass;
	JLabel loginField, passField;
	JButton btnLogin;
	JCheckBox showPassword;
	public LoginGUI ()
	{
		super ("Login");
		setLayout (null);//Calling constructor, and setting layout to absolute(Null), and the size
		setSize (800, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//Cose when you press X
		setLocationRelativeTo(null);//Center the app to the middle of screen
		getContentPane().setBackground(Color.decode("#7197D9"));//Set the background colour
		setResizable(false);//Window stays at contant size, cant be resized by user
		Image image = null;//Instantiating the image
		try {//try and catch, for if the URL is invalid or if the ImageIO cant read the URL picture
			URL url = new URL("http://i.imgur.com/3MnuLaj.png");//New URL for the Logo
			image = ImageIO.read(url);//reading the png image from the Url
		} catch (IOException e) {
			System.out.println("URL INVALID");//If the URL isnt valid then print error
		}
		JLabel label = new JLabel(new ImageIcon(image));//New JLabel containing URL image
		label.setBounds(210, 60, 400, 400);//Setting Bounds top center
		add(label);//adding Label


		btnLogin = new JButton ("Login");//Initializing the J components
		loginField = new JLabel ("Username:");
		passField = new JLabel ("Password:");
		loginUser= new JTextField();
		loginPass = new JPasswordField();
		btnLogin.setBounds (325, 375, 150, 30);//Setting bounds of each component
		loginField.setBounds(210,240, 100, 40);
		passField.setBounds(210,300, 100, 40);
		loginUser.setBounds(275, 240, 240, 40);
		loginPass.setBounds(275,300,240, 40);
		showPassword = new JCheckBox("Show Password", false);//New check box, default set to unchecked
		loginPass.setEchoChar('�');//When anything s typed in box, display this char
		showPassword.setBounds(335,340,150,30);//set bounds
		showPassword.setBackground(Color.decode("#7197D9"));//Match to background
		add(showPassword);//add each j component
		add(btnLogin);
		add(passField);
		add(loginField);
		add(loginUser);
		add(loginPass);
		btnLogin.addActionListener (this);//Listener for the Button
		showPassword.addItemListener(new ItemListener() {//Item listener, whener the state of the button is changed,  based on what it is either show the password or set the echo to a diff character
			public void itemStateChanged(ItemEvent e) {         
				loginPass.setEchoChar((e.getStateChange()==1?(char) 0:'�'));//based on state change which char is showed in field
			}           
		});
		setVisible (true);//Set visible of the Frame
	}

	public void actionPerformed (ActionEvent e){//If the login button is pressed
		System.out.println("logging in");
		if(loginUser.getText().equals(getLogin()) && (Arrays.equals(loginPass.getPassword(), getPass().toCharArray())) && getAttempts()>0){//If the login fields and password fields are equal to the saved ones and the attempts are still valid
			System.out.println("accepted");
			AccountsGUI MainGUI= new AccountsGUI();//Create a new Main GUI
			dispose();//Dispose of window
		}
		else if(getAttempts()==0){//If the attempts are out, then set the fields to say so and disable all of them
			loginUser.setText("Out Of Attempts");
			loginPass.setText("");
			loginUser.setEditable(false);//Set all fields to non editable
			loginPass.setEditable(false);
			btnLogin.setEnabled(false);
			showPassword.setEnabled(false);
			System.out.println("Denied");
		}
		else{
			setAttempt();//Add attempt if pass or user is wrong but attempt # is still valid
			loginUser.setText("");
			loginPass.setText("");
			System.out.println("Denied");
			System.out.println(getAttempts());//print out for error purposes
		}
	}
	public String getLogin(){//Return username
		return this.user;
	}
	public int getAttempts(){//Return attempts
		return this.loginAttempts;
	}
	public void setAttempt(){//add a attempt
		this.loginAttempts--;
	}
	public String getPass(){//Get the passoword
		return this.loginNum;
	}
	public static String readlogin(){//Read login user from text file
		try{
			FileReader reader = null;//new file reader
			reader = new FileReader ("login.txt");
			//Creating another new file reader for the file the user named
			BufferedReader input = new BufferedReader (reader);
			String user ="";//read first line and return it to variable after closing stream
			user = input.readLine();
			input.close();
			System.out.println(user);
			return user;
		}
		catch (Exception e){

		}
		return "";//incase reader has error
	}
	public static String readpass(){
		try{
			FileReader reader = null;//new file reader
			reader = new FileReader ("login.txt");
			//Creating another new file reader for the file the user named
			BufferedReader input = new BufferedReader (reader);
			input.readLine();//read second line and return it to variable after closing stream

			String pass="";

			pass = input.readLine();

			System.out.println(pass);
			return pass;
		}
		catch (Exception e) {

		}
		return "";//Incase reader has error
	}

	public static void main(String[] args) {//Main method for self testing
		LoginGUI LoginGUI = new LoginGUI();
	}
}
