package review;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.DBConnection;

public class Selection {
	private int articleID, reviewerID;
	private String status, title;
	public Selection(int articleID, int reviewerID, String status, String title) {
		this.articleID = articleID;
		this.reviewerID = reviewerID;
		this.status = status;
		this.title = title;
	}
	
	public String getTitle() {
		return title;
	}
	
	public int getArticleID() {
		return articleID;
	}
	
	public String getStatus() {
		return status;
	}
	
	public int getReviewerID() {
		return reviewerID;
	}
	
	
	public static List<Selection> getSelectedArticles(int reviewerID) {
		ArrayList<Selection> selections = new ArrayList<Selection>();
		DBConnection dbConnection = new DBConnection();
		String query = "SELECT A.reviewerID, A.review_Article_ID, A.`status`, B.title FROM article_selection A inner join article B on A.review_Article_ID = B.articleID WHERE A.submitted_Article_ID = ?;";
		int unpaidArticleID = SelectReview.getUnpaidArticleID(reviewerID);
		PreparedStatement pstm = null;
		ResultSet resultSet = null;
		try {
			
			pstm = dbConnection.createPreparedStatement(query);
			pstm.setInt(1, unpaidArticleID);
			resultSet = dbConnection.executeQuery(pstm);
			while (resultSet.next()) {
				selections.add(new Selection(resultSet.getInt("review_Article_ID"), resultSet.getInt("reviewerID"), resultSet.getString("status"), resultSet.getString("title")));
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
		
		return selections;
	}
}
