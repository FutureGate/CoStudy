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
	
	
}
