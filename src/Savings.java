import java.io.BufferedReader;
import java.io.FileReader;
import java.text.NumberFormat;
/*
 * Author: Sadeem Bilal and Janujan
 * Date: December 1, 2016
 * Description: This program is for the saving class which inherits from the account class. It has a modification to the 
 * 				withdraw method as it has a minimum balance. 
 * Method List: 
 * Savings()//default constructor
 * Savings (Customer owner)//overloaded constructor with owner as a parameter
 * Savings (Customer owner, double balance)//overloaded constructor with owner and balance as parameters
 * boolean withdraw (double amount)//method to withdraw money from the savings account with a fee if balance 
 * 									is lower than minimum balance
 * static double changeMin() //method to read the minimum balance from the file and returns to the private variable
 * static void main(String[] args)//self-testing main
 * 
 * Getter and Setter Methods
 * double getMinBalance() 
 * setMinBalance(double minBalance)
 * double getFee()
 * setFee(double fee) 
 */
public class Savings extends Account{

	//declaring private variable for fee and minimum balance
	private double fee = 2.50;
	private double minBalance = changeMin();
	
	//default constructor
	public Savings(){
		super(); //calls the parent constructor
	}
	//overloaded constructor with owner as a parameter
	public Savings (Customer owner){
		super(owner);
	}
	//overloaded constructor with owner and balance as parameters
	public Savings (Customer owner, double balance){
		super(owner, balance);
	}
	//method to withdraw money from the savings account with a 
	//fee if balance is lower than minimum balances account
	public boolean withdraw (double amount){
		//if balance is less than 2000, add fee to the amount being withdrawn
		if (super.getBalance()<minBalance){
			amount += fee;
			return super.withdraw(amount);
		}
		return super.withdraw(amount);
	}
	//geter and setter methods
	public double getMinBalance() {
		return minBalance;
	}
	public double getFee() {
		return fee;
	}
	public void setMinBalance(double minBalance) {
		this.minBalance = minBalance;
	}
	public void setFee(double fee) {
		this.fee = fee;
	}
	public static double changeMin(){ //method to read minimum balance from 
		try{
			double fileMinBalance = 0;//variable for minimum balance read from file
			FileReader reader = null;//initialize file reader 
			reader = new FileReader ("MinimumBalance.txt");
			BufferedReader input = new BufferedReader (reader);//creating another new file reader for the file the user named
			fileMinBalance  = Double.parseDouble(input.readLine());//store value read into variable
			System.out.println(fileMinBalance);
			return fileMinBalance;
		}
		catch (Exception e) {
			System.out.println("Error"); //display error
			return 2000; //return 2000 for minimum balance if there is an error
		}
	}
	//self-testing main
	public static void main (String args[]){
		NumberFormat f = NumberFormat.getCurrencyInstance(); //currency formatting

		//create Customer object
		Customer c = new Customer ();

		//process the string record into different data pieces (name, address, phone number)
		c.processRecord("Tony Campos,43 Hello World,647-943-2343"); 

		//create a Chequing object with the customer 
		Savings savingsAcct = new Savings(c);

		//set balance to $3000
		savingsAcct.setBalance(3000);

		//display customer information and balance
		System.out.println("Customer Information: " + savingsAcct.getCustomer());
		System.out.println("Balance: " + f.format(savingsAcct.getBalance()));

		//display the current fee
		System.out.println("Fee: " + f.format(savingsAcct.getFee()));

		//--[withdraw Testing]----
		//display true or false if money is able to be withdrawn and display new balance
		System.out.println("Withdraw $100: " + savingsAcct.withdraw(100)); 
		System.out.println("Remaining Balance: " + f.format(savingsAcct.getBalance()));

		//display true or false if money is able to be withdrawn and display new balance
		System.out.println("Withdraw $3000: " + savingsAcct.withdraw(3000));
		System.out.println("Remaining Balance: " + f.format(savingsAcct.getBalance()));

		//--[changeFee and getFee Testing]----
		savingsAcct.setFee(2.50);
		System.out.println("New Fee: " + f.format(savingsAcct.getFee()));

	}//main
}//class
