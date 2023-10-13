<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<title>List of Categories</title>
</head>
<body>
	<h1>All Categories</h1>
	
	<form method = "post" action = "categoryNavigationServlet">
		<table>
			<c:forEach var="category" items="${allCategories}">
				<tr>
					<td><input type="radio" name="id" value="${category.id}"></td>
					<td><h2>${category.name}</h2></td>
				</tr>
			</c:forEach>
		</table>
		<br />
		
		<input type = "submit" value = "Edit" name = "doThisToCategory">
		<input type = "submit" value = "Delete" name = "doThisToCategory">
	</form>
	<!-- </ul>  -->
	<a href="index.html">Return to Main Menu</a>
	<a href="addCategory.jsp">Add a Category</a>
	
<!-- 	<ul> -->
<%-- 		<c:forEach var="category" items="${allCategories}"> --%>
<%-- 			<li>${category.name}</li> --%>
<%-- 		</c:forEach> --%>
<!-- 	</ul> -->
<!-- 	<a href="index.html">Return to Main Menu</a> -->
</body>
</html>
