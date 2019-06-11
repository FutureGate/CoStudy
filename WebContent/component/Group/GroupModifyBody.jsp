<%@page import="java.net.URLEncoder"%>
<%@page import="cst.dto.GroupDTO"%>
<%@page import="cst.dto.UserDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html >

<head>
<%
	UserDTO user = null;
	GroupDTO group = null;
	String groupName = null;
	int isRegistered = 0;
	String userID = null;
	
	if(session.getAttribute("user") != null) {
		user = (UserDTO) session.getAttribute("user");
		
		userID = user.getUserID();
	}
	
	if(request.getAttribute("group") != null) {
		group = (GroupDTO) request.getAttribute("group");
	}
	
	if(request.getSession().getAttribute("user") != null) {
		user = (UserDTO) request.getSession().getAttribute("user");
	}
	
	if(request.getAttribute("isRegistered") != null) {
		isRegistered = (Integer) request.getAttribute("isRegistered");
	}
	
	if(request.getParameter("groupname") != null) {
		groupName = (String) request.getParameter("groupname");
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
		<br />
	
		<div class="ui container">
			<div class="ui secondary pointing menu">
	  				<a class="item" href="/CoStudy/group/view.do?groupname=<%= URLEncoder.encode(groupName, "UTF-8") %>">
	    				그룹 정보
	  				</a>
			  		
			  		<a class="item" href="/CoStudy/group/bbs/list.do?groupname=<%= URLEncoder.encode(groupName, "UTF-8") %>">
			    		그룹 게시판
			  		</a>
			  		
			  		<a class="item active" href="/CoStudy/group/modify.do?groupname=<%= URLEncoder.encode(groupName, "UTF-8") %>">
			    		그룹 정보 수정
			  		</a>
			  		
			  		<a class="item" href="/CoStudy/group/accept.do?groupname=<%= URLEncoder.encode(groupName, "UTF-8") %>">
			    		가입 승인
			  		</a>
			</div>
		</div>
	
		<br />
		<br />
		<br />
		<br />
		<br />
	
    	<div class="ui middle aligned center aligned grid" id="authForm">
      		<div class="column" id="authContent">
        		<h2 class="ui center aligned header">
          			스터디 그룹 정보 수정
        		</h2>
        		
        		<form class="ui large form" action="modifyAction.do" method="post">
          			<div class="ui stacked segment">
          			
                		<input name="groupname"  value="<%= URLEncoder.encode(group.getGroupName(), "UTF-8") %>" type="hidden" />

            			<div class="ui horizontal divider">
            				스터디 시작 시간
            			</div>
            			
            			<div class="field">
            				<div class="ui equal width devided grid">
            					<div class="ui column">
		  							<div class="ui calendar timepicker">
								    	<div class="ui input left icon">
								      		<i class="time icon"></i>
								      		<input type="text" id="studyStart" name="studyStart" placeholder="스터디 시작 시간" />
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
								      		<input type="text" id="studyFinish" name="studyFinish" placeholder="스터디 종료 시간" />
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
            					<input type="hidden" id="initPlace" data-coords="<%= group.getStudyLocation() %>" data-zoom="12" />
            					<div id="map"></div>
            				</div>
            				
              				<div class="ui fluid input">
                				<input id="studyLocation" name="studyLocation" placeholder="스터디 장소" type="hidden" />
              				</div>
            			</div>
            			
            			<br />
            			<div class="ui divider"></div>
            
            			<input type="submit" class="ui fluid large red submit button" value="정보 수정"></input>
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
		
