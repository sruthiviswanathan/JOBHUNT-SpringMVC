<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%> 	
	<%@ page import="com.zilker.onlinejobsearch.config.Config"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="${Config.BASE_PATH}Pages/css/forms.css">
<link rel="stylesheet" href="${Config.BASE_PATH}Pages/css/navbar.css">
<link href="https://fonts.googleapis.com/css?family=Lato" rel="stylesheet">
<title>POST JOB</title>
</head>

<body>
		<%
			if(session.getAttribute("email")==null){
				response.sendRedirect("http://localhost:8080/JobSearchApplication/index.jsp");
			}
		%> 
	<div class="container">
	
		 <div id="mySidenav" class="container__sidenav">
                        <div class="sidenav__items">
                        <a href="javascript:void(0)" class="closebtn" onclick="closeNav()">&times;</a>
						<a href="${Config.BASE_PATH}PostJobServlet">PUBLISH NEW VACANCY</a>
						<a href="${Config.BASE_PATH}ViewAppliedUsersServlet">VIEW INTERESTED USERS</a> 
						<a href="${Config.BASE_PATH}ViewPublishedJobsServlet">VIEW PUBLISHED JOBS</a>
                </div>
		</div>
	
		<div class="container__navbar">
			<ul class="navbar__list">
				<li><button onmouseover="openNav()" class="hambug"><i class="fa fa-bars" aria-hidden="true"></i></button></li>			
				<li>JOB HUNT</li>
				<li style="float: right"><a href="${Config.BASE_PATH}LogoutServlet">Logout</a></li>
				<li style="float: right">
					<a href="${Config.BASE_PATH}AdminStatisticsServlet">
					<li style="float: right"> Hi, <%= session.getAttribute("userName") %></li>
                    <li style="float: right"><i class="user fa fa-user-circle" aria-hidden="true"></i></li> 
                    </a>
                </li>
				<%-- <li style="float: right"><i class="fa fa-user-circle" aria-hidden="true"></i> Hi, <%= session.getAttribute("userName") %> </li> --%>
			</ul>
		</div> 

		<div class="container__title">
			<h3>YOUR NEXT HIRE IS HERE!!!</h3>
		</div>
		
		
		 <div id="snackbar">
                        
        </div>
        <script>
        				function displaySuccessMessage(){
        					 var para = document.createElement("p");
        					  var node = document.createTextNode("YOUR JOB VACANCY IS POSTED SUCCESSFULLY!!");
        					  para.appendChild(node);
        					  var element = document.getElementById("snackbar");
        					  element.className="show";
        					  element.appendChild(para);
        					  setTimeout(function(){ element.className = element.className.replace("show", ""); }, 3000);
        				}
        				function displayFailureMessage(){
       					 var para = document.createElement("p");
       					  var node = document.createTextNode("OOPS!!ERROR THIS JOB VACANCY IN YOUR COMPANY IS ALREADY PUBLISHED!!");
       					  para.appendChild(node);
       					  var element = document.getElementById("snackbar");
       					  element.className="show";
       					  element.appendChild(para);
       					  setTimeout(function(){ element.className = element.className.replace("show", ""); }, 3000);
       				}
        				
		</script>
        				<c:if test="${jobPosted == 'yes'}">
        				<script>
        				displaySuccessMessage();
        				</script>
						</c:if>
						<c:if test="${jobPosted == 'no'}">
        				<script>
        				displayFailureMessage();
        				</script>
						</c:if>
		
		<div class="container__postjob col-xs-12 col-md-12">
		<form action="${Config.BASE_PATH}PostJobServlet" id="postjob"
			name="postjob" onsubmit="return validatePostJob()" method="post">
		
		
				
				<div class="postjob__field col-xs-12 col-md-12">
				<label for="select-job" class="field__entry row col-75"><b>JOB DESIGNATION*</b></label>
			
					 <select id="job" class="select row col-75" name="job">
					 <option value="">Select a Job Designation</option>
						<c:forEach var="job" items="${jobs}">
							<option value="${job.getJobId()}"><c:out
									value="${job.getJobRole()}" /></option>
						</c:forEach>
					</select>
				
				</div>
				<div class="postjob__nav">
					<button type="button" class="button" id="add" onclick="openForm()">NEW</button>
				</div>
				
				<div class="postjob__field col-xs-12 col-md-12">
				<label for="location" class="field__entry row col-25"><b>LOCATION*</b></label>
				<input type="text" class="field__input row col-75" id="location" name="location" placeholder="Enter Job Location.."> 
				<span class="error"><p id="location_error"></p></span>
				</div>
				
				<div class="postjob__field col-xs-12 col-md-12">
				<label for="salary" class="field__entry row col-25"><b>SALARY(LPA)*</b></label> 
				<input type="number" class="field__input row col-75" id="salary" name="salary" placeholder="Enter Salary.." step=".01"> 
				<span class="error"><p id="salary_error"></p></span>
				</div>
				
				<div class="postjob__field col-xs-12 col-md-12">
				<label for="count" class="field__entry row col-75"><b>NO OF VACANCIES*</b></label>
				<input type="number" class="field__input row col-75" id="count" name="count" placeholder="Enter Vacancy Count..">
				<span class="error"><p id="count_error"></p></span>
				</div>
					
				<div class="postjob__field col-xs-12 col-md-12">	
				<label for="description" class="field__entry row col-75"><b>JOB DESCRIPTION*</b></label>
				<textarea rows="4" cols="50" name="description" class="field__input row col-75" placeholder="Enter Job Description"></textarea>
				<span class="error"><p id="desc_error"></p></span>
				</div>
			
			<br>
			
			<div class="postjob__nav">
				<input type="submit" class="button col-xs-12 col-md-12" value="PUBLISH" name="Submit">
				<button type="reset" id="cancel" class="button cancelbtn col-xs-12 col-md-12">CANCEL</button>
			</div>
		
		</form>
	</div>
	
	<div class="container__form-popup" id="myForm">
	<form action="${Config.BASE_PATH}AddNewJobDesignationServlet" class="form-container" method="post">
		<!-- <form action="login.html" class="form-container"> -->
			<h3>ADD NEW JOB DESIGNATION</h3>
			<input type="text" placeholder="Enter new Job designation"
				name="newjob" required>
			<button type="submit" id="addjob" class="btn">ADD
				DESIGNATION</button>
			<button type="button" class="cancel btn" onclick="closeForm()">CLOSE</button>
		</form>
	</div>
</div>
</body>
<script src="${Config.BASE_PATH}Pages/js/styles.js"></script>
<script src="${Config.BASE_PATH}Pages/js/validate.js"></script>
</html>