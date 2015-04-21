import javax.servlet.http.*;
import javax.servlet.*;

import java.io.*;

public class ReviewerLogin extends HttpServlet {
	//private int count = 0;
	private final String username = "admin";
    private final String password = "admin";
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		PrintWriter out = resp.getWriter();
        out.println("Hello World");
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		// get request parameters
		String reqUsername = req.getParameter("user");
		String reqPassword = req.getParameter("pwd");
		
		if (username.equals(reqUsername) && password.equals(reqPassword)) {
			Cookie loginCookie = new Cookie("user", reqUsername);
			loginCookie.setMaxAge(30);		// 30 seconds
			resp.addCookie(loginCookie);
			resp.sendRedirect("LoginSuccess.jsp");
		} else {
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.html");
            PrintWriter out = resp.getWriter();
            out.println("<font color=red>Either user name or password is wrong.</font>");
            rd.include(req, resp);
        }
		
//		resp.setContentType("text/plain");
//		PrintWriter out = resp.getWriter();
//		HttpSession session = req.getSession(true);
//		Integer count = (Integer) session.getAttribute("hits");
//		if (count == null) {
//			count = new Integer(1);
//		} else {
//			count = new Integer(count + 1);
//		}
//		session.setAttribute("hits", count);
//		out.println("You visted this page" + count + " times");
	}
	
}
