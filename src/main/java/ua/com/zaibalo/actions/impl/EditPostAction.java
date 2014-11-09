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

@Component
public class EditPostAction implements Action {
	
	@Autowired
	private PostsBusinessLogic postsBusinessLogic;
	@Autowired
	private CategoriesDAO categoriesDAO;
	
	@Override
	public AjaxResponse run(HttpServletRequest request, HttpServletResponse response) {
		
		String text = request.getParameter("post_text");
		String postIdStr = request.getParameter("post_id");

		if(text == null || "".equals(text.trim())){
			return new FailResponse(StringHelper.getLocalString("content_cant_be_blank"));
		}
		
		User user = (User)request.getSession().getAttribute(ZaibaloConstants.USER_PARAM_NAME);

		if(user.isGuest()){
			return new FailResponse(StringHelper.getLocalString("operation_forbidden"));
		}
		
		int postId = Integer.parseInt(postIdStr);
		
		Post post;
		try {
			post = postsBusinessLogic.updatePost(user, postId, text);
		} catch (ValidationException e) {
			return new FailResponse(e.getMessage());
		}

		request.setAttribute("post", post);
		
		CharArrayWriterResponse customResponse  = new CharArrayWriterResponse(response);
		try {
			request.getRequestDispatcher("/WEB-INF/jsp/post_wrapper.jsp").forward(request, customResponse);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}

	    Object postHTML = customResponse.getOutput();
	    return new SuccessResponse(postHTML);
	}

}
