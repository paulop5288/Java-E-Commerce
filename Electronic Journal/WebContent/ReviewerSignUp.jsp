
<head>

<title>Team8 - E-Journal Site</title>
<link rel="stylesheet" type="text/css" href="major.css">
</head>

<div id="maincontent">
	<fieldset>
		<legend>Sign Up</legend>
		<form
			action="http://localhost:8080/Electronic%20Journal/ReviewerSignUp"
			method="POST">
			<table>
				<tr>
					<td>Full Name</td>
					<td><input type="text" name="fullname" id="fullname"
						placeholder="Enter your fullname" /></td>
				</tr>
				<tr>
					<td>Email</td>
					<td><input type="text" name="email" id="email"
						placeholder="example@yourdomain.com" /></td>
				</tr>
				<tr>
					<td>Password</td>
					<td><input type="password" name="password" id="password"
						placeholder="Your password" /></td>
				</tr>
				<tr>
					<td>Confirm Password</td>
					<td><input type="password" name="password" id="password"
						placeholder="Confirm Your password" /></td>
				</tr>
				<tr>
					<td style="width: 200px"><label>Profession&nbsp;<em>*</em></label></td>
					<td><select name="profession" id="profession"
						style="width: px;"><option value="0">Select Discipline</option>
							<option value="1">Aerospace Engineering</option>
							<option value="2">Artificial Intelligence</option>
							<option value="3">Biotechnology</option>
							<option value="4">Chemical Engineering</option>
							<option value="5">Civil EngineeringCoastal Engineering</option>
							<option value="6">Computer Science</option>
							<option value="7">Electrical and Electronic Engineering</option>
							<option value="8">Infomation Systems</option>
							<option value="9">Maritime Technology</option>
							<option value="10">Mechanical Engineering</option>
							<option value="11">Metallurgy and Minerals Engineering</option>
							<option value="12">Production Engineering and Manufacturing</option>
							<option value="13">Software Engineering</option>
							<option value="14">Other</option></select><input type="hidden"
						id="custom_list-53761c7a9ba3b-list-style" value="select" /><input
						type="hidden" id="custom_list-53761c7a9ba3b-count" value="37" /></td>
				</tr>
				<tr>
					<td style="width: 200px"><label>Degree&nbsp;<em>*</em></label></td>
					<td><select name="degree" id="degree" style="width: px;"><option
								value="">Select Degree</option>
							<option value="1">B.A. (Bachelor of Arts) </option>
							<option value="2">B.Eng. (Bachelor of Engineering) </option>
							<option value="3">B.Sc. (Bachelor of Science)</option>
							<option value="4">LL.B. (Bachelor of Laws)</option>


							<option value="Others">Other</option></select><input type="hidden"
						id="custom_list-53761c7a9ba3b-list-style" value="select" /><input
						type="hidden" id="custom_list-53761c7a9ba3b-count" value="37" /></td>
				</tr>

				<tr>
					<td><input type="submit" name="login" /></td>
					<td><input type="reset" /></td>
				</tr>
			</table>
		</form>
		</fields>