<%@page import="cst.dto.GroupDTO"%>
<%@page import="cst.dto.BoardDTO"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
<!DOCTYPE html >

<%
	ArrayList<GroupDTO> groupList = null;
	
	if(request.getAttribute("groupList") != null) {
		groupList = (ArrayList<GroupDTO>) request.getAttribute("groupList");
		
	}

%>

<head>
<title>GroupListBody</title>
</head>
	<body>
		<br />
		<br />
		<br />
		<br />
		<br />
		<br />
		
		<div class="ui container">
			<a class="ui animated red button" href="createGroup.do" tabindex="0">
		  		<div class="visible content">
		  			<i class="plus square icon"></i> 그룹 만들기
		  		</div>
		  		
		  		<div class="hidden content">
			    	<i class="right arrow icon"></i>
			  	</div>
			</a>
		
			<br />
			<br />
		
			<div class="ui link cards">
				<c:forEach items="${groupList}" var="group">
	    			<a class="ui card" href="/CoStudy/group/view.do?groupname=${group.groupName}">
					 	<div class="content">
					    	<div class="header">${group.groupName}</div>
					    	<div class="meta">
					      		<span class="category">${group.groupMaster}</span>
					    	</div>
					    	
					    	<div class="description">
					      		<p></p>
					    	</div>
					  	</div>
				  	
					  	<div class="extra content">
					    	<div class="right floated author">
					      		<img class="ui avatar image" src="../static/img/costudy_logo.jpg"> Matt
					    	</div>
					  	</div>
					</a>
	    		</c:forEach>
			</div>
			
		</div>
		
		<br />
		<br />
		<br />
    </body>
		
