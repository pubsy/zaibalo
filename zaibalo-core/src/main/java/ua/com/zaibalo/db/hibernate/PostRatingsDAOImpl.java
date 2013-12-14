package ua.com.zaibalo.db.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ua.com.zaibalo.db.api.PostRatingsDAO;
import ua.com.zaibalo.model.Post;
import ua.com.zaibalo.model.PostRating;
import ua.com.zaibalo.model.UserRating;

@Transactional
@Repository
public class PostRatingsDAOImpl implements PostRatingsDAO {

	@Autowired
    private SessionFactory sessionFactory;
	
	@Override
	public void savePostRating(PostRating rating) {

		this.sessionFactory.getCurrentSession().save(rating);

		String hqlUpdate = "update Post set ratingSum = ratingSum + :ratingSum, ratingCount = ratingCount + 1 where id = :id"; 
		this.sessionFactory.getCurrentSession().createQuery( hqlUpdate ) 
		.setInteger("id", rating.getPostId())
		.setInteger("ratingSum", rating.getValue())
		.executeUpdate(); 
		
		

	}

	@Override
	public boolean isPostRatedByUser(int postId, int userId) {

		Criteria criteria = this.sessionFactory.getCurrentSession().createCriteria(PostRating.class);
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
	@SuppressWarnings("unchecked")
	public UserRating getUserPostRatingSum(int userId) {

		Criteria criteria = this.sessionFactory.getCurrentSession().createCriteria(Post.class);
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
