package ua.com.zaibalo.db.hibernate;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ua.com.zaibalo.db.api.PostRatingsDAO;
import ua.com.zaibalo.model.Post;
import ua.com.zaibalo.model.PostRating;
import ua.com.zaibalo.model.User;
import ua.com.zaibalo.model.UserRating;

@Repository
@Transactional(propagation=Propagation.MANDATORY)
public class PostRatingsDAOImpl implements PostRatingsDAO {

	@Autowired
    protected SessionFactory sessionFactory;
	
	@Override
	public void savePostRating(PostRating rating) {
		this.sessionFactory.getCurrentSession().save(rating);

		String hqlUpdate = "update Post set ratingSum = ratingSum + :ratingSum, ratingCount = ratingCount + 1 where id = :id"; 
		this.sessionFactory.getCurrentSession()
				.createQuery( hqlUpdate ) 
				.setInteger("id", rating.getPost().getId())
				.setInteger("ratingSum", rating.getValue())
				.executeUpdate(); 
	}

	@Override
	public boolean isPostRatedByUser(Post post, User user) {
		int size = (Integer)this.sessionFactory.getCurrentSession()
				.createCriteria(PostRating.class)
				.add(Restrictions.eq("post", post))
				.add(Restrictions.eq("userId", user))
				.setProjection(Projections.rowCount())
				.uniqueResult();
		
		return size > 0;
	}

	@Override
	@SuppressWarnings("unchecked")
	public UserRating getUserPostRatingSum(User user) {
		List<Post> list =  this.sessionFactory.getCurrentSession()
				.createCriteria(Post.class)
				.add(Restrictions.eq("author", user))
				.list();
		
		int sum = 0;
		int count = 0;
		for(Post post: list){
			sum += post.getRatingSum();
			count += post.getRatingCount();
		}
		
		return new UserRating(sum, count);
	}
	
	@Override
	public PostRating getUserVote(User user, Post post) {
		Criterion a = Restrictions.eq("post", post);
		Criterion b = Restrictions.eq("user", user);
		
		return (PostRating) this.sessionFactory.getCurrentSession()
				.createCriteria(PostRating.class)
				.add(Restrictions.and(a, b))
				.setMaxResults(1)
				.uniqueResult();
	}

	@Override
	public void deletePostRating(PostRating postRating) {
		this.sessionFactory.getCurrentSession().delete(postRating);	
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PostRating> getPostRatings(Post post) {
		return this.sessionFactory.getCurrentSession()
				.createCriteria(PostRating.class)
				.add(Restrictions.eq("post", post))
				.list();
	}

	@Override
	public void update(PostRating postRating){
		this.sessionFactory.getCurrentSession().update(postRating);
	}
	
}
