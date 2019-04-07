package cst.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import cst.dto.BoardDTO;

public class BoardDAO {

	private DataSource ds = null;
	
	public BoardDAO() {
		try {
			InitialContext init = new InitialContext();
			Context env = (Context) init.lookup("java:/comp/env");
			ds = (DataSource) env.lookup("jdbc/CoStudy");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int write(String userID, String boardType, String boardTitle, String boardContent) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		ResultSet rs = null;
		
		String sql = "insert into board values (null, ?, ?, ?, ?, now(), 0, 0)";
		
		try {
			conn = (Connection) ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, userID);
			pstmt.setString(2, boardType);
			pstmt.setString(3, boardTitle);
			pstmt.setString(4, boardContent);
			
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
	
	public int getNext() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "select boardID from board order by boardID desc";
		
		try {
			conn = (Connection) ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				return rs.getInt(1) + 1;
			}
			
			return 1;
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
	
	public ArrayList<BoardDTO> getList(int pageNumber) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		ResultSet rs = null;
		
		String sql = "select * from board where boardID < ? and boardDelete = 0 order by bbsID desc limit 10";
		ArrayList<BoardDTO> list = new ArrayList<BoardDTO>();
		
		try {
			conn = (Connection) ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, getNext() - (pageNumber - 1) * 10);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				BoardDTO bbs = new BoardDTO();
				
				bbs.setBoardID(rs.getInt("boardID"));
				bbs.setUserID(rs.getString("userID"));
				bbs.setBoardType(rs.getString("boardType"));
				bbs.setBoardTitle(rs.getString("boardTitle"));
				bbs.setBoardContent(rs.getString("boardContent"));
				bbs.setBoardDate(rs.getString("boardDate"));
				bbs.setBoardHit(rs.getInt("boardHit"));
				bbs.setBoardDelete(rs.getInt("boardDelete"));
				
				list.add(bbs);
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
		return list;
	}
	
	public boolean nextPage(int pageNumber) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		ResultSet rs = null;
		
		String sql = "select * from board where boardID < ? and boardDelete = 0 order by bbsID desc limit 10";
		ArrayList<BoardDTO> list = new ArrayList<BoardDTO>();
		
		try {
			conn = (Connection) ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, getNext() - (pageNumber - 1) * 10);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				return true;
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
		return false;
	}
}
