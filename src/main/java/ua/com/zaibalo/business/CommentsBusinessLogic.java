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

	public Comment saveComment(String authorLoginName, String content, int postId){
		Assert.notNull(authorLoginName);
		Assert.notNull(content);
		Assert.isTrue(postId > 0);
		
		Post post = postsDAO.getObjectById(postId);
		if(post == null){
			throw new RuntimeException("Post with id: " + postId + " does not exist");
		}
		
		User author = usersDAO.getUserByLoginName(authorLoginName);
		if(author == null){
			throw new RuntimeException("User with loginName: " + authorLoginName + " does not exist");
		}
		
		Comment comment = new Comment();
		comment.setAuthor(author);
		comment.setDate(new Date());
		comment.setPost(post);
		comment.setContent(content);
		comment.setDate(new Date());
		
		return commentsDAO.persist(comment);
	}
	
	public Comment updateComment(String authorLoginName, int commentId, String content){
		Comment comment = commentsDAO.getObjectById(commentId);
		if(comment == null){
			throw new RuntimeException("Comment with id: " + commentId + " does not exist");
		}
		
		User author = usersDAO.getUserByLoginName(authorLoginName);
		if(author == null){
			throw new RuntimeException("User with loginName: " + authorLoginName + " does not exist");
		}
		
		if(author.getId() != comment.getAuthor().getId()){
			throw new RuntimeException("You do not owe comment with id: " + commentId + ". This will be reported.");
		}
		
		comment.setContent(content);
		return commentsDAO.update(comment);
	}

	public Comment getCommentById(Integer commentId) {
		Comment comment = commentsDAO.getObjectById(commentId);
		if(comment == null){
			throw new RuntimeException("Comment with id: " + commentId + " does not exist");
		}
		return comment;
	}
}
