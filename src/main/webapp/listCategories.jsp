<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<title>List of Categories</title>
</head>
<body>
	<h1>All Categories</h1>
	<ul>
		<c:forEach var="category" items="${categories}">
			<li>${category.name}</li>
		</c:forEach>
	</ul>
	<a href="index.html">Return to Main Menu</a>
</body>
</html>
