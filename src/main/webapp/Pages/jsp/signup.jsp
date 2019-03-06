<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%-- <jsp:include page="/GetDataServlet"/> --%>
<%@ page import="com.zilker.onlinejobsearch.config.Config"%>
 <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<link rel="stylesheet" href="${Config.BASE_PATH}Pages/css/login.css">
<link rel="stylesheet" href="${Config.BASE_PATH}Pages/css/navbar.css">
<link rel="stylesheet" href="${Config.BASE_PATH}Pages/css/tags.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
	<link href="https://fonts.googleapis.com/css?family=Lato" rel="stylesheet">
<title>LOGIN/SIGNUP</title>
</head>


<body onload="viewsignup()">

	<div class="container">
 		
		<div class="container__navbar">
			<ul class="navbar__list">
				<li style="float: left"><i class="fa fa-search" aria-hidden="true"></i></li>
				<li style="float: left">JOB HUNT</li>
			</ul>
		</div> 

	<div class="container__formcontainer">

		<div id="myDIV"  class="formcontainer__nav" class="row col-25">
			<button class="button btn active col-xs-12 col-md-12" type="submit" onclick="viewlogin()">LOGIN</button><button class="btn button col-xs-12 col-md-12" type="submit" onclick="viewsignup()">SIGNUP</button>
		</div>
		
		<div class="formcontainer__forms">
		
		
			<div class="forms__loginform">
				<form:form action="${Config.BASE_PATH}users-login" modelAttribute="login"  id="login" name="login" onsubmit="return loginForm()" method="post">
					
					<div class="login col-xs-12 col-md-12">
						
						
						<div class="login__field col-xs-12 col-md-12">
						<label for="email" class="field__entry row col-25"><b>EMAIL*</b></label> 
						<form:input class="field__input row col-75" type="email"
							placeholder="Your Email ID.." path="email" name="email"/> 
							<span class="error"><p id="log_em_error"></p></span> 
						</div>
					
						<div class="login__field col-xs-12 col-md-12">
							<label for="psw" class="field__entry row col-25"><b>PASSWORD*</b></label> 
							<form:input class="field__input row col-75" type="password"
							placeholder="Enter Password" path="password" name="psw"/> 
							 <span class="error"><p id="log_psw_error"></p></span> 
						</div>
					
					<div class="error" id="loginError">
					<c:if test="${loginError == 'error'}">
					<c:out value="USERNAME OR PASSWORD INCORRECT!!!"/>
					</c:if>
					</div>
					
					<div class="login__field col-xs-12 col-md-12">
							<label for="checkbox"><input type="checkbox"checked="checked"
							name="remember">Remember me</label>
					</div>
					<div class="loginform__nav">
						<form:button class="button col-xs-12 col-md-12" type="submit">LOGIN</form:button>
						<form:button type="reset"  class="button cancelbtn col-xs-12 col-md-12">CANCEL</form:button>
					</div>
				
					</div>
				</form:form>
			</div>
		
		
		<div class="signupforms" id="hideonlogin">
			
			
			<form action="${Config.BASE_PATH}users" id="signup" name="signup" onsubmit="return registerForm()"
				method="post">
				<div class="signupforms__signup">
				
			 	
				
				<div id="defaultfields">
					
					<div class="signup__nav">
							<div id="admin">
								<button type="button" class="button field__entry" onclick="viewadminfields()">REGISTER
									AS ADMIN OF A COMPANY?</button>
							</div>
					</div>
					
					
					<div class="signup__field col-xs-12 col-md-12">
						 <label for="uname" class="field__entry row col-25"><b>USERNAME*</b></label>
						 <input type="text" class="field__input row col-75" name="userName"  placeholder="Your name.."/> 
						 <span class="error"><p id="reg_name_error"></span> 
					</div>
					
					
					<div class="signup__field col-xs-12 col-md-12">		 
							<label for="email" class="field__entry row col-25"><b>EMAIL*</b></label> 
							<input type="email" class="field__input row col-75" name="email"  placeholder="Your email id.."/>
							<span class="error"><p id="reg_email_error"></p></span> 
					</div>		
							
					<div class="signup__field col-xs-12 col-md-12">
							 <label for="psw"
							class="field__entry row col-25"><b>PASSWORD*</b></label> 
							<input class="field__input row col-75" type="password"  placeholder="Enter Password"
							name="psw"/> 
							 <span class="error"><p id="reg_psw_error"></p></span> 
					</div>
							
					<div class="signup__field col-xs-12 col-md-12">
							<label for="cpsw" class="field__entry row col-25"><b>CONFIRM PASSWORD*</b></label> 
							<input class="field__input row col-75" type="password"
							placeholder="Reenter Password" name="cpsw" />
							 <span class="error"><p id="reg_cpsw_error"></p></span> 
					</div>
						
					
					
					<div class="signup__field col-xs-12 col-md-12">
						<label for="cname" class="field__entry row col-25"><b>COMPANY NAME*</b></label>
						<input type="text" class="field__input row col-75" name="companyName" placeholder="Company name.."/>
						<span class="error"><p id="reg_comp_error"></p></span> 
					</div>
					
					<div class="signup__field col-xs-12 col-md-12">	
						<label for="designation" class="field__entry row col-25"><b>DESIGNATION*</b></label>
						<input type="text" class="field__input row col-75" name="designation" placeholder="Your designation.."/>
						<span class="error"><p id="reg_des_error"></p></span> 
					</div>
						
					<div class="signup__field col-xs-12 col-md-12">
						<label for="skills" class="field__entry row col-25"><b>ADD SKILLS TO YOUR PROFILE</b></label>
						<input type="hidden" id="skillset" name="skillset" value=""/>
						
						 <div class="tags-input field__input row col-75" id="skill" data-name="tags-input">
						 </div>
						
						
					</div>
					
					<div class="signup__nav">
							<button type="submit" class="button col-xs-12 col-md-12">REGISTER</button>
							<button type="reset"  class="button cancelbtn col-xs-12 col-md-12">CANCEL</button>
					</div>
						
					</div>
				</div>
			</form>
			
			
			<div id="adminoptional">
				
				<form action="${Config.BASE_PATH}register/admin"  name="admin" id="signupadmin" onsubmit="return validateForm()" method="post">
					
					<div class="signupadmin">
						
						<div class="signupadmin__nav">
							<div id="user">
								<button type="button" class="field__entry button" onclick="viewuserfields()">REGISTER
									AS USER?</button>
							</div>
						</div>
						
						<div class="signupadmin__field col-xs-12 col-md-12">	
						<label for="uname" class="field__entry row col-25"><b>USERNAME*</b></label> 
						<input type="text" class="field__input row col-75" name="userName"placeholder="Your name.."> 
					    <span class="error"><p id="name_error"></p></span> 
						</div>
						
						<div class="signupadmin__field col-xs-12 col-md-12">	
						<label for="email"class="field__entry row col-25"><b>EMAIL*</b></label> 
						<input type="email"class="field__input row col-75" name="email" placeholder="Your email id..">
						 <span class="error"><p id="email_error"></p></span> 
						</div>
						
						<div class="signupadmin__field col-xs-12 col-md-12">	
						<label for="psw" class="field__entry row col-25"><b>PASSWORD*</b></label> 
						<input class="field__input row col-75" type="password" placeholder="Enter Password" name="psw">
						<span class="error"><p id="psw_error"></p></span>
						</div>
						
						<div class="signupadmin__field col-xs-12 col-md-12">	
						<label for="psw" class="field__entry row col-25"><b>CONFIRM PASSWORD*</b></label>
						<input class="field__input row col-75" type="password" placeholder="Reenter Password" name="cpsw">
 						<span class="error"><p id="cpsw_error"></p></span>
						</div>
						
						
						<div class="signupadmin__field col-xs-12 col-md-12">	
						<label for="select-company" class="field__entry row col-25"><b>COMPANY NAME*</b></label>
						
							<select id="select" class="select row col-25" name="companyName">
								 <option value=" ">Select a Company Name</option>
								<c:forEach var="comp" items="${companies}">
									<option value="${comp.getCompanyId()}"><c:out
											value="${comp.getCompanyName()}" /></option>
								</c:forEach>
								
							</select>
							<button class="button field__entry col-xs-12 col-md-12" type="button" id="add" onclick="openForm()">NEW</button>
							<span class="error"><p id="select_error"></p></span>
							
						
						</div>
						
						<div class="signupadmin__nav">	
							<button type="submit" class="button col-xs-12 col-md-12">REGISTER</button>
							<button type="reset"  class="button cancelbtn col-xs-12 col-md-12">CANCEL</button>
						</div>
					</div>
				</form>
			</div>
			
			<br>

		</div>
		</div>
	</div>
	<div class="container__form-popup" id="myForm">
		<form action="${Config.BASE_PATH}companies" class="form-container" method="post">
			<h3>ADD NEW COMPANY</h3>
			<input type="text" placeholder="Enter new Company" name="companyName"
				required>
				<input type="text" placeholder="Enter Company Website URL" name="websiteUrl"
				required>
				<input type="text" placeholder="Give the URL of your company logo" name="companyLogo"
				required>
			<button type="submit" id="addcomp" class="btn">ADD COMPANY</button>
			<button type="button" class="cancel btn" onclick="closeForm()">CLOSE</button>
		</form>
	</div>
</div>
	
</body>
<script src="${Config.BASE_PATH}Pages/js/form.js"></script>
<script src="${Config.BASE_PATH}Pages/js/styles.js"></script>
<script src="${Config.BASE_PATH}Pages/js/validate.js"></script>
<script src="${Config.BASE_PATH}Pages/js/tags.js"></script>

</html>