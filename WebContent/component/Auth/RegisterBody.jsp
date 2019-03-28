<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html >

<head>
<title>RegisterBody</title>
</head>
	<body id="registerBody">
		<br />
		<br />
		<br />
		<br />
	
    	<div class="ui middle aligned center aligned grid" id="authForm">
      		<div class="column" id="authContent">
        		<h2 class="ui center aligned header">
          			코스터디 회원가입
        		</h2>
        		
        		<form class="ui large form" action="registerAction.do" method="post">
          			<div class="ui stacked segment">
          			
          				<div class="ui horizontal divider">
            				아이디
            			</div>
            			<div class="field">
              				<div class="ui left icon input">
                				<i class="user icon"></i>
                				
                				<input name="userID" placeholder="아이디" type="text" />
              				</div>
            			</div>
            
            			<br />
            			
            			<div class="ui horizontal divider">
            				비밀번호
            			</div>
            			<div class="field">
              				<div class="ui left icon input">
                				<i class="lock icon"></i>
                				
                				<input name="userPassword" placeholder="비밀번호" type="password" />
              				</div>
            			</div>
            			
            			<div class="field">
              				<div class="ui left icon input">
                				<i class="lock icon"></i>
                				
                				<input name="userPassword2" placeholder="비밀번호 확인" type="password" />
              				</div>
            			</div>
            			
            			<br />
            			
            			<div class="ui horizontal divider">
            				닉네임
            			</div>
            			
            			<div class="field">
              				<div class="ui left icon input">
                				<i class="address card icon"></i>
                				
                				<input name="userNick" placeholder="닉네임" type="text" />
              				</div>
            			</div>
            			
            			<br />
            			
            			<div class="ui horizontal divider">
            				이메일
            			</div>
            			<div class="field">
              				<div class="ui left icon input">
                				<i class="envelope outline icon"></i>
                				
                				<input name="userEmail" placeholder="이메일" type="email" />
              				</div>
            			</div>
            			
            			<br />
            			
            			<div class="ui horizontal divider">
            				생년월일
            			</div>
            			
            			<div class="field">
            				<div class="ui equal width devided grid">
            					<div class="ui column">
            						<input name="userYear" placeholder="년" type="number" />
            					</div>
            					<div class="ui column">
            						<input name="userMonth" placeholder="월" type="number" />
            					</div>
            					<div class="ui column">
            						<input name="userDay" placeholder="일" type="number" />
            					</div>
            				</div>
              				
            			</div>
            			
            			<br />
            			
            			<div class="ui horizontal divider">
            				성별
            			</div>
            			
            			<div class="field">
            				<div class="ui equal width devided grid">
            					<div class="ui column">
            						<div class="ui radio checkbox">
									  	<input type="radio" name="userGender" value="남자">
									  	<label>남자</label>
									</div>
            					</div>
            					<div class="ui column">
            						<div class="ui radio checkbox">
									  	<input type="radio" name="userGender" value="여자">
									  	<label>여자</label>
									</div>
            					</div>
            				</div>
              				
            			</div>
            			
            			<br />
            			<div class="ui divider"></div>
            
            			<input type="submit" class="ui fluid large red submit button" value="회원가입"></input>
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
		
