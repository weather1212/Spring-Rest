<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>게시글 작성</title>
<%@ include file="../include/header.jsp"%>
<script>
	$(document).ready(function() {
		// 댓글 목록
		listReply("1");	// **forwarding 방식
// 		listReply2();	// **json 리턴 방식
		
		// **댓글 쓰기
		$("#btnReply").click(function() {
			//reply();		// 파라미터로 입력
			replyJson();	// json으로 입력
		});

		$("#btnDelete").click(function() {
			if (confirm("삭제하시겠습니까?")) {
				document.form1.action = "${path}/board/delete";
				document.form1.submit();
			}
		});

		$("#btnUpdete").click(function() {
			//var title = document.form1.title.value; ==> name속성으로 처리할 경우
			//var content = document.form1.content.value;
			//var writer = document.form1.writer.value;
			var title = $("#title").val();
			var content = $("#content").val();
			var writer = $("#writer").val();
			if (title == "") {
				alert("제목을 입력하세요");
				document.form1.title.focus();
				return;
			}
			if (content == "") {
				alert("내용을 입력하세요");
				document.form1.content.focus();
				return;
			}
			/* if (writer == "") {
				alert("이름을 입력하세요");
				document.form1.writer.focus();
				return;
			} */
			document.form1.action = "${path}/board/updateAction"
			// 폼에 입력한 데이터를 서버로 전송
			document.form1.submit();
		});
		$("#btnList").click(function() {
			console.log("curPage=${map.curPage}&searchOption=${map.serchOption}&keyword=${map.keyword}");
			if (confirm("게시글 목록으로 돌아가시겠습니다까?")) {
				location.href = "${path}/board/list?curPage=${map.curPage}&searchOption=${map.serchOption}&keyword=${map.keyword}";
			}
		});
	});
	
	// **댓글 쓰기========================================
	// 파라미터 전달 방식 댓글쓰기
	function reply() {
		var replytext=$("#replytext").val();
		var bno = "${dto.bno}"
		// **비밀댓글 체크 여부
		var secretReply = 'n';
		// 태그.is(":속성") 태그에 해당 속성이 있는지 boolean반환
		if($("#secretReply").is(":checked")) {	//체크 여부
			secretReply = 'y';
		}
		//alert(secretReply);
		// **비밀댓글 파라미터 추가
		var param = "replytext=" + replytext + "&bno=" + bno + "&secretReply=" + secretReply;	//ajax를 통해 보낼 데이터
		$.ajax({
			type: "post",
			url: "${path}/reply/write",
			data: param,
			success: function() {
				alert("댓글이 등록되었습니다.")
				// **추가된 댓글 목록 ajax요청
				listReply("1");		// forward(@Controller)
// 				listReply2();	// json 리턴(@ReseteController)
			},
			error: function() {
				console.log("데이터 전송에 실패했습니다.");
			}
			
		});
	}
	// **json 방식 댓글 쓰기
	function replyJson() {
		var replytext=$("#replytext").val();
		var bno = "${dto.bno}"
		// **비밀댓글 체크 여부
		var secretReply = 'n';
		// 태그.is(":속성") 태그에 해당 속성이 있는지 boolean반환
		if($("#secretReply").is(":checked")) {	//체크 여부
			secretReply = 'y';
		}
		//alert(secretReply);
		$.ajax({
			type: "post",
			url: "${path}/reply/writeRest",
			contentType: "application/json",
			dataType: "text",
			data: JSON.stringify({
				bno: bno,
				replytext: replytext,
				secretReply: secretReply
			}),
			success: function(msg) {
				alert("댓글이 등록되었습니다.")
				console.log("ajax success: " + msg);
				// **추가된 댓글 목록 ajax요청
				listReply("1");		// forward(@Controller)
// 				listReply2();	// json 리턴(@ReseteController)
			},
			error: function(request, status, error) {	//status-상태, error-에러 내용
				console.log("데이터 전송에 실패했습니다. : " + "status : " + request.status + ", error : " + error);
			}
			
		});
	}
	
	// **댓글목록========================================
	// @Controller 방식(파라미터 전달)
	function listReply(num) {
		console.log(num);
		$.ajax({
			type: "get",
			url: "${path}/reply/list?bno=${dto.bno}&curPage=" + num,
			success: function(result) {
				// responseText가 result에 저장됨
				$("#listReply").html(result);
			},
			error: function() {
				console.log("댓글 목록을 불러오지 못했습니다.");
			}
			
		});
	}
	// @RestController 방식 (json)
	function listReply2() {
		$.ajax({
			type: "get",
			// contentType: "application/json", ==> RestController이기 때문에 생략가능
			url: "${path}/reply/listJson?bno=${dto.bno}",
			success: function(result) {
				console.log(result);
				var printout = "(RestController 방식)<table>";
				for(var i in result){
					printout += "<tr>";
					printout += "<td><br>" + result[i].userName;
					printout += "(" + changeDate(result[i].regdate) + ")<br>";
					printout += result[i].replytext + "</td>";
					printout += "<tr>";
				}
				printout += "</table>";
				$("#listReply").html(printout);
			},
			error: function() {
				console.log("댓글 목록을 불러오지 못했습니다.");
			}
		});
		
	}
	// **날짜 변환 함수 작성
	function changeDate(date) {
		date = new Date(parseInt(date));
		year = date.getFullYear();
		month = date.getMonth();
		day = date.getDate();
		hour = date.getHours();
		minute = date.getMinutes();
		second = date.getSeconds();
		strDate = year + "-" + month + "-" + day + "-" + hour + ":" + minute + ":" +second;
		
		return strDate;
	}
</script>
</head>
<body>
	<%@ include file="../include/menu.jsp"%>
	<h2>BOARD</h2>
	<form name="form1" method="post">
		<div>
			<!-- 원하는 날짜형식으로 출력하기 위해 fmt태그 사용 -->
			작성일자 :
			<fmt:formatDate value="${dto.regdate}" pattern="yyyy-MM-dd a HH:mm:ss" />
			<!-- 날짜 형식 => yyyy 4자리연도, MM 월, dd 일, a 오전/오후, HH 24시간제, hh 12시간제, mm 분, ss 초 -->
		</div>
		<div>조회수 : ${dto.viewcnt}</div>
		<div>
			작성자(이름)
			<%--  <input name="writer" id="writer" value="${dto.writer}"
				placeholder="이름을 입력해주세요"> --%>
			${dto.userName }
		</div>
		<hr>
		<div>
			제목
			<input name="title" id="title" size="80" value="${dto.title}" placeholder="제목을 입력해주세요">
		</div>
		<br>
		<div>
			<textarea name="content" id="content" rows="20" cols="88" placeholder="내용을 입력해주세요">${dto.content}</textarea>
		</div>
		<div style="width: 650px; text-align: center;">
			<!-- 게시물번호를 hidden으로 처리 -->
			<input type="hidden" name="bno" value="${dto.bno}">
			<input type="hidden" name="writer" value="${dto.writer}">
			<c:if test="${sessionScope.userId == dto.writer }">
				<button type="button" id="btnUpdete">수정</button>
				<button type="button" id="btnDelete">삭제</button>
			</c:if>
			<button type="button" id="btnList">목록으로</button>
		</div>
	</form>
	
	<!-- 댓글 섹션 -->
	<div style="width:650px;">
		<br>
		<!-- **로그인한 화원에게만 댓글 작성 폼이 보이게 처리 -->
		<c:if test="${sessionScope.userId != null }">
			<hr>
			<textarea rows="5" cols="50" id="replytext" placeholder="comment here!"></textarea>
			<br>
			<!-- **비밀댓글 체크 박스 -->
			<input type="checkbox" id="secretReply">비밀 댓글
			<button type="button" id="btnReply">댓글 달기</button>
			<hr>
		</c:if>
	</div>
	<!-- 댓글 목록을 출력할 위치 -->
	<div id="listReply"></div>
</body>
</html>
