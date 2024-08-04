package evoting.dao;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Base64.Encoder;

import evoting.dbutil.DBConnection;
import evoting.dto.CandidateDTO;
import evoting.dto.CandidateDetails;
import evoting.dto.CandidateInfo;

public class CandidateDAO {
	private static PreparedStatement ps, ps1, ps2, ps3, ps4, ps5;
	private static Statement st;
	static {
		try {
			st = DBConnection.getConnection().createStatement();
			ps = DBConnection.getConnection().prepareStatement("select count(*) from candidate");
			ps1 = DBConnection.getConnection().prepareStatement("select username from user_details where ctzn_no=?");
			ps2 = DBConnection.getConnection().prepareStatement("select distinct city from user_details");
			ps3 = DBConnection.getConnection().prepareStatement("insert into candidate values(?,?,?,?,?)");
			ps4 = DBConnection.getConnection().prepareStatement("select * from candidate where candidate_id=?");
			ps5 = DBConnection.getConnection().prepareStatement(
					"select candidate_id,username,party,symbol from candidate,user_details where candidate.user_id=user_details.ctzn_no and candidate.city=(select city from user_details where ctzn_no=?");
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
		if (rs.next()) {
			return rs.getString(1);
		} else {
			return null;
		}
	}

	public static ArrayList<String> getCity() throws SQLException {
		ArrayList<String> cityList = new ArrayList<>();
		ResultSet rs = ps2.executeQuery();
		while (rs.next()) {
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
		return ans != 0;
	}

	public static ArrayList<String> getCandidateId() throws SQLException {
		ArrayList<String> candidateIdList = new ArrayList<>();
		ResultSet rs = st.executeQuery("select candidate_id from candidate");
		while (rs.next()) {
			candidateIdList.add(rs.getString(1));
		}
		return candidateIdList;
	}

	public static CandidateDetails getDetailsById(String cid) throws Exception {
		ps4.setString(1, cid);
		ResultSet rs = ps4.executeQuery();
		CandidateDetails cd = new CandidateDetails();
		Blob blob;
		InputStream inputStream; // blob gets converted to inputStream
		byte[] buffer;// inputStream gets converted to byte array through outputstream
		byte[] imageBytes;
		int bytesRead;
		String base64Image;
		ByteArrayOutputStream outputStream;
		if (rs.next()) {
			blob = rs.getBlob(4);
			inputStream = blob.getBinaryStream();
			outputStream = new ByteArrayOutputStream();
			buffer = new byte[4096];
			bytesRead = -1;
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}
			imageBytes = outputStream.toByteArray(); // byte array gets converted to base64
			Encoder en = Base64.getEncoder();
			base64Image = en.encodeToString(imageBytes);
			cd.setSymbol(base64Image);
			cd.setCandidateId(cid);
			cd.setCandidateName(getUserNameById(rs.getString(3)));
			cd.setUserId(rs.getString(3));
			cd.setCity(rs.getString(5));
			cd.setParty(rs.getString(2));
		}
		return cd;
	}

	public static ArrayList<CandidateInfo> viewCandidate(String ctzn_id) throws Exception {
		ArrayList<CandidateInfo> candidateList = new ArrayList<>();
		ps5.setString(1, ctzn_id);
		ResultSet rs = ps5.executeQuery();
		Blob blob;
		InputStream inputStream; // blob gets converted to inputStream
		byte[] buffer;// inputStream gets converted to byte array through outputstream
		byte[] imageBytes;
		int bytesRead;
		String base64Image;
		ByteArrayOutputStream outputStream;

		while (rs.next()) {
			blob = rs.getBlob(4);
			inputStream = blob.getBinaryStream();
			outputStream = new ByteArrayOutputStream();
			buffer = new byte[4096];
			bytesRead = -1;
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}
			imageBytes = outputStream.toByteArray(); // byte array gets converted to base64
			Encoder en = Base64.getEncoder();
			base64Image = en.encodeToString(imageBytes);
			CandidateInfo cd = new CandidateInfo();
			cd.setSymbol(base64Image);
			cd.setCandidateId(rs.getString(1));
			cd.setCandidateId(rs.getString(2));
			cd.setParty(rs.getString(3));
			candidateList.add(cd);
		}
		return candidateList;
	}
}
