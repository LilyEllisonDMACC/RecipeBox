<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>View Recipe</title>
</head>
<body>
	<h1>${recipe.name}</h1>
	<p>Servings: ${recipe.servings}</p>
	<p>Preparation Time: ${recipe.preparationTime} minutes</p>
	<p>Category: ${recipe.category.name}</p>
	<p>Ingredients:</p>
	<ul>
		<c:forEach var="ingredient" items="${recipe.ingredients}">
			<li>${ingredient.name}</li>
		</c:forEach>
	</ul>
	<p>Instructions:</p>
	<pre>${recipe.instructions}</pre>
</body>
</html>
