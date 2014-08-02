package ua.com.zaibalo.servlets.listener;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import ua.com.zaibalo.constants.ZaibaloConstants;

public class ContextInitListener implements ServletContextListener {

	public static Set<String> blackSet;
	private static Properties applicationProperties;

	@Override
	public void contextInitialized(ServletContextEvent event) {
		Resource resource = new ClassPathResource("/zaibalo.properties");
		try {
			applicationProperties = PropertiesLoaderUtils.loadProperties(resource);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String[] blackArray = ContextInitListener.getProperty(ZaibaloConstants.BLACK_LIST).split(",");
		blackSet = new HashSet<String>(Arrays.asList(blackArray));
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {}

	public static String getProperty(String key) {
		return applicationProperties.getProperty(key);
	}
	
}
