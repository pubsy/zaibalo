package ua.com.zaibalo.actions.impl;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletException;
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

@Component
public class EditPostAction implements Action {
	
	@Autowired
	private PostsDAO postsDAO;
	@Autowired
	private CategoriesDAO categoriesDAO;
	
	@Override
	public AjaxResponse run(HttpServletRequest request, HttpServletResponse response) {
		
		String title = request.getParameter("post_title");
		String text = request.getParameter("post_text");
		String categoriesStr = request.getParameter("categories");
		String postIdStr = request.getParameter("post_id");
		
		if(title == null || "".equals(title.trim())){
			return new FailResponse(StringHelper.getLocalString("title_cant_be_blank"));
		}
		
		if(text == null || "".equals(text.trim())){
			return new FailResponse(StringHelper.getLocalString("content_cant_be_blank"));
		}
		
		if(categoriesStr == null || "".equals(categoriesStr.trim())){
			return new FailResponse(StringHelper.getLocalString("you_have_to_choose_category"));
		}
		
		User user = (User)request.getSession().getAttribute(ZaibaloConstants.USER_PARAM_NAME);

		if(user.isGuest()){
			return new FailResponse(StringHelper.getLocalString("operation_forbidden"));
		}
		
		int postId = Integer.parseInt(postIdStr);
		Post post = postsDAO.getObjectById(postId);
		
		if(user.isUser() && !post.getAuthor().equals(user)){
			return new FailResponse(StringHelper.getLocalString("you_cant_edit_other_users_post"));
		}
		
		if(user.isUser() && post.getComments().size() > 0){
			return new FailResponse(StringHelper.getLocalString("you_cant_edit_your_post_after_commented"));
		}
		
		Set<Category> postCategoriesAndTags = new HashSet<Category>();
		
		for (String catName : categoriesStr.split(",")){
			Category category = categoriesDAO.getCategoryByName(catName.trim());
			if(category != null){
				postCategoriesAndTags.add(category);
			}else{
				Category newTag = new Category(catName.trim());
				int catId = categoriesDAO.insert(newTag);
				newTag.setId(catId);
				postCategoriesAndTags.add(newTag);
			}
		}
		
		post.setCategories(postCategoriesAndTags);
		post.setTitle(title);
		post.setContent(text);
		
		postsDAO.update(post);

		request.setAttribute("post", post);
		
		CharArrayWriterResponse customResponse  = new CharArrayWriterResponse(response);
	    try {
			request.getRequestDispatcher("/WEB-INF/jsp/post_wrapper.jsp").forward(request, customResponse);
		} catch (ServletException e) {
			throw new RuntimeException(e.getMessage());
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}
	    Object postHTML = customResponse.getOutput();
		
	    return new SuccessResponse(postHTML);
	}

}
