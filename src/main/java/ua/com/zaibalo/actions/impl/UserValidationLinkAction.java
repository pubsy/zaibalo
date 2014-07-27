package ua.com.zaibalo.actions.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ua.com.zaibalo.actions.Action;
import ua.com.zaibalo.constants.ZaibaloConstants;
import ua.com.zaibalo.db.api.UsersDAO;
import ua.com.zaibalo.helper.StringHelper;
import ua.com.zaibalo.helper.ajax.AjaxResponse;
import ua.com.zaibalo.helper.ajax.FailResponse;
import ua.com.zaibalo.helper.ajax.SuccessResponse;
import ua.com.zaibalo.model.User;

@Component
public class UserValidationLinkAction implements Action {
	
	@Autowired
	private UsersDAO usersDAO;
	
	@Override
	public AjaxResponse run(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String name = request.getParameter(AutentificationAction.USER_NAME);
		String token = request.getParameter(AutentificationAction.USER_TOKEN);

		User user = usersDAO.getUserByName(name);
			
		if("".equals(user.getPassword())){
			//TODO redirect to error page
			//User already validated
			return new FailResponse();
		}
		
		if(!user.getToken().equals(token)){
			//TODO redirect to error page
			//Token is not valid
			return new FailResponse();
		}
		
		if(user.getPassword() == null || "".equals(user.getPassword())){
			//TODO int error. cont adm
			return new FailResponse();
		}
		
		if(!user.getPassword().startsWith(AutentificationAction.TEMP_COLON)){
			//TODO int error. cont adm
			return new FailResponse();
		}
		
		String newPassword = user.getPassword().substring(5);
			
		usersDAO.updateUserPassword(user.getId(), newPassword, false);
		usersDAO.updateUserToken(user.getId(), StringHelper.generateString(32));
		
		
		user.setPassword(newPassword);
		request.getSession().setAttribute(ZaibaloConstants.USER_PARAM_NAME, user);
			
		//response.sendRedirect("/");
		
		return new SuccessResponse();

	}
}
