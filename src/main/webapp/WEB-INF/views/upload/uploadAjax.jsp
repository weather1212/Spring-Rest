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
					alert(data);
					var str = "";
					// 이미지 파일이면 썸네일 이미지 출력
					if(checkImageType(data)) {
						str = "<div><a href='${path}/upload/displayFile?fileName=" + getImageLink(data) + "'>";
						str += "<img src='${path}/upload/displayFile?fileName=" + data + "'></a>";
					} else {	// 일반 파일이면 다운로드 링크
						str="<div><a href='${path}/upload/displayFile?fileName=" + data + "'>" + getOriginalName(data) + "</a>";
						
					}
					// 삭제버튼
					str += "<span data-src=" + data + ">[삭제]</span></div>";
					$(".uploadedList").append(str);
				}
			});

		});
		
		// 파일 삭제 요청 처리
		$(".uploadedList").one("click", "span", function(event) {
			alert("이미지 삭제")
			var that = $(this);	// this 는 클릭한 span태그
			$.ajax({
				type: "post",
				url: "${path}/upload/deleteFile",
				// data: "fileName=" + $(this).attr("data-src") = {fileName:$(this).attr("data-src")}
				// 태그.attr(속성)
				data: {fileName:$(this).attr("data-src")},	// json방식
				dataType: "text",
				success: function(result) {
					if(result == "deleted") {
						// 클릭한 태그가 속한 div를 제거
						that.parent("div").remove();
					}
				}
				
			});
		});
		
		//===================파일 관련 스크립트=============================
			
		// 원본 파일 이름을 목록에 출력
		function getOriginalName(fileName) {
			// 이미지 파일이면
			if(checkImageType(fileName)) {
				return;	//함수 종료
			}
			
			// uuid를 제외한 원래 파일이름을 리턴
			var idx = fileName.indexOf("_")+1;
			return fileName.substr(idx);
		}
		
		// 이미지파일 링크 - 클릭하면 원본 이미지 출력
		function getImageLink(fileName) {
			// 이미지파일 아니면
			if(!checkImageType(fileName)) {
				return;	//함수 종료
			}
			// 이미지 파일이면 (썸네일이 아닌 원본이미지를 가져오기 위해)
			// 썸네일 이미지 파일명 => 파일경로 + 파일명 => /2021/02/08/s_109db3b6-b4c5-48f7-bc0b-e9478f84bb28_캐나다 밴쿠버.jpg
			var front = fileName.substr(0, 12);	// 년월일 경로 추출
			var end = fileName.substr(14);	// 년월일 경로와 s_를 제거한 원본 파일명을 추출
			
			console.log(front);	// /2021/02/08/
			console.log(end);	// 109db3b6-b4c5-48f7-bc0b-e9478f84bb28_캐나다 밴쿠버.jpg
			
			// 원본 파일명 => /2021/02/08/109db3b6-b4c5-48f7-bc0b-e9478f84bb28_캐나다 밴쿠버.jpg
			return front+end;	// 디렉토리를 포함한 원본파일명을 리턴 
		}
		
		// 이미지 파일 형식 체크
		function checkImageType(fileName) {
			// i : ignore case(대소문자 무관)
			var pattern = /jpg|gif|png|jpeg/i;	// 정규표현식
		
			return fileName.match(pattern);	// 규칙이 맞으면 true리턴
		}
		
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