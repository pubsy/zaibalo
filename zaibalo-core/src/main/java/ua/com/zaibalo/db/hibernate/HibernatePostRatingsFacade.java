package ua.com.zaibalo.db.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import ua.com.zaibalo.db.api.PostRatingsAccessInterface;
import ua.com.zaibalo.model.Post;
import ua.com.zaibalo.model.PostRating;
import ua.com.zaibalo.model.UserRating;

public class HibernatePostRatingsFacade extends HibernateFacade implements PostRatingsAccessInterface {

	public HibernatePostRatingsFacade(Session session) {
		this.session = session;
	}

	@Override
	public void savePostRating(PostRating rating) {

		session.save(rating);

		String hqlUpdate = "update Post set ratingSum = ratingSum + :ratingSum, ratingCount = ratingCount + 1 where id = :id"; 
		session.createQuery( hqlUpdate ) 
		.setInteger("id", rating.getPostId())
		.setInteger("ratingSum", rating.getValue())
		.executeUpdate(); 
		
		

	}

	@Override
	public boolean isPostRatedByUser(int postId, int userId) {

		Criteria criteria = session.createCriteria(PostRating.class);
		criteria.add(Restrictions.eq("postId", postId));
		criteria.add(Restrictions.eq("userId", userId));
		criteria.setProjection(Projections.rowCount());
		int size = (Integer)criteria.uniqueResult();
		
		
		if(size > 0){
			return true;
		}
		
		return false;
	}

	@Override
	public UserRating getUserPostRatingSum(int userId) {

		Criteria criteria = session.createCriteria(Post.class);
		criteria.add(Restrictions.eq("authorId", userId));
		List<Post> list = criteria.list();
		
		
		int sum = 0;
		int count = 0;
		for(Post post: list){
			sum += post.getRatingSum();
			count += post.getRatingCount();
		}
		
		return new UserRating(sum, count);
	}

}
