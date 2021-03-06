package cst.dao;

import java.security.GeneralSecurityException;
import java.security.Key;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.codec.binary.Base64;
import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import cst.dto.UserDTO;
import cst.util.AES256Util;

public class UserDAO {
	
	private MongoClient mongo = null;
	private MongoDatabase db = null;
	private MongoCollection<Document> collection = null;
	private MongoCursor<Document> cur = null;
	
	public UserDAO() {
		try {
			MongoClientURI uri = new MongoClientURI("mongodb://13.125.255.107:11082");
			
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
				
				AES256Util aes = new AES256Util();
				
				String encPassword = rs.getString("userPassword");
				encPassword = aes.decrypt(encPassword);
				
				if(encPassword.equals(userPassword)) {
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
			
			ArrayList<String> registered = new ArrayList<String>();
			ArrayList<String> registerWaiting = new ArrayList<String>();
			
			if((registerCheck(userID) != 1)) {
				if(isUserNickExist(userNick) != 1) {
					AES256Util aes = new AES256Util();
					
					userPassword = aes.encrypt(userPassword);
					
					query.append("userLevel", 0);
					query.append("userID", userID);
					query.append("userPassword", userPassword);
					query.append("userNick", userNick);
					query.append("userEmail", userEmail);
					query.append("userProfile", null);
					query.append("userBorn", userBorn);
					query.append("userGender", userGender);
					query.append("isCertificated", 0);
		
					query.append("registered", registered);
					query.append("registerWaiting", registerWaiting);
					
					
					collection.insertOne(query);
					
					return 1;
				} else {
					return 0;
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
	
	public int isUserNickExist(String userNick) {
		try {
			Document query = new Document();

			query.append("userNick", userNick);
	
			cur = collection.find(query).iterator();
			
			if(cur.hasNext()) {
				return 1;
			} else {
				return 0;
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
	
	public int modifyUserNick(String userID, String userNick) {
		try {
			Document query = new Document();
			Document user = new Document();
			Document update = null;

			if(isUserNickExist(userNick) != 1) {
				query.append("userID", userID);
	
				user.append("userNick", userNick);
	
				update = new Document("$set", user);
	
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
	
	public int modifyUserPassword(String userID, String originPassword, String newPassword) {
		try {
			Document query = new Document();
			Document user = new Document();
			Document update = null;

			query.append("userID", userID);

			cur = collection.find(query).iterator();
			
			if(cur.hasNext()) {
				Document rs = cur.next();
				
				AES256Util aes = new AES256Util();
				
				String encPassword = rs.getString("userPassword");
				encPassword = aes.decrypt(encPassword);
				
				if(encPassword.equals(originPassword)) {
					newPassword = aes.encrypt(newPassword);
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
	
	public int registerUser(String groupName, String userID) {
		try {
			Document query = new Document();
			Document registerWaiting = new Document();
			Document update = null;
			
			ArrayList<String> registerWaitingList = null;
			
			query.append("userID", userID);

			cur = collection.find(query).iterator();
			
			if(cur.hasNext()) {
				Document rs = cur.next();
				
				registerWaitingList = (ArrayList<String>) rs.get("registerWaiting");
				
				registerWaitingList.add(groupName);
				
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
	
	public int forceRegisterUser(String groupName, String userID) {
		try {
			Document query = new Document();
			Document registered = new Document();
			Document update = null;
			
			ArrayList<String> registeredList = null;
			
			query.append("userID", userID);

			cur = collection.find(query).iterator();
			
			if(cur.hasNext()) {
				Document rs = cur.next();
				
				registeredList = (ArrayList<String>) rs.get("registered");
				
				registeredList.add(groupName);
				
				registered.append("registered", registeredList);
				
				update = new Document("$set", registered);
				
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
			Document update = null;
			
			ArrayList<String> registerWaitingList = null;
			ArrayList<String> registeredList = null;
			
			query.append("userID", userID);

			cur = collection.find(query).iterator();
			
			if(cur.hasNext()) {
				Document rs = cur.next();
				
				GroupDAO dao = new GroupDAO();
				
				if(dao.isRegistered(groupName, userID) == 2) {
					registerWaitingList = (ArrayList<String>) rs.get("registerWaiting");
					registerWaitingList.remove(groupName);
					
					registerWaiting.append("registerWaiting", registerWaitingList);
					
					update = new Document("$set", registerWaiting);
					
					collection.updateOne(query, update);
					update.clear();
					
					registeredList = (ArrayList<String>) rs.get("registered");
					registeredList.add(groupName);
					registered.append("registered", registeredList);
					
					update = new Document("$set", registered);
					
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

	
	
	public ArrayList<String> getRegistered(String userID) {
		try {
			Document query = new Document();
			
			ArrayList<String> registered = null;
			
			query.append("userID", userID);

			cur = collection.find(query).iterator();
			
			if(cur.hasNext()) {
				Document rs = cur.next();

				registered = (ArrayList<String>) rs.get("registered");
					
				return registered;
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
		return null;
	}
	
	public ArrayList<String> getRegisterWaiting(String userID) {
		try {
			Document query = new Document();
			
			ArrayList<String> registerWaiting = null;
			
			query.append("userID", userID);

			cur = collection.find(query).iterator();
			
			if(cur.hasNext()) {
				Document rs = cur.next();

				registerWaiting = (ArrayList<String>) rs.get("registerWaiting");
					
				return registerWaiting;
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
		return null;
	}
}
