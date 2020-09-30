<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="true"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Update user profile</title>
</head>
<body>
	
	<form method="post" action="updateUser">
		<table border="0" cellpadding="2" cellspacing="2">
            <tr>
				<td>Full name</td>
				<td><input type="text" name="fullname" value="<%= session.getAttribute("fullname") %>"/></td>
            </tr>
            <tr>
				<td>Birth date</td>
				<td><input type="date" name="birthdate" value="<%= session.getAttribute("birthdate") %>"/></td>
		   	</tr>
               <tr>
				<td>&nbsp;</td>
				<td><input type="submit" value="Update" /></td>
		   	</tr>		   	
		</table>
	</form>
</body>
</html>