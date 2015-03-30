<div id="login">
	<fieldset><legend>Change Password</legend>
		<form action="http://localhost:8080/FirstWeb/PasswordServlet" method="POST">
			<table>
				<tr>
					<td>User Name</td>
					<td><input type="text" name="username" id="username" placeholder="Your username"/></td>
				</tr>
				<tr>
					<td>Old Password</td>
					<td><input type="password" name="oldpassword" id="oldpassword" placeholder="Your Old Password"/></td>
				</tr>
				<tr>
					<td>Password</td>
					<td><input type="password" name="password" id="password" placeholder="Your New Password" /></td>
				</tr>
				<tr>
					<td>Confirm Password</td>
					<td><input type="password" name="cpassword" id="cpassword" placeholder="Confirm New Password" /></td>
				</tr>
				
				<tr>
					<td> <input type="submit" name="login"/></td>
					<td> <input type="reset" /> </td> 

				</tr>
	</table>
	</form>
	</fieldset>
	</div>
