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
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link href="https://fonts.googleapis.com/css?family=Lato" rel="stylesheet">
<script src="${Config.BASE_PATH}Pages/js/jquery-3.3.1.min.js"></script>


</head>

<body>
		<%
			if(session.getAttribute("email")==null){
				response.sendRedirect("http://localhost:8080/JobSearchApplication/index.jsp");
			}
		%> 
	<div id="maincontainer">
		
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
						 	<div id="snackbar">
                        
                        	</div>
	<div class="container__display">
		<form action="${Config.BASE_PATH}ReviewServlet" method="post">
			<c:forEach items="${displayCompany}" var="comp">
				<div class="display__row">
						<input type="hidden" name="company" value="${comp.getCompanyName()}">
						<c:set var="company" value="${comp.getCompanyName()}"></c:set>
					
					<div class="col-20 col-xs-12 col-sm-12">
						<label for="company" class="display__row__label">COMPANY NAME</label>
					</div>
					<%-- <div class="col-20 image col-xs-12 col-sm-12">
						<img src="${comp.getCompanyLogo()}"alt="zilker" />
					</div> --%>
					<div class="row__content col-60 col-xs-12 col-sm-12">
						<div class="content__value">
							<c:out value="${comp.getCompanyName()}" />
						</div>
					</div>
					
					 <div class="col-50 col-xs-12 col-sm-12 col-md-6">
						     <a href="<c:out value="${comp.getCompanyWebsiteUrl()}"/>" target="_blank"><button
								class="row__button col-xs-12 col-sm-12 col-md-6" type="button" value="WEBSITE URL"> WEBSITE URL <i class="dir fa fa-chevron-circle-right"> </i>
							</button></a>

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
							<c:out value="***No Rating For This Company***" />
							</c:if>
						</div>
					</div>
					<div class="col-50 col-xs-12 col-sm-12 col-md-6">
							<input type="submit" class="row__button col-xs-12 col-sm-12 col-md-6" value="VIEW ALL REVIEWS">
					</div> 
					
				</div>
				
			</c:forEach>		
				
		</form>
					<form action="${Config.BASE_PATH}ReviewsOnInterviewServlet" method="post">
					<input type="hidden" name="company" value="${company}">
					<div class="col-50 col-xs-12 col-sm-12 col-md-6">
					<input type="submit" class="row__button col-xs-12 col-sm-12 col-md-6" value="KNOW ABOUT INTERVIEWS">
					</div>
					</form>
	</div>
		
		
	<div class="container__title">
			<h3>JOBS AVAILABLE</h3>
	</div>
	
		<div class="container__maincard">
			 <span class="error col-40 col-xs-12 col-sm-12" id="companyError">
                                       <c:if test="${noVacancy == 'yes'}">
                                       <c:out value="There is no Vacancy in this Company as of now"></c:out>
                                       </c:if>
              </span>
			
			<c:forEach items="${displayVacancies}" var="vac" varStatus="loop">
			  
			  	<div class="maincard__card col-sm-6 col-xs-height">
					<div class="card__container">
						<p>
							<b><c:out value="${vac.getJobRole()}" /></b>
						</p>
						<p>
							<c:out value="${vac.getLocation()}" />
						</p>
						<p>
						<button class="container__button" id="btn${loop.count}"onclick="displaymodal(this.id)">VIEW MORE</button>
						</p>					
					</div>
				</div>

		<div class="container__modal">
				<div class=" modal btn${loop.count}">

					<div class="modal-content">
						
						<span class="close" onclick="closeModal(this.getAttribute('class'))">&times;</span>
						<div class="modal__row">
							
							<div class="col-60 col-xs-12 col-sm-12">
								<label for="job" class="modal__label">JOB DESIGNATION</label>
							</div>
							
							<div class="col-20 col-xs-12 col-sm-12">
								<p>
									<c:out value="${vac.getJobRole()}" />
								</p>
							</div>
							<c:choose>
							
							<c:when test="${vac.getFlag() == 1 }">
							<div class="col-20 col-xs-12 col-sm-12">
							<input class="disabled col-xs-12" type="submit" value="APPLIED"/>
							</div>
							</c:when>
							
							
							<c:otherwise>
							<div class="col-20 col-xs-12 col-sm-12">
								<form action="${Config.BASE_PATH}ApplyForJobServlet"  id="apply${loop.count}" onsubmit="event.preventDefault(); apply(this,'button${loop.count}');"  method="post">
									<input type="hidden" name="location" value="${vac.getLocation()}">
								<input type="hidden" name="companyName" value="${vac.getCompanyName()}">
								<input type="hidden" name="jobDesignation" value="${vac.getJobRole()}">
								<input class="row__button" id="button${loop.count}" type="submit" value="APPLY FOR JOB"/>
								</form>	
							</div>
							</c:otherwise>
							
							</c:choose>
							<div class="col-60 col-xs-12 col-sm-12">
								<label for="jobdesc" class="modal__label">JOB DESCRIPTION</label>
							</div>
							
							<div class="col-60 col-xs-12 col-sm-12">
								<p>
									<c:out value="${vac.getJobDescription()}" />
								</p>
							</div>
							
							<div class="col-60 col-xs-12 col-sm-12">
								<label for="salary" class="modal__label">SALARY(per annum)</label>
							</div>
							
							<div class="col-20 col-xs-12 col-sm-12">
								<p>
									<c:out value="${vac.getSalary()}" />
									Lakhs per annum
								</p>
							</div>
							
							<div class="col-60 col-xs-12 col-sm-12">
								<label for="location" class="modal__label">JOB LOCATION</label>
							</div>
							
							<div class="col-20 col-xs-12 col-sm-12">
								<p>
									<c:out value="${vac.getLocation()}" />
								</p>
							</div>
						
						</div>
					</div>
				</div>
			</div>
			</c:forEach>
		</div>
	</div>
</body>
<script src="${Config.BASE_PATH}Pages/js/styles.js"></script>

</html>