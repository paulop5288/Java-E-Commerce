package search;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.DBConnection;
import review.Article;

public class ShowSearch extends HttpServlet {
	private int articleID;
	private String filePath = "";
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			articleID = Integer.parseInt(req.getParameter("articleID"));
		} catch (NumberFormatException e) {
			resp.sendRedirect("noresult.jsp");
			return;
		}
		System.out.println(articleID);
		DBConnection dbConnection = new DBConnection();
		PreparedStatement pstm = null;
		ResultSet resultSet = null;
		String articleQuery = "SELECT * FROM article A INNER JOIN author B ON A.authorID = B.authorID WHERE articleID = ?;";
		try {
			pstm = dbConnection.createPreparedStatement(articleQuery);
			pstm.setInt(1, articleID);
			resultSet = pstm.executeQuery();
			if (resultSet.next()) {
				ArticleDetail articleDetail = new ArticleDetail(resultSet.getInt("articleID"),resultSet.getString("title"), 
						resultSet.getString("abstract"), resultSet.getString("username"), 
						resultSet.getString("keywords"), resultSet.getString("article_file"));
				req.setAttribute("detail", articleDetail);
				req.getRequestDispatcher("readarticle.jsp").forward(req, resp);
			} else {
				resp.sendRedirect("noresult.jsp");
				return;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			dbConnection.closeConnection();
			try {
				if (pstm != null) {
					pstm.close();
				}
			
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (resultSet != null) {
					resultSet.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}	
		
	}
}
