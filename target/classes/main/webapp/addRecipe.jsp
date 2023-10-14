<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Add A Recipe</title>
<style>
body {
	font-family: Arial, sans-serif;
	margin: 20px;
	color: black;
	background-color: white;
}

form {
	border: 1px solid black;
	padding: 20px;
	border-radius: 10px;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
	max-width: 500px;
	margin: auto;
}

label {
	font-weight: bold;
	margin-right: 10px;
}

input[type="text"], input[type="number"], select {
	width: 100%;
	padding: 10px;
	margin: 10px 0;
	border: 1px solid black;
	border-radius: 5px;
}

.submit-button-wrapper {
	text-align: center;
	margin-top: 20px;
}

input[type="submit"] {
	font-size: 18px;
	padding: 10px 20px;
}

h1 {
	text-align: center;
}

.center-text {
	text-align: center;
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
<script>
	function showNewCategoryInput() {
		const dropdown = document.getElementById("categoryDropdown");
		const newCategoryInput = document.getElementById("newCategoryInput");
		if (dropdown.value === "New") {
			newCategoryInput.style.display = "block";
		} else {
			newCategoryInput.style.display = "none";
		}
	}

	let ingredientListItems = [];
	let ingredientListAmounts = [];
	let instructionsList = [];
</script>
</head>
<body>
	<h1>Add a Recipe</h1>
	<form action="createRecipeServlet" method="post">
		<input type="hidden" name="action" value="add"> <label
			for="name">Recipe Name:</label> <input type="text" id="name"
			name="name" required> <br> <label for="servings">Number
			of Servings:</label> <input type="number" id="servings" min="1"
			name="servings" required> <br> <label
			for="preparationTime">Preparation Time (in minutes):</label> <input
			type="number" id="preparationTime" min="1" name="preparationTime"
			required> <br> <label for="categoryDropdown">Category:</label>
		<select id="categoryDropdown" name="category" required
			onchange="showNewCategoryInput();">
			<option value="" selected disabled>Select Category</option>
			<c:forEach var="category" items="${allCategories}">
				<option value="${category.id}">${category.name}</option>
			</c:forEach>
			<option value="New">-- Add New --</option>
		</select> <input type="text" id="newCategoryInput" name="newCategory"
			aria-label="New Category" style="display: none;"
			placeholder="Enter new category"> <br> <label
			for="ingredientItem">Ingredients, separated by commas:</label> <input
			type="text" id="ingredientItems" name="ingredients"> <br>
		<label for="instructionItem">Instructions, separated by
			commas:</label> <input type="text" id="instructionItem" name="instructions">
		<br>
		<div class="submit-button-wrapper">
			<input type="submit" value="Submit">
		</div>
	</form>
	<br>
	<div class="centered-links">
		<a href="index.jsp">Return to Main Menu</a>
		<a href="/RecipeBox/viewAllRecipesServlet">View All Recipes</a>
	</div>
</body>
</html>
