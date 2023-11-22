<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Perfil de usuario</title>
</head>
<body>
Hello ${sessionScope.rol}
	<h3>Acceso con rol BASIC ou ADMIN</h3>
	</br>
	</br> Perfil de usuario
	<form action="/" method="post">
		<input type="hidden" name="action" value="LogOut">
		<input type="submit" value="LgOut">
	</form>
</body>
</html>