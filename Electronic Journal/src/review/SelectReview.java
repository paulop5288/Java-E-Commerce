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
		articleID = Integer.parseInt(req.getParameter("article"));
		reviewerID = 3;
		// reviewerID = Integer.parseInt(req.getParameter("userID"));
		DBConnection dbConnection = new DBConnection();
		PreparedStatement pstm = null;
		String query = "INSERT INTO review (reviewID,reviewerID,articleID,status) VALUES "
				+ "(null,?,?,?);";
		String updateArticle = "UPDATE article set no_reviewer = no_reviewer + 1 where"
				+ " article.articleID = ?;";
		try {
			pstm = dbConnection.createPreparedStatement(query);
			pstm.setInt(1, reviewerID);
			pstm.setInt(2, articleID);
			pstm.setBoolean(3, false);
			int count = dbConnection.executeUpdate(pstm);
			if (count == 0) {
				System.out.println("failed to insert.");
			} else {
				pstm = dbConnection.createPreparedStatement(updateArticle);
				pstm.setInt(1, articleID);
				count = dbConnection.executeUpdate(pstm);
				if (count == 0) {
					System.out.println("failed to update");
				}
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
	
	public static List<Article> getArticlesForReview(int reviewerID) {
		DBConnection dbConnection = new DBConnection();
		PreparedStatement pstm = null;
		List<Article> articles = new ArrayList<Article>();
		String checkReviewQuery = "SELECT COUNT(review.reviewID) from review where review.reviewerID = ?;";
		String getArticleQuery = "select article.articleID, article.authorID, article.title, article.abstract from article "
				+ "where article.articleID not in "
				+ "(select review.articleID from review where review.reviewerID = ?) "
				+ "and article.authorID != ? "
				+ "and article.passed_review + article.no_reviewer <= ?;";
		try {
			pstm = dbConnection
					.createPreparedStatement(checkReviewQuery);
			pstm.setInt(1, reviewerID);
			ResultSet resultSet = dbConnection.executeQuery(pstm);
			int count = 0;
			if (resultSet.next()) {
				count = resultSet.getInt("COUNT(review.reviewID)");
			}
			if (count < 3) {
				pstm = dbConnection
						.createPreparedStatement(getArticleQuery);
				pstm.setInt(1, reviewerID);
				pstm.setInt(2, reviewerID);
				pstm.setInt(3, 5);
				resultSet = dbConnection.executeQuery(pstm);
				for (int i = 0; i < 5; i++) {
					if (resultSet.next()) {
						int articleID = resultSet.getInt("articleID");
						int authorID = resultSet.getInt("authorID");
						String title = resultSet.getString("title");
						String articleAbstract = resultSet.getString("abstract");
						articles.add(new Article(articleID, authorID, title,
								articleAbstract));
					}
				}
			}

			dbConnection.closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		System.out.println(articles);
		return articles;
	}
	

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);
	}



}
