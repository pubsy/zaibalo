package ua.com.zaibalo.controllers;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AuthenticationController {
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(HttpServletRequest request) {
		return "login";
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
