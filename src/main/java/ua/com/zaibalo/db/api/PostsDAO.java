package ua.com.zaibalo.db.api;

import java.util.Date;
import java.util.List;

import ua.com.zaibalo.model.Post;
import ua.com.zaibalo.model.User;

public interface PostsDAO{
	int insert(Post post);
	void delete(Post post);
	List<Post> getOrderedList(int from, int count, Post.PostOrder order);
	Post getObjectById(int id);

	List<Post> getLatestUserPosts(User user, int count);

	int getUserPostCount(User user);
	
	List<Post> getPostsList(List<Integer> ids, Date fromDate, Post.PostOrder order, int from, int count);
	long getPostsListSize(List<Integer> ids, Date fromDate);
	List<Post> getAllPostsList();
	void updatePostRatingSum(int value, int count, Post post);
	public abstract void update(Post post);

}
