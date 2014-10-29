package ua.com.zaibalo.security;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ua.com.zaibalo.business.UsersBusinessLogic;
import ua.com.zaibalo.constants.ZaibaloConstants;
import ua.com.zaibalo.helper.MD5Helper;
import ua.com.zaibalo.helper.StringHelper;
import ua.com.zaibalo.model.User;

@Controller
public class SecurityController {
    
    @Autowired
    private UsersBusinessLogic usersBusinessLogic;
    
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(HttpServletRequest request) {
		return "login";
	}

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public String authenticate(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String rememberMe = request.getParameter("remember_me");
        
        if(StringHelper.isBlank(username) || StringHelper.isBlank(password)){
            return "redirect:/login";
        }

        User user = usersBusinessLogic.getUserByLoginName(username);
        
        if(user == null){
            return "redirect:/login";
        }
        
        if(!user.getPassword().equals(MD5Helper.getMD5Of(password))){
            return "redirect:/login";
        }
        
        request.getSession().setAttribute(ZaibaloConstants.USER_PARAM_NAME, user);
        
        if(StringHelper.isBlank(rememberMe)){
            String value = user.getLoginName() + ":" + user.getToken();
            String cookieName = "SPRING_SECURITY_REMEMBER_ME_COOKIE";
            Cookie cookie = new Cookie(cookieName, URLEncoder.encode(value, "UTF-8"));
            cookie.setMaxAge(90*24*60*60);
            response.addCookie(cookie);
        }
        
        return "redirect:/";
    }

	@RequestMapping(value = { "/logout" }, method = RequestMethod.GET)
	public String logout(HttpServletRequest request,
			HttpServletResponse response) {
		request.getSession().invalidate();
		cancelCookie(request, response);
		return "redirect:/";
	}

	void cancelCookie(HttpServletRequest request, HttpServletResponse response) {
		String cookieName = "SPRING_SECURITY_REMEMBER_ME_COOKIE";
		Cookie cookie = new Cookie(cookieName, null);
		cookie.setMaxAge(0);
		boolean hasLength = StringUtils.hasLength(request.getContextPath());
		cookie.setPath(hasLength ? request.getContextPath() : "/");
		response.addCookie(cookie);
	}
}
