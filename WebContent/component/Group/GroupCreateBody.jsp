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
<style>
	#map-wrapper {
		margin: 2.5em auto;
		max-width: 960px;
		overflow: hidden;
	}
	
	#map {
		float: right;
		width: 100%;
		height: 200px;
	}
	
	@media screen and (max-width: 768px) {
		#places, #map {
			float: none;
			width: auto;
			margin-bottom: 1em;
		}
		
		#places {
			text-align: center;
		}
		
		#map {
			width: 100%;
		}
	}
	
</style>

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
		  							<div class="ui calendar timepicker">
								    	<div class="ui input left icon">
								      		<i class="time icon"></i>
								      		<input type="text" name="studyStart" placeholder="스터디 시작 시간">
								    	</div>
								  	</div>
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
		  							<div class="ui calendar timepicker">
								    	<div class="ui input left icon">
								      		<i class="time icon"></i>
								      		<input type="text" name="studyFinish" placeholder="스터디 종료 시간">
								    	</div>
								  	</div>
            					</div>
            				</div>
              				
            			</div>
            			
            			<br />
            			
            			<div class="ui horizontal divider">
            				스터디 장소
            			</div>
            			
            			<div class="field">
            				<div class="map-wrapper">
            					<input type="hidden" id="initPlace" data-coords="37.5650172,126.8494668" data-zoom="12" />
            					<div id="map"></div>
            				</div>
            				
              				<div class="ui fluid input">
                				<input id="studyLocation" name="studyLocation" placeholder="스터디 장소" type="hidden" />
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
		
