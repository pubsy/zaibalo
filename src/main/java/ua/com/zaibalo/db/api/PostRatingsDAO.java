package ua.com.zaibalo.db.api;

import java.util.List;

import ua.com.zaibalo.model.Post;
import ua.com.zaibalo.model.PostRating;
import ua.com.zaibalo.model.User;
import ua.com.zaibalo.model.UserRating;

public interface PostRatingsDAO {
	void savePostRating(PostRating rating);
	boolean isPostRatedByUser(Post post, User user);
	UserRating getUserPostRatingSum(User user);
	PostRating getUserVote(User user, Post post);
	void deletePostRating(PostRating postRating);
	List<PostRating> getPostRatings(Post post);
	void update(PostRating postRating);
}
