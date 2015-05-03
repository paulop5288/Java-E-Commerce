<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
  <title>Team8 - E-Journal Site</title>
  <link rel="stylesheet" type="text/css" href="major.css">
 
  <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
  <meta http-equiv="description" content="my first page">
  <meta http-equiv="content-type" content="text/html; charset=UTF-8">
  
</head>

<body>
  <div id="banner">
    <h1> International Journal of Software Engineering</h1>
  </div>
  <%@ page import="java.util.Date" %>
  <p><%= new Date() %></p>
  
  <div id="menubar"> 
    <li><a href="http://stucat.dcs.shef.ac.uk:8080/stucat008/index.jsp"> Home</a></li>
    <li><a href="changepassword.jsp"> Publish Article </a></li>
    <li><a href="changepassword.jsp"> Publication Form </a></li>
    <li><a href="changepassword.jsp"> Change Password </a></li>
  </div>
  <br><br>
  <fieldset>
    <legend>Publication Form</legend>

  

    <form method="post" 
    action="/Editor/publicationForm">
       <table>
        
           <tr>
            <td align="right">Publication Title :</td>
            <td>
            <input type="text"name="title" size = "20">
            </td>
           </tr>
           
           <tr>
            <td align="right">Year :</td>
            <td>
            <input type="text"name="year" size = "20">
            </td>
           </tr>

           <tr>
            <td align="right">volume :</td>
            <td>
            <input type="text"name="volume" size = "20">
            </td>
           </tr>
           
           <tr>
            <td align="right">Number :</td>
            <td>
            <input type="text"name="Number" size = "20">
            </td>
           </tr>
           <tr>
            <td align="right">pub_index :</td>
            <td>
            <input type="text"name="pub_index" size = "20">
            </td>
           </tr>
           
           <tr>
            <td align="right">Number_of_pages :</td>
            <td>
            <input type="text"name="to" size = "20">
            </td>
           </tr>

           <tr>
            <td align="right">Editors :</td>
            <td>
            <input type="text"name="Editors" size = "20">
            </td>
           </tr>
           
           <tr>
            <td align="right">Keyword :</td>
            <td>
            <input type="text"name="Keyword" size = "20">
            </td>
           </tr>
           <tr>
            <td align="right">Editor_nots :</td>
            <td>
            <textarea name= "message" cols="50" rows="6"></textarea>
            </td>
           </tr>
           <td></td>
           <td>
           <input type="submit" value="Submit"></b>
         <td>
        <tr>
        
       </table> 
     </form>
   </body>
</html>