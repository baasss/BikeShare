<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
    
    <script>
     function initialize()
     {
      if(document.getElementById('unameInput').value == ""){
    	alert("Values Missing!");
    	return false;
    }
  }   
    </script> 
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
                        <a href="bookabike">Book a bike</a>
                    </li>

                    <li>
                        <a href="StationMap">Station Map</a>
                    </li>
					<li>
                        <a href="returnOrCancel">Return/Cancel Booking</a>
                    </li>
                </ul>
            </div>
            <!-- /.navbar-collapse -->
        </div>
        <!-- /.container -->
    </nav>
<div id="container">
 <h2>RETURN OR CANCEL BOOKING</h2>
   <c:if test="${not empty message}"><div class="message green">${message}</div></c:if>
	<form:form action="/returnOrCancel" method="post" modelAttribute="user" onsubmit="return initialize()">
	<h4>Username: </h4>
	<form:input path="Loggingusername" id="unameInput" ></form:input></br></br>
	
	<input type="submit" value="Submit" id="Submit"  />
	&nbsp;
	</form:form>	
</div>
  
</body>
</html>