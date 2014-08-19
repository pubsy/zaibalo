package ua.com.zaibalo.actions.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ua.com.zaibalo.actions.Action;
import ua.com.zaibalo.constants.ZaibaloConstants;
import ua.com.zaibalo.db.api.PostsDAO;
import ua.com.zaibalo.helper.StringHelper;
import ua.com.zaibalo.helper.ajax.AjaxResponse;
import ua.com.zaibalo.helper.ajax.FailResponse;
import ua.com.zaibalo.helper.ajax.SuccessResponse;
import ua.com.zaibalo.model.Post;
import ua.com.zaibalo.model.User;

@Component
public class DeletePostAction implements Action{
	
	private final static Logger LOGGER = Logger.getLogger(DeletePostAction.class);

	@Autowired
	private PostsDAO postsDAO;
	
	@Override
	public AjaxResponse run(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String postIdParamValue = request.getParameter("postId");
		int postId = Integer.parseInt(postIdParamValue);

		User user = (User)request.getSession().getAttribute(ZaibaloConstants.USER_PARAM_NAME);
		
		if(user.getRole() > 2){
			return new FailResponse(StringHelper.getLocalString("operation_forbidden"));
		}
		
		Post post = postsDAO.getObjectById(postId);
		
		if(user.getId() != post.getAuthorId() && user.getRole() >=2){
			LOGGER.error("Rights violation!");
			return new FailResponse(StringHelper.getLocalString("you.are.not.powerfull.enough"));
		}
		
		postsDAO.delete(post);
		
		LOGGER.warn("User with id: " + user.getId() + " deleted post with id: " + postId);
		
		return new SuccessResponse();
	}
}
