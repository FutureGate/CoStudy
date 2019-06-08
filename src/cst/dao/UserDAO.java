package cst.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
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
			MongoClientURI uri = new MongoClientURI("mongodb://54.180.29.105:11082");
			
			mongo = new MongoClient(uri);
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
			
			query.append("userID", userID);
			
			cur = collection.find(query).iterator();
			
			if(cur.hasNext()) {
				Document rs = cur.next();
				
				if(rs.getString("userPassword").equals(userPassword)) {
					// ID and password correct
					
					UserDTO user = new UserDTO();
					
					user.setUserLevel(rs.getInteger("userLevel"));
					user.setUserID(rs.getString("userID"));
					user.setUserPassword(rs.getString("userPassword"));
					user.setUserNick(rs.getString("userNick"));
					user.setUserEmail(rs.getString("userEmail"));
					user.setUserProfile(rs.getString("userProfile"));
					user.setUserBorn(rs.getString("userBorn"));
					user.setUserGender(rs.getString("userGender"));
					user.setIsCertificated(rs.getInteger("isCertificated"));
					
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
			
			query.append("userID", userID);
			
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
			
			query.append("userLevel", 0);
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
	
	public int unregister(String userID) {
		try {
			Document query = new Document();

			query.append("userID", userID);

			collection.deleteOne(query);

			return 1;

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
	
	public int modifyUserBorn(String userID, String userBorn) {
		try {
			Document query = new Document();
			Document user = new Document();
			Document update = null;

			query.append("userID", userID);

			user.append("userBorn", userBorn);

			update = new Document("$set", user);

			collection.updateOne(query, update);

			return 1;

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
	
	public int modifyUserNick(String userID, String userNick) {
		try {
			Document query = new Document();
			Document user = new Document();
			Document update = null;

			query.append("userID", userID);

			user.append("userNick", userNick);

			update = new Document("$set", user);

			collection.updateOne(query, update);

			return 1;

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
	
	public int modifyUserPassword(String userID, String originPassword, String newPassword) {
		try {
			Document query = new Document();
			Document user = new Document();
			Document update = null;

			query.append("userID", userID);

			cur = collection.find(query).iterator();
			
			if(cur.hasNext()) {
				Document rs = cur.next();
				
				if(rs.getString("userPassword").equals(originPassword)) {
					
					user.append("userPassword", newPassword);

					update = new Document("$set", user);

					collection.updateOne(query, update);
					
					return 1;
				} else {
					return -1;
				}
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
		return -1;
	}
	
	public int isRegistered(String groupName, String userID) {
		try {
			Document query = new Document();
			
			ArrayList<String> registerWaiting = null;
			ArrayList<String> registered = null;
			
			query.append("groupName", groupName);

			cur = collection.find(query).iterator();
			
			if(cur.hasNext()) {
				Document rs = cur.next();
				
				registered = (ArrayList<String>) rs.get("registered");
				registerWaiting = (ArrayList<String>) rs.get("registerWaiting");
				
				for(String id : registered) {
					if(id.equals(userID)) {
						// user had registered
						return 1;
					}
				}
				
				for(String id : registerWaiting) {
					if(id.equals(userID)) {
						// user waiting registering
						return 2;
					}
				}
				
				// user not registered
				return 0;
			} else {
				return -1;
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
		return -1;
	}
	
	public int registerUser(String groupName, String userID) {
		try {
			Document query = new Document();
			Document registerWaiting = new Document();
			Document board = new Document();
			Document update = null;
			
			ArrayList<String> registerWaitingList = null;
			
			query.append("groupName", groupName);

			cur = collection.find(query).iterator();
			
			if(cur.hasNext()) {
				Document rs = cur.next();
				
				registerWaitingList = (ArrayList<String>) rs.get("registerWaiting");
				
				registerWaitingList.add(userID);
				
				registerWaiting.append("registerWaiting", registerWaitingList);
				
				update = new Document("$set", registerWaiting);
				
				collection.updateOne(query, update);
				
				return 1;
			} else {
				return -1;
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
		return -1;
	}
	
	public int acceptUser(String groupName, String userID) {
		try {
			Document query = new Document();
			Document registerWaiting = new Document();
			Document registered = new Document();
			Document group = new Document();
			Document board = new Document();
			Document update = null;
			
			ArrayList<String> registerWaitingList = null;
			ArrayList<String> registeredList = null;
			
			int groupPop = 0;
			
			query.append("groupName", groupName);

			cur = collection.find(query).iterator();
			
			if(cur.hasNext()) {
				Document rs = cur.next();
				
				if(isRegistered(groupName, userID) == 2) {
					registerWaitingList = (ArrayList<String>) rs.get("registerWaiting");
					registerWaitingList.remove(userID);
					
					registerWaiting.append("registerWaiting", registerWaitingList);
					
					update = new Document("$set", registerWaiting);
					
					collection.updateOne(query, update);
					update.clear();
					
					registeredList = (ArrayList<String>) rs.get("registered");
					registeredList.add(userID);
					registered.append("registered", registeredList);
					
					update = new Document("$set", registered);
					
					collection.updateOne(query, update);
					update.clear();
					
					groupPop = rs.getInteger("groupPop");
					group.append("groupPop", groupPop+1);

					update = new Document("$set", group);
					
					collection.updateOne(query, update);
					update.clear();
					
					return 1;
				} else {
					return -1;
				}
			} else {
				return -1;
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
		return -1;
	}
	
}
