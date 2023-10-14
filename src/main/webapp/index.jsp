<-- Main page for the Recipe Box application. -->
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Recipe Box</title>
<style>
body {
	font-family: Arial, sans-serif;
	margin: 20px;
	color: black;
	background-color: white;
}

h1 {
	text-align: center;
}

p {
	text-align: center;
	font-size: 18px;
}

ul {
	list-style: none;
	padding: 0;
	text-align: center;
}

li {
	margin: 40px 10px;
}

a {
	text-decoration: none;
	color: black;
	background-color: white;
	padding: 10px 20px;
	border: 1px solid black;
	border-radius: 5px;
}

a:hover {
	background-color: grey;
	color: white;
}
</style>
</head>
<body>
	<h1>Welcome to Our Recipe Box!</h1>
	<p>What would you like to do?</p>
	<ul>
		<li><a href="addCategoriesToRecipeServlet">Add a Recipe</a></li>
		<li><a href="viewAllRecipesServlet">View Recipe List</a></li>
		<li><a href="viewAllCategoriesServlet">Manage Categories</a></li>
	</ul>
</body>
</html>
