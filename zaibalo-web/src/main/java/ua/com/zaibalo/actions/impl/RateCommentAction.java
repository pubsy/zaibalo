package ua.com.zaibalo.actions.impl;

import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.com.zaibalo.actions.Action;
import ua.com.zaibalo.constants.ZaibaloConstants;
import ua.com.zaibalo.db.DataAccessFactory;
import ua.com.zaibalo.helper.StringHelper;
import ua.com.zaibalo.model.Comment;
import ua.com.zaibalo.model.CommentRating;
import ua.com.zaibalo.model.User;

public class RateCommentAction implements Action{

	@Override
	public void run(HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws Exception {
		
		String valueStr = request.getParameter("how");
		
		int value = Integer.parseInt(valueStr);
		
		String id = request.getParameter("commentId");
		int commentId = Integer.parseInt(id);
		
		User user = (User)request.getSession().getAttribute(ZaibaloConstants.USER_PARAM_NAME);
		
		DataAccessFactory factory = new DataAccessFactory(request);
		Comment comment = factory.getCommentsAccessInstance().getObjectById(commentId);
		
		if(user.getId() == comment.getAuthorId()){
			out.write("{\"status\":\"fail\", \"message\":\"" + StringHelper.getLocalString("you_cant_rate_own_comments") + "\"}");
			out.close();
			return;
		}
		
		CommentRating rate = factory.getCommentRatingAccessInstance().getUserVote(user.getId(), commentId);
		if(rate == null){
			rate = new CommentRating();
			rate.setCommentId(comment.getId());
			rate.setUserId(user.getId());
			rate.setDate(new Date());
			rate.setValue(value);
			rate.setPostTitle(comment.getPostTitle());
			rate.setPostId(comment.getPostId());
			rate.setUserDisplayName(user.getDisplayName());
			
			factory.getCommentRatingAccessInstance().createCommentRating(rate);
			factory.getCommentsAccessInstance().updateCommentRatingSum(value, 1, rate.getCommentId());

		}else{
			if(value == rate.getValue()){
				out.write("{\"status\":\"fail\", \"message\":\"" + StringHelper.getLocalString("you_already_rated_comment") + "\"}");
				out.close();
				return;
			}
			
			if(value == -rate.getValue()){
				factory.getCommentRatingAccessInstance().deleteCommentRate(rate.getId());
				factory.getCommentsAccessInstance().updateCommentRatingSum(- rate.getValue(), -1, rate.getCommentId());
			}
		}
		
		comment = factory.getCommentsAccessInstance().getObjectById(commentId);
		out.write("{\"status\":\"success\", \"sum\":\"" + comment.getRatingSum() +  "\", \"count\":\"" + comment.getRatingCount() + "\"}");
		out.close();
		return;

	}

}
