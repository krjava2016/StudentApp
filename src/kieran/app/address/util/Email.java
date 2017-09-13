package kieran.app.address.util;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Email {
	
	public static void sendEmail(String to, String subject, String msg,
	         String from, String userName, String password) {
	      Properties properties = new Properties();
	      properties.put("mail.smtp.auth", "true");
	      properties.put("mail.smtp.starttls.enable", "true");
	      properties.put("mail.smtp.host", "smtp.gmail.com");
	      properties.put("mail.smtp.port", "587");
	      Session session = Session.getInstance(properties, new Authenticator() {
	         protected PasswordAuthentication getPasswordAuthentication() {
	            return new PasswordAuthentication(userName, password);
	          }
	      });

	      try {
	         Message message = new MimeMessage(session);
	         message.setFrom(new InternetAddress(from));
	         message.setRecipients(Message.RecipientType.TO,
	            InternetAddress.parse(to));
	         message.setSubject(subject);
	         message.setText(msg);
	         Transport.send(message);
	         //System.out.println("Message send successfully....");
	      } catch (MessagingException e) {
	         throw new RuntimeException(e);
	      }
	   }

}






