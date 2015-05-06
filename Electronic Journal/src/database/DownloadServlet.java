package database;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DownloadServlet extends HttpServlet {

	private String dbServer = "jdbc:mysql://stusql.dcs.shef.ac.uk/";
	private String dbName = "team158";
	private String dbUsername = "team158";
	private String dbPassword = "9a5b309d";

	private boolean fileExist = false;
	private String username = "";

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("start download");
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.err.println("fail to load driver.");
		}
		int articleID = Integer.parseInt(req.getParameter("articleID"));
		int reviewerID = Integer.parseInt(req.getParameter("reviewerID"));
		String fileQuery = "SELECT article_file FROM article where articleID = ?";
		String checkQuery = "SELECT status FROM article_selection WHERE review_Article_ID = ? AND reviewerID = ?;";
		String updateSelection = "UPDATE article_selection SET "
				+ "status = ? WHERE reviewerID = ? AND review_Article_ID = ?; ";
		String updateArticle = "UPDATE article set no_reviewer = no_reviewer + 1 where"
				+ " article.articleID = ?;";
		String updateReviewform = "INSERT INTO review (reviewID,reviewerID,articleID) VALUES"
				+ " (null,?,?);";
		
		Connection dbCon = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		String fileName = "";
		// \export\tomtemp\
		String filePath = "\\export\\tomtemp\\";
		String fileType = "APPLICATION/PDF";
		try {
			// Get connection to team database
			dbCon = DriverManager.getConnection(dbServer + dbName, dbUsername,
					dbPassword);
			pstmt = dbCon.prepareStatement(fileQuery);
			pstmt.setInt(1, articleID);
			resultSet = pstmt.executeQuery();
			if (resultSet.next()) {
				fileName = resultSet.getString("article_file");
				System.out.println(fileName);
				pstmt = dbCon.prepareStatement(checkQuery);
				pstmt.setInt(1, articleID);
				pstmt.setInt(2, reviewerID);
				resultSet = pstmt.executeQuery();
				if (resultSet.next()) {
					String status = resultSet.getString("status");
					if (status.equalsIgnoreCase("downloaded")) {
						// start downloaded
					} else if (status.equalsIgnoreCase("selected")){
						// update article
						pstmt = dbCon.prepareStatement(updateArticle);
						pstmt.setInt(1, articleID);
						int result = pstmt.executeUpdate();
						System.out.println(result);
						
						// update article_selection
						pstmt = dbCon.prepareStatement(updateSelection);
						pstmt.setString(1, "downloaded");
						pstmt.setInt(2, reviewerID);
						pstmt.setInt(3, articleID);
						result = pstmt.executeUpdate();
						System.out.println(result);
						
						// update review form
						pstmt = dbCon.prepareStatement(updateReviewform);
						pstmt.setInt(1, reviewerID);
						pstmt.setInt(2, articleID);
						result = pstmt.executeUpdate();
						System.out.println(result);
						
					} else {
						PrintWriter out = resp.getWriter();
						resp.setContentType("text/html");
						out.println("<script type=\"text/javascript\">");
						out.println("alert(\"You have submitted your review.\");");
						out.println("window.location = './myreview.jsp';");
						out.println("</script>");
					}
				}
			}
		} catch (Exception e) {
			System.out.println("There is an exception.");
		}

		/*
		 * // Find this file id in database to get file name, and file type
		 * 
		 * // You must tell the browser the file type you are going to send //
		 * for example application/pdf, text/plain, text/html, image/jpg
		 * resp.setContentType(fileType);
		 * 
		 * // Make sure to show the download dialog
		 * resp.setHeader("Content-Disposition", "inline; filename=\"" +
		 * fileName + "\"");
		 * 
		 * // Assume file name is retrieved from database // For example
		 * D:\\file\\test.pdf
		 * 
		 * File my_file = new File(filePath + fileName);
		 * 
		 * // This should send the file to browser OutputStream out =
		 * resp.getOutputStream(); FileInputStream in = new
		 * FileInputStream(my_file); byte[] buffer = new byte[4096]; int length;
		 * while ((length = in.read(buffer)) > 0) { out.write(buffer, 0,
		 * length); } in.close(); out.flush();
		 */
		resp.sendRedirect("myreview.jsp");
	}

	public DownloadServlet() {
		// TODO Auto-generated constructor stub
	}

}
