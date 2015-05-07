
import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import java.sql.*;

import com.mysql.jdbc.Driver;
public class EditorLoginServlet extends HttpServlet { 
 
 
 
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
 		String query = "select * from editor where" + " username = '" + username 
 				+ "' and " + "password = '" + password + "';"; 

 
		Connection con = null; 
 		Statement stmt = null; 
		ResultSet resultSet = null; 
		boolean userIsMatched = false; 
 		String surname = null; 
 		String title = null; 
 		
 		//Start session management here
 		HttpSession session = req.getSession(true);
		try { 
 			con = DriverManager.getConnection( 
 					"jdbc:mysql://stusql.dcs.shef.ac.uk/team158", "team158", 
 					"9a5b309d"); 
			stmt = con.createStatement(); 
 			resultSet = stmt.executeQuery(query); 
 			if (resultSet.next()) { 
				surname = resultSet.getString("surname"); 
 				title = resultSet.getString("title"); 
 				session.setAttribute("username",resultSet.getString("username"));
 				session.setAttribute("role","Editor");
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
 			}
 				catch (SQLException e) { 
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
 			out.println("Hello " + surname + ", welcome to IJSE" 
 					); 
 			out.println("</body></html>"); 
 			System.out.println("sucessful."); 
 			res.sendRedirect("/stucat008/editor/editor.jsp");
 		} else { 
 			res.setContentType("text/html"); 
			PrintWriter out = res.getWriter(); 			out.println("<html><body>"); 
 			out.println("Invalid username or password."); 
 			out.println("</body></html>"); 
 			System.out.println("failed."); 
 		} 
 	} 
		
}