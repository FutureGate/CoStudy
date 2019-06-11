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
			MongoClientURI uri = new MongoClientURI("mongodb://13.125.255.107:11082");

			mongo = new MongoClient(uri);
			db = mongo.getDatabase("costudy");
			collection = db.getCollection("chat");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<ChatDTO> getReceivedChatList(String userID) {
		ArrayList<ChatDTO> chatList = new ArrayList<ChatDTO>();
		
		try {
			Document query = new Document();
			
			query.append("toID", userID);

			cur = collection.find(query).iterator();
			
			while(cur.hasNext()) {
				Document rs = cur.next();
				
				ChatDTO chat = new ChatDTO();
				
				chat.setFromID(rs.getString("fromID"));
				chat.setToID(rs.getString("toID"));
				chat.setChatContent(rs.getString("chatContent"));
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

		// DB Error
		return chatList;
	}
	
	public ArrayList<ChatDTO> getSendedChatList(String userID) {
		ArrayList<ChatDTO> chatList = new ArrayList<ChatDTO>();
		
		try {
			Document query = new Document();
			
			query.append("fromID", userID);

			cur = collection.find(query).iterator();
			
			while(cur.hasNext()) {
				Document rs = cur.next();
				
				ChatDTO chat = new ChatDTO();
				
				chat.setFromID(rs.getString("fromID"));
				chat.setToID(rs.getString("toID"));
				chat.setChatContent(rs.getString("chatContent"));
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

		// DB Error
		return chatList;
	}
	
	public int sendChat(String fromID, String toID, String chatContent) {
		try {
			Document query = new Document();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
			
			query.append("fromID", fromID);
			query.append("toID", toID);
			query.append("chatContent", chatContent);
			query.append("chatTime", format.format(new Date()));

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
}
