<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page session="true"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>My home page</title>
</head>
<body>
    <a href="./pageupdate.jsp">Update profile</a>
    <br>
    <a href="./products.jsp">Products</a>
    <br>
    <a href="./basket.jsp">Basket</a>
    <br>
    <table border="0" cellpadding="2" cellspacing="2">
        <tr>
            <td>Username</td>
            <td><input type="text" name="username" value="<%= session.getAttribute("username") %>"/></td>
        </tr>
        <tr>
            <td>Password</td>
            <td><input type="password" name="password" value="<%= session.getAttribute("password") %>"/></td>
        </tr>
            <tr>
            <td>Full name</td>
            <td><input type="text" name="fullname" value="<%= session.getAttribute("fullname") %>"/></td>
        </tr>
        <tr>
            <td>Birth date</td>
            <td><input type="date" name="birthdate" value="<%= session.getAttribute("birthdate") %>"/></td>
        </tr>
    </table>
</body>
</html>