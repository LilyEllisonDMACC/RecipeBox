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

<p>Category: ${currentRecipe.category.name} <br />
Preparation Time: ${currentRecipe.preparationTime} minutes<br />
Servings: ${currentRecipe.servings}</p>

<h2>Ingredients</h2>

<c:forEach items="${currentRecipe.allIngredients}" var="currentIngredient">
	<ol>
		<li>${currentIngredient}</li>
	</ol>
</c:forEach>

<h2>Instructions</h2>

<p>${currentRecipe.instructions}</p>

<a href="index.html">Return to main menu</a>

<footer>
	<div>
		Date Added: ${currentRecipe.dateAdded}
	</div>
	<div>
		Last Modified: ${currentRecipe.lastModified}
	</div>
</footer>

</body>
</html>