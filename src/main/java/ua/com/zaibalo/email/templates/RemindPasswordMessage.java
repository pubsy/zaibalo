package ua.com.zaibalo.email.templates;

import ua.com.zaibalo.helper.StringHelper;
import ua.com.zaibalo.spring.SpringPropertiesUtil;


public class RemindPasswordMessage extends AbstractMessage{
	
	private String newPassword;
	
	public RemindPasswordMessage(String recipientAddress){
		super(recipientAddress);
		this.subjectKey = "zaibalo_pass_remind_subj";
		this.templatePath = "templates/password_reminder.html";
	}

	@Override
	public String[] getParameters() {
		String link = "http://" + SpringPropertiesUtil.getProperty("app.server.domain") + "/secure/settings";
		
		return new String[] { 
				StringHelper.getLocalString("your_pass_is_reset", newPassword),
				StringHelper.getLocalString("you_can_change_your_pass", link) };
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	
}
