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
<%@ page import ="java.util.ArrayList"%>
<%@ page import ="java.util.List"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Products page</title>
<script type="text/javascript" src="js/myjs.js"></script>
<script type="text/javascript">
    function changeCountry() {
        var countryCode = document.getElementById("countries").value;
        return setCountry(countryCode);
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
    <a href="./products.jsp">Products</a>
    <br>
    <h1>Items in basket</h1>
        <ul>
            <%
                String totalPriceWithVatStr = "0";

                String discountStr = (String) session.getAttribute("discount");
                double discount = 0;
                String priceColor="black";
                if(discountStr!=null){
                    discount = Double.parseDouble(discountStr);
                    if(discount > 0) priceColor="red";
                }

                double vat = 24.0;
                String vatCode = "el";
                String vatStr = (String) session.getAttribute("vat");
                if(vatStr!=null){
                    vat = Double.parseDouble(vatStr);
                    vatCode = (String) session.getAttribute("vatCode");
                }

                ArrayList<String> productIds = (ArrayList<String>) session.getAttribute("productIds");
                out.println(productIds);

                double totalPrice = 0;
                double totalPriceWithVat  = 0;
                while (rs.next()) { 
                    String id = rs.getString("id");
                    if(productIds != null && productIds.contains(id)){
                        String title = rs.getString("title");
                        String priceStr = rs.getString("price");
                        double price = Double.parseDouble(priceStr);
                        double priceWithVat = price * (1 + vat/100);
                        String priceWithVatStr = String.format("%.2f", priceWithVat);
                        totalPrice+=price;
                        totalPriceWithVat+=priceWithVat;
            %>
            <li id="<%=rs.getString("id")%>">
                <span><%=title%></span>
                <br>
                Price: <input type="text" name="price" value="<%=priceStr%>"/>
                <br>
            </li>
            <br>
            <% }} 
                totalPriceWithVat = totalPriceWithVat * (1-discount);
                totalPriceWithVatStr = String.format("%.2f", totalPriceWithVat);
                rs.close(); 
            %>
        </ul>
        <p>
            Total price (incl. VAT <%=vat%>%): <span style="color:<%=priceColor%>;"><%=totalPriceWithVatStr%>$</span>
        </p>
        <br>
        <form method="post" action="basket">
            Coupon code: <input type="text" name="coupon" value=""/>
            <br>
            <input type="submit" value="Apply coupon">
            </form>
        <br>
        <br>
        <p>
            <label for="countries">Select country:</label>
            <select name="countries" id="countries" onchange="changeCountry()" value="<%=vatCode%>">
            <option selected hidden>Choose here</option>
              <option value="el">Greece</option>
              <option value="be">Belgium</option>
              <option value="de">Germany</option>
              <option value="uk">United Kingdom</option>
              <option value="it">Italy</option>
            </select>
            <br><br>
            <form method="post" action="order">
                <input type="submit" value="Submit order">
            </form>
        </p>
</body>
</html>