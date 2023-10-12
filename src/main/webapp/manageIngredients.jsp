<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>List of Ingredients</title>
</head>
<body>
	<h1>All Ingredients</h1>
	<ul>
		<c:forEach var="ingredient" items="${allIngredients}">
			<li><a href="viewIngredient.jsp?id=${ingredient.id}">${ingredient.name}</a></li>
		</c:forEach>
	</ul>
</body>
</html>
