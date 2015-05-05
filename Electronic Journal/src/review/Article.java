package review;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.DBConnection;

public class Article {
	
	private int articleID = 0;
	private int authorID = 0;
	private String title = "";
	private String articleAbstract = "";

	public Article(int articleID, int authorID, String title, String articleAbstract) {
		this.articleID = articleID;
		this.title = title;
		this.authorID = authorID;
		this.articleAbstract = articleAbstract;
		
	}
	
	public String getArticleAbstract() {
		return articleAbstract;
	}
	
	public String getArticleIDString() {
		return String.valueOf(articleID);
	}
	
	public String getAuthorIDString() {
		return String.valueOf(authorID);
	}
	
	
	public String getTitle() {
		return title;
	}
	
	public static int getUnpaidArticlesCount(int authorID) {
		DBConnection dbConnection = new DBConnection();
		String query = "SELECT COUNT(paid) FROM article WHERE authorID = ?;";
		PreparedStatement pstm = null;
		ResultSet resultSet = null;
		try {
			pstm = dbConnection.createPreparedStatement(query);
			pstm.setInt(1, authorID);
			resultSet = dbConnection.executeQuery(pstm);
			if (resultSet.next()) {
				return resultSet.getInt(1);
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
		return -1;
	}
	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Article " + articleID + " is written by author" + authorID + ".Named as " + title + "\n";
	}
	
}
