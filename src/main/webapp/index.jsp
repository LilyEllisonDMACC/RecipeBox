<!DOCTYPE html>
<html>
<head>
<title>Recipe Box</title>
</head>
<body>
	<h1>Welcome to Recipe Box</h1>
	<h2>What would you like to do?</h2>
	<ul>
		<li><a href="manageRecipes.jsp">Manage Recipes</a></li>
		<li><a href="manageIngredients.jsp">Manage Ingredients</a></li>
		<li><a href="manageCategories.jsp">Manage Categories</a></li>
	</ul>
	<h2>Search Recipes</h2>
	<form action="searchServlet" method="post">
		<input type="text" name="query" placeholder="Search..."> <input
			type="submit" value="Search">
	</form>
</body>
</html>
