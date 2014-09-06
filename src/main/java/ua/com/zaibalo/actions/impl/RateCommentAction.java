package ua.com.zaibalo.actions.impl;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ua.com.zaibalo.actions.Action;
import ua.com.zaibalo.constants.ZaibaloConstants;
import ua.com.zaibalo.db.api.CommentRatingsDAO;
import ua.com.zaibalo.db.api.CommentsDAO;
import ua.com.zaibalo.helper.StringHelper;
import ua.com.zaibalo.helper.ajax.AjaxResponse;
import ua.com.zaibalo.helper.ajax.FailResponse;
import ua.com.zaibalo.helper.ajax.RateCommentResponse;
import ua.com.zaibalo.model.Comment;
import ua.com.zaibalo.model.CommentRating;
import ua.com.zaibalo.model.User;

@Component
public class RateCommentAction implements Action{

	@Autowired
	private CommentsDAO commentsDAO;
	@Autowired
	private CommentRatingsDAO commentRatingsDAO;
	
	@Override
	public AjaxResponse run(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String valueStr = request.getParameter("how");
		
		int value = Integer.parseInt(valueStr);
		
		String id = request.getParameter("commentId");
		int commentId = Integer.parseInt(id);
		
		User user = (User)request.getSession().getAttribute(ZaibaloConstants.USER_PARAM_NAME);
		
		if(user.getRole() > 2){
			return new FailResponse(StringHelper.getLocalString("operation_forbidden"));
		}
		
		Comment comment = commentsDAO.getObjectById(commentId);
		
		if(user.getId() == comment.getAuthor().getId()){
			return new FailResponse(StringHelper.getLocalString("you_cant_rate_own_comments"));
		}
		
		CommentRating rate = commentRatingsDAO.getUserVote(user.getId(), commentId);
		if(rate == null){
			rate = new CommentRating();
			rate.setCommentId(comment.getId());
			rate.setUserId(user.getId());
			rate.setDate(new Date());
			rate.setValue(value);
			rate.setPostTitle(comment.getPost().getTitle());
			rate.setPostId(comment.getPost().getId());
			rate.setUserDisplayName(user.getDisplayName());
			
			commentRatingsDAO.createCommentRating(rate);
			commentsDAO.updateCommentRatingSum(value, 1, rate.getCommentId());

		}else{
			if(value == rate.getValue()){
				return new FailResponse(StringHelper.getLocalString("you_already_rated_comment"));
			}
			
			if(value == -rate.getValue()){
				commentRatingsDAO.deleteCommentRate(rate);
				commentsDAO.updateCommentRatingSum(- rate.getValue(), -1, rate.getCommentId());
			}
		}
		
		comment = commentsDAO.getObjectById(commentId);
		return new RateCommentResponse(comment.getRatingSum(), comment.getRatingCount());
	}
	
}
