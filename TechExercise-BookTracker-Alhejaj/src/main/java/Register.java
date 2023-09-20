

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Register
 */
@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String username = request.getParameter("username");
	    String password = request.getParameter("password");
	    String confirm = request.getParameter("cPassword");
	    
	    Connection connection = null;
	    String insertSql = "INSERT INTO Users (id, USER, EMAIL, PSWD) values (default, ?, ?, ?)";
	    
	    try {
	    	DBConnection.getDBConnection();
	    	connection = DBConnection.connection;
	    	if (validUser(username, connection) && password.contentEquals(confirm))
	    	{
		    	PreparedStatement preparedStatement = connection.prepareStatement(insertSql);
		    	preparedStatement.setString(1, username);
		    	preparedStatement.setString(2, email);
		    	preparedStatement.setString(3, Security.encrypt(password));
		    	preparedStatement.execute();
		    	connection.close();
		    	// TODO Login and Redirect
	    	}
	    	else
	    	{
	    		// TODO Invalid Registration
	    	}
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	}

	private boolean validUser(String username, Connection connection) {
		// TODO Check if username/email are available
		return false;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
