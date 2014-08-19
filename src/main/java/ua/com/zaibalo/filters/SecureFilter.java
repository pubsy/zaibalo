package ua.com.zaibalo.filters;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import ua.com.zaibalo.helper.ServletHelperService;
import ua.com.zaibalo.helper.StringHelper;
import ua.com.zaibalo.model.User;

public class SecureFilter implements HandlerInterceptor{

	@Autowired
	private ServletHelperService servletHelperService;

	@Override
	public boolean preHandle(HttpServletRequest httpRequest, HttpServletResponse httpResponse,
			Object arg2) throws Exception {
		User user = servletHelperService.updateUserAuthenication(httpRequest, httpResponse);
		
		if(user == null){
			throw new RuntimeException(StringHelper.getLocalString("please_authorise"));
		}
		
		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
	}

}
