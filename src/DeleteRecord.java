//JDBC Example - deleting a record
// Coded by Checn Li/Kirill Petrov Winter, 2005
// Slightly revised for ICS185 Spring 2005, by Norman Jacobson
	

import java.sql.*;              // Enable SQL processing

public class DeleteRecord
{
    public static void notmain(String[] arg) throws Exception
    {
        // Incorporate mySQL driver
        Class.forName("com.mysql.jdbc.Driver").newInstance();

        // Connect to the test database
        Connection connection = DriverManager.getConnection("jdbc:mysql:///moviedb","joelbandi", "Al05mighty");

        // create update DB statement -- deleting second record of table; return status
        Statement update = connection.createStatement();
        int retID = update.executeUpdate("delete from stars where id = 755011");
        System.out.println("retID = " + retID);
    }
}
