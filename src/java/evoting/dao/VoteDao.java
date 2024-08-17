/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evoting.dao;

import evoting.dbutil.DBConnection;
import evoting.dto.CandidateInfo;
import evoting.dto.VoteDTO;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author Dell
 */
public class VoteDao {
    private static PreparedStatement ps1,ps2,ps3,ps4,ps5,ps6,ps7;
    private static Statement st;
    static{
        try{
            ps1=DBConnection.getConnection().prepareStatement("Select candidate_id from voting where voter_id=?");
            ps2=DBConnection.getConnection().prepareStatement("select candidate_id,username,party,symbol from candidate,user_Details where candidate.user_id=user_Details.aadhar_no and candidate.candidate_id=?");
            ps3=DBConnection.getConnection().prepareStatement("insert into voting values(?,?)");
            ps4=DBConnection.getConnection().prepareStatement("select candidate_id,count(*) as votes_obtain from voting group by candidate_id order by votes_obtain desc");
            st=DBConnection.getConnection().createStatement();
            ps5=DBConnection.getConnection().prepareStatement("delete from voting where candidate_id=?");
            ps6=DBConnection.getConnection().prepareStatement("delete from voting where voter_id=?");
            ps7=DBConnection.getConnection().prepareStatement("select party,count(*) as votes_obtain from candidate,voting where candidate.candidate_id=voting.candidate_id group by party order by votes_obtain desc");
        }
        catch(SQLException sqle)
        {
            sqle.printStackTrace();
        }
    }
    public static String getCandidateid(String voterid) throws SQLException
    {
        ps1.setString(1, voterid);
        ResultSet rs=ps1.executeQuery();
        if(rs.next())
            return rs.getString(1);
        return null;
    }
    
    public static CandidateInfo getVote(String candidateid) throws Exception
    {
        ps2.setString(1, candidateid);
        ResultSet rs=ps2.executeQuery();
        CandidateInfo ci=null;
        if(rs.next())
        {
            ci=new CandidateInfo();
            ci.setCandidateid(rs.getString(1));
            ci.setCandidatename(rs.getString(2));
            ci.setParty(rs.getString(3));
            Blob blob;
            InputStream inputstream;
            byte[] buffer;
            byte[] imageByte;
            int byteRead;
            String base64Image;
            ByteArrayOutputStream outputStream;
            blob=rs.getBlob(4);
            inputstream=blob.getBinaryStream();
            outputStream=new ByteArrayOutputStream();
            buffer=new byte[4096];
            byteRead=-1;
            while((byteRead=inputstream.read(buffer))!=-1)
            {
                outputStream.write(buffer, 0, byteRead);
            }
            imageByte=outputStream.toByteArray();
            Base64.Encoder en=Base64.getEncoder();
            base64Image=en.encodeToString(imageByte);
            ci.setSymbol(base64Image);
        }
        return ci;
    }
    
    public static boolean addVote(VoteDTO obj) throws SQLException
    {
        ps3.setString(1, obj.getCandidateid());
        ps3.setString(2, obj.getVoterid());
        return ps3.executeUpdate()==1;
    }
    
    public static Map<String,Integer> getResult() throws SQLException
    {
        Map<String,Integer>mp=new LinkedHashMap<>();
        ResultSet rs=ps4.executeQuery();
        while(rs.next())
        {
            mp.put(rs.getString(1),rs.getInt(2));
        }
        return mp;
    }
    
    public static int getVoteCount()throws SQLException
    {
        ResultSet rs=st.executeQuery("select count(*) from voting");
        if(rs.next())
            return rs.getInt(1);
        return 0;
    }
    
    public static boolean delCandidate(String cid) throws SQLException
    {
//        System.out.println(cid+"in voting");
        ps5.setString(1, cid);
        int row=ps5.executeUpdate();
        return row>=0;
    }
    
    public static boolean deleteVoteGivenbyUser(String vid) throws SQLException
    {
        ps6.setString(1, vid);
        int row=ps6.executeUpdate();
        return row>=0;
    }
    
    public static boolean deleteUserFromVoting(String cid) throws SQLException
    {
         ps7.setString(1, cid);
        int row=ps7.executeUpdate();
        return row>0;
    }
    
    public static Map<String,Integer> getPartyResult() throws SQLException
    {
        Map<String,Integer>mp=new LinkedHashMap<>();
        ResultSet rs=ps7.executeQuery();
        while(rs.next())
        {
            mp.put(rs.getString(1),rs.getInt(2));
        }
        return mp;
    }
}
