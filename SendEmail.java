
import java.io.*;
import java.net.*;
import java.util.Properties;
import javax.mail.AuthenticationFailedException;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
public class SendEmail extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SendEmail() {
        super();
     }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		    res.setContentType("text/html");
		    PrintWriter out= res.getWriter();
		    
		    String host = "smtp.gmail.com";
		    String to = req.getParameter("to");
		    String subject = req.getParameter("subject");
		    String from = req.getParameter("email");
		    String password = req.getParameter("pass");
	        String message = req.getParameter("message");

	        try {
	        	
	         Properties ps = new Properties();
	            
	         ps.setProperty("mail.host", "smtp.gmail.com");
	         ps.setProperty("mail.smtp.port", "587");
	         ps.setProperty("mail.smtp.auth", "true");
	         ps.setProperty("mail.smtp.starttls.enable", "true");

	         Session session = Session.getDefaultInstance(ps, new javax.mail.Authenticator() {
	        	 
	         protected PasswordAuthentication getPasswordAuthentication() {
	         return new PasswordAuthentication(from,password);}
	             
	         });

	         MimeMessage ms = new MimeMessage(session);
	         ms.setText(message);
	         ms.setFrom(new InternetAddress(from));
	         ms.setSubject(subject);
	            
	         ms.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
	         Transport transport = session.getTransport("smtp");
	         Transport.send(ms);
	            
	         transport.connect(host, from, password);
	         transport.close();
	           
	         out.println("  The e-mail was sent successfully...  "); 
	 
		  
			  
		    }catch (MessagingException ex){
	        
	        ex.printStackTrace();
	        out.println("There were an error: ");
	        }  
	        
		    }
	
	class gmailAuthenticator extends Authenticator 
    {	
     String user;
	 String pass;
	     
    public gmailAuthenticator (String username, String password)
    {
    super();
    user = username;
    pass = password;
    }
	     
    public PasswordAuthentication getPasswordAuthentication()
	    
    {  return new PasswordAuthentication(user, pass);}
	    
	}
	
	}
