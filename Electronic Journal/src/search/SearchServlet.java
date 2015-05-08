package search;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
//import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.DBConnection;

import java.sql.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String searchOption = "", searchTerm = "", searchQuery = "";
	private String title = "", articleAbstract = "", articlePath = "", authorName = "";
	private String OtherAuthors = "", authorEmail = "", keywords = "";
	private int articleID = 0;
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.sendRedirect(req.getContextPath() + "/index.jsp");
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		// get parameter
		searchOption = req.getParameter("searchOpt");
		searchTerm = req.getParameter("searchTerm");
		System.out.println(searchOption + searchTerm);
		
		List<SearchResult> searchResults = new ArrayList<SearchResult>();
		
		boolean isYear = false;
		res.setContentType("text/html");

		if (searchOption.equalsIgnoreCase("year")) {
			try {
				Integer.parseInt(searchTerm);
				if (searchTerm.length() != 4) {
					isYear = false;
				} else {
					isYear = true;
				}
			} catch (NumberFormatException e) {
				isYear = false;
			}
		}
		
		
		// query database
		DBConnection dbConnection = new DBConnection();
		ResultSet resultSet = null;
		PreparedStatement pstm = null;
		try {
			switch (searchOption) {
			case "article":
				System.out.println(searchOption);
				searchQuery = this.getSearchByTitleQuery();
				pstm = dbConnection.createPreparedStatement(searchQuery);
				pstm.setString(1, "%" + searchTerm + "%");
				break;

			case "author":
				System.out.println(searchOption);
				searchQuery = this.getSearchByAuthorQuery();
				pstm = dbConnection.createPreparedStatement(searchQuery);
				pstm.setString(1, "%" + searchTerm + "%");
				pstm.setString(2, "%" + searchTerm + "%");
				pstm.setString(3, "%" + searchTerm + "%");
				break;

			case "year":
				if (isYear) {
					System.out.println(searchOption);
					searchQuery = this.getSearchByDateQuery();
					pstm = dbConnection.createPreparedStatement(searchQuery);
					pstm.setString(1, searchTerm + "-01-01");
					pstm.setString(2, searchTerm + "-12-31");
				} else {
					res.sendRedirect(req.getContextPath() + "/noresult.jsp");
					return;
				}
				
				break;

			case "keyword":
				System.out.println(searchOption);
				searchQuery = this.getSearchByKeywordsQuery();
				pstm = dbConnection.createPreparedStatement(searchQuery);
				pstm.setString(1, "%" + searchTerm + "%");
				break;

			default:
				searchQuery = this.getAllQuery();
				pstm = dbConnection.createPreparedStatement(searchQuery);
				break;
			}
			System.out.println(pstm);
			resultSet = pstm.executeQuery();
		// output query result 
			while (resultSet != null && resultSet.next()) {
				
				title = resultSet.getString("title");
				System.out.println(title);
				articleID = Integer.parseInt(resultSet.getString("articleID"));
				authorName = resultSet.getString("firstName") + " " + resultSet.getString("lastName");
				// to page
				searchResults.add(new SearchResult(articleID ,title, authorName));
			}
			
		} catch (SQLException e) {
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
		System.out.println(searchResults.size());
		req.setAttribute("results", searchResults);
		req.setAttribute("search", "search");
		req.getRequestDispatcher("search.jsp").forward(req, res);
	}
	
	private String getSearchByAuthorQuery() {
		return "SELECT B.articleID, B.title, A.firstName, A.lastName FROM author A "
				+ "INNER JOIN article B ON A.authorID = B.authorID WHERE (A.firstName "
				+ "LIKE ? OR A.lastName LIKE ? OR B.Other_authors LIKE ?) AND "
				+ "B.publication_id IS NOT NULL;";
	}
	private String getSearchByKeywordsQuery() {
		return "SELECT B.articleID, B.title, A.firstName, A.lastName FROM author A "
				+ "INNER JOIN article B ON A.authorID = B.authorID WHERE B.keywords "
				+ "LIKE ? AND B.publication_id IS NOT NULL;";
	}
	private String getSearchByDateQuery() {
		return "SELECT B.articleID, B.title, A.firstName, A.lastName FROM author A "
				+ "INNER JOIN article B ON A.authorID = B.authorID WHERE "
				+ "(publicationDate BETWEEN ? AND ?) AND B.publication_id IS NOT NULL;";
	}
	private String getSearchByTitleQuery() {
		return "SELECT B.articleID, B.title, A.firstName, A.lastName FROM author A "
				+ "INNER JOIN article B ON A.authorID = B.authorID WHERE "
				+ "B.title LIKE ? AND B.publication_id IS NOT NULL;";
	}
	private String getAllQuery() {
		return "SELECT B.articleID, B.title, A.firstName, A.lastName FROM author A "
				+ "INNER JOIN article B ON A.authorID = B.authorID WHERE "
				+ "B.publication_id IS NOT NULL;";
	}
}
