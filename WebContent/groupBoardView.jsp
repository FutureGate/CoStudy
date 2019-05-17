<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<%
		if(request.getSession().getAttribute("user") == null) {
			response.sendRedirect("/CoStudy/index.jsp");
		}
	%>

	<meta charset="UTF-8">
	<meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible" />
	<meta name="viewport" content="width=device-width, initial-scale=1">
	
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/semantic-ui@2.4.2/dist/semantic.min.css">
	<link rel="stylesheet" href="/CoStudy/css/mainHeader.css">
	<link rel="stylesheet" href="/CoStudy/css/footer.css">
	<link rel="stylesheet" href="/CoStudy/css/chat.css">
	
	<title>코스터디 :: CoStudy</title>
	
</head>



<body>
	<body id="root">
    	<jsp:include page="./component/General/DashboardHeader.jsp"></jsp:include>
    	
    	<jsp:include page="./component/Group/Board/GroupBoardViewBody.jsp"></jsp:include>
    
    	<jsp:include page="./component/General/Footer.jsp"></jsp:include>
	
		<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
		<script src="https://cdn.jsdelivr.net/npm/semantic-ui@2.4.2/dist/semantic.min.js"></script>
		<script src="/CoStudy/js/defaultHeader.js"></script>
		<script src="/CoStudy/js/chat.js"></script>
		
		<script>
			(function ($) {
				$(document).ready(function() {
					$("#btnDelete").click(function() {
						var isDelete = confirm("삭제하시겠습니까?");
						var bbsType = '<%= request.getParameter("bbs") %>';
						var bbsID = '<%= request.getParameter("bbsID") %>';
						if(isDelete == true) {
							window.location.href = "/CoStudy/bbs/deleteAction.do?bbs=" + bbsType + "&bbsID=" + bbsID;
						}
					});
				});
			}(jQuery));
		</script>
	</body>
	
	
</html>