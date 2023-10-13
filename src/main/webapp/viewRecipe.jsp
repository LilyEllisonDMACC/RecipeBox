<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="model.Recipe" %>

<!DOCTYPE html>
<html>
<head>
<title>View Recipe</title>
<%Recipe recipe = (Recipe)request.getAttribute("recipeToView"); %>
</head>
<body>
	<h1><%=recipe.getName()%></h1>
	<%=recipe.toString()%>
	<!-- 
	<p>Servings: </p>
	<p>Preparation Time:  minutes</p>
	<p>Category: </p>
	<p>Ingredients:</p>
	<ul>
	 -->
<a href="index.html">Return to Main Menu</a>
<a href="viewAllRecipesServlet">View Recipe List</a>
</body>
</html>
