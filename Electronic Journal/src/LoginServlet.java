import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import java.sql.*;

public class LoginServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
		out.println("<html><body>");
		out.println("You have used GET!");
		out.println("</body></html>");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.err.println("fail to load driver.");
		}
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String query = "select * from author where" + " username = '" + username
				+ "' and " + "password = '" + password + "';";

		Connection con = null;
		Statement stmt = null;
		ResultSet resultSet = null;
		boolean userIsMatched = false;
		String lastName = null;
		String organisation = null;
		String title = null;
		try {
			con = DriverManager.getConnection(
					"jdbc:mysql://stusql.dcs.shef.ac.uk/team158", "team158",
					"9a5b309d");
			stmt = con.createStatement();
			resultSet = stmt.executeQuery(query);
			if (resultSet.next()) {
				lastName = resultSet.getString("lastName");
				organisation = resultSet.getString("organisation");
				title = resultSet.getString("title");
				userIsMatched = true;
			} else {
				userIsMatched = false;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
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
			}
		}
		if (userIsMatched) {
			res.setContentType("text/html");
			PrintWriter out = res.getWriter();
			out.println("<html><body>");
			out.println("Hello " + title + "." + lastName + ", welcome back to "
					+ organisation);
			out.println("</body></html>");
			System.out.println("sucessful.");
		} else {
			res.setContentType("text/html");
			PrintWriter out = res.getWriter();
			out.println("<html><body>");
			out.println("Invalid username or password.");
			out.println("</body></html>");
			System.out.println("failed.");
		}
	}
}
