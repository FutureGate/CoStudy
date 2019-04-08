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
	private String tableName = null;
	
	public BoardDAO(String bbsType) {
		
		if(bbsType.equals("notice")) {
			tableName = "boardNotice";
		} else if(bbsType.equals("free")) {
			tableName = "boardFree";
		}
		
		try {
			InitialContext init = new InitialContext();
			Context env = (Context) init.lookup("java:/comp/env");
			ds = (DataSource) env.lookup("jdbc/CoStudy");
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int write(String userID, String userNick, String bbsType, String boardTitle, String boardContent) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		ResultSet rs = null;
		
		String sql = "insert into " + tableName + " values (null, ?, ?, ?, ?, now(), 0, 0)";
		
		try {
			conn = (Connection) ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, userID);
			pstmt.setString(2, userNick);
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
	
	public BoardDTO getBoardByID(String boardID) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		BoardDTO board = new BoardDTO();
		
		String sql = "select * from " + tableName +" where boardID = ?";
		
		try {
			conn = (Connection) ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, boardID);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				board.setBoardID(rs.getInt("boardID"));
				board.setUserID(rs.getString("userID"));
				board.setUserNick(rs.getString("userNick"));
				board.setBoardTitle(rs.getString("boardTitle"));
				board.setBoardContent(rs.getString("boardContent"));
				board.setBoardDate(rs.getString("boardDate"));
				board.setBoardHit(rs.getInt("boardHit"));
				board.setBoardDelete(rs.getInt("boardDelete"));
				
				return board;
			} else {
				return null;
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
		return null;
	}
	
	public ArrayList<BoardDTO> getList(int pageNumber) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		ResultSet rs = null;
		
		String sql = "select * from " + tableName +" where boardID < ? and boardDelete = 0 order by boardID desc limit 10";
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
				bbs.setUserNick(rs.getString("userNick"));
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
	
	public int getNext() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "select boardID from " + tableName + " order by boardID desc";
		
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
	
	public boolean nextPage(int pageNumber) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		ResultSet rs = null;
		
		String sql = "select * from " + tableName +" where boardID < ? and boardDelete = 0 order by boardID desc limit 10";
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
