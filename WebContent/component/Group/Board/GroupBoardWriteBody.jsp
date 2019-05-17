<%@page import="cst.dto.UserDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html >

<%
	String groupName = null;

	// 유저 정보
	String userID = null;
	String userNick = null;
	
	// 유저 정보 받기
	if(session.getAttribute("user") != null) {
		UserDTO user = (UserDTO) session.getAttribute("user");
		
		userID = user.getUserID();
		userNick = user.getUserNick();
	}
	
	if(request.getParameter("groupname") != null) {
		groupName = request.getParameter("groupname");
	}
%>

<head>
<title>BbsBody</title>
</head>
	<body>
		<jsp:include page="./GroupBoardHeader.jsp"></jsp:include>
	
		<div class="ui vertical stripe segment">
        	<div class="ui middle aligned stackable grid container">
          		<div class="row">
	            	<div class="fifteen wide column" style="text-align:center;">
	            	
	            		<!-- 게시판 표시 -->
   						<form class="ui form" action="writeAction.do" method="POST">
	   						<table class="ui red table center aligned">
							 	<thead>
							    	<tr>
							    		<th class="one wide">제목</th>
							    		<th class="fifteen wide">
							    			<div class="ui fluid input">
							    				<input type="text" name="boardTitle"></input>
							    				<input type="hidden" name="groupname" value="<%= groupName %>"></input>
							    				<input type="hidden" name="userID" value="<%= userID %>"></input>
							    				<input type="hidden" name="userNick" value="<%= userNick %>"></input>
							    			</div>
							    		</th>
							  		</tr>
						  		</thead>
							 	<tbody>
							    	<tr>
							      		<td colspan="2">
							      			<div class="field">
							      				<textarea class="ui fluid" name="boardContent" rows="20" cols="auto" placeholder="글 내용"></textarea>
							      			</div>
							      		</td>
							    	</tr>
							  	</tbody>
							</table>
							
							<div style="text-align: right;">
								<a class="ui black button" href="list.do?groupname=<%=  groupName %>">목록</a>
								<input type="submit" class="ui red button" value="작성"></a>
							</div>
						</form>
	            	</div>
          		</div>
        	</div>
      	</div>
    </body>
		
