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
        <title>View Jobs</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" href="${Config.BASE_PATH}/Pages/css/viewjobs.css">
        <link rel="stylesheet" href="${Config.BASE_PATH}/Pages/css/updatejobs.css">
        <link href="https://fonts.googleapis.com/css?family=Lato" rel="stylesheet">
</head>

<body onload="displayFirstVacancy()">
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
								<li><button onmouseover="openNav()" class="hambug">
										<i class="fa fa-bars" aria-hidden="true"></i>
									</button></li>
								<li>JOB HUNT</li>
								<li style="float: right"><a href="${Config.BASE_PATH}LogoutServlet">Logout</a></li>
								<li style="float: right">
									<a href="${Config.BASE_PATH}AdminStatisticsServlet">
									<li style="float: right"> Hi, <%= session.getAttribute("userName") %></li>
				                    <li style="float: right"><i class="user fa fa-user-circle" aria-hidden="true"></i></li> 
				                    </a>
				                </li>
								<%-- <li style="float: right"><i class="fa fa-user-circle" aria-hidden="true"></i> Hi, <%= session.getAttribute("userName") %> </li> --%>
								<li style="float: right" ><button class="arrow" id="btn" onclick="displaymenu(this.id)">
								<i class="fa fa-arrow-left" aria-hidden="true"></i></button></li>
								
							</ul>
						</div>
      					
      		<div id="snackbar">
                        
        	</div>
        <script>
        				function displayUpdatedSuccessMessage(){
        					 var para = document.createElement("p");
        					  var node = document.createTextNode("VACANCY HAS BEEN UPDATED SUCCESSFULLY");
        					  para.appendChild(node);
        					  var element = document.getElementById("snackbar");
        					  element.className="show";
        					  element.appendChild(para);
        					  setTimeout(function(){ element.className = element.className.replace("show", ""); }, 3000);
        				}
        				function displayDeletedSuccessMessage(){
       					 var para = document.createElement("p");
       					  var node = document.createTextNode("VACANCY HAS BEEN DELETED SUCCESSFULLY");
       					  para.appendChild(node);
       					  var element = document.getElementById("snackbar");
       					  element.className="show";
       					  element.appendChild(para);
       					  setTimeout(function(){ element.className = element.className.replace("show", ""); }, 3000);
       				}
        				
		</script>
        				<c:if test="${status == 'updated'}">
        				<script>
        				displayUpdatedSuccessMessage();
        				</script>
						</c:if>
        				<c:if test="${status == 'deleted'}">
        				<script>
        				displayDeletedSuccessMessage();
        				</script>
						</c:if>			
  					
      					
      					<c:choose>
      					
						<c:when test="${noVacancy == 'yes'}">
						<div class="success">
               			<c:out value="YOU HAVE NOT PUBLISHED ANY JOB VACANCIES"></c:out>
              			</div>
              			</c:when>
						
		<c:otherwise>
     		 <div class="container__split split--left">        
                       
                                <div class="left__jobs">
                                <div class="jobs__row">
                                	 
                                	 <c:forEach var="vac" items="${vacancyDetails}" varStatus="loop">
                                      
                                        <div class="row__card col-sm-6 col-xs-height">
                                                <h4><b><c:out value="${vac.getJobRole()}" /></b></h4>
                                                <p><c:out value="${vac.getLocation()}" /></p>
                                               
                                               <button class="btn${loop.count}" id="button" onclick="displayjobs(this.getAttribute('class'))">VIEW MORE</button>
                                               
                                        </div>
                                      
                                      </c:forEach>                                  
                                </div>
                        </div>
                    </div>

                        
                               <c:forEach var="vac" items="${vacancyDetails}" varStatus="loop">  
                            	
                            	    <div class="container__split split--right rightside btn${loop.count}">
                           				 <div class="right__form">
                                			
                                			<form action="${Config.BASE_PATH}UpdateVacancyServlet" name="postjob" method="post">
                                    		<input type="hidden" name="jobdesignation" value="${vac.getJobRole()}">
                                        		
                                        		<div class="content__updatejobs col-xs-12 col-md-12">
                                                
                                                <div class="updatejobs__field col-xs-12 col-md-12">
                                                <label for="select-job" class="field__entry row col-75"><b>JOB DESIGNATION*</b></label>
                                               
                                                        <select id="job" name="job" class="select row col-75" required>
                                                      		
                                                               <option value="${vac.getJobId()}">${vac.getJobRole()}</option> 
                                                               <c:forEach var="job" items="${jobs}">
																	<option value="${job.getJobId()}"><c:out value="${job.getJobRole()}" /></option>
																</c:forEach> 
                                                        </select>
                                               			 <span class="error"><p id="job_error"></p></span>
                                               </div>
                                               
                                               <!--  <div class="updatejobs__nav">
                                                        <button type="button" id="add" onclick="openForm()">NEW</button>
                                                </div> -->
                                               
                                                <div class="updatejobs__field col-xs-12 col-md-12">
                                                <label  for="location" class="field__entry row col-75"><b>LOCATION*</b></label>
                                                <input type="text" class="field__input row col-75" id="location" name="location" value="${vac.getLocation()}" required>
                                               <span class="error"><p id="location_error"></p></span>
                                               </div>
                                               
                                                <div class="updatejobs__field col-xs-12 col-md-12">
                                                <label  for="salary" class="field__entry row col-75"><b>SALARY(LPA)*</b></label>
                                                <input type="number" class="field__input row col-75" id="salary" name="salary" value="${vac.getSalary()}"
                                                        step=".01" required>
                                                 <span class="error"><p id="salary_error"></p></span>
                                                 </div>
                                                    
                                                 <div class="updatejobs__field col-xs-12 col-md-12">
                                                <label  for="count" class="field__entry row col-75"><b>NO OF VACANCIES*</b></label>
                                                <input type="number" class="field__input row col-75" id="count" name="count" value="${vac.getVacancyCount()}"required>
                                               <span class="error"><p id="count_error"></p></span>
                                               </div>
                                               
                                                <div class="updatejobs__field col-xs-12 col-md-12">
                                                <label for="description" class="field__entry row col-75"><b>JOB DESCRIPTION*</b></label>
                                                <textarea rows="4" cols="50" class="field__input row col-75" name="description" required>${vac.getJobDescription()}</textarea>
                                       			<span class="error"><p id="desc_error"></p></span>
                                       			</div>


                                        <div class="updatejobs__nav">
                                           
                                               	<input type="submit" class="button col-xs-12 col-md-12" name="action" value="UPDATE">
    											<input type="submit" class="button col-xs-12 col-md-12" name="action" value="DELETE">
                                                <button type="reset" id="cancel" class="cancelbtn button col-xs-12 col-md-12">DISCARD</button>
                                        </div>

                                        
                                     </div>
                                </form>
                              </div>
                            </div>

						</c:forEach>
						</c:otherwise>
						</c:choose>
                       
                        </div>
                   
</body>
<script src="${Config.BASE_PATH}/Pages/js/styles.js"></script>
<script src="${Config.BASE_PATH}/Pages/js/validate.js"></script>

</html>