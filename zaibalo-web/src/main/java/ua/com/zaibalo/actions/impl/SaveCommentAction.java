package ua.com.zaibalo.actions.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.com.zaibalo.actions.Action;
import ua.com.zaibalo.constants.ZaibaloConstants;
import ua.com.zaibalo.db.DataAccessFactory;
import ua.com.zaibalo.helper.AjaxResponse;
import ua.com.zaibalo.helper.CharArrayWriterResponse;
import ua.com.zaibalo.helper.StringHelper;
import ua.com.zaibalo.model.Comment;
import ua.com.zaibalo.model.Post;
import ua.com.zaibalo.model.User;
import ua.com.zaibalo.validation.Validator;

import com.google.gson.Gson;

public class SaveCommentAction implements Action{

	private static final String CONTENT_PARAM_NAME = "content";
	private static final String POST_ID_PARAM_NAME = "post_id";

	private static final long serialVersionUID = 1L;
	
	private Gson gson = new Gson();

	@Override
	public void run(HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws IOException, ServletException{

		String pIDVal = (String) request.getParameter(POST_ID_PARAM_NAME);
		int postId = Integer.parseInt(pIDVal);
		
		String content = (String) request.getParameter(CONTENT_PARAM_NAME);

		DataAccessFactory factory = new DataAccessFactory(request);
		Post post = factory.getPostsAccessInstance().getObjectById(postId);

		String postTitle = post.getTitle();
		
		User user = (User)request.getSession().getAttribute(ZaibaloConstants.USER_PARAM_NAME);
		
		//save comment
		Comment comment = new Comment();
		comment.setContent(content);
		comment.setAuthorId(user.getId());
		comment.setPostId(postId);
		comment.setDate(new Date());
		//comment.setAuthorDisplayName(user.getDisplayName());
		comment.setAuthor(user);
		comment.setPostTitle(postTitle);
		comment.setRatingCount(0);
		comment.setRatingSum(0);
		
		Validator validator = new Validator();
		if(!validator.validateComment(comment)){
			String responseJson = gson.toJson(new AjaxResponse(false, StringHelper.getLocalString("error_colon") + validator.getErrors()));
			out.print(responseJson);
			out.close();
			return;
		}
		
		int id = factory.getCommentsAccessInstance().insert(comment);

		comment.setId(id);
		
		request.setAttribute("comment", comment);
		
		CharArrayWriterResponse customResponse  = new CharArrayWriterResponse(response);
	    request.getRequestDispatcher("/jsp/comment_wrapper.jsp").forward(request, customResponse);
	    Object commentHTML = customResponse.getOutput();
	    
	    String responseJson = gson.toJson(new AjaxResponse(true, commentHTML));
	    out.print(responseJson);
	    out.close();    
		
	}

}
