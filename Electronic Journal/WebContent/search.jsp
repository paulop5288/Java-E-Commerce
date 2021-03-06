<%@page import="java.util.ArrayList"%>
<html>
<head>
<meta http-equiv="Content-Type"
	content="text/html; charset=windows-1252">

<title>Team8 - E-Journal Site</title>
<link rel="stylesheet" type="text/css" href="major.css">
</head>
<body>
	<%@ page import="java.util.Date"%>
	<%@ page import="java.util.List"%>
	<%@ page import="review.*"%>
	<%@ page import="database.*"%>
	<%@ page import="search.*"%>
	<% 
	ArrayList<SearchResult> results = (ArrayList<SearchResult>)request.getAttribute("results");
	String key = (String)request.getAttribute("search");
	if (key == null || results == null) {
		response.sendRedirect(request.getContextPath() + "/index.jsp");
		return;
	}
	
	%>
	<div id="banner">
		<h1>International Journal of Software Engineering</h1>
	</div>
	<p><%=new Date()%></p>
	<div id="menubar">
		<li><a
			href="http://stucat.dcs.shef.ac.uk:8080/stucat008/index.jsp">
				Home</a></li>
		<li><a
			href="http://stucat.dcs.shef.ac.uk:8080/stucat008/review/reviewform.jsp">
				Submit Review Form</a></li>
		<li><a
			href="http://stucat.dcs.shef.ac.uk:8080/stucat008/changepassword.jsp">
				Change Password </a></li>
	</div>
	<div id="maincontent">
		<div id="leftdiv">
		<%
		if (results.size() != 0) {
			int i = 1;
			%>
			<div id="result">
				<table style="width: 100%">
					<tr>
						<th>No.</th>
						<th>title</th>
						<th>author</th>
					</tr>
			<%
			for (SearchResult result : results) {
				%>
				<tr>
						<th><%= i %></th>
						<th><a href=<%= "http://localhost:8080/Electronic%20Journal/showsearch.html?articleID="+result.getArticleID() %>><%= result.getTitle() %></a></th>
						<th><%= result.getAuthorName() %></th>
				</tr>
				<%
				i++;
			}
			%>
			</table>
			</div>
			<%

			
		} else {
			response.sendRedirect(request.getContextPath() + "/noresult.jsp");
			return;
		}
		
		
		
		%>
		
		
		
		</div>
		<!--End of left div -->
	</div>
	<!-- End of Main content -->


	<!-- Footer Section -->
	<hr>
	<div id="footer">� Team8 2015</div>
</body>
</html>
