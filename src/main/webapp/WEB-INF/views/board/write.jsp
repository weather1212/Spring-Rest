<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>게시글 작성</title>
<%@ include file="../include/header.jsp"%>
<!-- 로그인 인증처리 Interceptor로 대신 -->
<%-- <%@ include file="../include/sessionCheck.jsp"%> --%>
<script type="text/javascript" src="${path}/resources/js/common.js"></script>
<script>
	$(document).ready(function() {
		//===========================파일 업로드==============================
		// 첨부파일 추가 ajax 요청
		// 파일 업로드 영역에 텍스트 파일 또는 이미지파일을 드래그했을 때 내용이 바로 보여지는 기본 효과 막음
		// dragenter : 마우스가 대상 객체의 위로 처음 진입할 때 발생
		// dragover : 드래그하면서 마우스가 대상 객체의 위에 자리 잡고 있을 때 발생
		$(".fileDrop").on("dragenter dragover", function(event) {
			event.preventDefault(); // 기본 효과를 막음
		});

		// event: jQuery의 이벤트
		// originalEvent: javascript의 이벤트
		$(".fileDrop").on("drop", function(event) {
			event.preventDefault(); // 기본 효과를 막음
			// 드래그된 파일의 정보
			var files = event.originalEvent.dataTransfer.files;
			// 첫번째 파일
			var file = files[0];
			// 콘솔에서 파일정보 확인
			console.log(file);

			// ajax로 전달할 폼 객체
			var formData = new FormData();
			// 폼 객체 파일추가, append("변수명", 값)
			formData.append("file", file);

			// file을 전달할 때는 ajax옵션 속성을  type:post, processDdata: false, contentType:false로 설정한다.
			$.ajax({
				type : "post",
				url : "${path}/upload/uploadAjax",
				data : formData,
				dataType : "text",
				// processDdata: true => get 방식, false => post 방식
				processData : false,
				// contentType: true => application/x-www-form-urlencoded,
				//				false => multipart/form-data
				contentType : false,
				success : function(data) {
					//로그 출력
					console.log(data);
					// 첨부 파일의 정보
					var fileInfo = getFileInfo(data);
					// 로그 출력
					console.log("파일인포쩜fileName : " + fileInfo.fileName);
					console.log("파일인포쩜imgsrc : " + fileInfo.imgsrc);
					console.log("파일인포쩜getLink : " + fileInfo.getLink);
					console.log("파일인포쩜fullName : " + fileInfo.fullName);
					// 하이퍼링크
					var html = "<div><img src='" + fileInfo.imgsrc + "'><br>" +fileInfo.fileName;
					html += "<a href = '#' class='fileDel' data-src='" + this + "'>[삭제]</a></div>";
					// hidden 태그 추가
					html += "<input type='hidden' class='file' value='"+fileInfo.fullName+"'>";
					// div에 추가
					$(".fileDrop").append(html);
				}
			});

		});
		
		// 첨부파일 삭제 ajax 요청
		// 태그.on("이벤트", "자손태그", 이벤트 핸들러)
		$(".fileDrop").on("click", ".fileDel", function(event) {
			var that = $(this); //클릭한 a태그
			$.ajax({
				type : "post",
				url : "${path}/upload/deleteFile",
				// data: "fileName=" + $(this).attr("data-src") = {fileName:$(this).attr("data-src")}
				data : {fileName : $(this).attr("data-src")},
				dataType : "text",
				success : function(result) {
					if (result == "deleted") {
						that.parent("div").remove();
					}
				}
			});
		});
		
		// 게시글 등록 버튼 클릭
		$("#btnSave").click(function() {
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
			
			// 첨부파일 이름을 form에 추가
			var that = $("#form1");
			var str = "";
			// 태그들.each(함수)
			// id가 uploadedList인 태그 내부에 있는 hidden태그들
			$(".fileDrop .file").each(function(i){
				str += "<input type='hidden' name='files["+i+"]' value='"+$(this).val()+"'>";
			});
			// form에 hidden태그들을 추가
			$("#form1").append(str);
			// 폼에 입력한 데이터를 서버로 전송
			document.form1.submit();
		});
		
		$("#btnList").click(function() {
			if (confirm("게시글 목록으로 돌아가시겠습니다까?")) {
				location.href = "${path}/board/list?curPage${curPage}&searchOption=${serchOption}&keyword=${keyword}";
			}
		});
	});
</script>
<style type="text/css">
/* 첨부파일을 드래그할 영역의 스타일 */
.fileDrop {
	width: 600px;
	height: auto;
	padding: 10px 0px; 
	border: 2px dotted gray;
}
</style>
</head>
<body>
	<%@ include file="../include/menu.jsp"%>
	<h2>POST</h2>
	<form action="${path}/board/writeAction" id="form1" name="form1" method="post">
		<div>작성자(이름) : ${sessionScope.userName }</div>
		<hr>
		<div>
			제목
			<input name="title" id="title" size="80" placeholder="제목을 입력해주세요">
		</div>
		<br>
		<div>
			<textarea name="content" id="content" rows="20" cols="88" placeholder="내용을 입력해주세요"></textarea>
		</div>
		<div>
			첨부파일 등록
			<!-- 첨부파일 등록 영역 -->
			<div class="fileDrop"></div><br>
			<!-- 첨부파일의 목록 출력영역 -->
			<div id="uploadedList"></div>
		</div>
		<br>
		<div style="width: 650px; text-align: center;">
			<button type="button" id="btnSave">확인</button>
			<button type="reset">취소</button>
			<button type="button" id="btnList">목록으로</button>
		</div>
	</form>
</body>
</html>
