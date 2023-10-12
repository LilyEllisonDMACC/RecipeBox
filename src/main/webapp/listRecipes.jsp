<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>List of Recipes</title>
</head>
<body>
	<h1>All Recipes</h1>
	<ul>
		<c:forEach var="recipe" items="${allRecipes}">
			<li><a href="viewRecipe.jsp?id=${recipe.id}">${recipe.name}</a></li>
		</c:forEach>
	</ul>
</body>
</html>
