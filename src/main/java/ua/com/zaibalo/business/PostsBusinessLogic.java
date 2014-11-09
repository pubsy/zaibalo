package ua.com.zaibalo.business;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ua.com.zaibalo.db.api.PostsDAO;
import ua.com.zaibalo.exceptions.ValidationException;
import ua.com.zaibalo.helper.StringHelper;
import ua.com.zaibalo.model.Category;
import ua.com.zaibalo.model.Post;
import ua.com.zaibalo.model.Post.PostOrder;
import ua.com.zaibalo.model.User;
import ua.com.zaibalo.validation.Validator;

@Component
@Transactional(propagation=Propagation.REQUIRED)
public class PostsBusinessLogic {
	
	@Autowired
	private PostsDAO postsDAO;
	@Autowired
	private CategoriesBusinessLogic categoriesBusinessLogic;
	
	public List<Post> getOrderedList(int from, int count, PostOrder order) {
		return postsDAO.getOrderedList(-1, 10, Post.PostOrder.ID);
	}

	public Post createPost(String content, User author) throws ValidationException{
		Post post = new Post();
		post.setAuthor(author);
		post.setContent(content);
		post.setTitle(extractPostTitle(content));
		post.setRatingCount(0);
		post.setRatingSum(0);
		post.setDate(new Date());
		
		Set<Category> postCategoriesAndTags = findCategoriesForNames(StringHelper.parseTags(content));
		post.setCategories(postCategoriesAndTags);
		
		Validator validator = new Validator();
		if (!validator.validatePost(post.getContent(), post.getTitle(), post.getCategories())) {
			throw new ValidationException(validator.getErrors());
		}
		
		return postsDAO.insert(post);
	}

	public Post getPostById(Integer postId) {
		return postsDAO.getObjectById(postId);
	}

	public Post updatePost(User user, int postId, String text) throws ValidationException {
		Post post = postsDAO.getObjectById(postId);
		
		if(user.isUser() && !post.getAuthor().equals(user)){
			throw new ValidationException(StringHelper.getLocalString("you_cant_edit_other_users_post"));
		}
		
		if(user.isUser() && post.getComments().size() > 0){
			throw new ValidationException(StringHelper.getLocalString("you_cant_edit_your_post_after_commented"));
		}
		
		Set<Category> postTags = findCategoriesForNames(StringHelper.parseTags(text));
		
		post.setCategories(postTags);
		post.setTitle(extractPostTitle(text));
		post.setContent(text);
		
		return postsDAO.update(post);
	}

	private String extractPostTitle(String text) {
		return StringHelper.extract(text, 32);
	}
	
	private Set<Category> findCategoriesForNames(Set<String> names){
		Set<Category> set = new HashSet<Category>();
		for(String name: names){
			Category category = categoriesBusinessLogic.getOrCreateCategoryByName(name);
			set.add(category);
		}
		return set;
	}

}
