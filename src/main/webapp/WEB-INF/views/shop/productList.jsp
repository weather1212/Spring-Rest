<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상품 목록</title>
<%@ include file="../include/header.jsp"%>
</head>
<body>
	<%@ include file="../include/menu.jsp"%>
	<h2>Shoes</h2>
	<table border="1" style="border-collapse: collapse; text-align: center;">
		<tr>
			<th>상품 ID번호</th>
			<th>상품 이미지</th>
			<th>상품명</th>
			<th>가격</th>
		</tr>
		<c:forEach var="row" items="${list }">
			<tr>
				<td>${row.productId }</td>
				<td>
					<a href="${path }/shop/product/detail/${row.productId}">
					<img alt="" src="${path}/resources/images/${row.productUrl}" width="120px" height="120px">
					</a>
				</td>
				<td>
					<a href="${path }/shop/product/detail/${row.productId}">${row.productName }</a>
				</td>
				<td>
					<!-- 상품가격의 가독성을 높이기 위해 숫자 3자리마다 콤마(,)를 찍어주도록 처리 -->
					<fmt:formatNumber value="${row.productPrice }" pattern="###,###,###" />
				</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>