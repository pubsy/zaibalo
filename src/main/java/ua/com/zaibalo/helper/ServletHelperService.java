package ua.com.zaibalo.helper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ua.com.zaibalo.constants.ZaibaloConstants;
import ua.com.zaibalo.db.api.MessagesDAO;
import ua.com.zaibalo.db.api.UserDetailDAO;
import ua.com.zaibalo.db.api.UsersDAO;
import ua.com.zaibalo.model.User;
import ua.com.zaibalo.model.UserDetail;
import ua.com.zaibalo.model.UserDetail.DetailType;

@Service
@Transactional(propagation=Propagation.REQUIRED)
public class ServletHelperService {
	
	@Autowired
	private UsersDAO usersDAO;
	@Autowired
	private MessagesDAO messagesDAO;
	@Autowired(required=true)
	private UserDetailDAO userDetailDAO;
	
	public static String getClientIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	private User getCookieUser(HttpServletRequest request, HttpServletResponse response){

		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			return null;
		}

		String userCookie = null;

		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(ZaibaloConstants.ZAIBALO_USER_COOKIE_NAME)) {
				try {
					userCookie = URLDecoder.decode(cookie.getValue(), "UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
					// NEVER GONNA HAPPEN)))
				}
			}
		}
		if (userCookie == null) {
			return null;
		}

		int colonIndex = userCookie.indexOf(":");
		if (colonIndex == -1) {
			response.addCookie(new Cookie(ZaibaloConstants.ZAIBALO_USER_COOKIE_NAME, ""));
			return null;
		}
		String userName = userCookie.substring(0, colonIndex);
		String userToken = userCookie.substring(colonIndex + 1);

		User cookieUser = null;

		cookieUser = usersDAO.getUserByName(userName);


		if (cookieUser != null && cookieUser.getToken().equals(userToken)) {
			return cookieUser;
		}

		response.addCookie(new Cookie(ZaibaloConstants.ZAIBALO_USER_COOKIE_NAME, ""));
		return null;
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public User updateUserAuthenication(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession();

		User user = (User)session.getAttribute(ZaibaloConstants.USER_PARAM_NAME);
		if (user != null) {
			return user;
		}

		User cookieUser = getCookieUser(request, response);

		if (cookieUser != null) {
			session.setAttribute(ZaibaloConstants.USER_PARAM_NAME, cookieUser);
			
			String ipAddr = ServletHelperService.getClientIpAddr(request);
			UserDetail userDetail = new UserDetail();
			userDetail.setDetailType(DetailType.IP);
			userDetail.setUser(cookieUser);
			userDetail.setValue(ipAddr);
			userDetailDAO.saveIfNotExists(userDetail);
			
			return cookieUser;
		}
		
		return null;
	}

	public void updateUnreadMessagesStatus(HttpServletRequest request){
		User user = (User)request.getSession().getAttribute(ZaibaloConstants.USER_PARAM_NAME);
		
		if(user == null){
			return;
		}
		
		int count = messagesDAO.getUnreadMessagesCount(user.getId());
		if(count != 0){			
			request.getSession().setAttribute("unreadMailCount", " [" + count + "]");
		}else{
			request.getSession().setAttribute("unreadMailCount", "");
		}
			
	}
	
	public static void redirectHome(HttpServletResponse response){
		try {
			response.sendRedirect("/");
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Internal system error.");
		}
	}
}
