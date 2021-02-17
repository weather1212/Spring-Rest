<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<html>
<head>
<title>Home</title>
<%@ include file="include/header.jsp"%>
</head>
<body>
	<%@ include file="include/menu.jsp"%>
	<h1>Hello world!</h1>

	<P>The time on the server is ${serverTime}.</P>
	<c:if test="${msg == 'success' }">
		<h2>${sessionScope.adminName}(${sessionScope.adminId})님환영합니다.</h2>
	</c:if>

</body>
</html>
