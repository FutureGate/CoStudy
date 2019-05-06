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
						
						<h5 class="left aligned">댓글</h5>
						
						<div class="ui form">
			  				<div class="ui grid">
			  					<div class="fourteen wide column">
			  						<div class="field">
			    						<textarea rows="3" id="commentContent"></textarea>
			   						</div>
			    				</div>
			    				<div class="two wide center aligned column">
			    					<button class="ui black button block"  id="btnCommentSend">전송</button>
			    				</div>
			  				</div>
		  				</div>
						
						<!-- 댓글 표시 -->
   						<table class="ui table center aligned">
						 	<thead>
						  	</thead>
						  	<tbody>
						    	<tr class="left aligned">
						    		<td class="one wide">작성자</th>
						    		<th class="nine wide"><%= board.getUserNick() %> (<%= board.getUserID() %>)</th>
						    		<th class="one wide">작성일</th>
					    		</tr>
					    		<tr class="left aligned">
					    			<td row-span="2"><%= board.getBoardContent() %></td>
					    		</tr>
						  	</tbody>
						</table>
	            	</div>
          		</div>
        	</div>
      	</div>
      	
      	<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
		<script>
			var fromID = '<%= userID %>';
			var toID;
			var lastID = 0;
			
			function sendChat() {
				fromID = '<%= userID %>';

				var chatContent = $("#chatContent").val();
				
				$.ajax({
					type: "POST",
					url: "chatSendAction.do",
					data: {
						fromID : encodeURIComponent(fromID),
						toID : encodeURIComponent(toID),
						chatContent : encodeURIComponent(chatContent)
					},
					dataType: "text",
					success: function(result) {
						console.log(result);
						if(result == 1) {
							//메시지 전송 성공
						} else if(result == 0) {
							// 메시지 전송 실패
						} else {
							// 예외 처리
						}
					}
				});
				
				$("#chatContent").val('');
			}
			
			
			function chatListFunction() {
				fromID = '<%= userID %>';
				
				$.ajax({
					url: "chatListAction.do",
					type: "POST",
					data : {
						fromID: encodeURIComponent(fromID),
						toID: encodeURIComponent(toID),
						lastID: encodeURIComponent(lastID)
					},
					success: function(data) {
						console.log(data);
						
						if(data == "")
						
							return;
						
						var jsonData = JSON.parse(data);
						var result = jsonData.result;
						
						for(i=0; i<result.length; i++) {
							addChat(result[i][0].value, result[i][2].value, result[i][3].value);
						}
						
						lastID = Number(jsonData.last);
					}
				});
			}
			
			function addChat(chatName, chatContent, chatTime) {
				var currentUser = '<%= userID %>';
				
				if(currentUser == chatName) {
					$("#chatList").append('<div class="item"' +
							'<h5 style="text-align: right;">' + '나의 메시지</h5>' +
							'<h5 style="text-align: right;">' + chatTime + '</h5>' +
							'<h5 style="text-align: right;" class="header">' + chatContent + '</h5>' +
							'</div>'
					);
				} else {
					$("#chatList").append('<div class="item"' +
							'<h5 style="text-align: left;">' + chatName  + '님의 메시지</h5>' +
							'<h5 style="text-align: left;">' + chatTime + '</h5>' +
							'<h5 style="text-align: left;" class="header">' + chatContent + '</h5>' +
							'</div>'
					);
				}
				
				
			}
			
			function getRealtimeChat() {
				setInterval(function() {
					chatListFunction(lastID);
				}, 1000);
			}
			
			(function ($) {
				$(document).ready(function() {
					$(".chatItem").click(function() {
						var selectedID = $(this).find(".chatUserNick").text();
						$(this).addClass("active");
						
						$("#chatList").empty();
						toID = selectedID;
						lastID = 0;
						chatListFunction();
						
						
					});
					
					$(".addUserItem").click(function() {
						$("#addUserModal")
							.modal('show');
					});
					
					$("#btnCommentWrite").click(function() {
						writeComment();
					});
					
					getRealtimeChat();
				});
			}(jQuery));
		</script>
    </body>
		
