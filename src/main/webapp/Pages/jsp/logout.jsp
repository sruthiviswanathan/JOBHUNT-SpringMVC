<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
		<%
	
		
		if (session != null) {
		    session.invalidate();
		    response.setHeader("Cache-Control", "no-cache");
		    response.setHeader("Pragma","no-cache");
		    response.setDateHeader("max-age",0);
		    response.setDateHeader("Expires",0);
		    response.sendRedirect("http://localhost:8080/JobSearchApplication/index.jsp");
		}
		
			//response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
			//response.sendRedirect("http://localhost:8080/JobSearchApplication/index.jsp");
		%> 
</body>
</html>