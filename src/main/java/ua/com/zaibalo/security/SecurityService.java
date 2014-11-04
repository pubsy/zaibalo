package ua.com.zaibalo.security;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import ua.com.zaibalo.business.UsersBusinessLogic;
import ua.com.zaibalo.constants.ZaibaloConstants;
import ua.com.zaibalo.model.User;

@Service
public class SecurityService {
	
    @Autowired
    private UsersBusinessLogic usersBusinessLogic;
	
	public User getRememberMeCookieUser(HttpServletRequest request){
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			return null;
		}

		String userNameAndToken = null;
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(ZaibaloConstants.REMEBER_ME)) {
				userNameAndToken = getDecodedValue(cookie.getValue());
			}
		}
		if (StringUtils.isEmpty(userNameAndToken)) {
			return null;
		}

		int colonIndex = userNameAndToken.indexOf(":");
		if (colonIndex == -1) {
			return null;
		}
		String userName = userNameAndToken.substring(0, colonIndex);
		String userToken = userNameAndToken.substring(colonIndex + 1);

		User cookieUser = usersBusinessLogic.getUserByLoginName(userName);
		if (cookieUser != null && cookieUser.getToken().equals(userToken)) {
			return cookieUser;
		}

		return null;
	}

	public void addRememberMeCookie(HttpServletResponse response, User user)
			throws UnsupportedEncodingException {
		String value = user.getLoginName() + ":" + user.getToken();
		Cookie cookie = new Cookie(ZaibaloConstants.REMEBER_ME, URLEncoder.encode(value, "UTF-8"));
		cookie.setMaxAge(90*24*60*60);
		response.addCookie(cookie);
	}

	public void removeRememberMeCookie(HttpServletRequest request, HttpServletResponse response) {
		Cookie cookie = new Cookie(ZaibaloConstants.REMEBER_ME, null);
		cookie.setMaxAge(0);
		boolean hasLength = StringUtils.hasLength(request.getContextPath());
		cookie.setPath(hasLength ? request.getContextPath() : "/");
		response.addCookie(cookie);
	}
	
	public void addUserToSession(HttpServletRequest request, User user){
		request.getSession().setAttribute(ZaibaloConstants.USER_PARAM_NAME, user);
	}
	
	public boolean isAuthenticated(HttpServletRequest request){
		return getAuthenticatedUser(request) != null;
	}
	
	public User getAuthenticatedUser(HttpServletRequest request){
		User user = (User)request.getSession().getAttribute(ZaibaloConstants.USER_PARAM_NAME);
		if(user != null){
			return user;
		}
		user = getRememberMeCookieUser(request);
		if(user != null){
			addUserToSession(request, user);
		}
		return user;
	}
	
	private String getDecodedValue(String value){
		try {
			return URLDecoder.decode(value, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage());
		}
	}
}
