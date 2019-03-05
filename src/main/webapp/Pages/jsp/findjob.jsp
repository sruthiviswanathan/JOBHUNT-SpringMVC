
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="com.zilker.onlinejobsearch.config.Config"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <%@ include file = "usernavbar.jsp" %>

<%--  <jsp:include page="/RetreiveAllCompanyServlet" /> --%>

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
        <div class="maincontainer" id="maincontainer">
                   
                   		<div class="success">
						<c:if test="${registerSuccess == 'yes'}">
						<c:out value="CONGRATS YOU ARE REGISTERED WITH US!!"/>
						</c:if>
						</div>
                   
                     
                <div class="container__searchbar col-xs-12 col-sm-12 col-md-3">
                        <form action="${Config.BASE_PATH}company/jobs" method="post">
                  <div class="searchbar__row">               
                                <div class="col-60 col-xs-12 col-sm-12 col-md-3">
                                    <label for="job" class="row__label col-md-3">SEARCH FOR JOB</label>
                                </div>
                                <div class="col-60 col-xs-12 col-sm-12 col-md-3">
                                        <input type="text" class="row__input" name="job" placeholder="Search for Job.."
                                                required><i class="icon fa fa-search" aria-hidden="true"></i>
                                </div>
                                <div class="col-10 col-xs-12 col-sm-12 col-md-3">
                                        <input type="submit" class=" row__button col-xs-12 col-sm-12 col-md-3" value="SEARCH">
                                </div>
                   </div>
                        </form>
                </div>
              
             
                <div class="container__maincard col-sm-12 col-xs-12">
                       
                      <c:forEach var="comp" items="${companyList}" varStatus="loop">
						
						 <div class="maincard__card col-sm-6 col-xs-12">
						 	<c:set var="company" value="${comp.getCompanyName()}"/>
                                <div class="card__container">
                                     <a href="${Config.BASE_PATH}company?companyName=${company}">
						  			<img src="${comp.getCompanyLogo()}"
                                                        alt="Avatar" style="width:50%;height:75%;"></a> 
                                        <h4><b><c:out value="${comp.getCompanyName()}" /></b></h4>
                                     
                                </div>
                        
                        </div>
                      
					</c:forEach>
                      
                </div>
        </div>
</body>
<script src="${Config.BASE_PATH}Pages/js/styles.js"></script>

</html>