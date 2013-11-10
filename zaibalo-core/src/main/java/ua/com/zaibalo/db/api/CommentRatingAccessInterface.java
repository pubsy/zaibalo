package ua.com.zaibalo.db.api;

import ua.com.zaibalo.model.CommentRating;
import ua.com.zaibalo.model.UserRating;

public interface CommentRatingAccessInterface {

	void createCommentRating(CommentRating rating);
	UserRating getUserCommentRatingSum(int userId);
	CommentRating getUserVote(int userId, int commentId);
	void deleteCommentRate(int id);
	
}
