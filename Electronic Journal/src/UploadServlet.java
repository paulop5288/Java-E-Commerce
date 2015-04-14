import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.omg.CORBA.PRIVATE_MEMBER;


public class UploadServlet extends HttpServlet {
	boolean isMultipart;
	String filePath;
	int maxFileSize = 50 * 1024;	// 50 kb
	int maxMemSize = 4 * 1024;
	File file = null;
	
	public void init() {
		// obtain file path
		filePath = getServletContext().getInitParameter("file-upload");
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		PrintWriter out = res.getWriter();
		isMultipart = ServletFileUpload.isMultipartContent(req);
		
		if (!isMultipart) {
			out.println("<html>");
			out.println("<head>");
			out.println("<title>Servlet upload</title>");
			out.println("</head>");
			out.println("<body>");
			out.println("<p>No file uploaded</p>");
			out.println("</body>");
			out.println("</html>");
			return;
		}

		DiskFileItemFactory factory = new DiskFileItemFactory();
		// maximum size that will be stored in memory
		factory.setSizeThreshold(maxMemSize);
		// location to save data that is larger than maxMemSize
		factory.setRepository(new File("c:\\temp"));
		
		// create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);
		// maximum file size to be uploaded
		upload.setSizeMax(maxFileSize);
		
		try {
			// parse the request to get file items
			List<FileItem> fileItems = upload.parseRequest(req);
			
			// process the uploaded file items
			Iterator iter = fileItems.iterator();

			out.println("<html>");
			out.println("<head>");
			out.println("<title>Servlet upload</title>");
			out.println("</head>");
			out.println("<body>");
			
			while (iter.hasNext()) {
				FileItem fileItem = (FileItem)iter.next();
				
				if (!fileItem.isFormField()) {
					// get the uploaded file parameters
					String fieldName = fileItem.getFieldName();
					String fileName = fileItem.getName();
					String contentType = fileItem.getContentType();
					boolean isInMemory = fileItem.isInMemory();
					long sizeInBytes = fileItem.getSize();
					
					// write the file
					if (fileName.lastIndexOf("\\") >= 0) {
						file = new File(filePath + 
								fileName.substring(fileName.lastIndexOf("\\")));
					} else {
						file = new File(filePath +
								fileName.substring(fileName.lastIndexOf("\\") + 1));
					}
					fileItem.write(file);
					out.println("Uploaded FileNameExtensionFilter : " + fileName + "<br>");
				}
				out.println("</body>");
				out.println("</html>");
			}
			//
		} catch (Exception e) {
			System.out.println("Have exception.");
			System.out.println(e);
		}

		System.out.println("sucessful.");
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
		out.println("<html><body>");
		out.println("You have used GET!");
		out.println("</body></html>");
	}

}
