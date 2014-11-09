package ua.com.zaibalo.db.hibernate;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ua.com.zaibalo.db.api.PostsDAO;
import ua.com.zaibalo.model.Post;
import ua.com.zaibalo.model.Post.PostOrder;
import ua.com.zaibalo.model.User;

@Repository
@Transactional(propagation=Propagation.MANDATORY)
public class PostsDAOImpl implements PostsDAO {

	@Autowired
    protected SessionFactory sessionFactory;
	
	@Override
	public Post insert(Post post) {
		this.sessionFactory.getCurrentSession().save(post);
		return post;
	}

	@Override
	public void delete(Post post) {
		this.sessionFactory.getCurrentSession().delete(post);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Post> getOrderedList(int from, int count, PostOrder order) {
		return this.sessionFactory.getCurrentSession()
				.createCriteria(Post.class)
				.setFirstResult(from)
				.setMaxResults(count)
				.addOrder(Order.desc(order.getPropertyName()))
				.list();
	}

	@Override
	public Post getObjectById(int id) {
		return (Post) this.sessionFactory.getCurrentSession().get(Post.class, id);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Post> getLatestUserPosts(User user, int count) {
		return this.sessionFactory.getCurrentSession()
				.createCriteria(Post.class)
				.setMaxResults(count)
				.addOrder(Order.desc("id"))
				.add(Restrictions.eq("author", user))
				.list();
	}

	@Override
	public int getUserPostCount(User user) {
		return this.sessionFactory.getCurrentSession()
				.createCriteria(Post.class)
				.add(Restrictions.eq("author", user))
				.list()
				.size();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Post> getPostsList(List<Integer> ids, Date fromDate, PostOrder order, int from, int count) {

		Criteria parent = this.sessionFactory.getCurrentSession().createCriteria(Post.class);

		if (ids != null && ids.size() > 0) {
			parent.createCriteria("categories").add(Restrictions.in("id", ids));
		}
		if (from != -1) {
			parent.setFirstResult(from);
		}
		if (fromDate != null) {
			parent.add(Restrictions.gt("date", fromDate));
		}
		parent.setMaxResults(count);
		if (order != null) {
			parent.addOrder(Order.desc(order.getPropertyName()));
		}
		return parent.list();
	}

	@Override
	public long getPostsListSize(List<Integer> ids, Date fromDate) {
		Criteria parent = this.sessionFactory.getCurrentSession()
				.createCriteria(Post.class);
		if (ids != null && ids.size() > 0) {
			parent.createCriteria("categories").add(Restrictions.in("id", ids));
		}

		if (fromDate != null) {
			parent.add(Restrictions.gt("date", fromDate));
		}

		return (Long) parent.setProjection(Projections.rowCount()).uniqueResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Post> getAllPostsList() {
		return this.sessionFactory.getCurrentSession().createCriteria(Post.class).list();
	}
	
	@Override
	public void updatePostRatingSum(int value, int count, Post post) {
		post.setRatingSum(post.getRatingSum() + value);
		post.setRatingCount(post.getRatingCount() + count);
		update(post);
	}

	@Override
	public Post update(Post post) {
		this.sessionFactory.getCurrentSession().update(post);
		return post;
	}

}
