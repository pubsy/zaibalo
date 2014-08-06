package ua.com.zaibalo.spring;

import java.util.Properties;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class SpringPropertiesUtil extends PropertyPlaceholderConfigurer implements InitializingBean {
	
	private static Properties properties;
	
    public void afterPropertiesSet(){
        try{
        	SpringPropertiesUtil.properties = this.mergeProperties();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public static String getProperty(String key){
    	return SpringPropertiesUtil.properties.getProperty(key);
    }
}
