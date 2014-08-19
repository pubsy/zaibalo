package ua.com.zaibalo.actions.impl;

import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ua.com.zaibalo.actions.Action;
import ua.com.zaibalo.constants.ZaibaloConstants;
import ua.com.zaibalo.db.api.UserDetailDAO;
import ua.com.zaibalo.db.api.UsersDAO;
import ua.com.zaibalo.helper.MD5Helper;
import ua.com.zaibalo.helper.ServletHelperService;
import ua.com.zaibalo.helper.StringHelper;
import ua.com.zaibalo.helper.ajax.AjaxResponse;
import ua.com.zaibalo.helper.ajax.FailResponse;
import ua.com.zaibalo.helper.ajax.SuccessResponse;
import ua.com.zaibalo.model.User;
import ua.com.zaibalo.model.UserDetail;
import ua.com.zaibalo.model.UserDetail.DetailType;

@Component
public class AutentificationAction implements Action{
	
	public static final String TEMP_COLON = "TEMP:";
	public static final String USER_NAME ="user_name";
	public static final String USER_TOKEN ="user_token";
	
	@Autowired
	private UsersDAO usersDAO;
	@Autowired
	private ServletHelperService servletHelperService;
	@Autowired
	private UserDetailDAO userDetailDAO;

	@Override
	public AjaxResponse run(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String remember = request.getParameter("remember");
		
		if(password.length() == 0){
			return new FailResponse(StringHelper.getLocalString("password_cant_be_blank"));
		}
		
		//Validate Latin password
		boolean valid = password.matches("(\\w|\\p{Punct})+");
		if(!valid){
			return new FailResponse(StringHelper.getLocalString("password_must_be_latin"));
		}
		
		if(name == null){
			return new FailResponse(StringHelper.getLocalString("user_name_cant_be_blank"));
		}
		
		User user = usersDAO.getUserByName(name);

		if(user != null && MD5Helper.getMD5Of(password).equals(user.getPassword())){			
			
			request.getSession().setAttribute(ZaibaloConstants.USER_PARAM_NAME, user);
			
			servletHelperService.updateUnreadMessagesStatus(request);
			
			if(remember != null && remember.equals("true")){
				String value = user.getLoginName() + ":" + user.getToken();
				Cookie cookie = new Cookie(ZaibaloConstants.ZAIBALO_USER_COOKIE_NAME, URLEncoder.encode(value, "UTF-8"));
				cookie.setMaxAge(90*24*60*60);
				response.addCookie(cookie);
			}
			
			String ipAddr = ServletHelperService.getClientIpAddr(request);
			UserDetail userDetail = new UserDetail();
			userDetail.setDetailType(DetailType.IP);
			userDetail.setUser(user);
			userDetail.setValue(ipAddr);
			userDetailDAO.saveIfNotExists(userDetail);
			
			return new SuccessResponse();
			
		}else{
			//wrong pass
			return new FailResponse(StringHelper.getLocalString("user_pass_is_incorrect"));
		}
	}
}
