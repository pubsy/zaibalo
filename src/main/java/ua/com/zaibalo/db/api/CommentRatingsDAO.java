package ua.com.zaibalo.db.api;

import java.util.List;

import ua.com.zaibalo.model.Comment;
import ua.com.zaibalo.model.CommentRating;
import ua.com.zaibalo.model.User;
import ua.com.zaibalo.model.UserRating;

public interface CommentRatingsDAO {

	void createCommentRating(CommentRating rating);
	UserRating getUserCommentRatingSum(User user);
	CommentRating getUserVote(User user, Comment comment);
	void deleteCommentRate(CommentRating rating);
	List<CommentRating> getUserCommentRatings(Comment comment);
	
}
