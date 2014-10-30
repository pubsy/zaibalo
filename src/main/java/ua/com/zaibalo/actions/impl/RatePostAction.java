package ua.com.zaibalo.actions.impl;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ua.com.zaibalo.actions.Action;
import ua.com.zaibalo.constants.ZaibaloConstants;
import ua.com.zaibalo.db.api.PostRatingsDAO;
import ua.com.zaibalo.db.api.PostsDAO;
import ua.com.zaibalo.helper.StringHelper;
import ua.com.zaibalo.helper.ajax.AjaxResponse;
import ua.com.zaibalo.helper.ajax.FailResponse;
import ua.com.zaibalo.helper.ajax.RateCommentResponse;
import ua.com.zaibalo.model.Post;
import ua.com.zaibalo.model.PostRating;
import ua.com.zaibalo.model.User;

@Component
public class RatePostAction implements Action {
	
	private final static Logger LOGGER = Logger.getLogger(RatePostAction.class);

	@Autowired
	private PostsDAO postsDAO;
	@Autowired
	private PostRatingsDAO postRatingsDAO;
	
	@Override
	public AjaxResponse run(HttpServletRequest request, HttpServletResponse response) {
		
		String action = request.getParameter("how");
		int value;
		if(action.equals("up")){
			value = 1;
		}else if (action.equals("down")){
			value = -1;
		}else{
			String message = "Wrong 'how' parameter sent";
			LOGGER.error(message);
			return new FailResponse(message);
		}
		
		String id = request.getParameter("postId");
		int postId = Integer.parseInt(id);

		
		User user = (User)request.getSession().getAttribute(ZaibaloConstants.USER_PARAM_NAME);
		
		if(user.isGuest()){
			return new FailResponse(StringHelper.getLocalString("operation_forbidden"));
		}
		
		Post post = postsDAO.getObjectById(postId);

		if (user.equals(post.getAuthor())) {
			return new FailResponse(StringHelper.getLocalString("you_cant_rate_own_posts"));
		}
		
		PostRating postRating = postRatingsDAO.getUserVote(user, post);

		int returnSum;
		int returnCount;
		
		if( postRating == null ) {
			PostRating rating = new PostRating();
			rating.setPost(post);
			rating.setUser(user);
			rating.setDate(new Date());
			rating.setValue(value);
			
			postRatingsDAO.savePostRating(rating);
			
			returnCount = post.getRatingCount() + 1;
			returnSum = post.getRatingSum() + value;
		} else {
			if(value == postRating.getValue()){
				return new FailResponse(StringHelper.getLocalString("you_already_rated"));
			}
			
			returnCount = post.getRatingCount() - 1;
			returnSum = post.getRatingSum() - postRating.getValue();

			postRatingsDAO.deletePostRating(postRating);
			postsDAO.updatePostRatingSum(-postRating.getValue(), -1, postRating.getPost());
		}
		
		return new RateCommentResponse(returnSum, returnCount);
	}
}
