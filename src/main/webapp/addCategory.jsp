<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Add A Category</title>
<style>
/* General styling */
body {
	font-family: Arial, sans-serif;
	margin: 20px;
}

h1 {
	text-align: center;
}

/* Form styling */
form {
	text-align: center;
	margin-top: 20px;
}

/* Centered button */
.centered-button {
	text-align: center;
	margin-top: 20px;
}

.submit-button-wrapper {
	text-align: center;
	margin-top: 20px;
}

/* Centered links */
.centered-links {
	text-align: center;
	margin-top: 20px;
}

.centered-links a {
	margin: 10px;
	text-decoration: none;
	color: black;
	background-color: white;
	padding: 10px 20px;
	border: 1px solid black;
	border-radius: 5px;
}

.centered-links a:hover {
	background-color: grey;
	color: white;
}
</style>
</head>
<body>

	<h1>Add A Category</h1>

	<form action="AddCategoriesServlet" method="post">
		<label for="name">Category Name:</label> <input type="text" id="name"
			name="name" required>
		<div class="submit-button-wrapper">
			<input type="submit" value="Submit">
		</div>
	</form>

	<div class="centered-links">
		<a href="index.jsp">Return to Main Menu</a> <a
			href="/RecipeBox/viewAllCategoriesServlet">View All Categories</a>
	</div>

</body>
</html>
