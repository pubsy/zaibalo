package ua.com.zaibalo.actions.impl;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.com.zaibalo.actions.Action;
import ua.com.zaibalo.constants.ZaibaloConstants;
import ua.com.zaibalo.db.DataAccessFactory;
import ua.com.zaibalo.helper.StringHelper;
import ua.com.zaibalo.model.User;

public class UserValidationLinkAction implements Action {
	@Override
	public void run(HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws ServletException, IOException {
		String name = request.getParameter(AutentificationAction.USER_NAME);
		String token = request.getParameter(AutentificationAction.USER_TOKEN);

		DataAccessFactory factory = new DataAccessFactory(request);
		User user = factory.getUsersAccessInstance().getUserByName(name);
			
		if("".equals(user.getPassword())){
			//TODO redirect to error page
			//User already validated
			return;
		}
		
		if(!user.getToken().equals(token)){
			//TODO redirect to error page
			//Token is not valid
			return;
		}
		
		if(user.getPassword() == null || "".equals(user.getPassword())){
			//TODO int error. cont adm
			return;
		}
		
		if(!user.getPassword().startsWith(AutentificationAction.TEMP_COLON)){
			//TODO int error. cont adm
			return;
		}
		
		String newPassword = user.getPassword().substring(5);
			
		factory.getUsersAccessInstance().updateUserPassword(user.getId(), newPassword, false);
		factory.getUsersAccessInstance().updateUserToken(user.getId(), StringHelper.generateString(32));
		
		
		user.setPassword(newPassword);
		request.getSession().setAttribute(ZaibaloConstants.USER_PARAM_NAME, user);
			
		response.sendRedirect("/");

	}
}
