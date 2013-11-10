package ua.com.zaibalo.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.hibernate.classic.Session;

import ua.com.zaibalo.db.HibernateUtils;

public class DBFilter implements Filter{

	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		
		Session session = HibernateUtils.getSession();
		session.beginTransaction();
		request.setAttribute("hibernateSession", session);
		
		chain.doFilter(request, response);
		
		session = (Session) request.getAttribute("hibernateSession");
		session.getTransaction().commit();
		session.close();
		
		request.removeAttribute("hibernateSession");
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}

}
