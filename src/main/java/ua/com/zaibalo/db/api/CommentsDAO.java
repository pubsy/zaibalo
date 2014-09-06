package ua.com.zaibalo.db.api;

import java.util.List;

import ua.com.zaibalo.model.Comment;

public interface CommentsDAO{
	List<Comment> getAllPostComments(int postId);
	Comment update(Comment comment);
	void delete(int id);
	List<Comment> getRecentComments(int count);
	int getUserCommentCount(int userId);
	Comment getObjectById(int commentId);
	void updateCommentRatingSum(int value, int count, int commentId);
	Comment persist(Comment comment);
}
