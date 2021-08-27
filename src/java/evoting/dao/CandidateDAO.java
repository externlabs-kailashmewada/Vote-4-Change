
package evoting.dao;

import evoting.dbutil.DBConnection;
import evoting.dto.CandidateDTO;
import evoting.dto.CandidateDetails;
import evoting.dto.CandidateInfo;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Base64;

public class CandidateDAO {
   private static Statement st;
   private static PreparedStatement ps,ps1,ps2,ps3,ps4,ps5,ps6,ps7,ps8,ps9; 
   static
   {
   try
    {
        st=DBConnection.getConnection().createStatement();  
        ps=DBConnection.getConnection().prepareStatement("select max(candidate_id) from candidate");
        ps1=DBConnection.getConnection().prepareStatement("Select username from user_details where adhar_no=?");
        ps2=DBConnection.getConnection().prepareStatement("Select distinct city from user_details");
        ps3=DBConnection.getConnection().prepareStatement("insert into candidate values(?,?,?,?,?)");         
        ps4=DBConnection.getConnection().prepareStatement("select * from candidate where candidate_id=?");
        ps5=DBConnection.getConnection().prepareStatement("select  candidate_id,username,party,symbol from candidate ,user_details where candidate.user_id=user_details.adhar_no and candidate.city=(select city from user_details where adhar_no=?)");
        ps6=DBConnection.getConnection().prepareStatement("delete from candidate where candidate_id=?");
        ps7=DBConnection.getConnection().prepareStatement("update candidate set party=?,symbol=?,city=? where candidate_id=?");
        ps8=DBConnection.getConnection().prepareStatement("select adhar_no from user_details where adhar_no not in(select user_id from candidate ) and  user_type='Voter'");
        ps9=DBConnection.getConnection().prepareStatement("select candidate_id from candidate where city=? and party=?");
    }
    catch(SQLException ex)
    {
      ex.printStackTrace();
    }
   }
   public static String getNewCandidateId()throws SQLException
   {
     ResultSet rs=ps.executeQuery();
     rs.next();
     String cid=rs.getString(1);
        if(cid==null)
            return "C101";
        else
        {

            int id=Integer.parseInt(cid.substring(1));
            return "C"+(id+1);
        }

   }
   public static String getUserNameById(String uid)throws SQLException
   {
       ps1.setString(1, uid);
       ResultSet rs=ps1.executeQuery();
       if(rs.next())
       {
           return rs.getString(1);
       }
       else
       {
           return null;
       }
   }
   public static ArrayList<String>getCity()throws SQLException
   {
      ArrayList<String>cityList=new ArrayList<>();
      ResultSet rs=ps2.executeQuery();
       while(rs.next())
       {
        cityList.add(rs.getString(1));
       }
       return cityList;
   }
   
   public static boolean addCandidate(CandidateDTO candidate,long l)throws SQLException, IOException
    {
       ps3.setString(1, candidate.getCandidateId());
       ps3.setString(2, candidate.getParty());
       ps3.setString(3, candidate.getUserId());
       ps3.setBinaryStream(4, candidate.getSymbol(),(int)l);
        //System.out.println(candidate.getSymbol());
       ps3.setString(5, candidate.getCity());

        return (ps3.executeUpdate()!=0);
    }
   
   public static ArrayList<String>getCandidateId()throws SQLException
    {
     ResultSet rs=st.executeQuery("select candidate_id from candidate");
     ArrayList<String> id=new ArrayList<>();
        while(rs.next())
        {
           id.add(rs.getString(1));
        }
        return id;
    }

       public static CandidateDetails getDetailsById(String cid)throws Exception
    {
        ps4.setString(1, cid);
        ResultSet rs=ps4.executeQuery();
        CandidateDetails candidate=new CandidateDetails();
        Blob blob ;
        InputStream inputStream;
        ByteArrayOutputStream outputStream;
        byte[] buffer;
        int bytesRead;
        byte[] imageBytes;
        String base64Image;
        if(rs.next())
        {
            blob=rs.getBlob(4);
            inputStream = blob.getBinaryStream();
            outputStream = new ByteArrayOutputStream();
            buffer = new byte[4096];
            bytesRead = -1;
             while ((bytesRead = inputStream.read(buffer)) != -1) 
             {
                outputStream.write(buffer, 0, bytesRead);                  
             }
            imageBytes = outputStream.toByteArray();
            base64Image = Base64.getEncoder().encodeToString(imageBytes);
            candidate.setSymbol(base64Image);
            candidate.setCandidateId(cid);
            candidate.setCandidateName(getUserNameById(rs.getString(3)));
            candidate.setParty(rs.getString(2));
            candidate.setUserId(rs.getString(3));
            candidate.setCity(rs.getString(5));
        }
        return candidate;
    }
 
       
  public static ArrayList <CandidateInfo> viewCandidate(String userId)throws SQLException, IOException
    {
        ArrayList <CandidateInfo> candidateList=new ArrayList<>();
        ps5.setString(1, userId);
        ResultSet rs=ps5.executeQuery();
        Blob blob ;
        InputStream inputStream;
        ByteArrayOutputStream outputStream;
        byte[] buffer;
        int bytesRead;
        byte[] imageBytes;
        String base64Image;
        while(rs.next())
        {
           blob=rs.getBlob(4);
           inputStream = blob.getBinaryStream();
           outputStream = new ByteArrayOutputStream();
           buffer = new byte[4096];
           bytesRead = -1;
             while ((bytesRead = inputStream.read(buffer)) != -1)
             {
                outputStream.write(buffer, 0, bytesRead);                  
                }
           imageBytes = outputStream.toByteArray();
           base64Image = Base64.getEncoder().encodeToString(imageBytes);
           CandidateInfo candidate=new CandidateInfo();
           candidate.setCandidateId(rs.getString(1));
           candidate.setCandidateName(rs.getString(2));
           candidate.setParty(rs.getString(3));
           candidate.setSymbol(base64Image);

          candidateList.add(candidate);
        }
        return candidateList;
    }

  public static boolean deleteCandidate(String cid)throws SQLException{
        ps6.setString(1, cid);
       return ps6.executeUpdate()==1;
    }
  public static boolean updateCandidate(CandidateDTO c,long l)throws SQLException{
        ps7.setString(1,c.getParty());
        ps7.setBinaryStream(2, c.getSymbol(),(int)l);
        ps7.setString(3,c.getCity());
        ps7.setString(4,c.getCandidateId());
        return (ps7.executeUpdate()==1);
    }
  
  public static ArrayList<String> getUserID()throws SQLException{
        ArrayList<String> userid=new ArrayList<>();
        ResultSet rs=ps8.executeQuery();
        while(rs.next()){
            userid.add(rs.getString(1));
        }
        return userid;
    }
  
  public static String checkPartyByCity(String city,String party)throws SQLException{
         ps9.setString(1, city);
         ps9.setString(2,party);
         ResultSet rs=ps9.executeQuery();
         if(rs.next()){
            // System.out.println("In CD DAO "+ rs.getString(1));
             return rs.getString(1);
         }
         return null;
     }
  
}
