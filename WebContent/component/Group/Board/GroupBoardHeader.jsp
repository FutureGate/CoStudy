<%@page import="cst.dto.UserDTO"%>
<%@page import="cst.dto.BoardDTO"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
<!DOCTYPE html >

<%
	String groupName = null;

	if(request.getParameter("groupname") != null) {
		groupName = (String) request.getParameter("groupname");
	}
%>

<head>


<title>GroupBoardHeader</title>
</head>
	<body>
		<div class="ui secondary pointing menu">
			<a class="item active" href="/CoStudy/group/view.do?groupname=<%= groupName %>">
 				그룹 정보
			</a>
	  		
	  		<a class="item">
	    		그룹 게시판
	  		</a>
		</div>
    </body>
		
