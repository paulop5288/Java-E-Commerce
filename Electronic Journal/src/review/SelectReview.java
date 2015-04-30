package review;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.DBConnection;
import database.DownloadServlet;
import review.*;

public class SelectReview extends HttpServlet {
	private int articleID = 0, reviewerID = 0;
	public SelectReview() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
//		articleID = Integer.parseInt(req.getParameter("article"));
//		reviewerID = Integer.parseInt(req.getParameter("userID"));
		articleID = 1;
		reviewerID = 1;
		DBConnection dbConnection = new DBConnection();
		PreparedStatement pstm = null;
		String query = "INSERT INTO review (reviewID,reviewerID,articleID,status) VALUES "
				+ "(null,?,?,?);";
		try {
			pstm = dbConnection.createPreparedStatement(query);
			pstm.setInt(1, reviewerID);
			pstm.setInt(2, articleID);
			pstm.setBoolean(3, false);
			int count = dbConnection.executeUpdate(pstm);
			if (count == 0) {
				System.out.println("failed to insert.");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		out.println("<html><body>");
		out.println("The article selected is article " + articleID);
		out.println("</body></html>");
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);
	}
	
	public static List<Article> getArticlesForReview() {
		DBConnection dbConnection = new DBConnection();
		PreparedStatement pstm = null;
		List<Article> articles = new ArrayList<Article>();
		try {
			pstm = dbConnection.createPreparedStatement("select * from article order by submissionDate ASC;");
			ResultSet resultSet = dbConnection.executeQuery(pstm);
			for (int i = 0; i < 5; i++) {
				if (resultSet.next()) {
					String articleID = resultSet.getString("articleID");
					String authorID = resultSet.getString("authorID");
					String title = resultSet.getString("title");
					String articleAbstract = resultSet.getString("abstract");
					articles.add(new Article(articleID, authorID, title, articleAbstract));
				}
			}			
			dbConnection.closeConnection();			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println(articles);
		return articles;
	}
	
	public static void main(String[] args) {
		SelectReview review = new SelectReview();
		review.getArticlesForReview();
	}
}