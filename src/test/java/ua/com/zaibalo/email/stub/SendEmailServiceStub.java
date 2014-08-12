package ua.com.zaibalo.email.stub;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import ua.com.zaibalo.email.SendEmailService;
import ua.com.zaibalo.email.templates.AbstractMessage;

public class SendEmailServiceStub implements SendEmailService{

	private static Set<AbstractMessage> emails = new HashSet<AbstractMessage>();
	
	@Override
	public void sendEmail(AbstractMessage message) {
		emails.add(message);
	}

	public static AbstractMessage pollMessage(){
		Iterator<AbstractMessage> iterator = emails.iterator();
		AbstractMessage message = null;
		if(iterator.hasNext()){
		    message = iterator.next();
		    iterator.remove();
		}
		return message;
	}
}
