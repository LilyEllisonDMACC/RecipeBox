<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>View Recipe: ${recipe.name}</title>
</head>
<body>
	<h1>${recipe.name}</h1>
	<h2>Date Added: ${recipe.dateAdded}</h2>
	<h2>Last Modified: ${recipe.lastModified}</h2>
	<p>
		<strong>Servings:</strong> ${recipe.servings}
	</p>
	<p>
		<strong>Preparation Time:</strong> ${recipe.preparationTime} minutes
	</p>
	<p>
		<strong>Category:</strong> ${recipe.category.name}
	</p>

	<h2>Ingredients:</h2>
	<ul>
		<c:forEach var="ingredient" items="${recipe.ingredients}">
			<li>${ingredient.name}</li>
		</c:forEach>
	</ul>

	<h2>Instructions:</h2>
	<pre>${recipe.instructions}</pre>

	<a href="manageRecipes.jsp">Back to Manage Recipes</a>
</body>
</html>
