package ua.com.zaibalo.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.com.zaibalo.constants.ZaibaloConstants;

@WebServlet("/logout.do")
public class Logout extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		request.getSession().removeAttribute(ZaibaloConstants.USER_PARAM_NAME);
		
		Cookie[] cookies = request.getCookies();

		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(ZaibaloConstants.ZAIBALO_USER_COOKIE_NAME)) {
				cookie.setMaxAge(0);
				response.addCookie(cookie);
			}
		}

		response.sendRedirect("/");

	}
}
