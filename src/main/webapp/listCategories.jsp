<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>List of Categories</title>
</head>
<body>

<form method = "post" action = "categoryServlet">
	<c:forEach items="${requestScope.allCategories}" var="currentCategory">
		<tr>
			<td><input type="radio" name="id" value="${currentCategory.id}"></td>
			<td>${currentCategory.name}</td>
		</tr>	
	</c:forEach>
	<input type="submit" value="Edit" name="doThisToCategory">
	<input type="submit" value="Delete" name="doThisToCategory">
</form>

	<a href="index.html">Return to main menu</a>

</body>
</html>