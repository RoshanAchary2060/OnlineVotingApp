/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evoting.dao;

import evoting.dbutil.DBConnection;
import evoting.dto.UserDTO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    private static PreparedStatement ps;

    static {
        try {
            ps = DBConnection.getConnection().prepareStatement("Select user_type from user_details where ctzn_no=? and password=?");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static String validateUser(UserDTO user) throws SQLException {
        ps.setString(1, user.getUserid());
        ps.setString(2, user.getPassword());
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getString(1);
        }
        return null;
    }
}
