<head>

<title> Team8 - E-Journal Site</title>
<link rel="stylesheet" type="text/css" href="major.css">
</head>

<div id="maincontent">
	<fieldset><legend>Appoint Editor</legend>
		<form action="/stucat008/AppointEditorServlet" method="POST">
			<table>
				<tr>
					<td>User Name</td>
					<td><input type="text" name="username" id="username" placeholder="Enter Username please..."/></td>
				</tr>
				<tr>
					<td>Password</td>
					<td><input type="text" name="password" id="password" placeholder="Enter password please..."/></td>
				</tr>
				
				<tr>
				<td style="width:200px"><label>Title&nbsp;<em>*</em></label></td>
				<td><select name="title" id="title" style="width:px;"><option value="0">...</option><option value="1">Mr</option><option value="2">Miss</option><option value="3">Mrs</option><option value="4">Ms</option><option value="5">Dr</option></select><input type="hidden" id="custom_list-53761c7a9ba3b-list-style" value="select"/><input type="hidden" id="custom_list-53761c7a9ba3b-count" value="5"/></td>
				</tr>
				
				<tr>
					<td>Surname</td>
					<td><input type="text" name="surname" id="surname" placeholder="Enter surname please..."/></td>
				</tr>
				<tr>
					<td>Forename</td>
					<td><input type="text" name="forename" id="forename" placeholder="Enter forname please..."/></td>
				</tr>
				<tr>
					<td> <input type="submit" name="login"/></td>
					<td> <input type="reset" /> </td> 

				</tr>
	</table>
	</form>
	</fieldset>
	</div>
