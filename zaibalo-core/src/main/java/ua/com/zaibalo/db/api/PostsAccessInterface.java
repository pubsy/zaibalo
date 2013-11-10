package ua.com.zaibalo.db.api;

import java.util.Date;
import java.util.List;

import ua.com.zaibalo.model.Post;

public interface PostsAccessInterface{
	int insert(Post object);
	void delete(Post post);
	List<Post> getOrderedList(int from, int count, Post.PostOrder order);
	Post getObjectById(int id);

	List<Post> getLatestUserPosts(int userId, int count);

	int getUserPostCount(int userId);
	void update(Post post);
	
	
	List<Post> getPostsList(List<Integer> ids, Date fromDate, Post.PostOrder order, int from, int count);
	int getPostsListSize(List<Integer> ids, Date fromDate);
	List<Post> getAllPostsList();
	
	List<Post> searchByPosts(String term, int from, int count);
	int getSearchPostsListSize(String searchTerm);

}
