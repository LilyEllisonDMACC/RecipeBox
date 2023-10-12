<%--suppress HtmlUnknownTarget --%>
<%--suppress ELValidationInspection --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Manage Recipes</title>
</head>
<body>
	<h1>Manage Recipes</h1>
	<table border="1">
		<tr>
			<th>Recipe Name</th>
			<th>Actions</th>
		</tr>
		<c:forEach var="recipe" items="${recipes}">
			<tr>
				<td><a href="RecipeServlet?action=view&id=${recipe.id}">${recipe.name}</a></td>
				<td><a href="RecipeServlet?action=edit&id=${recipe.id}">Edit</a>
					| <a href="RecipeServlet?action=delete&id=${recipe.id}">Delete</a>
				</td>
			</tr>
		</c:forEach>
	</table>
	<a href="addRecipe.jsp">Add New Recipe</a>
</body>
</html>
