<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Search Results</title>
</head>
<body>
<h1>Search Results</h1>

<h2>Results for ${searchTerm}:</h2>

<h3>Recipes:</h3>

	<form method = "post" action = "recipeServlet">
		<table>
			<c:forEach items="${requestScope.allRecipes}" var="currentRecipe">
				<tr>
					<td><input type="radio" name="id" value="${currentRecipe.id}"></td>
					<td><h2>${currentRecipe.name}</h2></td>
				</tr>
				<tr>
					<td colspan="2">Category: ${currentRecipe.category.name}</td>
				</tr>
				<c:forEach var = "ingredientList" items = "${currentRecipe.listOfIngredients}">
					<tr>
						<td></td><td colspan="2">${ingredientList.name}</td>
					</tr>
				</c:forEach>
			</c:forEach>
		</table>
		<br />
		<input type = "submit" value = "Edit Recipe" name="doThisToRecipe">
		<input type = "submit" value = "Delete Recipe" name="doThisToRecipe"> 
		<input type = "submit" value = "View Full Recipe" name="doThisToRecipe">
	</form>
	
	<h3>Ingredients:</h3>
	
	<form method = "post" action = "ingredientServlet">
		<c:forEach items="${requestScope.allIngredients}" var="currentIngredient">
			<tr>
				<td><input type="radio" name="id" value="${currentIngredient.id}"></td>
				<td>${currentIngredient.name}</td>
			</tr>	
		</c:forEach>
		<input type="submit" value="Edit" name="doThisToIngredient">
		<input type="submit" value="Delete" name="doThisToIngredient">
		<input type="submit" value="View" name="doThisToIngredient">
	</form>

	<a href="index.html">Return to main menu</a>

</body>
</html>