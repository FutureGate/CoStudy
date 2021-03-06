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

public class BoardDAO {

	private MongoClient mongo = null;
	private MongoDatabase db = null;
	private MongoCollection<Document> collection = null;
	private MongoCursor<Document> cur = null;

	private String collectionName = null;

	public BoardDAO(String bbsType) {

		if(bbsType.equals("notice")) {
			collectionName = "boardnotice";
		} else if(bbsType.equals("free")) {
			collectionName = "boardfree";
		}

		try {
			MongoClientURI uri = new MongoClientURI("mongodb://13.125.255.107:11082");

			mongo = new MongoClient(uri);
			db = mongo.getDatabase("costudy");
			collection = db.getCollection(collectionName);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ArrayList<BoardDTO> getBoardList(int pageNumber) {

		ArrayList<BoardDTO> list = new ArrayList<BoardDTO>();

		try {
			Document query = new Document();

			cur = collection.find(query).sort(Sorts.descending("boardID")).skip((pageNumber-1)*10).limit(10).iterator();

			while(cur.hasNext()) {

				Document rs = cur.next();

				BoardDTO bbs = new BoardDTO();

				bbs.setBoardID(rs.getInteger("boardID"));
				bbs.setUserID(rs.getString("userID"));
				bbs.setUserNick(rs.getString("userNick"));
				bbs.setBoardTitle(rs.getString("boardTitle"));
				bbs.setBoardContent(rs.getString("boardContent"));
				bbs.setBoardDate(rs.getString("boardDate"));
				bbs.setBoardHit(rs.getInteger("boardHit"));
				bbs.setBoardDelete(rs.getInteger("boardDelete"));

				list.add(bbs);
				
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
	
	public boolean getIsNext(int pageNumber) {

		try {
			Document query = new Document();
			
			int currentPage = (pageNumber-1)*10;
			int maxPage = (((pageNumber-1)/5) + 1)*5*10;
			
			cur = collection.find(query).sort(Sorts.descending("boardID")).skip(maxPage-currentPage).limit(10).iterator();
			if(cur.hasNext()) {
				return true;
			} else {
				return false;
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
	
	public BoardDTO getBoardByID(String boardID) {
		try {
			Document query = new Document();
			
			query.append("boardID", Integer.parseInt(boardID));

			cur = collection.find(query).iterator();

			if(cur.hasNext()) {
				Document rs = cur.next();

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
	
	public int getLastBoardID() {
		try {
			Document query = new Document();

			cur = collection.find().sort(Sorts.descending("boardID")).iterator();

			if(cur.hasNext()) {
				Document rs = cur.next();

				return rs.getInteger("boardID");
			} else {
				return 0;
			}

		} catch(Exception e) {
			e.printStackTrace();
		}

		return 0;
	}
	
	public int write(String userID, String userNick, String boardTitle, String boardContent) {
		try {
			Document query = new Document();
			ArrayList<Document> comment = new ArrayList<Document>();

			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
			
			query.append("boardID", getLastBoardID()+1);
			query.append("userID", userID);
			query.append("userNick", userNick);
			query.append("boardTitle", boardTitle);
			query.append("boardContent", boardContent);
			query.append("boardDate", format.format(new Date()));
			query.append("boardHit", 0);
			query.append("boardDelete", 0);
			query.append("commentList", comment);

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
