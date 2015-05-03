<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="major.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<div id="menubar">
<div id="login">
	<fieldset><legend>Retire an Editor</legend>
		<form action="/Electronic Journal/RetireEditor" method="POST">
			<table>
				<tr>
					<td>Username</td>
					<td><input type="text" name="username" id="username" /></td>
				</tr>
				<tr>
					<td>Password</td>
					<td><input type="password" name="password" id="password" /></td>
				</tr>
				<tr>
					<td> <input type="submit" name="login" value="Retire me"/></td>
					<td> <input type="reset" /> </td> 

				</tr>
	</table>
	</form>
	</fieldset>
	</div>
</div>
</body>
</html>