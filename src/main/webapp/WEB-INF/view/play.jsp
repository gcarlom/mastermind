<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE HTML>
<html>
<head>
<title>Mastermind</title>
<link href="<c:url value="/resources/css/main.css" />" rel="stylesheet"
	type="text/css" />



</head>
<body>
	<h1 id="title">Mastermind</h1>
	<div>

		No. of moves so far: &nbsp;
		<c:out value="${fn:length(history)}" />
		<br /> Session ID: &nbsp;
		<c:out value="${sessionId}" />
		<br />
		<!-- Secret seq: &nbsp; ---- <!-- <c:out value="${secret}" /> -->
		<br /> <br />

		<div id="move-area">

			<c:if test="${not empty history}">
				<table>
					<c:forEach items="${history}" var="round" varStatus="loop">
						<tr>
							<td>${loop.index + 1}</td>
							<td>${round.sequence}</td>
							<td>${round.result}</td>
						</tr>
					</c:forEach>
				</table>
			</c:if>

			<!--  ** <c:out value="${history}" />  -->
			<c:url value="/play" var="formAction" />
			<!--  
			<c:url value="/play" var="formActionOLD">
				< c:param name="sessionId" value="${sessionId}"/>
			</c:url>
			 -->
			
			<c:choose>
				<c:when test="${not endOfGame}">
					<%-- TODO use post --%>
					<form class="sequence-area" action=<c:url value="/play" /> autocomplete="off" method="get">
						<div>
							<input name="move" type="text" value="" autofocus autocomplete="off"></input>
							<input name="sessionId" type="hidden" value="${sessionId}" />
							<%-- sessionID from session  --%>
							<button>Send my Move</button>
						</div>
					</form>
				</c:when>
				<c:otherwise>
					<c:choose>
						<c:when test="${userWon}">
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
			
			<!-- 

		<form class="sequence-area" action="${formAction}" method="get">
          <input class="sequence" id="pos-1" maxlength="1" type="text" />
          <input class="sequence" id="pos-2" maxlength="1" type="text" size="1"/>
          <input class="sequence" id="pos-3" maxlength="1" type="text" size="1"/>
          <input class="sequence" id="pos-4" maxlength="1" type="text" size="1"/>
        &nbsp;
        <button> Send </button>
 
         <a href="${formAction}">SEND THIS </a>
      </form>
 -->
		</div>
	</div>
</body>
</html>
