<%@page import="cst.dto.GroupDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<%
		GroupDTO group = null;
		String studyStart = null;
		String studyFinish = null;
		String studyLocation = null;

		if(request.getAttribute("group") != null) {
			group = (GroupDTO) request.getAttribute("group");
			
			studyStart = group.getStudyStart();
			studyFinish = group.getStudyFinish();
			studyLocation = group.getStudyLocation();
		}
	
		if(request.getSession().getAttribute("user") == null) {
			response.sendRedirect("/CoStudy/index.jsp");
		}
	%>
	
	<meta charset="UTF-8">
	<meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible" />
	<meta name="viewport" content="width=device-width, initial-scale=1">
	
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/semantic-ui@2.4.2/dist/semantic.min.css">
	<link rel="stylesheet" href="../css/authBody.css">
	<link rel="stylesheet" href="../css/footer.css">
	
	<title>코스터디 :: CoStudy</title>
	
	<style>
		#btnLogin {
			margin-right: 0.5em;
		}
	</style>
	
</head>

<body>
	<body id="root">
		<jsp:include page="./component/General/DashboardHeader.jsp"></jsp:include>
    	
    	<jsp:include page="./component/Group/GroupModifyBody.jsp"></jsp:include>
    
    	<jsp:include page="./component/General/Footer.jsp"></jsp:include>
	
		<script src="https://code.jquery.com/jquery-2.1.4.js"></script>
		<script src="https://cdn.rawgit.com/mdehoog/Semantic-UI/6e6d051d47b598ebab05857545f242caf2b4b48c/dist/semantic.min.js"></script>
		<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.min.js" integrity="sha256-VazP97ZCwtekAsvgPBSUwPFKdrwD3unUfSGVYrahUqU="  crossorigin="anonymous"></script>
		<script src="https://cdn.jsdelivr.net/npm/semantic-ui@2.4.2/dist/semantic.min.js"></script>
		<script src="../js/registerBody.js"></script>
		<script src="/CoStudy/js/defaultHeader.js"></script>
		<script>
			(function ($) {
				var map;
				var markers = [];
				var studyStart = '<%= studyStart %>';
				var studyFinish = '<%= studyFinish %>';
				var studyLocation = '<%= studyLocation %>';
				
				$(document).ready(function() {
					var createMap = function( coords, zoom ) {
						var coordsParts = coords.split( "," );
						if( typeof google !== "undefined" ) {
							var options = {
								zoom: zoom,
								center: new google.maps.LatLng( coordsParts[0], coordsParts[1] ),
								mapTypeId: google.maps.MapTypeId.ROADMAP,
								disableDefaultUI: true
							};
							
							map = new google.maps.Map( document.getElementById( "map" ), options );
								
							var marker = new google.maps.Marker({
								position: new google.maps.LatLng( coordsParts[0], coordsParts[1] ),
								map: map
							});
							
							map.addListener('center_changed', function() {
								 window.setTimeout(function() {
					                  var center = map.getCenter();
					                  marker.setPosition(center);
					                  
					                  $('#studyLocation').val(center.lat() + ',' + center.lng());
					                }, 100);
								
							});
					
						}
					};
					
					createMap( $('#initPlace').data( "coords" ), $('#initPlace').data( "zoom" ) );
					
					$('.timepicker').calendar({
						ampm: false,
					  	type: 'time'
					});
					
					$('#studyStart').val(studyStart);
					$('#studyFinish').val(studyFinish);
					$('#studyLocation').val(studyLocation);
				});
			}(jQuery));
		</script>
		
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
		<script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key= AIzaSyBRd5iHCYVNcTPO-Wnp46ct3jdJi9ZM9_s"></script>
	</body>
</html>