<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>로그인 페이지</title>
<%@ include file="../include/header.jsp"%>
<script type="text/javascript">
	$(document).ready(function() {
		$("#btnLogin").click(function() {
			// Element.val() : 요소에 입력된 값
			// Element.val("") : 요소의 값을 인자로 변경
			var userId = $("#userId").val();
			var userPw = $("#userPw").val();
			if (userId == "") {
				alert("아이디를 입력하세요.");
				$("#userId").focus(); //입력포커스 이동
// 				return; // 함수 종료
			}
			if (userPw == "") {
				alert("비밀번호를 입력하세요.");
				$("#userPw").focus(); //입력포커스 이동
// 				return; // 함수 종료
			}
			//폼내부의 데이터를 전송할 주소
			document.form1.action = "${path}/member/loginCheck"; //action속성 값 지정
			//제출
			document.form1.submit();

		});

	});
</script>
</head>
<body>
	<%@ include file="../include/menu.jsp"%>
	<h2>로그인</h2>
	<form name="form1" method="post">
		<table border="1" width="400px">
			<tr>
				<td>아이디</td>
				<td><input name="userId" id="userId"></td>
			</tr>
			<tr>
				<td>비밀번호</td>
				<td><input type="password" name="userPw" id="userPw"></td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<button id="btnLogin">로그인</button> <c:if test="${msg == 'failure' }">
						<div style="color: red;">아이디 또는 비밀번호가 일치하지 않습니다.</div>
					</c:if> <c:if test="${msg == 'logout' }">
						<div style="color: red;">로그아웃 되었습니다.</div>
					</c:if>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>