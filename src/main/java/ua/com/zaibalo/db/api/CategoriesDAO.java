package ua.com.zaibalo.db.api;

import java.util.List;

import ua.com.zaibalo.model.Category;

public interface CategoriesDAO {
	
	List<Category> getCategoriesList();
	Category getCategoryByName( Category.CategoryType type, String name);
	int insert(Category category);
	List<Category> getPostCategories(int postId);
	void deleteAllPostCaegories(int postId);
	List<Category> getCategoriesList(Category.CategoryType catType);
	Category getCategoryById(int id);
}
