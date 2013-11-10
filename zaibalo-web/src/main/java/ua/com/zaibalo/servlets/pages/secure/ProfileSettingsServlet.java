package ua.com.zaibalo.servlets.pages.secure;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.com.zaibalo.constants.ZaibaloConstants;
import ua.com.zaibalo.helper.StringHelper;
import ua.com.zaibalo.model.User;
import ua.com.zaibalo.servlets.pages.ServletPage;

@WebServlet(name="ProfileSettingsPage", urlPatterns={"/secure/profileSettings.do"})
public class ProfileSettingsServlet extends ServletPage {

	private static final long serialVersionUID = 1L;

	@Override
	public String run(HttpServletRequest request, HttpServletResponse response,
			PrintWriter out) throws ServletException, IOException{
		
		User user = (User)request.getSession().getAttribute(ZaibaloConstants.USER_PARAM_NAME);
		
		request.setAttribute("profilePiscturePath", User.USERPHOTO_DIR_PATH + user.getBigImgPath());
		request.setAttribute("pageTitle", getPageTitle(StringHelper.getLocalString("profile_settings")));
		
		return "/jsp/profile_settings.jsp";
	}
}
