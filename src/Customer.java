/**
 * 
 */
import javax.swing.*;
/**
 *Author: Sadeem Bilal
 * Date: December 1, 2016
 * Description: This program is for the customer class. It creates a customer with a name, address and a phone number. 
 * 				Given a record string, it could break the string into pieces of data.
 * 
 * Method List:
 * Customer() //default constructor
 * Customer(String name, String address, String phoneNum) * setName(String name) {//getters and setter for name, address, and phoneNum
 * processRecord(String record)//method to process records by splitting string into different data pieces
 * String toString()
 * static void main(String[] args)//self-testing main
 * 
 * Getter and Setter Methods
 * setAddress(String address)
 * setPhoneNum(String phoneNum)
 * getName() 
 * getAddress()
 * getPhoneNum()
 */
public class Customer {

	//declaring private variable for name, address and phone number
	private String name;
	private String address; 
	private String phoneNum;

	//default constructor
	public Customer(){
		//initializing variables 
		this.name = "";
		this.address = "";
		this.phoneNum = "";
	}
	//overloaded constructor with name, address and phone number as parameters
	public Customer(String name, String address, String phoneNum)
	{
		//constructor initializes the client information with provided information
		this.name = name;
		this.address = address;
		this.phoneNum = phoneNum;
	}
	//getters and setter for name, address, and phoneNum
	public void setName(String name){
		this.name = name;
	}
	public void setAddress(String address){
		this.address = address;
	}
	public void setPhoneNum(String phoneNum){
		this.phoneNum = phoneNum;
	}
	public String getName(){
		return name;
	}
	public String getAddress(){
		return address;
	}
	public String getPhoneNum(){
		return phoneNum;
	}
	//method to process records by splitting string into different data pieces
	public void processRecord(String record){ 
		String word[];
		word = record.split(",");
		this.name = word[0];
		this.address = word[1];
		this.phoneNum = word[2];
	}
	//method to return processed information in terms of name, address and phone number
	public String toString(){
		return this.name + "," + this.address + "," + this.phoneNum;
	}
	//self-testing main
	public static void main(String[] args){
		//variable for record 
		String record; 

		//set record to a string with name, address and phone number
		record = "Janujan G,43 Hello World,647-943-2343";

		//create Customer object using default constructor
		Customer cInfo = new Customer ();

		//--[processRecord Testing]----
		//process the record and display the name, address and phone number
		cInfo.processRecord(record);
		System.out.println("Processed Record: " + cInfo.toString());

		//--[Getter Methods Testing]-------
		//display the name, address and phone number individually
		System.out.println("Name: " + cInfo.getName());
		System.out.println("Address: "+cInfo.getAddress());
		System.out.println("Phone#: "+cInfo.getPhoneNum());

		//create Customer object using overloaded constructor for name, address and phone number
		Customer cInfo2 = new Customer ("Justin Hanss","213 Tulip Dr.","123-421-3213");
		System.out.println("Processed Record: " + cInfo2.toString());

		//--[Setter Methods Testings]---
		cInfo2.setName("Tony Campos");
		cInfo2.setAddress("123 Drive");
		cInfo2.setPhoneNum("213-341-1241");

		//display new processed record
		System.out.println("New Processed Record: " + cInfo2.toString());
		//display the name, address and phone number individually
		System.out.println("Name: " + cInfo2.getName());
		System.out.println("Address: "+cInfo2.getAddress());
		System.out.println("Phone#: "+cInfo2.getPhoneNum());

	}//end main
}//end class
