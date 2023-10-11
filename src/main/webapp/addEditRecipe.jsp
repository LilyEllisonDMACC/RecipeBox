<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Add or Edit A Recipe</title>
</head>
<body>

	<script>
	
	//Adds amount and indregredients to list of ingredients on page
		function addIngredientWithQuantity() {
			//gets info entered by user
			var newIngredient = document.getElementById("ingredientItem").value;
			var newAmt = document.getElementById("ingredientAmt").value;
			
			//creates arrays to store info
			var ingredientListItems = [];
			var ingredientListAmts = [];
			
			//adds info to the arrays
			ingredientListItems.push(newIngredient);
			ingredientListAmts.push(newAmt);
			
			//adds amount and ingredient to the page display 
			newIngredientListItemAmt = ("<li><span name = \"ingredientAmount\">" + newAmt + "</span><span name = \"ingredientItem\"> " + newIngredient + "</span></li>");
			document.getElementById("ingredientList").innerHTML += newIngredientListItemAmt;
			
			//clears the input fields
			document.getElementById("ingredientItem").value = "";
			document.getElementById("ingredientAmt").value = "";
			
			return;
		}
		
	//Adds instructions on the page
		function addInstruction() {
			//gets info entered by user
			var newInstruction = document.getElementById("instruction").value;
			
			//creates an array to store instructions
			var instructionsList = [];
			
			//adds info to array
			instructionsList.push(newInstruction);
			
			//lists instructions on page
			newInstructionListItem = ("<li name = \"instruction\">" + newInstruction + "</li>");
			document.getElementById("instructionList").innerHTML += newInstructionListItem;
			
			//clears input field
			document.getElementById("instructionItem").value = "";
			
			return;
		}
	</script>

<!-- form to enter recipe info. If the recipe is one being edited, the name, servings, time, and category can easily be added. Ingredients and instructions might be tricky. -->
<form action = "recipeServlet" method = "post">
	Recipe Name: <input type = "text" name = "name"value = "${recipeToEdit.name}"> <br />
	
	Number of Servings: <input type = "number" min = "1" name = "servings" value = "${recipeToEdit.servings}"> <br />
	Preparation Time (in minutes): <input type = "number" min = "1" name = "preparationTime" value = "${recipeToEdit.preparationTime}"> <br />
	Category: <input type = "text" name = "category" value = "${recipeToEdit.category}"> <br />
	
	Ingredients: <input type = "text" name = "ingredientItem" id = "ingredientItem"> Quantity: <input type = "text" name = "ingredientAmount" id = "ingredientAmt"> 
	<input type = "button" value = "Add Ingredient" onclick="addIngredientWithQuantity();"> <br />
	<ol id = "ingredientList" >	
	</ol>
	
	Instructions: <input type ="text" name = "instruction" id = "instruction"> <input type = "button" value = "Add Instruction" onclick="addInstruction();"> <br />
	<ol id = "instructionList">
	</ol>
	<input type = "hidden" name = "id"  value = "${recipeToEdit.id}">
	<input type = "submit" value = "Submit">
		
</form>

<!-- Link to homepage -->
<a href = "index.html">Cancel and Return to Homepage</a>

</body>
</html>