package ua.com.zaibalo.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
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
import ua.com.zaibalo.helper.ajax.AjaxResponse;
import ua.com.zaibalo.helper.ajax.SuccessResponse;
import ua.com.zaibalo.model.Comment;
import ua.com.zaibalo.model.Post;
import ua.com.zaibalo.model.User;
import ua.com.zaibalo.security.Secured;

@Controller
@RequestMapping("/secure")
public class CommentController {

	@Autowired
    private UsersBusinessLogic usersBusinessLogic;
    @Autowired
    private CommentsBusinessLogic commentsBusinessLogic;
    @Autowired
    private PostsBusinessLogic postsBusinessLogic;
    
    @Secured
    @RequestMapping(value = "/comment/{commentId}", method = RequestMethod.DELETE, produces="application/json; charset=UTF-8")
    @ResponseBody
    public AjaxResponse deleteComment(@PathVariable Integer commentId, HttpServletRequest request) {
    	User user = (User)request.getSession().getAttribute(ZaibaloConstants.USER_PARAM_NAME);
        Comment comment = commentsBusinessLogic.getCommentById(commentId);
        
        commentsBusinessLogic.deleteComment(comment, user);
        
		return new SuccessResponse();
    }
    
    @Secured
	@RequestMapping(value = "/comment", method = RequestMethod.POST, produces="application/json; charset=UTF-8")
	@ResponseBody
	public Comment addComment(@RequestParam Integer postId, @RequestParam String content, HttpServletRequest request) {
		User user = (User)request.getSession().getAttribute(ZaibaloConstants.USER_PARAM_NAME);
		Post post = postsBusinessLogic.getPostById(postId);
		
		return commentsBusinessLogic.saveComment(user, post, content);
	}
	
	@RequestMapping(value = "/comment/{commentId}", method = RequestMethod.POST)
	@ResponseBody
	public Comment updateComment(@PathVariable Integer commentId, @RequestParam String content, HttpServletRequest request) {
		User user = (User)request.getSession().getAttribute(ZaibaloConstants.USER_PARAM_NAME);
		Comment comment = commentsBusinessLogic.getCommentById(commentId);
		
		return commentsBusinessLogic.updateComment(user, comment, content);
	}

}
