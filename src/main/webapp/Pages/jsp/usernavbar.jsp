<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
 <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <link rel="stylesheet" href="${Config.BASE_PATH}Pages/css/navbar.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link href="https://fonts.googleapis.com/css?family=Lato" rel="stylesheet">
</head>
<body>
<div class="topnavcontainer">
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
					<li style="float: right"><a href="${Config.BASE_PATH}users/update"> Hi, <%= session.getAttribute("userName") %></a></li>
                    <li style="float: right"><i class="user fa fa-user-circle" aria-hidden="true"></i></li>  
                    </ul>
                </div>
				
				
</div>
</body>
</html>