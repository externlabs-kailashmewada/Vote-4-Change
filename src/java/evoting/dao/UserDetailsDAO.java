
package evoting.dao;

import evoting.dbutil.DBConnection;
import evoting.dto.UserDetails;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class UserDetailsDAO {
    private static PreparedStatement ps1,ps2,ps3,ps4;
    static
    {
        try
        {
            ps1=DBConnection.getConnection().prepareStatement("select adhar_no from user_details where user_type='Voter'");
            ps2=DBConnection.getConnection().prepareStatement("select * from user_details where adhar_no=? and user_type='Voter'");
            ps3=DBConnection.getConnection().prepareStatement("delete from user_details  where adhar_no=?");
            ps4=DBConnection.getConnection().prepareStatement("select * from  user_Details where user_type='Voter'");
        }
        catch(SQLException sql)
        {
            System.out.println(sql);
        }
}
   
    public static ArrayList<String>getAllUserId()throws SQLException{
       ResultSet rs=ps1.executeQuery();
       ArrayList<String>list=new ArrayList<>();
       while(rs.next()){
           list.add(rs.getString(1));
       }
       return list;
    }
    public static UserDetails showUserDetails(String uid)throws SQLException
    {      
        ps2.setString(1, uid);
        ResultSet rs=ps2.executeQuery();
        UserDetails user=new UserDetails();
        if(rs.next())
        {   
            user.setUserid(rs.getString(1));
            user.setPassword(rs.getString(2));
            user.setUsername(rs.getString(3));
            user.setAddress(rs.getString(4));
            user.setCity(rs.getString(5));
            user.setEmail(rs.getString(6));
            user.setMobile(rs.getLong(7)); 
            user.setGender(rs.getString(9));
        }
        return user;
    }
    
  public static boolean deleteUser(String uid)throws SQLException
  {
        ps3.setString(1, uid);
        return ps3.executeUpdate()==1;
    }
  
   public static ArrayList<UserDetails>showUsers()throws SQLException{
        ArrayList<UserDetails>list=new ArrayList();
        ResultSet rs=ps4.executeQuery();
        while(rs.next()){
            UserDetails user=new UserDetails();
            user.setUserid(rs.getString(1));
            user.setPassword(rs.getString(2));
            user.setUsername(rs.getString(3));
            user.setAddress(rs.getString(4));
            user.setCity(rs.getString(5));
            user.setEmail(rs.getString(6));
            user.setMobile(rs.getLong(7));
            user.setGender(rs.getString(9));
            list.add(user);
        }
        return list;
    }
    
}
