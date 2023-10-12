<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>List of Categories</title>
</head>
<body>
	<h1>All Categories</h1>
	<ul>
		<c:forEach var="category" items="${categories}">
			<li>${category.name}</li>
		</c:forEach>
	</ul>
</body>
</html>
