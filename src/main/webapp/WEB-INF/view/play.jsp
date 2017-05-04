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
	<div class="container">

	<jsp:include page="./fragments/header.jsp" />

		<c:choose>
			<c:when test="${not empty history}">
				No. of moves so far: &nbsp;
				<c:out value="${history.length}" />
			</c:when>
			<c:otherwise>
				New game
			</c:otherwise>
		</c:choose>
		<br /> Session ID: &nbsp;
		<c:out value="${moveForm.sessionId}" />
		<br />
		<br />
		<br />

		<c:if test="${not empty history}">
			<div id="history-area">
				<div class="history-table" id="history-table">
					<c:forEach items="${history.rounds}" var="round" varStatus="counter">
						<div class="history-row">
							<span class="cell round-index">
								${counter.count}.&nbsp;
							</span>
							<%--  Sequence --%>
							<span id="round-sequence">
								<c:set var="sequence"  value="${round.sequence.asString}"/>
								<c:forEach var="i" begin="0" end="${fn:length(sequence)-1}" step="1">
								<c:set var="color"  value="${fn:substring(sequence, i, i+1)}"/>
									<span class="cell color color-${color}">
										<c:out value="${color}" />
									</span>
								</c:forEach>
							</span>

							<%-- result (black/white pegs) --%>
							<span class="cell round-result">
								${round.result}
							</span>
						</div>
					</c:forEach>
				</div>
			</div>
		</c:if>

			<c:url value="/play" var="formAction" >
				<%-- Comment following line if you prefer not to have sessionId in URL --%>
				<c:param name="sessionId" value="${moveForm.sessionId}"/>
			</c:url>

			<c:choose>
				<c:when test="${not endOfGame}">
				<%-- Game is not over yet: get next move --%>
					<form:form modelAttribute="moveForm" action="${formAction}" cssStyle="sequence-area" autocomplete="off" method="post">
						<form:errors path="move" cssClass="validation-error"/><br/>
						<form:input path="move" type="text" autofocus="autofocus" autocomplete="off"/> <!-- bind to moveForm.move-->

				<%-- Uncomment this if you prefer to have sessionId as hidden parameter instead of embedded in URL
					<form:input path="sessionId" type="hidden" /> <!-- bind to moveForm.sessionId-->
				--%>
						<button>Send my Move</button>
					</form:form>
				</c:when>

				<c:otherwise>
				<%-- Game is over --%>
					<c:choose>
						<c:when test="${userWon}">
						<%-- TODO GC: use localized messages here --%>
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
	<jsp:include page="./fragments/footer.jsp" />
</body>
</html>
