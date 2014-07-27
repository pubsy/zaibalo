package ua.com.zaibalo.db;

import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import ua.com.zaibalo.model.Category;
import ua.com.zaibalo.model.Comment;
import ua.com.zaibalo.model.CommentRating;
import ua.com.zaibalo.model.Discussion;
import ua.com.zaibalo.model.Message;
import ua.com.zaibalo.model.Post;
import ua.com.zaibalo.model.PostRating;
import ua.com.zaibalo.model.User;

public class HibernateUtils {
	
    private static SessionFactory sessionFactory;
    private static String url;
    private static String username;
    private static String password;

    private static void init() {

    	Properties extraProperties = new Properties();
    	
    	extraProperties.setProperty("hibernate.connection.url", url);
    	extraProperties.setProperty("hibernate.connection.username", username);
    	extraProperties.setProperty("hibernate.connection.password", password);
    	
    	extraProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
    	extraProperties.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
    	
    	extraProperties.setProperty("hibernate.c3p0.min_size", "5");
    	extraProperties.setProperty("hibernate.c3p0.max_size", "20");
		extraProperties.setProperty("hibernate.c3p0.timeout", "1800");
    	extraProperties.setProperty("hibernate.c3p0.max_statements", "50");
    	extraProperties.setProperty("hibernate.c3p0.idle_test_period", "10");
    	extraProperties.setProperty("hibernate.c3p0.acquire_increment", "5");
    	extraProperties.setProperty("connection.provider_class", "org.hibernate.connection.C3P0ConnectionProvider");
    	
        try {
			sessionFactory = new AnnotationConfiguration()
        	.addProperties(extraProperties)
        	.addPackage("ua.com.zaibalo.model")
        	.addAnnotatedClass(Category.class)
        	.addAnnotatedClass(Comment.class)
        	.addAnnotatedClass(CommentRating.class)
        	.addAnnotatedClass(Discussion.class)
        	.addAnnotatedClass(Message.class)
        	.addAnnotatedClass(Post.class)
        	.addAnnotatedClass(PostRating.class)
        	.addAnnotatedClass(User.class)
            .buildSessionFactory();
        } catch (HibernateException ex) {
            throw new ExceptionInInitializerError(ex);
        }

    }

    public static Session getSession() {
    	if(sessionFactory == null){
    		init();
    	}
        return sessionFactory.openSession();
    }

	public static void setUrl(String url) {
		HibernateUtils.url = url;
	}

	public static void setUsername(String username) {
		HibernateUtils.username = username;
	}

	public static void setPassword(String password) {
		HibernateUtils.password = password;
	}

}