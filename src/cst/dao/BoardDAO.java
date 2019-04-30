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
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Sorts;

import cst.dto.BoardDTO;

public class BoardDAO {

	private MongoClient mongo = null;
	private MongoDatabase db = null;
	private MongoCollection<Document> collection = null;
	private MongoCursor<Document> cur = null;
	
	private String collectionName = null;
	
	public BoardDAO(String bbsType) {
		
		if(bbsType.equals("notice")) {
			collectionName = "boardNotice";
		} else if(bbsType.equals("free")) {
			collectionName = "boardFree";
		}
		
		try {
			mongo = new MongoClient("localhost", 27017);
			db = mongo.getDatabase("costudy");
			collection = db.getCollection(collectionName);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int getBoardID() {
		try {
			Document query = new Document();
			
			cur = collection.find().sort(Sorts.ascending("boardID")).iterator();
			
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
			
			query.append("boardID", getBoardID());
			query.append("userID", userID);
			query.append("userNick", userNick);
			query.append("boardTitle", boardTitle);
			query.append("boardContent", boardContent);
			query.append("boardDate", new Date());
			query.append("boardHit", 0);
			query.append("boardDelete", 0);
			
			collection.insertOne(query);
			
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
	
	public BoardDTO getBoardByID(String boardID) {
		try {
			Document query = new Document();
			
			query.put("boardID", boardID);
			
			cur = collection.find(query).iterator();
			
			if(cur.hasNext()) {
				Document rs = cur.next();
				
				BoardDTO board = new BoardDTO();
				
				board.setBoardID(rs.getInteger("boardID"));
				board.setUserID(rs.getString("userID"));
				board.setUserNick(rs.getString("userNick"));
				board.setBoardTitle(rs.getString("boardTitle"));
				board.setBoardContent(rs.getString("boardContent"));
				board.setBoardDate(rs.getString("boardDate"));
				board.setBoardHit(rs.getInteger("boardHit"));
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
				if(mongo != null) mongo.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		// DB Error
		return null;
	}
	
	public ArrayList<BoardDTO> getList(int pageNumber) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		ResultSet rs = null;
		
		String sql = "select * from " + tableName +" where boardID < ? and boardDelete = 0 order by boardID desc limit 10";
		ArrayList<BoardDTO> list = new ArrayList<BoardDTO>();
		
		try {
			Document 
			
			conn = (Connection) ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, getNext() - (pageNumber - 1) * 10);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				BoardDTO bbs = new BoardDTO();
				
				bbs.setBoardID(rs.getInt("boardID"));
				bbs.setUserID(rs.getString("userID"));
				bbs.setUserNick(rs.getString("userNick"));
				bbs.setBoardTitle(rs.getString("boardTitle"));
				bbs.setBoardContent(rs.getString("boardContent"));
				bbs.setBoardDate(rs.getString("boardDate"));
				bbs.setBoardHit(rs.getInt("boardHit"));
				bbs.setBoardDelete(rs.getInt("boardDelete"));
				
				list.add(bbs);
			}
			
		} catch(Exception e) {
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
		
		// DB Error
		return list;
	}
	
	public int getNext() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "select boardID from " + tableName + " order by boardID desc";
		
		try {
			conn = (Connection) ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				return rs.getInt(1) + 1;
			}
			
			return 1;
		} catch(Exception e) {
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
		
		// DB Error
		return -1;
	}
	
	public boolean nextPage(int pageNumber) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		ResultSet rs = null;
		
		String sql = "select * from " + tableName +" where boardID < ? and boardDelete = 0 order by boardID desc limit 10";
		ArrayList<BoardDTO> list = new ArrayList<BoardDTO>();
		
		try {
			conn = (Connection) ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, getNext() - (pageNumber - 1) * 10);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				return true;
			}
			
		} catch(Exception e) {
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
		
		// DB Error
		return false;
	}
}
