<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<title>Edit Category</title>
</head>
<body>
	<h1>Add/Edit Category</h1>
	<form action="editCategoryServlet" method="post">
		
		<input type="hidden" name="id" value="${categoryToEdit.id}"> 
		
		<label for="name">Name:</label> 
		<input type="text" id="name" name="name" value="${categoryToEdit.name}" required> 
		
		<input type="submit" value="Save">
	</form>
	<a href="index.jsp">Return to Main Menu</a>
	<a href="/categoryNavigationServlet">View All Categories</a>
</body>
</html>
