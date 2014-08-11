package ua.com.zaibalo.email.impl;

import javax.mail.MessagingException;
import javax.mail.Transport;

import ua.com.zaibalo.email.SendEmailService;
import ua.com.zaibalo.email.templates.AbstractMessage;

public class SendEmailServiceImpl implements SendEmailService {

	@Override
	public void sendEmail(AbstractMessage message) {
		try {
			Transport.send(message.getMimeMessage());
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
	
}