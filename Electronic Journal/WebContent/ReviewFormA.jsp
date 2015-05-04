<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>Team8 - E-Journal Site</title>
<link rel="stylesheet" type="text/css"
	href="major.css">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="my first page">
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<!--<link rel="stylesheet" type="text/css" href="/major.css">-->
</head>

<body>
	<div id="banner">
		<h1>International Journal of Software Engineering</h1>
	</div>
	<%@ page import="java.util.Date"%>
	<%@ page import="review.*" %>
	<%@ page import="database.*" %>
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

		<select>
		<%	List<Article>
		
		
		
		%>
			<option id="0" value="select">please select</option>
			<option id="1" value="Genetic Algorithm for UTP">Saab</option>
			<option id="2" value="Programming for Rasbery">Mercedes</option>
			<option id="3" value="Sleep Application">Audi</option>
			
		</select> 
		<br>
		Please choose your expertise level <br><br>
		
		<form method="post"
			action="http://localhost:8080/Electronic%20Journal/submitreview">
			<input type="radio" name="level" value="expert">Expert <input
				type="radio" name="level" value="knowledgeable">Knoweledgeable
			<input type="radio" name="level" value="outsider">Outsider <br>
			<br> Please write a summary of the article <br>
			<br> Abstract <br>
			<textarea rows="15" cols="100" name="abstract"></textarea>
			<br>
			<br> Please write the novel contribution of the article<br>
			<textarea rows="15" cols="100" name="contribution"></textarea>
			<br>
			<br> Please write all the bad points<br>
			<textarea rows="2" cols="100" name="badpoint"></textarea>
			<br>
			<br> Other errors (For editor)<br>
			<textarea rows="15" cols="100" name="error"></textarea>
			<br>
			<br> <input type="submit" value="Submit">
		</form>
	</fieldset>
</body>
</html>
