package cst.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.sun.net.httpserver.Filter;

import cst.dto.ChatDTO;

public class ChatDAO {
	private MongoClient mongo = null;
	private MongoDatabase db = null;
	private MongoCollection<Document> collection = null;
	private MongoCursor<Document> cur = null;

	public ChatDAO() {
		try {
			MongoClientURI uri = new MongoClientURI("mongodb://54.180.29.105:11082");

			mongo = new MongoClient(uri);
			db = mongo.getDatabase("costudy");
			collection = db.getCollection("chat");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<ChatDTO> getChatListByID(String fromID, String toID, String chatID) {
		ArrayList<ChatDTO> chatList = null;
		
		try {
			Bson query = Filters.or(Filters.and(Filters.eq("fromID", fromID), Filters.eq("toID", toID)), Filters.and(Filters.eq("toID", toID), Filters.eq("fromID", fromID)));
			
			cur = collection.find(query).iterator();
			
			chatList = new ArrayList<ChatDTO>();
			
			while(cur.hasNext()) {
				Document rs = cur.next();
				
				ChatDTO chat = new ChatDTO();
				
				chat.setFromID(encode(rs.getString("fromID")));
				chat.setToID(encode(rs.getString("toID")));
				chat.setChatContent(encode(rs.getString("chatContent")));
				chat.setChatTime(rs.getString("chatTime"));
				
				chatList.add(chat);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(cur != null) cur.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return chatList;
	}
//	
//	public ArrayList<ChatDTO> getChatListByRecent(String fromID, String toID, int number) {
//		ArrayList<ChatDTO> chatList = null;
//		
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		
//		String sql = "select * from chat where ((fromID = ? AND toID = ?) OR (fromID = ? AND toID = ?)) AND chatID > (select MAX(chatID) -? from chat) ORDER BY chatTime";
//		
//		try {
//			conn = ds.getConnection();
//			pstmt = conn.prepareStatement(sql);
//			
//			pstmt.setString(1, fromID);
//			pstmt.setString(2, toID);
//			pstmt.setString(3, toID);
//			pstmt.setString(4, fromID);
//			pstmt.setInt(5, number);
//			
//			rs = pstmt.executeQuery();
//			
//			chatList = new ArrayList<ChatDTO>();
//			
//			while(rs.next()) {
//				ChatDTO chat = new ChatDTO();
//				
//				chat.setChatID(rs.getInt("chatID"));
//				chat.setFromID(encode(rs.getString("fromID")));
//				chat.setToID(encode(rs.getString("toID")));
//				chat.setChatContent(encode(rs.getString("chatContent")));
//				
//				int chatTime = Integer.parseInt(rs.getString("chatTime").substring(11, 13));
//				String timeType = "오전";
//				
//				if(chatTime > 12) {
//					timeType ="오후";
//					chatTime -= 12;
//				}
//				
//				chat.setChatTime(rs.getString("chatTime").substring(0, 11) + " " + timeType + " " + chatTime + ":" + rs.getString("chatTime").substring(14, 16));
//				chatList.add(chat);
//			}
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				if(rs != null) rs.close();
//				if(pstmt != null) pstmt.close();
//				if(conn != null) conn.close();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//		
//		return chatList;
//	}
//	
//	public ArrayList<ChatDTO> getChatRoomListByID(String ID) {
//		ArrayList<ChatDTO> chatList = null;
//		
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		
//		String sql = "select * from chat where ((fromID = ?) OR (toID = ?))";
//		
//		try {
//			conn = ds.getConnection();
//			pstmt = conn.prepareStatement(sql);
//			
//			pstmt.setString(1, ID);
//			pstmt.setString(2, ID);
//			
//			rs = pstmt.executeQuery();
//			
//			chatList = new ArrayList<ChatDTO>();
//			
//			while(rs.next()) {
//				ChatDTO chat = new ChatDTO();
//				
//				chat.setFromID(encode(rs.getString("fromID")));
//				chat.setToID(encode(rs.getString("toID")));
//				chat.setChatContent(encode(rs.getString("chatContent")));
//				
//				int chatTime = Integer.parseInt(rs.getString("chatTime").substring(11, 13));
//				String timeType = "오전";
//				
//				if(chatTime > 12) {
//					timeType ="오후";
//					chatTime -= 12;
//				}
//				
//				chat.setChatTime(rs.getString("chatTime").substring(0, 11) + " " + timeType + " " + chatTime + ":" + rs.getString("chatTime").substring(14, 16));
//				chatList.add(chat);
//			}
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				if(rs != null) rs.close();
//				if(pstmt != null) pstmt.close();
//				if(conn != null) conn.close();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//		
//		return chatList;
//	}
//	
	public int sendChat(String fromID, String toID, String chatContent) {
		try {
			Document query = new Document();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
			
			query.append("toID", toID);
			query.append("fromID", fromID);
			query.append("chatContent", chatContent);
			query.append("chatDate", format.format(new Date()));

			collection.insertOne(query);

		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(cur != null) cur.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// DB Error
		return -1;
	}
	
	public String encode(String data) {
		return data.replaceAll(" ", "&nbsp;").replaceAll("<", "lt").replaceAll(">", "&gt;").replaceAll("\n", "<br />");
	}
	
	
}
