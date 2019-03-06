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
    <title>REQUEST VACANCY</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="${Config.BASE_PATH}Pages/css/forms.css">
    <link rel="stylesheet" href="${Config.BASE_PATH}Pages/css/navbar.css">
    <link href="https://fonts.googleapis.com/css?family=Lato" rel="stylesheet">
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
                        <a href="${Config.BASE_PATH}findjobs">FIND JOB</a>
                        <a href="${Config.BASE_PATH}findcompany">FIND COMPANY</a>
                         <a href="${Config.BASE_PATH}findlocation">SEARCH BY LOCATION</a>
                        <a href="${Config.BASE_PATH}UserProfileServlet">YOUR PROFILE</a>
                        <a href="${Config.BASE_PATH}RequestVacancyServlet">REQUEST A VACANCY</a>
                        <a href="${Config.BASE_PATH}users/appliedjobs">VIEW APPLIED JOBS</a>
                      </div>
                </div>
            
            <div class="container__navbar">
                           <ul class="navbar__list">
					
					<li><button onmouseover="openNav()" class="hambug"><i class="fa fa-bars" aria-hidden="true"></i></button></li>			
					<li>JOB HUNT</li>
					<li style="float: right"><a href="${Config.BASE_PATH}LogoutServlet">Logout</a></li>
					<li style="float: right"><a href="${Config.BASE_PATH}UserProfileServlet"><i class="fa fa-user-circle" aria-hidden="true"></i> Hi, <%= session.getAttribute("userName") %></a> </li>
                    
                    </ul>
            </div>
        <div class="container__title">
                <h3>REQUEST FOR A VACANCY!! GET NOTIFIED!!</h3>
        </div>
        <div id="snackbar">
                        
        </div>
      <script>
        				function displaySuccessMessage(){
        					 var para = document.createElement("p");
        					  var node = document.createTextNode("YOUR REQUEST IS SAVED!!YOU WILL BE NOTIFIED WHEN YOUR REQUIREMENT MATCHES ANY VACANCIES!!");
        					  para.appendChild(node);
        					  var element = document.getElementById("snackbar");
        					  element.className="show";
        					  element.appendChild(para);
        					  setTimeout(function(){ element.className = element.className.replace("show", ""); }, 3000);
        				}
        				
		</script>
        				 <c:if test="${saved == 'yes'}">
        				<script>
        				displaySuccessMessage();
        				</script>
						</c:if>  
        				
        
      <div class="container__requestvacancy col-xs-12 col-md-12">
						        
    		<form action="${Config.BASE_PATH}RequestVacancyServlet" id="requestvacancy" name="requestvacancy" onsubmit="return validateRequestVacancy();" method="post">
        
       
        		<div class="requestvacancy__field col-xs-12 col-md-12">
                <label for="select-job" class="field__entry row col-75"><b>JOB DESIGNATION*</b></label>
                            
                       <select id="job" name="job" class="formValue select row col-75">
					 	<option value="">Select a Job Designation</option>
						<c:forEach var="job" items="${jobs}">
							<option value="${job.getJobId()}"><c:out value="${job.getJobRole()}" /></option>
						</c:forEach>
					</select>
					 <span class="error"><p id="job_error"></p></span>
                
                </div>
            
                <div class="requestvacancy__field col-xs-12 col-md-12">
                <label for="location" class="field__entry row col-75"><b>LOCATION*</b></label>
                <input type="text" class="formValue field__input row col-75" id="location" name="location" placeholder="Enter Job Location..">
                <span class="error"><p id="location_error"></p></span>
                </div>
               
                <div class="requestvacancy__field col-xs-12 col-md-12">
                <label for="salary" class="field__entry row col-75"><b>SALARY EXPECTED(LPA)*</b></label>
                <input type="number" class="formValue field__input row col-75" id="salary" name="salary" placeholder="Enter Salary.." step=".01">
                <span class="error"><p id="salary_error"></p></span>
       			</div>
       			
				<div class="requestvacancy__nav">
        		<input type="submit" class="button col-xs-12 col-md-12" value="SAVE JOB" name="Submit">
        		<!-- <button type="button" class="button col-xs-12 col-md-12" onclick="myFunction(); return false;">SAVE JOB</button> -->
        		<button type="reset" id="cancel" class="button cancelbtn col-xs-12 col-md-12">CANCEL</button>
        		</div>
        	
    </form>   	
    </div>
</div>

</body>

<script src="${Config.BASE_PATH}Pages/js/styles.js"></script>
<script src="${Config.BASE_PATH}Pages/js/validate.js"></script>
</html>