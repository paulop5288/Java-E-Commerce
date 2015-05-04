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
		user = (String) session.getAttribute("username");
		String userName = null;
		String sessionID = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("username"))
					userName = cookie.getValue();
				if (cookie.getName().equals("JSESSIONID"))
					sessionID = cookie.getValue();
			}
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
		<div id="rightdiv">
			<div id="login">
				<fieldset>
					<legend>Login - Track your Article Progress</legend>
					<form action="/Electronic%20Journal/LoginServlet" method="POST">
						<table>
							<tr>
								<td>Username</td>
								<td><input type="text" name="username" id="username" /></td>
							</tr>
							<tr>
								<td>Password</td>
								<td><input type="password" name="password" id="password" /></td>
							</tr>
							<input type="hidden" name="reviewer" value="true">
							<tr>
								<td><input type="submit" value="login" /></td>
								<td><input type="reset" value="reset" /></td>

							</tr>
						</table>
					</form>
				</fieldset>
			</div>

		</div>
		<!-- end right div -->

		<id="leftdiv">
		<div id="select">
			<%
			if (!SelectReview.checkNumberOfReview(3)) {
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
				action="http://localhost:8080/Electronic%20Journal/SelectReview.html"
				method=post>
				<table border="1" style="width: 100%">
					<%!  private List<Article> articles = null; %>
					<%  
				 		articles = SelectReview.getArticlesForReview(3); %>

					<%
						for (Article article : articles) {
					%>
					<tr>
						<td><input type="radio" name="article"
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