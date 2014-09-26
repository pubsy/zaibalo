package ua.com.zaibalo.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ua.com.zaibalo.actions.impl.DeleteCommentAction;
import ua.com.zaibalo.business.CommentsBusinessLogic;
import ua.com.zaibalo.business.UsersBusinessLogic;
import ua.com.zaibalo.model.Comment;
import ua.com.zaibalo.model.User;

@Controller
@RequestMapping("/secure")
@Secured("USER")
public class CommentController {
    
	@Autowired
    private DeleteCommentAction deleteCommentAction;
	@Autowired
    private UsersBusinessLogic usersBusinessLogic;
    @Autowired
    private CommentsBusinessLogic commentsBusinessLogic;
    
    @RequestMapping(value = "/delete-comment/{commentId}", method = RequestMethod.POST)
    public void deleteComment(@PathVariable Integer commentId, Principal principal) {
        User user = usersBusinessLogic.getUserByLoginName(principal.getName());
        Comment comment = commentsBusinessLogic.getCommentById(commentId);
        
        deleteCommentAction.deleteComment(comment, user);
    }
}
