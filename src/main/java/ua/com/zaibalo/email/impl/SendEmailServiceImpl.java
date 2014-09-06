package ua.com.zaibalo.email.impl;

import javax.mail.MessagingException;
import javax.mail.Transport;

import ua.com.zaibalo.email.SendEmailService;
import ua.com.zaibalo.email.templates.AbstractMessage;

public class SendEmailServiceImpl implements SendEmailService {

	@Override
	public void sendEmail(final AbstractMessage message) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Transport.send(message.getMimeMessage());
				} catch (MessagingException e) {
					throw new RuntimeException(e);
				}
			}
		}).start();
	}

}