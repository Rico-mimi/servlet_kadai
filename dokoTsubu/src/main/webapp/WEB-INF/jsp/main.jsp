<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- <%@ page import="model.User, model.Mutter, java.util.List" %>

<%
//セッションスコープに保存されたユーザー情報を取得
User loginUser = (User)session.getAttribute("loginUser");

//アプリケーションスコープに保存されたユーザー情報を取得
List<Mutter>mutterList=(List<Mutter>)application.getAttribute("mutterList");

//リクエストスコープに保存されたエラーメッセージを取得
String errorMsg = (String)request.getAttribute("errorMsg");

%>
 --%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>どこつぶ</title>
</head>
<body>
<h1>どこつぶメイン</h1>
<p>
<c:out value="${loginUser.name}" />さん、ログイン中
<a href="Logout">ログアウト</a>
</p>
<p><a href="Main">更新</a></p>
<form action="Main" method="post">
<input type="text" name="text">
<input type="submit" value="つぶやく">
</form>

<c:if test="${not empty errorMsg}">
	<p><c:out value="${errorMsg}" /></p>
</c:if>

<c:forEach var="mutter" items="${mutterList}">
	<p><c:out value="${mutter.userName}" />:
		<c:out value="${mutter.text}" />
	
	<form action="Main" method="post">
		<input type="hidden" name="mutterId" value="${mutter.id}"/>
		<input type="hidden" name="name" value="${mutter.userName}"/>
		<input type="submit" value="削除">
	
	</form>	
		
	</p>
</c:forEach>

</body>
</html>