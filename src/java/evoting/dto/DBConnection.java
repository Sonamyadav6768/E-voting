
package evoting.dbutil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static Connection conn=null;
    static{
        try{
            
        Class.forName("oracle.jdbc.OracleDriver");
        conn=DriverManager.getConnection("jdbc:oracle:thin:@//DESKTOP-V1FRB4J:1521/XE","evoting","evoting");
        System.out.println("Driver loaded and conn opened");
        }
        catch (ClassNotFoundException cnf)
        {
            cnf.printStackTrace();
        }
        catch(SQLException sqle)
        {
            sqle.printStackTrace(); 
        }
    }
    
    public static Connection getConnection()
    {
        return conn;
    }
    
    public static void closeConnection()
    {
        try{
            if(conn!=null)
                conn.close();
        }
        catch(SQLException sqle)
        {
            sqle.printStackTrace();
        }
    }
}
