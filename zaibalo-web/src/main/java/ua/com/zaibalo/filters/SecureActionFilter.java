package ua.com.zaibalo.filters;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.com.zaibalo.helper.ServletHelperService;
import ua.com.zaibalo.helper.StringHelper;
import ua.com.zaibalo.helper.ZAppContext;
import ua.com.zaibalo.model.User;

public class SecureActionFilter implements Filter{

	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		HttpServletResponse httpResponse = (HttpServletResponse)response;
		PrintWriter out = response.getWriter();  
		
		User user = ZAppContext.getServletHelperService().checkUserAuthorised(httpRequest, httpResponse);
		
		if(user == null){
			out.write("{\"status\":\"fail\", \"message\":\"" + StringHelper.getLocalString("please_authorise") + "\"}");
			out.close();
			return;
		}
		
		chain.doFilter(httpRequest, httpResponse);
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}

}
