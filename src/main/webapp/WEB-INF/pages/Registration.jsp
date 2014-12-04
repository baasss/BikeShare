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
    </head>
    
<body>
<div id="container">
 <h2>BIKE SHARE</h2>
 
	<form:form action="/registered" method="post" modelAttribute="user">
	<h4>Name: </h4>
	<form:input path="name" id="nameInput"></form:input></br></br>
	<h4>Email: </h4>
	<form:input path="email" ></form:input></br></br>
	<h4>Username: </h4>
	<form:input path="username" ></form:input></br></br>
	<h4>Password: </h4>
	<form:input path="password" ></form:input></br></br>
	<h4>Number of Owned Bikes: </h4>
	<form:input path="bikes_owned" ></form:input></br></br>
	<h4>Mobile Number:  </h4>
	<form:input path="mobileNo" ></form:input></br></br>
	
	
	<input type="submit" value="Submit" id="submitButton"/>
	
</form:form>



</div>
</html>
