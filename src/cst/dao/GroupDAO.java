package cst.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Updates;

import cst.dto.BoardDTO;
import cst.dto.CommentDTO;
import cst.dto.GroupDTO;

public class GroupDAO {

	private MongoClient mongo = null;
	private MongoDatabase db = null;
	private MongoCollection<Document> collection = null;
	private MongoCursor<Document> cur = null;

	public GroupDAO() {
		try {
			MongoClientURI uri = new MongoClientURI("mongodb://54.180.29.105:11082");

			mongo = new MongoClient(uri);
			db = mongo.getDatabase("costudy");
			collection = db.getCollection("group");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int createGroup(String groupName, String groupMaster, String studyStart, String studyFinish, String studyLocation) {
		try {
			Document query = new Document();
			Document board = new Document();
			ArrayList<String> registered = new ArrayList<String>();
			
			registered.add(groupMaster);
			
			query.append("groupName", groupName);
			query.append("groupMaster", groupMaster);
			query.append("studyStart", studyStart);
			query.append("studyFinish", studyFinish);
			query.append("studyLocation", studyLocation);
			query.append("groupPop", 1);
			query.append("board", board);
			query.append("registered", registered);
			
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

	public GroupDTO getGroup(String groupName) {
		try {
			Document query = new Document();

			query.append("groupName", groupName);
			
			cur = collection.find(query).iterator();
			
			if(cur.hasNext()) {
				Document rs = cur.next();
				
				GroupDTO group = new GroupDTO();
				
				group.setGroupName(rs.getString("groupName"));
				group.setGroupMaster(rs.getString("groupMaster"));
				group.setStudyStart(rs.getString("studyStart"));
				group.setStudyFinish(rs.getString("studyFinish"));
				group.setStudyLocation(rs.getString("studyLocation"));
				group.setGroupPop(rs.getInteger("groupPop"));
				
				return group;
			} else {
				return null;
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
	
	public int modify(String boardID, String userNick, String boardTitle, String boardContent) {
		try {
			Document query = new Document();
			Document board = new Document();
			Document update = null;

			query.append("boardID", Integer.parseInt(boardID));

			board.append("userNick", userNick);
			board.append("boardTitle", boardTitle);
			board.append("boardContent", boardContent);
			board.append("boardDate", new Date().toString());

			update = new Document("$set", board);

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

	public int delete(String boardID) {
		try {
			Document query = new Document();

			query.append("boardID", Integer.parseInt(boardID));

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
	
	public int getLastCommentID(String boardID) {
		try {
			Document query = new Document();
			
			query.append("boardID", Integer.parseInt(boardID));
			
			cur = collection.find(query).iterator();
			
			if(cur.hasNext()) {
				Document rs = cur.next();
				ArrayList<Document> resultList =(ArrayList<Document>) rs.get("commentList");
				
				if(resultList.size() == 0) {
					return 1;
				} else {
					return (resultList.get(resultList.size()-1).getInteger("commentID")+1);
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
		return 0;
	}
	
	public ArrayList<CommentDTO> getCommentListByID(String boardID, int lastID) {
		ArrayList<CommentDTO> list = new ArrayList<CommentDTO>();
		
		try {
			Document query = new Document();
			
			
			query.append("boardID", Integer.parseInt(boardID));
			
			cur = collection.find(query).iterator();
			
			if(cur.hasNext()) {
				Document rs = cur.next();
				ArrayList<Document> resultList =(ArrayList<Document>) rs.get("commentList");
				
				for (Document result : resultList) {
					if(result.getInteger("commentID") > lastID) {
						CommentDTO comment = new CommentDTO();
						
						comment.setCommentID(result.getInteger("commentID"));
						comment.setUserID(result.getString("userID"));
						comment.setUserNick(result.getString("userNick"));
						comment.setCommentContent(result.getString("commentContent"));
						comment.setCommentDate(result.getString("commentDate"));
						
						list.add(comment);
					}
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
		return list;
	}
	
	public int writeComment(String boardID, String userID, String userNick, String commentContent) {
		try {
			Document board = new Document();
			Document query = new Document();
			Document comment = new Document();
			Document update = null;
			ArrayList<Document> commentList = null;
			
			comment.append("commentID", getLastCommentID(boardID));
			comment.append("userID", userID);
			comment.append("userNick", userNick);
			comment.append("commentContent", commentContent);
			comment.append("commentDate", new Date().toString());
			
			query.append("boardID", Integer.parseInt(boardID));
			
			cur = collection.find(query).iterator();
			
			if(cur.hasNext()) {
				Document rs = cur.next();
				
				commentList = (ArrayList<Document>) rs.get("commentList");
				
				commentList.add(comment);
				
				board.append("commentList", commentList);
				
				update = new Document("$set", board);

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
	
	public int deleteComment(String boardID, int commentID) {
		try {
			Document board = new Document();
			Document query = new Document();
			Document comment = new Document();
			Document update = null;
			
			ArrayList<Document> commentList = null;
			query.append("boardID", Integer.parseInt(boardID));
			
			cur = collection.find(query).iterator();
			
			if(cur.hasNext()) {
				Document rs = cur.next();
				
				commentList = (ArrayList<Document>) rs.get("commentList");
				
				for(int i=0; i<commentList.size(); i++) {
					if(commentList.get(i).getInteger("commentID") == commentID) {
						commentList.remove(i);
					}
				}
				
				board.append("commentList", commentList);
				
				update = new Document("$set", board);

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
	
}
