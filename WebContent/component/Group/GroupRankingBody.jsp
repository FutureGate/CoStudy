<%@page import="cst.dto.GroupDTO"%>
<%@page import="cst.dto.UserDTO"%>
<%@page import="cst.dto.BoardDTO"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
<!DOCTYPE html >

<head>

<%
	ArrayList<GroupDTO> groupList = null;
	UserDTO user = null;

	String groupName = request.getParameter("groupname");
	
	if(session.getAttribute("user") != null) {
		user = (UserDTO) session.getAttribute("user");
	}
	
	if(request.getAttribute("groupList") != null) {
		groupList = (ArrayList<GroupDTO>) request.getAttribute("groupList");
	}
%>

<title>BbsBody</title>
</head>
	<body>
		<br />
		<br />
		<br />
		<br />
		<br />
		<br />
		<br />
		<br />

		<div class="ui container">
			<h1 class="ui horizontal divider header">
				<i class="chess queen icon"></i> 그룹 랭킹
            </h1>
		
			<br />
			<br />
		
			<div class="ui red segments">
			
	           		<%
	           			if(groupList != null) {
	           				if(groupList.size() != 0) {
	           					for(int i=0; i<groupList.size(); i++) {
	           						GroupDTO group = groupList.get(i);
	           		%>		
		           				<div class="ui black segment">
			            			<div class="ui right aligned grid">
		  								<div class="left aligned eleven wide column">
		    								<%= i+1 %> 위 
		    								<pre><i class="book icon"></i> <%= group.getGroupName() %> - <%= group.getGroupScore() %> 점</pre>
										</div>
										
										<div class="five wide column"">
	    										<a class="ui blue button" href="/CoStudy/group/view.do?groupname=<%= group.getGroupName() %>">바로가기</a>
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
		  									그룹이 없습니다.
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
		  									그룹이 없습니다.
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
		
