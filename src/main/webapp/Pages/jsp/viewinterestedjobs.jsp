<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.zilker.onlinejobsearch.config.Config"%>
<%@ include file = "usernavbar.jsp" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta http-equiv="X-UA-Compatible" content="ie=edge">
  <title>APPLIED JOBS</title>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
  <link rel="stylesheet" href="${Config.BASE_PATH}Pages/css/viewinterested.css">
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
    
		<div class="container__title">
			<h3>GETTING HIRED JUST GOT EASIER WITH US!!!</h3>
		</div>
					<c:choose>
						<c:when test="${noAppliedJobs == 'yes'}">
						<div class="success">
						<c:out value="YOU HAVE NOT APPLIED FOR ANY JOBS YET!!"/>
						</div>
						</c:when>
		<c:otherwise>
		<div style="overflow-x:auto">
		<table class="container__customers">
			
  				<tr>
    			
    				<th>COMPANY NAME</th>
    				<th>JOB DESIGNATION</th>
    				<th>LOCATION</th>
  			    </tr>
  			 
					 <c:forEach var="user" items="${appliedJobs}">
						<tr>
							<td>	<c:out value="${user.getCompanyName()}" /> </td> 
							<td>	<c:out value="${user.getJobRole()}" /> </td> 
							<td>	<c:out value="${user.getLocation()}" /> </td> 
						</tr>	
					</c:forEach>
		</table>
		</div>
		</c:otherwise>
		</c:choose>
</div>
</body>
<script src="${Config.BASE_PATH}Pages/js/styles.js"></script>
</html>
    