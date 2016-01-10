// JDBC Example - updating a DB via SQL template and value groups
// Coded by Chen Li/Kirill Petrov Winter, 2005
// Slightly revised for ICS185 Spring 2005, by Norman Jacobson

import java.sql.*;                              // Enable SQL processing

public class MainRef
{
    public static void notmain(String[] arg) throws Exception
    {
    	// Incorporate mySQL driver
        Class.forName("com.mysql.jdbc.Driver").newInstance();

         // Connect to the test database
        Connection connection = DriverManager.getConnection("jdbc:mysql:///moviedb","joelbandi", "Al05mighty");

        // Create an execute an SQL statement to select all of table"Stars" records
        Statement select = connection.createStatement();
        ResultSet result = select.executeQuery("Select * from stars");

//        // Get metadata from stars; print # of attributes in table
//        System.out.println("The results of the query");
//        ResultSetMetaData metadata = result.getMetaData();
//        System.out.println("There are " + metadata.getColumnCount() + " columns");

        // Print type of each attribute
//        for (int i = 1; i <= metadata.getColumnCount(); i++)
//                System.out.println("Type of column "+ i + " is " + metadata.getColumnTypeName(i));

        // print table's contents, field by field
        while (result.next())
        {
                System.out.println("Id = " + result.getInt(1));
                System.out.println("Name = " + result.getString(2) + result.getString(3));
                System.out.println("DOB = " + result.getString(4));
                System.out.println("photoURL = " + result.getString(5));
                System.out.println();
        }
    }
}
