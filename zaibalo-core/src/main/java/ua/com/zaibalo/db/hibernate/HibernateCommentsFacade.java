package ua.com.zaibalo.db.hibernate;

import java.util.List;

import org.hibernate.classic.Session;
import org.hibernate.criterion.Order;

import ua.com.zaibalo.db.api.CommentsAccessInterface;
import ua.com.zaibalo.model.Comment;
import ua.com.zaibalo.model.Post;
import ua.com.zaibalo.model.User;

public class HibernateCommentsFacade extends HibernateFacade implements CommentsAccessInterface {

	public HibernateCommentsFacade(Session session) {
		this.session = session;
	}

	@Override
	public List<Comment> getAllPostComments(int id) {

		Post post = (Post)session.get(Post.class, id);
		List<Comment> commentsList = post.getComments(); 
		
		return commentsList;
	}

	@Override
	public int insert(Comment comment) {

		session.save(comment);
		int id = comment.getId();
		
		return id;
	}

	@Override
	public void delete(int id) {

		Comment comment = (Comment)session.get(Comment.class, id);
		session.delete(comment);
		
	}

	@Override
	public List<Comment> getRecentComments(int count) {

		List<Comment> commentsList = session.createCriteria(Comment.class).
		setMaxResults(count).
		addOrder(Order.desc("id")).
		list();
		
		return commentsList;
	}

	@Override
	public int getUserCommentCount(int userId) {

		User user = (User)session.get(User.class, userId);
		
		return user.getComments().size();
	}

	@Override
	public Comment getObjectById(int commentId) {

		Comment comment = (Comment)session.get(Comment.class, commentId);
		
		return comment;
	}

	@Override
	public void updateCommentRatingSum(int value, int count, int commentId) {

		Comment comment = (Comment)session.get(Comment.class, commentId);
		comment.setRatingSum(comment.getRatingSum() + value);
		comment.setRatingCount(comment.getRatingCount() + count);
		session.update(comment);
		
	}

}
