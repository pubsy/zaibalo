package ua.com.zaibalo.email;

import ua.com.zaibalo.email.templates.AbstractMessage;

public interface SendEmailService {

	public void sendEmail(AbstractMessage message);
	
}
