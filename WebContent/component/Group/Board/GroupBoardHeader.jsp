<%@page import="cst.dao.GroupDAO"%>
<%@page import="cst.dto.GroupDTO"%>
<%@page import="cst.dto.BoardDTO"%>
<%@page import="cst.dto.UserDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html >

<%
	String groupName = null;
	GroupDTO group = null;
	UserDTO user = null;

	if(session.getAttribute("user") != null) {
		user = (UserDTO) session.getAttribute("user");
	}


	if(request.getParameter("groupname") != null) {
		groupName = request.getParameter("groupname");
		
		GroupDAO dao = new GroupDAO();
		group = dao.getGroup(groupName);
	}
%>

<head>
<title>BbsBody</title>
</head>
	<body>
		<div class="ui  container">
				<br />
				<br />
				<br />
				<br />
				<br />
				
				<div class="ui secondary pointing menu">
					<a class="item" href="/CoStudy/group/view.do?groupname=<%= groupName %>">
    				그룹 정보
  				</a>

		  		<a class="item active" href="/CoStudy/group/bbs/list.do?groupname=<%= groupName %>">
		    		그룹 게시판
		  		</a>
			  	
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
		</div>
    </body>
		
