<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html> 
<head> 
  <meta http-equiv="content-type" content="text/html; charset=UTF-8" /> 
  <title>Google Maps Multiple Markers</title> 
  <script src="http://maps.google.com/maps/api/js?sensor=false" 
          type="text/javascript"></script> 
</head> 
<body>
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

  
</body>
</html>