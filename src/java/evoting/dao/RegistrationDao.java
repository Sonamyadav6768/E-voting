/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evoting.dao;

import evoting.dbutil.DBConnection;
import evoting.dto.UserDetails;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Dell
 */
public class RegistrationDao {
    private static PreparedStatement ps1,ps2; 
    static{
        try{
            ps1=DBConnection.getConnection().prepareStatement("Select * from user_details where aadhar_no=?");
            ps2=DBConnection.getConnection().prepareStatement("insert into user_details values(?,?,?,?,?,?,?,?)");
        }
        catch(SQLException sqle)
        {
            sqle.printStackTrace();
        }
    }
    
    public static boolean searchUser(String adhar_no)throws SQLException
    {
        ps1.setString(1, adhar_no);
        ResultSet rs=ps1.executeQuery();
        return rs.next();
    }
    
    public static boolean registerUser(UserDetails user)throws SQLException
    {
        ps2.setString(1, user.getUserId());
        ps2.setString(2, user.getPassword());
        ps2.setString(3, user.getUserName());
        ps2.setString(4, user.getAddress());
        ps2.setString(5, user.getCity());
        ps2.setString(6, user.getEmail());
        ps2.setLong(7, user.getMobileNo());
        ps2.setString(8, "voter"); 
        return ps2.executeUpdate()==1;
    }
}

