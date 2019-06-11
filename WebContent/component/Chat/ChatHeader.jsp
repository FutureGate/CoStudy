<%@page import="java.net.URLEncoder"%>
<%@page import="cst.dto.BoardDTO"%>
<%@page import="cst.dto.UserDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html >

<%
	String groupName = null;

	if(request.getParameter("groupname") != null) {
		groupName = request.getParameter("groupname");
		
		
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
					<a class="item" href="/CoStudy/chat/receive.do">
		 				받은 메시지
					</a>
		  		
			  		<a class="item" href="/CoStudy/chat/send.do">
			    		보낸 메시지
			  		</a>
				</div>
		</div>
    </body>
		
