<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<title>List of Recipes</title>
<style>
/* General styling */
body {
	font-family: Arial, sans-serif;
	margin: 20px;
	color: black;
	background-color: white;
}

h1 {
	text-align: center;
}

/* Recipe card styling */
.recipe-card {
	border: 1px solid #ccc;
	border-radius: 10px;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
	max-width: 300px;
	margin: 20px;
	padding: 20px;
	text-align: center;
	display: inline-block;
	cursor: pointer;
}

.recipe-card:hover {
	box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
}

.recipe-card h2 {
	font-size: 24px;
	margin-bottom: 10px;
}

.recipe-card button {
	cursor: pointer;
	margin-top: 10px;
}

/* Centered links */
.centered-links {
	text-align: center;
	margin-top: 20px;
}

.centered-links a {
	margin: 10px;
	text-decoration: none;
	color: black;
	background-color: white;
	padding: 10px 20px;
	border: 1px solid black;
	border-radius: 5px;
}

.centered-links a:hover {
	background-color: grey;
	color: white;
}
</style>
</head>
<body>
	<h1>All Recipes</h1>
	<form method="post" action="navigationServlet">
		<c:forEach var="recipe" items="${allRecipes}">
			<div class="recipe-card"
				onclick="window.location.href='singleRecipeServlet?id=${recipe.id}'">
				<h2>${recipe.name}</h2>
				<p>Category: ${recipe.category.name}</p>
				<p>Servings: ${recipe.servings}</p>
				<p>Preparation Time: ${recipe.preparationTime} minutes</p>
				<button type="button"
					onclick="event.stopPropagation(); window.location.href='editRecipeServlet?action=edit&id=${recipe.id}'">Edit</button>
				<button type="button"
					onclick="event.stopPropagation(); window.location.href='editRecipeServlet?action=delete&id=${recipe.id}'">Delete</button>
			</div>
		</c:forEach>
	</form>
	<div class="centered-links">
		<a href="index.jsp">Return to Main Menu</a> <a
			href="addCategoriesToRecipeServlet">Add a Recipe</a>
	</div>
</body>
</html>
