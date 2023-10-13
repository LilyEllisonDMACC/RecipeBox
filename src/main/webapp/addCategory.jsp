<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Add A Category</title>
</head>
<body>
	<form action ="AddCategoriesServlet" method = "post">
	<label for="name">Category Name:</label> 
		<input type="text" id="name" name="name" required> <br>
		
		<input type="submit" value="Submit">
	
	</form>
	
	<a href="index.html">Return to Main Menu</a>
	
	<a href="/categoryNavigationServlet">View All Categories</a>

</body>
</html>