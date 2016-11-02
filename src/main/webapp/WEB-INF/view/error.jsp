<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE HTML>
<html>
  <head>
    <title>Mastermind</title>
    <link href="<c:url value="/resources/css/main.css" />" rel="stylesheet" type="text/css" />

 <%-- we need c:url to add the context path into the URL
  c:url correctly resolves "/resources/css/main.css" -> "http://hostname/mastermind/resources/css/main.css"
  If we used: <link href="/resources/css/main.css" ..> the browser would issue a GET for  "http://hostname/resources/css/main.css
  and would get an 404 (not found) as response
 --%>

 	</head>
		<body>
		<h1 id="title">Mastermind</h1>
		
		<p>
		Sorry, an error occurred:<br/>
		${errorMessage}
		</p>
		<p>
		Do you want to play again?
			<a href="<c:url value="/play"/>"> Play</a>
		</p>
		<p>
		<hr/>
		Admin page
			<a href="<c:url value="/session"/>"> Play</a>
		</p>
	</body>
</html>