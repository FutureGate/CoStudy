<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html >

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
			  		
			  		<a class="item" href="bornSetting.do">
			    		생년월일
			  		</a>
			  		
			  		<a class="item active" href="withdraw.do">
			    		회원탈퇴
			  		</a>
				</div>
		</div>
	
    	<div class="ui middle aligned center aligned grid" id="authForm">
      		<div class="column" id="authContent">
        		<h2 class="ui center aligned header">
          			회원 탈퇴
        		</h2>
        		
        		<form class="ui large form" action="nicknameSettingAction.do" method="post">
          			<div class="ui stacked segment">
            
            			<input type="button" id="btnDelete" class="ui fluid large red button" value="탈퇴하기"></input>
        			</div>
          			
          			<div class="ui error message"></div>
        		</form>
      		</div>
    	</div>
    	
    	<br />
		<br />
		<br />
		<br />
		
		
		<div class="ui modal" id="deleteModal">
			<div class="header">
				회원 탈퇴
			</div>
	  		
	  		<div class="content">
	    		탈퇴하시겠습니까? 이 작업은 되돌릴 수 없습니다.
			</div>
			  
		  	<div class="actions">
		  		<button class="ui red button yes ok">네</button>
		    	<button class="ui black button no ok">아니오</button>
  			</div>
		</div>
    </body>
		
