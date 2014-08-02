package ua.com.zaibalo.helper;

//File Name SendHTMLEmail.java

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

import ua.com.zaibalo.servlets.listener.ContextInitListener;

public class SendEmailHelper {
	
	private final static String LOGIN = ContextInitListener.getProperty("email.address"); 
	private final static String PASSWORD = ContextInitListener.getProperty("email.pass"); 
	final String host = "smtp.gmail.com";
	
	Session session = null;


	public SendEmailHelper(){
		init();
	}
	
	public void send(MimeMessage message) throws MessagingException{
		Transport.send(message);
	}
	
	private void init(){
		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", host);
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.auth", "true");
		
		Authenticator authenticator = getAuthenticator();
		this.session = Session.getDefaultInstance(properties, authenticator);
	}
	
	public MimeMessage createMessage(String to, String subject, String body) throws AddressException, MessagingException {
		MimeMessage message = new MimeMessage(this.session);
		message.setFrom(new InternetAddress(LOGIN));
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
		message.setSubject(subject, "utf-8");
		message.setContent(body, "text/html; charset=UTF-8");
		
		return message;
	}

	private Authenticator getAuthenticator() {
		Authenticator authenticator = new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(LOGIN, PASSWORD);
			}
		};
		return authenticator;
	}
}