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

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String articleIDString = req.getParameter("article");
		if (articleIDString != null) {
			reviewerID = Integer.parseInt(req.getParameter("reviewer"));
			DBConnection dbConnection = new DBConnection();
			PreparedStatement pstm = null;
			String query = "INSERT INTO article_selection (reviewerID,articleID,status) VALUES "
					+ "(?,?,?);";
			// String updateArticle =
			// "UPDATE article set no_reviewer = no_reviewer + 1 where"
			// + " article.articleID = ?;";
			String[] articleIDs = req.getParameterValues("article");
			for (String articleID : articleIDs) {
				System.out.println(articleID);
				try {
					pstm = dbConnection.createPreparedStatement(query);
					pstm.setInt(1, reviewerID);
					pstm.setInt(2, Integer.parseInt(articleID));
					pstm.setString(3, "selected");
					int count = dbConnection.executeUpdate(pstm);
					if (count == 0) {
						System.out.println("failed to insert.");
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			resp.setContentType("text/html");
			resp.sendRedirect("myreview.jsp");
		} else {
			resp.setContentType("text/html");
			PrintWriter out = resp.getWriter();
			out.println("<html><body>");
			out.println("You have not select any article.");
			out.println("</body></html>");
		}
	}

	public static List<Article> getArticlesForReview(int reviewerID) {
		DBConnection dbConnection = new DBConnection();
		PreparedStatement pstm = null;
		List<Article> articles = new ArrayList<Article>();
		String getArticleQuery = "select article.articleID, article.authorID, article.title, article.abstract from article "
				+ "where article.articleID not in "
				+ "(select review.articleID from review where review.reviewerID = ?) "
				+ "and article.authorID != ? "
				+ "and article.passed_review + article.no_reviewer <= ?;";
		try {

			if (SelectReview.checkNumberOfReview(reviewerID)) {
				pstm = dbConnection.createPreparedStatement(getArticleQuery);
				pstm.setInt(1, reviewerID);
				pstm.setInt(2, reviewerID);
				pstm.setInt(3, 5);
				ResultSet resultSet = dbConnection.executeQuery(pstm);
				for (int i = 0; i < 5; i++) {
					if (resultSet.next()) {
						int articleID = resultSet.getInt("articleID");
						int authorID = resultSet.getInt("authorID");
						String title = resultSet.getString("title");
						String articleAbstract = resultSet
								.getString("abstract");
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

	public static boolean checkNumberOfReview(int reviewerID) {
		String checkReviewQuery = "SELECT COUNT(review.reviewID) from review where review.reviewerID = ?;";
		DBConnection dbConnection = new DBConnection();
		PreparedStatement pstm = null;
		try {
			pstm = dbConnection.createPreparedStatement(checkReviewQuery);
			pstm.setInt(1, reviewerID);
			ResultSet resultSet = dbConnection.executeQuery(pstm);
			int count = 0;
			if (resultSet.next()) {
				count = resultSet.getInt("COUNT(review.reviewID)");
			}
			if (count < 3) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	public static boolean isReviewer(int reviewerID) {
		int unpaid = Article.getUnpaidArticleS(reviewerID);
		int credit = 0;
		DBConnection dbConnection = new DBConnection();
		PreparedStatement pstm = null;
		String query = "SELECT credit FROM author WHERE authorID = ?;";
		try {
			pstm = dbConnection.createPreparedStatement(query);
			pstm.setInt(1, reviewerID);
			ResultSet resultSet = dbConnection.executeQuery(pstm);
			if (resultSet.next()) {
				credit = resultSet.getInt("credit");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

		if ((credit - 3 * unpaid) < 0) {
			return true;
		}
		return false;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);
	}

}
