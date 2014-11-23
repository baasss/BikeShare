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
    <script>
     function initialize()
     {
      if(document.getElementById('unameInput').value == "" || document.getElementById('pwdInput').value == "" ){
    	alert("Values Missing!");
    	return false;
    }
     }
    
    </script>
    
<body>
<div id="container">
 <h2>BIKE SHARE</h2>
   
	<form:form action="/validating" method="post" modelAttribute="user" onsubmit="return initialize()">
	<h4>Username: </h4>
	<form:input path="Loggingusername" id="unameInput" ></form:input></br></br>
	<h4>Password: </h4>
	<form:input path="Loggingpassword" id="pwdInput" ></form:input></br></br>
	<input type="submit" value="Submit" id="Submit"  />
	&nbsp;
	<a href ="http://localhost:8080/bikeshare-1/Registration"><span><input type="button" value="Register"></span></a>
	
	</form:form>

	
</div>
</html>
