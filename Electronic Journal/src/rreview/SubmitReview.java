package rreview;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import database.*;


public class SubmitReview extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1575704856800198225L;
	private String reviewerLevel = "", articleAbstract = "", contribution = "", badPoints = "", errors = "";
	private int  authorID, articleID;
	public SubmitReview() {
		super();
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.err.println("fail to load driver.");
		}
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		
		// should get from user
		authorID = 1;
		articleID = 1;
		
		// review form
		reviewerLevel = req.getParameter("level");
		articleAbstract = req.getParameter("abstract");
		contribution = req.getParameter("contribution");
		badPoints = req.getParameter("badpoint");
		errors = req.getParameter("error");
		
		DBConnection dbConnection = new DBConnection();
		PreparedStatement pstm = null;
		try {
			pstm = dbConnection.createPreparedStatement("INSERT INTO review VALUES (null,?,?,?,?,?,?,?);");
			pstm.setInt(1, authorID);
			pstm.setInt(2, articleID);
			pstm.setString(3, reviewerLevel);
			pstm.setString(4, articleAbstract);
			pstm.setString(5, contribution);
			pstm.setString(6, badPoints);
			pstm.setString(7, errors);
			
			int updateResult = dbConnection.executeUpdate(pstm);
			System.out.println(updateResult);
			dbConnection.closeConnection();			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		out.println("<html><body>");
		out.println(reviewerLevel);
		out.println(articleAbstract);
		out.println(contribution);
		out.println(badPoints);
		out.println(errors);
		out.println("</body></html>");
	}
}
