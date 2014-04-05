package ua.com.zaibalo.servlets.pages.secure;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import ua.com.zaibalo.constants.ZaibaloConstants;
import ua.com.zaibalo.helper.StringHelper;
import ua.com.zaibalo.model.User;

@Component
public class ProfileSettingsServlet {

	public String run(HttpServletRequest request, HttpServletResponse response) {
		
		User user = (User)request.getSession().getAttribute(ZaibaloConstants.USER_PARAM_NAME);
		
		request.setAttribute("profilePiscturePath", User.USERPHOTO_DIR_PATH + user.getBigImgPath());
		request.setAttribute("pageTitle",
				StringHelper.getLocalString("zaibalo_blog") + " " +
				StringHelper.getLocalString("profile_settings"));
		
		return "profile_settings";
	}
}
