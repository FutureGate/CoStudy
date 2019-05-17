<%@page import="cst.dto.GroupDTO"%>
<%@page import="cst.dto.BoardDTO"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
<!DOCTYPE html >

<%
	GroupDTO group = null;
	String groupName = null;

	if(request.getAttribute("group") != null) {
		group = (GroupDTO) request.getAttribute("group");
	}
	
	if(request.getParameter("groupname") != null) {
		groupName = (String) request.getParameter("groupname");
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
			<div class="ui secondary pointing menu">
  				<a class="item active">
    				그룹 정보
  				</a>
		  		
		  		<a class="item" href="/CoStudy/group/bbs/list.do?groupname=<%= groupName %>">
		    		그룹 게시판
		  		</a>
			</div>

			<%
				if(group != null) {
			%>
				<h3><%= group.getGroupMaster() %></h3>
				<h3><%= group.getGroupName() %></h3>
				<h3><%= group.getStudyStart() %></h3>
				<h3><%= group.getStudyFinish() %></h3>
				<h3><%= group.getStudyLocation() %></h3>
				<h3><%= group.getGroupPop() %></h3>
				
			<%
				}
			%>
		</div>
		
		<br />
		<br />
		<br />
    </body>
		
