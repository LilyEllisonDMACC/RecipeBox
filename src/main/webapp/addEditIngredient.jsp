<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Add or Edit an Ingredient</title>
</head>
<body>
<!-- form to enter ingredient info. If the ingredient is one being edited, the name and id can be added. -->
	<form action="ingredientServlet" method="post">
		ID: ${currentIngredient.id} <br />
		Ingredient: <input type = "text" name = "name" value = "${currentIngredient.name}">
		
	<input type = "submit" value = "Submit">
	</form>
	<!-- Link to homepage -->
<a href = "index.html">Cancel and Return to Homepage</a>

</body>
</html>