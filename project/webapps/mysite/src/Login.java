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
/**
 * Servlet implementation class session
 */
@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public Login() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{ 
			String username = request.getParameter("username");
			String password = request.getParameter("password");

			System.out.println("before validate");  
			User user = validate(username,password);
			if (user != null)
			{
				System.out.println(user.birthdate);  
				HttpSession session = request.getSession();
				session.setAttribute("username", user.username);
				session.setAttribute("password", user.password);
				session.setAttribute("fullname", user.fullname);

				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				String birthdate = format.format(user.birthdate);
				session.setAttribute("birthdate", birthdate);
				// response.sendRedirect("myhomepage.jsp");
				request.getRequestDispatcher("myhomepage.jsp").forward(request, response);
			}
			else
			{
				request.setAttribute("username", username);
				request.setAttribute("message", "Account is invalid");
				request.getRequestDispatcher("newUser.jsp").forward(request, response);
			}
		}
		catch (Exception e) { 
            e.printStackTrace(); 
        } 
	}

	public static User validate(String username, String password) {
        User user = null;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {  
            conn = DatabaseConnection.initializeDatabase();  

            pst = conn  
                    .prepareStatement("select * from myuser where username=? and password=?");  
            pst.setString(1, username);  
            pst.setString(2, password);  

            rs = pst.executeQuery();  
            if(rs.next()){
				user = new User();
				user.username = rs.getString("username");
				user.password = rs.getString("password");
				user.fullname = rs.getString("fullname");
				java.sql.Timestamp timestamp = rs.getTimestamp("birthdate");
				if (timestamp != null){
					user.birthdate = new java.util.Date(timestamp.getTime());
				}
			}
        } 
		catch (Exception e) {  
            System.out.println(e);  
        } 
		finally {  
            try {
                rs.close();
                pst.close();
                conn.close();
            } 
			catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return user;
    }

}
