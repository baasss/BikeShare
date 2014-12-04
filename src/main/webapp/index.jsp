<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE HTML>
<html>
  <head>
    <title>Bike Share</title>
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
    <style>
    	body { background-color: #eee; font: helvetica; }
    	#container { width: 500px; background-color: #fff; margin: 30px auto; padding: 30px; border-radius: 5px; }
    	.green { font-weight: bold; color: red; }
    	.message { margin-bottom: 10px; }
    	label {width:70px; display:inline-block;}
    	form {line-height: 160%; }
    	.hide { display: none; }
    </style>
    <head>
    <script
    src="http://maps.googleapis.com/maps/api/js?key=AIzaSyClkmSPNsPZPdqGrcVnsvakV7HeslxbgRY&sensor=false">
    </script>

    <script>
    function initialize()
    {
    var myLatlng = new google.maps.LatLng(37.3393857,-121.894955499999998);
    var mapProp = {
      center:new google.maps.LatLng(37.3393857,-121.894955499999998),
      zoom:10,
      mapTypeId:google.maps.MapTypeId.ROADMAP
      };
    var map=new google.maps.Map(document.getElementById("googleMap"),mapProp);
    var marker = new google.maps.Marker({
          position: myLatlng,
          map: map,
          title: 'bike stand'
      });
}

    google.maps.event.addDomListener(window, 'load', initialize);
    </script>
  </head>
  <body>
   <div id="googleMap" style="width:500px;height:380px;"></div>
	<div id="container">
    <h2>BIKE SHARE</h2>
		<c:if test="${not empty message}"><div class="message green">${message}</div></c:if>

		<form:form modelAttribute="Location">
			<label for="LocationSelect">Location:</label>
			<form:select path="location" id="LocationSelect">
				<form:option value="">Select the nearest location: </form:option>
				<c:forEach items="${locations}" var="location">
					<form:option value="${location}">${location}</form:option>
				</c:forEach>
			</form:select>
			<br/>

			<br/>
			<input type="submit" value="Submit" />
		</form:form>
	</div>
</body>
</html>