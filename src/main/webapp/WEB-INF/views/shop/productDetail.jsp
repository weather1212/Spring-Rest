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
	<h2>상품 상세정보</h2>
	<table border="1">
		<tr>
			<td>
				<img src="${path}/resources/images/${vo.productUrl}" width="350" height="350">
			</td>
			<td>
				<table border="1" style="height: 350px; width: 500px; text-align:center; border-collapse: collapse;" >
					<tr>
						<th>상품명</td>
						<td>${vo.productName}</td>
					</tr>
					<tr>
						<th>가격</td>
						<td><fmt:formatNumber value="${vo.productPrice}" pattern="###,###,###"/></td>
					</tr>
					<tr>
						<th style="width:80px; white-space: nowrap;">상품소개</td>
						<td>${vo.productDesc}</td>
					</tr>
					<tr>
						<td colspan="2">
							<form name="form1" method="post" action="${path}/shop/cart/insert">
								<input type="hidden" name="productId" value="${vo.productId}">
								<select name="amount">
									<c:forEach begin="1" end="10" var="i">
										<option value="${i}">${i}</option>
									</c:forEach>
								</select>&nbsp;개
								<input type="submit" value="장바구니에 담기">
							</form>
							<a href="${path}/shop/product/list">상품목록</a>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</body>
</html>