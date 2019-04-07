package cst.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import cst.dto.ChatDTO;

public class ChatDAO {
private DataSource ds = null;
	
	public ChatDAO() {
		try {
			InitialContext init = new InitialContext();
			Context env = (Context) init.lookup("java:/comp/env");
			ds = (DataSource) env.lookup("jdbc/CoStudy");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<ChatDTO> getChatListByID(String fromID, String toID, String chatID) {
		ArrayList<ChatDTO> chatList = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "select * from chat where ((fromID = ? AND toID = ?) OR (fromID = ? AND toID = ?)) AND chatID > ? ORDER BY chatTime";
		
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, fromID);
			pstmt.setString(2, toID);
			pstmt.setString(3, toID);
			pstmt.setString(4, fromID);
			pstmt.setInt(5, Integer.parseInt(chatID));
			
			rs = pstmt.executeQuery();
			
			chatList = new ArrayList<ChatDTO>();
			
			while(rs.next()) {
				ChatDTO chat = new ChatDTO();
				
				chat.setChatID(rs.getInt("chatID"));
				chat.setFromID(encode(rs.getString("fromID")));
				chat.setToID(encode(rs.getString("toID")));
				chat.setChatContent(encode(rs.getString("chatContent")));
				
				int chatTime = Integer.parseInt(rs.getString("chatTime").substring(11, 13));
				String timeType = "오전";
				
				if(chatTime > 12) {
					timeType ="오후";
					chatTime -= 12;
				}
				
				chat.setChatTime(rs.getString("chatTime").substring(0, 11) + " " + timeType + " " + chatTime + ":" + rs.getString("chatTime").substring(14, 16));
				chatList.add(chat);
			}
			
		} catch (Exception e) {
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
		
		return chatList;
	}
	
	public ArrayList<ChatDTO> getChatListByRecent(String fromID, String toID, int number) {
		ArrayList<ChatDTO> chatList = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "select * from chat where ((fromID = ? AND toID = ?) OR (fromID = ? AND toID = ?)) AND chatID > (select MAX(chatID) -? from chat) ORDER BY chatTime";
		
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, fromID);
			pstmt.setString(2, toID);
			pstmt.setString(3, toID);
			pstmt.setString(4, fromID);
			pstmt.setInt(5, number);
			
			rs = pstmt.executeQuery();
			
			chatList = new ArrayList<ChatDTO>();
			
			while(rs.next()) {
				ChatDTO chat = new ChatDTO();
				
				chat.setChatID(rs.getInt("chatID"));
				chat.setFromID(encode(rs.getString("fromID")));
				chat.setToID(encode(rs.getString("toID")));
				chat.setChatContent(encode(rs.getString("chatContent")));
				
				int chatTime = Integer.parseInt(rs.getString("chatTime").substring(11, 13));
				String timeType = "오전";
				
				if(chatTime > 12) {
					timeType ="오후";
					chatTime -= 12;
				}
				
				chat.setChatTime(rs.getString("chatTime").substring(0, 11) + " " + timeType + " " + chatTime + ":" + rs.getString("chatTime").substring(14, 16));
				chatList.add(chat);
			}
			
		} catch (Exception e) {
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
		
		return chatList;
	}
	
	public ArrayList<ChatDTO> getChatRoomListByID(String ID) {
		ArrayList<ChatDTO> chatList = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "select * from chat where ((fromID = ?) OR (toID = ?))";
		
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, ID);
			pstmt.setString(2, ID);
			
			rs = pstmt.executeQuery();
			
			chatList = new ArrayList<ChatDTO>();
			
			while(rs.next()) {
				ChatDTO chat = new ChatDTO();
				
				chat.setFromID(encode(rs.getString("fromID")));
				chat.setToID(encode(rs.getString("toID")));
				chat.setChatContent(encode(rs.getString("chatContent")));
				
				int chatTime = Integer.parseInt(rs.getString("chatTime").substring(11, 13));
				String timeType = "오전";
				
				if(chatTime > 12) {
					timeType ="오후";
					chatTime -= 12;
				}
				
				chat.setChatTime(rs.getString("chatTime").substring(0, 11) + " " + timeType + " " + chatTime + ":" + rs.getString("chatTime").substring(14, 16));
				chatList.add(chat);
			}
			
		} catch (Exception e) {
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
		
		return chatList;
	}
	
	public int sendChat(String fromID, String toID, String chatContent) {
		ArrayList<ChatDTO> chatList = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "insert into chat values (NULL, ?, ?, ?, NOW(), 0)";
		
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, fromID);
			pstmt.setString(2, toID);
			pstmt.setString(3, chatContent);
			
			return pstmt.executeUpdate();
		} catch (Exception e) {
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
	
	public String encode(String data) {
		return data.replaceAll(" ", "&nbsp;").replaceAll("<", "lt").replaceAll(">", "&gt;").replaceAll("\n", "<br />");
	}
	
	
}
