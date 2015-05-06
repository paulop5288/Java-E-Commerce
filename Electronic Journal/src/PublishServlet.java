
import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import java.sql.*;

import com.mysql.jdbc.Driver;

public class PublishServlet extends HttpServlet {
	private String publication = "", article = "", pages = "", message="";
	private Connection dbCon = null; // connection to a database

	public PublishServlet() {
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
		//Article publication parameters
		publication=req.getParameter("publicationid");
		article=req.getParameter("articleid");
		pages = req.getParameter("numpages");
		

		
		
		if (publication.trim().compareTo("")==0){
			out.println("Please, select current publication title.");
		}
		if (article.trim().compareTo("") == 0) {
			out.println("Please select an article to punlish.");
			out.println("</body></html>");
			return;
		}
		
		
		PreparedStatement pstmt = null;
		PreparedStatement pstmtArticle = null;

		try {
			dbCon = DriverManager.getConnection(dbServer + dbname, user,
					myPassword);
			
			//Update article as published and set in what journal it was published in.
			pstmt = dbCon
					.prepareStatement("UPDATE article SET publication_id=" + Integer.parseInt(publication) +", publicationDate=CURDATE() WHERE articleID="+article);
			
			pstmt.executeUpdate();


		} catch (SQLException ex) {

			ex.getMessage();
			out.println("<span class=\"error\">Article Publication Error: </span>" + ex.getMessage());
			return;
		}

		finally {
			if (dbCon != null)
				try {
					dbCon.close();
				} catch (SQLException ex) {
				}
		}
		

		out.println("<h2> The Article has been successfully published");
		out.println("</body></html>");
	}
	
}
