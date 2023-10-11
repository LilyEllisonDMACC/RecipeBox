<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>View Recipe</title>
</head>
<body>

<h1>${currentRecipe.name}</h1>

<h2>Ingredients</h2>

<c:forEach items="${requestScope.Recipe.allIngredients}" var="currentIngredient">
	<ol>
		<li>${currentIngredient}</li>
	</ol>
</c:forEach>

</body>
</html>