// JDBC Example - updating a DB via SQL template and value groups
// Coded by Chen Li/Kirill Petrov Winter, 2005
// Slightly revised for ICS185 Spring 2005, by Norman Jacobson

import java.sql.*;                              // Enable SQL processing

public class UpdateRecord
{
    public static void notmain(String[] arg) throws Exception
    {
	        // Incorporate MySQL driver
	         Class.forName("com.mysql.jdbc.Driver").newInstance();
	
	        // Connect to the test database
	        Connection connection =
	            DriverManager.getConnection("jdbc:mysql:///moviedb", "mytestuser", "mypassword");
	
	        // prepare SQL statement template that's to be repeatedly excuted
	        String updateString = "update stars set first_name = ? where id = ?";
	        PreparedStatement updateStars = connection.prepareStatement(updateString);
	
	        // values for first and second "?" wildcard in statement template
	        int [] ids = {755011, 755017};
	        String [] firstNames = {"New Arnold", "New Eddie"};
	
	        // for each record in table, update to new values given
	        for(int i = 0; i < ids.length; i++)
	            {
	                updateStars.setString(1, firstNames[i]);
	                //updateStars.setInt(2, ids[i]);
	                updateStars.executeUpdate();
	            }
    }
}
