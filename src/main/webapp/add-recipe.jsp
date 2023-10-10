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
			
			newIngredientListItem = ("<li><span name = \"ingredient\">" + newAmt + "</span><span name = \"amount\"> " + newIngredient + "</span></li>");
			document.getElementById("ingredientList").innerHTML += newIngredientListItem;
			
			document.getElementById("ingredientItem").value = "";
			document.getElementById("ingredientAmt").value = "";
			
			return;
		}
		
		function addInstruction() {
			var newInstruction = document.getElementById("instructionItem").value;
			
			var instructionsList = [];
			
			instructionsList.push(newInstruction);
			
			newInstructionListItem = ("<li name = \"instruction\">" + newInstruction + "</li>");
			document.getElementById("instructionList").innerHTML += newInstructionListItem;
			
			document.getElementById("instructionItem").value = "";
			
			return;
		}
	</script>

<form action = "addRecipeServlet" method = "post">
	Recipe Name: <input type = "text" name = "name"> <br />
	Number of Servings: <input type = "number" min = "1" name = "servings"> <br />
	Preparation Time (in minutes): <input type = "number" min = "1" name = "preparationTime"> <br />
	Category: <input type = "text" name = "category"> <br />
	Ingredients: <input type = "text" name = "ingredient" id = "ingredientItem"> Quantity: <input type = "text" name = "quantity" id = "ingredientAmt"> 
	<input type = "button" value = "Add Ingredient" onclick="addIngredientWithQuantity();"> <br />
	<ol id = "ingredientList" >	
	</ol>
	
	Instructions: <input type ="text" name = "instruction" id = "instructionItem"> <input type = "button" value = "Add Instruction" onclick="addInstruction();"> <br />
	<ol id = "instructionList">
	</ol>
	<input type = "submit" value = "Submit">
		
</form>

<a href = "options.html">Cancel and Return to Homepage</a>

</body>
</html>