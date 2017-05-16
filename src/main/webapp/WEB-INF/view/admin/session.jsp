<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@page session="true"%>

<!DOCTYPE HTML>
<html>
	<head>
	<title>Mastermind - Admin</title>
	<link href="<c:url value="/resources/css/main.css" />" rel="stylesheet"
		type="text/css" />

	<%-- we need c:url to add the context path into the URL
	  c:url correctly resolves "/resources/css/main.css" -> "http://hostname/mastermind/resources/css/main.css"
	  If we used: <link href="/resources/css/main.css" ..> the browser would issue a GET for  "http://hostname/resources/css/main.css
	  and would get an 404 (not found) as response
	 --%>

	</head>
	<body>
		<h1 class="logo">Mastermind Admin</h1>

		<c:if test="${pageContext.request.userPrincipal.name != null}">
			<h4>Logged in as: ${pageContext.request.userPrincipal.name}</h4>
		</c:if>

		<h2>Sessions:</h2>
		<c:choose>
			<c:when test="${empty sessionInfo}">
				No session
			</c:when>

			<c:otherwise>
				<p>
				Active game sessions: <c:out value="${fn:length(sessionInfo)}"></c:out>
				</p>
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
			</c:otherwise>
		</c:choose>

		<a href="sessions"> Update</a>
		<a class="button-link" href="sessions">Update</a>
		<p>
			<a class="button-link" href="<c:url value="/login?logout" />" >Logout</a>
		</p>
	<jsp:include page="../fragments/footer.jsp"/>
	</body>
</html>