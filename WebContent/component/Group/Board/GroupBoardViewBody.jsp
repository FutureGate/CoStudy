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

	String userID = null;
	String userNick = null;
	String boardUserID = null;
	String boardID = null;
	
	if(session.getAttribute("user") != null) {
		user = (UserDTO) session.getAttribute("user");
		
		userID = user.getUserID();
		userNick = user.getUserNick();
	}
	
	if(request.getAttribute("board") != null) {
		board = (BoardDTO) request.getAttribute("board");
		
		boardUserID = board.getUserID();
		boardID = Integer.toString(board.getBoardID());
	} else {
		return;
	}
%>

<title>BbsBody</title>
</head>
	<body>
		<div class="ui vertical stripe segment">
        	<div class="ui middle aligned stackable grid container">
          		<div class="row">
	            	<div class="fifteen wide column" style="text-align:center;">
	            		
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
							<a class="ui black button" href="list.do?groupname=<%= groupName %>">목록</a>
							
							<%
								if(userID != null && boardUserID != null) {	
									if(userID.equals(boardUserID)) {
							%>
								
								<a class="ui red button" href="edit.do?groupname=<%= groupName %>&bbsID=<%= board.getBoardID() %>">수정</a>
								<button class="ui red button" id="btnDelete">삭제</button>
							<%
									}
								}
							%>
						</div>
						
						<br />
						<br />
						<h2 class="ui divider"></h2>
						
						<h5 class="left aligned">댓글</h5>
						
						<div class="ui form">
			  				<div class="ui grid">
			  					<div class="fourteen wide column">
			  						<div class="field">
			    						<textarea rows="3" id="commentContent"></textarea>
			   						</div>
			    				</div>
			    				<div class="two wide center aligned column">
			    					<button class="ui black button block"  id="btnCommentWrite">작성</button>
			    				</div>
			  				</div>
		  				</div>
						
						<!-- 댓글 표시 -->
						
						<div class="ui middle aligned divided list" id="commentList">
						</div>
	            	</div>
          		</div>
        	</div>
      	</div>
      	
      	
      	
      	<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
		<script>			
			var userID = '<%= userID %>';
			var userNick = '<%= userNick %>';
			var commentContent;
			var boardID = '<%= boardID %>';
			var groupName = '<%= groupName %>';
			var lastID = 0;
		
			function writeComment() {
				commentContent = $("#commentContent").val();
				
				$.ajax({
					type: "POST",
					url: "writeCommentAction.do",
					data: {
						userID : encodeURIComponent(userID),
						userNick : encodeURIComponent(userNick),
						commentContent : encodeURIComponent(commentContent),
						boardID : encodeURIComponent(boardID),
						bbsType : encodeURIComponent(bbsType)
					},
					dataType: "text",
					success: function(result) {
						if(result == 1) {
							//메시지 전송 성공
						} else if(result == 0) {
							// 메시지 전송 실패
						} else {
							// 예외 처리
						}
					}
				});
				
				$("#commentContent").val('');
			}
			
			function deleteComment(commentID) {
				$.ajax({
					type: "POST",
					url: "deleteCommentAction.do",
					data: {
						commentID : encodeURIComponent(commentID),
						boardID : encodeURIComponent(boardID),
						bbsType : encodeURIComponent(bbsType)
					},
					dataType: "text",
					success: function(result) {
						if(result == 1) {
							// 성공
						} else if(result == 0) {
							// 실패
						} else {
							// 예외 처리
						}
					},
					complete: function () {
						refresh();
					}
				});
			}
			
			function refresh() {
				$("#commentList").empty();
				lastID = 0;
			}
			
			function commentListFunction() {
				$.ajax({
					url: "commentListAction.do",
					type: "POST",
					data : {
						bbsType: encodeURIComponent(bbsType),
						boardID: encodeURIComponent(boardID),
						lastID: encodeURIComponent(lastID)
					},
					success: function(data) {
						if(data == "")
						
							return;
						
						var jsonData = JSON.parse(data);
						var result = jsonData.result;
						
						for(i=0; i<result.length; i++) {
							addComment(result[i][0].value, result[i][1].value, result[i][2].value, result[i][3].value, result[i][4].value);
						}
						
						lastID = Number(jsonData.last);
					}
				});
			}
			
			function addComment(commentID, userID, userNick, CommentContent, commentDate) {
				var currentUser = '<%= userID %>';
				var userSection = '';
				
				if(currentUser == userID) {
					userSection = '<button class="ui black button" id="btnCommentModify">수정</button>' +
						'<button class="ui red button btnCommentDelete" commentId="' + commentID + '">삭제</button>';
				}
				
				$("#commentList").append('<div class="item"' +
						'<h5 style="text-align: right;">' + userNick +'(' + userID + ')</h5>' +
						'<h5 style="text-align: right;">' + commentDate + '</h5>' +
						'<h5 style="text-align: right;" class="header">' + CommentContent + '</h5>' +
						userSection +
						'</div>'
				);
				
				$(".btnCommentDelete").unbind("click");
				
				$(".btnCommentDelete").click(function() {
					var isDelete = confirm("삭제하시겠습니까?");
					
					targetID = $(this).attr("commentID");
					
					if(isDelete == true) {
						deleteComment(targetID);
					}
				});
			}
			
			function getRealtimeComment() {
				setInterval(function() {
					commentListFunction(lastID);
				}, 1000);
			}
			
			(function ($) {
				$(document).ready(function() {
					$("#btnCommentWrite").click(function() {
						writeComment();
					});
					
					commentListFunction(lastID);
					getRealtimeComment();

				});
			}(jQuery));
		</script>
    </body>
		
