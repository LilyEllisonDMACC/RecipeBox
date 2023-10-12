<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="recipeResults" type="java.util.List" scope="request"/>
<jsp:useBean id="ingredientResults" type="java.util.List" scope="request"/>
<jsp:useBean id="categoryResults" type="java.util.List" scope="request"/>
<!DOCTYPE html>
<html>
<head>
    <title>Search Results</title>
</head>
<body>
<h1>Search Results</h1>

<h2>Recipes</h2>
<ul>
    <c:forEach var="recipe" items="${recipeResults}">
        <li><a href="viewRecipe.jsp?id=${recipe.id}">${recipe.name}</a></li>
    </c:forEach>
</ul>

<h2>Ingredients</h2>
<ul>
    <c:forEach var="ingredient" items="${ingredientResults}">
        <li><a href="viewIngredient.jsp?id=${ingredient.id}">${ingredient.name}</a></li>
    </c:forEach>
</ul>

<h2>Categories</h2>
<ul>
    <c:forEach var="category" items="${categoryResults}">
        <li><a href="viewCategory.jsp?id=${category.id}">${category.name}</a></li>
    </c:forEach>
</ul>
</body>
</html>
