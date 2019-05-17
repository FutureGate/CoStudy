<%@page import="cst.dto.UserDTO"%>
<%@page import="cst.dto.BoardDTO"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
<!DOCTYPE html >

<%
	int pageNumber = 1;
	int userLevel = 0;
	
	UserDTO user = null;
	String groupName = null;
	
	if(session.getAttribute("user") != null) {
		user = (UserDTO) session.getAttribute("user");
		
		userLevel = user.getUserLevel();
	}
	
	if(request.getParameter("pageNumber") != null) {
		pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
	}
	
	if(request.getParameter("groupname") != null) {
		groupName = request.getParameter("groupname");
	}
	
	int index = (((pageNumber-1)/5) + 1)*5;
	String isPrevClass = "";
	String prevURL = "";
	
	boolean isNext = false;
	String isNextClass = "";
	String nextURL = "";
	
	if(index == 5) {
		isPrevClass = "disabled";
		prevURL = "#";
	} else {
		//prevURL = "list.do?bbs=" + bbsType + "&pageNumber=" + (index-5);
	}
	
	if(request.getAttribute("isNext") != null) {
		isNext = (Boolean) request.getAttribute("isNext");
	}
	
	if(isNext == false) {
		isNextClass = "disabled";
		nextURL = "#";
	} else {
		//nextURL = "list.do?bbs=" + bbsType + "&pageNumber=" + (index+1);
	}
%>

<head>
<title>BbsBody</title>
</head>
	<body>
		<jsp:include page="./GroupBoardHeader.jsp"></jsp:include>
		
		<div class="ui vertical stripe segment">
        	<div class="ui middle aligned stackable grid container">
          		
          		<div class="row">
	            	<div class="fifteen wide column" style="text-align:center;">
	            		
	            		<!-- 게시판 표시 -->
   						<table class="ui selectable red table center aligned">
						 	<thead>
						    	<tr>
						    		<th class="one wide">번호</th>
						    		<th class="eight wide">제목</th>
						    		<th class="three wide">작성자</th>
						    		<th class="three wide">작성일</th>
						    		<th class="one wide">조회수</th>
						  		</tr>
						  	</thead>
						  	<tbody>
						    	
						    		<c:forEach items="${boardList}" var="bbs">
						    			<tr>
							    			<td>${bbs.boardID}</td>
							    			<td><a href="view.do?groupname=<%= groupName %>&bbsID=${bbs.boardID}">${bbs.boardTitle}</a></td>
							    			<td>${bbs.userNick}</td>
							    			<td>${bbs.boardDate}</td>
							    			<td>${bbs.boardHit}</td>
							    		</tr>
						    		</c:forEach>
						  	</tbody>
						  	<tfoot>
						    	<tr>
						      		<td class="center aligned" colspan="5">
						      			<div class="ui pagination menu">

									        <a class="icon item <%= isPrevClass %>" href="<%= prevURL %>">
									        	<i class="left chevron icon"></i>
									        </a>
									        
									        <% 
									        	for(int i=index-4; i<=index; i++) {
									        %>
									        
									        <a class="item" href="list.do?bbs=&pageNumber=<%= i %>"><%= i %></a>
									        
									        <%
									        	}
									        %>
									        
									        <a class="icon item <%= isNextClass %>" href="<%= nextURL %>">
									        	<i class="right chevron icon"></i>
									        </a>
									    </div>
						      		</td>
						    	</tr>
						  	</tfoot>
						</table>
						
						<div style="text-align: right;">
								<a class="ui red button" href="write.do?groupname=<%= groupName %>">글쓰기</a>
						</div>
	            	</div>
          		</div>
        	</div>
      	</div>
    </body>
		
