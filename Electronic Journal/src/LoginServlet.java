import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import review.SelectReview;

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
		String role = req.getParameter("role");
		String query = "select * from author where" + " username = '" + username
				+ "' and " + "password = '" + password + "';";
		Connection con = null;
		Statement stmt = null;
		ResultSet resultSet = null;
		boolean userIsMatched = false;
		boolean isAuthor = true;
		String lastName = null;
		String organisation = null;
		String title = null;
		int userID = 0;
		PrintWriter out = res.getWriter();
		if (role != null && role.equalsIgnoreCase("reviewer")) {
			isAuthor = false;
		} else if (role != null && role.equalsIgnoreCase("author")) {
			isAuthor = true;
		}
		//Start session management here
		HttpSession session = req.getSession();

		try {
			con = DriverManager.getConnection(
					"jdbc:mysql://stusql.dcs.shef.ac.uk/team158", "team158",
					"9a5b309d");
			stmt = con.createStatement();
			out.print("connection successful");
			resultSet = stmt.executeQuery(query);
			if (resultSet.next()) {
				lastName = resultSet.getString("lastName");
				organisation = resultSet.getString("organisation");
				title = resultSet.getString("title");
				userID = resultSet.getInt("authorID");
				userIsMatched = true;
				session.setMaxInactiveInterval(30);	// 30 s
				session.setAttribute("username",resultSet.getString("username"));
				if (isAuthor) {
					session.setAttribute("role", "author");
				} else {
					session.setAttribute("role", "reviewer");
				}
				
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
				out.println(e.getMessage());
			}
			try {
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
				out.println(e.getMessage());
			}
		}
		if (userIsMatched) {
			if (!isAuthor && SelectReview.isReviewer(userID)) {
				res.setContentType("text/html");
				out.println("<html><body>");
				out.println("Hello " + title + "." + lastName + ", welcome back to International Journal of Software Engineering"
						+ "\nYou are logged in as a reviewer.");
				out.println("</body></html>");
				Cookie userName = new Cookie("username", username);
	            userName.setMaxAge(30);
	            res.addCookie(userName);
				res.sendRedirect("SelectReview.jsp");
				System.out.println("sucessful. reviewer");
			} else if (isAuthor) {
				// for author
			} else if (!isAuthor) {
				res.setContentType("text/html");
				out.println("<html><body>");
				out.println("Login failed: You are not a reviewer.");
				out.println("</body></html>");
				System.out.println("failed. reviewer");
			}			
		} else {
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/reviewer.jsp");
            out.println("<font color=red>Either user name or password is wrong.</font>");
            System.out.println("invalid");
            rd.include(req, res);
		}
	}
}
