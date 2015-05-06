package review;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.DBConnection;

public class CancelSelection extends HttpServlet {

	private int reviewerID = 0, articleID = 0;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		reviewerID = Integer.parseInt(req.getParameter("reviewerID"));
		articleID = Integer.parseInt(req.getParameter("articleID"));
		DBConnection dbConnection = new DBConnection();
		PreparedStatement pstm = null;
		ResultSet resultSet = null;
		String checkQuery = "SELECT status FROM article_selection WHERE reviewerID = ? AND"
				+ " review_Article_ID = ? AND status = ?;";
		String query = "DELETE FROM article_selection WHERE "
				+ "reviewerID = ? AND review_Article_ID = ?;";
		try {
			// check if the article has been downloaded or not
			pstm = dbConnection.createPreparedStatement(checkQuery);
			pstm.setInt(1, reviewerID);
			pstm.setInt(2, articleID);
			pstm.setString(3, "selected");
			resultSet = pstm.executeQuery();
			if (resultSet.next()) {
				System.out.println(pstm.toString());
				pstm = dbConnection.createPreparedStatement(query);
				pstm.setInt(1, reviewerID);
				pstm.setInt(2, articleID);
				int result = dbConnection.executeUpdate(pstm);
				resp.sendRedirect("myreview.jsp");
				return;
			} else {
				PrintWriter out = resp.getWriter();
				resp.setContentType("text/html");
				out.println("<script type=\"text/javascript\">");
				out.println("alert(\"You cannot cancel the paper that you are reviewing.\");");
				out.println("window.location = 'myreview.jsp';");
				out.println("</script>");
			}
			

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
