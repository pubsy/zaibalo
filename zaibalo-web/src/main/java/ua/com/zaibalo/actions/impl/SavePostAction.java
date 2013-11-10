package ua.com.zaibalo.actions.impl;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.com.zaibalo.actions.Action;
import ua.com.zaibalo.constants.ZaibaloConstants;
import ua.com.zaibalo.db.DataAccessFactory;
import ua.com.zaibalo.helper.AjaxResponse;
import ua.com.zaibalo.helper.CharArrayWriterResponse;
import ua.com.zaibalo.model.Category;
import ua.com.zaibalo.model.Post;
import ua.com.zaibalo.model.User;
import ua.com.zaibalo.social.FBPostToGroup;
import ua.com.zaibalo.social.VKPostToGroup;
import ua.com.zaibalo.validation.Validator;

import com.google.gson.Gson;

public class SavePostAction implements Action{

	private static final long serialVersionUID = 1L;
	private static final String POST_TITLE = "post_title";
	private static final String POST_TEXT = "post_text";
	private static final String POST_CATEGORIES = "categories";
	
	private Gson gson = new Gson();

	@Override
	public void run(HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws Exception {
		String postTitle = (String) request.getParameter(POST_TITLE);
		String postText = (String) request.getParameter(POST_TEXT);
		String postCategories = (String) request.getParameter(POST_CATEGORIES);

		User user = (User) request.getSession().getAttribute(ZaibaloConstants.USER_PARAM_NAME);

		// save post
		Post post = new Post();
		post.setAuthor(user);
		post.setContent(postText);
		post.setTitle(postTitle);
		post.setAuthorId(user.getId());
		post.setRatingCount(0);
		post.setRatingSum(0);
		
		DataAccessFactory factory = new DataAccessFactory(request);
		Set<Category> postCategoriesAndTags = new HashSet<Category>();
		if(postCategories == null || postCategories.equals("")){
			Category otherCategory = factory.getCategorysAccessInstance().getCategoryById(6);
			postCategoriesAndTags.add(otherCategory);
		}
		
		int id = 0;


		for (String catName : postCategories.split(",")) {
			Category category = factory.getCategorysAccessInstance().getCategoryByName(Category.CategoryType.BOTH, catName.trim());
			if(category == null){
				category = new Category(catName.trim(), Category.CategoryType.TAG);
				factory.getCategorysAccessInstance().insert(category);
			}
			postCategoriesAndTags.add(category);
		}

		List<Category> list = new ArrayList<Category>();
		list.addAll(postCategoriesAndTags);
		
		post.setCategories(list);
		post.setDate(new Date());

		Validator validator = new Validator();
		if (!validator.validatePost(post)) {
			String responseJson = gson.toJson(new AjaxResponse(false, validator.getErrors()));
			out.print(responseJson);
			out.close();
			return;
		} else {
			id = factory.getPostsAccessInstance().insert(post);
		}

		post.setId(id);

		request.setAttribute("post", post);
		
		//String link = "zaibalo.com.ua/post.do?id=" + post.getId();
		//String extr = StringHelper.extract(post.getContent(), 140 - post.getAuthorName().length() - link.length() -4);
		String extr = post.getContent();
		final String text = extr + "\n\n" + post.getAuthor().getDisplayName() + "\n\n" + "http://www.zaibalo.com.ua/post.do?id=" + post.getId();
			
		new Thread(new Runnable(){
			@Override
			public void run() {
				VKPostToGroup.postToVKGroup(text);
				FBPostToGroup.postToFBGroup(text);
			}
		}).start();
		
		CharArrayWriterResponse customResponse  = new CharArrayWriterResponse(response);
	    request.getRequestDispatcher("/jsp/post_wrapper.jsp").forward(request, customResponse);
	    Object postHTML = customResponse.getOutput();
	    
	    String responseJson = gson.toJson(new AjaxResponse(true, postHTML));
	    out.print(responseJson);
	    out.close();  
	}
}
