package ua.com.zaibalo.db.hibernate;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import ua.com.zaibalo.db.api.PostsAccessInterface;
import ua.com.zaibalo.model.Comment;
import ua.com.zaibalo.model.Post;
import ua.com.zaibalo.model.Post.PostOrder;

public class HibernatePostsFacade extends HibernateFacade implements PostsAccessInterface {

	public HibernatePostsFacade(Session session) {
		this.session = session;
	}

	@Override
	public int insert(Post object) {

		session.save(object);
		int id = object.getId();
		
		return id;
	}

	@Override
	public void delete(Post post) {

		List<Comment> list = post.getComments();
		for(Comment c: list){
			session.delete(c);
		}
		session.delete(post);
		
	}

	@Override
	public List<Post> getOrderedList(int from, int count, PostOrder order) {

		List<Post> list = session.createCriteria(Post.class)
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

		Post post = (Post)session.get(Post.class, id);
		if (post == null){
			return null;
		}
		post.getComments().size();
		post.getCategories().size();

		
		return post;
	}

	@Override
	public List<Post> getLatestUserPosts(int userId, int count) {

		List<Post> postsList = session.createCriteria(Post.class)
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

		int size = session.createCriteria(Post.class)
		.add(Restrictions.eq("authorId", userId))
		.list().size();
		
		return size;
	}

	@Override
	public void update(Post post) {

		session.update(post);
		
	}

	@Override
	public List<Post> getPostsList(List<Integer> ids, Date fromDate, PostOrder order, int from, int count) {



		List<Post> postsList = null;
		try{
			Criteria parent = session.createCriteria(Post.class);
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
			Criteria parent = session.createCriteria(Post.class);
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
	public List<Post> getAllPostsList() {

		List<Post> list = session.createCriteria(Post.class).list();
		
		return list;
	}

	@Override
	public List<Post> searchByPosts(String term, int from, int count) {
		String hqlSelect = "from Post post where post.content like :term or post.title like :term";
		

		List<Post> list = session.createQuery(hqlSelect) 
		.setString("term", "%" + term + "%")
		.setMaxResults(count)
		.setFirstResult(from)
		.list(); 
		
		return list;
	}

	@Override
	public int getSearchPostsListSize(String term) {
		String hqlSelect = "from Post post where post.content like :term or post.title like :term"; 
		

		int size = session.createQuery(hqlSelect) 
		.setString("term", "%" + term + "%")
		.list().size();
		
		return size;
	}

}
