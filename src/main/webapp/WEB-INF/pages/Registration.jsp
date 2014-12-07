	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>



<!DOCTYPE HTML>
<html>
  <head>
  
 
   
  <body>
   
    <title>Bike Share</title>
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
    <style >
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
    var x = document.getElementById("form").elements.length;
    for(var i =0; i<x-1;i++)
    {
         var field = document.getElementById("form").elements[i].value ;
         if(field == "")
         {
         alert("values missing");
         return false;
         }
         else if (document.getElementById("pwdinput"))
         {
         	if(document.getElementById("pwdinput").value.length<6)
         	{
         alert("password must be atleast 6 characters long");
         return false;
         	}
        	 else if(document.getElementById("pwdinput").value != document.getElementById("cnfpwdinput").value)
        	 {
         alert("Passwords do not match");
         return false;
         	}
         
         }
         
         if(document.getElementById("mailInput").value != "")
         {
        var x = document.getElementById("mailInput").value;
    	var atpos = x.indexOf("@");
   		var dotpos = x.lastIndexOf(".");
    	if (atpos< 1 || dotpos<atpos+2 || dotpos+2>=x.length) {
        alert("Not a valid e-mail address");
        return false;
  		  }
         
         }
         if(document.getElementById("mobileInput").value != "")
         {
         var mobile = document.getElementById("mobileInput").value;
          if (/^\d{10}$/.test(mobile))
            return true;
            else
            {
            alert("Enter valid moile number");
            return false;
            }
         
         }
         
     }
    	return true;
   
    }
    
    </script>
    

<div id="container">
 <h2>BIKE SHARE</h2>
    <c:if test="${found}">  <h2>Username already taken. Enter new details!</h2>  </c:if>
	<form:form id="form" action="/registered" method="post" modelAttribute="user" onsubmit="return initialize()">
	<h4>Name: </h4>
	<form:input path="name" id="nameInput"></form:input></br></br>
	<h4>Email: </h4>
	<form:input path="email" id="mailInput"></form:input></br></br>
	<h4>Username: </h4>
	<form:input path="username" ></form:input></br></br>
	<h4>Password: </h4>
	<form:input path="password" type = "password" id="pwdinput"></form:input></br></br>
	<h4>Confirm Password: </h4>
	<form:input path="confpassword" type = "password" id="cnfpwdinput"></form:input></br></br>
	<h4>Mobile Number:  </h4>
	<form:input path="mobileNo" id="mobileInput"></form:input></br></br>
	<h4>Location:  </h4>
	
			<form:select path="locationInput" id="LocationSelect" >
				<form:option value="">Select the  location: </form:option>
				<c:forEach items="${locations}" var="location1">
					<form:option value="${location1}">${location1}</form:option>
				</c:forEach>
				
			</form:select>
	
	
	</br></br>
	
	<input type="submit" value="Submit" id="Submit"  />
	
</form:form>



</div>
</body>
</html>
