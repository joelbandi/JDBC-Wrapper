

//JOEL TOOK CARE OF THE USER PROOFING!!
//ARPAN _ SEARCH MOVIES
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
	private static final String remote = "jdbc:mysql://176.32.230.251/cl57-moviedb";
	private static final String local = "jdbc:mysql://localhost:3306/moviedb";
	private static final String aws = "jdbc:mysql://ec2-52-34-199-3.us-west-2.compute.amazonaws.com:3306/moviedb";
	private static String username;
	private static String password;
	private static Connection connection = null;
	private static boolean loggedin;
	private static String using = local;
	
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

			try {
				mainMenu();
			} catch (InputMismatchException e) {
				System.out.println("Please enter a number");
			}
		}
		/*----------------------------------------------------------------------------------------------------------------------*/
		System.out.println("bye...\n");
		
		
	}

	private static void mainMenu() throws InputMismatchException{
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
		
		inp.nextLine();
		choice = inp.nextInt();
			switch (choice) {
			case 1: 
				searchMoviesX();
				break;

			case 2:
				//Add a star...
				addStarX();
				break;
				
				
			case 3: 
				//Add a customer...
				addCustomerX();
				break;
			
			
			case 4:
				//Delete a customer...
				deleteCustomerX();
				break;
				
				
			case 5:
				//Get metadata...
				getMetaData();
				break;
				
				
			case 6:
				//Custom...
				custom();
				break;
				
				
			case 7:
				//logging out...
				logout();
				break;
				
			case 8:
				//Exiting the program...
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
		System.out.println("Please Login to your database");
		System.out.println("Enter username: "); username = inp.next();
		System.out.println("Password: "); password = inp.next();
		try{
			connection = (Connection)DriverManager.getConnection(using,username,password);
			System.out.println("Congratulations! You are connected to the Database!!");
			loggedin = true;
		}catch(SQLException e){
			System.out.println("\n"
					+ " ----------------------------------\n"
					+"|   FAILED to establish connection  |\n"
					+ " ----------------------------------\n");
			
//			e.printStackTrace();
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
	
	private static void jdbcInit(){
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		}catch (Exception e){
			System.out.println("\n"
					+ " ----------------------------------\n"
					+"|JDBC Driver initialization failure |\n"
					+ " ----------------------------------\n");
			halt("JDBC driver failure.");
		}
	}

	/*----------------------------------------------------------------------------------------------------------------------*/
	private static void searchMoviesX() {
		System.out.println(" Query by Actor's");
		System.out.println(" 1. First Name ");
		System.out.println(" 2. Last Name  ");
		System.out.println(" 3. First & Last Name ");
		System.out.println(" 4. Star ID ");
		System.out.println("Please enter a numeric choice");
		int option;
		inp.nextLine();
		option=inp.nextInt();
		
		
		switch(option){
		case 1:
			System.out.println("Enter First Name: ");
			
			try {
				String firstName;
				firstName=br.readLine();
				showStarMoviesFirst(firstName);
			} catch (IOException e) {
//				e.printStackTrace();
				System.out.println("Error reading input");
				return;
			} catch (SQLException e) {
//				e.printStackTrace();
				System.out.println("SQL error found");
				return;
			}
			break;
		case 2:
			System.out.println("Enter Last Name: ");
			
			try {
				String lastName;
				lastName=br.readLine();
				showStarMoviesLast(lastName);
			} catch (IOException e) {
//				e.printStackTrace();
				System.out.println("Error reading input");
				return;
			} catch (SQLException e) {
//				e.printStackTrace();
				System.out.println("SQL error found");
				return;
			}
			break;
		case 3:
			System.out.println("Enter First Name: ");
			String firstName;
			String lastName;
			try {
				
				firstName=br.readLine();
			} catch (IOException e) {
//				e.printStackTrace();
				System.out.println("Error reading input");
				return;
			}
			System.out.println("Enter Last Name: ");
			try {
				lastName=br.readLine();
			} catch (IOException e) {
//				e.printStackTrace();
				System.out.println("Error reading input");
				return;
			}
			try {
				showStarMoviesFirstLast2(firstName,lastName);
			} catch (SQLException e) {
				System.out.println("Sql error found");
//				e.printStackTrace();
				return;
			}
			break;
		case 4:
			int id;
			System.out.println("Enter id: ");
			try {
				id=inp.nextInt();
				showStarMoviesid(id);
			} catch (SQLException e) {
//				e.printStackTrace();
				System.out.println("Sql error found. please try again");
				return;
			} catch(InputMismatchException e){
				System.out.println("please enter a valid option number");
				return;
			}
			break;
		}
		
	}
	private static void showStarMoviesid(int id) throws SQLException {
			Statement query = connection.createStatement();
			ResultSet result=query.executeQuery("SELECT * from movies where id IN(SELECT movie_id FROM stars_in_movies where star_id="+id+")"+";");
			ResultSetMetaData metadata = result.getMetaData();
//			while(result.next())
//			{
//				System.out.println("------------------------------------------------");
//				System.out.println("Movie Title -> "+ result.getString(2));
//				System.out.println("Year of production -> "+ result.getString(3));
//				System.out.println("Director -> "+ result.getString(4));
//				System.out.println("Banner URL -> "+ result.getString(5));
//				System.out.println("Trailer URL -> "+ result.getString(6));
//				System.out.println("------------------------------------------------ \n");
//			}
			while(result.next()){
				System.out.println("Query Details : ");
				for (int i =1;i<=metadata.getColumnCount();i++){
					System.out.println(i+".) "+metadata.getColumnName(i)+ " -> "+ result.getString(i));
				}
				System.out.println("------------------------------------------------");
				System.out.println("\n\n");
				
			}
			if(!result.first()){
				System.out.println("No such records exist");
			}

	}
	private static void showStarMoviesFirstLast(String firstName, String lastName) throws SQLException {
			Statement statement = connection.createStatement();
			ResultSet result=statement.executeQuery("SELECT * from movies where id IN(SELECT movie_id FROM stars_in_movies where star_id IN(select id from stars where first_name='"+firstName+"' AND last_name='"+lastName+"'));");
			ResultSetMetaData metadata = result.getMetaData();
//			while(result.next())
//			{
//				System.out.println("================================================");
//				System.out.println("Movie Title: "+ result.getString(2));
//				System.out.println("Year of production: "+ result.getString(3));
//				System.out.println("Director: "+ result.getString(4));
//				System.out.println("Banner URL: "+ result.getString(5));
//				System.out.println("Trailer URL: "+ result.getString(6));
//				System.out.println("\n\n================================================");
//			}
			while(result.next()){
				System.out.println("Query Details : ");
				for (int i =1;i<=metadata.getColumnCount();i++){
					System.out.println(i+".) "+metadata.getColumnName(i)+ " -> "+ result.getString(i));
				}
				System.out.println("------------------------------------------------");
				System.out.println("\n\n");
			}
			if(!result.first()){
				System.out.println("No such records exist");
			}
	}
	private static void showStarMoviesLast(String lastName) throws SQLException {
			Statement statement = connection.createStatement();
			ResultSet result=statement.executeQuery("SELECT * from movies where id IN(SELECT movie_id FROM stars_in_movies where star_id IN(select id from stars where last_name='"+lastName+"'));");
			ResultSetMetaData metadata = result.getMetaData();
//			while(result.next())
//			{
//				System.out.println("================================================");
//				System.out.println("Movie Title: "+ result.getString(2));
//				System.out.println("Year of production: "+ result.getString(3));
//				System.out.println("Director: "+ result.getString(4));
//				System.out.println("Banner URL: "+ result.getString(5));
//				System.out.println("Trailer URL: "+ result.getString(6));
//				System.out.println("\n\n================================================");
//			}
			while(result.next()){
				System.out.println("Query Details : ");
				for (int i =1;i<=metadata.getColumnCount();i++){
					System.out.println(i+".) "+metadata.getColumnName(i)+ " -> "+ result.getString(i));
				}
				System.out.println("------------------------------------------------");
				System.out.println("\n\n");
			}
			if(!result.first()){
				System.out.println("No such records exist");
			}
		}
	private static void showStarMoviesFirst(String firstName) throws SQLException {

			Statement statement = connection.createStatement();
			ResultSet result=statement.executeQuery("SELECT * from movies where id IN(SELECT movie_id FROM stars_in_movies where star_id IN(select id from stars where first_name='"+firstName+"'));");
			ResultSetMetaData metadata = result.getMetaData();
//			while(result.next())
//			{
//				System.out.println("================================================");
//				System.out.println("Movie Title: "+ result.getString(2));
//				System.out.println("Year of production: "+ result.getString(3));
//				System.out.println("Director: "+ result.getString(4));
//				System.out.println("Banner URL: "+ result.getString(5));
//				System.out.println("Trailer URL: "+ result.getString(6));
//				System.out.println("\n\n================================================");
//			}
			while(result.next()){
				System.out.println("Query Details : ");
				for (int i =1;i<=metadata.getColumnCount();i++){
					System.out.println(i+".) "+metadata.getColumnName(i)+ " -> "+ result.getString(i));
				}
				System.out.println("------------------------------------------------");
				System.out.println("\n\n");
			}
			if(!result.first()){
				System.out.println("No such records exist");
			}
		}
	private static void showStarMoviesFirstLast2(String firstName, String lastName) throws SQLException{
		Statement statement = connection.createStatement();
		ResultSet result=statement.executeQuery("SELECT * FROM (SELECT stars.first_name,stars.last_name,stars_in_movies.movie_id "
				+ "FROM stars INNER JOIN stars_in_movies ON stars.id=stars_in_movies.star_id) T1 INNER "
				+ "JOIN movies ON T1.movie_id=movies.id WHERE first_name='"+firstName+"' AND last_name='"+lastName+"';");
		ResultSetMetaData metadata = result.getMetaData();
//		while(result.next())
//		{
//			System.out.println("================================================");
//			System.out.println("Movie Title: "+ result.getString(2));
//			System.out.println("Year of production: "+ result.getString(3));
//			System.out.println("Director: "+ result.getString(4));
//			System.out.println("Banner URL: "+ result.getString(5));
//			System.out.println("Trailer URL: "+ result.getString(6));
//			System.out.println("\n\n================================================");
//		}
		while(result.next()){
			System.out.println("Query Details : ");
			for (int i =1;i<=metadata.getColumnCount();i++){
				System.out.println(i+".) "+metadata.getColumnName(i)+ " -> "+ result.getString(i));
			}
			System.out.println("------------------------------------------------");
			System.out.println("\n\n");
		}
		if(!result.first()){
			System.out.println("No such records exist");
		}
	}
	//------------------------------------------------
	
	private static void addStarX(){
		System.out.println("Adding Star...");
		System.out.println(" Please enter the id: ");
		int id;
		try {
			inp.nextLine();
			id= inp.nextInt();
		} catch (InputMismatchException e1) {
			System.out.println("You did not enter a number!!");
			System.out.println("Try again");
			return;
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
				System.out.println("Error in reading star details");
				return;
			}
		
		}
		if(ar.get(0)!="" && ar.get(1)==""){
			ar.set(1, ar.get(0));
			ar.set(0,"");
		}
	
		try {
			addStar(id,ar.get(0),ar.get(1),ar.get(2),ar.get(3));
		}catch(SQLSyntaxErrorException e){
			System.out.println("Error in SQL syntax! Please Try again");
			return;
		}catch (SQLException e) {
			System.out.println("Could not add star");
//			e.printStackTrace();
			return;
		}
		return;
	}
	private static void addStar(int id,String first,String last,String dob,String photo) throws SQLException {
		Statement query = connection.createStatement();
		query.executeUpdate("INSERT INTO stars VALUES("
                 + id + ", '"
                 + first + "', '"
                 + last + "', DATE('"
                 + dob + "'), '"
                 + photo + "');");
		 System.out.println("Star "+first+" "+last+" successfully added");
		 
	}
	//------------------------------------------------
	private static void addCustomerX(){
		System.out.println("Adding Customer...");
		System.out.println(" Please enter the id: ");
		int id;
		try {
			inp.nextLine();
			id = inp.nextInt();
		} catch (InputMismatchException e) {
			System.out.println("You did not enter a number!!");
			System.out.println("Try again");
			return;
		} catch (Exception e){
			System.out.println("Cannot recognize input");
			return;
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
				System.out.println("Error reading customer details");
				return;
			}
		}
		try {
			addCustomer(id,ar.get(0),ar.get(1),ar.get(2),ar.get(3),ar.get(4),ar.get(5));
		}
		catch(SQLIntegrityConstraintViolationException e){
			System.out.println("Credit card does not exist");
			System.out.println("Please enter a valid credit card number");
			return;
		}catch(SQLSyntaxErrorException e){
			System.out.println("Input not recognized. Please try again");
			return;
		}catch(SQLException e){
			System.out.println("Sql Error, please try again!");
			return;
		}
	}
	private static void addCustomer(int id, String first, String last, String cc, String add, String email, String pwd) throws SQLException {
		 Statement query = connection.createStatement();
		 query.executeUpdate("INSERT INTO customers VALUES("
                 + id + ", '"
                 + first + "', '"
                 + last + "', '"
                 + cc + "', '"
                 + add + "','"
                 + email + "','"
                 + pwd + "');");
		 System.out.println("Customer "+first+" "+last+" successfully added");
		 
	}
//------------------------------------------------
	private static void deleteCustomerX(){
		System.out.println("Deleting customer...");
		System.out.println(" Please enter the id: ");
		int id;
		try {
			inp.nextLine();
			id= inp.nextInt();
		} catch (InputMismatchException e) {
			System.out.println("You did not enter a number!!");
			System.out.println("Try again");
			return;
		}	
		
		try {
			deleteCustomer(id);
			System.out.println("succe6ssfully deleted \n");
		}catch(SQLIntegrityConstraintViolationException e){
			System.out.println("Cannot delete a customer without removing their credit cards ");
		}
		catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Customer record does not exist: ");
			return;
		}
	}
	private static void deleteCustomer(int id) throws SQLException {
		Statement update = connection.createStatement();
        update.executeUpdate("delete from customers where id = "+id);
	}
	//------------------------------------------------
	//------------------------------------------------
//------------------------------------------------	
	private static void getMetaData() {
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
                System.out.println("\n" + "There are "+ metadata.getColumnCount()+ " columns in this table.");
                for (int i = 1; i <= metadata.getColumnCount(); i++) {
                    System.out.println( i + ". " + metadata.getColumnName(i) + " of type " + metadata.getColumnTypeName(i));
                }
                System.out.println("------------------------------------------------");	
            }
            System.out.println();
        } catch (SQLException e) {
//            e.printStackTrace();
            System.out.println("Cannot get database metadata...");
            System.out.println("Try again");
            return;
        }
    }
	//------------------------------------------------
	//------------------------------------------------
//------------------------------------------------	
	private static void customQuery(String custom){
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
				System.out.println("------------------------------------------------");
				System.out.println("\n\n");
			}
			if(!result.first()){
				System.out.println("No such records exist");
			}
		}catch(SQLSyntaxErrorException e){
			System.out.println("SQL syntax error. Please review your SQL statement again");
			System.out.println("The documentation and reference for MySQL : http://dev.mysql.com/doc/refman/5.7/en/ ");
			return;
		}catch (SQLException e){
			System.out.println("SQL exception");
			e.printStackTrace();
			return;
		}catch(Exception e ){
			e.printStackTrace();
			return;
		}
		
	}
	private static void customQueryUpdate(String custom){
		try {
			Statement update = connection.createStatement();
			int k = update.executeUpdate(custom);
			System.out.println("Total modified column count: "+k);
		}catch(SQLSyntaxErrorException e){
			System.out.println("SQL syntax error. Please review your SQL statement again");
			System.out.println("The documentation and reference for MySQL : http://dev.mysql.com/doc/refman/5.7/en/ ");
			return;
		}
		catch (SQLIntegrityConstraintViolationException e){
			System.out.println("Error : Contraints are being violated ");
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
				customQuery(custom);
				return;
			}
			else if (arr[0].compareToIgnoreCase("update")==0 || arr[0].compareToIgnoreCase("insert")==0 || arr[0].compareToIgnoreCase("delete")==0){
				customQueryUpdate(custom);
				return;
			}
			else {
				System.out.println("SQL query not recognized");
				System.out.println("Probable syntax Errors");
				System.out.println("The documentation and reference for MySQL : http://dev.mysql.com/doc/refman/5.7/en/ ");
				return;
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error while parsing SQL statement");
			return;
		}
	}
	//------------------------------------------------
	//------------------------------------------------
	
//------------------------------------------------	
	private static void logout(){
		try {
			connection.close();
			loggedin = false;
			System.out.println("Logging out... \n\n");
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Unable to release resources");
			System.out.println("killing whole program as an alternative please start the program again");
			halt("");
		}
	}
//------------------------------------------------
}

