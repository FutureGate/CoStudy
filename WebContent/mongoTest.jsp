<%@page import="com.mongodb.MongoClientURI"%>
<%@page import="com.mongodb.client.MongoCursor"%>
<%@page import="org.bson.Document"%>
<%@page import="com.mongodb.client.MongoCollection"%>
<%@page import="com.mongodb.client.MongoDatabase"%>
<%@page import="com.mongodb.MongoClient"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>

<head>
<%
	MongoClient mongo = null;
	MongoDatabase db = null;
	MongoCollection<Document> collection = null;
	MongoCursor<Document> cur = null;
	
	try {
		MongoClientURI uri = new MongoClientURI("mongodb://54.180.29.105:11082");
		
		mongo = new MongoClient(uri);
		db = mongo.getDatabase("costudy");
		collection = db.getCollection("user");
		
		System.out.println("DB Connected!");
		
	} catch (Exception e) {
		e.printStackTrace();
	}


%>


<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>

</body>
</html>