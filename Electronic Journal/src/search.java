

import java.io.IOException;



import javax.servlet.RequestDispatcher;
//import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.*;
import java.io.*;
import java.util.ArrayList;

public class search extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public search() {
        super();
       
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		res.setContentType("text/html");
		PrintWriter out= res.getWriter();
		Connection con= null;
		Statement st= null;
		ResultSet result= null;
		
		String SearchTerm= req.getParameter("searchTerm");
		String Search2= req.getParameter("search2");
		String keyword= req.getParameter("keyword");
		
		//String url="jdbc:mysql://localhost:3306/";
		//String  db="maxiproject";
		//String user= "team158";
		//String password= "9a5b309d";
		String Driver= "com.mysql.jdbc.Driver";
		String query1= "select * from publication where title='" + SearchTerm + "' and "  + "keyword = '" + keyword +  "';";
        String query2= "select * from publication where author='" + SearchTerm + "' and "  + "keyword = '" + keyword +  "';";
  
        out.print("<td><caption> Search Results :</caption></td></tr>");
     
        out.print("<table width=50% border=1>"); 
		try{
		
		Class.forName(Driver);
	    con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team158", "team158",
				"9a5b309d");
		st= con.createStatement();
		
		if(Search2.equalsIgnoreCase("Article"))
		   {result =st.executeQuery(query1);}
		
		else if (Search2.equalsIgnoreCase("Author"))
	       {result =st.executeQuery(query2);}
		
		
	    ResultSetMetaData rs=result.getMetaData();
	    int total=rs.getColumnCount(); 
	    
	    out.print("<tr>");  
	    for(int i=2;i<total;i++)  
	    { 
	    	
	    out.print("<th>"+rs.getColumnName(i)+"</th>");  
	    }  
	      
	    out.print("</tr>");
	    
	    while(result.next())  
	    {  
	    	
	    out.print("<tr><td>"+result.getString(2)+"</td><td>"+result.getString(3)+"</td><td>"+result.getString(4)+
	    		"</td><td>"+result.getString(5)+"</td><td>"+result.getString(6)+"</td><td>"+result.getString(7)+
	    		"</td><td>"+result.getString(8)+"</td><td>"+result.getString(9)+
	    		"</td><td>"+result.getString(10)+"</td></tr>");    
	                      
	    }  
	      
	    out.print("</table>");  
	                  
	    }catch (Exception e2) {e2.printStackTrace();}  
	              
	    finally{out.close();}  
	      
	    }  
	    }  
