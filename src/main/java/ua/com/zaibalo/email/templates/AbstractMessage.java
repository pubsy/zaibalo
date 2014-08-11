package ua.com.zaibalo.email.templates;

import java.text.MessageFormat;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.core.io.ClassPathResource;

import ua.com.zaibalo.helper.StringHelper;
import ua.com.zaibalo.spring.SpringPropertiesUtil;

public abstract class AbstractMessage {
	
	protected String subjectKey;
	protected String templatePath;
	protected String recipientAddress;
	
	public AbstractMessage(String recipientAddress){
		this.recipientAddress = recipientAddress;
	}

	protected abstract String[] getParameters();
	
	public MimeMessage getMimeMessage() {
		String pattern = new ClassPathResource(this.templatePath).toString();
		String body = new MessageFormat(pattern).format(getParameters(), new StringBuffer(), null).toString();
		String subject = StringHelper.getLocalString(this.subjectKey);
		
		Session mailSession = createMailSession();
		MimeMessage message = new MimeMessage(mailSession);
		
		String emailAddress = SpringPropertiesUtil.getProperty("email.address");
		try {
			message.setFrom(new InternetAddress(emailAddress));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(this.recipientAddress));
			message.setSubject(subject, "utf-8");
			message.setContent(body, "text/html; charset=UTF-8");
		} catch (AddressException e) {
			throw new RuntimeException(e);
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		
		return message;
	}
	
	private Session createMailSession(){
		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", SpringPropertiesUtil.getProperty("mail.smtp.host"));
		properties.put("mail.smtp.starttls.enable", SpringPropertiesUtil.getProperty("mail.smtp.starttls.enable"));
		properties.put("mail.smtp.auth", SpringPropertiesUtil.getProperty("mail.smtp.auth"));
		
		Authenticator authenticator = new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				String emailAddress = SpringPropertiesUtil.getProperty("email.address"); 
				String password = SpringPropertiesUtil.getProperty("email.pass");
				return new PasswordAuthentication(emailAddress, password);
			}
		};
		
		return Session.getDefaultInstance(properties, authenticator);
	}

}
