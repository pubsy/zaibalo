package ua.com.zaibalo.actions.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ua.com.zaibalo.actions.Action;
import ua.com.zaibalo.business.UsersBusinessLogic;
import ua.com.zaibalo.db.api.UsersDAO;
import ua.com.zaibalo.email.SendEmailService;
import ua.com.zaibalo.email.templates.RegisterUserMessage;
import ua.com.zaibalo.helper.MD5Helper;
import ua.com.zaibalo.helper.StringHelper;
import ua.com.zaibalo.helper.ajax.AjaxResponse;
import ua.com.zaibalo.helper.ajax.FailResponse;
import ua.com.zaibalo.model.User;

@Component
public class RegisterAction  implements Action{

	@Autowired
	private UsersDAO usersDAO;
	
	@Autowired
	private UsersBusinessLogic userBusinessLogic;
	
	@Autowired
	private SendEmailService sendEmailService;
	
	@Override
	public AjaxResponse run(HttpServletRequest request, HttpServletResponse response) {
		
		final String email = request.getParameter("email");
		final String login = request.getParameter("register_login");
		
		if(!StringHelper.isValidEmailAddress(email)){
			return new FailResponse(StringHelper.getLocalString("invalid_email"));
		}
		
		User user = usersDAO.getUserByEmail(email);
		
		if(user != null){
			return new FailResponse(StringHelper.getLocalString("email_already_registered"));
		}
		
		if(login == null || "".equals(login)){
			return new FailResponse(StringHelper.getLocalString("login_is_blank"));
		}
		
		user = usersDAO.getUserByLoginOrDisplayName(login);
		
		if(user != null){
			return new FailResponse(StringHelper.getLocalString("user_name_taken"));
		}
		
		String newPassword = StringHelper.generateString(10);
		userBusinessLogic.createUser(login, email, MD5Helper.getMD5Of(newPassword), login, null, null);

		RegisterUserMessage message = new RegisterUserMessage(email);
		message.setLogin(login);
		message.setNewPassword(newPassword);
		sendEmailService.sendEmail(message);

		return new SuccessMessageResponse(StringHelper.getLocalString("check_your_mail_box"));
	}
	
	class SuccessMessageResponse extends AjaxResponse{

		private String message;
		
		public SuccessMessageResponse(String message) {
			super(true);
			this.message = message;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}
		
	}
}
