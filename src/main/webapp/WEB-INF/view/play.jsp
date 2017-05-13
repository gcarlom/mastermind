<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

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
			<div id="history-area" class="history-area">
				<div class="history-table" id="history-table">
					<c:forEach items="${history.rounds}" var="round" varStatus="counter">
						<%--ROW BEGIN --%>
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

							<%-- result.asSymbol is a list of strings representing black/white pegs --%>
							<c:set var="resultList"  value="${round.result.asSymbol}"/>
							<span class="cell round-result">
								<c:forEach items="${resultList}" var="resultPeg">
									<c:choose>
										<c:when test="${resultPeg == 'X'}">
											<c:set var="pegClass"  value="peg-black" />
											<%--  cross product (U-2A2F) --%>
											<c:set var="pegSymbol"  value="&#x2a2f;" />
										</c:when>
										<c:when test="${resultPeg == 'O'}">
											<c:set var="pegClass"  value="peg-white" />
											<c:set var="pegSymbol"  value="o" />
										</c:when>
									</c:choose>
									<span class="peg ${pegClass}">
										${pegSymbol}
									</span>
								</c:forEach>
							</span>

						</div>
						<%--ROW END --%>
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
						<button class="send-move">Send my Move</button>
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
