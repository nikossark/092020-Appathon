<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="true"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Register a new user</title>
</head>
<body>
	
	<form method="post" action="newUser">
		<table border="0" cellpadding="2" cellspacing="2">
			<tr>
				<td>Username</td>
				<td><input type="text" name="username" value="<% out.println(request.getAttribute("username")); %>" /></td>
			</tr>
			<tr>
				<td>Password</td>
				<td><input type="password" name="password" /></td>
		   	</tr>
               <tr>
				<td>Full name</td>
				<td><input type="text" name="fullname" /></td>
            </tr>
            <tr>
				<td>Birth date</td>
				<td><input type="date" name="birthdate" /></td>
		   	</tr>
               <tr>
				<td>&nbsp;</td>
				<td><input type="submit" value="Register" /></td>
		   	</tr>		   	
		</table>
	</form>
</body>
</html>