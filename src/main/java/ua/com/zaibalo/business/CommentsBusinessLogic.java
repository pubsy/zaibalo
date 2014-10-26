package ua.com.zaibalo.business;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import ua.com.zaibalo.db.api.CommentsDAO;
import ua.com.zaibalo.db.api.PostsDAO;
import ua.com.zaibalo.db.api.UsersDAO;
import ua.com.zaibalo.helper.StringHelper;
import ua.com.zaibalo.model.Comment;
import ua.com.zaibalo.model.Post;
import ua.com.zaibalo.model.User;

@Component
@Transactional(propagation=Propagation.REQUIRED)
public class CommentsBusinessLogic {
	
	@Autowired
	private CommentsDAO commentsDAO;
	
	@Autowired
	private PostsDAO postsDAO;
	
	@Autowired
	private UsersDAO usersDAO;

	public Comment saveComment(User author, Post post, String content){
		Assert.notNull(author, "Auhtor cannot be null");
		Assert.notNull(content, "Content cannot be null");
		Assert.notNull(post, "Post cannot be null");
		
		Comment comment = new Comment();
		comment.setAuthor(author);
		comment.setDate(new Date());
		comment.setPost(post);
		comment.setContent(content);
		comment.setDate(new Date());
		
		return commentsDAO.persist(comment);
	}
	
	public Comment updateComment(User author, Comment comment, String newContent){
		Assert.notNull(author, "Auhtor cannot be null");
		Assert.notNull(newContent, "Content cannot be null");
		Assert.notNull(comment, "Comment cannot be null");

		if(author.getId() != comment.getAuthor().getId()){
			throw new RuntimeException("You do not owe comment with id: " + comment.getId() + ". This will be reported.");
		}
		
		comment.setContent(newContent);
		return commentsDAO.update(comment);
	}

	public Comment getCommentById(Integer commentId) {
		Comment comment = commentsDAO.getObjectById(commentId);
		if(comment == null){
			throw new RuntimeException("Comment with id: " + commentId + " does not exist");
		}
		return comment;
	}
	
	public void deleteComment(Comment comment, User user) {
		Assert.notNull(comment);
		Assert.notNull(user);
		
		if(!user.equals(comment.getAuthor())){
			throw new RuntimeException(StringHelper.getLocalString("you.are.not.powerfull.enough"));
		}

		commentsDAO.delete(comment);
	}
}
