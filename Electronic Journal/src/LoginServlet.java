import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import database.User;
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
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String role = req.getParameter("role");
		boolean isAuthor = false;
		User user = new User(username, password);
		PrintWriter out = res.getWriter();
		if (role != null && role.equalsIgnoreCase("reviewer")) {
			isAuthor = false;
		} else if (role != null && role.equalsIgnoreCase("author")) {
			isAuthor = true;
		}
		// Start session management here
		HttpSession session = req.getSession();

		if (user.isMatched()) {
			session.setAttribute("username", username);
			session.setMaxInactiveInterval(30); // 30 s

			// if (isAuthor) {
			// session.setAttribute("role", "author");
			// } else {
			// session.setAttribute("role", "reviewer");
			// }
			//
			if (!isAuthor && SelectReview.isReviewer(user.getID())) {
				res.setContentType("text/html");
				out.println("<html><body>");
				out.println("Hello "
						+ user.getTitle()
						+ "."
						+ user.getLastName()
						+ ", welcome back to International Journal of Software Engineering"
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
			RequestDispatcher rd = getServletContext().getRequestDispatcher(
					"/reviewer.jsp");
			out.println("<p Either user name or password is wrong.>");
			System.out.println("invalid");
			rd.include(req, res);
		}
	}
}
