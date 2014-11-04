package ua.com.zaibalo.business;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ua.com.zaibalo.db.api.CategoriesDAO;
import ua.com.zaibalo.db.api.PostsDAO;
import ua.com.zaibalo.model.Category;
import ua.com.zaibalo.model.Post;
import ua.com.zaibalo.model.Post.PostOrder;
import ua.com.zaibalo.model.User;

@Component
@Transactional(propagation=Propagation.REQUIRED)
public class PostsBusinessLogic {
	
	@Autowired
	private CategoriesDAO categoriesDAO;
	@Autowired
	private PostsDAO postsDAO;
	
	public List<Post> getOrderedList(int from, int count, PostOrder order) {
		return postsDAO.getOrderedList(-1, 10, Post.PostOrder.ID);
	}

	public Post createPost(String title, String content, User author, String[] postCategories){
		Post post = new Post();
		post.setAuthor(author);
		post.setContent(content);
		post.setTitle(title);
		post.setRatingCount(0);
		post.setRatingSum(0);
		post.setDate(new Date());
		
		Set<Category> postCategoriesAndTags = new HashSet<Category>();
		if(postCategories == null || postCategories.length == 0){
			Category otherCategory = categoriesDAO.getCategoryById(6);
			postCategoriesAndTags.add(otherCategory);
		}

		for (String catName : postCategories) {
			Category category = categoriesDAO.getCategoryByName(catName.trim());
			if(category == null){
				category = new Category(catName.trim());
				categoriesDAO.insert(category);
			}
			postCategoriesAndTags.add(category);
		}
		
		post.setCategories(postCategoriesAndTags);
		
		return postsDAO.insert(post);
	}

	public Post getPostById(Integer postId) {
		return postsDAO.getObjectById(postId);
	}
}
