<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<%@ include file="../include/header.jsp"%>
<script type="text/javascript">
	// 댓글 수정
	$("#btnReplyUpdate").click(function() {
		var detailReplytext = $("#detailReplytext").val();
		$.ajax({
			type : "put",
			url : "${path}/reply/update/${vo.rno}",
			contentType : "application/json",
			data : JSON.stringify({ 	// 데이터를 json형태의 문자열로 변환
				replytext : detailReplytext
			}),
			dataType : "text",
			success : function(result) {
				if (result == "success") {
					$("#modifyReply").css("visibility", "hidden");
					// 댓글 목록 갱신
					listReplyRest("1");
				}
			}
		});
	});

	// 댓글 상세 화면 닫기
	$("#btnReplyClose").click(function() {
		$("#modifyReply").css("visibility", "hidden")
	});

	// 댓글 삭제
	$("#btnReplyDelete").click(function() {
		if (confirm("삭제하시겠습니까?")) {
			$.ajax({
				type : "delete",
				url : "${path}/reply/delete/${vo.rno}",
				success : function(result) {
					if (result == "success") {
						alert("삭제되었습니다.");
						$("#modifiReply").css("visibility", "hidden");
						listReplyRest("1");
					}
				}
			});
		}
	});
</script>
</head>
<body>
	댓글 번호 : ${vo.rno }
	<br>
	<textarea rows="5" cols="50" id="detailReplytext">${vo.replytext }</textarea>
	<div style="text-align: center;">
		<!-- 본인댓글만 수정, 삭제가 가능하도록 처리 -->
		<c:if test="${sessionScope.userId == vo.replyer}">
			<button id="btnReplyUpdate">수정</button>
			<button id="btnReplyDelete">삭제</button>
		</c:if>
		<button id="btnReplyClose">닫기</button>
	</div>
</body>
</html>