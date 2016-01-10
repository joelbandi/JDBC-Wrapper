// JDBC Example - printing a database's metadata
// Coded by Chen Li/Kirill Petrov Winter, 2005
// Slightly revised for ICS185 Spring 2005, by Norman Jacobson


import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.*;                              // Enable SQL processing
import java.util.Collections;
import java.util.Scanner;

public class Main
{
	

	
       public static void main(String[] arg) throws Exception
       {
    	   
    	   // USER DEFINED VARS

    	   String remote = "jdbc:mysql://176.32.230.251/cl57-moviedb";
    	   String local = "jdbc:mysql://localhost:3306/moviedb";
    	   
    	   
    	// Incorporate mySQL driver
           Class.forName("com.mysql.jdbc.Driver").newInstance();

            // Connect to the test database
           Connection connection = (Connection)DriverManager.getConnection(remote,"cl57-moviedb", "arpanjoe");
           
           // Create an execute an SQL statement to select all of table"Stars" records
           Statement select = connection.createStatement();
           ResultSet result = select.executeQuery("SELECT * from stars limit 1,3");
           while(result.next()){
        	   System.out.println("names: " + result.getString(2));
           }        	
       }
}
