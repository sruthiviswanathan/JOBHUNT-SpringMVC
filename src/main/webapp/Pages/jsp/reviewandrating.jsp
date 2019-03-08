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
    <link rel="stylesheet" href="${Config.BASE_PATH}Pages/css/reviewrating.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link href="https://fonts.googleapis.com/css?family=Lato" rel="stylesheet">
</head>
<body>

<div class="maincontainer">
	
	<div class="container__forms">
    <form id="reviewForm" action="${Config.BASE_PATH}company/rate" method="post">
        
        <div class="tab">
        		<div class="tab__label">
                <h2>Rate <c:out value="${companyname}"/></h2>
                </div>
                 
          <p><input type="text" value="${companyname}" name="company" readonly></p>              
          <p><input type="number" placeholder="Rate this company on a Scale of 5"  oninput="this.className = ''" name="rating" step=".01"></p>
          <p><textarea placeholder="Share something about this company..."  oninput="this.className = ''" name="review" required></textarea></p>
        </div>
         
        <div class="tab">
        		<div class="tab__label">
                <h2>KNOW THE INTERVIEW PROCESS?</h2>
                </div>
                <select id="job" name="job" class="tab__field">
                        <option value="">Select a Job Designation</option>
						<c:forEach var="job" items="${jobs}">
							<option value="${job.getJobRole()}"><c:out
									value="${job.getJobRole()}" /></option>
						</c:forEach>
                </select>
                <p><textarea placeholder="Do you know about the interview process?..." class="tab__field" oninput="this.className = ''" name="interview"></textarea></p>
        </div>
        
        <div style="overflow:auto;">
          <div style="float:right;">
            <button class="button" type="button" id="prevBtn" onclick="nextPrev(-1)">PREVIOUS</button>
            <button class="button" type="button" id="nextBtn" onclick="nextPrev(1)">NEXT</button>
          </div>
        </div>
        
        <!-- Circles which indicates the steps of the form: -->
        <div style="text-align:center;margin-top:40px;">
          <span class="step"></span>
          <span class="step"></span>
        </div>
    
    </form>
    </div>
</div>
</body>
<script src="${Config.BASE_PATH}Pages/js/review.js"></script>
<script src="${Config.BASE_PATH}Pages/js/styles.js"></script>
</html>