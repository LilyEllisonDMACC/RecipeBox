<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Edit Recipe</title>
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

	<form action = "editRecipeServlet" method = "post"></form>		
		<label for="name">Recipe Name:</label> 
		<input type="text" id="name" name="name" required value = "${recipeToEdit.name}"> <br> 
		
		<label for="servings">Number of Servings:</label> 
		<input type="number" id="servings" min="1" name="servings" required value = "${recipeToEdit.servings}"> <br> 
		
		<label for="preparationTime">Preparation Time (in minutes):</label> 
		<input type="number" id="preparationTime" min="1" name="preparationTime" required value = "${recipeToEdit.preparationTime}"> <br> 
		
		<label for="categoryDropdown">Category:</label>
		<select id="categoryDropdown" name="category" required onchange="showNewCategoryInput();">
			<option value="" selected disabled>Select Category</option>
			<c:forEach var="category" items="${allCategories}">
				<option value="${category.id}">${category.name}</option>
			</c:forEach>
			<option value="New">-- Add New --</option>
		</select> 
		<input type="text" id="newCategoryInput" name="newCategory" aria-label="New Category" style="display: none;" placeholder="Enter new category"> <br> 
		
		<label for="ingredientItem">Ingredients, separated by commas:</label> 
		<input type="text" id="ingredientItems" name="ingredients" value = "${recipeToEdit.listIngredients()}"> <br>
		

		<label for="instructionItem">Instructions, separated by commas:</label> 
		<input type="text" id="instructionItem" name="instructions" value = "${recipeToEdit.instructions}"> <br>

		<input type="submit" value="Submit">

</body>
</html>