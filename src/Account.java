import java.text.NumberFormat;
import java.util.Random;
/*
 * Author: Janujan Gathieswaran
 * Date: December 1, 2016
 * Description: This program is for the account class which keeps track of the customer's balance, account number and allows
 *                              the user to deposit, withdraw and update the balance. 
 * Method List: 
 * Account (Customer theOwner)//default constructor
 * Account (Customer theOwner, double balance)//another constructor with owner and balance as parameters
 * deposit (double amount)//method to deposit money
 * withdraw (double amount) //method to withdraw money only when there is sufficient balance
 * String toString()//method to return all processed information
 * static void main(String[] args)//self-testing main
 * 
 * Getter and Setters
 * getCustomer() 
 * getAccountNumber() 
 * getBalance ()
 * setBalance(double balance) 
 * generateAcctNum () //generates an account number
 * setCustomer(Customer customer)
 * setAcctNum(int newAccountNum)
 */
public class Account
{
	//private class data for balance, account number and customer
	private double balance;
	private long accountNum;
	private Customer customer;

	//Default constructor
	/* initializes the balance
	 * creates an account number
	 * initializes a customer object
	 */
	public Account (){
		this.balance = 0;
		generateAcctNum();
		this.customer = new Customer();
	}
	//Overloaded constructor with customer as a parameter
	/* initializes balance
	 * generates an account number and initializes customer object theOwner
	 */
	public Account (Customer theOwner){
		this.balance = 0;
		generateAcctNum();
		this.customer = theOwner;
	}
	//overloaded constructor with customer and balance as parameters
	public Account (Customer theOwner, double balance){
		this.balance = balance;
		generateAcctNum();
		this.customer = theOwner;
	}
	//method to deposit money into balance
	public void deposit (double amount){
		this.balance += amount;
	}
	//method to withdraw money from balance
	/*
	 * Checks if the amount can be withdraw and returns true if it is possible
	 * updates balance
	 */
	public boolean withdraw (double amount){
		if ((balance - amount) >0){
			this.balance -= amount;
			return true;
		}
		return false;
	}
	//method to generate account number
	public void generateAcctNum (){
		//Reference to generate random 9 digit numbers
		//http://stackoverflow.com/questions/5392693/java-random-number-with-given-length
		Random rnd = new Random(); //used to generate random numbers

		//generate random numbers from 100000000 to 999999999
		this.accountNum = 100000000 + rnd.nextInt(900000000);

		/*basic functionality of random
		 * starts from 0 to 5 for rnd.nexInt(6)
		 * to start from 1, 1 must added to the rnd.nextInt(6)
		 * */

		/*to count from 100000000 to 999999999
		 * rnd.nextInt(999999999) won't work as it'll generate a number from 0 to 999999998
		 * adding 100000000 + rnd.nextInt(999999999) would yield 100000000 to 1,099,999,998
		 * so instead, subtract 999999999 - 100000000 and add 1 to get correct range*/
		//System.out.println(accountNum); //debug point for account number
	}
	//getter and setter methods
	public Customer getCustomer(){
		return customer;
	}
	public double getBalance (){
		return balance;
	}
	public long getAccountNumber(){
		return accountNum;
	}
	public void setAcctNum(long acctNum){
		this.accountNum = acctNum;
	}
	public void setBalance(double balance){
		this.balance = balance;
	}
	public void setCustomer(Customer customer){
		this.customer = customer;
	}
	//method to return all processed info
	public String toString(){ 
		return customer.getName() + "," + customer.getAddress() + "," + customer.getPhoneNum() + "," + this.balance + "," + this.accountNum;
	}
	//Self-testing Main
	public static void main (String args[]){
		NumberFormat f = NumberFormat.getCurrencyInstance(); //currency formatting
		//create an Account object
		Account account = new Account(); //testing for default constructor
		//create a Customer object
		Customer c = new Customer("Sadeem Bilal","43 Georgian Rd","905-842-2343");

		//--[generateAcctNum Testing]-----
		account.generateAcctNum();
		System.out.println("Account #: " + account.getAccountNumber()); //getAccountNumber Testing

		//--[deposit Testing]-----
		account.deposit(2000);
		System.out.println("New Balance: " + f.format(account.getBalance())); //getBalance testing

		//--[withdraw Testing]-----
		System.out.println("Withdraw $3000: " + account.withdraw(3000));
		System.out.println("New Balance: " + f.format(account.getBalance()));

		//--[setCustomer and toString Testing]-----
		account.setCustomer(c);
		System.out.println("Processed Information: " + account.toString()); //toString testing

		//--[getCustomer Testing]
		System.out.println("Get Customer Information: " + account.getCustomer());
	}
}
