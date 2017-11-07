import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.text.NumberFormat;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/*
 * Author: Janujan
 * Date: December 4, 2016
 * Description: This program creates a list of account records and allows the user 
 * 				to view the list, change, insert or delete account records. Moreover, it also allows the user to deposit and withdraw money to 
 * 				a selected account. 
 * Method List: 
 * AccountList()//default constructor
 * AccountList(int maxSize)//overloaded constructor with maxSize
 * boolean insert (AccountRecord record)//method to insert new record 
 * boolean delete (AccountRecord record)//method to delete record 
 * boolean change (AccountRecord oldR, AccountRecord newR) //method to change a specified AccountRecord 
 * void swap (int first, int second) //method to swap two elements of an array
 * void bubbleSort() //method for sorting in ascending order in terms of customer name
 * void insertionSort (String sortOrder, String sortType) //method for insertion sort algorithm which sorts based on sort order and sort type
 * int binarySearch(String searchKey)//method for binary search for customer name
 * void loadFileIntoList (String fileName) throws IOException //method to load from file into list array
 * void writeToFile (String fileName) throws IOException //method to write list onto file
 * static void main(String[] args)//self-testing main
 * 
 * Getter and Setter Methods
 * int getSize()
 * AccountRecord[] getList()
 * void setMaxSize(int maxSize)
 * void setSize(int size)
 */
public class AccountList {
	//private variables for max size, current size and array of account records
	private int maxSize;
	private int size;
	private AccountRecord list[];

	//default constructor
	public AccountList(){
		this.maxSize = 50;
		this.size = 0;
		this.list = new AccountRecord [maxSize];
	}
	//overloaded constructor with maxSize
	public AccountList(int maxSize){
		this.maxSize = maxSize;
		this.size = 0;
		this.list = new AccountRecord [maxSize];
	}
	//getter and setter methods for size and list
	public int getSize(){ 
		return size;
	}
	public AccountRecord[] getList(){
		return list;
	}
	public void setMaxSize(int maxSize){
		this.maxSize = maxSize;
	}
	public void setSize(int size){
		this.size = size;
	}
	//method to insert new record 
	public boolean insert (AccountRecord record){
		//check if there is space to add
		if (size<maxSize){ 
			size++;
			list[size-1] = record;
			return true;
		}
		return false;
	}
	//method to delete record 
	public boolean delete (AccountRecord record){
		//get location of the record using the binary search method
		int location = binarySearch(record.getCustomerName());

		//swap the particular record with the last element
		if (location>=0){
			list[location] = list[size-1];
			size--;
			bubbleSort();
			return true;
		}
		return false;	
	}
	//method to change a specified AccountRecord 
	public boolean change (AccountRecord oldR, AccountRecord newR){
		//if able to delete old record
		if (delete(oldR)){ 
			insert(newR); //add new record
			bubbleSort();
			return true; 
		}
		return false;
	}
	//method to swap two elements of an array
	public void swap (int first, int second){
		//temporary AccountRecord variable
		AccountRecord hold;
		hold = list[first];
		list[first] = list[second]; //swap order
		list[second] = hold;		
	}
	//method for bubble sort in ascending order of customer name
	public void bubbleSort(){
		//loop to control number of passes
		for (int pass = 0; pass< size - 1;pass++){
			//loop to control the number of comparisons
			//every time it goes through whole array, take out the last pass (shortens bubble sort)
			for (int element = 0; element< size-pass-1;element++){
				//compare side-by-side elements and swap them if second element's account customer name
				//is not in alphabetical order
				if (list[element].getCustomerName().compareToIgnoreCase(list[element+1].getCustomerName())>0){
					swap (element, element+1);
				}//if
			}//comparisons
		}//passes
	}	
	public void insertionSort (String sortOrder, String sortType) //method for insertion sort algorithm which sorts based on sort order and sort type
	{
		for (int top = 1; top<size;top++){
			AccountRecord aTemp = list[top]; 
			int i = top;

			//general functionality: checks previous element to see if greater or less than in alphabetical order and swaps
			if (sortOrder.equalsIgnoreCase("up")){ //ascending order
				if (sortType.equalsIgnoreCase("name")){
					while (i>0 && aTemp.getCustomerName().compareToIgnoreCase(list[i-1].getCustomerName()) < 0){
						list[i] = list[i-1];
						i--;
					}
					list[i]=aTemp;	
				}
				else if (sortType.equalsIgnoreCase("address")){
					while (i>0 && aTemp.getCustomerAddress().compareToIgnoreCase(list[i-1].getCustomerAddress()) < 0){
						list[i] = list[i-1];
						i--;
					}
					list[i]=aTemp;
				}
				else if (sortType.equalsIgnoreCase("phone")){
					while (i>0 && aTemp.getCustomerPhoneNum().compareToIgnoreCase(list[i-1].getCustomerPhoneNum()) < 0){
						list[i] = list[i-1];
						i--;
					}
					list[i]=aTemp;
				}
				else if (sortType.equalsIgnoreCase("account number")){
					while (i>0 && aTemp.getChequingAccountNum()<list[i-1].getChequingAccountNum()){
						list[i] = list[i-1];
						i--;
					}
					list[i]=aTemp;
				}
				else if (sortType.equalsIgnoreCase("chequing balance")){
					while (i>0 && aTemp.getChequingBalance()<list[i-1].getChequingBalance()){
						list[i] = list[i-1];
						i--;
					}
					list[i]=aTemp;
				}
				else if (sortType.equalsIgnoreCase("savings balance")){
					while (i>0 && aTemp.getSavingsBalance()<list[i-1].getSavingsBalance()){
						list[i] = list[i-1];
						i--;
					}
					list[i]=aTemp;
				}
			}
			else{ //descending order
				if (sortType.equalsIgnoreCase("name")){
					while (i>0 && aTemp.getCustomerName().compareToIgnoreCase(list[i-1].getCustomerName()) > 0){
						list[i] = list[i-1];
						i--;
					}
					list[i]=aTemp;	
				}
				else if (sortType.equalsIgnoreCase("address")){
					while (i>0 && aTemp.getCustomerAddress().compareToIgnoreCase(list[i-1].getCustomerAddress()) > 0){
						list[i] = list[i-1];
						i--;
					}
					list[i]=aTemp;
				}
				else if (sortType.equalsIgnoreCase("phone")){
					while (i>0 && aTemp.getCustomerPhoneNum().compareToIgnoreCase(list[i-1].getCustomerPhoneNum()) > 0){
						list[i] = list[i-1];
						i--;
					}
					list[i]=aTemp;
				}
				else if (sortType.equalsIgnoreCase("account number")){
					while (i>0 && aTemp.getChequingAccountNum()>list[i-1].getChequingAccountNum()){
						list[i] = list[i-1];
						i--;
					}
					list[i]=aTemp;
				}
				else if (sortType.equalsIgnoreCase("chequing balance")){
					while (i>0 && aTemp.getChequingBalance()>list[i-1].getChequingBalance()){
						list[i] = list[i-1];
						i--;
					}
					list[i]=aTemp;
				}
				else if (sortType.equalsIgnoreCase("savings balance")){
					while (i>0 && aTemp.getSavingsBalance()>list[i-1].getSavingsBalance()){
						list[i] = list[i-1];
						i--;
					}
					list[i]=aTemp;
				}//end else if
			}//end else
		}//end for
	}//end method
	//method for binary search for customer name
	public int binarySearch(String searchKey){
		bubbleSort();

		//set bottom and top of array
		int low = 0, high = size-1, middle;

		while (low<=high){
			middle = (high+low)/2; //divide array in 2
			if (searchKey.equalsIgnoreCase(list[middle].getCustomerName())){
				return middle; //return the location of the account 
			}
			else if (searchKey.compareToIgnoreCase(list[middle].getCustomerName())<0){//narrow the search - searchKey before the middle
				high = middle-1; //change upper boundary
			}
			else{
				low = middle+1; //change lower boundary
			}
		}
		return -1; 
	}
	//method to load from file into list array
	public void loadFileIntoList (String fileName) throws IOException { 

		//----[Variable Declaration]-------
		String line = ""; //variable to store the lines 
		String aRecord[]; //declare String array to return
		int lineCount = 0; //variable to store line count
		//---------------------------------

		//open file for reading
		FileReader fr = new FileReader (fileName);
		BufferedReader inputFile = new BufferedReader (fr);

		line = inputFile.readLine();//reads first line.

		// loop to count the number of lines in the file
		while (line != null) {//while not a null character
			lineCount++;
			line = inputFile.readLine();
		}
		inputFile.close(); //close input file

		// re-open the file to read
		fr = new FileReader (fileName);
		inputFile = new BufferedReader (fr);

		aRecord = new String [lineCount]; //declare and create a string array

		//loop through array to store records
		for (int j = 0; j < aRecord.length; j++) {
			aRecord[j] = inputFile.readLine();
			//process each record and insert into list array
			AccountRecord accountInfo = new AccountRecord();
			accountInfo.processRecord(aRecord[j]);
			insert(accountInfo);
		}
		inputFile.close(); //close input file
		//return 
	}
	//method to write list onto file
	public void writeToFile (String fileName) throws IOException { 
		//open file for reading
		FileWriter fw = new FileWriter (fileName);
		PrintWriter outputFile = new PrintWriter (fw); //from grade 11 unit 5 examples

		//loop through array to store records
		for (int j = 0; j < size; j++) {
			outputFile.println(list[j]);
		}
		outputFile.close(); //close input file
	}
	//self-testing main
	public static void main(String[] args) {
		//create an object of the accountList class
		AccountList accountList = new AccountList();

		//variable for selected option
		String selectedOption = ""; 

		//array to hold the different options
		String [] options = {"Insert", "Print", "Delete", "Change", "Sort", "Load From File", "Binary Search", "Write to File", 
				"Deposit", "Withdraw", "Quit"};

		while(true) //infinite while loop
		{
			//prompt for the option (task to perform) from a drop-down menu
			selectedOption = (String) JOptionPane.showInputDialog (null, "Choose a task to perform", 
					"Task", JOptionPane.PLAIN_MESSAGE, null, options, "Insert");

			if (selectedOption.equals("Quit")) //quit program
			{
				break;
			}
			//switch case to perform an action depending on which button was pressed
			switch(selectedOption)
			{
			case "Insert": //insert element
			{
				//variable for the record
				String record = JOptionPane.showInputDialog(null, "Enter, <Name>,<Address>,<Phone Number>,<Account Number>,"
						+ "<Chequing Balance>,<Savings Balance>", "Sadeem Bilal,34 Glen Dr,647-832-3233;1000;3000");

				record += ";0;0";

				//AccountRecord object
				AccountRecord aInfo = new AccountRecord();

				//separate record into different portions of data
				aInfo.processRecord(record);

				//insert vehicle record if there is space
				if (!accountList.insert(aInfo)){
					JOptionPane.showMessageDialog(null, "Item not added");
				}
				else{
					JOptionPane.showMessageDialog(null, "Success");
				}
				break;
			}
			case "Print": //print list
			{
				NumberFormat f = NumberFormat.getCurrencyInstance(); //currency formatting

				//text area and scroller for output
				JTextArea display = new JTextArea(20,90);
				JScrollPane scroller = new JScrollPane(display);
				String text = "";

				text += "Record #" + "\t" + "Customer Name" + "\t" + "Customer Address" + "\t" + "Customer Phone #"
						+ "\t" + "Chequing Account #" + "\t" + "Savings Account #" + "\t" + "Chequing Balance" + "\t" 
						+ "Savings Balance" + "\n";

				//loop through the size of the list array and display all information
				for (int i = 0; i<accountList.getSize();i++){
					text += "Record #" + (i) + "\t" +  accountList.getList()[i].getCustomerName() + "\t" 
							+ accountList.getList()[i].getCustomerAddress() + "\t" 
							+ accountList.getList()[i].getCustomerPhoneNum() + "\t"
							+ accountList.getList()[i].getChequingAccountNum() + "\t"
							+ accountList.getList()[i].getSavingsAccountNum() + "\t"
							+ f.format(accountList.getList()[i].getChequingBalance()) + "\t"
							+ f.format(accountList.getList()[i].getSavingsBalance()) + "\n";
				}//for

				//set the text of the output to the text string and display editing
				display.setText(text); 

				//set tab size to 15 and disable editing
				display.setTabSize(15);
				display.setEditable(false);

				//display the output
				JOptionPane.showMessageDialog(null, scroller,null,JOptionPane.PLAIN_MESSAGE);
				break;
			}
			case "Change": //change element
			{
				//variable for old record and new record
				String oldR = JOptionPane.showInputDialog(null, "Old Record: Enter <Name>,<Address>,<Phone Number>,<Account Number>,"
						+ "<Chequing Balance>,<Savings Balance>", 
						"Sadeem Bilal,34 Glen Dr,647-832-3233;323323232;1000;3000");

				String newR= JOptionPane.showInputDialog(null, "New Record: Enter Name>,<Address>,<Phone Number>,<Account Number>,"
						+ "<Chequing Balance>,<Savings Balance>", 
						"Janujan Gathieswaran,34 Glen Dr,647-832-3233;323323232;10000;35000");

				//variable for old and new info
				AccountRecord oldInfo = new AccountRecord();
				AccountRecord newInfo = new AccountRecord();

				//process old and new records and put into array of data portions
				oldInfo.processRecord(oldR); 

				newInfo.processRecord(newR); 

				accountList.bubbleSort(); //sort in ascending alphabetical order

				if (accountList.change(oldInfo, newInfo)){
					JOptionPane.showMessageDialog(null, "Success and sorted alphabetically.");
				}
				else{
					JOptionPane.showMessageDialog(null, "Fail");
				}
				break;
			}
			case "Delete": //delete element
			{
				//sort list in alphabetical order in terms of vehicle make 
				accountList.bubbleSort();

				//variable for record
				String record = JOptionPane.showInputDialog(null, "Enter Name>,<Address>,<Phone Number>,<Account Number>,"
						+ "<Chequing Balance>,<Savings Balance>", 
						"Sadeem Bilal,34 Glen Dr,647-832-3233;323323232;1000;3000");

				//AccountRecord object
				AccountRecord aInfo = new AccountRecord();

				aInfo.processRecord(record); //put it into array of data

				if (!accountList.delete(aInfo)){
					JOptionPane.showMessageDialog(null, "Item not found");
				}
				else{
					JOptionPane.showMessageDialog(null, "Success and sorted alphabetically.");
				}
				break;
			}
			case "Sort":
			{
				//variable for selected sort order
				String selectedSortOrder = ""; 

				//variable for selected sort type
				String selectedSortType = ""; 

				//array to hold the different sort order options
				String [] sortOrder = {"Up", "Down"};

				//array to hold the different sort type options
				String [] sortType = {"Name", "Address", "Phone", "Account Number", "Chequing Balance", "Savings Balance"};

				//prompt for the sort order from a drop-down menu
				selectedSortOrder = (String) JOptionPane.showInputDialog (null, "Choose sort order", 
						"Sort Order", JOptionPane.PLAIN_MESSAGE, null, sortOrder, "Up");

				//prompt for the sort type from a drop-down menu
				selectedSortType = (String) JOptionPane.showInputDialog (null, "Choose sort type", 
						"Sort Type", JOptionPane.PLAIN_MESSAGE, null, sortType, "Name");

				//call insertionSort to sort in terms of selected sort order and type
				accountList.insertionSort(selectedSortOrder, selectedSortType);

				//display that sort was successful
				JOptionPane.showMessageDialog(null, "Insertion Sort Sucessful");
				break;
			}
			case "Load From File":
			{
				//variable for file name to open 
				String fileName = JOptionPane.showInputDialog(null, "Enter the file name you would like to read "
						+ "from", "AccountRecord.txt");

				//try-catch to display an error if file not loaded correctly
				try{
					accountList.loadFileIntoList(fileName);
					JOptionPane.showMessageDialog(null, "File loaded successfully");
				}
				catch (FileNotFoundException e){
					JOptionPane.showMessageDialog (null, "File not loaded successfully");
				}
				catch (IOException e){
					JOptionPane.showMessageDialog (null, "File corrupted");
				}
				catch (Exception e){
					JOptionPane.showMessageDialog (null, "Error");
				}
				break;
			}
			case "Binary Search": //binary search
			{
				//sort in ascending order alphabetically before performing binary search
				accountList.bubbleSort();

				//variable for search key
				String searchKey = JOptionPane.showInputDialog(null, "Enter a make to search", "Toyota");

				//variable for location of item
				int location = accountList.binarySearch(searchKey);

				if (location >=0){
					JOptionPane.showMessageDialog(null, searchKey + "found at location " + location + 
							"\nDetailed Information: " + accountList.getList()[location].toString());
				}
				else{
					JOptionPane.showMessageDialog(null, searchKey + " not found. Try again.");
				}
				break;
			}
			case "Deposit":
			{
				//prompt for customer name
				String customerName = JOptionPane.showInputDialog(null, "Enter the customer's name");

				//variables for different account types
				String [] accountType = {"Chequing", "Savings"};

				//prompt user for the account using a drop-down menu
				String selectedAccount = (String) JOptionPane.showInputDialog (null, "Choose the account type", 
						"Account Type", JOptionPane.PLAIN_MESSAGE, null, accountType, "Chequing");

				//prompt the user an amount to deposit 
				double amount = Double.parseDouble(JOptionPane.showInputDialog(null,"Enter an amount to deposit"));

				//get the location of the particular customer 
				int location = accountList.binarySearch(customerName);

				//debugging point
				//System.out.println(location);
				//if customer wasn't found, display an error
				if (location<0){
					JOptionPane.showMessageDialog(null, "Customer not found.",null,JOptionPane.ERROR_MESSAGE);
				}
				else {
					//deposit money to chosen account
					if (selectedAccount.equalsIgnoreCase("chequing")){
						accountList.getList()[location].getChequingAcct().deposit(amount);
					}
					else{
						accountList.getList()[location].getSavingsAcct().deposit(amount);
					}
				}
				break;
			}
			case "Withdrawal":
			{
				//prompt for customer name
				String customerName = JOptionPane.showInputDialog(null, "Enter the customer's name");

				//variables for different account types
				String [] accountType = {"Chequing", "Savings"};

				//prompt user for the account using a drop-down menu
				String selectedAccount = (String) JOptionPane.showInputDialog (null, "Choose the account type", 
						"Account Type", JOptionPane.PLAIN_MESSAGE, null, accountType, "Chequing");

				//prompt the user an amount to withdraw 
				double amount = Double.parseDouble(JOptionPane.showInputDialog(null,"Enter an amount to withdraw"));

				//get the location of the particular customer 
				int location = accountList.binarySearch(customerName);

				//if customer wasn't found, display an error
				if (location<0){
					JOptionPane.showMessageDialog(null, "Customer not found.",null,JOptionPane.ERROR_MESSAGE);
				}
				else{
					//withdraw money from chosen account
					if (selectedAccount.equalsIgnoreCase("chequing")){
						accountList.getList()[location].getChequingAcct().withdraw(amount);
					}
					else{
						accountList.getList()[location].getSavingsAcct().withdraw(amount);
					}
				}
				break;
			}
			case "Write to File":
			{
				//variable for file name to open 
				String fileName = JOptionPane.showInputDialog(null, "Enter the file name you would like to write to "
						, "AccountRecord.txt");

				//try-catch to display particular errors
				try{
					accountList.writeToFile(fileName);
					JOptionPane.showMessageDialog(null, "File written successfully");
				}
				catch (FileNotFoundException e){
					JOptionPane.showMessageDialog (null, "File not loaded successfully");
				}
				catch (IOException e){
					JOptionPane.showMessageDialog (null, "File corrupted");
				}
				catch (Exception e){
					JOptionPane.showMessageDialog (null, "Error");
				}

				break;
			}
			}
		}//while

		//exit the program
		System.exit(0);
	}//end main
}//end class
