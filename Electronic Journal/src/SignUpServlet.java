import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import com.mysql.jdbc.Driver;

public class SignUpServlet extends HttpServlet {

	private String email = "", password = "", cpassword = "", title = "", fname = "",
			lname = "", qualification = "", organisation = "",
			specialisation = "",message="";

	//Article credentials
	private String articleTitle="" , articleAbstract="",coauthors="",keywords="",filepath="";
	private int authorid=0;
	private int role = 1, count = 0;
	private Connection dbCon = null; // connection to a database

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

		//Article submission parameters
		articleTitle=req.getParameter("articletitle");
		articleAbstract = req.getParameter("articleabstract");
		coauthors = req.getParameter("coauthors");
		keywords = req.getParameter("keywords");
		
		//Author Registration Parameters
		email = req.getParameter("email");
		password = req.getParameter("password");
		cpassword = req.getParameter("cpassword");
		title = req.getParameter("title");
		lname = req.getParameter("lname");
		fname = req.getParameter("fname");
		organisation = req.getParameter("organisation");

		//Prepare HTML page for output
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
		message="<html><head><link rel=\"stylesheet\" type=\"text/css\" href=\"major.css\"></head><body>";
		out.println(message);message="";
		//Start session management here
		HttpSession session = req.getSession(true);
		

		if (email.trim().compareTo("") == 0) {
			message="Email address cannot be empty.</body></html>";
			out.println(message);
			return;
		}
		
		if (password.compareTo(cpassword) != 0) {
			message="Password and Confirmed password should be the same.</body></html>";
			out.println(message);
			return;
		}
		if (password.trim().compareTo("") == 0) {
			message="Password cannot be empty.</body></html>";
			out.println(message);
			return;
		}
		
		if (fname.trim().compareTo("") == 0) {
			message="First name cannot be empty.</body></html>";
			out.println(message);
			return;
		}
		
		
		
		PreparedStatement pstmt = null;
		PreparedStatement pstmtArticle = null;

		try {
			dbCon = DriverManager.getConnection(dbServer + dbname, user,
					myPassword);
			// Get connection to team database
			pstmt = dbCon
					.prepareStatement("INSERT INTO author VALUES (null, ?, ?,?,?,?,?,?,?)");
			pstmt.setString(1, email);
			pstmt.setString(2, password);
			pstmt.setString(3, title);
			pstmt.setString(4, fname);
			pstmt.setString(5, lname);
			pstmt.setString(6, qualification);
			pstmt.setString(7, organisation);
			pstmt.setString(8, specialisation);
			int count = pstmt.executeUpdate();

			if(count > 0){
				message="Hello " + fname
				+ ", You have succesfully registered as an author<br/>";
				session.setAttribute("username",email);
				session.setAttribute("password",password);
				session.setAttribute("role","author");
				out.println(message);

			}
			//Handles article submission, However, we need a fileupload wrapper for this to work on this tomcat server
			authorid=count;
			filepath = articleTitle + authorid;
			//Retrieve file here, do upload and set corrected file name
			
			pstmtArticle = dbCon.prepareStatement(
  				"INSERT INTO article(id,authorID,title,Other_authors,abstract,keywords,article_file) VALUES (null, ?, ?,?,?,?,?)");
			pstmtArticle.setInt(1,authorid);
			pstmtArticle.setString(2,articleTitle);
			pstmtArticle.setString(3,coauthors);
			pstmtArticle.setString(4,articleAbstract);
			pstmtArticle.setString(5,keywords);
			pstmtArticle.setString(6,filepath);
			count = 0;
			count=pstmtArticle.executeUpdate();
			if(count > 0){
				message+="<span class=\"success\">Article submission was successful</br></span>";
				session.setAttribute("article",articleTitle);
			}

		} catch (SQLException ex) {

			ex.printStackTrace();
			out.println("<span class=\"error\">Article upload or Author Registration Error: </span>" + ex.getMessage());
			return;
		}

		finally {
			if (dbCon != null)
				try {
					dbCon.close();
				} catch (SQLException ex) {
				}
		}
		message+="</body></html>";
		out.println(message);
	}
}