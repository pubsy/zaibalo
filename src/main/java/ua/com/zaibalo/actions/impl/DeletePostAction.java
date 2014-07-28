package ua.com.zaibalo.actions.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ua.com.zaibalo.actions.Action;
import ua.com.zaibalo.constants.ZaibaloConstants;
import ua.com.zaibalo.db.api.PostsDAO;
import ua.com.zaibalo.helper.ServletHelperService;
import ua.com.zaibalo.helper.StringHelper;
import ua.com.zaibalo.helper.ajax.AjaxResponse;
import ua.com.zaibalo.helper.ajax.FailResponse;
import ua.com.zaibalo.helper.ajax.SuccessResponse;
import ua.com.zaibalo.model.Post;
import ua.com.zaibalo.model.User;

@Component
public class DeletePostAction implements Action{

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
			ServletHelperService.logException(new Exception("Rights violation!"), request);
			return new FailResponse(StringHelper.getLocalString("you.are.not.powerfull.enough"));
		}
		
		postsDAO.delete(post);
		
		System.out.println();
		System.out.println("User with id: " + user.getId() + " deleted post with id: " + postId);
		System.out.println();
		
		return new SuccessResponse();
	}
}
