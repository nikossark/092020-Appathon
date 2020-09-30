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

@WebServlet("/updateUser")
public class UpdateUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public UpdateUser() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{ 
            HttpSession session=request.getSession(false);  
            String username=(String)session.getAttribute("username"); 
            User user = new User();
            user.username = username;
            user.fullname = request.getParameter("fullname");
            String birthdate = request.getParameter("birthdate");

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date = format.parse(birthdate); 
            user.birthdate = date;

            if(updateUser(user)){
                request.setAttribute("message", "Successful update!");
                System.out.println(request.getAttribute("message"));

                session.setAttribute("fullname", user.fullname);
				session.setAttribute("birthdate", format.format(user.birthdate));

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

    public static boolean updateUser(User user) {
        Connection conn = null;
        PreparedStatement pst = null;
        boolean status = false;

        try {  
            conn = DatabaseConnection.initializeDatabase();  
            pst = conn .prepareStatement("update myuser set fullname=?,birthdate=? where username=?");  
            pst.setString(1, user.fullname);  

            java.sql.Timestamp timestamp = convert(user.birthdate);
            pst.setTimestamp(2, timestamp); 
            pst.setString(3, user.username); 

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
