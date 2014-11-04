package ua.com.zaibalo.security;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ua.com.zaibalo.business.UsersBusinessLogic;
import ua.com.zaibalo.helper.MD5Helper;
import ua.com.zaibalo.helper.StringHelper;
import ua.com.zaibalo.model.User;

@Controller
public class SecurityController {
    
    @Autowired
    private UsersBusinessLogic usersBusinessLogic;
    
    @Autowired
    private SecurityService securityService;
    
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(@RequestParam(required = false, defaultValue = "/") String redirect, 
			HttpServletRequest request) {
		if(securityService.isAuthenticated(request)){
			return "redirect:" + redirect;
		}
		request.setAttribute("successRedirectURL", redirect);
		return "login";
	}

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public String authenticate(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
    	
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String rememberMe = request.getParameter("remember_me");
        String redirectURI = request.getParameter("successRedirectURL");

        if(StringHelper.isBlank(username) || StringHelper.isBlank(password)){
        	return forwardToLoginPage(request, redirectURI);
        }

        User user = usersBusinessLogic.getUserByLoginName(username);
        
        if(user == null){
        	return forwardToLoginPage(request, redirectURI);
        }
        
        if(!user.getPassword().equals(MD5Helper.getMD5Of(password))){
        	return forwardToLoginPage(request, redirectURI);
        }
        
        securityService.addUserToSession(request, user);
        
        if(!StringHelper.isBlank(rememberMe)){
        	securityService.addRememberMeCookie(response, user);
        }
        
        return "redirect:" + redirectURI;
    }

	private String forwardToLoginPage(HttpServletRequest request, String redirectURI) {
		request.setAttribute("authenticationError", StringHelper.getLocalString("user_pass_is_incorrect"));
		return "redirect:/login?redirect=" + redirectURI;
	}

	@RequestMapping(value = { "/logout" }, method = RequestMethod.GET)
	public String logout(HttpServletRequest request,
			HttpServletResponse response) {
		request.getSession().invalidate();
		securityService.removeRememberMeCookie(request, response);
		return "redirect:/";
	}

}
