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
import ua.com.zaibalo.model.Comment;
import ua.com.zaibalo.model.Post;
import ua.com.zaibalo.model.Post.PostOrder;

@Repository
@Transactional(propagation=Propagation.MANDATORY)
public class PostsDAOImpl implements PostsDAO {

	@Autowired
    protected SessionFactory sessionFactory;
	
	@Override
	public int insert(Post post) {

		this.sessionFactory.getCurrentSession().save(post);
		int id = post.getId();
		
		return id;
	}

	@Override
	public void delete(Post post) {

		List<Comment> list = post.getComments();
		for(Comment c: list){
			this.sessionFactory.getCurrentSession().delete(c);
		}
		this.sessionFactory.getCurrentSession().delete(post);
		
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Post> getOrderedList(int from, int count, PostOrder order) {

		List<Post> list = this.sessionFactory.getCurrentSession().createCriteria(Post.class)
		.setFirstResult(from)
		.setMaxResults(count)
		.addOrder(Order.desc(order.getPropertyName()))
		.list();
		for(Post post: list){
			post.getComments().size();
		}
		
		return list;
	}

	@Override
	public Post getObjectById(int id) {

		Post post = (Post)this.sessionFactory.getCurrentSession().get(Post.class, id);
		if (post == null){
			return null;
		}
		post.getComments().size();
		post.getCategories().size();

		
		return post;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Post> getLatestUserPosts(int userId, int count) {

		List<Post> postsList = this.sessionFactory.getCurrentSession().createCriteria(Post.class)
		.setMaxResults(count)
		.addOrder(Order.desc("id"))
		.add(Restrictions.eq("authorId", userId))
		.list();
		
		for(Post post: postsList){
			post.getComments().size();
			post.getCategories().size();
		}
		
		
		return postsList;
	}

	@Override
	public int getUserPostCount(int userId) {

		int size = this.sessionFactory.getCurrentSession().createCriteria(Post.class)
		.add(Restrictions.eq("authorId", userId))
		.list().size();
		
		return size;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Post> getPostsList(List<Integer> ids, Date fromDate, PostOrder order, int from, int count) {
		List<Post> postsList = null;
		try{
			Criteria parent = this.sessionFactory.getCurrentSession().createCriteria(Post.class);
			if(ids != null && ids.size() > 0){
				parent.createCriteria("categories").add(Restrictions.in("id", ids));
			}
			if(from != -1){
				parent.setFirstResult(from);
			}
			if(fromDate != null){
				parent.add(Restrictions.gt("date", fromDate));
			}
			parent.setMaxResults(count);
			if(order != null){
				parent.addOrder(Order.desc(order.getPropertyName()));
			}
			postsList = parent.list();			
			
			for(Post post: postsList){
				post.getComments().size();
				post.getCategories().size();
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return postsList;
	}

	@Override
	public long getPostsListSize(List<Integer> ids, Date fromDate) {

		long size = 0;
		try{
			Criteria parent = this.sessionFactory.getCurrentSession().createCriteria(Post.class);
			if(ids != null && ids.size() > 0){
				parent.createCriteria("categories").add(Restrictions.in("id", ids));
			}

			if(fromDate != null){
				parent.add(Restrictions.gt("date", fromDate));
			}

			size = (Long)parent.setProjection(Projections.rowCount()).uniqueResult();

		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return size;
		
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Post> getAllPostsList() {

		List<Post> list = this.sessionFactory.getCurrentSession().createCriteria(Post.class).list();
		
		return list;
	}
	
	@Override
	public void updatePostRatingSum(int value, int count, int postId) {

		Post post = (Post)this.sessionFactory.getCurrentSession().get(Post.class, postId);
		post.setRatingSum(post.getRatingSum() + value);
		post.setRatingCount(post.getRatingCount() + count);
		this.sessionFactory.getCurrentSession().update(post);
		
	}

	public void update(Post post) {
		this.sessionFactory.getCurrentSession().update(post);
	}

}
