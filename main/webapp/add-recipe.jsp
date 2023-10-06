<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Add A Recipe</title>
</head>
<body>
<form action = "addRecipeServlet" method = "post">
	Recipe Name: <input type = "text" name = "name"> <br />
	Number of Servings: <input type = "number" name = "servings"> <br />
	Preparation Time (in minutes): <input type = "number" name = "preparationTime"> <br />
	Category: <input type = "text" name = "category"> <br />
	Add Ingredients and Instructions: <input type ="submit" value= "Next">	
</form>

<a href = "index.html">Return to Homepage</a>

</body>
</html>