package ua.com.zaibalo.db.api;

import java.util.List;

import ua.com.zaibalo.model.Comment;

public interface CommentsAccessInterface{
	List<Comment> getAllPostComments(int postId);
	int insert(Comment object);
	void delete(int id);
	List<Comment> getRecentComments(int count);
	int getUserCommentCount(int userId);
	Comment getObjectById(int commentId);
	void updateCommentRatingSum(int value, int count, int commentId);
}
