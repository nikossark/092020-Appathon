package main;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletContext;

import java.sql.Connection; 
import java.sql.PreparedStatement; 
import java.sql.SQLException; 
import java.sql.ResultSet; 
import java.text.SimpleDateFormat;

import main.DatabaseConnection; 
import java.util.UUID;
import java.util.ArrayList;
import java.io.*;

@WebServlet("/order")
public class Order extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Order() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{ 
            HttpSession session=request.getSession(false);  
            String username=(String)session.getAttribute("username"); 
            String orderId = UUID.randomUUID().toString();
            //check if logedIn
            System.out.println("submiting order...........");
            ArrayList<String> productIds = (ArrayList<String>) session.getAttribute("productIds");
            if(productIds != null && productIds.size()> 0 && insertOrder(orderId, username, productIds)){
                saveToFile(request, orderId, username, productIds);
                request.setAttribute("message", "Successful order!");
                System.out.println(request.getAttribute("message"));
                request.getRequestDispatcher("myhomepage.jsp").forward(request, response);
            }
            else{
                request.setAttribute("message", "Error in update.");
                System.out.println(request.getAttribute("message"));
            }
        }
		catch (Exception e) { 
            e.printStackTrace(); 
        }
	}

    public static boolean insertOrder(String orderId, String username, ArrayList<String> productIds) {
        Connection conn = null;
        PreparedStatement pst = null;
        boolean status = false;

        try {  
            conn = DatabaseConnection.initializeDatabase();  
            pst = conn .prepareStatement("insert into orders values (?,?)");  
            pst.setString(1, orderId);  
            pst.setString(2, username);  

            int rowsUpdated = pst.executeUpdate();  
            status = rowsUpdated == 1;

            String ordrItemsSql = "insert into orderitem values ";
            for (String productId : productIds) { 		
                String orderItemId = UUID.randomUUID().toString();
                String message = String.format("('%s','%s','%s'),", orderItemId, orderId, productId); 
                ordrItemsSql += message;
            }	
            //remove last commma
            ordrItemsSql = ordrItemsSql.substring(0, ordrItemsSql.length() - 1);
            System.out.println("query: " + ordrItemsSql);
            pst = conn.prepareStatement(ordrItemsSql);  
            rowsUpdated = pst.executeUpdate(); 
            status = (status == true) && (rowsUpdated > 0);
        }
        catch (Exception e) {  
            System.out.println(e);  
        } 
        finally {  
            try {
                pst.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return status;
    }

    public void saveToFile(HttpServletRequest request, String orderId, String username, ArrayList<String> productIds){
        try{
            ServletContext context = request.getServletContext();
            String fileName = context.getRealPath("/files/order.txt");
            FileWriter filewrt = new FileWriter(fileName,true);
            BufferedWriter fileout = new BufferedWriter(filewrt);
            fileout.write("order id: " + orderId+ ", username: "+username);
            fileout.newLine();
            fileout.write("products list: ");
            fileout.newLine();
            for (String productId : productIds) { 
                fileout.write(productId);
                fileout.newLine();
                
            }
            fileout.write("------------------------------------------------------------------------------------------------------");
            fileout.newLine();
            fileout.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }

}
