package ua.com.zaibalo.db.hibernate;

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

@Repository
@Transactional(propagation = Propagation.MANDATORY)
public class CategoriesDAOImpl implements CategoriesDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Category getCategoryByName(String name) {
		Criterion a = Restrictions.eq("name", name);
		Criteria base = this.sessionFactory.getCurrentSession().createCriteria(Category.class);
		base.add(a);

		return (Category) base.uniqueResult();
	}

	@Override
	public Category getCategoryById(int id) {
		return (Category) this.sessionFactory.getCurrentSession().get(Category.class, id);
	}

	@Override
	public int insert(Category category) {
		return (Integer) this.sessionFactory.getCurrentSession().save(category);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Category> getCategoriesList(Category.CategoryType catType) {
		return this.sessionFactory.getCurrentSession().createCriteria(Category.class)
				.add(Restrictions.eq("type", catType)).list();
	}

}
