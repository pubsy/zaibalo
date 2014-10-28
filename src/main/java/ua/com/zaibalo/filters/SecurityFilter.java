package ua.com.zaibalo.filters;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import ua.com.zaibalo.constants.ZaibaloConstants;
import ua.com.zaibalo.security.Secured;

public class SecurityFilter  implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object object) throws Exception {
		if(!isSecuredController(object)){
			return true;
		}
		
		return isAuthenticated(request);
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
