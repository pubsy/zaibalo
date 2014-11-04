package ua.com.zaibalo.db.api;

import java.util.List;

import ua.com.zaibalo.model.Category;

public interface CategoriesDAO {
	
	Category getCategoryByName(String name);
	int insert(Category category);
	List<Category> getMostPopularCategoriesList(int count);
	Category getCategoryById(int id);
}
