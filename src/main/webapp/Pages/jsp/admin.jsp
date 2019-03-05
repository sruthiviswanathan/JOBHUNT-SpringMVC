<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.zilker.onlinejobsearch.config.Config"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <%@ include file = "adminnavbar.jsp" %>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>Find Jobs</title>
<link rel="stylesheet" href="${Config.BASE_PATH}Pages/css/mainpage.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link href="https://fonts.googleapis.com/css?family=Lato" rel="stylesheet">
</head>

<body>

		 <%
			if(session.getAttribute("email")==null){
				response.sendRedirect("http://localhost:8080/JobSearchApplication/index.jsp");
			} 
		%> 
		
	<div class="maincontainer">
		
					 	<div class="success">
						<c:if test="${registerSuccess == 'yes'}">
						<c:out value="CONGRATS YOU ARE REGISTERED WITH US!!"/>
						</c:if>
						</div> 
		
		<div class="container__title">
			<h1>YOUR NEXT HIRE IS HERE!!!</h1>
		</div>
		<div class="container__image">
		
		 <div class="container__maincard col-sm-12 col-xs-12">
            			
            			 <div class="maincard__card col-sm-6 col-xs-height">
						 	
                                <div class="card__container">
                                    <div class="card__container__text">
                                     <a href="${Config.BASE_PATH}ViewPublishedJobsServlet">
						  			  <h4><b><c:out value="NO OF VACANCIES PUBLISHED" /></b></h4>
						  			</a> 
                                      <h2><b><c:out value="${postedJobs}"/></b></h2>
                                      <a href="${Config.BASE_PATH}ViewAppliedUsersServlet">
                                      <h4><b><c:out value="CANDIDATES APPLIED" /></b></h4>
						  			</a> 
                                       <h2><b><c:out value="${appliedUsers}"/></b></h2>
                                     </div>
                                </div>
                           </div>
            			
						  <div class="maincard__card col-sm-6 col-xs-height">
						 	
                                <div class="card__container">
                                     <a href="${Config.BASE_PATH}PostJobServlet">
						  			<img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTpSOeEh3aUkQgcUF28pOs4d1jOaK9nba-hATsRDyEaZKW0LXJWHQ"alt="Avatar"></a> 
                                        <h4><b><c:out value="PUBLISH NEW VACANCY" /></b></h4>
                                     
                                </div>
                           </div>
                                
                             <div class="maincard__card col-sm-6 col-xs-height">
                                <div class="card__container">
                                     <a href="${Config.BASE_PATH}ViewAppliedUsersServlet">
						  			<img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQV-s57fKLRPJdJfP5x6W0NdUhsbb-bdnqMht73fFnY1410cERJ"
                                                        alt="Avatar"></a> 
                                        <h4><b><c:out value="VIEW INTERESTED USERS" /></b></h4>
                                     
                                </div>
                   			
                   			</div>             
                              <div class="maincard__card col-sm-6 col-xs-height">
                                <div class="card__container">
                                     <a href="${Config.BASE_PATH}ViewPublishedJobsServlet">
						  			<img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQCmEAMa4TOu7L_M5bVz3yWVc-p-3E5axRiAJO9EnTQbEVVoM99" alt="Avatar"></a> 
                                        <h4><b><c:out value="VIEW PUBLISHED VACANCIES" /></b></h4>
                                     
                                </div>
                        
                        	</div>
                        	
                        	
                			
                </div>
                <!-- <div class="maincard__image col-sm-12 col-xs-12">
                    <img src="https://i.guim.co.uk/img/static/sys-images/Guardian/Pix/pictures/2015/9/8/1441712108064/ca67c8f9-71e0-4680-b3f8-d20fe1d5a035-620x372.png?width=700&quality=85&auto=format&fit=max&s=a85b3995b77d35ee8fc06705d73b6547">
                </div> -->
		
		</div>
	</div>
</body>
<script src="${Config.BASE_PATH}Pages/js/styles.js"></script>
</html>