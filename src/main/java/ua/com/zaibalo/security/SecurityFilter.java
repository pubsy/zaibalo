package ua.com.zaibalo.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;

import ua.com.zaibalo.constants.ZaibaloConstants;

public class SecurityFilter  implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {

        if (isAuthenticated(request) || isNotHadlerMethodObject(object) || isNotAController(object) || isNotASecuredController(object)) {
            return true;
        }

        // Authentication needed
        HandlerMethod method = (HandlerMethod) object;
        if(isProducingApplicationJson(method)){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        
        ModelAndView mv = new ModelAndView("redirect:/login");
        throw new ModelAndViewDefiningException(mv);
    }

	private boolean isNotHadlerMethodObject(Object object) {
        return !(object instanceof HandlerMethod);
    }

    private boolean isProducingApplicationJson(Object object) {
        HandlerMethod method = (HandlerMethod) object;
        String[] produces = method.getMethodAnnotation(RequestMapping.class).produces();
        return ArrayUtils.contains(produces, "application/json; charset=UTF-8");
    }

    private boolean isNotAController(Object object) {
        HandlerMethod method = (HandlerMethod) object;
		return !method.getBean().getClass().isAnnotationPresent(Controller.class);
	}

	private boolean isAuthenticated(HttpServletRequest request) {
		return request.getSession().getAttribute(ZaibaloConstants.USER_PARAM_NAME) != null;
	}

	private boolean isNotASecuredController(Object object) {
	    HandlerMethod method = (HandlerMethod) object;
		return !method.getMethod().isAnnotationPresent(Secured.class);
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
