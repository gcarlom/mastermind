<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE HTML>
<html>
	<head>
		<title>Mastermind - Login</title>
		<link href="<c:url value="/resources/css/main.css" />" rel="stylesheet" type="text/css" />
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	</head>
	<body>
		<jsp:include page="../fragments/header.jsp" />
		<form:form name="submitForm" method="POST">
			<div align="center">
				<table>
					<tr>
						<td class="login">User</td>
						<td>
							<input class="login" type="text" name="userName" />
						</td>
					</tr>
					<tr>
						<td class="login">Password</td>
						<td>
							<input class="login" type="password" name="password" />
						</td>
					</tr>
					<tr>
						<td></td>
						<td>
							<button class="login">Login</button>
						</td>
					</tr>
				</table>
				<div style="color: red">${error}</div>
			</div>
		</form:form>

		<jsp:include page="../fragments/footer.jsp"/>
	</body>
</html>