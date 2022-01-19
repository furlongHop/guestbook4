<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<!-- 루트(/)부터 시작하는 절대 경로 -->
	<form action="/guestbook4/guest/delete" method="get">
		비밀번호 <input type="password" name="password" value="">
		<button type="submit">확인</button>
		
		<input type="hidden" name="no" value="${param.no}">
	</form>
	<!-- a태그를 이용한 인터넷 주소(http://localhost:8088는 생략 가능) -->
	<a href="/guestbook4/guest/list">메인으로 돌아가기</a>

</body>
</html>