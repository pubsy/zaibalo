package ua.com.zaibalo.servlets.pages;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ua.com.zaibalo.constants.ZaibaloConstants;

@Component
@Transactional(propagation=Propagation.REQUIRED)
public class LogoutRedirect {

	public String run(HttpServletRequest request,
			HttpServletResponse response){
		request.getSession().removeAttribute(ZaibaloConstants.USER_PARAM_NAME);
		
		Cookie[] cookies = request.getCookies();
		
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(ZaibaloConstants.ZAIBALO_USER_COOKIE_NAME)) {
				cookie.setMaxAge(0);
				response.addCookie(cookie);
			}
		}
		
		return "redirect:/";
	}
}
