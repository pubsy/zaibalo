package ua.com.zaibalo.db;

import javax.servlet.http.HttpServletRequest;

import ua.com.zaibalo.db.api.CategoriesDAO;
import ua.com.zaibalo.db.api.CommentRatingsDAO;
import ua.com.zaibalo.db.api.CommentsDAO;
import ua.com.zaibalo.db.api.DiscussionsDAO;
import ua.com.zaibalo.db.api.MessagesDAO;
import ua.com.zaibalo.db.api.PostRatingsDAO;
import ua.com.zaibalo.db.api.PostsDAO;
import ua.com.zaibalo.db.api.UsersDAO;
import ua.com.zaibalo.helper.ZAppContext;

public class DataAccessFactory {

	public DataAccessFactory(HttpServletRequest request) {
		// TODO Auto-generated constructor stub
	}

	public UsersDAO getUsersAccessInstance() {
		return ZAppContext.getUsersDAO();
	}

	public CommentsDAO getCommentsAccessInstance() {
		return ZAppContext.getCommentsDAO();
	}

	public PostsDAO getPostsAccessInstance() {
		return ZAppContext.getPostsDAO();
	}

	public CategoriesDAO getCategorysAccessInstance() {
		return ZAppContext.getCategoriesDao();
	}

	public CommentRatingsDAO getCommentRatingAccessInstance() {
		return ZAppContext.getCommentRatingsDAO();
	}

	public PostRatingsDAO getPostRatingsAccessInstance() {
		return ZAppContext.getPostRatingsDAO();
	}

	public DiscussionsDAO getDiscussionsAccessInstance() {
		return ZAppContext.getDiscussionsDAO();
	}

	public MessagesDAO getMessageAccessInstance() {
		return ZAppContext.getMessagesDAO();
	}

}
