package review;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
	private String reviewerLevel = "", score = "", articleAbstract = "", contribution = "", badPoints = "", errors = "";
	private int  reviewerID, articleID;
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
		reviewerID = Integer.parseInt(req.getParameter("reviewer"));
		articleID = Integer.parseInt(req.getParameter("article"));
		
		// review form
		reviewerLevel = req.getParameter("level");
		articleAbstract = req.getParameter("abstract");
		contribution = req.getParameter("contribution");
		badPoints = req.getParameter("badpoint");
		errors = req.getParameter("error");
		score = req.getParameter("score");
		
		DBConnection dbConnection = new DBConnection();
		PreparedStatement pstm = null;
		try {
			//update review table
			pstm = dbConnection.createPreparedStatement("UPDATE review SET"
					+ " level = ?,score = ?, abstract = ?, contribution = ?,"
					+ " badpoint = ?, error = ?, status = ? WHERE reviewerID = ?"
					+ " AND articleID = ?;");
			
			pstm.setString(1, reviewerLevel);
			pstm.setString(2, score);
			pstm.setString(3, articleAbstract);
			pstm.setString(4, contribution);
			pstm.setString(5, badPoints);
			pstm.setString(6, errors);
			pstm.setBoolean(7, true);
			pstm.setInt(8, reviewerID);
			pstm.setInt(9, articleID);
			int updateResult = dbConnection.executeUpdate(pstm);
			if (updateResult == 1) {
				// update article_selection table
				pstm = dbConnection.createPreparedStatement("UPDATE article_selection SET "
						+ "status = \"reviewed\" WHERE reviewerID = ? AND review_Article_ID = ?;");
				pstm.setInt(1, reviewerID);
				pstm.setInt(2, articleID);
				updateResult = pstm.executeUpdate();
				System.out.println(updateResult);
				
				// get updatedtable submitted_Article_ID
				pstm = dbConnection.createPreparedStatement("SELECT COUNT(status), submitted_Article_ID FROM "
						+ "article_selection WHERE submitted_Article_ID = "
						+ "(SELECT submitted_Article_ID FROM article_selection "
						+ "WHERE reviewerID = ? AND review_Article_ID = ?) "
						+ "AND status = \"reviewed\";");
				pstm.setInt(1, reviewerID);
				pstm.setInt(2, articleID);
				ResultSet resultSet = pstm.executeQuery();
				int reviewer_Article_ID = 0;
				int count = 0;
				if (resultSet.next()) {
					reviewer_Article_ID = resultSet.getInt("submitted_Article_ID");
					count = resultSet.getInt("COUNT(status)");
				}
				if (count == 3) {
					// update article table for reviewer
					pstm = dbConnection.createPreparedStatement("UPDATE article SET "
						+ "paid = ? WHERE articleID = ?;");
					pstm.setBoolean(1, true);
					pstm.setInt(2, reviewer_Article_ID);
					updateResult = pstm.executeUpdate();
					System.out.println(updateResult);				
				}
				// return all
				out = resp.getWriter();
				resp.setContentType("text/html");
				out.println("<script type=\"text/javascript\">");
				out.println("alert(\"Thank you for submitting the review.\");");
				out.println("window.location = '/Electronic%20Journal/myreview.jsp';");
				out.println("</script>");
				
			}
			dbConnection.closeConnection();			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public static List<Article> getDownloadedArticles(int reviewerID) {
		List<Article> articles = new ArrayList<Article>();
		String query = "SELECT * FROM article A inner join review B on A.articleID = B.articleID WHERE B.reviewerID = ? AND B.`status` = false;";
		DBConnection dbConnection = new DBConnection();
		PreparedStatement pstm = null;
		ResultSet resultSet = null;
		try {
			pstm = dbConnection.createPreparedStatement(query);
			pstm.setInt(1, reviewerID);
			resultSet = dbConnection.executeQuery(pstm);
			while (resultSet.next()) {
				int articleID = resultSet.getInt("articleID");
				int authorID = resultSet.getInt("authorID");
				String title = resultSet.getString("title");
				String articleAbstract = resultSet
						.getString("abstract");
				articles.add(new Article(articleID, authorID, title,
						articleAbstract));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
		
		return articles;
	}

}
