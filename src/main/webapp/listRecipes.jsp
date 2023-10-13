<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<title>List of Recipes</title>
</head>
<body>
	<h1>All Recipes</h1>
	<ul>
		<c:forEach var="recipe" items="${allRecipes}">
			<li><a href="singleRecipeServlet?id=${recipe.id}">${recipe.name}</a></li>
		</c:forEach>
	</ul>
	<a href="index.html">Return to Main Menu</a>
</body>
</html>
