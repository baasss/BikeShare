<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html> 
<head> 
  <meta http-equiv="content-type" content="text/html; charset=UTF-8" /> 
  <title>Google Maps Multiple Markers</title> 
  <script src="http://maps.google.com/maps/api/js?sensor=false" 
          type="text/javascript"></script> 
  <script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
    <!-- Bootstrap Core CSS -->
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/business-casual.css" rel="stylesheet">
</head> 
<style>
    	body { background-color: #eee; font: helvetica; }
    	#container { width: 500px; background-color: #fff; margin: 30px auto; padding: 30px; border-radius: 5px; }
    </style>
<body>
<div class="brand">Bike Share</div>
 <!-- Navigation -->
    <nav class="navbar navbar-default" role="navigation">
        <div class="container">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <!-- navbar-brand is hidden on larger screens, but visible when the menu is collapsed -->
                <a class="navbar-brand" href="index.html">Techno Trends</a>
            </div>
            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav">
                    <li>
                        <a href="home">Home</a>
                    </li>
                    <li>
                        <a href="login">Login</a>
                    </li>
                    <li>
                        <a href="Registration">Register</a>
                    </li>
                    <li>
                        <a href="StationMap">Station Map</a>
                    </li>
					<li>
                        <a href="contact.html">Share Your Bike</a>
                    </li>
                </ul>
            </div>
            <!-- /.navbar-collapse -->
        </div>
        <!-- /.container -->
    </nav>

  <div id="map" style="width: 1350px; height: 400px;"></div>

   <script type="text/javascript">
    var locations = [
      ['SANJOSE', 37.3333, -121.9, "${bike1}"],
      ['MILPITAS',37.432334 ,-121.899574 , "${bike2}"],
      ['SUNNYVALE', 37.3711, -122.0375, "${bike3}"],
      ['FRUITDALE', 37.3128, -121.9358, "${bike4}"],
      ['SANTACLARA', 37.3544, -121.9696, "${bike5}"],
      ['CUPERTINO', 37.32298, -122.032182, "${bike6}"],
      ['SARATOGA', 37.263832, -122.023015, "${bike7}"],
      ['LOSGATOS', 37.235808, -121.962375, "${bike8}"],
      ['PALOALTO', 37.441883, -122.143019, "${bike9}"],
      ['SANCARLOS', 37.507159, -122.260522, "${bike10}"]
      
    ];
	
 
	
var map = new google.maps.Map(document.getElementById('map'), {
      zoom: 10,
      center: new google.maps.LatLng(37.3333,-121.9000),
      mapTypeId: google.maps.MapTypeId.ROADMAP
    });
	

	
var infowindow = new google.maps.InfoWindow({
  });

    var marker, i;

    for (i = 0; i < locations.length; i++) {  
      marker = new google.maps.Marker({
        position: new google.maps.LatLng(locations[i][1], locations[i][2]),
        map: map
      });

      google.maps.event.addListener(marker, 'click', (function(marker, i) {
        return function() {
        	var content='<div class = "MarkerPopUp" style="width: 200px;"><div class = "MarkerContext">'+'<p><center>'+locations[i][0]+'</center>'+'<br>'+'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; AVAILABLE BIKES = '+locations[i][3]+'</p>'+'</div></div>';
          infowindow.setContent(content);
		  infowindow.open(map, marker);
        }
      })(marker, i));
    }
  </script> 
<!-- jQuery -->
   
	<script src="${pageContext.request.contextPath}/js/jquery.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <!-- Script to Activate the Carousel -->
  
</body>
</html>