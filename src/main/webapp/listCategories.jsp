<-- List of Categories -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<title>List of Categories</title>
<style>
/* General styling */
body {
	font-family: Arial, sans-serif;
	margin: 20px;
}

h1 {
	text-align: center;
}

/* Table styling */
table {
	width: 20%;
	margin: auto;
	border-collapse: collapse;
	border: 1px solid black;
}

td, th {
	padding: 1rem;
	border-bottom: 2px solid black;
}

th {
	text-align: left;
}

td:last-child {
	text-align: right;
}

/* Centered button */
.centered-button {
	text-align: center;
	margin-top: 20px;
}

a.button {
	text-decoration: none;
	color: black;
	background-color: white;
	padding: 10px 20px;
	border: 1px solid black;
	border-radius: 5px;
}

a.button:hover {
	background-color: grey;
	color: white;
}
</style>
</head>
<body>

	<h1>All Categories</h1>

	<c:if test="${not empty errorMessage}">
		<div style="color: red; text-align: center;">${errorMessage}</div>
	</c:if>

	<table>
		<tr>
			<th style='text-align: left'>Category</th>
			<th style='text-align: right'>Actions</th>
		</tr>
		<c:forEach var="category" items="${allCategories}">
			<tr>
				<td>${category.name}</td>
				<td>
					<form action="editCategoryServlet" method="get"
						style="display: inline;">
						<input type="hidden" name="action" value="edit"> <input
							type="hidden" name="id" value="${category.id}">
						<button type="submit">Edit</button>
					</form>
					<form action="editCategoryServlet" method="get"
						style="display: inline;">
						<input type="hidden" name="action" value="delete"> <input
							type="hidden" name="id" value="${category.id}">
						<button type="submit">Delete</button>
					</form>
				</td>

			</tr>
		</c:forEach>
	</table>
	<div class="centered-button">
		<a href="index.jsp" class="button">Return to Main Menu</a> <a
			href="addCategory.jsp" class="button">Add a Category</a>
	</div>

</body>
</html>
