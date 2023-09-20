

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();

    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		String invalidLogin = "<a href=/TechExercise-BookTracker-Alhejaj/login.html>Invalid Login. Try Again.</a><br/>";
		
		out.println("<!DOCTYPE html>\n" +
					"<html>\n");
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			DBConnection.getDBConnection();
			connection = DBConnection.connection;
			
			if (username.isEmpty() || password.isEmpty()) {
				out.println(invalidLogin);
			}
			else {
				String selectSQL = "SELECT * FROM Users WHERE USER=? AND PSWD=?";
				preparedStatement = connection.prepareStatement(selectSQL);
				preparedStatement.setString(1, username);
				preparedStatement.setString(2, Security.encrypt(password));
				
				ResultSet results = preparedStatement.executeQuery();
				
				while (results.next()) {
					// TODO Login User and Redirect
					out.println("User found!");
				}
				
				results.close();
			}
			
			out.println("</body></html>");
	        preparedStatement.close();
	        connection.close();
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
		    e.printStackTrace();
		} finally {
		   try {
		      if (preparedStatement != null)
		         preparedStatement.close();
		   } catch (SQLException se2) {
		   }
		   try {
		      if (connection != null)
		         connection.close();
		   } catch (SQLException se) {
		      se.printStackTrace();
		   }
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
}
