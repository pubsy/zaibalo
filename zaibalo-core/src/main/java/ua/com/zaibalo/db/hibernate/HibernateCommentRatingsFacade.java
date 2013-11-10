package ua.com.zaibalo.db.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import ua.com.zaibalo.db.api.CommentRatingAccessInterface;
import ua.com.zaibalo.model.CommentRating;
import ua.com.zaibalo.model.UserRating;

public class HibernateCommentRatingsFacade extends HibernateFacade implements CommentRatingAccessInterface {

	public HibernateCommentRatingsFacade(Session session) {
		this.session = session;
	}

	@Override
	public void createCommentRating(CommentRating rating) {

		session.save(rating);
		
	}

	@Override
	public UserRating getUserCommentRatingSum(int userId) {

		Query base = session.createQuery("select count(id), sum(value) from CommentRating cr where cr.commentId in (select id from Comment c where c.authorId = ?)");
		base.setInteger(0, userId);
		Object[] result = (Object[])base.uniqueResult();
		
		
		long count = (Long)result[0];
		long sum = result[1] == null ? 0 : (Long)result[1];
		return new UserRating(sum, count);
	}

	@Override
	public CommentRating getUserVote(int userId, int commentId) {
		Criterion a = Restrictions.eq("commentId", commentId);
		Criterion b = Restrictions.eq("userId", userId);
		

		Criteria base = session.createCriteria(CommentRating.class).add(Restrictions.and(a, b));
		base.setMaxResults(1);
		
		List<CommentRating> list  = base.list();
		
		if(list.size() == 1){
			return list.get(0);
		}else{
			return null;
		}
	}

	@Override
	public void deleteCommentRate(int id) {

		CommentRating cr = (CommentRating)session.get(CommentRating.class, id);
		session.delete(cr);
		
	}

}
