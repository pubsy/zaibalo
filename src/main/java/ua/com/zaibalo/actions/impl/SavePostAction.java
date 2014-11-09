package ua.com.zaibalo.actions.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ua.com.zaibalo.actions.Action;
import ua.com.zaibalo.business.PostsBusinessLogic;
import ua.com.zaibalo.constants.ZaibaloConstants;
import ua.com.zaibalo.db.api.CategoriesDAO;
import ua.com.zaibalo.exceptions.ValidationException;
import ua.com.zaibalo.helper.CharArrayWriterResponse;
import ua.com.zaibalo.helper.StringHelper;
import ua.com.zaibalo.helper.ajax.AjaxResponse;
import ua.com.zaibalo.helper.ajax.FailResponse;
import ua.com.zaibalo.helper.ajax.SuccessResponse;
import ua.com.zaibalo.model.Post;
import ua.com.zaibalo.model.User;
import ua.com.zaibalo.social.VKPostToGroup;

@Component
public class SavePostAction implements Action{

	private static final String POST_TEXT = "post_text";
	
	@Autowired
	private CategoriesDAO categoriesDAO;
	@Autowired
	private PostsBusinessLogic postsBusinessLogic;
	
	@Override
	public AjaxResponse run(HttpServletRequest request, HttpServletResponse response) {
		String content = (String) request.getParameter(POST_TEXT);

		User user = (User) request.getSession().getAttribute(ZaibaloConstants.USER_PARAM_NAME);
		if(user.isGuest()){
			return new FailResponse(StringHelper.getLocalString("operation_forbidden"));
		}
		
		Post post;
		try {
			post = postsBusinessLogic.createPost(content, user);
		} catch (ValidationException e) {
			return new FailResponse(e.getMessage());
		}

		request.setAttribute("post", post);
		
		String extr = post.getContent();
		final String text = extr + "\n\n" + post.getAuthor().getDisplayName() + "\n\n" + "http://www.zaibalo.com.ua/post/" + post.getId();
			
		new Thread(new Runnable(){
			@Override
			public void run() {
				VKPostToGroup.postToVKGroup(text);
			}
		}).start();
		
		CharArrayWriterResponse customResponse  = new CharArrayWriterResponse(response);
	    try {
			request.getRequestDispatcher("/WEB-INF/jsp/post_wrapper.jsp").forward(request, customResponse);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	    
	    return new SuccessResponse(customResponse.getOutput());
	}
}
