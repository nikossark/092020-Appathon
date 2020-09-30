package main;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;

@WebServlet("/addToBasket")
public class AddToBasket extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AddToBasket() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{ 
            HttpSession session=request.getSession();  
            String id = request.getParameter("id");

            ArrayList<String> productIds = (ArrayList<String>) session.getAttribute("productIds");
            if (productIds == null){
                productIds = new ArrayList<>();
            }
            productIds.add(id);
            session.setAttribute("productIds", productIds);
            System.out.println("id-------: " + id);
        }
		catch (Exception e) { 
            e.printStackTrace(); 
        }
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
}
