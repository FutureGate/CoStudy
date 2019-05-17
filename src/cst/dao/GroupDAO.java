package cst.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
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
import com.mongodb.client.model.Aggregates;
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
	
	public ArrayList<GroupDTO> getGroupList() {
		try {
			
			cur = collection.aggregate(Arrays.asList(Aggregates.sample(1))).iterator();
			
			if(cur.hasNext()) {
				Document rs = cur.next();
				
				GroupDTO group = new GroupDTO();
				
				group.setGroupName(rs.getString("groupName"));
				group.setGroupMaster(rs.getString("groupMaster"));
				group.setStudyStart(rs.getString("studyStart"));
				group.setStudyFinish(rs.getString("studyFinish"));
				group.setStudyLocation(rs.getString("studyLocation"));
				group.setGroupPop(rs.getInteger("groupPop"));
				
				return null;
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
	
	public ArrayList<Document> getBoardList(String groupName) {
		
		ArrayList<Document> boardList = new ArrayList<Document>();
		
		try {
			Document query = new Document();

			query.append("groupName", groupName);
			
			cur = collection.find(query).iterator();

			if(cur.hasNext()) {
				Document rs = cur.next();

				boardList = (ArrayList<Document>) rs.get("board");
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
		return boardList;
	}
	
	public ArrayList<BoardDTO> getBoardListByPage(String groupName, int pageNumber) {

		ArrayList<BoardDTO> list = new ArrayList<BoardDTO>();

		int count = 0;
		int index = pageNumber;
		
		try {
			Document query = new Document();

			query.append("groupName", groupName);
			
			cur = collection.find(query).iterator();

			if(cur.hasNext()) {
				Document rs = cur.next();

				ArrayList<Document> boardList = (ArrayList<Document>) rs.get("board");
				
				
				for(Document board : boardList) {
						BoardDTO bbs = new BoardDTO();
	
						bbs.setBoardID(board.getInteger("boardID"));
						bbs.setUserID(board.getString("userID"));
						bbs.setUserNick(board.getString("userNick"));
						bbs.setBoardTitle(board.getString("boardTitle"));
						bbs.setBoardContent(board.getString("boardContent"));
						bbs.setBoardDate(board.getString("boardDate"));
						bbs.setBoardHit(board.getInteger("boardHit"));
						bbs.setBoardDelete(board.getInteger("boardDelete"));
	
						list.add(bbs);
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
	
	public BoardDTO getBoardByID(String groupName, String boardID) {
		try {
			ArrayList<Document> boardList = getBoardList(groupName);
			Document rs = findBoardByID(boardList, Integer.parseInt(boardID));
			
			if(rs != null) {
				BoardDTO board = new BoardDTO();

				doHit(boardID, rs.getInteger("boardHit"));

				board.setBoardID(rs.getInteger("boardID"));
				board.setUserID(rs.getString("userID"));
				board.setUserNick(rs.getString("userNick"));
				board.setBoardTitle(rs.getString("boardTitle"));
				board.setBoardContent(rs.getString("boardContent"));
				board.setBoardDate(rs.getString("boardDate"));
				board.setBoardHit(rs.getInteger("boardHit")+1);
				board.setBoardDelete(rs.getInteger("boardDelete"));
				
				return board;
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

	public int doHit(String boardID, int hit) {
		try {
			Document query = new Document();

			query = collection.findOneAndUpdate(Filters.eq("boardID", Integer.parseInt(boardID)), Updates.set("boardHit", hit+1));

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
	
	public Document findBoardByID(ArrayList<Document> list, int boardID) {
		for(Document board : list) {
			if(board.getInteger("boardID") == boardID) {
				return board;
			}
		}
		
		return null;
	}
	
	public int write(String groupName, String userID, String userNick, String boardTitle, String boardContent) {
		try {
			Document query = new Document();
			Document newBoard = new Document();
			Document board = new Document();
			Document update = null;

			query.append("groupName", groupName);

			ArrayList<Document> boardList = getBoardList(groupName);
			
			ArrayList<Document> comment = new ArrayList<Document>();
			
			newBoard.append("boardID", getLastBoardID(boardList)+1);
			newBoard.append("userID", userID);
			newBoard.append("userNick", userNick);
			newBoard.append("boardTitle", boardTitle);
			newBoard.append("boardContent", boardContent);
			newBoard.append("boardDate", new Date().toString());
			newBoard.append("boardHit", 0);
			newBoard.append("boardDelete", 0);
			newBoard.append("commentList", comment);
			
			boardList.add(newBoard);
			
			board.append("board", boardList);

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
	
	public int getLastBoardID(ArrayList<Document> list) {
		int result = 0;
		
		for(Document board : list) {
			if(result < board.getInteger("boardID")) {
				result = board.getInteger("boardID");
			}
		}
		
		return result;
	}
}
