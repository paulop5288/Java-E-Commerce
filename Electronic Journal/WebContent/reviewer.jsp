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
			<%
				//allow access only if session exists
				if (session.getAttribute("username") != null
						&& session.getAttribute("role") != null) {
					response.sendRedirect("selectreview.jsp");
				}
			%>
			<div id="login">
				<fieldset>
					<legend>Login - Track your Review Progress</legend>
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
							<input type="hidden" name="role" value="reviewer">
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
