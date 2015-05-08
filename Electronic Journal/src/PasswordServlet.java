

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class PasswordServlet
 */
@WebServlet("/PasswordServlet")
public class PasswordServlet extends HttpServlet {


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("../stucat008/changepassword.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.err.println("fail to load driver.");
		}
		String username = request.getParameter("username");
		String oldPassword = request.getParameter("oldpassword");
		String newPassword = request.getParameter("password");
		String confirmedPassword = request.getParameter("cpassword");
		PrintWriter out = response.getWriter();
		String query = "update author set password = '" + newPassword + "'"
						+ " where username = '" + username + "' and " + "password = '" + oldPassword + "';";
		Connection con = null;
		Statement stmt = null;
		boolean userIsMatched = false;
		if (newPassword.compareTo(confirmedPassword) != 0) {
			response.setContentType("text/html");
			out.println("<html><body>");
			out.println("New Password and Confirmed Password are not the same.");
			out.println("</body></html>");
			return;
		}
		if (newPassword.trim().compareTo("") == 0) {
			response.setContentType("text/html");
			
			out.println("<html><body>");
			out.println("New Password cannot be empty.");
			out.println("</body></html>");
			return;
		}
		try {
			con = DriverManager.getConnection(
					"jdbc:mysql://stusql.dcs.shef.ac.uk/team158", "team158",
					"9a5b309d");
			stmt = con.createStatement();
			int result = stmt.executeUpdate(query);
			if (result == 1) {
				userIsMatched = true;
			} else {
				userIsMatched = false;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			out.println("Error:" + e1.getMessage());
			} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
				out.println("Error:" + e.getMessage());
			}
		}
		if (userIsMatched) {
			response.setContentType("text/html");
			out.println("<html><body>");
			out.println("Hello " + username + ", you have changed the password.");
			out.println("</body></html>");
		} else {
			response.setContentType("text/html");
			
			out.println("<html><body>");
			out.println("Invalid username or password.");
			out.println("</body></html>");
		}
	}

}
