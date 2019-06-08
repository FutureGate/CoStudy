<%@page import="cst.dto.UserDTO"%>
<%@page import="cst.dto.GroupDTO"%>
<%@page import="cst.dto.BoardDTO"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
<!DOCTYPE html >

<%
	GroupDTO group = null;
	String groupName = null;
	int isRegistered = 0;
	UserDTO user = null;
	
	if(request.getAttribute("group") != null) {
		group = (GroupDTO) request.getAttribute("group");
	}
	
	if(request.getSession().getAttribute("user") != null) {
		user = (UserDTO) request.getSession().getAttribute("user");
	}
	
	if(request.getAttribute("isRegistered") != null) {
		isRegistered = (Integer) request.getAttribute("isRegistered");
	}
	
	if(request.getParameter("groupname") != null) {
		groupName = (String) request.getParameter("groupname");
	}
%>

<head>
<title>GroupListBody</title>

<style>
	#map-wrapper {
		margin: 2.5em auto;
		overflow: hidden;
	}
	
	#map {
		float: right;
		width: 100%;
		height: 500px;
	}
	
	@media screen and (max-width: 768px) {
		#places, #map {
			float: none;
			width: auto;
			margin-bottom: 1em;
		}
		
		#map {
			width: 100%;
		}
	}
	
</style>

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
		  		
		  		<%
		  			if(isRegistered == 1) {
		  		%>
			  		<a class="item" href="/CoStudy/group/bbs/list.do?groupname=<%= groupName %>">
			    		그룹 게시판
			  		</a>
			  	<%
			  		}
			  	%>
			  	
			  	<%
		  			if(group.getGroupMaster().equals(user.getUserID())) {
		  		%>
			  		<a class="item" href="/CoStudy/group/modify.do?groupname=<%= groupName %>">
			    		그룹 정보 수정
			  		</a>
			  		
			  		<a class="item" href="/CoStudy/group/accept.do?groupname=<%= groupName %>">
			    		가입 승인
			  		</a>
			  	<%
			  		}
			  	%>
			</div>

			<%
				if(group != null) {
					if(isRegistered == 0) {
			%>
						<form method="post" action="registerAction.do">
							<input type="hidden" name="groupname" value="<%= groupName %>" />
							<input type="hidden" name="userID" value="<%= user.getUserID() %>" />
							<input type="submit" class="ui red button" value="가입신청" />
						</form>
			<%
					} else if(isRegistered == 1) {
			%>
						<form method="post" action="registerAction.do">
							<input type="button" class="ui blue button" value="가입됨" />
						</form>
			<%
					} else if(isRegistered == 2) {
			%>
						<form method="post" action="registerAction.do">
							<input type="button" class="ui orange button" value="가입 대기 중" />
						</form>
			<%
					}
				}
			%>
			
			<!-- 그룹 정보 표시 -->
			<table class="ui red table center aligned">
			 	<thead>
			    	<tr>
			    		<th class="two wide">그룹 이름</th>
			    		<th class="six wide"><%= group.getGroupName() %></th>
			    		<th class="two wide">그룹장</th>
			    		<th class="six wide"><%= group.getGroupMaster() %></th>
			  		</tr>
			  		<tr>
			    		<th class="two wide">스터디 시작</th>
			    		<th class="six wide"><%=  group.getStudyStart() %></th>
			    		<th class="two wide">스터디 종료</th>
			    		<th class="six wide"><%= group.getStudyFinish() %></th>
			  		</tr>
			  	</thead>
			  	<tbody>
			    	<tr class="center aligned">
		    			<td colspan="4">
		    				<div class="map-wrapper">
            					<input type="hidden" id="initPlace" data-coords="37.5650172,126.8494668" data-zoom="12" />
            					<div id="map"></div>
            				</div>
		    			</td>
		    		</tr>
			  	</tbody>
			</table>
		</div>
		
		<br />
		<br />
		<br />
    </body>
		
