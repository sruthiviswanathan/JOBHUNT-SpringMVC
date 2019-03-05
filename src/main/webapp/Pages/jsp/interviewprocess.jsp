<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ page import="com.zilker.onlinejobsearch.config.Config"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file = "usernavbar.jsp" %>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>company details</title>
<link rel="stylesheet" type="text/css" href="${Config.BASE_PATH}Pages/css/company.css ">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
	<link href="https://fonts.googleapis.com/css?family=Lato" rel="stylesheet">
</head>

<body>
<%
			if(session.getAttribute("email")==null){
				response.sendRedirect("http://localhost:8080/JobSearchApplication/index.jsp");
			}
		%> 
<div class="maincontainer">
		
		<div class="container__searchbar col-xs-12 col-sm-12 col-md-3">
			<form action="${Config.BASE_PATH}FindCompanyServlet" method="post">
				<div class="searchbar__row">
				
				<div class="searchbar__label col-xs-12 col-sm-12 col-md-3">
					<label for="job" class="row__label col-md-3">SEARCH FOR COMPANY</label>
				</div>
				
				<div class="searchbar__input col-xs-12 col-sm-12 col-md-3">
					<input type="text" name="companyName" class="row__input"
						placeholder="Search for Company.." required><i
						class="icon fa fa-search" aria-hidden="true"></i>
				</div>
				
				<div class="searchbar__button col-xs-12 col-sm-12 col-md-3">
					<input type="submit" class="row__button col-xs-12 col-sm-12 col-md-3" value="SEARCH">
				</div>
				
				</div>
			</form>
		</div>
	<form action="${Config.BASE_PATH}RateServlet" method="get">	
	<div class="container__display">
	
	
	<c:forEach items="${displayCompany}" var="comp">
	<input type="hidden" name="companyname" value="${comp.getCompanyName()}">
		<div class="display__row">

			<div class="col-20 col-xs-12 col-sm-12">
				<label for="company" class="display__row__label">COMPANY NAME</label>
			</div>
			
			<div class="row__content col-60 col-xs-12 col-sm-12">
				<div class="content__value">
							<c:out value="${comp.getCompanyName()}" />
				</div>
			</div>
			
			<div class="col-20 col-xs-12 col-sm-12 col-md-6">
					<a href="<c:out value="${comp.getCompanyWebsiteUrl()}" />">
					<button class="row__button col-xs-12 col-sm-12 col-md-6" type="button" value="WEBSITE URL"> WEBSITE URL <i class="dir fa fa-chevron-circle-right"> </i></button></a>
			</div>
		</div>
		
		<div class="display__row">
		
			<div class="col-20 col-xs-12 col-sm-12">
						<label for="rating" class="display__row__label">OVERALL RATING</label>
			</div>
		
			<div class="row__content col-60 col-xs-12 col-sm-12">
						
						<div class="content__value">
							<c:if test="${comp.getAverageRating() != 0}">
							<c:out value="${comp.getAverageRating()}" />
							</c:if>
							<c:if test="${comp.getAverageRating() == 0}">
							<c:out value="No Rating For This Company!Be The First to Rate This Company" />
							</c:if>
						</div>
			</div>
		
			<div class="col-20 col-xs-12 col-sm-12 col-md-6">
				<button class="row__button col-xs-12 col-sm-12 col-md-6" type="submit" value="RATE THIS COMPANY">RATE THIS COMPANY</button>
			</div>
			
		</div>
		
		</c:forEach>
		
	</div>		
	
	<div class="container__title">
			<h3>KNOW WHAT OTHERS SAY ABOUT THE INTERVIEW PROCESS!!!!</h3>
	   </div>	
			
		<!-- <div class="display__reviews"> -->
		
		 	<c:choose>
		 	<c:when test="${noReviews == 'yes'}">
    		<div class="error col-40 col-xs-12 col-sm-12">
                 <c:out value="***No Reviews have been given yet***"></c:out>
             </div>
             </c:when>
		
			<c:otherwise>
			
			
			<c:forEach items="${displayInterviewProcess}" var="rev"> 
				<div class="maincard__reviewcard col-sm-6 col-xs-height">
					<div class="card__container">
						<p>
							<b><c:out value="${rev.getUserName()}" /></b> ,<i><c:out value="${rev.getUserCompany()}" /> , <c:out value="${rev.getUserDesignation()}" /> </i>
						</p>
						<p>
							I have undergone the interview for <c:out value="${rev.getJobRole()}" /> role in this company,<c:out value="${rev.getInterviewProcess()}" />
						</p>
										
					</div>
				</div>
			</c:forEach>
			</c:otherwise>
			
			</c:choose>
			
	</form>
	
</div>
</body>
<script src="${Config.BASE_PATH}Pages/js/styles.js"></script>

</html>