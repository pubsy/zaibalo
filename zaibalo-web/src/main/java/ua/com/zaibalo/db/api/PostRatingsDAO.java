package ua.com.zaibalo.db.api;

import java.util.List;

import ua.com.zaibalo.model.PostRating;
import ua.com.zaibalo.model.UserRating;

public interface PostRatingsDAO {
	void savePostRating(PostRating rating);
	boolean isPostRatedByUser(int postId, int userId);
	UserRating getUserPostRatingSum(int userId);
	PostRating getUserVote(int userId, int postId);
	void deletePostRating(PostRating postRating);
	List<PostRating> getPostRatings(int postId);
	void update(PostRating postRating);
}
