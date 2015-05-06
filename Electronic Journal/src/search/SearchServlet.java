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

public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String searchOption = "", searchTerm = "", searchQuery = "";
	private String title = "", articleAbstract = "", articlePath = "", authorName = "";
	private String OtherAuthors = "", authorEmail = "", keywords = "";
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		// get parameter
		searchOption = req.getParameter("searchOpt");
		searchTerm = req.getParameter("searchTerm");
		System.out.println(searchOption + searchTerm);

		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
		String searchByTitleQuery = "select article.title from article"
				+ " where article.publication_id is Not NULL "
				+ "and where article.title LIKE '%" + searchTerm
				+ "%' OR title LIKE '" + searchTerm + "%' OR title LIKE '%"
				+ searchTerm + "'";
		String searchByKeywordQuery = "";
		String searchByYearQuery = "";
		String searchAllQuery = "select title.*";

		// query database
		DBConnection dbConnection = new DBConnection();
		ResultSet resultSet = null;
		PreparedStatement pstm = null;
		try {
			switch (searchOption) {
			case "article":
				System.out.println(searchOption);
				break;

			case "author":
				System.out.println(searchOption);
				searchQuery = this.getSearchByAuthorQuery();
				pstm = dbConnection.createPreparedStatement(searchQuery);
				pstm.setString(1, searchTerm);
				pstm.setString(2, searchTerm);
				pstm.setString(3, searchTerm);
				resultSet = dbConnection.executeQuery(pstm);
				break;

			case "year":
				System.out.println(searchOption);
				break;

			case "keyword":
				System.out.println(searchOption);
				break;

			default:
				break;
			}
		// output query result 
			if (resultSet != null && resultSet.next()) {
				
				title = resultSet.getString("title");
				articleAbstract = resultSet.getString("abstract");
				articlePath = resultSet.getString("article_file");
				authorName = resultSet.getString("firstName") + " " + resultSet.getString("lastName");
				OtherAuthors = resultSet.getString("Other_authors");
				authorEmail = resultSet.getString("username");
				keywords = resultSet.getString("keywords");
				// to page
				out.print("<hr><br><td><h3>" + "The title is: "
						+ title + "</h3></td>");

				out.print(" <h4>" + "The summary:</h4>" + "<td>"
						+ articleAbstract);
				out.print("<br><td> <a href ="
						+ articlePath
						+ ">Clik here</a> to download the PDF of the full article</td>");
				out.print(" <br><br><h4>About the author(s):</h4><br> Name(s): "
						+ "<td> " + authorName + "  </td> <br><td>      "
						+ " the keywords :" + keywords
						+ "</td><br><td> Email address:"
						+ authorEmail + " </td>");

				out.print("<br><td> <a href ='temp.jsp'>Back</a>");
	
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
	}
	
	private String getSearchByAuthorQuery() {
		return "SELECT B.title, B.keywords, A.username, A.firstName, A.lastName, "
				+ "B.Other_authors, B.abstract, B.article_file FROM author A "
				+ "INNER JOIN article B ON A.authorID = B.authorID WHERE A.firstName "
				+ "LIKE ? OR A.lastName LIKE ? OR B.Other_authors LIKE ?;";
	}
}
