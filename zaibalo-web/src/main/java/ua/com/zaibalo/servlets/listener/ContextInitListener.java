package ua.com.zaibalo.servlets.listener;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.hibernate.classic.Session;

import ua.com.zaibalo.constants.ZaibaloConstants;
import ua.com.zaibalo.db.HibernateUtils;
import ua.com.zaibalo.db.hibernate.HibernateCategorysFacade;
import ua.com.zaibalo.helper.AppProperties;
import ua.com.zaibalo.model.Category;

@WebListener
public class ContextInitListener implements ServletContextListener{
	
	public static Set<String> blackSet;

	@Override
	public void contextInitialized(ServletContextEvent event) {
		ServletContext sc = event.getServletContext();
		
		String basePath = sc.getRealPath("");
		String appConfFileName = sc.getInitParameter("application.conf.file.name");
		String appConfFilePath = basePath + File.separator +"WEB-INF" + File.separator + appConfFileName;
		AppProperties.setAppPropertiesFilePath(appConfFilePath);
		
		String[] blackArray = AppProperties.getProperty(ZaibaloConstants.BLACK_LIST).split(",");
		blackSet = new HashSet<String>(Arrays.asList(blackArray));
		
		HibernateUtils.setUrl(sc.getInitParameter("db.url"));
		HibernateUtils.setUsername(sc.getInitParameter("db.username"));
		HibernateUtils.setPassword(sc.getInitParameter("db.password"));
		
		Session session = HibernateUtils.getSession();
		session.beginTransaction();
		
		List<Category> catList = new HibernateCategorysFacade(session).getCategoriesList(Category.CategoryType.CATEGORY);
		event.getServletContext().setAttribute("categories", catList);
		
		session.getTransaction().commit();
		session.close();

	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		
	}
}
