package ua.com.zaibalo.email.templates;

import ua.com.zaibalo.helper.StringHelper;

public class PrivateMessageNotificationMessage extends AbstractMessage {

	private String senderName;
	private String text;
	
	public PrivateMessageNotificationMessage(String recipientAddress) {
		super(recipientAddress);
		this.subjectKey = "zaibalo_new_pm";
		this.templatePath = "templates/pm_notification.html";
	}

	@Override
	protected String[] getParameters() {
		return new String[] {
				StringHelper.getLocalString("you_got_new_message", senderName),
				StringHelper.getLocalString("message_text", text) 
		};
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public void setText(String text) {
		this.text = text;
	}
	
}
