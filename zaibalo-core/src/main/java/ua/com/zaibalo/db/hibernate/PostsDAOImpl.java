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
import org.springframework.transaction.annotation.Transactional;

import ua.com.zaibalo.db.api.PostsDAO;
import ua.com.zaibalo.model.Comment;
import ua.com.zaibalo.model.Post;
import ua.com.zaibalo.model.Post.PostOrder;

@Transactional
@Repository
public class PostsDAOImpl implements PostsDAO {

	@Autowired
    private SessionFactory sessionFactory;

	@Override
	public int insert(Post object) {

		this.sessionFactory.getCurrentSession().save(object);
		int id = object.getId();
		
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
	public void update(Post post) {

		this.sessionFactory.getCurrentSession().update(post);
		
	}

	@Transactional
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
			parent.addOrder(Order.desc(order.getPropertyName()));
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
	public int getPostsListSize(List<Integer> ids, Date fromDate) {
		


		int size = 0;
		try{
			Criteria parent = this.sessionFactory.getCurrentSession().createCriteria(Post.class);
			if(ids != null && ids.size() > 0){
				parent.createCriteria("categories").add(Restrictions.in("id", ids));
			}

			if(fromDate != null){
				parent.add(Restrictions.gt("date", fromDate));
			}

			size = (Integer)parent.setProjection(Projections.rowCount()).uniqueResult();

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

}
