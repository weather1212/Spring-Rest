<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<%@ include file="../include/header.jsp"%>
<style type="text/css">
/* iframe 스타일 설정 */
iframe {
	width: 600px;
	height: 100px;
	border: 1px;
	border-style: solid;
}
</style>
</head>
<body>
	<%@ include file="../include/menu.jsp"%>
	<!-- target을 지정한 곳으로 form data가 이동 -->
	<!-- enctype="multipart/form-data" => 파일 업로드시 필수 옵션 -->
	<form action="${path }/upload/uploadForm" method="post" enctype="multipart/form-data"
		id="form1" target="iframePhoto"
	>
		<input type="file" name="file">
		<input type="submit" value="업로드">
	</form>
	<!-- form data가 이곳으로 이동 -->
	<iframe name="iframePhoto" />
	
	<script type="text/javascript">
		function addFilePath(msg) {
			console.log(msg); //파일명 콘솔 출력
			document.getElementById("form1").reset(); //iframe에 업로드 결과를 출력 후 form에 저장된 데이터 초기화
		}
	</script>
</body>
</html>