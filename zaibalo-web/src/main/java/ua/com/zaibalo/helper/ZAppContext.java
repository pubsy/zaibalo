package ua.com.zaibalo.helper;

import org.hibernate.SessionFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import ua.com.zaibalo.db.api.CategoriesDAO;
import ua.com.zaibalo.db.api.CommentRatingsDAO;
import ua.com.zaibalo.db.api.CommentsDAO;
import ua.com.zaibalo.db.api.DiscussionsDAO;
import ua.com.zaibalo.db.api.MessagesDAO;
import ua.com.zaibalo.db.api.PostRatingsDAO;
import ua.com.zaibalo.db.api.PostsDAO;
import ua.com.zaibalo.db.api.UsersDAO;

@Service
public class ZAppContext implements ApplicationContextAware{

	private static CategoriesDAO categoriesDao;
	private static CommentsDAO commentsDAO;
	private static CommentRatingsDAO commentRatingsDAO;
	private static DiscussionsDAO discussionsDAO;
	private static MessagesDAO messagesDAO;
	private static PostRatingsDAO postRatingsDAO;
	private static PostsDAO postsDAO;
	private static UsersDAO usersDAO;
	private static ServletHelperService servletHelperService;
	//private static SessionFactory sessionFactory;
	
	@Override
	public void setApplicationContext(
			org.springframework.context.ApplicationContext context)
			throws BeansException {
		categoriesDao = context.getBean(CategoriesDAO.class);
		commentsDAO = context.getBean(CommentsDAO.class);
		commentRatingsDAO = context.getBean(CommentRatingsDAO.class);
		discussionsDAO = context.getBean(DiscussionsDAO.class);
		messagesDAO = context.getBean(MessagesDAO.class);
		postRatingsDAO = context.getBean(PostRatingsDAO.class);
		postsDAO = context.getBean(PostsDAO.class);
		usersDAO = context.getBean(UsersDAO.class);
		servletHelperService = context.getBean(ServletHelperService.class);
		//sessionFactory = context.getBean(SessionFactory.class);
	}

	public static CategoriesDAO getCategoriesDao() {
		return categoriesDao;
	}

	public static CommentsDAO getCommentsDAO() {
		return commentsDAO;
	}

	public static CommentRatingsDAO getCommentRatingsDAO() {
		return commentRatingsDAO;
	}

	public static DiscussionsDAO getDiscussionsDAO() {
		return discussionsDAO;
	}

	public static MessagesDAO getMessagesDAO() {
		return messagesDAO;
	}

	public static PostRatingsDAO getPostRatingsDAO() {
		return postRatingsDAO;
	}

	public static PostsDAO getPostsDAO() {
		return postsDAO;
	}

	public static UsersDAO getUsersDAO() {
		return usersDAO;
	}
	
	public static ServletHelperService getServletHelperService() {
		return servletHelperService;
	}
}
