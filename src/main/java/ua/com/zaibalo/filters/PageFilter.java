package ua.com.zaibalo.filters;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import ua.com.zaibalo.business.PageFilterBusinessLogic;

public class PageFilter implements HandlerInterceptor{

	@Autowired
	private PageFilterBusinessLogic pageFilterBusinessLogic;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object object) throws Exception {
		//String loggedInUserName = SecurityContextHolder.getContext().getAuthentication().getName();
		pageFilterBusinessLogic.prePage(request, null);
		
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
