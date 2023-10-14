<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<title>View Ingredient</title>
</head>
<body>
	<h1>${ingredient.name}</h1>
	<h2>Used in the following recipes:</h2>
	<ul>
		<c:forEach var="recipe" items="${recipesUsingIngredient}">
			<li>${recipe.name}</li>
		</c:forEach>
	</ul>
</body>
</html>
