package ua.com.zaibalo.actions.impl;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ua.com.zaibalo.actions.Action;
import ua.com.zaibalo.db.api.UsersDAO;
import ua.com.zaibalo.email.SendEmailService;
import ua.com.zaibalo.email.templates.RemindPasswordMessage;
import ua.com.zaibalo.helper.StringHelper;
import ua.com.zaibalo.helper.ajax.AjaxResponse;
import ua.com.zaibalo.helper.ajax.FailResponse;
import ua.com.zaibalo.helper.ajax.SuccessResponse;
import ua.com.zaibalo.model.User;

@Component
public class RemindPasswordAction implements Action {

	@Autowired
	private UsersDAO usersDAO;
	
	@Autowired
	private SendEmailService sendEmailService;
	
	@Override
	public AjaxResponse run(HttpServletRequest request, HttpServletResponse response) throws IOException, AddressException, MessagingException {
		String userName = request.getParameter("userName");

		final User user = usersDAO.getUserByLoginName(userName);
		if (user == null) {
			return new FailResponse(StringHelper.getLocalString("user_doesnt_exist"));
		}

		String newPassword = StringHelper.generateString(10);
		usersDAO.updateUserPassword(user, newPassword);

		RemindPasswordMessage message = new RemindPasswordMessage(user.getEmail());
		message.setNewPassword(newPassword);
		sendEmailService.sendEmail(message);

		return new SuccessResponse(StringHelper.getLocalString("reminder_mess_sent", user.getPartlyHiddenEmail()));
	}

}
