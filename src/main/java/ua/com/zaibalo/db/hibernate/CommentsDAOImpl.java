package ua.com.zaibalo.db.hibernate;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ua.com.zaibalo.db.api.CommentsDAO;
import ua.com.zaibalo.model.Comment;
import ua.com.zaibalo.model.Post;
import ua.com.zaibalo.model.User;

@Repository
@Transactional(propagation=Propagation.MANDATORY)
public class CommentsDAOImpl implements CommentsDAO {

	@Autowired
    private SessionFactory sessionFactory;

	@Override
	public List<Comment> getAllPostComments(int id) {

		Post post = (Post)this.sessionFactory.getCurrentSession().get(Post.class, id);
		List<Comment> commentsList = post.getComments(); 
		
		return commentsList;
	}

	@Override
	public Comment update(Comment comment) {
		Session currentSession = this.sessionFactory.getCurrentSession();
		currentSession.update(comment);
		return comment;
	}
	
	@Override
	public Comment persist(Comment comment) {
		Session currentSession = this.sessionFactory.getCurrentSession();
		currentSession.persist(comment);
		return comment;
	}

	@Override
	public void delete(int id) {

		Comment comment = (Comment)this.sessionFactory.getCurrentSession().get(Comment.class, id);
		this.sessionFactory.getCurrentSession().delete(comment);
		
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Comment> getRecentComments(int count) {

		List<?> commentsList = this.sessionFactory.getCurrentSession().createCriteria(Comment.class).
		setMaxResults(count).
		addOrder(Order.desc("id")).
		list();
		
		return (List<Comment>) commentsList;
	}

	@Override
	public int getUserCommentCount(int userId) {

		User user = (User)this.sessionFactory.getCurrentSession().get(User.class, userId);
		
		return user.getComments().size();
	}

	@Override
	public Comment getObjectById(int commentId) {

		Comment comment = (Comment)this.sessionFactory.getCurrentSession().get(Comment.class, commentId);
		
		return comment;
	}

	@Override
	public void updateCommentRatingSum(int value, int count, int commentId) {

		Comment comment = (Comment)this.sessionFactory.getCurrentSession().get(Comment.class, commentId);
		comment.setRatingSum(comment.getRatingSum() + value);
		comment.setRatingCount(comment.getRatingCount() + count);
		this.sessionFactory.getCurrentSession().update(comment);
		
	}

}
