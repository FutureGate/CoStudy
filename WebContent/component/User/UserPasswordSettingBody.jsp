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
		  		
			  		<a class="item active" href="passwordSetting.do">
			    		비밀번호
			  		</a>
			  		
			  		<a class="item" href="bornSetting.do">
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
          			비밀번호 수정
        		</h2>
        		
        		<form class="ui large form" action="nicknameSettingAction.do" method="post">
          			<div class="ui stacked segment">
          			
          				<div class="ui horizontal divider">
            				비밀번호 수정
            			</div>
            			<div class="field">
              				<div class="ui left icon input">
                				<i class="address card icon"></i>
                				
                				<input name="userNickname" placeholder="닉네임" type="text" />
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
		
