<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html >

<head>
<title>LoginBody</title>
</head>
	<body>
    	<div class="ui middle aligned center aligned grid">
      		<div class="column">
        		<h2 class="ui center aligned header">
          			로그인
        		</h2>
        		
        		<form class="ui large form" action="loginAction.do" method="post">
          			<div class="ui stacked segment">
            			<div class="field">
              				<div class="ui left icon input">
                				<i class="user icon"></i>
                				
                				<input name="userID" placeholder="아이디" type="text" />
              				</div>
            			</div>
            
            			<div class="field">
              				<div class="ui left icon input">
                				<i class="lock icon"></i>
                				
                				<input name="userPassword" placeholder="비밀번호" type="password" />
              				</div>
            			</div>
            
            			<div class="ui fluid large red submit button">로그인</div>
        			</div>
          			
          			<div class="ui error message"></div>
        		</form>
        
        		<div class="ui message">처음이신가요?<a href="register.jsp"> 회원가입</a></div>
      		</div>
    	</div>
    </body>
		
