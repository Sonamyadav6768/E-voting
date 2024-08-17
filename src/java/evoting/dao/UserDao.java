/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evoting.dao;

import evoting.dbutil.DBConnection;
import evoting.dto.CandidateDTO;
import evoting.dto.UserDTO;
import evoting.dto.UserInfo;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Dell
 */
public class UserDao {
    private static PreparedStatement ps1,ps2,ps3,ps4,ps5,ps6;
    static{
        try{
            ps1=DBConnection.getConnection().prepareStatement("Select user_type from user_details where aadhar_no=? and password=?");
            ps2=DBConnection.getConnection().prepareStatement("select aadhar_no,username,address,city,email,mobile_no from user_details where user_type='voter' order by aadhar_no");
            ps3=DBConnection.getConnection().prepareStatement("select aadhar_no from user_details where user_type='voter' order by aadhar_no");
            ps4=DBConnection.getConnection().prepareStatement("select username,address,city,email,mobile_no from user_details where aadhar_no=?");
            ps5=DBConnection.getConnection().prepareStatement("delete from user_details where aadhar_no=?");
            ps6=DBConnection.getConnection().prepareStatement("update user_details set city=? where aadhar_no=?");
        }
        catch(SQLException sqle)
        {
            sqle.printStackTrace();
        }
    }
    
    public static String validateUser(UserDTO user)throws SQLException
    {
        ps1.setString(1, user.getUserid());
        ps1.setString(2, user.getPassword());
        ResultSet rs=ps1.executeQuery();
        return rs.next()==true ? rs.getString(1) : null;
    }
    
    public static ArrayList<UserInfo> getUser() throws SQLException
    {
        ArrayList<UserInfo> userList=new ArrayList<>();
        ResultSet rs=ps2.executeQuery();
        while(rs.next())
        {
            UserInfo u=new UserInfo();
            u.setUserid(rs.getString(1));
            u.setUsername(rs.getString(2));
            u.setAddress(rs.getString(3));
            u.setCity(rs.getString(4));
            u.setEmailid(rs.getString(5));
            u.setMobile(rs.getLong(6));
            userList.add(u);
        }
        return userList;
    }
    
    public static ArrayList<String> getUserList() throws SQLException
    {
         ArrayList<String> userList=new ArrayList<>();   
         ResultSet rs=ps3.executeQuery();
         while(rs.next())
         {
             userList.add(rs.getString(1));
         }
         return userList;
    }
    public static UserInfo getUserDetails(String id) throws SQLException
    {
        ps4.setString(1, id);
        ResultSet rs=ps4.executeQuery();
        UserInfo u=null;
        if(rs.next())
        {
            u=new UserInfo();
            u.setUserid(id);
            u.setUsername(rs.getString(1));
            u.setAddress(rs.getString(2));
            u.setCity(rs.getString(3));
            u.setEmailid(rs.getString(4));
            u.setMobile(rs.getLong(5));
        }
        return u;
    }
    
    public static boolean deleteUser(String uid) throws SQLException
    {
        ps5.setString(1, uid);
        int row=ps5.executeUpdate();
        return row>0;
    }
    
    public static boolean updateUser(String uid,CandidateDTO c) throws SQLException
    {
        ps6.setString(1, c.getCity());
        ps6.setString(2, uid);
        int row=ps6.executeUpdate();
        return row>0;
    }
}
