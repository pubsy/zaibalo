package ua.com.zaibalo.actions.impl;

import java.io.InputStream;
import java.io.StringWriter;
import java.text.MessageFormat;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ua.com.zaibalo.actions.Action;
import ua.com.zaibalo.business.UserBusinessLogic;
import ua.com.zaibalo.db.api.UsersDAO;
import ua.com.zaibalo.helper.MD5Helper;
import ua.com.zaibalo.helper.SendEmailHelper;
import ua.com.zaibalo.helper.StringHelper;
import ua.com.zaibalo.helper.ajax.AjaxResponse;
import ua.com.zaibalo.helper.ajax.FailResponse;
import ua.com.zaibalo.model.User;

@Component
public class RegisterAction  implements Action{

	@Autowired
	private UsersDAO usersDAO;
	
	@Autowired
	private UserBusinessLogic userBusinessLogic;
	
	@Override
	public AjaxResponse run(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
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
		
		user = usersDAO.getUserByName(login);
		
		if(user != null){
			return new FailResponse(StringHelper.getLocalString("user_name_taken"));
		}
		
		String newPassword = StringHelper.generateString(10);
		userBusinessLogic.addUser(login, email, MD5Helper.getMD5Of(newPassword), login, null, null);

		final SendEmailHelper helper = new SendEmailHelper();

		InputStream is = request.getServletContext().getResourceAsStream("html/register.html");

		StringWriter writer = new StringWriter();
		IOUtils.copy(is, writer, "UTF-8");

		String pattern = writer.toString();
		String[] params = { StringHelper.getLocalString("thank_you_for_reg"),
				StringHelper.getLocalString("your_login_is", login),
				StringHelper.getLocalString("your_password_is", newPassword) };
		String body = new MessageFormat(pattern).format(params, new StringBuffer(), null).toString();

		final MimeMessage message = helper.createMessage(email, StringHelper.getLocalString("zaibalo_registration"), body);
		new Thread(new Runnable(){
			@Override
			public void run() {
				try {
					helper.send(message);
				} catch (MessagingException ex) {
					System.out.println("ERROR: Sending registration message failed.");
					System.out.println("ERROR: Email: " + email);
					System.out.println("ERROR: Login: " + login);
					throw new RuntimeException("Sending registration message failed.");
				}
			}
		});

		return new SuccessMessageResponse(StringHelper.getLocalString("check_your_mail_box"));
	}
	
	class SuccessMessageResponse extends AjaxResponse{

		private String message;
		
		public SuccessMessageResponse(String message) {
			super(true);
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}
		
	}
}
