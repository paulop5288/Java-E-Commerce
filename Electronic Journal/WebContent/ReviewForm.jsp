<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
  <title>Team8 - E-Journal Site</title>
  <link rel="stylesheet" type="text/css" href="./Team8 - E-Journal Site_files/major.css">
  <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
  <meta http-equiv="description" content="my first page">
  <meta http-equiv="content-type" content="text/html; charset=UTF-8">
  <!--<link rel="stylesheet" type="text/css" href="./styles.css">-->
</head>

<body>
  <div id="banner">
    <h1> International Journal of Software Engineering</h1>
  </div>
  <%@ page import="java.util.Date" %>
  <p><%= new Date() %></p>
  
  <div id="menubar"> 
    <li><a href="http://stucat.dcs.shef.ac.uk:8080/stucat008/index.jsp"> Home</a></li>
    <li> <a href="authorSignUp.jsp"> Submit Article</a></li>
    <li> <a href="mysubmissions.jsp">My Submissions</a> </li>
    <li><a href="changepassword.jsp"> Change Password </a></li>
  </div>
  <br><br>
  <fieldset>
    <legend>Review Form</legend>

    Please choose your expertise level
    <br><br>
    <form method="post" 
    action="http://localhost:8080/FirstWeb/LoginServlet">
    <input type="radio" name="level" value="expert">Expert
    <input type="radio" name="level" value="knowledgeable">Knoweledgeable
    <input type="radio" name="level" value="outsider">Outsider
    <br><br>
    Please write a summary of the article
    <br><br>
    Abstract
    <br>
    <textarea rows="15" cols="100" name="abstract"></textarea>      
    <br><br>
    Please write the novel contribution of the article<br>
    <textarea rows="15" cols="100" name="contribution"></textarea>
    <br><br>
    Please write all the bad points<br>
    <textarea rows="2" cols="100" name="badpoint"></textarea>
    <br><br>
    Other errors (For editor)<br>
    <textarea rows="15" cols="100" name="error"></textarea>
    <br><br>
    <input type="submit" value="Submit">
    </form>
  </fieldset>
</body>
</html>
