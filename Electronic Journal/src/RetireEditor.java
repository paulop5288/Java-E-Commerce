
import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import java.sql.*;

public class RetireEditor extends HttpServlet {
	private String message="",username="",password="";
	Connection con = null;
	Statement stmt = null;
	ResultSet resultSet = null;
	boolean userIsMatched = false;
	String lastName = null;
	String organisation = null;
	String title = null;
	PrintWriter out=null;
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		
		out = res.getWriter();
		username = req.getParameter("username");
		password = req.getParameter("password");
		if (username.trim().compareTo("") == 0) {
			message="Username cannot be empty.</body></html>";
			out.println(message);
			return;
		}
		if (password.trim().compareTo("") == 0) {
			message="Username cannot be empty.</body></html>";
			out.println(message);
			return;
		}
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.err.println("fail to load driver.");
		}
		
		

						//Start session management here
		HttpSession session = req.getSession(true);
		userIsMatched= this.isExistsEditor();
		
		if (userIsMatched) {
			res.setContentType("text/html");
			boolean checkother = this.isExistsOtherEditor();
			if(!checkother){
				out.println("<html><body>");
				out.println("Sorry, you cannot retire as you are the only ediotr left. Please, appoint a new editor before retiring.");
				out.println("</body></html>");
				return;
			}
			boolean retired = this.retireEditor();
			if(retired){
				out.println("<html><body>");
				out.println("Thanks for your Service.<br/> You have successfully retired as an editor of IJSE.");
				out.println("</body></html>");
				
			}
			else{
				out.println("<html><body>");
				out.println("System Error:<br/> Your retirement as an editor of IJSE was NOT successful.");
				out.println("</body></html>");
				
			}
			
		} else {
			res.setContentType("text/html");
			out.println("<html><body>");
			out.println("Login failed: Invalid username or password Or Inactive editor.");
			out.println("</body></html>");
			System.out.println("failed.");
		}
	}
	
	//Check if this editor exists and is really active
	boolean isExistsEditor(){
		boolean exists=false;
		try {
			con = DriverManager.getConnection(
					"jdbc:mysql://stusql.dcs.shef.ac.uk/team158", "team158",
					"9a5b309d");
			String query = "select * from editor WHERE username = '" + username
					+ "' AND " + "password = '" + password + "' AND status='active';";
			stmt = con.createStatement();
			out.print("connection successful");
			resultSet = stmt.executeQuery(query);
			if (resultSet.next()) {
				
				exists = true;
				
			} 
		} catch (SQLException e1) {
			e1.printStackTrace();
			out.println(e1.getMessage());
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}

			} catch (SQLException e) {
				e.printStackTrace();
				out.println(e.getMessage());
			}
			try {
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
				out.println(e.getMessage());
			}
		}
		return exists;
	}
	
	//Check if any other active editor exists apart from current editor
	boolean isExistsOtherEditor(){
		boolean exists=false;
		try {
			con = DriverManager.getConnection(
					"jdbc:mysql://stusql.dcs.shef.ac.uk/team158", "team158",
					"9a5b309d");
			String query = "select * from editor WHERE username <> '" + username
					+ "' AND status='active'";
			stmt = con.createStatement();
			resultSet = stmt.executeQuery(query);
			if (resultSet.next()) {
				
				exists = true;
				
			} 
		} catch (SQLException e1) {
			out.println(e1.getMessage());
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}

			} catch (SQLException e) {
				e.printStackTrace();
				out.println(e.getMessage());
			}
			try {
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
				out.println(e.getMessage());
			}
		}
		return exists;
	}
	
	//Retire an Editor
	boolean retireEditor(){
		boolean isRetired=false;
		try {
			con = DriverManager.getConnection(
					"jdbc:mysql://stusql.dcs.shef.ac.uk/team158", "team158",
					"9a5b309d");
			String query = "UPDATE editor set status='inactive' WHERE username ='" + username + "'";
			stmt = con.createStatement();
			int count=0;
			count = stmt.executeUpdate(query);
			if (count>0) {
				
				isRetired = true;
				
			} 
		} catch (SQLException e1) {
			out.println(e1.getMessage());
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}

			} catch (SQLException e) {
				e.printStackTrace();
				out.println(e.getMessage());
			}
			try {
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
				out.println(e.getMessage());
			}
		}
		return isRetired;
		
	}
	
	
}

