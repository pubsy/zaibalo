package ua.com.zaibalo.actions.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ua.com.zaibalo.actions.Action;
import ua.com.zaibalo.constants.ZaibaloConstants;
import ua.com.zaibalo.db.api.CategoriesDAO;
import ua.com.zaibalo.db.api.PostsDAO;
import ua.com.zaibalo.helper.CharArrayWriterResponse;
import ua.com.zaibalo.helper.StringHelper;
import ua.com.zaibalo.helper.ajax.AjaxResponse;
import ua.com.zaibalo.helper.ajax.FailResponse;
import ua.com.zaibalo.helper.ajax.SuccessResponse;
import ua.com.zaibalo.model.Category;
import ua.com.zaibalo.model.Post;
import ua.com.zaibalo.model.User;
import ua.com.zaibalo.social.VKPostToGroup;
import ua.com.zaibalo.validation.Validator;

@Component
public class SavePostAction implements Action{

	private static final String POST_TITLE = "post_title";
	private static final String POST_TEXT = "post_text";
	private static final String POST_CATEGORIES = "categories";
	
	@Autowired
	private CategoriesDAO categoriesDAO;
	@Autowired
	private PostsDAO postsDAO;
	
	@Override
	public AjaxResponse run(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String postTitle = (String) request.getParameter(POST_TITLE);
		String postText = (String) request.getParameter(POST_TEXT);
		String postCategories = (String) request.getParameter(POST_CATEGORIES);

		User user = (User) request.getSession().getAttribute(ZaibaloConstants.USER_PARAM_NAME);

		if(user.getRole() > 2){
			return new FailResponse(StringHelper.getLocalString("operation_forbidden"));
		}
		
		// save post
		Post post = new Post();
		post.setAuthor(user);
		post.setContent(postText);
		post.setTitle(postTitle);
		post.setAuthorId(user.getId());
		post.setRatingCount(0);
		post.setRatingSum(0);
		
		Set<Category> postCategoriesAndTags = new HashSet<Category>();
		if(postCategories == null || postCategories.equals("")){
			Category otherCategory = categoriesDAO.getCategoryById(6);
			postCategoriesAndTags.add(otherCategory);
		}
		
		int id = 0;


		for (String catName : postCategories.split(",")) {
			Category category = categoriesDAO.getCategoryByName(Category.CategoryType.BOTH, catName.trim());
			if(category == null){
				category = new Category(catName.trim(), Category.CategoryType.TAG);
				categoriesDAO.insert(category);
			}
			postCategoriesAndTags.add(category);
		}

		List<Category> list = new ArrayList<Category>();
		list.addAll(postCategoriesAndTags);
		
		post.setCategories(list);
		post.setDate(new Date());

		Validator validator = new Validator();
		if (!validator.validatePost(post)) {
			return new FailResponse(validator.getErrors());

		} else {
			id = postsDAO.insert(post);
		}

		post.setId(id);

		request.setAttribute("post", post);
		
		String extr = post.getContent();
		final String text = extr + "\n\n" + post.getAuthor().getDisplayName() + "\n\n" + "http://www.zaibalo.com.ua/post/" + post.getId();
			
		new Thread(new Runnable(){
			@Override
			public void run() {
				VKPostToGroup.postToVKGroup(text);
				//FBPostToGroup.postToFBGroup(text);
			}
		}).start();
		
		CharArrayWriterResponse customResponse  = new CharArrayWriterResponse(response);
	    request.getRequestDispatcher("/jsp/post_wrapper.jsp").forward(request, customResponse);
	    
	    return new SuccessResponse(customResponse.getOutput());
	}
}
