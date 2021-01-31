<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<%@ include file="../include/header.jsp"%>
</head>
<body>
	(controller방식)
	<table style="width: 700px">
		<c:forEach var="row" items="${list }">
			<tr>
				<td>
					<br> ${row.userName} (
					<fmt:formatDate value="${row.regdate }" pattern="yyyy-MM-dd HH:mm:ss" />
					) <br> ${row.replytext }
					<hr>
				</td>
			</tr>
		</c:forEach>
		<!-- 페이징 처리 -->
		<tr>
			<td>
				<!-- **처음페이지로 이동 : 현재페이지가 1보다 크면 [<<]하이퍼링크를 화면에 출력 -->
				<c:if test="${replyPagination.curBlock > 1 }">
					<a href="javascript:listReply('1')">[&#60;&#60;]</a>
				</c:if>
				<!-- **이전페이지 블록으로 이동 : 현재페이지블럭이 1보다 크면 [<]하이퍼링크를 화면에 출력 -->
				<c:if test="${replyPagination.curBlock > 1 }">
					<a href="javascript:listReply('${replyPagination.prevPage }')">[&#60;]</a>
				</c:if>

				<!-- **하나의 블럭에서 반복문 수행 시작페이지부터 끝페이지까지 -->
				<c:forEach var="num" begin="${replyPagination.blockBegin }"
					end="${replyPagination.blockEnd }"
				>
					<!-- **현재페이지면 하이퍼링크 제거 -->
					<c:choose>
						<c:when test="${num == replyPagination.curPage }">
							<span style="color: red;">${num }</span>&nbsp;
						</c:when>
						<c:otherwise>
							<a href="javascript:listReply('${num }')">${num }</a>&nbsp;
						</c:otherwise>
					</c:choose>
				</c:forEach>

				<!-- **다음페이지 블록으로 이동 : 현재페이지 블록이 전체 페이지 블럭보다 작거나 같음면 [>]하이퍼링크를 화면에 출력  -->
				<c:if test="${replyPagination.curBlock <= replyPagination.totBlock }">
					<a href="javascript:listReply('${replyPagination.nextPage }')">[&#62;]</a>
				</c:if>
				<!-- **끝페이지로 이동 : 현재 페이지가 전체페이지보다 작으면 [>>]하이퍼링크를 화면에 출력  -->
				<c:if test="${replyPagination.curPage < replyPagination.totPage }">
					<a href="javascript:listReply('${replyPagination.totPage }')">[&#62;&#62;]</a>
				</c:if>
	</table>
</body>
</html>