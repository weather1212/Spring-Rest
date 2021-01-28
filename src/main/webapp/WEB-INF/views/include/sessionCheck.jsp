<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- jstl 코어 태그 -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:if test="${sessionScope.userId == null }">
	<script>
		alert("로그인이 필요합니다.");
		location.href = "${path}/member/login";
	</script>
</c:if>