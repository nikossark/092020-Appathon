package main;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.Connection; 
import java.sql.PreparedStatement; 
import java.sql.SQLException; 
import java.sql.ResultSet; 
import java.text.SimpleDateFormat;

import main.DatabaseConnection; 

@WebServlet("/newUser")
public class NewUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public NewUser() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{ 
            User user = new User();
            user.username = request.getParameter("username");
            user.password = request.getParameter("password");
            user.fullname = request.getParameter("fullname");
            String birthdate = request.getParameter("birthdate");

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date = format.parse(birthdate); 
            user.birthdate = date;

            if(insertUser(user)){
                request.setAttribute("message", "Account created!");
                System.out.println(request.getAttribute("message"));
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
            else{
                request.setAttribute("message", "Error in creation.");
                 System.out.println(request.getAttribute("message"));
            }
        }
		catch (Exception e) { 
            e.printStackTrace(); 
        }
	}

    public static boolean insertUser(User user) {
        Connection conn = null;
        PreparedStatement pst = null;
        boolean status = false;

        try {  
            conn = DatabaseConnection.initializeDatabase();  
            pst = conn .prepareStatement("insert into myuser values (?,?,?,?)");  
            pst.setString(1, user.username);  
            pst.setString(2, user.password);  
            pst.setString(3, user.fullname); 

            java.sql.Timestamp timestamp = convert(user.birthdate);
            pst.setTimestamp(4, timestamp); 

            int rowsUpdated = pst.executeUpdate();  
            status = rowsUpdated == 1;

        } catch (Exception e) {  
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

    public static java.sql.Timestamp convert(java.util.Date date)
	{
		return new java.sql.Timestamp(date.getTime());
	}

}
