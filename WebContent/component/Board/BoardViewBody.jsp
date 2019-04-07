<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
<!DOCTYPE html >

<%
	String bbsType = request.getParameter("bbs");
	String bbsName = null;

	if(bbsType.equals("free"))
		bbsName = "자유게시판";
	else
		bbsName = "공시사항";
%>

<head>
<title>BbsBody</title>
</head>
	<body>
		<div class="ui vertical stripe segment">
        	<div class="ui middle aligned stackable grid container">
          		<div class="row">
	            	<div class="fifteen wide column" style="text-align:center;">
	            		
	            		<h2 class="ui horizontal divider header"><%= bbsName %></h2>
	            		
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
						    	<tr>
						      		<td>1</td>
						      		<td>test</td>
						      		<td>test</td>
						      		<td>2011-11-11</td>
						      		<td>24</td>
						    	</tr>
						  	</tbody>
						  	<tfoot>
						    	<tr>
						      		<td class="center aligned" colspan="5">
						      			<div class="ui pagination menu">
									        
									        <a class="icon item">
									        	<i class="left chevron icon"></i>
									        </a>
									        
									        <a class="item">1</a>
									        <a class="item">2</a>
									        <a class="item">3</a>
									        <a class="item">4</a>
									        
									        <a class="icon item">
									        	<i class="right chevron icon"></i>
									        </a>
									    </div>
						      		</td>
						    	</tr>
						  	</tfoot>
						</table>
						
						<div style="text-align: right;">
							<a class="ui red button" href="write.do?bbs=<%= bbsType %>">글쓰기</a>
						</div>
	            	</div>
          		</div>
        	</div>
      	</div>
    </body>
		
