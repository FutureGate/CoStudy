<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html >

<head>
<title>DashboardBody</title>
</head>
	<body>
		<div class="ui vertical stripe segment">
        	<div class="ui middle aligned stackable grid container">
          		<div class="row">
	            	<div class="fifteen wide column" style="text-align:center;">
	            		
	            		
	            		<jsp:include page="./DashboardGroupRegistered.jsp"></jsp:include>
	              
	              		<jsp:include page="./DashboardGroupRegisterWaiting.jsp"></jsp:include>
	              		
	            	</div>
          		</div>
        	</div>
      	</div>
    </body>
		
