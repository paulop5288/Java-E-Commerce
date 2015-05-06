<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="major.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Team8 -Article Status</title>
</head>
<body>
<div id="maincontent">
	<%
	Connection con=null;
    Statement st=null;
    ResultSet result=null;
    String editordashboard="<table width=\"100%\" border=\"1\">";
    int serial=0;
    try {
		Class.forName("com.mysql.jdbc.Driver");
	} catch (ClassNotFoundException e) {
		out.println("Failed to load driver.");
		return;
	}
    try{
		con = DriverManager.getConnection(
				"jdbc:mysql://stusql.dcs.shef.ac.uk/team158", "team158",
				"9a5b309d");
		
	}
	catch(Exception e){
		
		out.println(e.getMessage());
		return;
	}
    editordashboard +="<thead><th>Serial</th><th>Title</th><th></th>";
    String query = "select * from article";
    editordashboard +="<tbody>";
		try {
			st = con.createStatement();
			result = st.executeQuery(query);
			while(result.next()){
	             
				editordashboard +="<tr><td>" + ++serial+ "</td><td>"+result.getString("title")+"</td><td><td><a href=\"ArticleDetails.jsp?article=" +result.getInt("articleID")+ "&author="+result.getInt("authorID")+ "\" >View Details </a>" +"</tr>";
				
			}
		} catch (SQLException e) {
			
			out.println("SQL ERROR:" + e.getMessage());
			return;
		}
		finally{
			con.close();
		}
		editordashboard+="</tbody></table>";
		out.println(editordashboard);	
	%>

</div>

</body>
</html>