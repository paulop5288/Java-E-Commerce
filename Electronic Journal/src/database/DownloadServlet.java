package database;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DownloadServlet extends HttpServlet {

	private String role = "", callbackURL = "", filePath = null;
	private int articleID = 0;
	private boolean allowDownload = false;
	

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("start download");
		resp.setContentType("text/html");
		
		
		// get parameters
		role = req.getParameter("role");
		articleID = Integer.parseInt(req.getParameter("articleID"));

		// setup database
		DBConnection dbCon = new DBConnection();
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;

		try {
			switch (role) {
			case "reviewer":
				String status = "";
				int reviewerID = Integer.parseInt(req
						.getParameter("reviewerID"));
				String checkQuery = "SELECT status FROM article_selection WHERE review_Article_ID = ? AND reviewerID = ?;";
				String updateSelection = "UPDATE article_selection SET "
						+ "status = ? WHERE reviewerID = ? AND review_Article_ID = ?; ";
				String updateArticle = "UPDATE article set no_reviewer = no_reviewer + 1 where"
						+ " article.articleID = ?;";
				String updateReviewform = "INSERT INTO review (reviewID,reviewerID,articleID) VALUES"
						+ " (null,?,?);";
				// check status

				pstmt = dbCon.createPreparedStatement(checkQuery);

				pstmt.setInt(1, articleID);
				pstmt.setInt(2, reviewerID);
				resultSet = dbCon.executeQuery(pstmt);
				if (resultSet.next()) {
					status = resultSet.getString("status");
				}
				if (status.equalsIgnoreCase("selected")) {
					// update article_selection
					pstmt = dbCon.createPreparedStatement(updateSelection);
					pstmt.setString(1, "downloaded");
					pstmt.setInt(2, reviewerID);
					pstmt.setInt(3, articleID);
					int updateResult = pstmt.executeUpdate();

					// update article
					pstmt = dbCon.createPreparedStatement(updateArticle);
					pstmt.setInt(1, articleID);
					updateResult = pstmt.executeUpdate();

					// update reviewform
					pstmt = dbCon.createPreparedStatement(updateReviewform);
					pstmt.setInt(1, reviewerID);
					pstmt.setInt(2, articleID);
					updateResult = pstmt.executeUpdate();

					allowDownload = true;
				} else if (status.equalsIgnoreCase("downloaded")) {
					allowDownload = true;
				} else {
					allowDownload = false;
					PrintWriter out = resp.getWriter();
					out = resp.getWriter();
					resp.setContentType("text/html");
					out.println("<script type=\"text/javascript\">");
					out.println("alert(\"You have submitted the review.\");");
					out.println("window.location = 'review/myreview.jsp';");
					out.println("</script>");
				}
				break;
			case "editor":
				allowDownload = true;
				break;
			case "reader":
				allowDownload = true;
				break;
			default:
				break;
			}

			if (allowDownload) {
				String downloadQuery = "SELECT article_file FROM article WHERE articleID = ?; ";
				pstmt = dbCon.createPreparedStatement(downloadQuery);
				pstmt.setInt(1, articleID);
				resultSet = pstmt.executeQuery();
				if (resultSet.next()) {
					filePath = resultSet.getString("article_file");
				}
				
				if (filePath != null) {
					String fileType = "application/pdf";
					String tempPath = "C:/Users/Paul/Desktop/Assignment2014-15.pdf";
					String fileName = tempPath.substring(tempPath.lastIndexOf("/") + 1);
			         // Find this file id in database to get file name, and file type

			         // You must tell the browser the file type you are going to send
			         // for example application/pdf, text/plain, text/html, image/jpg
			         resp.setContentType(fileType);

			         // Make sure to show the download dialog
			         resp.setHeader("Content-disposition","attachment; filename="+fileName);

			         // Assume file name is retrieved from database
			         // For example D:\\file\\test.pdf

			         File my_file = new File(tempPath);

			         // This should send the file to browser
			         OutputStream stream = resp.getOutputStream();
			         FileInputStream in = new FileInputStream(my_file);
			         byte[] buffer = new byte[4096];
			         int length;
			         while ((length = in.read(buffer)) > 0){
			        	 stream.write(buffer, 0, length);
			         }
			         in.close();
			         stream.flush();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(filePath);
	}

	public DownloadServlet() {
		// TODO Auto-generated constructor stub
	}

}
