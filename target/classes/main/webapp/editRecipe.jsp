<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Edit Recipe</title>
<style>
/* General styling */
body {
	font-family: Arial, sans-serif;
	margin: 20px;
	color: black;
	background-color: white;
}

h1 {
	text-align: center;
}

form {
	text-align: center;
}

label, input, select {
	margin-bottom: 10px;
}

/* Centered button */
.centered-button {
	text-align: center;
	margin-top: 20px;
}

input[type="submit"] {
	padding: 10px 20px;
	border: 1px solid black;
	border-radius: 5px;
	cursor: pointer;
}

input[type="submit"]:hover {
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
</script>
</head>
<body>

	<h1>Edit Recipe</h1>
	<form action="editRecipeServlet" method="post">

		<input type="hidden" name="id" value="${recipeToEdit.id}"> <label
			for="name">Recipe Name:</label> <input type="text" id="name"
			name="name" required value="${recipeToEdit.name}"> <br>

		<label for="servings">Number of Servings:</label> <input type="number"
			id="servings" min="1" name="servings" required
			value="${recipeToEdit.servings}"> <br> <label
			for="preparationTime">Preparation Time (in minutes):</label> <input
			type="number" id="preparationTime" min="1" name="preparationTime"
			required value="${recipeToEdit.preparationTime}"> <br> <label
			for="categoryDropdown">Category:</label> <select
			id="categoryDropdown" name="category" required
			onchange="showNewCategoryInput();">
			<option value="" selected disabled>Select Category</option>
			<c:forEach var="category" items="${allCategories}">
				<option value="${category.id}">${category.name}</option>
			</c:forEach>
			<option value="New">-- Add New --</option>
		</select> <input type="text" id="newCategoryInput" name="newCategory"
			aria-label="New Category" style="display: none;"
			placeholder="Enter new category"> <br> <label
			for="ingredientItems">Ingredients, separated by commas:</label> <input
			type="text" id="ingredientItems" name="ingredients"
			value="${recipeToEdit.listIngredients()}"> <br> <label
			for="instructionItem">Instructions, separated by commas:</label> <input
			type="text" id="instructionItem" name="instructions"
			value="${recipeToEdit.instructions}"> <br>

		<div class="centered-button">
			<input type="submit" value="Save">
		</div>
	</form>

</body>
</html>
