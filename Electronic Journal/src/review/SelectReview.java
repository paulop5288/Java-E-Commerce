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
		reviewerID = Integer.parseInt(req.getParameter("reviewer"));
		String[] articleIDs = req.getParameterValues("article");
		int selected = Selection.getSelectedArticles(reviewerID).size();
		if (articleIDs != null && articleIDs.length + selected <= 3) {
			
			DBConnection dbConnection = new DBConnection();
			PreparedStatement pstm = null;
			String query = "INSERT INTO article_selection (submitted_Article_ID,review_Article_ID,reviewerID,reviewformID,status) VALUES "
					+ "(?,?,?,NULL,?);";
			// String updateArticle =
			// "UPDATE article set no_reviewer = no_reviewer + 1 where"
			// + " article.articleID = ?;";
			
			for (String articleID : articleIDs) {
				System.out.println(articleID);
				try {

					pstm = dbConnection.createPreparedStatement(query);
					pstm.setInt(1, SelectReview.getUnpaidArticleID(reviewerID));
					pstm.setInt(2, Integer.parseInt(articleID));
					pstm.setInt(3, reviewerID);
					pstm.setString(4, "selected");
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
			PrintWriter out = resp.getWriter();
			resp.setContentType("text/html");
			out.println("<script type=\"text/javascript\">");
			out.println("alert(\"You can only select 3 articles for reviewing.\");");
			out.println("window.location = '/Electronic%20Journal/selectreview.jsp';");
			out.println("</script>");
		}
	}

	public static List<Article> getArticlesForReview(int reviewerID) {
		DBConnection dbConnection = new DBConnection();
		PreparedStatement pstm = null;
		List<Article> articles = new ArrayList<Article>();
		String getArticleQuery = "SELECT article.articleID, article.authorID, article.title,"
				+ " article.abstract FROM article WHERE article.articleID NOT IN "
				+ "(SELECT A.articleID FROM article A INNER JOIN article_selection B "
				+ "ON A.articleID = B.review_Article_ID WHERE B.reviewerID = ?) "
				+ "AND article.authorID != ? AND article.passed_review + article.no_reviewer <= 5;";
		try {

			if (SelectReview.checkNumberOfReview(reviewerID)) {
				pstm = dbConnection.createPreparedStatement(getArticleQuery);
				pstm.setInt(1, reviewerID);
				pstm.setInt(2, reviewerID);
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

	public static int getUnpaidArticleID(int reviewerID) {
		DBConnection dbConnection = new DBConnection();
		PreparedStatement pstm = null;
		ResultSet resultSet = null;
		String query = "SELECT articleID FROM article WHERE authorID = ? AND paid = false;";
		try {
			pstm = dbConnection.createPreparedStatement(query);
			pstm.setInt(1, reviewerID);
			resultSet = pstm.executeQuery();
			if (resultSet.next()) {
				return resultSet.getInt("articleID");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbConnection.closeConnection();
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pstm != null) {
				try {
					pstm.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return 0;
	}

	public static boolean isReviewer(int reviewerID) {
		int unpaid = Article.getUnpaidArticlesCount(reviewerID);
		if (unpaid > 0) {
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

	public static void main(String[] args) {
		System.out.println(SelectReview.isReviewer(3));
	}

}
