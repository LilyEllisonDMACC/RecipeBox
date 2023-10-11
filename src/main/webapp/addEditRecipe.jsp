<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Add A Recipe</title>
</head>
<body>

	<script>
		function addIngredientWithQuantity() {
			var newIngredient = document.getElementById("ingredientItem").value;
			var newAmt = document.getElementById("ingredientAmt").value;
			
			var ingredientListItems = [];
			var ingredientListAmts = [];
			
			ingredientListItems.push(newIngredient);
			ingredientListAmts.push(newAmt);
			
			newIngredientListItemAmt = ("<li><span name = \"ingredientItem\">" + newAmt + "</span><span name = \"ingredientAmount\"> " + newIngredient + "</span></li>");
			document.getElementById("ingredientList").innerHTML += newIngredientListItemAmt;
			
			document.getElementById("ingredientItem").value = "";
			document.getElementById("ingredientAmt").value = "";
			
			return;
		}
		
		function addInstruction() {
			var newInstruction = document.getElementById("instruction").value;
			
			var instructionsList = [];
			
			instructionsList.push(newInstruction);
			
			newInstructionListItem = ("<li name = \"instruction\">" + newInstruction + "</li>");
			document.getElementById("instructionList").innerHTML += newInstructionListItem;
			
			document.getElementById("instructionItem").value = "";
			
			return;
		}
	</script>

<form action = "recipeServlet" method = "post">
	Recipe Name: <input type = "text" name = "name"> <br />
	Number of Servings: <input type = "number" min = "1" name = "servings"> <br />
	Preparation Time (in minutes): <input type = "number" min = "1" name = "preparationTime"> <br />
	Category: <input type = "text" name = "category"> <br />
	Ingredients: <input type = "text" name = "ingredientItem" id = "ingredientItem"> Quantity: <input type = "text" name = "ingredientAmount" id = "ingredientAmt"> 
	<input type = "button" value = "Add Ingredient" onclick="addIngredientWithQuantity();"> <br />
	<ol id = "ingredientList" >	
	</ol>
	
	Instructions: <input type ="text" name = "instruction" id = "instruction"> <input type = "button" value = "Add Instruction" onclick="addInstruction();"> <br />
	<ol id = "instructionList">
	</ol>
	<input type = "submit" value = "Submit">
		
</form>

<a href = "index.html">Cancel and Return to Homepage</a>

</body>
</html>