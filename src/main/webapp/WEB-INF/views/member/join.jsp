<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>회원가입 페이지</title>
<%@ include file="../include/header.jsp"%>
<script type="text/javascript">
	$(document).ready(function() {
		$("#userId").blur(function() {
			var userId = $("#userId").val();
			$.ajax({
				url : "${path}/member/idCheck?userId=" + userId,
				type : "get",
				// 				data: userId,
				success : function(data) {
					console.log("1 = 아이디 중복 / 0 = 사용가능한 아이디" + data);

					if (data == 1) {
						// 1 : 아이디가 중복되는 문구
						$("#idCheck").text("사용중인 아이디입니다 :(");
						$("#idCheck").css("color", "red");
						// 								$("#").attr("disabled", true);
						document.joinForm.attr("disabled", true);
					} else {
						$("#idCheck").text("사용가능한 아이디입니다 :p");
						$("#idCheck").css("color", "green");
					}
				},
				error : function() {
					console.log("아이디체크 실패");
				}
			});
		});
	});
</script>
</head>
<body>
	<%@ include file="../include/menu.jsp"%>
	<h2>JOIN US</h2>
	<form action="${path }/member/joinAction" name="joinForm" method="post">
		<fieldset>
			<legend>Information entry</legend>
			<label for="userId">아이디</label>
			<input type="text" name="userId" id="userId" required="required"
				placeholder="아이디를 입력하세요"
			>
			<div id="idCheck"></div>
			<label for="userPw">비밀번호</label>
			<input type="password" name="userPw" id="userPw" required="required"
				placeholder="비밀번호를 입력하세요"
			>
			<br /> <label for="userName">이름</label>
			<input type="text" name="userName" id="userName" required="required"
				placeholder="이름을 입력하세요"
			>
			<br /> <label for="userEmail">이메일</label>
			<input type="email" name="userEmail" id="userEmail" required="required"
				placeholder="email을 입력하세요"
			>
			<br>
			<br>
			<input type="submit" id="btnJoin" value="회원가입">
			<c:if test="${msg == 'failure' }">
				<div style="color: red;">회원가입에 실패했습니다.</div>
			</c:if>
			<c:if test="${msg == 'logout' }">
				<div style="color: red;">로그아웃 되었습니다.</div>
			</c:if>
		</fieldset>
	</form>
</body>
</html>