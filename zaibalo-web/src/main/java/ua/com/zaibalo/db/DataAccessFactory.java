package ua.com.zaibalo.db;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.classic.Session;

import ua.com.zaibalo.db.hibernate.HibernateCategorysFacade;
import ua.com.zaibalo.db.hibernate.HibernateCommentRatingsFacade;
import ua.com.zaibalo.db.hibernate.HibernateCommentsFacade;
import ua.com.zaibalo.db.hibernate.HibernateDiscussionsFacade;
import ua.com.zaibalo.db.hibernate.HibernateMessagesFacade;
import ua.com.zaibalo.db.hibernate.HibernatePostRatingsFacade;
import ua.com.zaibalo.db.hibernate.HibernatePostsFacade;
import ua.com.zaibalo.db.hibernate.HibernateUsersFacade;

public class DataAccessFactory {

	private Session session;
	
	public DataAccessFactory(HttpServletRequest request) {
		this.session = (Session) request.getAttribute("hibernateSession");
	}

	public HibernateUsersFacade getUsersAccessInstance() {
		return new HibernateUsersFacade(session);
	}

	public HibernateCommentsFacade getCommentsAccessInstance() {
		return new HibernateCommentsFacade(session);
	}

	public HibernatePostsFacade getPostsAccessInstance() {
		return new HibernatePostsFacade(session);
	}

	public HibernateCategorysFacade getCategorysAccessInstance() {
		return new HibernateCategorysFacade(session);
	}

	public HibernateCommentRatingsFacade getCommentRatingAccessInstance() {
		return new HibernateCommentRatingsFacade(session);
	}

	public HibernatePostRatingsFacade getPostRatingsAccessInstance() {
		return new HibernatePostRatingsFacade(session);
	}

	public HibernateDiscussionsFacade getDiscussionsAccessInstance() {
		return new HibernateDiscussionsFacade(session);
	}

	public HibernateMessagesFacade getMessageAccessInstance() {
		return new HibernateMessagesFacade(session);
	}

}
