package ua.com.zaibalo.servlets.pages.secure;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import ua.com.zaibalo.constants.ZaibaloConstants;
import ua.com.zaibalo.helper.StringHelper;
import ua.com.zaibalo.model.User;
import ua.com.zaibalo.servlets.pages.ServletPage;

@Component
public class ProfileSettingsServlet extends ServletPage {

	@Override
	public String runInternal(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		User user = (User)request.getSession().getAttribute(ZaibaloConstants.USER_PARAM_NAME);
		
		request.setAttribute("profilePiscturePath", User.USERPHOTO_DIR_PATH + user.getBigImgPath());
		request.setAttribute("pageTitle", getPageTitle(StringHelper.getLocalString("profile_settings")));
		
		return "profile_settings";
	}
}
