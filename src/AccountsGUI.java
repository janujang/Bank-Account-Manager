import javax.imageio.ImageIO;//Importing Packages needed for jcomponenets and filechoosers
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.URL;
import java.text.NumberFormat;
//Author Justin Hans
//Date Dec, 4
//Desc. GUI that login class loads once a valid login is entered, this GUI allows the user to manage their customers bank accounts and perform various functions
/**method List:
 * AccountsGUI ()
 * void mouseClicked(MouseEvent evt)
 * void actionPerformed(ActionEvent event)
 * static void enableComponenets()
 * static void refresh()
 * void actionPerformed (ActionEvent e)
 * static void deposit(double amount, boolean waccount)
 * static void withdraw(double amount, boolean waccount)
 * static void addCustomer(String record)
 * static void changeCustomer(String newR)
 * Void ActionPerformed (ActionEvent e)
 * Void ItemStateChanged(ItemEvent e)
 * static void main(String[] args) 
 * 
 * Getters and Setters:
 * Static AccountList getaList()
 * void setaList(AccountList aList)
 * static boolean isState()
 * void setState(boolean state)
 * static String getAccountInfo(int num)
 * @author Justin
 *
 */
public class AccountsGUI extends JFrame implements ActionListener//Constructor for AccountsGUI
{//*********** many of the methods have to be static and the variables also because it means that you can call it even though the object hasnt been created(all the other GUI components require accountsGUI to funtion like this
	private static boolean state = false;;//Private boolean for the state of either deposit or withcraw, fundsGUI uses this to determine what to do
	private static boolean doubleClick = false; //private boolean to register double or single click
	private static int index=1;//Private int for the currently selected list element, default of 1 to prevent errors
	private static AccountList aList = new AccountList();//Private aList that contains all the Jlist data

	public static AccountList getaList() {//Returns aList to other GUIS or methods that call it
		return aList;
	}
	public static boolean isState() {//Return the state for deposit/withdraw
		return state;
	}
	public void setState(boolean state) {//Sets the state, used by deposit and withdraw button
		this.state = state;
	}

	static JLabel name,phone,address;//declaring Static components that need their values accesable by other methods in order to function
	JLabel accountOwner,chequingsHeading,savingsHeader;
	static JLabel errorLabel;
	static JButton btnDeposit,btnWithdraw;
	static JList<String> list;
	JList<String> chequingsList,savingsList;
	static JComboBox<String> sortType;//Combobox for the sort by--
	static DefaultListModel listModel,listModel2,listModel3;//ListModels for the Jlists

	//currency number formatting
	static NumberFormat f = NumberFormat.getCurrencyInstance();

	public AccountsGUI ()
	{
		super ("Account Manager");//frame name
		setLayout (null);//absolute layout
		setSize (1070, 650);//Size of frame
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//exit when window is closed
		setLocationRelativeTo(null);//center app to screen
		getContentPane().setBackground(Color.decode("#7197D9"));//apply background colour
		setResizable(false);//Size cannot be changed
		Image image = null;//Instantiating the image
		try {//try and catch, for if the URL is invalid or if the ImageIO cant read the URL picture
			URL url = new URL("http://i.imgur.com/pMYZRb8.png");//New URL for the Logo
			image = ImageIO.read(url);//reading the png image from the Url
		} catch (IOException e) {
			System.out.println("URL INVALID");//If the URL isnt valid then print error
		}
		JLabel label = new JLabel(new ImageIcon(image));//New JLabel containing URL image
		label.setBounds(30, -15, 400, 400);//Setting Bounds top center
		add(label);//adding Label
		errorLabel = new JLabel ("***Load File To Enable Functions***");//Display this so user knows buttons will not be enabled until a file is loaded or customer manually added
		btnDeposit = new JButton ("Deposit");//buttons for fundsGUI functions
		btnWithdraw = new JButton ("Withdraw");
		errorLabel.setFont(new Font("Serif", Font.BOLD, 16));
		name = new JLabel("Name: ");//Initializing all Jcomponents of program
		accountOwner = new JLabel("Customer");
		chequingsHeading=new JLabel("Chequings Account");
		savingsHeader=new JLabel("Savings Account");
		name.setFont(new Font("Serif", Font.PLAIN, 17));//font for headers
		phone = new JLabel("Phone: ");
		phone.setFont(new Font("Serif", Font.PLAIN, 17));
		address = new JLabel ("Address: ");
		address.setFont(new Font("Serif", Font.PLAIN, 17));
		accountOwner.setFont(new Font("Serif", Font.PLAIN, 19));
		accountOwner.setBounds(230, 95, 100, 30);
		chequingsHeading.setFont(new Font("Serif", Font.PLAIN, 18));
		chequingsHeading.setBounds(520, 95, 250, 30);
		savingsHeader.setFont(new Font("Serif", Font.PLAIN, 18));
		savingsHeader.setBounds(780, 95, 200, 30);
		name.setBounds(180, 35, 260, 30);
		errorLabel.setBounds(40, 500, 290, 40);
		phone.setBounds(460, 35, 250, 30);
		btnDeposit.setBounds(400, 500, 100, 35);
		btnWithdraw.setBounds(550, 500, 100, 35);
		address.setBounds(680, 35, 350, 30);
		btnDeposit.addActionListener (this);//action listeners for when clicked on
		btnWithdraw.addActionListener (this);
		add(savingsHeader);
		add(name);
		add(chequingsHeading);
		add(accountOwner);
		add(address);
		add(phone);
		add(errorLabel);
		add(btnDeposit);
		add(btnWithdraw);
		btnDeposit.setEnabled(false);//buttons start not enabled until file loaded
		btnWithdraw.setEnabled(false);

		JSplitPane splitPane = new JSplitPane();//new split pane for left side customers right side is another split pane with each account type on either side
		splitPane.setOneTouchExpandable(true);//expandable with button click on expand arrows
		splitPane.setResizeWeight(0.4);//start at resize ratio of 40%
		splitPane.setForeground(Color.BLACK);
		splitPane.setBounds(100, 125, 850, 350);//set size of splitpane
		add(splitPane);//add it

		listModel = new DefaultListModel<String>();//new list model for customer name list
		list = new JList<String>(listModel);;
		JScrollPane scrollPane = new JScrollPane();//scroll pane for the customer name list
		scrollPane.setViewportView(list);
		splitPane.setLeftComponent(scrollPane);
		splitPane.setDividerLocation(350);//placing where the movable divider is placed
		list.addMouseListener(new MouseAdapter() {//Mouse listener for when the user clicks on a list item, 1 click shows corresponding attribute in next jlist, 2 clicks shows account information
			public void mouseClicked(MouseEvent evt) {
				JList list = (JList)evt.getSource();//variable for selected attribute
				if (evt.getClickCount() == 2) {//If jlist is double clicked
					doubleClick = true; 
					if(list.getSelectedValue()!= null){//If something valid is clicked
						listModel2.clear();//clear both account lists and populate them with information about each account retrieved from aList
						listModel3.clear();
						listModel2.addElement("Account Number: " + getaList().getList()[list.getSelectedIndex()].getChequingAccountNum());
						listModel2.addElement("Account Fee Per Transaction: $1.50");
						listModel2.addElement("Account Balance: "+ f.format(getaList().getList()[list.getSelectedIndex()].getChequingBalance()));
						listModel3.addElement("Account Number: " + getaList().getList()[list.getSelectedIndex()].getSavingsAccountNum());
						listModel3.addElement("Account Below Limit Fee: $2.50");
						listModel3.addElement("Account Min Balance: "+ f.format(Savings.changeMin()));
						listModel3.addElement("Account Balance: "+ f.format(getaList().getList()[list.getSelectedIndex()].getSavingsBalance()));
						System.out.println("Clicked 2 times on " + list.getSelectedValue());
					}
				} else if (evt.getClickCount() == 1) {//upon single click, highlight corresponding jlist data
					if(list.getSelectedValue()!= null){//if selection is valid
						doubleClick = false;
						name.setText("Name: "+(String) list.getSelectedValue());
						phone.setText("Phone: "+ getaList().getList()[list.getSelectedIndex()].getCustomerPhoneNum());
						address.setText("Address: "+ getaList().getList()[list.getSelectedIndex()].getCustomerAddress());
						listModel2.clear();//clear both account lists and populate them with information about each account retrieved from aList
						listModel3.clear();
						listModel2.addElement("Account Balance: "+ f.format(getaList().getList()[list.getSelectedIndex()].getChequingBalance()));
						listModel3.addElement("Account Balance: "+ f.format(getaList().getList()[list.getSelectedIndex()].getSavingsBalance()));
						//chequingsList.setSelectedIndex(list.getSelectedIndex());//Highlist corresponding element in other lists
						//savingsList.setSelectedIndex(list.getSelectedIndex());
						System.out.println("Clicked 1 times on " + list.getSelectedValue());
					}
				}
			}
		});
		String[] sortOptions = new String[] {"Sort By--","Name Ascending", "Name Descending", "Savings High-Low","Savings Low-High", "Chequings High-Low","Chequings Low-High"};//array for options to sort by

		sortType = new JComboBox<String>(sortOptions);//New drop down combo box populated with array options
		sortType.setBounds(800, 500, 150, 35);
		sortType.setEnabled(false);
		add(sortType);
		sortType.addActionListener(new ActionListener() {//action listener for when a combobox item is selected
			@Override
			public void actionPerformed(ActionEvent event) {
				JComboBox<String> combo = (JComboBox<String>) event.getSource();//Get the box at which was selected
				String selectedSort = (String) combo.getSelectedItem();
				if (selectedSort.equals("Name Ascending")) {//Sorts by corresponding type to what the user selected, uses an insertion sort for all objects in aList
					aList.insertionSort("Up", "Name");
					refresh();//refresh the Jlist
					System.out.println("Name Ascending");
				} else if (selectedSort.equals("Name Descending")) {
					aList.insertionSort("Down", "Name");
					refresh();//refresh the Jlist
					System.out.println("Name Descending");
				}
				else if (selectedSort.equals("Savings High-Low")) {
					aList.insertionSort("Down", "savings balance");
					refresh();//refresh the Jlist
					System.out.println("Savings high- low");
				}
				else if (selectedSort.equals("Savings Low-High")) {
					aList.insertionSort("Up", "savings balance");
					refresh();//refresh the Jlist
					System.out.println("Savings low-high");
				}
				else if (selectedSort.equals("Chequings High-Low")) {
					aList.insertionSort("Down", "chequing balance");
					refresh();//refresh the Jlist
					System.out.println("Savings high- low");
				}
				else if (selectedSort.equals("Chequings Low-High")) {
					aList.insertionSort("Up", "chequing balance");
					refresh();//refresh the Jlist
					System.out.println("Savings low-high");
				}

			}
		});

		JSplitPane splitPane_1 = new JSplitPane();//adding another split pane into the right side of the first split pane
		splitPane_1.setOneTouchExpandable(true);
		splitPane_1.setResizeWeight(0.5);
		splitPane.setRightComponent(splitPane_1);

		listModel2 = new DefaultListModel();//new list and list model that will hold the savings or chequings account balances
		chequingsList = new JList<String>(listModel2);
		JScrollPane scrollPane2 = new JScrollPane();//scroll pane for the jlist
		scrollPane2.setViewportView(chequingsList);//set the scrollpane onto the JList
		splitPane_1.setLeftComponent(scrollPane2);//add the scrollpane to the spitpane

		listModel3 = new DefaultListModel();//new list and list model that will hold the savings or chequings account balances
		savingsList = new JList<String>(listModel3);
		JScrollPane scrollPane3 = new JScrollPane();//scroll pane for the jlist
		scrollPane3.setViewportView(savingsList);//set the scrollpane onto the JList
		splitPane_1.setRightComponent(scrollPane3);//add the scrollpane to the spitpane

		JMenuBar menubar = new JMenuBar();//new menubar that comtains the 4 submenus

		JMenu file = new JMenu("File");//creating a new submenu that contains more options 
		JMenuItem exitMenu = new JMenuItem("Exit");
		exitMenu.setToolTipText("Exit application");//when hovered over it displays....
		exitMenu.addActionListener(this);//action listener for when the menubar item is clicked
		JMenuItem logoutMenu = new JMenuItem("Logout");
		logoutMenu.setToolTipText("Logout");
		logoutMenu.addActionListener(this);
		JMenuItem loadMenu = new JMenuItem("Load File");
		loadMenu.setToolTipText("Load a system file");
		loadMenu.addActionListener(this);
		JMenuItem saveMenu = new JMenuItem("Save File");
		saveMenu.setToolTipText("Save your workspace");
		saveMenu.addActionListener(this);
		file.add(loadMenu);//add all the options to the submenu
		file.add(saveMenu);
		file.add(logoutMenu);
		file.add(exitMenu);
		menubar.add(file);//add the submenu to the menu, REPEATS FOR ALL SUBMENUS

		JMenu settings = new JMenu("Admin");
		JMenuItem changeloginMenu = new JMenuItem("Change Login");
		changeloginMenu.setToolTipText("Change Login Password/Username");
		changeloginMenu.addActionListener(this);
		JMenuItem changeminimumMenu = new JMenuItem("Change Minimum Fees");
		changeminimumMenu.setToolTipText("Change System Minimum Fees");
		changeminimumMenu.addActionListener(this);
		settings.add(changeloginMenu);
		settings.add(changeminimumMenu);
		menubar.add(settings);

		JMenu edit = new JMenu("Edit");
		JMenuItem changeinfoMenu = new JMenuItem("Change Info");
		changeinfoMenu.setToolTipText("Change Users Personal Information");
		changeinfoMenu.addActionListener(this);
		JMenuItem addcustomerMenu = new JMenuItem("Add Customer");
		addcustomerMenu.setToolTipText("Add a Customer to the System");
		addcustomerMenu.addActionListener(this);
		JMenuItem removecustomerMenu = new JMenuItem("Remove Customer");
		removecustomerMenu.setToolTipText("Remove Current Customer From System");
		removecustomerMenu.addActionListener(this);
		edit.add(changeinfoMenu);
		edit.add(addcustomerMenu);
		edit.add(removecustomerMenu);
		menubar.add(edit);

		JMenu help = new JMenu("Help");
		JMenuItem helpMenu = new JMenuItem("User Manual");
		helpMenu.setToolTipText("Open User Readme");
		helpMenu.addActionListener(this);
		help.add(helpMenu);
		menubar.add(help);

		setJMenuBar(menubar);
		setVisible (true);//set GUI visible
	}

	public static void enableComponenets(){//Once a valid file is loaded or customer is added, it will enable all disabled J components
		errorLabel.setVisible(false);
		sortType.setEnabled(true);
		btnDeposit.setEnabled(true);
		btnWithdraw.setEnabled(true);
	}

	public static void refresh(){//refreshes the visible list which updates the values according to the changes user has made
		listModel.clear();//clear all the lists
		listModel2.clear();
		listModel3.clear();
		for (int i = 0; i<getaList().getSize();i++){//repopulate the Jlists with the aList values
			listModel.addElement(getaList().getList()[i].getCustomerName());//add each element of the object to each list element

			//listModel2.addElement("$"+ getaList().getList()[i].getChequingBalance());
			//listModel3.addElement("$"+ getaList().getList()[i].getSavingsBalance());
		}
		if (index>=0){//if it refreshes and a valid item was selected before hand
			name.setText("Name: "+getaList().getList()[index].getCustomerName());//set the name phone address, to the same index of data, updated, or same
			phone.setText("Phone: "+ getaList().getList()[index].getCustomerPhoneNum());
			address.setText("Address: "+ getaList().getList()[index].getCustomerAddress());
		}
		errorLabel.setVisible(false);//after refreshing, a valid command must have went through, so the error label doesnt need to be visible
	}

	public static void deposit(double amount, boolean whichAccount){//method to deposit to selected account
		if(whichAccount){//if its true, deposit to chequings
			aList.getList()[index].getChequingAcct().deposit(amount);//get indexed account and deposit into it
			refresh();//refresh list
			refreshBalances();//reference balances

		}
		else{//else (false) depesit to savings
			aList.getList()[index].getSavingsAcct().deposit(amount);//get indexed account and deposit into it
			refresh();//refresh
			refreshBalances();//reference balances	
		}
	}
	public static void withdraw(double amount, boolean waccount){//method to withdraw from selected account
		if(waccount){//if true then withdraw from chequings
			aList.getList()[index].getChequingAcct().withdraw(amount);//get index account and withdraw from it
			refresh();//refresh list
			refreshBalances();//reference balances	
		}
		else{//if (false) then withdrawm from savings
			aList.getList()[index].getSavingsAcct().withdraw(amount);//get index account and withdraw from it
			refresh();//refresh list
			refreshBalances();//reference balances

		}
	}
	public static void refreshBalances(){
		if (doubleClick){
			listModel2.addElement("Account Number: " + getaList().getList()[index].getChequingAccountNum());
			listModel2.addElement("Account Fee Per Transaction: $1.50");
			listModel2.addElement("Account Balance: "+ f.format(getaList().getList()[index].getChequingBalance()));
			listModel3.addElement("Account Number: " + getaList().getList()[index].getSavingsAccountNum());
			listModel3.addElement("Account Below Limit Fee: $2.50");
			listModel3.addElement("Account Min Balance: "+ f.format(Savings.changeMin()));
			listModel3.addElement("Account Balance: "+ f.format(getaList().getList()[index].getSavingsBalance()));
			list.setSelectedIndex(index);//Highlist corresponding element in other lists
		}
		else{
			listModel2.addElement("Account Balance: "+ f.format(getaList().getList()[index].getChequingBalance()));
			listModel3.addElement("Account Balance: "+ f.format(getaList().getList()[index].getSavingsBalance()));
			list.setSelectedIndex(index);//Highlist corresponding element in other lists
		}
	}
	public static void addCustomer(String record){//method to add a customer to the aList
		AccountRecord aInfo = new AccountRecord();//new record
		aInfo.processRecord(record);//process the entered record
		aList.insert(aInfo);//insert the record into the list
		refresh();//refresh the jlists
	}
	public static void changeCustomer(String newR){//method to replace customers old info with new info
		String oldR = (aList.getList()[index].toString());//old record is the selected index that was clicked on
		AccountRecord oldInfo = new AccountRecord();//new records for each one
		AccountRecord newInfo = new AccountRecord();
		oldInfo.processRecord(oldR); //process both records
		newInfo.processRecord(newR); 
		aList.bubbleSort();//bubble sort the records(needed to change them)
		aList.change(oldInfo, newInfo);//swap them
		refresh();//refresh the jlists
	}

	public void actionPerformed (ActionEvent e){//when either a button or a jmenu item is clicked
		if (e.getSource()==btnDeposit)//if it is the deposit button
		{
			index = list.getSelectedIndex();//if a index is selected and not -1
			if (index>=0){
				setState(true);//set state to true
				FundsGUI fundsGUI = new FundsGUI();//create a GUI to deposit
			}
			else{
				errorLabel.setText("***Select Element In List***");//if a index isnt selected show error
				errorLabel.setVisible(true);
			}
		}
		else if (e.getSource()==btnWithdraw){//when the with draw button is pressed
			index = list.getSelectedIndex();//get the index, and if its valid(not -1)
			if (index>=0){
				setState(false);//set the boolean state to false(withdraw mode)
				FundsGUI fundsGUI = new FundsGUI();//new funds GUI
			}
			else{
				errorLabel.setText("***Select Element In List***");//if the index is invalid then display error
				errorLabel.setVisible(true);
			}
		}

		else if (e.getActionCommand()=="Exit"){//if exit is chosen, exit program
			System.exit(1);  
		}
		else if(e.getActionCommand()=="Logout")//if logout is chosen dispose of current window and create a new login window
		{
			dispose();
			LoginGUI loginGUI= new LoginGUI();
		}
		else if(e.getActionCommand()=="Load File")//if load file is chosen
		{
			try{
				enableComponenets();//enable all the components since a valid file is being chosen
				aList.setSize(0);//set the lsit size to 0 to get rid of all old elements if you loaded a list 2 times
				final JFileChooser fc = new JFileChooser();//create file chooser
				int returnVal = fc.showOpenDialog(AccountsGUI.this);//open the filechooser menu and choose file
				if (returnVal == JFileChooser.APPROVE_OPTION) {//if option is valid then get the directory and set it to the file field
					File file = fc.getCurrentDirectory();//get directory of file
					System.out.println(file.getCanonicalPath()+"//"+fc.getSelectedFile().getName());
					String fileName = (file.getCanonicalPath()+"//"+fc.getSelectedFile().getName());//set file name to the path of the file
					getaList().loadFileIntoList(fileName);//load the information from the file into the list
					String text ="";
					NumberFormat f = NumberFormat.getCurrencyInstance();//format for the account balances
					refresh();//repopulate them with the new values
//					for (int i = 0; i<getaList().getSize();i++){//print out in console a string of all the values read, error debugging purposes
//						text += "Record #" + (i) + "\t" +  getaList().getList()[i].getCustomerName() + "\t" 
//								+ getaList().getList()[i].getCustomerAddress() + "\t" 
//								+ getaList().getList()[i].getCustomerPhoneNum() + "\t"
//								+ getaList().getList()[i].getChequingAccountNum() + "\t"
//								+ getaList().getList()[i].getSavingsAccountNum() + "\t"
//								+ f.format(getaList().getList()[i].getChequingBalance()) + "\t"
//								+ f.format(getaList().getList()[i].getSavingsBalance()) + "\n";
//					}
//					System.out.println(text);
				} 
			}
			catch (Exception a)
			{
				System.out.println ("Error Loading File");//if any files are not valid then print error
			}
			System.out.println("Load File");
		}
		else if(e.getActionCommand()=="Save File")//save file chosen
		{
			try{
				final JFileChooser fc = new JFileChooser();//create file chooser
				int returnVal = fc.showOpenDialog(AccountsGUI.this);//open the filechooser menu and choose file
				if (returnVal == JFileChooser.APPROVE_OPTION) {//if option is valid then get the directory and set it to the file field
					File file = fc.getCurrentDirectory();//get current directory of file
					System.out.println(file.getCanonicalPath()+"//"+fc.getSelectedFile().getName());
					String filename =(file.getCanonicalPath()+"//"+fc.getSelectedFile().getName());//set file name to the selected file path
					getaList().writeToFile(filename);//write the accountList to the chosen file
					errorLabel.setText("File Saved");
					errorLabel.setVisible(true);
				}
				else 
				{
				}
			}
			catch (Exception a)
			{
				System.out.println ("error");//if cant write to file print error
				errorLabel.setVisible(true);
			}

			System.out.println("Save file");
		}
		else if(e.getActionCommand()=="Change Login")//if change login selected
		{
			setLoginGUI setloginGUI = new setLoginGUI();//new change login GUI, disposes of this window because it is a admin function, makes you relogin
			System.out.println("Change Login");
			dispose();
		}
		else if(e.getActionCommand()=="Change Minimum Fees")//if change min fees selected
		{
			setMinimumBalanceGUI setloginGUI = new setMinimumBalanceGUI();//new min balance GUI, admin function so it disposes of this program and makes you re login
			System.out.println("Change Minimum Fees");
			dispose();
		}
		else if(e.getActionCommand()=="Change Info")//if its change the info of a customer
		{
			index = list.getSelectedIndex();
			if(index>=0){	//if the selected index is valid and not(-1)
				changeCustomerGUI changecustomerGUI = new changeCustomerGUI();//new GUI to change the customer
			}
			else{
				errorLabel.setText("***Select Element In List***");//if a index isnt selected
				errorLabel.setVisible(true);
			}
			System.out.println("Change Info");
		}
		else if(e.getActionCommand()=="Add Customer")//If add customer is selected
		{
			enableComponenets();//add customer enables components because it means the list will be populated
			addCustomerGUI addcustomerGUI = new addCustomerGUI();//add customer GUI
			System.out.println("Add Customer");
		}
		else if(e.getActionCommand()=="Remove Customer")//If remove custoemr is selected
		{
			index = list.getSelectedIndex();
			if(index>=0){	//if a valid index is selected and not -1
				String record = (aList.getList()[index].toString());//String is the record selected that you wish to delete
				getaList().bubbleSort();//bubble sort the entire accountlist
				AccountRecord aInfo = new AccountRecord();//create a new record
				aInfo.processRecord(record);//process the record you wish to delete
				aList.delete(aInfo);//delete the record
				refresh();//refresh the Jlists with the removed value
			}
			else{
				errorLabel.setText("***Select Element In List***");//if valid index isnt selected
				errorLabel.setVisible(true);
			}
			System.out.println("Remove Customer");
		}
		else if(e.getActionCommand()=="User Manual")//if usermanual is selected
		{
			ProcessBuilder openHelp = new ProcessBuilder("Notepad.exe", "Manual.txt");//new process builder that opens the Manual.txt with the notepad program
			try {
				openHelp.start();//open the text document
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println("manual");
		}
	}
	public static String getAccountInfo(int num){//method for the change customer to get the old info, so that the user can see what infromation they are changing
		if (num==1){//based on the int given, return various indexed parameters of the record
			return getaList().getList()[index].getCustomerName();
		}
		else if (num==2){
			return getaList().getList()[index].getCustomerAddress();
		}
		else if (num==3){
			return getaList().getList()[index].getCustomerPhoneNum();
		}
		else if (num==4){
			return Double.toString(getaList().getList()[index].getChequingBalance());
		}
		else if (num==5){
			return Double.toString(getaList().getList()[index].getSavingsBalance());
		}
		else{
			return "Could Not Retrieve";//if given invalid integer
		}
	}

	public static void main(String[] args) {//create a new accountsGUi for selftesting
		AccountsGUI LoginGUI = new AccountsGUI();
	}

}