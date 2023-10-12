<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Recipe Box</title>
</head>
<body>
	<h1>Welcome to the Recipe Box!</h1>

	<!-- Search Form -->
	<form action="searchServlet" method="post">
		<label for="searchQuery">Search:</label> <input type="text"
			id="searchQuery" name="searchQuery" required> <label
			for="searchType">Search By:</label> <select id="searchType"
			name="searchType">
			<option value="name">Name</option>
			<option value="category">Category</option>
			<option value="ingredient">Ingredient</option>
			<option value="servingSize">Serving Size</option>
		</select> <input type="submit" value="Search">
	</form>

	<h2>What would you like to do?</h2>
	<ul>
		<li><a href="addEditRecipe.jsp">Add or Edit Recipe</a></li>
		<li><a href="listRecipes.jsp">View Recipe List</a></li>
		<li><a href="listIngredients.jsp">Manage Ingredients</a></li>
		<li><a href="listCategories.jsp">Manage Categories</a></li>
	</ul>

</body>
</html>
