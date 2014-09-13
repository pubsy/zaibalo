package ua.com.zaibalo.db.api;

import java.util.List;

import ua.com.zaibalo.model.Comment;
import ua.com.zaibalo.model.User;

public interface CommentsDAO{
	Comment update(Comment comment);
	void delete(Comment comment);
	List<Comment> getRecentComments(int count);
	int getUserCommentCount(User user);
	Comment getObjectById(int commentId);
	void updateCommentRatingSum(int value, int count, Comment comment);
	Comment persist(Comment comment);
}
