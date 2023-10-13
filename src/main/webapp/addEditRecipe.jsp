<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Add A Recipe</title>
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

	function addIngredientWithQuantity() {
		const newIngredient = document.getElementById("ingredientItem").value;
		const newAmt = document.getElementById("ingredientAmt").value;

		if (!newIngredient || !newAmt) {
			alert("Both ingredient and quantity are required.");
			return;
		}

		ingredientListItems.push(newIngredient);
		ingredientListAmounts.push(newAmt);

		const newIngredientListItemAmt = ("<li><span name = \"ingredientAmount\">" + newAmt + "</span><span name = \"ingredientItem\"> " + newIngredient + "</span></li>");
		document.getElementById("ingredientList").innerHTML += newIngredientListItemAmt;
		
		//const newIngredientListItem = "<li><span name='amount'>${newAmt}</span><span name='ingredient'> ${newIngredient}</span></li>";
		//document.getElementById("ingredientList").innerHTML += newIngredientListItem;

		const hiddenAmtField = document.createElement("input");
		hiddenAmtField.type = "hidden";
		hiddenAmtField.name = "ingredientAmount";
		hiddenAmtField.value = "${newAmt}";
		document.forms[0].appendChild(hiddenField);
		
		const hiddenIngField = document.createElement("input");
		hiddenIngField.type = "hidden";
		hiddenIngField.name = "ingredientAmount";
		hiddenIngField.value = "${newIngredient}";
		document.forms[0].appendChild(hiddenField);
		
		

		document.getElementById("ingredientItem").value = "";
		document.getElementById("ingredientAmt").value = "";
		

	}

	function addInstruction() {
		const newInstruction = document.getElementById("instructionItem").value;

		if (!newInstruction) {
			alert("Instruction is required.");
			return;
		}

		instructionsList.push(newInstruction);

		newInstructionListItem = ("<li name = \"instruction\">" + newInstruction + "</li>");
		document.getElementById("instructionList").innerHTML += newInstructionListItem;
		
		//const newInstructionListItem = `<li name='instruction'>${newInstruction}</li>`;
		//document.getElementById("instructionList").innerHTML += newInstructionListItem;

		const hiddenField = document.createElement("input");
		hiddenField.type = "hidden";
		hiddenField.name = "instructions";
		hiddenField.value = newInstruction;
		document.forms[0].appendChild(hiddenField);

		document.getElementById("instructionItem").value = "";
	}
</script>
</head>
<body>
	<form action="recipeServlet" method="post">
		<input type="hidden" name="action" value="add"> 
		
		<label for="name">Recipe Name:</label> 
		<input type="text" id="name" name="name" required> <br> 
		
		<label for="servings">Number of Servings:</label> 
		<input type="number" id="servings" min="1" name="servings" required> <br> 
		
		<label for="preparationTime">Preparation Time (in minutes):</label> 
		<input type="number" id="preparationTime" min="1" name="preparationTime" required> <br> 
		
		<label for="categoryDropdown">Category:</label>
		<select id="categoryDropdown" name="category" required onchange="showNewCategoryInput();">
			<option value="" selected disabled>Select Category</option>
			<c:forEach var="category" items="${categories}">
				<option value="${category.id}">${category.name}</option>
			</c:forEach>
			<option value="New">-- Add New --</option>
		</select> 
		<input type="text" id="newCategoryInput" name="newCategory" aria-label="New Category" style="display: none;" placeholder="Enter new category"> <br> 
		
		<label for="ingredientItem">Ingredients, separated by commas:</label> 
		<input type="text" id="ingredientItems" name="ingredients"> <br>
		
		<!-- 
		<label for="ingredientAmt">Quantity:</label>
		<input type="text" id="ingredientAmt"> 
		<input type="button" value="Add Ingredient" onclick="addIngredientWithQuantity();">	<br>
		<ol id="ingredientList"></ol>
		 -->
		<label for="instructionItem">Instructions, separated by commas:</label> 
		<input type="text" id="instructionItem" name="instructions"> <br>
		<!-- 
		<input type="button" value="Add Instruction" onclick="addInstruction();"> <br>
		<ol id="instructionList"></ol>
		 -->
		<input type="submit" value="Submit">
	</form>
	<a href="index.html">Return to Main Menu</a>
</body>
</html>
