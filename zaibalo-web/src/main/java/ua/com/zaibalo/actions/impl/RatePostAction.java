package ua.com.zaibalo.actions.impl;

import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.com.zaibalo.actions.Action;
import ua.com.zaibalo.constants.ZaibaloConstants;
import ua.com.zaibalo.db.DataAccessFactory;
import ua.com.zaibalo.helper.StringHelper;
import ua.com.zaibalo.model.Post;
import ua.com.zaibalo.model.PostRating;
import ua.com.zaibalo.model.User;

public class RatePostAction implements Action {
	private static final long serialVersionUID = 1L;

	@Override
	public void run(HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws Exception {
		
		String action = request.getParameter("how");
		int value;
		if(action.equals("up")){
			value = 1;
		}else if (action.equals("down")){
			value = -1;
		}else{
			out.write("{\"status\":\"fail\", \"message\":\"Wrong 'how' parameter sent\"}");
			out.close();
			return;
		}
		
		String id = request.getParameter("postId");
		int postId = Integer.parseInt(id);

		
		User user = (User)request.getSession().getAttribute(ZaibaloConstants.USER_PARAM_NAME);
		
		DataAccessFactory factory = new DataAccessFactory(request);
		boolean	isRated = factory.getPostRatingsAccessInstance().isPostRatedByUser(postId, user.getId());

		
		if(isRated){
			out.write("{\"status\":\"fail\", \"message\":\"" + StringHelper.getLocalString("you_already_rated") + "\"}");
			out.close();
			return;
		}
		
		Post post = factory.getPostsAccessInstance().getObjectById(postId);

		if (user.getId() == post.getAuthorId()) {
			out.write("{\"status\":\"fail\", \"message\":\"" + StringHelper.getLocalString("you_cant_rate_own_posts") + "\"}");
			out.close();
			return;
		}

		PostRating rating = new PostRating();
		rating.setPostId(post.getId());
		rating.setUserId(user.getId());
		rating.setDate(new Date());
		rating.setValue(value);
		rating.setPostTitle(post.getTitle());
		rating.setUserDisplayName(user.getDisplayName());

		factory.getPostRatingsAccessInstance().savePostRating(rating);
		
		out.write("{\"status\":\"success\"}");
		out.close();
		
	}
}
