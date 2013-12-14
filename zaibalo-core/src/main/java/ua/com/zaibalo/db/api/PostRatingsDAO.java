package ua.com.zaibalo.db.api;

import ua.com.zaibalo.model.PostRating;
import ua.com.zaibalo.model.UserRating;

public interface PostRatingsDAO {
	void savePostRating(PostRating rating);
	boolean isPostRatedByUser(int postId, int userId);
	UserRating getUserPostRatingSum(int userId);
}
