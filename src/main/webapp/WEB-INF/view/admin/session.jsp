<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE HTML>
<html>
<head>
<title>Mastermind Admin</title>
<link href="<c:url value="/resources/css/main.css" />" rel="stylesheet"
	type="text/css" />

<%-- we need c:url to add the context path into the URL
  c:url correctly resolves "/resources/css/main.css" -> "http://hostname/mastermind/resources/css/main.css"
  If we used: <link href="/resources/css/main.css" ..> the browser would issue a GET for  "http://hostname/resources/css/main.css
  and would get an 404 (not found) as response
 --%>

<%--  TODO remove this page ? (admin page controller) --%>
</head>
<body>
	<h1 id="title">Mastermind Admin</h1>

	<h2>Sessions:</h2>
	<c:choose>
		<c:when test="${empty sessions}">
			No session
			</c:when>
		<c:otherwise>
			<p>
			<ul>
				<c:forEach var="session" items="${sessions}">
					<li>SessionId=${session.key} Secret= ${session.value}</li>
				</c:forEach>
			</ul>
			</p>
		</c:otherwise>
	</c:choose>

	<p>
		<a href="<c:url value="/session"/>">Refresh</a>
	</p>
	<jsp:include page="../fragments/footer.jsp"/>
</body>
</html>