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

String query = "select article.*,review.* from article,review  WHERE articleID="+ articleID + " AND article.id = review.articleID";

try {
	stmt = con.createStatement();
	result = stmt.executeQuery(query);
	if (result.next()) {
		
		details+="<td>Title:</td>"+result.getString("title");
		details+="<td>Abstract:</td><td>"+result.getString("abstract");
		details+="<td></td><td><a href=\""+result.getString("article_file")+"\"> Download Full Article</a></td>";
		details+="<td> No. of Reviews:</td><td>" + result.getInt("no_reviewer")+"</td>";
		details+="<td> No. of Author's Reviews:</td><td>" + result.getInt("author_review")+"</td>";
				} 
	details+="<a href=\" Review.jsp\"> Review Article</a> <a href=\"editor/publish.jsp\">Publish Article</a><a href=\"editor/reject.jsp?articleid=" +articleID + "\">Reject Article</a>";
	
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

out.print(details);

	


%>

</body>
</html>