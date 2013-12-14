package ua.com.zaibalo.actions.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.com.zaibalo.actions.Action;
import ua.com.zaibalo.constants.ZaibaloConstants;
import ua.com.zaibalo.db.DataAccessFactory;
import ua.com.zaibalo.helper.MD5Helper;
import ua.com.zaibalo.helper.ServletHelperService;
import ua.com.zaibalo.helper.StringHelper;
import ua.com.zaibalo.helper.ZAppContext;
import ua.com.zaibalo.model.User;

public class AutentificationAction implements Action{
	
	public static final String TEMP_COLON = "TEMP:";
	public static final String USER_NAME ="user_name";
	public static final String USER_TOKEN ="user_token";
	

	@Override
	public void run(HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws ServletException, IOException{
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String remember = request.getParameter("remember");
		
		if(password.length() == 0){
			out.write("{\"status\":\"fail\", \"message\":\"" +  StringHelper.getLocalString("password_cant_be_blank") + "\"}");
			out.close();
			return;
		}
		
		//Validate Latin password
		boolean valid = password.matches("(\\w|\\p{Punct})+");
		if(!valid){
			out.write("{\"status\":\"fail\", \"message\":\"" + StringHelper.getLocalString("password_must_be_latin") + "\"}");
			out.close();
			return;
		}
		
		if(name == null){
			out.write("{\"status\":\"fail\", \"message\":\"" +  StringHelper.getLocalString("user_name_cant_be_blank") + "\"}");
			out.close();
			return;
		}
		
		DataAccessFactory factory = new DataAccessFactory(request);
		
		User user = factory.getUsersAccessInstance().getUserByName(name);

		if(user != null && MD5Helper.getMD5Of(password).equals(user.getPassword())){			
			
			request.getSession().setAttribute(ZaibaloConstants.USER_PARAM_NAME, user);
			
			ZAppContext.getServletHelperService().updateUnreadMessagesStatus(request);
			
			if(remember != null && remember.equals("true")){
				String value = user.getLoginName() + ":" + user.getToken();
				Cookie cookie = new Cookie(ZaibaloConstants.ZAIBALO_USER_COOKIE_NAME, URLEncoder.encode(value, "UTF-8"));
				cookie.setMaxAge(90*24*60*60);
				response.addCookie(cookie);
			}
			
	        out.write("{\"status\":\"success\"}");
	        out.close();
	        return;
			
		}else{
			//wrong pass
			out.write("{\"status\":\"fail\", \"message\":\"" + StringHelper.getLocalString("user_pass_is_incorrect") + "\"}");
	        out.close();
			return;
		}
	}
}
