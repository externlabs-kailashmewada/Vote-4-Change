
package evoting.dbutil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class DBConnection 
{
     private static Connection conn=null;
   
   static
   {
      try
      {
          Class.forName("oracle.jdbc.OracleDriver");
          conn=DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/xe","evoting","java");
          System.out.println("Driver load Conn establish");      
      }
      catch(ClassNotFoundException ex)
      { 
      ex.printStackTrace();
      }
      catch(SQLException ex)
      { 
      ex.printStackTrace();
      }
   }
   
   public static Connection getConnection()
   {
       return conn;
   }
   public static void closeConnection()
   {
        try
        { 
            if(conn !=null)
             conn.close();  
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();                
        }
   }
}
