<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html >

<head>
<title>HeaderSidebar</title>
</head>
	<body>
		<!-- 네비게이션 바 (상단고정) -->
		<div class="ui top fixed inverted pointing menu" id="mainMenu">
      		<div class="ui container">
	        	<a class="header item" href="#root">
	          		<i class="pencil alternate icon"></i> 코스터디
	        	</a>
        	
        		<a class="item" href="#aboutPage">소개</a>
            	<a class="item" href="#studyGroupPage">스터디 그룹</a>
            	<a class="item" href="#contactPage">문의</a>
            	
            	<div class="right item">
             		<a class="ui inverted button" id="btnLogin" href="login.jsp">로그인</a>
             		<a class="ui inverted button" id="btnRegister" href="register.jsp">회원가입</a>
           		</div>
          	</div>
        </div>
		
		<!-- 모바일 화면 사이드바 메뉴 -->
        <div class="ui vertical inverted sidebar menu" id="mainMenuSidebar">
	    	<a class="active item">
	      		<i class="pencil alternate icon"></i> 코스터디
	      	</a>
	     	<a class="item" href="#aboutPage">소개</a>
           	<a class="item" href="#studyGroupPage">스터디 그룹</a>
           	<a class="item" href="#contactPage">문의</a>
	    </div>
    </body>
		
