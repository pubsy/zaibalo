package ua.com.zaibalo.actions.impl;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.com.zaibalo.actions.Action;
import ua.com.zaibalo.constants.ZaibaloConstants;
import ua.com.zaibalo.db.DataAccessFactory;
import ua.com.zaibalo.helper.ServletHelper;
import ua.com.zaibalo.helper.StringHelper;
import ua.com.zaibalo.model.Post;
import ua.com.zaibalo.model.User;

public class DeletePostAction implements Action{

	private static final long serialVersionUID = 1L;

	@Override
	public void run(HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws Exception {
		String postIdParamValue = request.getParameter("postId");
		int postId = Integer.parseInt(postIdParamValue);

		DataAccessFactory factory = new DataAccessFactory(request);
		User user = (User)request.getSession().getAttribute(ZaibaloConstants.USER_PARAM_NAME);
		Post post = factory.getPostsAccessInstance().getObjectById(postId);
		
		if(user.getId() != post.getAuthorId() && user.getRole() >=2){
			out.write("{\"status\":\"fail\", \"message\":\"" + StringHelper.getLocalString("you.are.not.powerfull.enough") + "\"}");
			ServletHelper.logException(new Exception("Rights violation!"), request);
			return;
		}
		
		factory.getPostsAccessInstance().delete(post);
		
		System.out.println();
		System.out.println("User with id: " + user.getId() + " deleted post with id: " + postId);
		System.out.println();
		
		out.write("{\"status\":\"success\"}");
	}
}
