<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<link rel="stylesheet" type="text/css" href="major.css">

  <meta http-equiv="keywords" content="Software,Computer,OOP">
  <meta http-equiv="description" content="Publish Article">
  <meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>IJSE - Publish Article</title>

</head>
<body>

<fieldset><legend>Publish Article</legend>

<form action="">

<table>

<tr>

            <td align="right">Publication Title :</td>

            <td>

            <select name="publicationid">
    
        <%

    Connection con=null;

    Statement st=null;

    ResultSet result=null;

    String hostName="stusql.dcs.shef.ac.uk";

    String dbname="team158";

    String mySqlUrl="jdbc:mysql://"+hostName+"/"+dbname;

    String userid="team158";

    String myPassword ="9a5b309d";

    boolean hasPublication=false;

    

        try {

Class.forName("com.mysql.jdbc.Driver");


con= DriverManager.getConnection(mySqlUrl,userid,myPassword);

st= con.createStatement();

String sql1= "Select id,title  from Publication";

result= st.executeQuery(sql1);

while(result.next())

{

             

out.print("<option value=\""+ result.getInt("id") + "\">" + result.getString("title") + "</option>" );

hasPublication=true;

}

%>

  </select>

        </td>

          </tr>

           

        <tr>

            <td align="right">Article Title</td>

            <td>

            <select name="articleid">

           

<% 

if (!hasPublication){

out.print("Please create a Publication before publishing an Article");

return;

}

sql1="select articleID,title from article";


result= st.executeQuery(sql1);

boolean hasArticle=false;

while(result.next())

{

             

out.print("<option value=\""+ result.getInt("articleID") + "\">" + result.getString("title") + "</option>" );

hasArticle=true;

}

if(!hasArticle){

out.print("You don't have any aticle ready for Publication");

return;

}


        } catch (Exception e) {

out.print("Error:" 

+ e.getMessage());

        }

//System.out.println("finish");

try {

con.close();

st.close();

result.close();

} catch (Exception e) {


e.printStackTrace();

}

        

        %>

          

            </select>

            </td>

           </tr>

           <tr>

            <td align="right">Number of Pages</td>

            <td>

            <input type="number" name="numpages" size = "20">

            </td>

           </tr>

<tr>

           <td></td>

           <td>

           <input type="submit" value="Publish"></b>

         <td>

        <tr>

        

       </table> 



</form>

</fieldset>

</body>

</html>
    