import java.text.NumberFormat;

/*
 * Author: Janujan Gathieswaran
 * Date: December 1, 2016
 * Description: This program is for the chequing class, which inherits from the account class with a modification to the 
 * 				withdrawal method and one extra variable for fee. 
 * Method List:
 * Chequing()//default constructor 
 * Chequing(Customer owner)//overloaded constructor with customer as parameter
 * Chequing(Customer owner, double balance)//overloaded constructor with customer and balance as parameters
 * static void main(String[] args)//self-testing main
 * 
 * Getters and Setters
 * void setFee (double newFee)//method to change the fee
 * double getFee ()//method to get the fee
 */
public class Chequing extends Account
{
	//private variable for the fee for transactions
	private double fee = 1.50;

	//default constructor to create chequing account
	public Chequing(){
		super(); //calls the parent constructor
	}
	//overloaded constructor with customer as parameter
	public Chequing(Customer owner){
		super(owner);
	}
	//overloaded constructor with customer and balance as parameters
	public Chequing(Customer owner, double balance){
		super(owner, balance);
	}
	//method to withdraw along with a fee for every transaction
	/*
	 * checks if balance is sufficient
	 * returns true if successful and false if not
	 */
	public boolean withdraw (double amt){
		amt += this.fee; //add fee to the amount
		return super.withdraw(amt); //call the withdraw method from the account class with amount
	}
	//method to change the fee
	public void setFee (double newFee){
		this.fee = newFee;
	}
	//method to get the current fee
	public double getFee (){
		return fee;
	}
	//self-testing main 
	public static void main (String args[]){
		NumberFormat f = NumberFormat.getCurrencyInstance(); //currency formatting

		//create Customer object
		Customer c = new Customer ();
		//process the string record into different data pieces (name, address, phone number)
		c.processRecord("Sadeem Bilal,43 Hello World,647-943-2343"); 

		//create a Chequing object with the customer 
		Chequing chequingAcct = new Chequing(c);

		//set balance to $3000
		chequingAcct.setBalance(3000);

		//display customer information and balance
		System.out.println("Customer Information: " + chequingAcct.getCustomer());
		System.out.println("Balance: " + f.format(chequingAcct.getBalance()));
		
		//display the current fee
		System.out.println("Fee: " + f.format(chequingAcct.getFee()));
		
		//--[withdraw Testing]----
		//display true or false if money is able to be withdrawn and display new balance
		System.out.println("Withdraw $100: " + chequingAcct.withdraw(100)); 
		System.out.println("Remaining Balance: " + f.format(chequingAcct.getBalance()));
		
		//display true or false if money is able to be withdrawn and display new balance
		System.out.println("Withdraw $3000: " + chequingAcct.withdraw(3000));
		System.out.println("Remaining Balance: " + f.format(chequingAcct.getBalance()));
		
		//--[changeFee and setFee Testing]----
		chequingAcct.setFee(2.50);
		System.out.println("New Fee: " + f.format(chequingAcct.getFee()));
	}
}
