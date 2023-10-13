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
	<!-- <ul>  -->
	<form method = "post" action = "navigationServlet">
		<table>
			<c:forEach var="recipe" items="${allRecipes}">
				<!-- <li><a href="singleRecipeServlet?id=${recipe.id}">${recipe.name}</a></li>  -->
				<tr>
					<td><input type="radio" name="id" value="${recipe.id}"></td>
					<td><h2>${recipe.name}</h2></td>
				</tr>
				<tr>
					<td colspan="2">Category: ${recipe.category.name}</td>
				</tr>
			</c:forEach>
		</table>
		<br />
		<input type = "submit" value = "View" name = "doThisToRecipe">
		<input type = "submit" value = "Edit" name = "doThisToRecipe">
		<input type = "submit" value = "Delete" name = "doThisToRecipe">
	</form>
	<!-- </ul>  -->
	<a href="index.html">Return to Main Menu</a>
	<a href="addCategoriesToRecipeServlet">Add a Recipe</a>
</body>
</html>
