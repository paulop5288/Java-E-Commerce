
<html>

<head>
<title> Team8 - E-Journal Site</title>
<link rel="stylesheet" type="text/css" href="major.css">
</head>


<body>

<%@ include file="includes/authorheader.jsp" %>
<!-- UPLOADING ARTICLE -->

<div id="article" class="markout">
<form enctype="multipart/form-data"  method="post" action="/SignUpServlet" name="signup" id="signup" autocomplete="on"> 
<!--
<form  method="post" action="/stucat008/SignUpServlet" name="signup" id="signup" autocomplete="on">
-->
<fieldset>
<legend>Article Upload Form</legend>

<table>
	
	<tr>
		<td style="width:200px"><label>Title of Article&nbsp;<em>*</em></label></td>
		<td><input type="text" name="articletitle" id="articletitle"  placeholder="Title of Article "/></td>
	</tr>
	<tr>
		<td>Abstract</td>
		<td><textarea cols="100" rows="15" name="articleabstract" placeholder="Abstract not more than 250 words"></textarea></td>
	</tr>
	<tr>
		<td>Co-authors</td>
		<td><input type="text" name="coauthors" id="coauthors" placeholder="Comma Separated(Author2,Email2,Author3,Email3)"/></td>
	</tr>
	
	<tr>
		<td style="width:200px"><label>Keywords&nbsp;<em>*</em></label></td>
		<td><input type="text" name="keywords" id="keywords" style="width:300px;" placeholder="Comma separated Keywords"/></td>
	</tr>
	
	
	<tr>
		<td style="width:200px"><label>Upload Article&nbsp;<em>*</em></label></td>
		<td><input type="hidden" name="MAX_FILE_SIZE" value="10240000"/><input name="filename" id="filename" type="file" size="10240000"/></td>
	</tr>
	
	<tr>
		<td style="width:200px"><label>E-mail Adress&nbsp;<em>*</em></label></td>
		<td><input type="text" name="email" id="email"  placeholder="Contact Author's E-mail "/></td>
	</tr>
	
	<tr>
		<td>Password</td>
		<td><input type="password" name="password" id="password" placeholder="Sent to your mail after submission" disabled="disabled"/></td>
	</tr>
	<!--
	<tr>
		<td>Confirm Password</td>
		<td><input type="password" name="cpassword" id="cpassword" placeholder="Confirm your Passord"/></td>
	</tr> -->
	<tr>
		<td style="width:200px"><label>First Name&nbsp;<em>*</em></label></td>
		<td><input type="text" name="fname" id="fname"  placeholder="Enter Given Name"/></td>
	</tr>
	<tr>
		<td style="width:200px"><label>Last Name&nbsp;<em>*</em></label></td>
		<td><input type="text" name="lname" id="lname"  placeholder="Enter Family Name"/></td>
	</tr>
	
</table>
<div>
<input type="submit" name="submitSignUp" id="submitSignUp" class="submit" value="Submit Article" />
</div>
</fieldset>
</div>

<div class="markout" id="author">

<fieldset>
<em>Check your email for Username and password you will use to track your article and Review other articles in order to progress your article for publication.</em>
<legend>Article Progress</legend>

<table>
	
	<!--
	
	<tr>
		<td style="width:200px"><label>Title&nbsp;<em>*</em></label></td>
		<td><select name="title" id="title" style="width:px;"><option value="0">...</option><option value="Mr">Mr.</option><option value="Ass Prof.">Assistant Prof.</option><option value="2">Dr</option><option value="3">Associate Prof.</option><option value="4">Proffesor</option><option value="5">Research Assistant</option><option value="6">Lecturer</option><option value="7">Technical Assistant</option><option value="8">Laboratory Technician</option><option value="9">Principal</option><option value="10">Director</option><option value="11">Scientist</option><option value="12">Researcher</option><option value="13">Other</option></select><input type="hidden" id="custom_list-53761c7a9d977-list-style" value="select"/><input type="hidden" id="custom_list-53761c7a9d977-count" value="13"/></td>
	</tr>
	
	
	
	
	<tr>
		<td style="width:200px"><label>Country&nbsp;<em>*</em></label></td>
		<td><select name="country" id="country" style="width:px;"><option value="0">...</option><option value="1" >Germany</option><option value="2">Afghanistan</option><option value="3">Albania</option><option value="4">Algeria</option><option value="5">Andorra</option><option value="6">Angola</option><option value="7">Antigua &amp; Deps</option><option value="8">Argentina</option><option value="9">Armenia</option><option value="10">Australia</option><option value="11">Austria</option><option value="12">Azerbaijan</option><option value="13">Bahamas</option><option value="14">Bahrain</option><option value="15">Bangladesh</option><option value="16">Barbados</option><option value="17">Belarus</option><option value="18">Belgium</option><option value="19">Belize</option><option value="20">Benin</option><option value="21">Bhutan</option><option value="22">Bolivia</option><option value="23">Bosnia Herzegovina</option><option value="24">Botswana</option><option value="25">Brazil</option><option value="26">Brunei</option><option value="27">Bulgaria</option><option value="28">Burkina</option><option value="29">Burma</option><option value="30">Burundi</option><option value="31">Cambodia</option><option value="32">Cameroon</option><option value="33">Canada</option><option value="34">Cape Verde</option><option value="35">Central African Rep</option><option value="36">Chad</option><option value="37">Chile</option><option value="38">People's Republic of China</option><option value="39">Republic of China</option><option value="40">Colombia</option><option value="41">Comoros</option><option value="42">Democratic Republic of the Congo</option><option value="43">Republic of the Congo</option><option value="44">Costa Rica</option><option value="45">Croatia</option><option value="46">Cuba</option><option value="47">Cyprus</option><option value="48">Czech Republic</option><option value="49">Danzig</option><option value="50">Denmark</option><option value="51">Djibouti</option><option value="52">Dominica</option><option value="53">Dominican Republic</option><option value="54">East Timor</option><option value="55">Ecuador</option><option value="56">Egypt</option><option value="57">El Salvador</option><option value="58">Equatorial Guinea</option><option value="59">Eritrea</option><option value="60">Estonia</option><option value="61">Ethiopia</option><option value="62">Fiji</option><option value="63">Finland</option><option value="64">France</option><option value="65">Gabon</option><option value="66">Gaza Strip</option><option value="67">The Gambia</option><option value="68">Georgia</option><option value="69">Ghana</option><option value="70">Greece</option><option value="71">Grenada</option><option value="72">Guatemala</option><option value="73">Guinea</option><option value="74">Guinea-Bissau</option><option value="75">Guyana</option><option value="76">Haiti</option><option value="77">Holy Roman Empire</option><option value="78">Honduras</option><option value="79">Hungary</option><option value="80">Iceland</option><option value="81">India</option><option value="82">Indonesia</option><option value="83">Iran</option><option value="84">Iraq</option><option value="85">Republic of Ireland</option><option value="86">Israel</option><option value="87">Italy</option><option value="88">Ivory Coast</option><option value="89">Jamaica</option><option value="90">Japan</option><option value="91">Jonathanland</option><option value="92">Jordan</option><option value="93">Kazakhstan</option><option value="94">Kenya</option><option value="95">Kiribati</option><option value="96">North Korea</option><option value="97">South Korea</option><option value="98">Kosovo</option><option value="99">Kuwait</option><option value="100">Kyrgyzstan</option><option value="101">Laos</option><option value="102">Latvia</option><option value="103">Lebanon</option><option value="104">Lesotho</option><option value="105">Liberia</option><option value="106">Libya</option><option value="107">Liechtenstein</option><option value="108">Lithuania</option><option value="109">Luxembourg</option><option value="110">Macedonia</option><option value="111">Madagascar</option><option value="112">Malawi</option><option value="113">Malaysia</option><option value="114">Maldives</option><option value="115">Mali</option><option value="116">Malta</option><option value="117">Marshall Islands</option><option value="118">Mauritania</option><option value="119">Mauritius</option><option value="120">Mexico</option><option value="121">Micronesia</option><option value="122">Moldova</option><option value="123">Monaco</option><option value="124">Mongolia</option><option value="125">Montenegro</option><option value="126">Morocco</option><option value="127">Mount Athos</option><option value="128">Mozambique</option><option value="129">Namibia</option><option value="130">Nauru</option><option value="131">Nepal</option><option value="132">Newfoundland</option><option value="133">Netherlands</option><option value="134">New Zealand</option><option value="135">Nicaragua</option><option value="136">Niger</option><option value="137">Nigeria</option><option value="138">Norway</option><option value="139">Oman</option><option value="140">Ottoman Empire</option><option value="141">Pakistan</option><option value="142">Palau</option><option value="143">Panama</option><option value="144">Papua New Guinea</option><option value="145">Paraguay</option><option value="146">Peru</option><option value="147">Philippines</option><option value="148">Poland</option><option value="149">Portugal</option><option value="150">Prussia</option><option value="151">Qatar</option><option value="152">Romania</option><option value="153">Rome</option><option value="154">Russian Federation</option><option value="155">Rwanda</option><option value="156">St Kitts &amp; Nevis</option><option value="157">St Lucia</option><option value="158">Saint Vincent &amp; the</option><option value="159">Grenadines</option><option value="160">Samoa</option><option value="161">San Marino</option><option value="162">Sao Tome &amp; Principe</option><option value="163">Saudi Arabia</option><option value="164">Senegal</option><option value="165">Serbia</option><option value="166">Seychelles</option><option value="167">Sierra Leone</option><option value="168">Singapore</option><option value="169">Slovakia</option><option value="170">Slovenia</option><option value="171">Solomon Islands</option><option value="172">Somalia</option><option value="173">South Africa</option><option value="174">Spain</option><option value="175">Sri Lanka</option><option value="176">Sudan</option><option value="177">Suriname</option><option value="178">Swaziland</option><option value="179">Sweden</option><option value="180">Switzerland</option><option value="181">Syria</option><option value="182">Tajikistan</option><option value="183">Tanzania</option><option value="184">Thailand</option><option value="185">Togo</option><option value="186">Tonga</option><option value="187">Trinidad &amp; Tobago</option><option value="188">Tunisia</option><option value="189">Turkey</option><option value="190">Turkmenistan</option><option value="191">Tuvalu</option><option value="192">Uganda</option><option value="193">Ukraine</option><option value="194">United Arab Emirates</option><option value="195" selected="selected">United Kingdom</option><option value="196">United States of America</option><option value="197">Uruguay</option><option value="198">Uzbekistan</option><option value="199">Vanuatu</option><option value="200">Vatican City</option><option value="201">Venezuela</option><option value="202">Vietnam</option><option value="203">Yemen</option><option value="204">Zambia</option><option value="205">Zimbabwe</option></select><input type="hidden" id="custom_list-53761c7a9aeb1-list-style" value="select"/><input type="hidden" id="custom_list-53761c7a9aeb1-count" value="205"/></td>
	</tr>
	<tr>
		<td style="width:200px"><label>Branch&nbsp;<em>*</em></label></td>
		<td><select name="branch" id="branch" style="width:px;"><option value="0">...</option><option value="1">Aeronautical and Astronautical Engineering</option><option value="2">Agricultural Engineering</option><option value="3">Automotive Engineering</option><option value="4">Biomedical Engineering</option><option value="5">Ceramic Engineering</option><option value="6">Chemical Engineering</option><option value="7">Civil EngineeringCoastal Engineering</option><option value="8">Computer Science</option><option value="9">Corrosion Engineering</option><option value="10">Cost Management Engineering</option><option value="11">Electrical Engineering</option><option value="12">Environmental Engineering</option><option value="13">Ergonomics Engineering</option><option value="14">Facilities Engineering</option><option value="15">Energy Engineering</option><option value="16">Renewable &amp; Sustainable Energy Technologies</option><option value="17">Heating Ventilating Air-Conditioning and Refrigeration Engineering</option><option value="18">Industrial Engineering</option><option value="19">Manufacturing Engineering</option><option value="20">Marine Engineering</option><option value="21">Materials and Metallurgy Engineering</option><option value="22">Mechanical Engineering</option><option value="23">Minerals and Metals Engineering</option><option value="24">Mining Engineering</option><option value="25">Naval Engineering</option><option value="26">Nuclear Engineering</option><option value="27">Optical Engineering</option><option value="28">Petroleum Engineering</option><option value="29">Plastics Engineering</option><option value="30">Plumbing Engineering</option><option value="31">Quality Management Reliability Engineering</option><option value="32">Safety Engineering</option><option value="33">Software Engineering</option><option value="34">Structural Engineering</option><option value="35">Systems Engineering</option><option value="36">Welding Engineering</option><option value="37">Other</option></select><input type="hidden" id="custom_list-53761c7a9ba3b-list-style" value="select"/><input type="hidden" id="custom_list-53761c7a9ba3b-count" value="37"/></td>
	</tr>
	
	<tr>
		<td style="width:200px"><label>Qualification&nbsp;<em>*</em></label></td>
		<td><select name="qualification" id="qualification" ><option value="0">...</option><option value="PhD">PhD</option><option value="Post Doc">Post Doc</option><option value="MSc">MSc</option><option value="M.Tech">M.Tech</option><option value=MCA">MCA</option><option value="M.E">M.E</option><option value="PGD">Post Graduate Diploma</option></select><input type="hidden" id="custom_list-53761c7a9c9d8-list-style" value="select"/><input type="hidden" id="customcount" value="7"/></td>
	</tr>
	<tr>
		<td style="width:200px"><label>University/Organisation&nbsp;<em>*</em></label></td>
		<td><input type="text" name="organisation" id="organisation"  placeholder=""/></td>
	</tr>
	<!--
	<tr>
	<td style="width:200px"><label> I agree to Terms of Service and Privacy Policy&nbsp;<em>*</em></label></td>
	<td><input type="checkbox" name="tac" id="tac" style="float:right;"/></td>
	</tr>
	-->
</table>

</fieldset>

</form>
</div>


</div> <!-- Ends main content div from file include -->

</body>
</html>