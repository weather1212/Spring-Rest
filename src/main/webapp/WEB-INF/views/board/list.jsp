<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>게시글 목록</title>
<%@ include file="../include/header.jsp"%>
<script>
	$(document).ready(function() {
		$("#btnWrite").click(function() {
			// 페이지 주소 변경(이동)
			location.href = "${path}/board/write";
		});
	});
	// 원하는 페이지로 이동시 검색조건, 키워드 값을 유지하기 위해
	function list(page) {
		location.href = "${path}/board/list?curPage=" + page
				+ "&searchOption=${map.serchOption}&keyword=${map.keyword}"
		location.href = "${path}/board/list?curPage=" + page
				+ "&searchOption-${map.searchOption}"
				+ "&keyword=${map.keyword}";
	}
</script>
</head>
<body>
	<%@ include file="../include/menu.jsp"%>
	<h2>Board list</h2>
	<form name="form1" method="post" action="${path}/board/list">
		<select name="searchOption">
			<!-- 검색조건을 검색처리후 결과화면에 보여주기위해  c:out 출력태그 사용, 삼항연산자 -->
			<option value="all" <c:out value="${map.searchOption == 'all'?'selected':''}"/>>제목+이름+제목</option>
			<option value="writer" <c:out value="${map.searchOption == 'writer'?'selected':''}"/>>이름</option>
			<option value="content" <c:out value="${map.searchOption == 'content'?'selected':''}"/>>내용</option>
			<option value="title" <c:out value="${map.searchOption == 'title'?'selected':''}"/>>제목</option>
		</select>
		<input name="keyword" value="${map.keyword}">
		<input type="submit" value="조회">
		<!-- 로그인한 사용자만 글쓰기 가능 -->
		<c:if test="${sessionScope.userId != null }">
			<button type="button" id="btnWrite">글쓰기</button>
		</c:if>
	</form>
	<hr>
	<br>
	<!-- 레코드의 갯수를 출력 -->
	${map.count}개의 게시물이 있습니다.
	<br>
	<br>
	<table border="1" width="800px" style="text-align: center; border-collapse: collapse;">
		<tr style="white-space: nowrap;">
			<th>번호</th>
			<th>제목</th>
			<th>이름</th>
			<th>작성일</th>
			<th>조회수</th>
		</tr>
		<c:forEach var="row" items="${map.list}">
			<c:choose>
				<c:when test="${row.show == 'y' }">
				<!-- show 컬럼이 y일때(삭제상태 x) -->
					<tr>
						<td>${row.bno}</td>
						<!-- 게시글 상세보기 페이지로 이동시 게시글 목록페이지에 있는 검색조건, 키워드, 현재페이지 값을 유지하기 위해 파라미터로 같이 넘겨줌-->
						<td style="text-align: left;">
							<a
								href="${path}/board/view?bno=${row.bno}&curPage=${map.boardPagination.curPage}&searchOption=${map.searchOption}&keyword=${map.keyword}"
							>${row.title} <!--  **댓글이 있으면 게시글 이름 옆에 댓글 개수 출력 --> <c:if
									test="${row.recnt > 0 }"
								>
									<span style="color: green;">(${row.recnt })</span>
								</c:if>
							</a>
						</td>
						<td>${row.userName}</td>
						<td>
							<!-- 원하는 날짜형식으로 출력하기 위해 fmt태그 사용 -->
							<fmt:formatDate value="${row.regdate}" pattern="yyyy-MM-dd HH:mm:ss" />
						</td>
						<td>${row.viewcnt}</td>
					</tr>
				</c:when>
				<c:otherwise>
				<!-- show 컬럼이 n일때(삭제상태) -->
					<tr>
						<td colspan="5" align="left">
							<c:if test="${row.recnt > 0 }">
								<a
									href="${path}/board/view.do?bno=${row.bno}&curPage=${map.boardPagination.curPage}&searchOption=${map.searchOption}&keyword=${map.keyword}"
								>삭제된 게시물입니다. <!-- 댓글이 있으면 게시글 이름 옆에 출력 --> <span style="color: red;">(${row.recnt })</span>
								</a>
							</c:if>
							<c:if test="${row.recnt == 0 }">
							삭제된 게시물입니다.
							</c:if>
						</td>
					</tr>
				</c:otherwise>
			</c:choose>
		</c:forEach>

		<!-- 페이징 처리 -->
		<tr>
			<td colspan="5">
				<!-- **처음페이지로 이동 : 현재페이지가 1보다 크면 [<<]하이퍼링크를 화면에 출력 -->
				<c:if test="${map.boardPagination.curBlock > 1 }">
					<a href="javascript:list('1')">[&#60;&#60;]</a>
				</c:if>
				<!-- **이전페이지 블록으로 이동 : 현재페이지블럭이 1보다 크면 [<]하이퍼링크를 화면에 출력 -->
				<c:if test="${map.boardPagination.curBlock > 1 }">
					<a href="javascript:list('${map.boardPagination.prevPage }')">[&#60;]</a>
				</c:if>

				<!-- **하나의 블럭에서 반복문 수행 시작페이지부터 끝페이지까지 -->
				<c:forEach var="num" begin="${map.boardPagination.blockBegin }"
					end="${map.boardPagination.blockEnd }"
				>
					<!-- **현재페이지면 하이퍼링크 제거 -->
					<c:choose>
						<c:when test="${num == map.boardPagination.curPage }">
							<span style="color: red;">${num }</span>&nbsp;
						</c:when>
						<c:otherwise>
							<a href="javascript:list('${num }')">${num }</a>&nbsp;
						</c:otherwise>
					</c:choose>
				</c:forEach>

				<!-- **다음페이지 블록으로 이동 : 현재페이지 블록이 전체 페이지 블럭보다 작거나 같음면 [>]하이퍼링크를 화면에 출력  -->
				<c:if test="${map.boardPagination.curBlock <= map.boardPagination.totBlock }">
					<a href="javascript:list('${map.boardPagination.nextPage }')">[&#62;]</a>
				</c:if>
				<!-- **끝페이지로 이동 : 현재 페이지가 전체페이지보다 작으면 [>>]하이퍼링크를 화면에 출력  -->
				<c:if test="${map.boardPagination.curPage < map.boardPagination.totPage }">
					<a href="javascript:list('${map.boardPagination.totPage }')">[&#62;&#62;]</a>
				</c:if>
			</td>
		</tr>
	</table>
</body>
</html>
