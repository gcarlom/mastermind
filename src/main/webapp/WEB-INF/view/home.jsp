<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE HTML>
<html>
	<head>
	<title>Mastermind</title>
	<link href="<c:url value="/resources/css/main.css" />" rel="stylesheet" type="text/css" />

	<%-- We need c:url to add the context path into the URL
	  c:url correctly resolves "/resources/css/main.css" -> "http://hostname/mastermind/resources/css/main.css"
	  If we used: <link href="/resources/css/main.css" ..> the browser would issue a GET for  "http://hostname/resources/css/main.css
	  and would get an 404 (not found) as response
	 --%>

	</head>
		<body>
		<h1 class="logo">Welcome to Mastermind !</h1>

		<p>
			<a href="<c:url value="/play"/>"> Start a new game</a>
		</p>
		<jsp:include page="./fragments/footer.jsp"/>

</body>
</html>