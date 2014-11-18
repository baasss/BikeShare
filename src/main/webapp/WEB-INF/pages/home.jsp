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
    	.green { font-weight: bold; color: blue; }
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
    if(document.getElementById('LocationSelect').value == ""){	
	    var mapProp = {
	    center:new google.maps.LatLng(37.3333,-121.9000),
	    zoom:14,
	    mapTypeId:google.maps.MapTypeId.ROADMAP
	      };
    }else{
	    var mapProp = {
	    center:new google.maps.LatLng("${latitude}","${longitude}"),
	    zoom:14,
	    mapTypeId:google.maps.MapTypeId.ROADMAP
	      };
    }
    var map=new google.maps.Map(document.getElementById("googleMap"),mapProp);
    var myLatlng = new google.maps.LatLng("${latitude}","${longitude}");
    
    var marker = new google.maps.Marker({
          position: myLatlng,
          map: map,
          title: 'bike stand'
      });
    if(document.getElementById('LocationSelect').value != ""){
    	document.getElementById('phonenumberInput').disabled = false;
    }
}

    google.maps.event.addDomListener(window, 'load', initialize);
    </script>

  </head>
  <body onload="findselected()">
   <div id="googleMap" style="width:1350px;height:380px;"></div>
	<div id="container">
    <h2>BIKE SHARE</h2>
		<c:if test="${not empty message}"><div class="message green">${message}</div></c:if>

		<form:form modelAttribute="location" action="/loadmap">
			<label for="LocationSelect">Location:</label>
			<form:select path="location" id="LocationSelect" onchange="this.form.submit()">
				<form:option value="">Select the nearest location: </form:option>
				<c:forEach items="${locations}" var="location1">
					<form:option value="${location1}">${location1}</form:option>
				</c:forEach>
			</form:select>
			<br/>

			<br/>
			 <!--  <input type="submit" value="Submit" onclick="initialize()" /> --> 
			
		</form:form>
					<br/>
		<form:form id="user" modelAttribute="user" action="/sendcode">
		<h4>Enter your phone number below to book the bike</h4>
					<form:input path="phonenumber" id="phonenumberInput" disabled="true"></form:input>
					
					<input id="locationInput" name="location" type="hidden" value="${loc}"/>
			<br/>
			<input type="submit" value="Submit" id="submitButton"/>
			
		</form:form>
	</div>

</body>
</html>