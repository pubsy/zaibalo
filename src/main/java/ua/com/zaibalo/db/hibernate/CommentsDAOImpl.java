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
import ua.com.zaibalo.model.User;

@Repository
@Transactional(propagation=Propagation.MANDATORY)
public class CommentsDAOImpl implements CommentsDAO {

	@Autowired
    private SessionFactory sessionFactory;

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
	public void delete(Comment comment) {
		this.sessionFactory.getCurrentSession().delete(comment);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Comment> getRecentComments(int count) {
		List<Comment> list = this.sessionFactory.getCurrentSession()
				.createCriteria(Comment.class)
				.setMaxResults(count)
				.addOrder(Order.desc("id"))
				.list();
		for (Comment comment : list) {
			comment.getPost().getId();
		}
		return list;
	}

	@Override
	public int getUserCommentCount(User user) {
		return user.getComments().size();
	}

	@Override
	public Comment getObjectById(int commentId) {
		return (Comment)this.sessionFactory.getCurrentSession().get(Comment.class, commentId);
	}

	@Override
	public void updateCommentRatingSum(int value, int count, Comment comment) {
		comment.setRatingSum(comment.getRatingSum() + value);
		comment.setRatingCount(comment.getRatingCount() + count);
		this.sessionFactory.getCurrentSession().update(comment);
	}

}
