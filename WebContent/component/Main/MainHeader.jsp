<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html >

<head>
<title>Header</title>
</head>
	<body>
		<!-- 메인페이지 헤더 -->
    	<div class="pusher">
      		<div class="ui inverted vertical masthead center aligned segment">
        		<div class="ui container">
          			<div class="ui large secondary inverted pointing menu">
          			
          				<!-- 모바일 화면 사이드바 활성화 버튼 -->
            			<a class="toc item">
            				<i class="sidebar icon" id="btnSidebarToggler"></i>
            			</a>
            			
            			<!-- 메뉴 아이템 -->
            			<a class="header item" href="#root">
            				<i class="pencil alternate icon"></i> 코스터디
            			</a>
			            <a class="item" href="#aboutPage">소개</a>
			            <a class="item" href="#studyGroupPage">스터디 그룹</a>
			            <a class="item" href="#contactPage">문의</a>
	            		
	            		<!-- 우측 정렬 메뉴 아이템 -->
	            		<div class="right item">
	              			<a class="ui inverted button" id="btnLogin" href="login.jsp">로그인</a>
             		<a class="ui inverted button" id="btnRegister" href="register.jsp">회원가입</a>
	            		</div>
          			</div>
        		</div>
        	
        		<!-- 메인 헤더 내용 -->
	        	<div class="ui text container">
	          		<h1 class="ui inverted header">Co-Study</h1>
	          		<h2>코스터디와 함께 공부를 시작하세요</h2>
	          		
	          		<a class="ui huge red button" href="register.jsp">
	            		시작하기 <i class="right arrow icon"></i>
	          		</a>
	        	</div>
   			</div>
		</div>
		
		
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
    </body>
		
