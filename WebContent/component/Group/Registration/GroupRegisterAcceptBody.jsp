<%@page import="cst.dto.UserDTO"%>
<%@page import="cst.dto.BoardDTO"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
<!DOCTYPE html >

<head>

<%
	BoardDTO board = null;
	UserDTO user = null;

	String groupName = request.getParameter("groupname");
	ArrayList<String> registerWaiting = null;
	
	if(session.getAttribute("user") != null) {
		user = (UserDTO) session.getAttribute("user");
	}
	
	if(request.getAttribute("registerWaiting") != null) {
		registerWaiting = (ArrayList<String>) request.getAttribute("registerWaiting");
	}
%>

<title>BbsBody</title>
</head>
	<body>
		<jsp:include page="./GroupRegisterHeader.jsp"></jsp:include>
	
		<br />
		<br />
		<br />
		<br />

		<div class="ui container">
			<div class="ui red segments">

	           		<%
	           			if(registerWaiting != null) {
	           				if(registerWaiting.size() != 0) {
	           					for(String id : registerWaiting) {
	           		%>
		           				<div class="ui black segment">
			            			<div class="ui right aligned grid">
		  								<div class="left aligned eleven wide column">
		    								<pre><i class="user icon"> <%= id %></i></pre>
										</div>
										
										<div class="five wide column"">
	    										<a class="ui blue button" href="/CoStudy/group/acceptAction.do?groupname=<%= groupName %>&userID=<%= id %>">승인</a>
	    										<a class="ui red button" href="/CoStudy/group/denyAction.do?groupname=<%= groupName %>&userID=<%= id %>">거절</a>
		  								</div>
			  						</div>
			            		</div>
	           		<%
	           					}
	           				} else {
	           		%>
	           		
	           				<div class="ui black segment">
			            			<div class="ui center aligned grid">
		  								<div class="column">
		  									가입 승인 대기 중인 회원이 없습니다.
		  								</div>
			  						</div>
			            	</div>
	           		
	           		<%
	           				}
	           			} else {
           			%>
           					<div class="ui black segment">
			            			<div class="ui center aligned grid">
		  								<div class="column">
		  									가입 승인 대기 중인 회원이 없습니다.
		  								</div>
			  						</div>
			            	</div>
           			<%
	           			}
           			%>
           	</div>
        </div>
      	
      	<br />
		<br />
		<br />
		<br />
		<br />
		<br />
		<br />
		<br />
		<br />
		<br />
		<br />
		<br />
      	
      	<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
    </body>
		
