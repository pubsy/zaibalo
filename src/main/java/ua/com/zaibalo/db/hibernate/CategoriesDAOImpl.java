package ua.com.zaibalo.db.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ua.com.zaibalo.db.api.CategoriesDAO;
import ua.com.zaibalo.model.Category;
import ua.com.zaibalo.model.Post;

@Repository
@Transactional(propagation=Propagation.MANDATORY)
public class CategoriesDAOImpl implements CategoriesDAO{

	@Autowired
    private SessionFactory sessionFactory;
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Category> getCategoriesList() {
		List<Category> list = this.sessionFactory.getCurrentSession().createCriteria(Category.class).list();
		return list;
	}

	@Override
	public Category getCategoryByName(Category.CategoryType type, String name) {
		Criterion a = Restrictions.eq("name", name);
		Criterion b = Restrictions.like("type", type.getType());

		Criteria base = this.sessionFactory.getCurrentSession().createCriteria(Category.class);
			
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
		Category category = (Category) this.sessionFactory.getCurrentSession().get(Category.class, id);
		return category; 
	}

	@Override
	public int insert(Category category){

		this.sessionFactory.getCurrentSession().save(category);
		int id = category.getId();
		
		return id;
	}

	@Override
	public List<Category> getPostCategories(int postId){

		Post post = (Post)this.sessionFactory.getCurrentSession().createCriteria(Post.class).add(Restrictions.eq("id", postId)).uniqueResult();
		List<Category> list =  post.getCategories();
		
		return list;
	}

	@Override
	public void deleteAllPostCaegories(int postId){
		Post post = (Post)this.sessionFactory.getCurrentSession().createCriteria(Post.class).add(Restrictions.eq("id", postId)).uniqueResult();
		post.setCategories(new ArrayList<Category>());
		this.sessionFactory.getCurrentSession().update(post);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Category> getCategoriesList(Category.CategoryType catType){

		if(catType == Category.CategoryType.BOTH){
			return getCategoriesList();
		}

		List<Category> list = this.sessionFactory.getCurrentSession().createCriteria(Category.class).add(Restrictions.eq("type", catType.getType())).list();
		
		
		return list;
	}

}
