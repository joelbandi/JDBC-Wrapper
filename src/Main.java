
// JDBC Example - printing a database's metadata
// Coded by Chen Li/Kirill Petrov Winter, 2005
// Slightly revised for ICS185 Spring 2005, by Norman Jacobson
//ARPAN TAKE CARE OF THE USER PROOFING

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*; // Enable SQL processing
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	
	
	static int choice;
	static Scanner inp = new Scanner(System.in);
	static boolean exit;
	static String remote = "jdbc:mysql://176.32.230.251/cl57-moviedb";
	static String username;
	static String password;
	static Connection connection = null;
	

	public static void main(String[] arg) throws Exception
       {
 	   System.out.println("--->Welcome to the PIKFLIX movie database interactive terminal<---");  
		
		
 	   
 	   try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		}catch (Exception e){
			System.out.println("\n"
					+ "***********************************\n"
					+ "com.mysql.jdbc.Driver instantiation FAILED!\n"
					+ "***********************************\n");
					restart();
		}

 	   
 	   
		System.out.println("\n Enter username: "); username = inp.next();
		System.out.println("Password: "); password = inp.next();
		try{
			connection = (Connection)DriverManager.getConnection(remote,"cl57-moviedb", "arpanjoe");
			System.out.println("Congratulations! connection to movie database established!!");
		}catch(Exception e){
			System.out.println("\n"
					+ "***********************************\n"
					+ "FAILED to establish connection\n"
					+ "***********************************\n");
					restart();	
		}


    	   System.out.println("--->Welcome to the PIKFLIX movie database interactive terminal<---");
    	   System.out.println("What do you want to do?");
    	   System.out.println("1. Search movies by actor");
    	   System.out.println("2. Add a star");
    	   System.out.println("3. Add a customer");
    	   System.out.println("4. Delete a customer");
    	   System.out.println("5. Provide metadata");
    	   System.out.println("6. Run custom SQL query");
    	   System.out.println("Please make a choice");
    	   choice  = inp.nextInt();
    	   switch (choice) {
		
    	   
    	   
    	   
    	case 1: 
			System.out.println(" Please enter a name: ");
			String a = inp.nextLine();
			searchmovies(a);
			break;
			
			
			
		
		case 2:
			System.out.println(" Please enter a name: ");
			String b = inp.nextLine();
			addstar(b);
		break;
		
		
		
		
		
		case 3: 
			
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Adding Customer...");
			System.out.println(" Please enter the id: ");
			int id = inp.nextInt();			
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
			        ar.add(br.readLine());
			        
			    }
			Customer customer = new Customer(id,ar.get(0),ar.get(1),ar.get(2),ar.get(3),ar.get(4),ar.get(5));
			addcustomer(customer.getId(),customer.getFirst_name(),customer
					.getLast_name(),customer.getCc(),customer
					.getAdd(),customer.getEmail(),customer.getPwd());
			
			
			
			
			
		default:
			restart();
			break;
		}
    		 
    		   
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
		 }catch(SQLException e){
			 System.out.println("Credit card does not exist");
			 System.out.println(e);
			 restart();
			 
		 }
	}

	private static void addstar(String b) {
		// TODO Auto-generated method stub
		
	}

	private static void searchmovies(String a) {
		// TODO Auto-generated method stub
		
	}
	
	private static void restart(){}
}
