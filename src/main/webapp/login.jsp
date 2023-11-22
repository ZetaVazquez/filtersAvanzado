<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Página principal con login</title>
</head>
<body>
<h3>Acceso con  rol ANON, USER ou ADMIN</h3></br></br>
 <form action="/" method="post">
  Usuario: <input type="text" name="nome"/>
  <br/> 
  <input type="submit" value="acceso" />
<!-- Non enviamos contrasinal para simplificar !-->
 </form>
<br/><br/>
 <h3>...Información de acceso público...</h3>
</body>
</html>