
// JDBC Example - printing a database's metadata
// Coded by Chen Li/Kirill Petrov Winter, 2005
// Slightly revised for ICS185 Spring 2005, by Norman Jacobson
//ARPAN TAKE CARE OF THE USER PROOFING

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*; // Enable SQL processing
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
	
	static Scanner inp = new Scanner(System.in);
	static boolean exit;
	static String remote = "jdbc:mysql://176.32.230.251/cl57-moviedb";
	static String username;
	static String password;
	static Connection connection = null;
	
	/*----------------------------------------------------------------------------------------------------------------------*/
	public static void main(String[] arg) throws Exception{
		
		/*----------------------------------environment variable initialization-------------------------------------------------*/
		System.out.println("Initializing environment variables...");
		try {
		    Thread.sleep(2000);         //2 seconds
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		exit = false;
		/*----------------------------------------------------------------------------------------------------------------------*/

		 
		jdbcInit();
		
		System.out.println("--->Welcome to PIKFLIX<---"); 
		
		loginToDatabase();

		
		/*---------------------------------------------super loop---------------------------------------------------------------*/
		while(!exit){   
			mainMenu();  
		}
		/*----------------------------------------------------------------------------------------------------------------------*/
		System.out.println("\nbye...");
		
		
	}

	private static void mainMenu(){
		System.out.println("\n\nWhat do you want to do?");
		System.out.println("1. Search movies by actor");
		System.out.println("2. Add a star");
		System.out.println("3. Add a customer");
		System.out.println("4. Delete a customer");
		System.out.println("5. Get metadata");
		System.out.println("6. Run custom SQL query");
		System.out.println("7. Logout");
		System.out.println("8. Exit");
		System.out.println("\nPlease make a choice");
		int choice;
		choice = inp.nextInt();
		switch (choice) {
		case 1: 
			searchmoviesX();
			break;

		case 2:
			//Add a star...
			addstarX();
			break;
			
			
		case 3: 
			//Add a customer...
			addcustomerX();
			break;
		
		
		case 4:
			//Delete a customer...
			deletecustomerX();
			break;
			
			
		case 5:
			//Get metadata...
			getmetadata();
			break;
			
			
		case 6:
			//Custom...
			customqueryX();
			break;
			
			
		case 7:
			break;
			
		case 8:
			exit=true;
			break;
				
		default:
			System.out.println("INVALID OPTION...");
			break;
		}


	}
	
	private static void halt(String e){
		exit=true;
		System.out.println("system halting...");
		System.out.println("Shut down caused by "+e);
	};
	
	private static void loginToDatabase(){
		
		System.out.println("Enter username: "); username = inp.next();
		System.out.println("Password: "); password = inp.next();
		try{
			connection = (Connection)DriverManager.getConnection(remote,username,password);
			System.out.println("Congratulations! You are connected to the Database!!");
		}catch(SQLException e){
			System.out.println("\n"
					+ "***********************************\n"
					+ "FAILED to establish connection\n"
					+ "***********************************\n");
			if(e.getErrorCode()==1045){
				System.out.println("Wrong user name and//or password");
				System.out.println("Try again");
			}
			halt("database connectivity/Login issues.");	
		}
	}
	
	private static void jdbcInit(){
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		}catch (Exception e){
			System.out.println("\n"
					+ "*************************************************\n"
					+ "***com.mysql.jdbc.Driver instantiation FAILED!***\n"
					+ "*************************************************\n");
			halt("JDBC driver failure.");
		}
	}
	
	/*----------------------------------------------------------------------------------------------------------------------*/
	
	private static void addcustomerX(){
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Adding Customer...");
		System.out.println(" Please enter the id: ");
		int id;
		try {
			id = inp.nextInt();
		} catch (InputMismatchException e1) {
			System.out.println("You did not enter a number!!");
			System.out.println("Try again");
			id = inp.nextInt();
		}			
		String[] prompts = {
				"Please enter a first name: ",
				"Please enter a last name: ",
				"Please enter a credit card number: ",
				"Please enter an address: ",
				"Please enter an email id: ",
				"Please enter the customer's password: "
		};
		ArrayList<String> ar = new ArrayList<String>();
		for (String prompt : prompts) {
			System.out.println(prompt);
			try {
				ar.add(br.readLine());
			} catch (IOException e) {
				return;
			}

		}
		Customer customer = new Customer(id,ar.get(0),ar.get(1),ar.get(2),ar.get(3),ar.get(4),ar.get(5));
		try {
			addcustomer(customer.getId(),customer.getFirst_name(),customer.getLast_name(),customer.getCc(),customer
					.getAdd(),customer.getEmail(),customer.getPwd());
		} catch (SQLException e) {
			System.out.println("Could not add customer");
		}
		return;
	}
	private static void addcustomer(int id, String first, String last, String cc, String add, String email, String pwd) throws SQLException {
		 Statement query = connection.createStatement();
		 try{
		 query.executeUpdate("INSERT INTO customers VALUES("
                 + id + ", '"
                 + first + "', '"
                 + last + "', '"
                 + cc + "', '"
                 + add + "','"
                 + email + "','"
                 + pwd + "');");
		 System.out.println("Customer "+first+" "+last+" successfully added");
		 }catch(SQLIntegrityConstraintViolationException e){
			 System.out.println("Credit card does not exist");
		 }catch(SQLSyntaxErrorException e){
			 System.out.println(" Input not recognized");
		 }
	}
	
	//------------------------------------------------
	private static void addstarX(){
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Adding Star...");
		System.out.println(" Please enter the id: ");
		int id;
		try {
			id= inp.nextInt();
		} catch (InputMismatchException e1) {
			System.out.println("You did not enter a number!!");
			System.out.println("Try again");
			id = inp.nextInt();
		}			
		
		String[] prompts = {
				"Please enter a first name: ",
				"Please enter a last name: ",
				"Please enter a dob in YYYY/MM/DD: ",
				"Please enter a photo url: ",
				
		};
		ArrayList<String> ar = new ArrayList<String>();
		for (String prompt : prompts) {
			System.out.println(prompt);
			try {
				ar.add(br.readLine());
			} catch (IOException e) {
				return;
			}

		}
		if(ar.get(0)!="" && ar.get(1)==""){
			ar.set(1, ar.get(0));
			ar.set(0,"");
		}
	
		Star star = new Star(id,ar.get(0),ar.get(1),ar.get(2),ar.get(3));
		try {
			addstar(star.getId(),star.getFirst_name(),star.getLast_name(),star.getDob(),star.getPhotoURL());
		}catch (SQLException e) {
			System.out.println("Could not add star");
			e.printStackTrace();
		}
		return;
	}
	private static void addstar(int id,String first,String last,String dob,String photo) throws SQLException {
		Statement query = connection.createStatement();
		 try{
		 query.executeUpdate("INSERT INTO stars VALUES("
                 + id + ", '"
                 + first + "', '"
                 + last + "', DATE('"
                 + dob + "'), '"
                 + photo + "');");
		 System.out.println("Star "+first+" "+last+" successfully added");
		 }catch(SQLSyntaxErrorException e){
			 System.out.println("Input not recognized");
		 }
	}
	//------------------------------------------------
	private static void deletecustomerX(){
		System.out.println("Deleting customer...");
		System.out.println(" Please enter the id: ");
		int id2;
		try {
			id2= inp.nextInt();
		} catch (InputMismatchException e1) {
			System.out.println("You did not enter a number!!");
			System.out.println("Try again");
			return;
		}	
		
		try {
			deletecustomer(id2);
		} catch (SQLException e) {
			System.out.println("Customer record does not exist: ");
		}
	}
	private static void deletecustomer(int id) throws SQLException {
		Statement update = connection.createStatement();
        update.executeUpdate("delete from customers where id = "+id);
		
	}
	//------------------------------------------------
	private static void getmetadata() {   
        boolean nextTable = false;
        try {
            
            Statement select = connection.createStatement();
            Statement tableQuery = connection.createStatement();
            ResultSet tableList = tableQuery.executeQuery("SHOW TABLES");

            
            while (tableList.next()) {
                if (nextTable) {
                    //pause(); 
                    System.out.println("______________________________________");
                } else {
                    nextTable = true;
                }
                String table = tableList.getString(1);
                System.out.println("\nTABLE: " + table);
                ResultSet result = select.executeQuery("Select * from " + table);
                ResultSetMetaData metadata = result.getMetaData();
                System.out.println("\n" + metadata.getColumnCount() + "= number of columns");
                for (int i = 1; i <= metadata.getColumnCount(); i++) {
                    System.out.println("(" + i + ") " + metadata.getColumnName(i) + " :: " + metadata.getColumnTypeName(i));
                }
                System.out.println("\n______________________________________");
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
	//------------------------------------------------
	private static void searchmoviesX() {
		// TODO Auto-generated method stub
		
	}
	//------------------------------------------------
	private static void customqueryX(){
		System.out.println("enter query");
//		String cmd = inp.next();
//		try {
//			Statement query = connection.createStatement();
//			query.executeQuery(cmd);
//		} catch (SQLException e) {
//			System.out.println(e);
//		}
	}
	

}
