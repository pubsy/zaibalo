package ua.com.zaibalo.helper;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import ua.com.zaibalo.spring.SpringPropertiesUtil;

public class SendEmailHelper {

	private static final String HOST = "smtp.gmail.com";
	
	Session session = null;


	public SendEmailHelper(){
		init();
	}
	
	public void send(MimeMessage message) throws MessagingException{
		Transport.send(message);
	}
	
	private void init(){
		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", HOST);
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.auth", "true");
		
		Authenticator authenticator = getAuthenticator();
		this.session = Session.getDefaultInstance(properties, authenticator);
	}
	
	public MimeMessage createMessage(String to, String subject, String body) throws AddressException, MessagingException {
		String emailAddress = SpringPropertiesUtil.getProperty("email.address");
		
		MimeMessage message = new MimeMessage(this.session);
		message.setFrom(new InternetAddress(emailAddress));
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
		message.setSubject(subject, "utf-8");
		message.setContent(body, "text/html; charset=UTF-8");
		
		return message;
	}

	private Authenticator getAuthenticator() {
		Authenticator authenticator = new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				String emailAddress = SpringPropertiesUtil.getProperty("email.address"); 
				String password = SpringPropertiesUtil.getProperty("email.pass");
				return new PasswordAuthentication(emailAddress, password);
			}
		};
		
		
		return authenticator;
	}
	
	
}