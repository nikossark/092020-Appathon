package main;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;

@WebServlet("/basket")
public class Basket extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Basket() {
        super();
    }

    Country[] countries = { 
        new Country(24.0, "el"), 
        new Country(21.0, "be"), 
        new Country(19.0, "de"),
        new Country(20.0, "uk"),
        new Country(22.0, "it") 
    };

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{ 
            HttpSession session=request.getSession();  
            String countryCode = request.getParameter("countryCode");
            double newVat = 24.0;
            String newVatCode = "el";
            for (int i = 0; i < countries.length; i++){
                if(countryCode.equals(countries[i].code)){
                    newVat = countries[i].vat;
                    newVatCode = countries[i].code;
                }
            }
            session.setAttribute("vat", String.valueOf(newVat));
            session.setAttribute("vatCode", String.valueOf(newVatCode));
            request.getRequestDispatcher("basket.jsp").forward(request, response);
        }
		catch (Exception e) { 
            e.printStackTrace(); 
        }
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{ 
            HttpSession session=request.getSession(false);  
            String coupon = request.getParameter("coupon");
            if(coupon != null && coupon.equals("studentdiscount")){
                session.setAttribute("discount", "0.2");
            }
            else{
                session.setAttribute("discount", "0");
            }
            request.getRequestDispatcher("basket.jsp").forward(request, response);
        }
		catch (Exception e) { 
            e.printStackTrace(); 
        }
        
	}
}

class Country { 
    double vat;
    String code;

    public Country(double vat, String code) 
    { 
        this.vat = vat; 
        this.code = code; 
    } 
}
