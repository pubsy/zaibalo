package ua.com.zaibalo.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class AppProperties {
	
	private static String appPropsFilePath;
	private static Properties props = null;
	
	private static void init(){
		props = new Properties();
		File file = new File(appPropsFilePath);
		try {
			props.load(new FileInputStream(file));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getProperty(String key) {
		return props.getProperty(key);
	}
	
	public static void setAppPropertiesFilePath(String path){
			appPropsFilePath = path;
			init();
	}
	
}
