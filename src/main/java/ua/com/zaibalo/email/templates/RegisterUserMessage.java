package ua.com.zaibalo.email.templates;

import ua.com.zaibalo.helper.StringHelper;

public class RegisterUserMessage extends AbstractMessage{

	private String login;
	private String newPassword;
	
	public RegisterUserMessage(String recipientAddress){
		super(recipientAddress);
		this.subjectKey = "zaibalo_registration";
		this.templatePath = "templates/register.html";
	}

	@Override
	public String[] getParameters() {
		return new String[] { StringHelper.getLocalString("thank_you_for_reg"),
				StringHelper.getLocalString("your_login_is", this.login),
				StringHelper.getLocalString("your_password_is", this.newPassword) };
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	
}
