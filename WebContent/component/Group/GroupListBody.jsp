<%@page import="java.net.URLEncoder"%>
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
				<%
					for(GroupDTO group : groupList) {
				%>
	    			<a class="ui card" href="/CoStudy/group/view.do?groupname=<%= URLEncoder.encode(group.getGroupName(), "UTF-8") %>">
					 	<div class="content">
					    	<div class="header"><%= group.getGroupName2() %></div>
					    	<div class="meta">
					    	</div>
					    	
					    	<div class="description">
					      		<p></p>
					    	</div>
					  	</div>
				  	
					  	<div class="extra content">
							<div class="ui equal width grid">
								<div class="column">
									그룹장 - <%= group.getGroupMaster() %>
								</div>
						  		<div class="right aligned column">
						  			<%= group.getGroupPop() %> / 50 명
						  		</div>
							</div>
					  	</div>
					</a>
	    		<%
					}
	    		%>
			</div>
			
		</div>
		
		<br />
		<br />
		<br />
    </body>
		
