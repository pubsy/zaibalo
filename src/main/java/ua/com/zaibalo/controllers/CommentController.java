package ua.com.zaibalo.controllers;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ua.com.zaibalo.business.CommentsBusinessLogic;
import ua.com.zaibalo.business.PostsBusinessLogic;
import ua.com.zaibalo.business.UsersBusinessLogic;
import ua.com.zaibalo.constants.ZaibaloConstants;
import ua.com.zaibalo.model.Comment;
import ua.com.zaibalo.model.Post;
import ua.com.zaibalo.model.User;

@Controller
@RequestMapping("/secure")
@Secured({"ROLE_USER", "ROLE_ADMIN"})
public class CommentController {

	@Autowired
    private UsersBusinessLogic usersBusinessLogic;
    @Autowired
    private CommentsBusinessLogic commentsBusinessLogic;
    @Autowired
    private PostsBusinessLogic postsBusinessLogic;
    
    @RequestMapping(value = "/comment/{commentId}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteComment(@PathVariable Integer commentId, Principal principal) {
        User user = usersBusinessLogic.getUserByLoginName(principal.getName());
        Comment comment = commentsBusinessLogic.getCommentById(commentId);
        
        commentsBusinessLogic.deleteComment(comment, user);
        
		return new ResponseEntity<String>("{\"status\":\"success\"}", HttpStatus.OK);
    }
    
	@RequestMapping(value = "/comment", method = RequestMethod.POST)
	@ResponseBody
	public Comment addComment(@RequestParam Integer postId, @RequestParam String content, HttpServletRequest request) {
		User user = (User)request.getSession().getAttribute(ZaibaloConstants.USER_PARAM_NAME);
		Post post = postsBusinessLogic.getPostById(postId);
		
		return commentsBusinessLogic.saveComment(user, post, content);
	}
	
	@RequestMapping(value = "/comment/{commentId}", method = RequestMethod.POST)
	@ResponseBody
	public Comment updateComment(@PathVariable Integer commentId, @RequestParam String content, Principal principal) {
		User user = usersBusinessLogic.getUserByLoginName(principal.getName());
		Comment comment = commentsBusinessLogic.getCommentById(commentId);
		
		return commentsBusinessLogic.updateComment(user, comment, content);
	}

}
