<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Add or Edit a Category</title>
</head>
<body>
<!-- form to enter category info. If the category is one being edited, the name and id can be added. -->
	<form action="categoryServlet" method="post">
		ID: ${currentCategory.id} <br />
		Category: <input type = "text" name = "name" value = "${currentCategory.name}">
		
	<input type = "submit" value = "Submit">
	</form>
	<!-- Link to homepage -->
<a href = "index.html">Cancel and Return to Homepage</a>

</body>
</html>