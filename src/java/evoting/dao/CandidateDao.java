
package evoting.dao;

import evoting.dbutil.DBConnection;
import evoting.dto.CandidateDTO;
import evoting.dto.CandidateDetailDTO;
import evoting.dto.CandidateInfo;
import evoting.dto.PartyResultDTO;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;

public class CandidateDao {
    private static PreparedStatement ps1,ps2,ps3,ps4,ps5,ps6,ps7,ps8,ps9,ps10,ps11,ps12,ps13,ps14;
    static{
        try{
            ps1=DBConnection.getConnection().prepareStatement("select max(candidate_id) from candidate");
            ps2=DBConnection.getConnection().prepareStatement("select username from user_Details where aadhar_no=?");
            ps3=DBConnection.getConnection().prepareStatement("select distinct city from user_details");
            ps4=DBConnection.getConnection().prepareStatement("insert into candidate values(?,?,?,?,?)");
            ps5=DBConnection.getConnection().prepareStatement("select candidate_id from candidate");
            ps6=DBConnection.getConnection().prepareStatement("select * from candidate where candidate_id=?");
            ps7=DBConnection.getConnection().prepareStatement("select candidate_id,username,party,symbol from candidate,user_details where candidate.user_id=user_details.aadhar_no and candidate.city=(select city from user_details where aadhar_no=?)");
            ps8=DBConnection.getConnection().prepareStatement("update candidate set party=?, symbol=?, city=? where candidate_id=?");
            ps9=DBConnection.getConnection().prepareStatement("update candidate set party=?,city=? where candidate_id=?");
            ps10=DBConnection.getConnection().prepareStatement("delete from candidate where candidate_id=?");
            ps11=DBConnection.getConnection().prepareStatement("select candidate_id from candidate where user_id=?");
            ps12=DBConnection.getConnection().prepareStatement("delete from candidate where user_id=?");
            ps13=DBConnection.getConnection().prepareStatement("select * from candidate where user_id=?");
            ps14=DBConnection.getConnection().prepareStatement("select symbol from candidate where party=?");
        }
        catch(SQLException sqle)
        {
            sqle.printStackTrace();
        }
    }
    
    public static String getNewId()throws SQLException
    {
        ResultSet rs=ps1.executeQuery();
        rs.next();
        String s=rs.getString(1);
        int id=100;
        if(s!=null)
        id=Integer.parseInt(s.substring(1));
        return "C"+(id+1);
    }
    
    public static String getUserNameById(String userId) throws SQLException
    {
        ps2.setString(1, userId);
        ResultSet rs=ps2.executeQuery();
        if(rs.next())
        {
            return rs.getString(1);
        }
        else
            return null;
    }
    
    public static ArrayList<String> getCity() throws SQLException
    {
        ArrayList<String> arr=new ArrayList<>();
        ResultSet rs=ps3.executeQuery();
        
        while(rs.next())
        {
            arr.add(rs.getString(1));
        }
        return arr;
    }
    
    
    public static boolean addCandidate(CandidateDTO cand) throws SQLException
    {
        ps4.setString(1, cand.getCandidateid());
        ps4.setString(2, cand.getParty());
        ps4.setString(3, cand.getUserid());
        ps4.setBinaryStream(4, cand.getSymbol());
        ps4.setString(5, cand.getCity());
        int row=ps4.executeUpdate();
        return row==1;
    }
    public static boolean updateCandidate(CandidateDTO cand, boolean ImageUploaded) throws SQLException
    {
        if(ImageUploaded)
        {
            ps8.setString(1, cand.getParty());
            ps8.setBinaryStream(2, cand.getSymbol());
            ps8.setString(3, cand.getCity());
            ps8.setString(4, cand.getCandidateid());
            int row=ps8.executeUpdate();
            return row==1;
        }
        else
        {
            ps9.setString(1, cand.getParty());
            ps9.setString(2, cand.getCity());
            ps9.setString(3, cand.getCandidateid());
            int row=ps9.executeUpdate();
            return row==1; 
        }
    }
    public static ArrayList<String> getCandidateId()throws SQLException
    {
        ArrayList<String> idList=new ArrayList<>();
        ResultSet rs=ps5.executeQuery();
        while(rs.next())
            idList.add(rs.getString(1));
        return idList;
    }

    public static CandidateDetailDTO getDetailsById(String candidateid)throws Exception
    {
        ps6.setString(1, candidateid);
        ResultSet rs=ps6.executeQuery();
        CandidateDetailDTO cd=cd=new CandidateDetailDTO();
        Blob blob;
        InputStream inputStream;
        byte[] buffer;
        byte[] imageByte;
        int byteRead;
        String base64Image;
        ByteArrayOutputStream outputStream;
        rs.next();
        blob=rs.getBlob(4);
        inputStream=blob.getBinaryStream();
        outputStream=new ByteArrayOutputStream();
        buffer =new byte[4096];
        byteRead=-1;
        while((byteRead=inputStream.read(buffer))!=-1)
        {
            outputStream.write(buffer, 0, byteRead);
        }
        imageByte=outputStream.toByteArray();
        Base64.Encoder en=Base64.getEncoder();
        base64Image=en.encodeToString(imageByte);
        cd.setCandidateid(candidateid);
        cd.setParty(rs.getString(2));
        cd.setUserid(rs.getString(3));
        cd.setSymbol(base64Image);
        cd.setCity(rs.getString(5));
        cd.setCandidatename(CandidateDao.getUserNameById(rs.getString(3)));
        return cd;
    }
    public static ArrayList<CandidateInfo> viewCandidate(String aahar_id) throws Exception
    {
        ArrayList<CandidateInfo> candiList=new ArrayList<>();
        ps7.setString(1, aahar_id);
        ResultSet rs=ps7.executeQuery();
        Blob blob;
        InputStream inputStream;
        byte[] buffer;
        byte[] imageByte;
        int byteRead;
        String base64Image;
        ByteArrayOutputStream outputStream;
        while(rs.next())
        {
            CandidateInfo ci=new CandidateInfo();
            ci.setCandidateid(rs.getString(1));
            ci.setCandidatename(rs.getString(2));
            ci.setParty(rs.getString(3));
            blob=rs.getBlob(4);
            inputStream=blob.getBinaryStream();
            outputStream=new ByteArrayOutputStream();
            buffer =new byte[4096];
            byteRead=-1;
            while((byteRead=inputStream.read(buffer))!=-1)
            {
                outputStream.write(buffer, 0, byteRead);
            }
            imageByte=outputStream.toByteArray();
            Base64.Encoder en=Base64.getEncoder();
            base64Image=en.encodeToString(imageByte);
            ci.setSymbol(base64Image);
            candiList.add(ci);
        }
        return candiList;
    }
    public static boolean delCandidate(String cid) throws SQLException
    {
        ps10.setString(1, cid);
        int row=ps10.executeUpdate();
        System.out.println("Row deleted in candidate"+row);
        return row>0;
    }
    
    public static String getCidFromUid(String uid) throws SQLException
    {
        System.out.println(uid+" in getCidFromUid");
        ps11.setString(1, uid);
        ResultSet rs=ps11.executeQuery();
        if(rs.next())
            return rs.getString(1);
        return null;
    }
    
    public static boolean deleteCandidateByUser(String uid) throws SQLException
    {
        ps12.setString(1, uid);
        int row=ps12.executeUpdate();
        return row>0;
    }
    
    public static boolean userExist(String uid) throws SQLException
    {
        ps13.setString(1, uid);
        ResultSet rs=ps13.executeQuery();
        if(rs.next())
            return true;
        return false;
    }
    public static PartyResultDTO getSymbolFromParty(String party) throws Exception
    {
        ps14.setString(1, party);
        ResultSet rs=ps14.executeQuery();
        Blob blob;
        InputStream inputStream;
        byte[] buffer;
        byte[] imageByte;
        int byteRead;
        String base64Image;
        ByteArrayOutputStream outputStream;
        rs.next();
        blob=rs.getBlob(1);
        inputStream=blob.getBinaryStream();
        outputStream=new ByteArrayOutputStream();
        buffer =new byte[4096];
        byteRead=-1;
        while((byteRead=inputStream.read(buffer))!=-1)
        {
            outputStream.write(buffer, 0, byteRead);
        }
        imageByte=outputStream.toByteArray();
        Base64.Encoder en=Base64.getEncoder();
        base64Image=en.encodeToString(imageByte);
        PartyResultDTO pr=new PartyResultDTO();
        pr.setParty(party);
        pr.setSymbol(base64Image);
        return pr;
    }
}
