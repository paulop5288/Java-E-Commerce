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

		if (isAuthor) {
			// for author
		} else {
			
			if (user.isMatched() && SelectReview.isReviewer(user.getID())) {
				// Start session management here
				HttpSession session = req.getSession();
				session.setAttribute("username", username);
				session.setMaxInactiveInterval(5*60); // 5 min
				session.setAttribute("role", "reviewer");

				res.setContentType("text/html");
				Cookie userName = new Cookie("username", username);
				userName.setMaxAge(5*60);
				res.addCookie(userName);
				res.sendRedirect("selectreview.jsp");
				System.out.println("sucessful. reviewer");

			} else if (user.isMatched() && !SelectReview.isReviewer(user.getID())) {
				res.setContentType("text/html");
				out.println("<script type=\"text/javascript\">");
				out.println("alert(\"You are not a reviewer.\");");
				out.println("window.location = '/Electronic%20Journal/reviewer.jsp';");
				out.println("</script>");
			} else {
				res.setContentType("text/html");
				out.println("<script type=\"text/javascript\">");
				out.println("alert(\"Username/password doesn't match.\");");
				out.println("window.location = '/Electronic%20Journal/reviewer.jsp';");
				out.println("</script>");
			}

		}
	}
}
