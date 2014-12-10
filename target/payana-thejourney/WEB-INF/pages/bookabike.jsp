<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE HTML>
<% 
	response.setHeader("Cache-Control", "no-cache,no-store,private,must-revalidate,max-stale=0,post-check=0,pre-check=0"); // HTTP 1.1.
	response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
	response.setDateHeader("Expires", 0); // Proxies.
%>

<%
	
	
	Cookie[] cookies = request.getCookies();
	boolean flag=false;
	if(cookies !=null)
	{
	
			for(Cookie cookie : cookies)
			{
				String username = null;
    			if(cookie.getName().equals("user")) 
    			{
    				System.out.println("test "+cookie.getName());
    				username = cookie.getValue();
					System.out.println("test "+username);
					flag= true;
    			}
    		}
	}
	
	if(!(flag)) 
	{
	System.out.println("no cookie");
	response.sendRedirect("/payana-thejourney/login");
	}
%>

<html>

  <head>
    <title>Payana</title>
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
    <!-- Bootstrap Core CSS -->
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/business-casual.css" rel="stylesheet">
    
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
    	noavailablebikes
    	if(document.getElementById('noavailablebikes').value != "" && document.getElementById('noavailablebikes').value != null){
    		document.getElementById("mes").style.display = "block";
    	}
    	if(document.getElementById('bikesid0').value != ""){
        	document.getElementById("fb").style.display = "block";
        }
        if(document.getElementById('bikesid1').value != ""){
        	document.getElementById("sb").style.display = "block";
        }
        if(document.getElementById('bikesid2').value != ""){
        	document.getElementById("tb").style.display = "block";
        }
    	if(document.getElementById('enableslot').value != "" && document.getElementById('enableslot').value != null){
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

}

    google.maps.event.addDomListener(window, 'load', initialize);
    </script>
  <script>

  $(function() {

    $( "#datepicker" ).datepicker({
        minDate: '1d',
        maxDate: '+30d'
    });

  });

  </script>
  
  </head>
  
  <body onload="findselected()">
  <div class="brand">Payana</div>
    

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
   <div id="googleMap" style="width:1350px;height:380px;"></div>
	<div id="container">
    <form:form action="/sessionLogout" method="post" modelAttribute="user" >
   
   	 <div style="position: absolute; top: 0; right: 900; width: 1000px; text-align:right;">
    <label >${a}</label>
    <input type="submit" value="Logout" id="submitbutton"/>
  </div>
	</form:form>
    
		
<link rel="stylesheet" href="//code.jquery.com/ui/1.11.2/themes/smoothness/jquery-ui.css">

  <script src="//code.jquery.com/jquery-1.10.2.js"></script>

  <script src="//code.jquery.com/ui/1.11.2/jquery-ui.js"></script>

  <link rel="stylesheet" href="/resources/demos/style.css ">


  <div class="message green">${noavailablebikes}</div>
<div class="message green">${successmessage}</div>
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
										
		<h4 id="mes" style="display:none">Select a bike to see the available slots</h4>
		<table id="bikestable">
		
			<tr>				
				<td id="fb" style="display:none"><input type="radio" name="selbike" value="${bikesid0}" onclick="this.form.submit()">${bikesid0}</td>
				<td id="sb" style="display:none"><input type="radio" name="selbike" value="${bikesid1}" onclick="this.form.submit()">${bikesid1}</td>
				<td id="tb" style="display:none"><input type="radio" name="selbike" value="${bikesid2}" onclick="this.form.submit()">${bikesid2}</td>
			</tr>

		</table>
		
			<input id="noavailablebikes" type="hidden" value="${noavailablebikes}"/>
			<input id="bikeid" type="hidden" value="${bikeid}"/>
			<input id="bikesid0" type="hidden" value="${bikesid0}"/>
			<input id="bikesid1" type="hidden" value="${bikesid1}"/>
			<input id="bikesid2" type="hidden" value="${bikesid2}"/>
			<input id="firstSlot" type="hidden" value="${firstSlot}"/>
			<input id="secondSlot" type="hidden" value="${secondSlot}"/>
			<input id="thirdSlot" type="hidden" value="${thirdSlot}"/>
			<input id="enableslot" type="hidden" value="${enableslot}"/>
			
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
		<input id="Loggingusername" name="Loggingusername" type="hidden" value="${Loggingusername}"/>
			<input style="display:none" type="submit" value="Submit" id="submitButton"/>
			
		</form:form>
	</div>
	
	<!-- jQuery -->
   
	<script src="${pageContext.request.contextPath}/js/jquery.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

</body>
</html>