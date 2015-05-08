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
		ArticleDetail detail = (ArticleDetail) request
				.getAttribute("detail");

		if (detail == null) {
			response.sendRedirect(request.getContextPath()
					+ "/noresult.jsp");
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
			<hr>
			<br>
			<td><h3>
					The title is :
					<%=detail.getTitle()%>
				</h3></td>
			<h4>
				Keywords :
				<%=detail.getKeywords()%></h4>
			<h4>
				The summary :
				<%=detail.getArticleAbstract()%></h4>
			<h4>
				Email :
				<%=detail.getEmail()%></h4>
			<form action="../download.html" method="post">
				<h4>
					Download : <input type="hidden" name="articleID"
						value=<%=detail.getArticleID()%>> <input type="hidden"
						name="role" value="reader"> <input type="submit"
						value="download">

				</h4>
			</form>
		</div>
		<!--End of left div -->
	</div>
	<!-- End of Main content -->


	<!-- Footer Section -->
	<hr>
	<div id="footer">© Team8 2015</div>
</body>
</html>