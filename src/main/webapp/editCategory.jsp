<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<title>Edit Category</title>
<style>
body {
	font-family: Arial, sans-serif;
	margin: 20px;
	color: black;
	background-color: white;
}

form {
	border: 1px solid black;
	padding: 20px;
	border-radius: 10px;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
	max-width: 500px;
	margin: auto;
}

label {
	font-weight: bold;
	margin-right: 10px;
}

input[type="text"] {
	width: 100%;
	padding: 10px;
	margin: 10px 0;
	border: 1px solid black;
	border-radius: 5px;
}

input[type="submit"] {
	background-color: #f2f2f2;
	color: black;
	padding: 10px 20px;
	border: 1px solid black;
	border-radius: 5px;
	cursor: pointer;
	display: block;
	margin: auto;
}

h1 {
	text-align: center;
}

.center-text {
	text-align: center;
}

a {
	text-decoration: none;
	color: black;
	background-color: white;
	padding: 10px 20px;
	border: 1px solid black;
	border-radius: 5px;
}

a:hover {
	background-color: grey;
	color: white;
}
</style>
</head>
<body>
	<h1>Edit Category</h1>
	<form action="editCategoryServlet" method="post">
		<input type="hidden" name="id" value="${categoryToEdit.id}"> <label
			for="name">Name:</label> <input type="text" id="name" name="name"
			value="${categoryToEdit.name}" required> <input type="submit"
			value="Save">
	</form>
	<br>
	<br>
	<div class="center-text">
		<a href="index.jsp">Return to Main Menu</a> <a
			href="/RecipeBox/viewAllCategoriesServlet">View All Categories</a>
	</div>
</body>
</html>
