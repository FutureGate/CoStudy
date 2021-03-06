package cst.dao;

import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
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
			MongoClientURI uri = new MongoClientURI("mongodb://13.125.255.107:11082");

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
			ArrayList<Document> board = new ArrayList<Document>();
			ArrayList<String> registered = new ArrayList<String>();
			ArrayList<String> registerWaiting = new ArrayList<String>();
			
			groupName = URLEncoder.encode(groupName, "UTF-8");
			
			if(isGroupExist(groupName) != 1) {
				registered.add(groupMaster);
				
				UserDAO dao = new UserDAO();
				
				dao.forceRegisterUser(groupName, groupMaster);
				
				query.append("groupName", groupName);
				query.append("groupMaster", groupMaster);
				query.append("studyStart", studyStart);
				query.append("studyFinish", studyFinish);
				query.append("studyLocation", studyLocation);
				query.append("groupPop", 1);
				query.append("groupScore", 0);
				query.append("board", board);
				query.append("registered", registered);
				query.append("registerWaiting", registerWaiting);
				
				collection.insertOne(query);
			
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
	
	public int isGroupExist(String groupName) {
		try {
			Document query = new Document();
			
			query.append("groupName", groupName);
			
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

	public int modifyGroup(String groupName, String studyStart, String studyFinish, String studyLocation) {
		try {
			Document query = new Document();
			Document newGroup = new Document();
			Document update = null;
			
			query.append("groupName", groupName);

			newGroup.append("studyStart", studyStart);
			newGroup.append("studyFinish", studyFinish);
			newGroup.append("studyLocation", studyLocation);
			
			update = new Document("$set", newGroup);
			
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
	
	public GroupDTO getGroup(String groupName) {
		try {
			Document query = new Document();

			query.append("groupName", groupName);
			
			cur = collection.find(query).iterator();
			
			if(cur.hasNext()) {
				Document rs = cur.next();
				
				GroupDTO group = new GroupDTO();
				
				group.setGroupURL(rs.getString("groupURL"));
				group.setGroupName(rs.getString("groupName"));
				group.setGroupMaster(rs.getString("groupMaster"));
				group.setStudyStart(rs.getString("studyStart"));
				group.setStudyFinish(rs.getString("studyFinish"));
				group.setStudyLocation(rs.getString("studyLocation"));
				group.setGroupPop(rs.getInteger("groupPop"));
				group.setGroupScore(rs.getInteger("groupScore"));
				
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
		ArrayList<GroupDTO> groupList = new ArrayList<GroupDTO>();
		
		try {
			
			cur = collection.aggregate(Arrays.asList(Aggregates.sample(12))).iterator();
			
			while(cur.hasNext()) {
				Document rs = cur.next();
				
				GroupDTO group = new GroupDTO();
				
				group.setGroupURL(rs.getString("groupURL"));
				group.setGroupName(rs.getString("groupName"));
				group.setGroupMaster(rs.getString("groupMaster"));
				group.setStudyStart(rs.getString("studyStart"));
				group.setStudyFinish(rs.getString("studyFinish"));
				group.setStudyLocation(rs.getString("studyLocation"));
				group.setGroupPop(rs.getInteger("groupPop"));
				group.setGroupScore(rs.getInteger("groupScore"));
				
				groupList.add(group);
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
		return groupList;
	}
	
	public ArrayList<GroupDTO> getGroupListByRank() {
		ArrayList<GroupDTO> groupList = new ArrayList<GroupDTO>();
		
		try {
			
			cur = collection.find().sort(Sorts.descending("groupScore")).limit(10).iterator();
			
			while(cur.hasNext()) {
				Document rs = cur.next();
				
				GroupDTO group = new GroupDTO();
				
				group.setGroupURL(rs.getString("groupURL"));
				group.setGroupName(rs.getString("groupName"));
				group.setGroupMaster(rs.getString("groupMaster"));
				group.setStudyStart(rs.getString("studyStart"));
				group.setStudyFinish(rs.getString("studyFinish"));
				group.setStudyLocation(rs.getString("studyLocation"));
				group.setGroupPop(rs.getInteger("groupPop"));
				group.setGroupScore(rs.getInteger("groupScore"));
				
				groupList.add(group);
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
		return groupList;
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
				
				boardList = sortBoardList(boardList);
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
		int i = (pageNumber-1)*10;
		int index = (pageNumber-1)*10;
		
		try {
			Document query = new Document();

			query.append("groupName", groupName);
			
			cur = collection.find(query).iterator();

			if(cur.hasNext()) {
				Document rs = cur.next();

				ArrayList<Document> boardList = (ArrayList<Document>) rs.get("board");
				
				boardList = sortBoardList(boardList);
				
				
				while(i < boardList.size()) {

					if(count == 10)
						break;
					
					if(boardList.size() > i) {
						Document board = boardList.get(i);
					
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
						
						count++;
					}
					
					i++;
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
				
				UserDAO dao = new UserDAO();
				
				dao.registerUser(groupName, userID);
				
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
	
	public ArrayList<String> getRegisterWaiting(String groupName) {
		try {
			Document query = new Document();
			
			ArrayList<String> registerWaitingList = null;
			
			query.append("groupName", groupName);

			cur = collection.find(query).iterator();
			
			if(cur.hasNext()) {
				Document rs = cur.next();
				
				registerWaitingList = (ArrayList<String>) rs.get("registerWaiting");
				
				return registerWaitingList;
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
					UserDAO dao = new UserDAO();
					
					dao.acceptUser(groupName, userID);
					
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
	
	public int denyUser(String groupName, String userID) {
		try {
			Document query = new Document();
			Document registerWaiting = new Document();
			Document group = new Document();
			Document board = new Document();
			Document update = null;
			
			ArrayList<String> registerWaitingList = null;
			
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
	
	public void addScore(String groupName, int score) {
		try {
			Document query = new Document();
			Document newGroup = new Document();
			Document update = null;
		
			int index = -1;
			
			query.append("groupName", groupName);

			cur = collection.find(query).iterator();
			
			if(cur.hasNext()) {
				Document rs = cur.next();
				
				int groupScore = rs.getInteger("groupScore");
				
				newGroup.append("groupScore", groupScore+score);
				
				update = new Document("$set", newGroup);

				collection.updateOne(query, update);
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
	}
	
	public boolean getIsNext(String groupName, int pageNumber) {

		ArrayList<BoardDTO> list = new ArrayList<BoardDTO>();

		int i = ((((pageNumber-1)/5) + 1)*5)*10;
		
		try {
			Document query = new Document();

			query.append("groupName", groupName);
			
			cur = collection.find(query).iterator();

			if(cur.hasNext()) {
				Document rs = cur.next();

				ArrayList<Document> boardList = (ArrayList<Document>) rs.get("board");
				
				boardList = sortBoardList(boardList);
				
				if(i > boardList.size()) {
					return false;
				} else {
					return true;
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
		return false;
	}
	
	public ArrayList<Document> sortBoardList(ArrayList<Document> boardList) {
		boardList.sort(new Comparator<Document>() {
			@Override
			public int compare(Document arg0, Document arg1) {
				int boardID0 = arg0.getInteger("boardID");
				int boardID1 = arg1.getInteger("boardID");
				
				if(boardID0 == boardID1) return 0;
				else if(boardID0 > boardID1) return -1;
				else return 1;
			}
		});
		
		return boardList;
	}
	
	public BoardDTO getBoardByID(String groupName, String boardID) {
		try {
			ArrayList<Document> boardList = getBoardList(groupName);
			Document rs = findBoardByID(boardList, Integer.parseInt(boardID));
			
			if(rs != null) {
				BoardDTO board = new BoardDTO();

				doHit(groupName, boardID, rs.getInteger("boardHit"));

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

	public int doHit(String groupName, String boardID, int hit) {
		try {
			Document query = new Document();
			Document newBoard = new Document();
			Document board = new Document();
			Document update = null;
		
			int index = -1;
			
			query.append("groupName", groupName);

			ArrayList<Document> boardList = getBoardList(groupName);
			
			index = findIndexByBoardID(boardList, Integer.parseInt(boardID));
			
			newBoard = boardList.get(index);
			
			newBoard.remove("boardHit");
			newBoard.append("boardHit", hit+1);
						
			boardList.set(index, newBoard);
			
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
			
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
			
			query.append("groupName", groupName);

			ArrayList<Document> boardList = getBoardList(groupName);
			
			ArrayList<Document> comment = new ArrayList<Document>();
			
			newBoard.append("boardID", getLastBoardID(boardList)+1);
			newBoard.append("userID", userID);
			newBoard.append("userNick", userNick);
			newBoard.append("boardTitle", boardTitle);
			newBoard.append("boardContent", boardContent);
			newBoard.append("boardDate", format.format(new Date()));
			newBoard.append("boardHit", 0);
			newBoard.append("boardDelete", 0);
			newBoard.append("commentList", comment);
			
			boardList.add(newBoard);
			
			board.append("board", boardList);

			update = new Document("$set", board);

			collection.updateOne(query, update);

			addScore(groupName, 5);
			
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
	
	public int modify(String groupName, String boardID, String userNick, String boardTitle, String boardContent) {
		try {
			Document query = new Document();
			Document newBoard = new Document();
			Document board = new Document();
			Document update = null;

			int index = -1;
			
			query.append("groupName", groupName);

			ArrayList<Document> boardList = getBoardList(groupName);
			
			index = findIndexByBoardID(boardList, Integer.parseInt(boardID));
			
			newBoard = boardList.get(index);
			
			newBoard.remove("userNick");
			newBoard.remove("boardTitle");
			newBoard.remove("boardContent");
			
			newBoard.append("userNick", userNick);
			newBoard.append("boardTitle", boardTitle);
			newBoard.append("boardContent", boardContent);
			
			boardList.set(index, newBoard);
			
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
	
	public int findIndexByBoardID(ArrayList<Document> boardList, int boardID) {
		int result = -1;
		
		for(Document board : boardList) {
			if(board.getInteger("boardID") == boardID) {
				result = boardList.indexOf(board);
			}
		}
		
		return result;
	}
	
	public int delete(String groupName, String boardID) {
		try {
			Document query = new Document();
			Document board = new Document();
			Document update = null;

			int index = -1;
			
			query.append("groupName", groupName);

			ArrayList<Document> boardList = getBoardList(groupName);
			
			index = findIndexByBoardID(boardList, Integer.parseInt(boardID));
			
			boardList.remove(index);
			
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
	
	public ArrayList<CommentDTO> getCommentListByID(String groupName, String boardID, int lastID) {
		ArrayList<CommentDTO> list = new ArrayList<CommentDTO>();
		
		try {
			Document query = new Document();
			
			query.append("groupName", groupName);
			
			cur = collection.find(query).iterator();
			
			if(cur.hasNext()) {
				Document rs = cur.next();
				ArrayList<Document> boardList =(ArrayList<Document>) rs.get("board");
				
				Document board = boardList.get(findIndexByBoardID(boardList, Integer.parseInt(boardID)));
				
				ArrayList<Document> commentList = (ArrayList<Document>) board.get("commentList");

				for(Document result : commentList) {
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
	
	public int getLastCommentID(ArrayList<Document> commentList, String boardID) {
		try {
			if(commentList.size() == 0) {
				return 1;
			} else {
				return (commentList.get(commentList.size()-1).getInteger("commentID")+1);
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
	
	public int writeComment(String groupName, String boardID, String userID, String userNick, String commentContent) {
		try {
			Document newBoard = new Document();
			Document board = new Document();
			Document query = new Document();
			Document comment = new Document();
			Document update = null;
			ArrayList<Document> commentList = null;
			int index = -1;
			
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
			
			query.append("groupName", groupName);
			
			ArrayList<Document> boardList = getBoardList(groupName);
			index = findIndexByBoardID(boardList, Integer.parseInt(boardID));
			newBoard = boardList.get(index);
			
			commentList = (ArrayList<Document>) boardList.get(index).get("commentList");
			
			comment.append("commentID", getLastCommentID(commentList, boardID));
			comment.append("userID", userID);
			comment.append("userNick", userNick);
			comment.append("commentContent", commentContent);
			comment.append("commentDate", format.format(new Date()));
			
			commentList.add(comment);
			
			boardList.set(index, newBoard);
			
			board.append("board", boardList);
			
			update = new Document("$set", board);

			collection.updateOne(query, update);

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
	
	public int findIndexByCommentID(ArrayList<Document> commentList, int commentID) {
		int result = -1;
		
		for(Document comment : commentList) {
			if(comment.getInteger("commentID") == commentID) {
				result = commentList.indexOf(comment);
			}
		}
		
		return result;
	}
	
	public int deleteComment(String groupName, String boardID, int commentID) {
		try {
			Document newBoard = new Document();
			Document board = new Document();
			Document query = new Document();
			Document comment = new Document();
			Document update = null;
			ArrayList<Document> commentList = null;
			int index = -1;
			
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
			
			query.append("groupName", groupName);
			
			ArrayList<Document> boardList = getBoardList(groupName);
			index = findIndexByBoardID(boardList, Integer.parseInt(boardID));
			newBoard = boardList.get(index);
			
			commentList = (ArrayList<Document>) boardList.get(index).get("commentList");
			
			commentList.remove(findIndexByCommentID(commentList, commentID));
			
			newBoard.remove("commentList");
			newBoard.append("commentList", commentList);
			
			boardList.set(index, newBoard);
			
			board.append("board", boardList);
			
			update = new Document("$set", board);

			collection.updateOne(query, update);

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
