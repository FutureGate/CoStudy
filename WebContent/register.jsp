<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<%
		if(request.getSession().getAttribute("user") != null) {
			response.sendRedirect("/CoStudy/dashboard.do");
		}
	%>
	
	<meta charset="UTF-8">
	<meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible" />
	<meta name="viewport" content="width=device-width, initial-scale=1">
	
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/semantic-ui@2.4.2/dist/semantic.min.css">
	<link rel="stylesheet" href="./css/authBody.css">
	<link rel="stylesheet" href="./css/footer.css">
	
	<title>코스터디 :: 회원가입</title>
	
	<style>
		#btnLogin {
			margin-right: 0.5em;
		}
	</style>
	
</head>

<body>
	<body id="root">
		<jsp:include page="./component/Auth/AuthHeader.jsp"></jsp:include>
    	
    	<jsp:include page="./component/Auth/RegisterBody.jsp"></jsp:include>
    
    	<jsp:include page="./component/General/Footer.jsp"></jsp:include>
	
		<script src="https://code.jquery.com/jquery-2.1.4.js"></script>
		<script src="https://cdn.rawgit.com/mdehoog/Semantic-UI/6e6d051d47b598ebab05857545f242caf2b4b48c/dist/semantic.min.js"></script>
		<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.min.js" integrity="sha256-VazP97ZCwtekAsvgPBSUwPFKdrwD3unUfSGVYrahUqU="  crossorigin="anonymous"></script>
		<script src="https://cdn.jsdelivr.net/npm/semantic-ui@2.4.2/dist/semantic.min.js"></script>
		<script src="/CoStudy/js/defaultHeader.js"></script>
		<script src="../js/registerBody.js"></script>
		
		<script>
			(function ($) {
				$(document).ready(function() {
					$('.datepicker').calendar({
						monthFirst: false,
						type:'date',
						formatter: {
							date: function (date, settings) {
						      	if (!date) return userBorn;
						      	var day = date.getDate();
						      	var month = date.getMonth() + 1;
						      	var year = date.getFullYear();
						      	
						      	return year + '-' + month + '-' + day;
						   	}
						}
					});
				});
			}(jQuery));
			
			
		</script>
	</body>
</html>