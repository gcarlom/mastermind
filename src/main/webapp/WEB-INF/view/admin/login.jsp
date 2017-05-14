<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE HTML>
<html>
	<head>
		<title>Mastermind - Login</title>
		<link href="<c:url value="/resources/css/main.css" />" rel="stylesheet" type="text/css" />
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	</head>
	<body onload='document.loginForm.userName.focus();'>
		<jsp:include page="../fragments/header.jsp" />
		<c:url var='actionUrl'  value='/perform_login' />
		<%-- default if nor redefined in spring-security.xml: login-processing-url would be j_spring_security_check' --%>
		<form:form name="loginForm" action="${actionUrl}" method="POST">
			<div align="center">
				<div class="error">${error}</div>
				<div class="message">${msg}</div>

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
							<button class="login" name="submit" value="submit">Login</button>
						</td>
					</tr>
				</table>
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />

			</div>
		</form:form>

		<jsp:include page="../fragments/footer.jsp"/>
	</body>
</html>