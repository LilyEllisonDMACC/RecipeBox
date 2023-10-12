<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Search Results</title>
</head>
<body>
	<h1>Search Results</h1>
	<ul>
		<c:forEach var="recipe" items="${searchResults}">
			<li><a href="viewRecipe.jsp?id=${recipe.id}">${recipe.name}</a></li>
		</c:forEach>
	</ul>
</body>
</html>
