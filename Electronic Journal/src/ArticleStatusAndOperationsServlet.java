import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import java.sql.*;

import com.mysql.jdbc.Driver;

public class ArticleStatusAndOperationsServlet extends HttpServlet{
	private String editordashboard="";
	private int serial=1,authorid=0,articleid=0;
	private Connection con = null;
	private Statement stmt = null;
	private ResultSet result = null;
		String author = null;
	String organisation = null;
	String title = null;
	PrintWriter out=null;
	public ArticleStatusAndOperationsServlet() {
		super();
		// Load database driver
		try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				System.err.println("fail to load driver.");
				return;
			}
		try{
			con = DriverManager.getConnection(
					"jdbc:mysql://stusql.dcs.shef.ac.uk/team158", "team158",
					"9a5b309d");
			
		}
		catch(Exception e){
			
			e.printStackTrace();
		}
			

	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		out = res.getWriter();
		editordashboard+= "<table><tr><th>S.N.</th><th>Title</th><th>Lead Author Email </th><th></th></tr>";
		this.getArticleDetails();
		try {
			if (con != null) {
				con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			out.println(e.getMessage());
		}
	
	}
	
	
	
	void getArticleDetails(){
		String query = "select * from article";
		try {
			Statement stmt = con.createStatement();
			ResultSet result = stmt.executeQuery(query);
			{
	             
				editordashboard +="<tr><td>" + ++serial+ "</td><td>"+result.getString("title")+"</td><td>" + this.getAuthorEmail(result.getInt("authorID"))+"</td><td>" + "<a href=\"articleDetails.jsp?article=" +result.getInt("articleID")+ "&author="+result.getInt("authorID")+ "\" >View Details </a>" +"</tr>";
				
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
			}
	
	void getArticleRevision(){
		
	}
	
	void getArticleReviews(){
		
	}
	
	void getArticleStatus(){
		
	}
String getAuthorEmail(int authorid){
	String query = "select * from author WHERE id="+ authorid,email="";
	Statement	stmt1=null;
	
	try {
		stmt1 = con.createStatement();
	ResultSet	result1 = stmt1.executeQuery(query);
		if (result1.next()) {
			
			
			email=result1.getString("username");
		} 
		
	}catch (SQLException e) {
		e.printStackTrace();
		out.print(e.getMessage());
	}
	finally {
		try {
			if (stmt1 != null) {
				stmt1.close();
			}

		} catch (SQLException e) {
			e.printStackTrace();
			out.println(e.getMessage());
		}
		
	}
	return email;
	
}
String getArticleDetail(int articleID, int authorID){
	String details="<table><tr>";
	String query = "select * from article WHERE articleID="+ articleID;
	Statement	stmt1=null;
	try {
		stmt1 = con.createStatement();
	ResultSet	result1 = stmt1.executeQuery(query);
		if (result1.next()) {
			
			
			details+="<td>Title:</td>"+result1.getString("title");
			details+="<td>Abstract:</td><td>"+result1.getString("abstract");
			details+="<td></td><td><a href=\""+result1.getString("article_file")+"\"> Download Full Article</a></td>";
			details+="<td> No. of Reviews:</td><td>" + result1.getInt("no_reviewer")+"</td>";
			details+="<td> No. of Author's Reviews:</td><td>" + result1.getInt("author_reviews")+"</td>";
					} 
		details+="<a href=\" Review.jsp\"> Review Article</a> <a href=\"editor/publish.jsp\">Publish Article</a><a href=\"editor/reject.jsp?articleid=" +articleID + "\">Reject Article</a>";
		
	}catch (SQLException e) {
		e.printStackTrace();
		out.print(e.getMessage());
	}
	finally {
		try {
			if (stmt1 != null) {
				stmt1.close();
			}

		} catch (SQLException e) {
			e.printStackTrace();
			out.println(e.getMessage());
		}
		
	}
	
	return details;
}

}