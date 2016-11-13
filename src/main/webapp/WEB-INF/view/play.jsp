<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE HTML>
<html>
<head>
<title>Mastermind</title>
<link href="<c:url value="/resources/css/main.css" />" rel="stylesheet"
	type="text/css" />
</head>

<body>
	<jsp:include page="./fragments/header.jsp" />

	<div>
		No. of moves so far: &nbsp;
		<c:out value="${history.length}" />
		<br /> Session ID: &nbsp;
		<c:out value="${moveForm.sessionId}" />
		<br />
		<br />
		<br />

			<c:if test="${not empty history}">
			<div id="history-area">
				<table>
					<c:forEach items="${history.rounds}" var="round" varStatus="loop">
						<tr>
							<td>${loop.index+1}:</td>
							<td>${round.sequence}</td>
							<td>${round.result}</td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</c:if>

			<c:url value="/play" var="formAction" />

			<c:choose>
				<c:when test="${not endOfGame}">
				<%-- Game is not over yet: get next move --%>
					<form:form modelAttribute="moveForm" action="${formAction}" cssStyle="sequence-area" autocomplete="off" method="post">
						<form:errors path="move" cssClass="validation-error"/><br/>
						<form:input path="move" type="text" autofocus="autofocus" autocomplete="off"/> <!-- bind to moveForm.move-->

						<form:input path="sessionId" type="hidden" /> <!-- bind to moveForm.sessionId-->
						<button>Send my Move</button>
					</form:form>
				</c:when>

				<c:otherwise>
				<%-- Game is over --%>
					<c:choose>
						<c:when test="${userWon}">
						<%-- TODO use localized messages here --%>
							<h2>Congratulations, you won !</h2>
						</c:when>
						<c:otherwise>
							<h2>Sorry, you lost.</h2>
							<h3>Secret sequence was: ${secret}</h3>
						</c:otherwise>
					</c:choose>
					<p>
						<a href="<c:url value="/play"/>">Play again</a>
					</p>
				</c:otherwise>
			</c:choose>


	</div>
	<jsp:include page="./fragments/footer.jsp"/>
</body>
</html>
