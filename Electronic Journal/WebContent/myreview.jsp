<%@page
	import="org.apache.taglibs.standard.tag.common.core.ForEachSupport"%>
<%@page import="database.DBConnection"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
	<%
		//allow access only if session exists
		String user = null;
		if (session.getAttribute("username") == null) {
			response.sendRedirect("reviewer.jsp");
		} else {
			user = (String) session.getAttribute("username");
		}
		String username = null;
		String sessionID = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("username"))
					username = cookie.getValue();
				if (cookie.getName().equals("JSESSIONID"))
					sessionID = cookie.getValue();
			}
		}
		User reviewer = new User(user, "");
	%>
	<div id="banner">
		<h1>International Journal of Software Engineering</h1>
	</div>
	<p><%=new Date()%></p>
	<div id="menubar">
		<li><a
			href="http://stucat.dcs.shef.ac.uk:8080/stucat008/index.jsp">
				Home</a></li>
		<li><a href="./selectreview.jsp"> Select Review</a></li>
		<li><a href="./reviewform.jsp"> Submit Review</a></li>
	</div>
	<div id="maincontent">
		<div id="rightdiv">
			<div id="logout">
				<fieldset>
					<legend>Logout</legend>
					<p>
						Hi,
						<%=user%></p>
					<form
						action="http://localhost:8080/Electronic%20Journal/logout.html"
						method="post">
						<input type="hidden" name="role" value="reviewer"> <input
							type="submit" value="logout">
					</form>
				</fieldset>
			</div>
		</div>
		<!-- end right div -->

		<div id="leftdiv">
			<div id="reviews">
				<table style="width: 100%">
					<tr>
						<th>No.</th>
						<th>title</th>
						<th>status</th>
						<th></th>
						<th></th>
					</tr>
					<% List<Selection> selections = Selection.getSelectedArticles(reviewer.getID()); 
					int count = 1;
					for (Selection selection : selections) {
						 %>

					<tr>
						<form action="download.html" method="post">
						<td><%= count %></td>
						<td><%= selection.getTitle() %></td>
						<td><%= selection.getStatus() %></td>
						<td><input type="hidden" name="articleID"
							value=<%= selection.getArticleID() %>> <input
							type="hidden" name="reviewerID" value=<%= reviewer.getID() %>>
							<input type="submit" value="download"></td>
						</form>


						<%
						if (selection.getStatus().equalsIgnoreCase("selected")) {
							%>
						<form action="cancelselection.html" method="post">
						<td><input type="submit" value="cancel"> <input
							type="hidden" name="articleID"
							value=<%= selection.getArticleID() %>> <input
							type="hidden" name="reviewerID" value=<%= reviewer.getID() %>>
						</td>
						</form>
						<% 
						} else {
							%>
						<td>reviewing</td>
						<%
						}
						%>

					</tr>
					<%
						 count++;
					}
					%>
				</table>


			</div>
			<p></p>
			<h3>Guideline for Reviewers</h3>
			Follow these guidelines while creating and submitting your review
			form:
			<p></p>
			<p>
				Download template <a
					href="http://stucat.dcs.shef.ac.uk:8080/stucat008/reviewtemplate.docx">Here</a>.
			</p>
		</div>

		<!--End of left div -->
	</div>
	<!-- End of Main content -->


	<!-- Footer Section -->
	<hr>
	<div id="footer">© Team8 2015</div>
</body>
</html>
<script type="text/javascript">
function readAbstract(p1) {
	var text = document.getElementById(p1);
	alert("article abstract : \n\n" + text.innerHTML);
}

</script>