package ua.com.zaibalo.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;
import org.springframework.stereotype.Controller;

import ua.com.zaibalo.constants.ZaibaloConstants;

public class SecurityFilter  implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object object) throws Exception {
		if(!isController(object)){
			return true;
		}
		
		if(!isSecuredController(object)){
			return true;
		}
		
		if(isAuthenticated(request)){
			return true;
		}
		
		ModelAndView mv = new ModelAndView("redirect:/login"); 
		ModelAndViewDefiningException mvde = new ModelAndViewDefiningException(mv); 
		throw mvde; 
	}

	private boolean isController(Object object) {
		if(object instanceof HandlerMethod){
			HandlerMethod method = (HandlerMethod)object;
			if(method.getBean().getClass().getAnnotation(Controller.class) != null){
				return true;
			}
		}
		return false;
	}

	private boolean isAuthenticated(HttpServletRequest request) {
		return request.getSession().getAttribute(ZaibaloConstants.USER_PARAM_NAME) != null;
	}

	private boolean isSecuredController(Object object) {
		if(object instanceof HandlerMethod){
			HandlerMethod method = (HandlerMethod)object;
			if(method.getMethod().getAnnotation(Secured.class) != null){
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object object, Exception exception)
			throws Exception {
		
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response,
			Object object, ModelAndView modelAndView) throws Exception {
		
	}

}
