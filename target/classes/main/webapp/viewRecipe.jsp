<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>Recipe Details</title>
<style>
/* General styling */
body {
	font-family: Arial, sans-serif;
	margin: 20px;
}
/* Recipe card styling */
.recipe-card {
	border: 1px solid #ccc;
	border-radius: 10px;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
	max-width: 400px;
	margin: auto;
	padding: 20px;
	text-align: left;
}

.recipe-card h1 {
	font-size: 24px;
}

.recipe-card h2 {
	font-size: 18px;
	margin-top: 20px;
}

.recipe-card table {
	width: 100%;
}

.recipe-card td {
	padding: 8px;
}

.recipe-card ul {
	list-style-type: disc;
	margin-left: 20px;
}

.center {
	border: 5px solid;
	margin: auto;
	width: 50%;
	padding: 10px;
}
</style>
</head>
<body>
	<div class="recipe-card">
		<h1>${recipeToView.name}</h1>
		<hr>
		<table>
			<tr>
				<td><strong>Category:</strong></td>
				<td>${recipeToView.category.name}</td>
			</tr>
			<tr>
				<td><strong>Servings:</strong></td>
				<td>${recipeToView.servings}</td>
			</tr>
			<tr>
				<td><strong>Preparation Time:</strong></td>
				<td>${recipeToView.preparationTime}minutes</td>
			</tr>
			<tr>
				<td><strong>Date Added:</strong></td>
				<td><fmt:formatDate value="${recipeToView.dateAdded}"
						pattern="MM/dd/yyyy" /></td>
			</tr>
			<tr>
				<td><strong>Last Modified:</strong></td>
				<td><fmt:formatDate value="${recipeToView.lastModified}"
						pattern="MM/dd/yyyy" /></td>
			</tr>
		</table>
		<hr>
		<h2>Ingredients:</h2>
		<ul>
			<c:forEach var="ingredient" items="${recipeToView.ingredients}">
				<li>${ingredient.name}</li>
			</c:forEach>
		</ul>
		<hr>
		<h2>Instructions:</h2>
		<p>${fn:replace(recipeToView.instructions, newline, "<br/>")}</p>
	</div>
	<br>
	<div style="text-align: center;">
		<a href="index.jsp">Return to Main Menu</a> <a
			href="editRecipeServlet?id=${recipeToView.id}">Edit Recipe</a> <a
			href="viewAllRecipesServlet">View All Recipes</a>
	</div>
</body>
</html>
