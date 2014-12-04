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
    	document.getElementById('datepicker').disabled = false;
    }
    if(document.getElementById('datepicker').value != ""){
    	document.getElementById("availtable").style.display = "block"; 
    	document.getElementById("mess").style.display = "block"; 
    	document.getElementById("sendmessage").style.display = "block";
    	document.getElementById("submitButton").style.display = "block";
        if(document.getElementById('firstSlot').value == "True"){
        	document.getElementById("fs").style.display = "block";
        }
        if(document.getElementById('secondSlot').value == "True"){
        	document.getElementById("ss").style.display = "block";
        }
        if(document.getElementById('thirdSlot').value == "True"){
        	document.getElementById("ts").style.display = "block";
        }
    }

}

    google.maps.event.addDomListener(window, 'load', initialize);
    </script>

  </head>
  
  <body onload="findselected()">

   <div id="googleMap" style="width:1350px;height:380px;"></div>
	<div id="container">
    <h2>BIKE SHARE</h2>
		<c:if test="${not empty message}"><div class="message green">${successmessage}</div></c:if>
		
<link rel="stylesheet" href="//code.jquery.com/ui/1.11.2/themes/smoothness/jquery-ui.css">

  <script src="//code.jquery.com/jquery-1.10.2.js"></script>

  <script src="//code.jquery.com/ui/1.11.2/jquery-ui.js"></script>

  <link rel="stylesheet" href="/resources/demos/style.css ">

  <script>

  $(function() {

    $( "#datepicker" ).datepicker({
        minDate: '0d',
        maxDate: '+1d'
    });

  });

  </script>

		<form:form modelAttribute="location" action="/loadmap" >
			<label for="LocationSelect">Location:</label>
			<form:select path="location" id="LocationSelect" onchange="this.form.submit()">
				<form:option value="">Select the nearest location: </form:option>
				<c:forEach items="${locations}" var="location1">
					<form:option value="${location1}">${location1}</form:option>
				</c:forEach>
			</form:select>
			<br/>
			<p>Date: <form:input type="text" path="preffered_date" id="datepicker" value="${preffered_date}" onchange="this.form.submit()"
										disabled="true"></form:input></p>
			<input id="firstSlot" type="hidden" value="${firstSlot}"/>
			<input id="secondSlot" type="hidden" value="${secondSlot}"/>
			<input id="thirdSlot" type="hidden" value="${thirdSlot}"/>
		</form:form>
<form:form id="user" modelAttribute="user" action="/sendcode">
	
		<table id="availtable" style="display:none">
			<tr>
				<td>${bikeid}</td>
				<td id="fs" style="display:none"><input type="radio" name="selslot" value="fslot" checked="checked">08AM to 10AM</td>
				<td id="ss" style="display:none"><input type="radio" name="selslot" value="sslot">10AM to 12PM</td>
				<td id="ts" style="display:none"><input type="radio" name="selslot" value="tslot">12PM to 2PM</td>
			</tr>

		</table>
		
		
		
		<h4 id="mess" style="display:none">Select a mode to receive the bike access code</h4>
			<table id="sendmessage" style="display:none">
			<tr>
				<td><input type="radio" name="sendcode" value="sendmessage" checked="checked">Message</td>
				<td><input type="radio" name="sendcode" value="sendmail">Mail</td>
			</tr>

		</table>
			<input style="display:none" type="submit" value="Submit" id="submitButton"/>
			
		</form:form>
	</div>

</body>
</html>