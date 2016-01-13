

//JOEL TOOK CARE OF THE USER PROOFING!!
//ARPAN _ SEARCH MOVIES
//pRACHI_SEARCH MOIVES
//JOEL _ JUST WROTE THE CUSTOM QUERY FUNCTION>>>WORKS LIKE A CHARM!!!!!

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*; // Enable SQL processing
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
	
	private static Scanner inp = new Scanner(System.in);
	private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	private static boolean exit;
	private static String remote = "jdbc:mysql://176.32.230.251/cl57-moviedb";
	private static String local = "jdbc:mysql://localhost:3306/moviedb";
	private static String aws = "jdbc:mysql://172-31-19-179:3306/moviedb";
	private static String username;
	private static String password;
	private static Connection connection = null;
	private static boolean loggedin;
	
	/*----------------------------------------------------------------------------------------------------------------------*/
	public static void main(String[] arg) throws Exception{
		
		/*----------------------------------environment variable initialization-------------------------------------------------*/
		System.out.println("Initializing environment variables...");
		try {
		    Thread.sleep(1000);         //1 seconds
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		System.out.print(".");
		try {
		    Thread.sleep(1000);         //1 seconds
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		System.out.print(".");
		try {
		    Thread.sleep(1000);         //1 seconds
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		System.out.print(".");

		
		
		exit = false;
		loggedin = false;
		/*----------------------------------------------------------------------------------------------------------------------*/

		System.out.println("\n--->Welcome to PIKFLIX<---\n"); 
//		jdbcInit();
		
		
		
		while(!loggedin && !exit){
			loginToDatabase();
		}
		
		/*---------------------------------------------super loop---------------------------------------------------------------*/
		while(!exit){
			while(!loggedin && !exit){
				loginToDatabase();
			}
			mainMenu();
//			try{
//				mainMenu();
//			}catch(InputMismatchException e){
//				System.out.println("Please enter a valid option!");
//			}
			
		}
		/*----------------------------------------------------------------------------------------------------------------------*/
		System.out.println("\nbye...");
		
		
	}

	private static void mainMenu() throws Exception{
		System.out.println("\n\nWhat do you want to do?");
		System.out.println("1. Search movies by actor");
		System.out.println("2. Add a star");
		System.out.println("3. Add a customer");
		System.out.println("4. Delete a customer");
		System.out.println("5. Get metadata");
		System.out.println("6. Run custom SQL query");
		System.out.println("7. Logout");
		System.out.println("8. Exit");
		System.out.println("\nPlease make a choice: Enter your option Number");
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
			custom();
			break;
			
			
		case 7:
			Logout();
			break;
			
		case 8:
			//Exiting the program;
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
		System.out.println("Please try again!!");
	};
	
	private static void loginToDatabase(){
		
		System.out.println("Enter username: "); username = inp.next();
		System.out.println("Password: "); password = inp.next();
		try{
			connection = (Connection)DriverManager.getConnection(local,username,password);
			System.out.println("Congratulations! You are connected to the Database!!");
			loggedin = true;
		}catch(SQLException e){
			System.out.println("\n"
					+ " ----------------------------------\n"
					+"|   FAILED to establish connection  |\n"
					+ " ----------------------------------\n");
			
			e.printStackTrace();
			if(e.getErrorCode()==1045){
				System.out.println("Wrong user name and/or password");
				System.out.println("Try again? <y/n> ");
				String ans = inp.next();
				if(ans.compareToIgnoreCase("Y")==0){
					return;
				}else{
					exit=true;
					return;
				}
				
			}else{
				halt("database connectivity issues.");
			}					
		}
	}
	
//	private static void jdbcInit(){
//		try{
//			Class.forName("com.mysql.jdbc.Driver").newInstance();
//		}catch (Exception e){
//			System.out.println("\n"
//					+ " ----------------------------------\n"
//					+"|JDBC Driver initialization failure |\n"
//					+ " ----------------------------------\n");
//			halt("JDBC driver failure.");
//		}
//	}
//	
	/*----------------------------------------------------------------------------------------------------------------------*/
	private static void addcustomerX(){
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
		try {
			addcustomer(id,ar.get(0),ar.get(1),ar.get(2),ar.get(3),ar.get(4),ar.get(5));
		} catch (SQLException e) {
			System.out.println("Could not add customer");
			return;
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
	
		try {
			addstar(id,ar.get(0),ar.get(1),ar.get(2),ar.get(3));
		}catch (SQLException e) {
			System.out.println("Could not add star");
//			e.printStackTrace();
			return;
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
		 }catch(SQLException e1){
//			 e1.printStackTrace();
			 System.out.println("Data input error.\nTry again...");
		 }
	}
	//------------------------------------------------
	//------------------------------------------------
	private static void deletecustomerX(){
		System.out.println("Deleting customer...");
		System.out.println(" Please enter the id: ");
		int id;
		try {
			id= inp.nextInt();
		} catch (InputMismatchException e) {
			System.out.println("You did not enter a number!!");
			System.out.println("Try again");
			return;
		}	
		
		try {
			deletecustomer(id);
		} catch (SQLException e) {
			System.out.println("Customer record does not exist: ");
		}
	}
	private static void deletecustomer(int id) throws SQLException {
		Statement update = connection.createStatement();
        update.executeUpdate("delete from customers where id = "+id);
		
	}
	//------------------------------------------------
	//------------------------------------------------
	private static void getmetadata() {   
//        boolean nextTable = false;
        try {
            
            Statement query = connection.createStatement();
            Statement primaryquery = connection.createStatement();
            ResultSet res = primaryquery.executeQuery("SHOW TABLES");
            System.out.println("------------------------------------------------");	
            while (res.next()) {
                
                String table = res.getString(1);
                System.out.println("\nTABLE: " + table);
                ResultSet result = query.executeQuery("Select * from " + table);
                ResultSetMetaData metadata = result.getMetaData();
                System.out.println("\n" + "There are "+ metadata.getColumnCount()+ " in this table.");
                for (int i = 1; i <= metadata.getColumnCount(); i++) {
                    System.out.println( i + ". " + metadata.getColumnName(i) + " of type " + metadata.getColumnTypeName(i));
                }
                System.out.println("------------------------------------------------");	
            }
            System.out.println();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Cannot get database metadata...");
            System.out.println("Try again");
            return;
        }
    }
	//------------------------------------------------
	//------------------------------------------------
	private static void searchmoviesX() {
		// TODO Auto-generated method stub
		
	}
	//------------------------------------------------
	private static void customquery(String custom){
		try {
			Statement query = connection.createStatement();
			ResultSet result = null;
			ResultSetMetaData metadata = null;
			result = query.executeQuery(custom);
			metadata = result.getMetaData();
			int n = metadata.getColumnCount();
			while(result.next()){
				System.out.println("Query Details : ");
				for (int i =1;i<=n;i++){
					System.out.println(i+".) "+metadata.getColumnName(i)+ " -> "+ result.getString(i));
				}
				System.out.println("\n\n");
				
			}
		}catch(SQLSyntaxErrorException e){
			System.out.println("SQL syntax error. Please review your SQL statement again");
			System.out.println("The documentation and reference for MySQL : http://dev.mysql.com/doc/refman/5.7/en/ ");
			return;
		}
		catch (SQLException e){
			System.out.println("SQL exception");
			e.printStackTrace();
			return;
		}
		
	}
	private static void customqueryupdate(String custom){
		try {
			Statement update = connection.createStatement();
			int k = update.executeUpdate(custom);
			System.out.println("Total modified column count: "+k);
		}catch(SQLSyntaxErrorException e){
			System.out.println("SQL syntax error. Please review your SQL statement again");
			System.out.println("The documentation and reference for MySQL : http://dev.mysql.com/doc/refman/5.7/en/ ");
			return;
		}
		catch (SQLException e) {
			e.printStackTrace();
			return;
		}
	}
	private static void custom(){
		System.out.println("Enter Query");
		try {
			String custom = br.readLine();
			String arr[] = custom.split(" ", 2);
			if(arr[0].compareToIgnoreCase("select")==0){
				customquery(custom);
			}
			else if (arr[0].compareToIgnoreCase("update")==0 || arr[0].compareToIgnoreCase("insert")==0 || arr[0].compareToIgnoreCase("delete")==0){
				customqueryupdate(custom);
			}
			else {
				System.out.println("SQL query not recognized");
				return;
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error while parsing SQL statement");
			return;
		}
	}
	//------------------------------------------------
	private static void Logout(){
		try {
			connection.close();
			loggedin = false;
			System.out.println("Logging out... \n\n\n");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Unable to release resources");
			System.out.println("killing whole program as an alternative please start the program again");
			halt("");
		}
	}
}

