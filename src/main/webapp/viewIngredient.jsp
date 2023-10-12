<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
