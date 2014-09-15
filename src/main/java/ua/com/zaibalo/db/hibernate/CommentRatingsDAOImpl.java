package ua.com.zaibalo.db.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ua.com.zaibalo.db.api.CommentRatingsDAO;
import ua.com.zaibalo.model.Comment;
import ua.com.zaibalo.model.CommentRating;
import ua.com.zaibalo.model.User;
import ua.com.zaibalo.model.UserRating;

@Repository
@Transactional(propagation=Propagation.MANDATORY)
public class CommentRatingsDAOImpl implements CommentRatingsDAO {

	@Autowired
    private SessionFactory sessionFactory;

	@Override
	public void createCommentRating(CommentRating rating) {

		this.sessionFactory.getCurrentSession().save(rating);
		
	}

	@Override
	public UserRating getUserCommentRatingSum(User user) {

		Query base = this.sessionFactory.getCurrentSession().createQuery("select count(id), sum(value) from CommentRating cr where cr.comment.author = :author");
		base.setParameter("author", user);
		Object[] result = (Object[])base.uniqueResult();
		
		
		long count = (Long)result[0];
		long sum = result[1] == null ? 0 : (Long)result[1];
		return new UserRating(sum, count);
	}

	@Override
	@SuppressWarnings("unchecked")
	public CommentRating getUserVote(User user, Comment comment) {
		Criterion a = Restrictions.eq("comment", comment);
		Criterion b = Restrictions.eq("user", user);
		

		Criteria base = this.sessionFactory.getCurrentSession().createCriteria(CommentRating.class).add(Restrictions.and(a, b));
		base.setMaxResults(1);
		
		List<CommentRating> list  = base.list();
		
		if(list.size() == 1){
			return list.get(0);
		}else{
			return null;
		}
	}

	@Override
	public void deleteCommentRate(CommentRating rating) {
		this.sessionFactory.getCurrentSession().delete(rating);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CommentRating> getUserCommentRatings(Comment comment) {
		Criterion a = Restrictions.eq("comment", comment);

		Criteria base = this.sessionFactory.getCurrentSession().createCriteria(CommentRating.class).add(a);
		
		return base.list();
	}

}
