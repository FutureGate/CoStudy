<%@page import="cst.dto.UserDTO"%>
<%@page import="cst.dto.BoardDTO"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
<!DOCTYPE html >

<head>

<%
	String bbsType = request.getParameter("bbs");
	String bbsName = null;
	
	BoardDTO board = null;
	
	UserDTO user = null;
	String userID = null;
	String boardUserID = null;
	
	if(session.getAttribute("user") != null) {
		user = (UserDTO) session.getAttribute("user");
		
		userID = user.getUserID();
	}
	
	if(request.getAttribute("board") != null) {
		board = (BoardDTO) request.getAttribute("board");
		
		boardUserID = board.getUserID();
	} else {
		return;
	}
	
	if(bbsType.equals("free"))
		bbsName = "자유게시판";
	else
		bbsName = "공시사항";
%>

<title>BbsBody</title>
</head>
	<body>
		<div class="ui vertical stripe segment">
        	<div class="ui middle aligned stackable grid container">
          		<div class="row">
	            	<div class="fifteen wide column" style="text-align:center;">
	            		
	            		<h2 class="ui horizontal divider header"><%= bbsName %></h2>
	            		
	            		<!-- 게시판 표시 -->
   						<table class="ui red table center aligned">
						 	<thead>
						    	<tr>
						    		<th class="one wide">제목</th>
						    		<th class="nine wide"><%= board.getBoardTitle() %></th>
						    		<th class="one wide">조회수</th>
						    		<th class="four wide"><%= board.getBoardHit() %></th>
						  		</tr>
						  		<tr>
						    		<th class="one wide">작성자</th>
						    		<th class="nine wide"><%= board.getUserNick() %> (<%= board.getUserID() %>)</th>
						    		<th class="one wide">작성일</th>
						    		<th class="four wide"><%= board.getBoardDate() %></th>
						  		</tr>
						  	</thead>
						  	<tbody>
						    	<tr class="left aligned">
					    			<td colspan="2"><%= board.getBoardContent() %></td>
					    		</tr>
					    		<tr class="center aligned">
					    			안녕하세요 !!
					    		</tr>
						  	</tbody>
						</table>
						
						<div style="text-align: right;">
							<a class="ui black button" href="list.do?bbs=<%= bbsType %>">목록</a>
							
							<%
								if(userID != null && boardUserID != null) {	
									if(userID.equals(boardUserID)) {
							%>
								
								<a class="ui red button" href="edit.do?bbs=<%= bbsType %>&bbsID=<%= board.getBoardID() %>">수정</a>
								<button class="ui red button" id="btnDelete">삭제</button>
							<%
									}
								}
							%>
						</div>
	            	</div>
          		</div>
        	</div>
      	</div>
    </body>
		
