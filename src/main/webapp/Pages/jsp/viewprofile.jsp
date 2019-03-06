<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
        <%@ page import="com.zilker.onlinejobsearch.config.Config"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%--  <%@ include file = "usernavbar.jsp" %> --%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="${Config.BASE_PATH}Pages/css/forms.css">
	  <link rel="stylesheet" href="${Config.BASE_PATH}Pages/css/navbar.css">
    <link rel="stylesheet" href="${Config.BASE_PATH}Pages/css/tags.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link href="https://fonts.googleapis.com/css?family=Lato" rel="stylesheet">
    <title>YOUR PROFILE</title>
   
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
                        <a href="${Config.BASE_PATH}users/update">YOUR PROFILE</a>
                        <a href="${Config.BASE_PATH}users/request">REQUEST A VACANCY</a>
                        <a href="${Config.BASE_PATH}users/appliedjobs">VIEW APPLIED JOBS</a>
           </div>
          </div> 
        <div class="container__navbar">
            <ul class="navbar__list">
					
					<li><button onmouseover="openNav()" class="hambug"><i class="fa fa-bars" aria-hidden="true"></i></button></li>			
					<li>JOB HUNT</li>
					<li style="float: right"><a href="${Config.BASE_PATH}logout">Logout</a></li>
					<li style="float: right"><a href="${Config.BASE_PATH}users/update"><i class="fa fa-user-circle" aria-hidden="true"></i> Hi, <%= session.getAttribute("userName") %></a></li>
                    
                    </ul>
        </div> 
						
		<div class="container__title">
			<h3>YOUR PROFILE!!!</h3>
		</div>						

		 <div id="snackbar">
                        
        </div>
        <script>
        				function displaySuccessMessage(){
        					 var para = document.createElement("p");
        					  var node = document.createTextNode("YOUR PROFILE IS UPDATED!!");
        					  para.appendChild(node);
        					  var element = document.getElementById("snackbar");
        					  element.className="show";
        					  element.appendChild(para);
        					  setTimeout(function(){ element.className = element.className.replace("show", ""); }, 3000);
        				}
        				function deleteTag(id){
        					console.log(id);
        					var elem = document.getElementById("item"+id).remove();
        					
        				}
        				
		</script>
        				<c:if test="${updated == 'yes'}">
        				<script>
        				displaySuccessMessage();
        				</script>
						</c:if>

        <div class="container__profile col-xs-12 col-md-12">
            <form action="${Config.BASE_PATH}users/update" name ="update" id="update"  onsubmit="return updateUser()" method="post">
               
             
          
                <c:forEach var="data" items="${userData}">
             <div class="profile__field col-xs-12 col-md-12">     
                    <label for="uname" class="field__entry row col-75"><b>USERNAME*</b></label>
                    <input type="text" class="field__input row col-75" id="uname" name="username" value="${data.getUserName()}">  
                     <span class="error"><p id="name_error"></p></span>                     
                    </div>
                    
             <div class="profile__field col-xs-12 col-md-12">          
                        <label for="cname" class="field__entry row col-75"><b>COMPANY NAME*</b></label>
                        <input type="text" class="field__input row col-75" id="cname" name="cname" value="${data.getCompany()}" >
                         <span class="error"><p id="comp_error"></p></span> 
                    </div>
                    
                       <div class="profile__field col-xs-12 col-md-12">
                        <label for="designation" class="field__entry row col-75"><b>DESIGNATION*</b></label>
                        <input type="text" class="field__input row col-75" id="desig" name="designation" value="${data.getDesignation()}">
                        <span class="error"><p id="des_error"></p></span> 
                   	   </div>
                       
                       <div class="profile__field col-xs-12 col-md-12">
                        <label for="skills" class="field__entry row col-75"><b>SKILLS SAVED IN YOUR PROFILE</b></label>
                         <input type="hidden" id="skillset" name="skillset" value="">
                         <div class="tags-input field__input row col-75" id="skill" data-name="tags-input">
                     
                     <c:if test="${userTech.size() != 0 }">    
                     <c:forEach var="user" items="${userTech}" varStatus="loop"> 	
					
					<div class="input__skills"id="item${loop.count}">                          
                       <span class="tag" id="tag${loop.count}">${user.getTechnologyName()}<%-- <c:out value="${user.getTechnologyName()}"></c:out> --%>
                       <span class="close" id="${loop.count}" onclick="deleteTag(this.id)"></span>
                       </span>
     				</div>
					</c:forEach>
					</c:if>
					
						</div>
                       
						
						</div>
					</c:forEach>
             
                <div class="profile__nav">
                    <input type="submit" class="button col-xs-12 col-md-12" value="UPDATE" name="Submit">  
                    <button type="reset" id="cancel" class="button cancelbtn col-xs-12 col-md-12">CANCEL</button>
                </div>
            </form>
        </div>
    </div>
</body>
<script src="${Config.BASE_PATH}Pages/js/styles.js"></script>
<script src="${Config.BASE_PATH}Pages/js/validate.js"></script>
 <script src="${Config.BASE_PATH}Pages/js/tags.js"></script>
</html>