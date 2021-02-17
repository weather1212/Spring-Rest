<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- jstl 코어 태그 -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<style type="text/css">
a:link {
	color: rgb(140, 89, 185);
	text-decoration: none;
}

a:visited {
	color: rgb(140, 89, 185);
	text-decoration: none;
}

a:hover {
	color: black;
	text-decoration: none;
	box-shadow: 0 -6px rgba(75, 112, 253, 0.3) inset;
}
</style>

<!-- 일반 메뉴 -->
<a href="${path}/board/list">BOARD</a> |
<a href="${path}/upload/uploadForm">Upload</a> |
<a href="${path}/upload/uploadAjax">Upload(AJAX)</a> |
<a href="${path}/shop/product/list">SHOP</a> |
<a href="${path}/shop/cart/list">Cart</a> |

<!-- 관리자 권한일 경우 -->
<c:if test="${sessionScope.adminId != null }">
	<a href="${path}/shop/product/write">상품등록</a> |
</c:if>

<!-- 로그인, 로그아웃 -->
<c:choose>
	<c:when test="${sessionScope.userId == null}">
		<a href="${path}/member/login">LOGIN</a> |
		<a href="${path}/member/join">JOIN</a> |
		<a href="${path}/admin/login">!ADMIN!</a>
	</c:when>
	<c:otherwise>
		${sessionScope.userName}님이 로그인중입니다.
		<a href="${path}/member/logout">LOGOUT</a> |
	</c:otherwise>
</c:choose>	
<hr>