
package evoting.dao;

import evoting.dbutil.DBConnection;
import evoting.dto.CandidateInfo;
import evoting.dto.VoteDTO;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;

public class VoteDAO 
{
        private static PreparedStatement ps1,ps2,ps3,ps4,ps5,ps6,ps7,ps8;

    static
    {
        try {
            ps1=DBConnection.getConnection().prepareStatement("select candidate_id from voting where voter_id=?");
            ps2=DBConnection.getConnection().prepareStatement("select  candidate_id,username,party,symbol from candidate ,user_details where candidate.user_id=user_details.adhar_no and candidate_id=?");
            ps3=DBConnection.getConnection().prepareStatement("insert into voting values(?,?)");
            ps4=DBConnection.getConnection().prepareStatement("select candidate_id,count(*)as votes_obt from voting group by candidate_id order by votes_obt desc");
            ps5= DBConnection.getConnection().prepareStatement("select count(*)from voting");
            ps6=DBConnection.getConnection().prepareStatement("select party,count(*) as vote from candidate,voting where voting.candidate_id=candidate.candidate_id group by party order by 2 desc");
            ps7=DBConnection.getConnection().prepareStatement("select symbol from candidate where party=?");
            ps8=DBConnection.getConnection().prepareStatement("select gender,count(*) as vote from user_Details,voting where voting.voter_id=user_Details.adhar_no group by gender order by 2 desc");
        } 
        catch (SQLException ex) 
        {
          ex.printStackTrace();
        }
    }
    public static String getCandidateId(String userid)throws SQLException
    {
         ps1.setString(1, userid);
         ResultSet rs=ps1.executeQuery();
        if(rs.next())
            return rs.getString(1);
        return null;
    }
    public static CandidateInfo getVote(String candidateid)throws SQLException, IOException
    {
        CandidateInfo candidate=new CandidateInfo();
        Blob blob ;
        InputStream inputStream;
        ByteArrayOutputStream outputStream;
        byte[] buffer;
        int bytesRead;
        byte[] imageBytes;
        String cImage;
        ps2.setString(1, candidateid);
        ResultSet rs=ps2.executeQuery();
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
            cImage = Base64.getEncoder().encodeToString(imageBytes);
            candidate.setCandidateId(rs.getString(1));
            candidate.setCandidateName(rs.getString(2));
            candidate.setParty(rs.getString(3));
            candidate.setSymbol(cImage);
            }

        return candidate;
    }
    
public static boolean addVote(VoteDTO vote)throws SQLException
    {

            ps3.setString(1, vote.getCandidateId());
            ps3.setString(2, vote.getVoterId());
            return (ps3.executeUpdate()!=0);

    }
public static Map<String,Integer> getResult()throws SQLException
{
   ResultSet rs=ps4.executeQuery();
   Map<String,Integer> vote=new LinkedHashMap<>();
   while(rs.next())
   {
      vote.put(rs.getString(1),rs.getInt(2));
   }
  return vote;
}

public static int getVoteCount()throws SQLException
{
   ResultSet rs=ps5.executeQuery();
   if(rs.next())
   {
     return rs.getInt(1);
   }
  return 0;
}
    


public static Map<String,Integer> getPartyResult()throws SQLException
{
   ResultSet rs=ps6.executeQuery();
   Map<String,Integer> vote=new LinkedHashMap<>();
   while(rs.next())
   {
      vote.put(rs.getString(1),rs.getInt(2));
   }
  return vote;
}



 public static String getSymbol(String party)throws Exception{
        ps7.setString(1, party);
        ResultSet rs=ps7.executeQuery();
        if(rs.next()){
            Blob b=rs.getBlob(1);
            InputStream ip=b.getBinaryStream();
            byte []buffer=new byte[20496];
            int byteRead=-1;
            ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
            while((byteRead=ip.read(buffer))!=-1){
                outputStream.write(buffer,0,byteRead);
            }
            byte[]img=outputStream.toByteArray();
            Base64.Encoder en=Base64.getEncoder();
           return en.encodeToString(img);
        }
        return null;
    }
 
 
 public static Map<String,Integer> getGenderResult()throws SQLException{
  ResultSet rs=ps8.executeQuery();
  Map<String,Integer> vote=new LinkedHashMap<>();
  while(rs.next()){
      vote.put(rs.getString(1),rs.getInt(2));
  }
  return vote;
}
}