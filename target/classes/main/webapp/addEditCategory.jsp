<!DOCTYPE html>
<html>
<head>
<title>Add/Edit Category</title>
</head>
<body>
	<h1>Add/Edit Category</h1>
	<form action="CategoryServlet" method="post">
		<input type="hidden" name="id" value="${category.id}"> <label
			for="name">Name:</label> <input type="text" id="name" name="name"
			value="${category.name}" required> <input type="submit"
			value="Save">
	</form>
</body>
</html>
