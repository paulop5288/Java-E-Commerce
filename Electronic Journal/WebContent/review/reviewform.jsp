<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page import="java.util.ArrayList"%>
<html>
<head>
<title>Team8 - E-Journal Site</title>
<link rel="stylesheet" type="text/css" href="../major.css">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="my first page">
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
</head>

<body>
	<div id="banner">
		<h1>International Journal of Software Engineering</h1>
	</div>
	<%@ page import="java.util.Date"%>
	<%@ page import="java.util.List"%>
	<%@ page import="review.*"%>
	<%@ page import="database.*"%>
	<%
	//allow access only if session exists
	String username = (String) session.getAttribute("username");
	String role = (String)session.getAttribute("role");
	User reviewer = null;
	if (username == null || role == null || !role.equalsIgnoreCase("reviewer")) {
		response.sendRedirect(request.getContextPath() + "/reviewer.jsp");
		return;
	} else {
		reviewer = new User(username, "");
		if (!SelectReview.isReviewer(reviewer.getID())) {
			response.sendRedirect(request.getContextPath() + "/reviewer.jsp");
			session.invalidate();
			return;
		}
	}
	%>
	<p><%= new Date() %></p>

	<div id="menubar">
		<li><a
			href="http://stucat.dcs.shef.ac.uk:8080/stucat008/index.jsp">
				Home</a></li>
		<li><a href="./selectreview.jsp">Select Review</a></li>
		<li><a href="./myreview.jsp">My Review</a></li>
	</div>
	<br>
	<br>
	<fieldset>
		<legend>Review Form</legend>
		<form method="post"
			action="http://localhost:8080/Electronic%20Journal/submitreview.html">
			<select name="article">
				<option id="0" value="Select">Please select</option>
				<%	List<Article> articles = SubmitReview.getDownloadedArticles(reviewer.getID());
		int count = 1;
		for (Article article : articles) {
			%>
				<option id=<%= count %> value=<%= article.getArticleIDString() %>><%= article.getTitle() %></option>
				<%
			count++;
		}
		%>
			</select> <br> Please choose your expertise level <br>
			<br> <input type="radio" name="level" value="expert">Expert
			<input type="radio" name="level" value="knowledgeable">Knoweledgeable
			<input type="radio" name="level" value="outsider">Outsider <br>
			<br> Please give your opinion about this article<br>
			<br> <input type="radio" name="score" value="champion">champion
			<input type="radio" name="score" value="favourable">favourable
			<input type="radio" name="score" value="indifferent">indifferent
			<br> <input type="radio" name="score" value="indifferent">detractor
			<br> <br> Please write a summary of the article <br> <br>
			Abstract <br>
			<textarea rows="15" cols="100" name="abstract"></textarea>
			<br> <br> Please write the novel contribution of the
			article<br>
			<textarea rows="15" cols="100" name="contribution"></textarea>
			<br> <br> Please write all the bad points<br>
			<textarea rows="2" cols="100" name="badpoint"></textarea>
			<br> <br> Other errors (For editor)<br>
			<textarea rows="15" cols="100" name="error"></textarea>
			<br> <br> <input type="submit" value="Submit"> <input
				type="hidden" name="reviewer" value=<%= reviewer.getID() %>>
		</form>
	</fieldset>
</body>
</html>
