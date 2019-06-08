<%@page import="cst.dto.UserDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html >

<%
	UserDTO user = null;
	String userNick = null;	

	if(request.getSession().getAttribute("user") != null) {
		user = (UserDTO) request.getSession().getAttribute("user");
		userNick = user.getUserNick();
	}

%>

<head>
<title>Header</title>
</head>
	<body>
	
		<!-- 기본 헤더 ( 로그인 유저 ) -->
		<div class="ui top fixed inverted pointing menu" id="defaultMenu">
      		<div class="ui container">
	        	<a class="header item" href="/CoStudy/dashboard.do">
	          		<i class="pencil alternate icon"></i> 코스터디
	        	</a>
	        	
        		<a class="item" href="/CoStudy/group/viewAll.do">스터디 그룹</a>
        		<a class="item" href="/CoStudy/bbs/list.do?bbs=notice">공지 사항</a>
            	<a class="item" href="/CoStudy/bbs/list.do?bbs=free">자유 게시판</a>
            	<a class="item" href="/CoStudy/chat.do">메시지</a>
            	
            	<div class="ui right item">
            		<div class="ui icon input">
			        	<input type="text" placeholder="검색">
			        	<i class="search link icon"></i>
			      	</div>
            	
            		<div class="ui dropdown item" style="margin-left: 0.5em;">
            			<i class="user outline icon"></i> <%= userNick %>
				    	<i class="dropdown icon"></i>
				    	
				    	<div class="menu">
				      		<a class="item" href="/CoStudy/user/profile.do">프로필</a>
				      		<a class="item" href="/CoStudy/user/nicknameSetting.do">계정 설정</a>
				      		<a class="item" href="/CoStudy/user/logout.do">로그아웃</a>
				    	</div>
            		</div>
			    </div>
			</div>
		</div>
    </body>
		
