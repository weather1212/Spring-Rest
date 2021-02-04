<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<%@ include file="../include/header.jsp"%>
<style type="text/css">
.fileDrop {
	width: 600px;
	height: 200px;
	border: 1px dotted blue;
}

small {
	margin-left: 3px;
	font-weight: bold;
	color: gray;
}
</style>
<script type="text/javascript">
	$(document).ready(function() {
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
				type: "post",
				url: "${path}/upload/uploadAjax";
				data: formData,
				// processDdata: true => get 방식, false => post 방식
				dataType: "text",
				// contentType: true => application/x-www-form-urlencoded,
				//				false => multipart/form-data
				success: function(data) {
					alert(data);
				}
			});

		});
	});
</script>
</head>
<body>
	<%@ include file="../include/menu.jsp"%>
	<h2>AJAX File Upload</h2>
	<!-- 파일을 업로드할 영역 -->
	<div class="fileDrop"></div>
	<!-- 업로드된 파일 목록 -->
	<div class="uploadedList"></div>
</body>
</html>