package cst.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import cst.dto.UserDTO;

public class UserDAO {
	
	private MongoClient mongo = null;
	private MongoDatabase db = null;
	private MongoCollection<Document> collection = null;
	private MongoCursor<Document> cur = null;
	
	public UserDAO() {
		try {
			mongo = new MongoClient("localhost", 27017);
			db = mongo.getDatabase("costudy");
			collection = db.getCollection("user");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Login
	public int login(String userID, String userPassword, HttpServletRequest req) {
		try {
			Document query = new Document();
			
			query.put("userID", userID);
			
			cur = collection.find(query).iterator();
			
			if(cur.hasNext()) {
				Document rs = cur.next();
				
				if(rs.getString("userPassword").equals(userPassword)) {
					// ID and password correct
					
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
				
				// ID is correct, but password is not correct
				return 2;
			} else {
				// Cannot find user id
				return -1;
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(cur != null) cur.close();
				if(mongo != null) mongo.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		// DB Error
		return -2;
	}
	
	// Register
	public int registerCheck(String userID) {
		try {
			Document query = new Document();
			
			query.put("userID", userID);
			
			cur = collection.find(query).iterator();
			
			if(cur.hasNext()) {
				// user ID is exists
				return 1;
			} else {
				// user ID is not exists
				return 0;
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(cur != null) cur.close();
				if(mongo != null) mongo.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		// DB Error
		return -1;
	}
	
	public int register(String userID, String userPassword, String userNick, String userEmail, String userBorn, String userGender) {
		try {
			Document query = new Document();
			
			query.append("userID", userID);
			query.append("userPassword", userPassword);
			query.append("userNick", userNick);
			query.append("userEmail", userEmail);
			query.append("userProfile", null);
			query.append("userBorn", userBorn);
			query.append("userGender", userGender);
			query.append("isCertificated", 0);
			
			collection.insertOne(query);
			
			return 1;
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(cur != null) cur.close();
				if(mongo != null) mongo.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		// DB Error
		return -1;
	}
}
