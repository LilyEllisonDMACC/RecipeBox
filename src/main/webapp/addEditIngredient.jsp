<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Add/Edit Ingredient</title>
</head>
<body>
	<form action="ingredientServlet" method="post">
		<label for="name">Name:</label> <input type="text" id="name"
			name="name" required> <input type="submit" value="Submit">
	</form>
</body>
</html>
