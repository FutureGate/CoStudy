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
						  	<tbody>
						    	<tr>
						    		<td>
										<div class="scrolling content" style="overflow-y: auto; height: 500px;">
											<div class="ui middle aligned selection list" id="chatList">
											</div>
										</div>
									</td>
						    	</tr>
						  	</tbody>
						</table>
					</div>
	            </div>
          	</div>
        </div>

		<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
		<script>
		</script>
	</body>
		
