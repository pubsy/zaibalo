package ua.com.zaibalo.db.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import ua.com.zaibalo.db.api.CategorysAccessInterface;
import ua.com.zaibalo.model.Category;
import ua.com.zaibalo.model.Comment;
import ua.com.zaibalo.model.Post;

public class HibernateCategorysFacade extends HibernateFacade implements CategorysAccessInterface{

	public HibernateCategorysFacade(Session session) {
		this.session = session;
	}

	@Override
	public List<Category> getCategoriesList() {
		List<Category> list = session.createCriteria(Category.class).list();
		return list;
	}

	@Override
	public Category getCategoryByName(Category.CategoryType type, String name) {
		Criterion a = Restrictions.eq("name", name);
		Criterion b = Restrictions.like("type", type.getType());

		Criteria base = session.createCriteria(Category.class);
			
		if(type != Category.CategoryType.BOTH){
			base.add(Restrictions.and(a, b));
		}else{
			base.add(a);
		}

		Category category = (Category)base.uniqueResult();
	
		return category; 
	}
	
	@Override
	public Category getCategoryById( int id) {		
		Category category = (Category) session.get(Category.class, id);
		return category; 
	}

	@Override
	public int insert(Category category){

		session.save(category);
		int id = category.getId();
		
		return id;
	}

	@Override
	public List<Category> getPostCategories(int postId){

		Post post = (Post)session.createCriteria(Post.class).add(Restrictions.eq("id", postId)).uniqueResult();
		List<Category> list =  post.getCategories();
		
		return list;
	}

	@Override
	public void deleteAllPostCaegories(int postId){
		Post post = (Post)session.createCriteria(Post.class).add(Restrictions.eq("id", postId)).uniqueResult();
		post.setCategories(new ArrayList<Category>());
		session.update(post);
	}

	@Override
	public List<Category> getCategoriesList(Category.CategoryType catType){

		if(catType == Category.CategoryType.BOTH){
			return getCategoriesList();
		}

		List<Category> list = session.createCriteria(Category.class).add(Restrictions.eq("type", catType.getType())).list();
		
		
		return list;
	}

}
