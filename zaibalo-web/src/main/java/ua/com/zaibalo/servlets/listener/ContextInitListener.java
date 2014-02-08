package ua.com.zaibalo.servlets.listener;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import ua.com.zaibalo.constants.ZaibaloConstants;
import ua.com.zaibalo.helper.AppProperties;

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

	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		
	}

}
