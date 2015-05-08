<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert Search Term</title>
<br>
<br>
<form method="post" action="search.html">
	<table border="0" width="300" align="center">
		<tr>
			<td colspan=2 style="font-size: 12pt;" align="center">
				<h3>Journal Search</h3>
			</td>
		</tr>

		<tr> 
			<td align="right">Search for :</td>
			<td><input type="text" name="searchTerm" id="searchTerm">
			</td>
		</tr>

		<tr>
			<td align="right">Search Criteria :</td>
			<td><select name="searchOpt">
					<option value="article">Article</option>
					<option value="author">Author</option>
					<option value="year">Year</option>
					<option value="keyword">Keyword</option>
					<option value="all">All</option>
			</select></td>
		</tr>


		<tr>
			<td colspan=2 align="center"><input type="submit" name="submit"
				value="Search"></td>
		</tr>
	</table>
</form>
</html>