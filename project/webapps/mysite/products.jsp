<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page session="true"%>
<%@ page import="java.sql.*"%>
<%
	String connectionURL = "jdbc:mysql://mysql:3306/db";
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Products page</title>
<script type="text/javascript" src="js/myjs.js"></script>
<script type="text/javascript">
    function myFocusFunction(id) {
        document.getElementById(id).style.display = "inline";
    }
    
    function myBlurFunction(id) {
      document.getElementById(id).style.display = "none";
    }
    </script>
</head>
<body>
    <%
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		connection = DriverManager.getConnection(connectionURL, "user", "password");
		statement = connection.createStatement();
		rs = statement.executeQuery("SELECT * FROM products");
    %>
        
    <a href="./myhomepage.jsp">Home page</a>
    <br>
    <a href="./basket.jsp">Basket</a>
    <br>
    <h1>Product List</h1>
        <ul>
            <% while (rs.next()) { 
                String id = rs.getString("id");
                String title = rs.getString("title");
                String priceStr = rs.getString("price");
                double price = Double.parseDouble(priceStr);
                double priceWithVat = price * 1.24;
                String priceWithVatStr = String.format("%.2f", priceWithVat);
            %>
            <li id="<%=rs.getString("id")%>">
                <span><%=title%></span>
                <br>
                Price: <input type="text" name="price" value="<%=priceStr%>" onfocus="myFocusFunction('<%=id%>-vat')" onblur="myBlurFunction('<%=id%>-vat')"/>
                <span id="<%=id%>-vat" style="display:none;">incl. VAT: <%=priceWithVatStr%></span>
                <br>
                <button type="button" onclick="return addToBasket('<%=id%>');">Add to basket</button>
                <br>
            </li>
            <br>
            <% } rs.close(); %>
        </ul>
</body>
</html>