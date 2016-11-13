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
			<c:when test="${empty sessionInfo}">
				No session
			</c:when>

			<c:otherwise>
				<p>
				Active game sessions
				<table class='session-info'>
					<tr>
						<th>#</th>
						<th>Timestamp</th>
						<th>Game Id</th>
						<th>Secret</th>
						<th>No. of moves</th>
					</tr>
					<c:forEach var="sessionInfo" items="${sessionInfo}" varStatus="counter">
						<tr>
							<td>
								${counter.count}
							</td>
							<td>
								<c:out value="${sessionInfo.formattedTimestamp}" />
							</td>
							<td>
								<c:out value="${sessionInfo.sessionId}" />
							</td>
							<td>
								<c:out value="${sessionInfo.secret}" />
							</td>
							<td>
								<c:out value="${sessionInfo.numberOfMoves}" />
							</td>
						</tr>
					</c:forEach>
				</table>
				</p>
			</c:otherwise>
		</c:choose>

		<p>
			<a href="<c:url value="/session"/>">Refresh</a>
		</p>
		<jsp:include page="../fragments/footer.jsp"/>
	</body>
</html>