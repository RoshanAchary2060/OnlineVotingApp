package evoting.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import evoting.dbutil.DBConnection;
import evoting.dto.CandidateDTO;

public class CandidateDAO {
private static PreparedStatement ps, ps1, ps2, ps3;

    static {
        try {
    
            ps = DBConnection.getConnection().prepareStatement("select count(*) from candidate");
            ps1 = DBConnection.getConnection().prepareStatement("select username from user_details where ctzn_no=?");
            ps2 = DBConnection.getConnection().prepareStatement("select distinct city from user_details");
            ps3 = DBConnection.getConnection().prepareStatement("insert into candidate values(?,?,?,?,?)");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public static String getNewId() throws SQLException {
        ResultSet rs = ps.executeQuery();
        rs.next();
        return "C" + (100 + rs.getInt(1) + 1);
    }
    public static String getUserNameById(String uid) throws SQLException {
        ps1.setString(1, uid);
        ResultSet rs = ps1.executeQuery();
        if(rs.next()) {
            return rs.getString(1);
        } else {
            return null;
        }
    }
    public static ArrayList<String> getCity() throws SQLException {
        ArrayList<String> cityList = new ArrayList<>();
        ResultSet rs = ps2.executeQuery();
        while(rs.next()) {
            cityList.add(rs.getString(1));
        }
        return cityList;
    }
    public static boolean addCandidate(CandidateDTO dto) throws SQLException {
        ps3.setString(1, dto.getCandidateId());
        ps3.setString(2, dto.getParty());
        ps3.setString(3, dto.getUserid());
        ps3.setBinaryStream(4, dto.getSymbol());
        ps3.setString(5, dto.getCity());
        int ans = ps3.executeUpdate();
        return ans!=0;
    }
}
