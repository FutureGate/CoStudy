<%@page import="cst.dto.UserDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html >

<head>
<title>ChatBody</title>
</head>
	<body>
		<%
			String userID = null;
		
			if(session.getAttribute("user") != null) {
				UserDTO user = (UserDTO) session.getAttribute("user");
				userID = user.getUserID();
			}	
		%>
	
		<div class="ui vertical stripe segment">
        	<div class="ui middle aligned stackable grid container">
          		<div class="row">
	            	<div class="fifteen wide column" style="text-align:center;">
	            		
	            		<!-- 채팅창 표시 -->
	            		<table class="ui red table center aligned">
						 	<thead>
						    	<tr>
						    		<th><h2>메시지</h2></th>
						  		</tr>
						  	</thead>
						  	<tbody>
						    	<tr>
						    		<td>
						    			<div class="ui divided grid" id="chatBody" style="margin-top: 0.5em; margin-bottom: 0.5em;">
											<div class="twelve wide column">
												<div class="scrolling content" style="overflow-y: auto; height: 500px;">
													<div class="ui middle aligned selection list" id="chatList">
													</div>
												</div>
											</div>
											
							  				<div class="four wide column">
							  					<div class="scrolling content"  style="overflow-y: auto; height: 500px;">
							  						<div class="ui middle aligned selection list">
							  							<div class="item" id="addUser">
							    							<i class="plus icon"></i>
							    							
							    							<div class="content">
							      								<div class="addUserItem header">새로운 상대</div>
							    							</div>
							  							</div>
							  						</div>
							  						<div class="ui middle aligned selection list">
							  							<div class="chatItem item">
							    							<i class="user icon"></i>
							    							
							    							<div class="content">
							      								<div class="chatUserNick header">이도현</div>
							    							</div>
							  							</div>
							  							
							  							<div class="chatItem item">
							    							<i class="user icon"></i>
							    							
							    							<div class="content">
							      								<div class="chatUserNick header">김영훈</div>
							    							</div>
							  							</div>
							  							
							  							<div class="chatItem item">
							    							<i class="user icon"></i>
							    							
							    							<div class="content">
							      								<div class="chatUserNick header">임현채</div>
							    							</div>
							  							</div>
													</div>
												</div>
						  					</div>
										</div>
									</td>
						    	</tr>
						  	</tbody>
						  	<tfoot>
						    	<tr>
						      		<td class="center aligned" colspan="5">
						      			<div class="actions">
							  				<div class="ui form">
								  				<div class="ui grid">
								  					<div class="fourteen wide column">
								  						<div class="field">
								    						<textarea rows="3" id="chatContent"></textarea>
								   						</div>
								    				</div>
								    				<div class="two wide center aligned column">
								    					<button class="ui black button block"  id="btnChatSend">전송</button>
								    				</div>
								  				</div>
							  				</div>
							  			</div>
						      		</td>
						    	</tr>
						  	</tfoot>
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
					
					$("#btnChatSend").click(function() {
						sendChat();
					});
					
					getRealtimeChat();
				});
			}(jQuery));
		</script>
	</body>
		
