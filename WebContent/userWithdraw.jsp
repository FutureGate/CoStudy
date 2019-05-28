<%@page import="cst.dto.UserDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<%
		UserDTO user = null;
	
		if(request.getSession().getAttribute("user") != null) {
			user = (UserDTO) request.getSession().getAttribute("user");
		}
	
		if(request.getSession().getAttribute("user") == null) {
			response.sendRedirect("/CoStudy/index.jsp");
		}
	%>
	
	<meta charset="UTF-8">
	<meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible" />
	<meta name="viewport" content="width=device-width, initial-scale=1">
	
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/semantic-ui@2.4.2/dist/semantic.min.css">
	<link rel="stylesheet" href="/CoStudy/css/authBody.css">
	<link rel="stylesheet" href="/CoStudy/css/footer.css">
	
	<title>코스터디 :: 회원가입</title>
	
	<style>
		#btnLogin {
			margin-right: 0.5em;
		}
	</style>
	
</head>

<body>
	<body id="root">
		<jsp:include page="./component/General/DashboardHeader.jsp"></jsp:include>
    	
    	<jsp:include page="./component/User/UserWithdrawBody.jsp"></jsp:include>
    
    	<jsp:include page="./component/General/Footer.jsp"></jsp:include>
	
		<script src="https://code.jquery.com/jquery-2.1.4.js"></script>
		<script src="https://cdn.rawgit.com/mdehoog/Semantic-UI/6e6d051d47b598ebab05857545f242caf2b4b48c/dist/semantic.min.js"></script>
		<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.min.js" integrity="sha256-VazP97ZCwtekAsvgPBSUwPFKdrwD3unUfSGVYrahUqU="  crossorigin="anonymous"></script>
		<script src="https://cdn.jsdelivr.net/npm/semantic-ui@2.4.2/dist/semantic.min.js"></script>
		<script src="/CoStudy/js/defaultHeader.js"></script>
		<script src="../js/registerBody.js"></script>
		
		<script>
			var userID = '<%= user.getUserID() %>';
		
			(function ($) {
				$(document).ready(function() {
					$('#btnDelete').click(function() {
						$('#deleteModal').modal({
							onApprove: function (e) {
							    if (e.hasClass('yes')) {
							    	window.location.href = "/CoStudy/user/userWithdrawAction.do?userID=" + userID;
							    }
							    if (e.hasClass('no')) {
							    }
							},
						}).modal('show');
					});
				});
			}(jQuery));
			
			
		</script>
	</body>
</html>