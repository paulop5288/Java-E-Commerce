<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> <%@ page import="java.sql.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="major.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Article Details</title>
</head>
<body>
<%
	 Connection con = null;
	 Statement stmt = null;
	 ResultSet result = null;
	 boolean hasrevision =false;
	 String details="";
	 int articleID=Integer.parseInt(request.getParameter("article"));
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

String query = "select article.*,articlerevision.* from article,articlerevision  WHERE articleID="+ articleID + " AND article.articleID = articlerevision.articleid";

try {
	stmt = con.createStatement();
	result = stmt.executeQuery(query);
	while (result.next()) {
		
		details+="<tr><td>Title:</td><td>"+result.getString("title") +"</td></tr>";
		details+="<tr><td>Abstract:</td><td>"+result.getString("abstract")+"</td></tr>";
		details+="<td></td><td><a href=\""+result.getString("article_file")+"\"> Download Full Article</a></td></tr>";
		details+="<td> Revision:</td><td>" + result.getString("revision")+"</td></tr>";
		details+="<td> Revision Date:</td><td>" + result.getDate("revision_date")+"</td><tr>";

				} 
	details+="<a href=\" /stucat08/editor/acceptrevision.jsp\"> Accept Revision</a> <a href=\"editor/rejectrevision.jsp?articleid=" +articleID + "\">Reject Revision</a>";
	
}catch (SQLException e) {
	//e.printStackTrace();
	out.print(e.getMessage());
}
finally {
	try {
		if (con != null) {
			con.close();
		}

	} catch (SQLException e) {
		e.printStackTrace();
		out.println(e.getMessage());
	}
	
}
if(!hasrevision){
	details="<span class=\"markout\">This article has not been revised by the author yet</span>";
}
out.print(details);

	


%>

</body>
</html>