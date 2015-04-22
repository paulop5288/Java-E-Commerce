import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import java.sql.*;

import com.mysql.jdbc.Driver;

public class AppointEditorServlet extends HttpServlet {
	private String editorID = "", username = "", password = "", title = "", surname = "", forename = "", status="active";
	
	//Appoint Editor credentials
	
	private Connection dbCon = null; // connection to a database

	public AppointEditorServlet() {
		super();
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		// Database Parameters
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.err.println("fail to load driver.");
		}
		String dbServer = "jdbc:mysql://stusql.dcs.shef.ac.uk/";
		String dbname = "team158";
		String user = "team158";
		String myPassword = "9a5b309d";
		
		//Notification submission parameters
		username=req.getParameter("username");
		password=req.getParameter("password");
		title = req.getParameter("title");
		surname =  req.getParameter("surname");
		forename = req.getParameter("forename");
		

		//Prepare HTML page for output
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
		out.println("<html><body>");
		
		if (username.trim().compareTo("")==0){
			out.println("Username cannot be empty.");
		}
		if (password.trim().compareTo("") == 0) {
			out.println("Password cannot be empty.");
		
			out.println("</body></html>");
			return;
		}
		
		PreparedStatement pstmt = null;

		try {
			dbCon = DriverManager.getConnection(dbServer + dbname, user,
					myPassword);
			// Get connection to team database
			
			//Handles Notification submission
			pstmt = dbCon
					.prepareStatement("INSERT INTO editor VALUES (null,?,?,?,?,?,?)");
			pstmt.setString(1, username);
			pstmt.setString(2,password);
			pstmt.setString(3, title);
			pstmt.setString(4, surname);
			pstmt.setString(5, forename);
			pstmt.setString(6, status);
			pstmt.executeUpdate();


		} catch (SQLException ex) {

			ex.printStackTrace();

			return;
		}

		finally {
			if (dbCon != null)
				try {
					dbCon.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
		}
		

		out.println("Hello, You have succesfully appointed " + forename + " as another editor");
		out.println("</body></html>");
	}
}