package cst.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import cst.dto.UserDTO;

public class UserDAO {
	
	private DataSource ds = null;
	
	public UserDAO() {
		try {
			InitialContext init = new InitialContext();
			Context env = (Context) init.lookup("java:/comp/env");
			ds = (DataSource) env.lookup("jdbc/CoStudy");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 로그인
	public int login(String userID, String userPassword, HttpServletRequest req) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		ResultSet rs = null;
		
		String sql = "select * from user where userID = ?";
		
		try {
			conn = (Connection) ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, userID);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				if(rs.getString("userPassword").toString().equals(userPassword)) {
					// 로그인 성공
					
					UserDTO user = new UserDTO();
					
					user.setUserID(rs.getString("userID"));
					user.setUserPassword(rs.getString("userPassword"));
					user.setUserNick(rs.getString("userNick"));
					user.setUserEmail(rs.getString("userEmail"));
					user.setUserProfile(rs.getString("userProfile"));
					user.setUserBorn(rs.getString("userBorn"));
					user.setUserGender(rs.getString("userGender"));
					user.setCertificated(rs.getBoolean("isCertificated"));
					
					req.getSession().setAttribute("user", user);
					
					return 1;
				}
				
				// 비밀번호 불일치
				return 2;
			} else {
				// 해당하는 아이디를 가진 회원이 존재하지 않음.
				return -1;
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		// DB 오류
		return -2;
	}
	
	// 아이디 중복 체크
	public int registerCheck(String userID) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		ResultSet rs = null;
		
		String sql = "select * from user where userID = ?";
		
		try {
			conn = (Connection) ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, userID);
			
			if(rs.next()) {
				// 해당하는 회원 존재
				return 1;
			} else {
				// 해당하는 아이디를 가진 회원이 존재하지 않음.
				return 0;
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		// DB 오류
		return -1;
	}
	
	public int register(String userID, String userPassword, String userNick, String userEmail, String userBorn, String userGender) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		ResultSet rs = null;
		
		String sql = "insert into user values (?, ?, ?, ?, ?, ?, ?, ?)";
		
		try {
			conn = (Connection) ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, userID);
			pstmt.setString(2, userPassword);
			pstmt.setString(3, userNick);
			pstmt.setString(4, userEmail);
			pstmt.setString(5, null);
			pstmt.setString(6, userBorn);
			pstmt.setString(7, userGender);
			pstmt.setInt(8, 0);
			
			return pstmt.executeUpdate();
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		// DB 오류
		return -1;
	}
}
