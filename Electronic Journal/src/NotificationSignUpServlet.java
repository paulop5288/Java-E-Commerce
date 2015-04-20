import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import java.sql.*;

import com.mysql.jdbc.Driver;

public class NotificationSignUpServlet extends HttpServlet {
	private String fullname = "", email = "", profession = "", interest = "", message="";
	
	//Notification credentials
	
	private Connection dbCon = null; // connection to a database

	public NotificationSignUpServlet() {
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
		
		//Prepare HTML page for output
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
		
		
		message="<html><head><link rel=\"stylesheet\" type=\"text/css\" href=\"major.css\"></head><body>";
		out.println(message);message="";
		//Notification submission parameters
		fullname=req.getParameter("fullname");
		email=req.getParameter("email");
		profession = req.getParameter("profession");
		interest =  req.getParameter("interest");

		
		
		if (fullname.trim().compareTo("")==0){
			out.println("Fullname cannot be empty.");
		}
		if (email.trim().compareTo("") == 0) {
			out.println("Email address cannot be empty.");
			out.println("</body></html>");
			return;
		}
		
		
		PreparedStatement pstmt = null;
		PreparedStatement pstmtArticle = null;

		try {
			dbCon = DriverManager.getConnection(dbServer + dbname, user,
					myPassword);
			// Get connection to team database
			
			//Handles Notification submission
			pstmt = dbCon
					.prepareStatement("INSERT INTO Notification(id,fullname,email,proffesion,interest) VALUES (null,?, ?,?,?)");
			pstmt.setString(1, fullname);
			pstmt.setString(2,email);
			pstmt.setString(3, profession);
			pstmt.setString(4, interest);
			pstmt.executeUpdate();


		} catch (SQLException ex) {

			ex.getMessage();
			out.println("<span class=\"error\">Notification Sign Up Error: </span>" + ex.getMessage());
			return;
		}

		finally {
			if (dbCon != null)
				try {
					dbCon.close();
				} catch (SQLException ex) {
				}
		}
		

		out.println("Hello " + fullname
				+ ", You have succesfully registered for Notification");
		out.println("</body></html>");
	}
}
