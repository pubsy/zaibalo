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
import ua.com.zaibalo.db.DataAccessFactory;
import ua.com.zaibalo.helper.AppProperties;
import ua.com.zaibalo.helper.SendEmailHelper;
import ua.com.zaibalo.helper.StringHelper;
import ua.com.zaibalo.model.User;

public class RemindPasswordAction implements Action {

	private static final long serialVersionUID = 1L;

	@Override
	public void run(HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws Exception {
		String userName = request.getParameter("userName");

		DataAccessFactory factory = new DataAccessFactory(request);
		User user = factory.getUsersAccessInstance().getUserByName(userName);
		if (user == null) {
			out.write("{\"status\":\"success\", \"message\":\"" + StringHelper.getLocalString("user_doesnt_exist") + "\"}");
			out.close();
			return;
		}

		String newPassword = StringHelper.generateString(10);
		factory.getUsersAccessInstance().updateUserPassword(user.getId(), newPassword);

		
		InputStream is = request.getServletContext().getResourceAsStream("html/password_reminder.html");
		StringWriter writer = new StringWriter();
		IOUtils.copy(is, writer, "UTF-8");
		String pattern = writer.toString();
		String link = "http://" + AppProperties.getProperty("app_server_address") + "/secure/profileSettings.do";

		String[] params = { 
				StringHelper.getLocalString("your_pass_is_reset", newPassword),
				StringHelper.getLocalString("you_can_change_your_pass", link) 
			};
		String body = new MessageFormat(pattern).format(params, new StringBuffer(), null).toString();

		SendEmailHelper helper = new SendEmailHelper();
		MimeMessage message = helper.createMessage(
				user.getEmail(),
				StringHelper.getLocalString("zaibalo_pass_remind_subj"), body
			);

		out.write("{\"status\":\"success\", \"message\":\"" + StringHelper.getLocalString("reminder_mess_sent", user.getPartlyHiddenEmail()) + "\"}");
		out.close();

		try {
			helper.send(message);
		} catch (MessagingException ex) {
			System.out.println("ERROR: Sending reminder message failed.");
			System.out.println("ERROR: Email: " + user.getEmail());
			System.out.println("ERROR: Login: " + user.getLoginName());
			throw new Exception("Sending reminder message failed.");
		}

	}

}
