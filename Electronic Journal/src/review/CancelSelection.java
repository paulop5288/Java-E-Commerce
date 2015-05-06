package review;

import java.io.IOException;
import java.sql.PreparedStatement;
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
		String query = "DELETE FROM article_selection WHERE "
				+ "reviewerID = ? AND review_Article_ID = ?;";
		try {
			pstm = dbConnection.createPreparedStatement(query);
			pstm.setInt(1, reviewerID);
			pstm.setInt(2, articleID);
			int result = dbConnection.executeUpdate(pstm);
			System.out.println(result);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		resp.sendRedirect("myreview.jsp");
	}
}
