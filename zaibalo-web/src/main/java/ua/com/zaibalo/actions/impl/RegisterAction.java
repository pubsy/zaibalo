package ua.com.zaibalo.actions.impl;

import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.MessageFormat;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import ua.com.zaibalo.actions.Action;
import ua.com.zaibalo.business.UserBusinessLogic;
import ua.com.zaibalo.db.DataAccessFactory;
import ua.com.zaibalo.helper.MD5Helper;
import ua.com.zaibalo.helper.SendEmailHelper;
import ua.com.zaibalo.helper.StringHelper;
import ua.com.zaibalo.model.User;

public class RegisterAction  implements Action{

	@Override
	public void run(HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws Exception {
		
		String email = request.getParameter("email");
		String login = request.getParameter("register_login");
		
		if(!StringHelper.isValidEmailAddress(email)){
			out.write("{\"status\":\"fail\", \"message\":\""+ StringHelper.getLocalString("invalid_email")  +"\"}");
			return;
		}
		
		DataAccessFactory factory = new DataAccessFactory(request);
		User user = factory.getUsersAccessInstance().getUserByEmail(email);
		
		if(user != null){
			out.write("{\"status\":\"fail\", \"message\":\""+ StringHelper.getLocalString("email_already_registered")  +"\"}");
			return;
		}
		
		if(login == null || "".equals(login)){
			out.write("{\"status\":\"fail\", \"message\":\""+ StringHelper.getLocalString("login_is_blank")  +"\"}");
			return;
		}
		
		user = factory.getUsersAccessInstance().getUserByName(login);
		
		if(user != null){
			out.write("{\"status\":\"fail\", \"message\":\""+ StringHelper.getLocalString("user_name_taken")  +"\"}");
			return;
		}
		
		String newPassword = StringHelper.generateString(10);
		new UserBusinessLogic().addUser(login, email, MD5Helper.getMD5Of(newPassword), login, null, null, factory);

		SendEmailHelper helper = new SendEmailHelper();

		InputStream is = request.getServletContext().getResourceAsStream("html/register.html");

		StringWriter writer = new StringWriter();
		IOUtils.copy(is, writer, "UTF-8");

		String pattern = writer.toString();
		String[] params = { StringHelper.getLocalString("thank_you_for_reg"),
				StringHelper.getLocalString("your_login_is", login),
				StringHelper.getLocalString("your_password_is", newPassword) };
		String body = new MessageFormat(pattern).format(params, new StringBuffer(), null).toString();

		out.write("{\"status\":\"success\", \"message\":\"" + StringHelper.getLocalString("check_your_mail_box") + "\"}");
		out.close();

		MimeMessage message = helper.createMessage(email, StringHelper.getLocalString("zaibalo_registration"), body);
		try {
			helper.send(message);
		} catch (MessagingException ex) {
			System.out.println("ERROR: Sending registration message failed.");
			System.out.println("ERROR: Email: " + email);
			System.out.println("ERROR: Login: " + login);
			throw new Exception("Sending registration message failed.");
		}
		
	}
}
