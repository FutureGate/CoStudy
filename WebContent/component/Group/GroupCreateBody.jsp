<%@page import="cst.dto.UserDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html >

<head>
<%
	UserDTO user = null;
	
	String userID = null;
	
	if(session.getAttribute("user") != null) {
		user = (UserDTO) session.getAttribute("user");
		
		userID = user.getUserID();
	}
%>

<title>RegisterBody</title>
</head>
	<body id="registerBody">
		<br />
		<br />
		<br />
		<br />
		<br />
	
    	<div class="ui middle aligned center aligned grid" id="authForm">
      		<div class="column" id="authContent">
        		<h2 class="ui center aligned header">
          			스터디 그룹 생성
        		</h2>
        		
        		<form class="ui large form" action="createGroupAction.do" method="post">
          			<div class="ui stacked segment">
          			
          				<input name="groupMaster" value="<%= userID %>" type="hidden" />
          			
          				<div class="ui horizontal divider">
            				그룹 이름
            			</div>
            			<div class="field">
              				<div class="ui left icon input">
                				<i class="user icon"></i>
                				
                				<input name="groupName" placeholder="그룹 이름" type="text" />
              				</div>
            			</div>
            
            			<br />
            			
            			<div class="ui horizontal divider">
            				스터디 시작 시간
            			</div>
            			
            			<div class="field">
            				<div class="ui equal width devided grid">
            					<div class="ui column">
            						<input name="studyStartHour" placeholder="시" type="number" />
            					</div>
            					<div class="ui column">
            						<input name="studyStartMin" placeholder="분" type="number" />
            					</div>
            				</div>
              				
            			</div>
            			
            			<br />
            			
            			<div class="ui horizontal divider">
            				스터디 종료 시간
            			</div>
            			
            			<div class="field">
            				<div class="ui equal width devided grid">
            					<div class="ui column">
            						<input name="studyFinishHour" placeholder="시" type="number" />
            					</div>
            					<div class="ui column">
            						<input name="studyFinishMin" placeholder="분" type="number" />
            					</div>
            				</div>
              				
            			</div>
            			
            			<br />
            			
            			<div class="ui horizontal divider">
            				스터디 장소
            			</div>
            			
            			<div class="field">
              				<div class="ui left icon input">
                				<i class="user icon"></i>
                				
                				<input name="studyLocation" placeholder="스터디 장소" type="text" />
              				</div>
            			</div>
            			
            			<br />
            			<div class="ui divider"></div>
            
            			<input type="submit" class="ui fluid large red submit button" value="그룹 생성"></input>
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
		