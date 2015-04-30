

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * Servlet implementation class SignUp
 */

public class publicationForm extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public publicationForm() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse res)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		Connection con= null;
	    PreparedStatement ps = null;
        String title = req.getParameter("title");
        String year = req.getParameter("year");
        String volume = req.getParameter("volume");
        String Number = req.getParameter("Number");
        String pub_index  = req.getParameter("pub_index");
        String Numbers_of_pages = req.getParameter("Number_of_pages");
        String Editors = req.getParameter("Editors");
        String keyword = req.getParameter("Keyword");
        String Editor_nots = req.getParameter("message");
		PrintWriter out = res.getWriter();
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.err.println("fail to load driver.");
		}
		try{
		con = DriverManager.getConnection
				("jdbc:mysql://stusql.dcs.shef.ac.uk/team158","team158","9a5b309d");
	    ps = con.prepareStatement("INSERT INTO publication VALUES(null, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		ps.setString(1, title);
		ps.setString(2, year);
		ps.setString(3, volume);
		ps.setString(4, Number);
		ps.setString(5, pub_index);
		ps.setString(6, Numbers_of_pages);
		ps.setString(7, Editors);
		ps.setString(8, keyword);
		ps.setString(9, Editor_nots);
		int i = ps.executeUpdate();
        if(i!=0)
            out.println("The Form has been submitted successfully...");
            
          else
            out.println("Submission Unsuccessful");
		ps.close();
		con.close();}
		catch(SQLException e){
			e.printStackTrace();
			System.err.println("error");
					}
		// TODO Auto-generated method stub
	}

}
