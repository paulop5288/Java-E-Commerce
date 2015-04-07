import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import java.sql.*;

import com.mysql.jdbc.Driver;

public class SignUpServlet extends HttpServlet {

	String email = "", password = "", cpassword = "", title = "", fname = "",
			lname = "", qualification = "", organisation = "",
			specialisation = "";
			
	//Article credentials
	String articleTitle="" , articleAbstract="",coauthors="",keywords="",filepath="";
	int authorid=0;
	int role = 1, count = 0;
	Connection dbCon = null; // connection to a database

	public SignUpServlet() {
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
		
		email = req.getParameter("email");
		password = req.getParameter("password");
		cpassword = req.getParameter("cpassword");
		lname = req.getParameter("lname");
		fname = req.getParameter("fname");
		organisation = req.getParameter("organisation");
		if (email.trim().compareTo("") == 0) {
			res.setContentType("text/html");
			PrintWriter out = res.getWriter();
			out.println("<html><body>");
			out.println("Email address cannot be empty.");
			out.println("</body></html>");
			return;
		}
		
		if (password.compareTo(cpassword) != 0) {
			res.setContentType("text/html");
			PrintWriter out = res.getWriter();
			out.println("<html><body>");
			out.println("Password and Confirmed password should be the same.");
			out.println("</body></html>");
			return;
		}
		if (password.trim().compareTo("") == 0) {
			res.setContentType("text/html");
			PrintWriter out = res.getWriter();
			out.println("<html><body>");
			out.println("Password cannot be empty.");
			out.println("</body></html>");
			return;
		}
		
		
		
		
		
		PreparedStatement pstmt = null;

		try {
			dbCon = DriverManager.getConnection(dbServer + dbname, user,
					myPassword);
			// Get connection to team database
			pstmt = dbCon
					.prepareStatement("INSERT INTO author VALUES (null, ?, ?,?,?,?,?,?,?,?)");
			pstmt.setString(1, email);
			pstmt.setString(2, password);
			pstmt.setInt(3, role);
			pstmt.setString(4, title);
			pstmt.setString(5, fname);
			pstmt.setString(6, lname);
			pstmt.setString(7, qualification);
			pstmt.setString(8, organisation);
			pstmt.setString(9, specialisation);
			int count = pstmt.executeUpdate();

		} catch (SQLException ex) {

			ex.printStackTrace();

			return;
		}

		finally {
			if (dbCon != null)
				try {
					dbCon.close();
				} catch (SQLException ex) {
				}
		}
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();

		out.println("<html><body>");
		out.println("Hello " + fname
				+ ", You have succesfully registered as an author");
		out.println("</body></html>");
	}
}