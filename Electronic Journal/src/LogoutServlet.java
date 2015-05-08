import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class LogoutServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String role = "";
		resp.setContentType("text/html");
		Cookie[] cookies = req.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("JSESSIONID")) {
					System.out.println("JSESSIONID=" + cookie.getValue());
					break;
				}
			}
		}
		// invalidate the session if exists
		HttpSession session = req.getSession(false);		
		if (session != null) {
			role = (String) session.getAttribute("role");
			if (role.equalsIgnoreCase("author")) {
				// back to author home page
				resp.sendRedirect("author.jsp");
			} else if (role.equalsIgnoreCase("reviewer")) {
				resp.sendRedirect("reviewer.jsp");
			} else {
				// back to home page
				resp.sendRedirect("index.jsp");
			}
			session.invalidate();
		} else {
			// back to home page
			resp.sendRedirect("index.jsp");
		}
	}
}
