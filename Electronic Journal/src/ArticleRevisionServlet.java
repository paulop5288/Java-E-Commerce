import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import com.mysql.jdbc.Driver;
import org.apache.commons.fileupload.*;
import org.apache.commons.io.output.*;
import org.apache.commons.fileupload.disk.*;
import org.apache.commons.fileupload.servlet.*;
import java.util.*;


public class ArticleRevisionServlet extends HttpServlet {

	private String email = "", revision = "", title = "", fname = "",
			lname = "",message="";
	private String curDir ="";
	PrintWriter out;
	//Article credentials
	private String articleTitle="" , articleAbstract="",coauthors="",keywords="",filepath="uploads/";
	private File file;
	InputStream inps = null;
	private int maxFileSize = 10240000;
	private int authorid=0,articleid=0;
	private int version = 1, count = 0;
	private Connection dbCon = null; // connection to a database
	private String dbServer = "jdbc:mysql://stusql.dcs.shef.ac.uk/";
	private String dbname = "team158";
	private String user = "team158";
	private String myPassword = "9a5b309d";

	public ArticleRevisionServlet() {
		super();
		// Load database driver
		try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				System.err.println("fail to load driver.");
			}

	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		
		//Prepare HTML page for output
		res.setContentType("text/html");
		out = res.getWriter();
		ServletContext context = req.getServletContext();
		filepath = context.getInitParameter("filepath");
		curDir=req.getContextPath();
		message="<html><head><link rel=\"stylesheet\" type=\"text/css\" href=\"major.css\"></head><body>";
		out.println(message);message="";
		
		message+="<span class=\"success\">";
		message += "<br/>";
		
		// verify content type
		String requestContentType = req.getContentType();
		if((requestContentType.indexOf("multipart/form-data")>=0)){
			DiskFileItemFactory dFactory = new DiskFileItemFactory();
			dFactory.setSizeThreshold(maxFileSize);
			
			
			//Now create a new file upload handler
			ServletFileUpload fUpload = new ServletFileUpload(dFactory);
			fUpload.setSizeMax(maxFileSize);
			String fieldName="",fieldValue;
			try{
				//Get the file items from the request
				List<FileItem> fileItems = fUpload.parseRequest(req);
				//Now process the uploaded file items
				Iterator<FileItem> i = fileItems.iterator();
				while(i.hasNext()){
					FileItem fItem = (FileItem)i.next();
					if(!fItem.isFormField()){
						//Get the uploaded file parameters and process them
					message += "<p>Trying to upload submited Article...</p><hr/>";
					String fileName = fItem.getName();
					
					//Stream will be saved to database as backup for file save.
					inps = fItem.getInputStream();
					if(inps!=null){
						message += "Got the file:" + fItem.getName()+"<br/>";
					}
					else{
						message += "Got NO file: Name=" + fItem.getName();
					}
					boolean isInMemory = fItem.isInMemory();
					long byteSize = fItem.getSize();
					//Write the article to the articles folder
					if(fileName.lastIndexOf("\\")>=0){
						file = new File(filepath + fileName.substring(fileName.lastIndexOf("\\" )));
					}
					else{
						file = new File(filepath + fileName.substring(fileName.lastIndexOf("\\") + 1));
					}
					//Saves to file
					fItem.write(file);
					//message+= "File uploaded to:" + file.getAbsolutePath() +"<br/>";
					filepath = file.getPath();
						
					}
					else{
						// Process the other form fields submitted
						fieldName=fItem.getFieldName();
						fieldValue = fItem.getString();
						//message += fieldName + ": " + fieldValue + "<br/>";
						switch(fieldName){
						
						case "email":
							email=fieldValue; break;
						case "revision":
							revision = fieldValue; break;
						case "version":
							version=Integer.parseInt(fieldValue); break;
						case "title":
							title=fieldValue; break;
						default:
							//message+= "<p>Unknown field added</p>";
						}
					}
				}
				
			}
			catch(Exception ex){
				message= ex.getMessage();
				out.println("<span class=\"error\">Fileupload error: </span>" + message + ":"+ curDir);
				return;
			}
		}
		
		

		//Start session management here
		HttpSession session = req.getSession(true);
		

		if (email.trim().compareTo("") == 0) {
			message="Email address cannot be empty.</body></html>";
			out.println(message);
			return;
		}
		
		if (revision.trim().compareTo("") == 0) {
			message="Please describe how you have addressed the errors from reviewers.</body></html>";
			out.println(message);
			return;
		}
		
		
		authorid = this.getAuthorId();
		PreparedStatement pstmt = null;
		PreparedStatement pstmtArticle = null;

		try {
			// Get connection to team database
			dbCon = DriverManager.getConnection(dbServer + dbname, user,
					myPassword);
			int count=0;
			
			//Register new author only if not previously registered.
			if(authorid>0){
				pstmt = dbCon
					.prepareStatement("INSERT INTO articlerevision(id,authorid,articleid,revision,revision_no,filepath) VALUES (null, ?, ?,?,?,?)");
				pstmt.setInt(1, authorid);
				pstmt.setInt(2, articleid);
				pstmt.setString(3, revision);
				pstmt.setInt(4, version);
				pstmt.setString(5, filepath);
				
				count = pstmt.executeUpdate();
			}

			if(count > 0){
				authorid = this.getAuthorId();
				message="Hello " + fname
				+ ", You have succesfully submitted your article revision<br/>";
				
				out.println(message);

			}
			//Handling article upload
			
			

		} catch (Exception ex) {

			ex.printStackTrace();
			out.println("<span class=\"error\">Article upload or Author Registration Error: </span>" + ex.getMessage());
			return;
		}

		finally {
			if (dbCon != null)
				try {
					dbCon.close();
				} catch (SQLException ex) {
				}
		}
		
		message+="</body></html>";
		out.println(message);
	}
	

	public int getAuthorId(){
		int authorid=0;
		if(email==null || email.equals(""))
			return authorid;
		try{
			// Get connection to team database
			dbCon = DriverManager.getConnection(dbServer + dbname, user,
								myPassword);
			Statement st = dbCon.createStatement();
			ResultSet result = st.executeQuery("Select * FROM author username='"+ email + "'");
			while(result.next()){
				
				authorid=result.getInt("authorID");
				
			}
			
		}
		catch (Exception ex) {

			ex.printStackTrace();
			authorid=-1;
			return authorid;
		}

		finally {
			if (dbCon != null)
				try {
					dbCon.close();
				} catch (SQLException ex) {
				}
		}
		
		
		return authorid;
		
	}
}