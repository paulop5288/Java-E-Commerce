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
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("start download");
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.err.println("fail to load driver.");
		}
		
		String query = "SELECT article_file FROM article where id = ?";
		Connection dbCon = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		String id = req.getParameter("articleID");
		String fileName = "";
		// \export\tomtemp\
		String filePath = "\\export\\tomtemp\\";
		String fileType = "APPLICATION/PDF";
		try {
			// Get connection to team database
			dbCon = DriverManager.getConnection(dbServer + dbName, dbUsername,
					dbPassword);
			pstmt = dbCon.prepareStatement(query);
			pstmt.setString(1, "1");
			resultSet = pstmt.executeQuery();
			if (resultSet.next()) {
				//fileName = resultSet.getString("article_file");
				System.out.println(fileName);
			}
		} catch (Exception e) {
			System.out.println("There is an exception.");
		}
		
		
		// Find this file id in database to get file name, and file type

		// You must tell the browser the file type you are going to send
		// for example application/pdf, text/plain, text/html, image/jpg
		resp.setContentType(fileType);

		// Make sure to show the download dialog
		resp.setHeader("Content-Disposition",
				"inline; filename=\"" + fileName + "\"");

		// Assume file name is retrieved from database
		// For example D:\\file\\test.pdf

		File my_file = new File(filePath + fileName);

		// This should send the file to browser
		OutputStream out = resp.getOutputStream();
		FileInputStream in = new FileInputStream(my_file);
		byte[] buffer = new byte[4096];
		int length;
		while ((length = in.read(buffer)) > 0) {
			out.write(buffer, 0, length);
		}
		in.close();
		out.flush();
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		PrintWriter out = resp.getWriter();
        out.println("Hello World");
	}
	
	public DownloadServlet() {
		// TODO Auto-generated constructor stub
	}

}
