package ua.com.zaibalo.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ua.com.zaibalo.db.api.CategoriesDAO;
import ua.com.zaibalo.model.Category;

@Component
@Transactional(propagation=Propagation.REQUIRED)
public class CategoriesBusinessLogic {

	@Autowired
	private CategoriesDAO categoriesDAO;

	public Category getOrCreateCategoryByName(String name) {
		Category category = categoriesDAO.getCategoryByName(name);
		if(category != null){
			return category;
		}else{
			Category newCategory = new Category(name);
			int catId = categoriesDAO.insert(newCategory);
			newCategory.setId(catId);
			return newCategory;
		}
	}
}
