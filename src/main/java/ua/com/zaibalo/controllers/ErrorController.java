package ua.com.zaibalo.controllers;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import ua.com.zaibalo.constants.ZaibaloConstants;
import ua.com.zaibalo.helper.ServletHelperService;
import ua.com.zaibalo.model.User;

@ControllerAdvice
public class ErrorController {

	private final static Logger LOGGER = Logger.getLogger(ErrorController.class);
	
	//@ExceptionHandler({RuntimeException.class})
	public String handleError(HttpServletRequest request, Exception exception) {
		LOGGER.error("Request: " + request.getRequestURL() + ((request.getQueryString() == null) ? "" : "?" + request.getQueryString()));
		LOGGER.error("From IP: " + ServletHelperService.getClientIpAddr(request));
		Map<String, String[]> parameterMap = request.getParameterMap();
		if(!parameterMap.isEmpty()){
			LOGGER.error("Parameters:");
			for(String paramKey : parameterMap.keySet()){
				LOGGER.error(paramKey);
				LOGGER.error(": ");
				String[] par = parameterMap.get(paramKey);
				for(String str: par){
					LOGGER.error(str);
				}
			}
		}
		
		User user = (User)request.getSession().getAttribute(ZaibaloConstants.USER_PARAM_NAME);
		if(user != null){
			LOGGER.error("Logged in user: " + user.getLoginName() + " id: " + user.getId());
		}
		
		LOGGER.error("Exception details", exception);
		
		return "error/error";
	}
}
