<%@page import="cst.dto.UserDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html >

<%
		UserDTO user = null;
	
		if(request.getSession().getAttribute("user") != null) {
			user = (UserDTO) request.getSession().getAttribute("user");
		}
%>

<head>
<title>RegisterBody</title>
</head>
	<body id="registerBody">
		<div class="ui  container">
				<br />
				<br />
				<br />
				<br />
				<br />
				
				<div class="ui secondary pointing menu">
					<a class="item" href="nicknameSetting.do">
		 				닉네임
					</a>
		  		
			  		<a class="item" href="passwordSetting.do">
			    		비밀번호
			  		</a>
			  		
			  		<a class="item active" href="bornSetting.do">
			    		생년월일
			  		</a>
			  		
			  		<a class="item" href="withdraw.do">
			    		회원탈퇴
			  		</a>
				</div>
		</div>
	
    	<div class="ui middle aligned center aligned grid" id="authForm">
      		<div class="column" id="authContent">
        		<h2 class="ui center aligned header">
          			생년월일 수정
        		</h2>
        		
        		<form class="ui large form" action="userBornSettingAction.do" method="post">
          			<div class="ui stacked segment">
          			
          				<div class="ui horizontal divider">
            				생년월일
            			</div>
            			<div class="field">
            				<div class="ui calendar datepicker">
							    <div class="ui left icon input">
							    	<i class="calendar icon"></i>
                				
                					<input name="userID" type="hidden" value="<%= user.getUserID() %>"/>
                					<input id="userBornInput" name="userBorn" placeholder="생년월일" type="text"/>
							    </div>
							</div>
            			</div>
            
            			<br />
            			<div class="ui divider"></div>
            
            			<input type="submit" class="ui fluid large red submit button" value="수정"></input>
        			</div>
          			
          			<div class="ui error message"></div>
        		</form>
      		</div>
    	</div>
    	
    	<br />
		<br />
		<br />
		<br />
    </body>
		
