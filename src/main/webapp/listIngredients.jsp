<-- List of Ingredients -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<title>List of Ingredients</title>
</head>
<body>
	<h1>All Ingredients</h1>
	<ul>
		<c:forEach var="ingredient" items="${allIngredients}">
			<li><a href="viewIngredient.jsp?id=${ingredient.id}">${ingredient.name}</a></li>
		</c:forEach>
	</ul>
	<a href="index.html">Return to Main Menu</a>
</body>
</html>
