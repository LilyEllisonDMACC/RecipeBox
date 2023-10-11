<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>List of Ingredients</title>
</head>
<body>

<form method = "post" action = "ingredientServlet">
	<c:forEach items="${requestScope.allIngredients}" var="currentIngredient">
		<tr>
			<td><input type="radio" name="id" value="${currentIngredient.id}"></td>
			<td>${currentIngredient.name}</td>
		</tr>	
	</c:forEach>
	<input type="submit" value="Edit" name="doThisToIngredient">
	<input type="submit" value="Delete" name="doThisToIngredient">
	<input type="submit" value="View" name="doThisToIngredient">
</form>

	<a href="index.html">Return to main menu</a>

</body>
</html>