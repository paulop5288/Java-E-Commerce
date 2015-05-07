<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> <%@ page import="java.sql.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="major.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Reject Review</title>
</head>

<body>


<%
	 Connection con = null;
	 Statement stmt = null;
	 PreparedStatement pstmt = null;
	 String message="";
	 int reviewID=Integer.parseInt(request.getParameter("review"));
	 
	 try{
			con = DriverManager.getConnection(
					"jdbc:mysql://stusql.dcs.shef.ac.uk/team158", "team158",
					"9a5b309d");
			
			
		}
		catch(Exception e){
			
			//e.printStackTrace();
			out.print(e.getMessage());
			return;
		}

		int count =0;
		try{
			pstmt = con
				.prepareStatement("UPDATE review SET accept='no' WHERE reviewID="+ reviewID);
		
			count = pstmt.executeUpdate();
		}
		catch (SQLException ex) {

			ex.getMessage();
			out.println("<span class=\"error\">Could not register review rejection: </span>" + ex.getMessage());
			return;
		}

		finally {
			if (con != null)
				try {
					con.close();
				} catch (SQLException ex) {
				}
		}

if(count >0){
	out.println("<span class=\"success\">Review Rejected Successfully. </span>");
}

%>

</body>
</html>