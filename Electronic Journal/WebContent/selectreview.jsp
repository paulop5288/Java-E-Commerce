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
		<li><a
			href="./myreview.jsp">
				My Reviews</a></li>
		<li><a
			href="./reviewform.jsp">
				Submit Review</a></li>
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

		<id="leftdiv">
		<div id="select">
			<%
				if (!SelectReview.checkNumberOfReview(reviewer.getID())) {
			%>
			<p>
				You have selected 3 articles for review.<br> Please submit your
				review form.
			</p>
			<%
			} else { 
				%>
			<br> <br> Please select the article you want to review
			<form
				action="http://localhost:8080/Electronic%20Journal/selectreview.html"
				method=post>
				<table border="1" style="width: 100%">
					<%!  private List<Article> articles = null; %>
					<%  
				 		articles = SelectReview.getArticlesForReview(reviewer.getID()); %>

					<%
						for (Article article : articles) {
					%>
					<tr>
						<td><input type="checkbox" name="article"
							value=<%=article.getArticleIDString()%>>Select</td>
						<td><%=
								article.getTitle()
							%></td>
						<td><input type="button"
							onclick="readAbstract(<%=article.getArticleIDString()%>)"
							value="Read Abstract"></td>
						<p hidden id=<%=article.getArticleIDString()%>><%=article.getArticleAbstract()%></p>
					</tr>

					<%
						}
					%>
					<input type="hidden" name="reviewer" value=<%= reviewer.getID() %>>
				</table>
				<input type="submit" value="Choose">
			</form>
			<%
			}
		
		%>

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
		</id>
	</div>
	<!--End of left div -->

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