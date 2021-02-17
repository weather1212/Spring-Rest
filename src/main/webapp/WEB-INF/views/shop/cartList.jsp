<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상품 장바구니 목록</title>
<%@ include file="../include/header.jsp"%>
<script type="text/javascript">
	$(document).ready(function() {
		// 리스트 페이지 이동
		$("#btnList").click(function() {
			location.href = "${path}/shop/product/list";
		});
	});
</script>
</head>
<body>
	<%@ include file="../include/menu.jsp"%>
	<h2>MY Shopping Cart</h2>
	<c:choose>
		<c:when test="${map.count == 0 }">
			<h1>텅</h1>
		</c:when>
		<c:otherwise>
			<form action="${path }/shop/cart/update" name="form1" id="form1" method="post">
				<table border="1">
					<tr>
						<th>상품명</th>
						<th>단가</th>
						<th>수량</th>
						<th>금액</th>
						<th>취소</th>
					</tr>
					<c:forEach var="row" items="${map.list }" varStatus="i">
						<tr>
							<td>${row.productName }</td>
							<td style="width: 80px; text-align: right">
								<fmt:formatNumber pattern="###,###,###" value="${row.productPrice }" />
							</td>
							<td>
								<input type="number" style="width: 40px;" name="amount" value="${row.amount }" min="1">
								<input type="hidden" name="productId" value="${row.productId }">
							</td>
							<td style="width: 100px; text-align: right;">
								<fmt:formatNumber pattern="###,###,###" value="${row.money }" />
							</td>
							<td>
								<a href="${path }/shop/cart/delete?cartId=${row.cartId }">삭제</a>
							</td>
						</tr>
					</c:forEach>
					<tr>
						<td colspan="5" style="text-align: right;">
							장바구니 금액 합계 : <fmt:formatNumber pattern="###,###,###" value="${map.sumMoney }" /><br>
							배송료 : ${map.fee }<br>
							전체 주문 금액 : <fmt:formatNumber pattern="###,###,###" value="${map.allSum }" />
						</td>
					</tr>
				</table>
				<input type="hidden" name="count" value="${map.count }">
				<button type="submit" id="btnUpdate">수정</button>
			</form>
		</c:otherwise>
	</c:choose>
	<button type="button" id="btnList">상품목록</button>
</body>
</html>