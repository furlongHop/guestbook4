<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<!-- 등록 폼 영역 -->
	<form action="/guestbook4/guest/add" method="get">
	<table border="1" width="500px">
		<tr>
			<td>이름</td>
			<td><input type="text" name="name" value=""></td>
			<td>비밀번호</td>
			<td><input type="password" name="password" value=""></td>
		</tr>
		
		<tr>
			<td colspan="4">
				<textarea rows="5" cols="65" name="content"></textarea>
			</td>		
		</tr>
		
		<tr>
			<td colspan="4">
				<button type="submit">글 작성</button>
			</td>
		</tr>
	</table>
	</form>
	<!-- /등록 폼 영역 -->
	
	<br>
	
	<!-- 리스트 영역 -->
	<c:forEach items="${gbList}" var="gbVo">
		<table border="1" width="500px">
			<tr>
				<td>${gbVo.no}</td>
				<td>${gbVo.name}</td>
				<td>${gbVo.regDate}</td>
				<td><a href="/guestbook4/guest/deleteForm?no=${gbVo.no}">삭제</a></td>
			</tr>
			<tr>
				<td colspan="4">
					${gbVo.content}
				</td>
			</tr>
		</table>
		<br>
	</c:forEach>
	<!-- /리스트 영역 -->
</body>
</html>