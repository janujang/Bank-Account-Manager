import java.text.NumberFormat;

/*
 * Author: Janujan
 * Date: December 4, 2016
 * Description: This account records program  stores a particular user's chequing and savings account information as well as customer information. 
 * Method List: 
 * AccountRecord()//default constructor 
 * void processRecord (String record)//method to split record into different data pieces and set chequing and savings account information 
 * String toString()//method to return the processed information
 * static void main(String[] args)//self-testing main
 * 
 * Getter Methods
 * String getCustomerName()
 * String getCustomerAddress()
 * String getCustomerPhoneNum()
 * long getChequingAccountNum()
 * long getSavingsAccountNum()
 * double getChequingBalance()
 * double getSavingsBalance()
 * Chequing getChequingAcct()
 * Savings getSavingsAcct()
 */
public class AccountRecord {

	//private class data for chequing account and saving account
	private Chequing chequingAcct;
	private Savings savingAcct;

	//default constructor 
	public AccountRecord(){
		//create chequing and savings accounts with null data
		this.chequingAcct = new Chequing (null);
		this.savingAcct = new Savings (null);
	}
	//method to split record into different data pieces and set chequing and savings account information 
	public void processRecord (String record){
		//variables for chequing and savings account number, chequing and savings balance and word array
		long chequingAcctNum = 0, savingsAcctNum = 0;
		double chequingBalance = 0, savingsBalance = 0;
		String phrase [];

		//split the record into an array of strings for every semi-colon
		phrase = record.split(";");

		//create a customer object and process the first phrase into different data pieces
		Customer c = new Customer();
		c.processRecord(phrase[0]);

		//assign different parts of the phrase to different variables
		chequingBalance = Double.parseDouble(phrase[1]);
		savingsBalance = Double.parseDouble(phrase[2]);
		chequingAcctNum = Long.parseLong(phrase[3]);
		savingsAcctNum = Long.parseLong(phrase[4]);

		//create a chequing and savings account with the customer information and balance, which would also generate a number
		this.chequingAcct = new Chequing(c,chequingBalance);
		this.savingAcct = new Savings(c,savingsBalance);

		//if the account numbers of both the chequing and savings account are not 0, set the account number to values read from file
		if (chequingAcctNum != 0 && savingsAcctNum !=0){
			this.chequingAcct.setAcctNum(chequingAcctNum);
			this.savingAcct.setAcctNum(savingsAcctNum);
		}
	}
	//method to return the processed information
	public String toString(){
		//return the string
		return (this.chequingAcct.getCustomer().getName() + "," + this.chequingAcct.getCustomer().getAddress() + "," 
				+ this.chequingAcct.getCustomer().getPhoneNum() + ";" + this.chequingAcct.getBalance()+ ";"
				+ this.savingAcct.getBalance()) + ";" + this.chequingAcct.getAccountNumber()+ ";" 
				+ this.savingAcct.getAccountNumber();
	}
	//Getter Methods
	public String getCustomerName(){
		return chequingAcct.getCustomer().getName();
	}
	public String getCustomerAddress(){
		return chequingAcct.getCustomer().getAddress();
	}
	public String getCustomerPhoneNum(){
		return chequingAcct.getCustomer().getPhoneNum();
	}
	public long getChequingAccountNum(){
		return chequingAcct.getAccountNumber();
	}
	public long getSavingsAccountNum(){
		return savingAcct.getAccountNumber();
	}
	public double getChequingBalance(){
		return chequingAcct.getBalance();
	}
	public double getSavingsBalance(){
		return savingAcct.getBalance();
	}
	public Chequing getChequingAcct(){
		return chequingAcct;
	}
	public Savings getSavingsAcct(){
		return savingAcct;
	}
	//self-testing main
	public static void main(String[] args){
		NumberFormat f = NumberFormat.getCurrencyInstance(); //currency formatting
		
		//variable for record 
		String record  = "Sadeem Bilal,34 Glen Dr,647-832-3233;1000;3000;323323232;234233234";

		//create an AccountRecord object
		AccountRecord aRecord = new AccountRecord();

		//--[Process Record Testing]----
		//process the record
		aRecord.processRecord(record);
		
		//display the processed record
		System.out.println("Processed Record:" + aRecord.toString());

		//--[Getter Method Testing]----
		System.out.println("Customer Name: " + aRecord.getCustomerName());
		System.out.println("Customer Address: " + aRecord.getCustomerAddress());
		System.out.println("Customer Phone Number: " + aRecord.getCustomerPhoneNum());
		System.out.println("Chequings Account Number: " + aRecord.getChequingAccountNum());
		System.out.println("Savings Account Number: " + aRecord.getSavingsAccountNum());
		System.out.println("Chequings Account Balance: " + f.format(aRecord.getChequingBalance()));
		System.out.println("Savings Account Balace: " + f.format(aRecord.getSavingsBalance()));

	}//main
}//class
