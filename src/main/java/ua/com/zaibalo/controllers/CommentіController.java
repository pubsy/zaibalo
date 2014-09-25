package ua.com.zaibalo.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Required;
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

@Controller(value="/secure")
@Secured("USER")
public class CommentіController {
    
    private DeleteCommentAction deleteCommentAction;
    private UsersBusinessLogic usersBusinessLogic;
    private CommentsBusinessLogic commentsBusinessLogic;
    
    @RequestMapping(value = "/delete-comment/{commentId}", method = RequestMethod.GET)
    public void deleteComment(@PathVariable Integer commentId, Principal principal) {
        User user = usersBusinessLogic.getUserByLoginName(principal.getName());
        Comment comment = commentsBusinessLogic.getCommentById(commentId);
        
        deleteCommentAction.deleteComment(comment, user);
    }

    @Required
    public void setDeleteCommentAction(DeleteCommentAction deleteCommentAction) {
        this.deleteCommentAction = deleteCommentAction;
    }

    @Required
    public void setUserBusinessLogic(UsersBusinessLogic userBusinessLogic) {
        this.usersBusinessLogic = userBusinessLogic;
    }

    @Required
    public void setCommentsBusinessLogic(CommentsBusinessLogic commentsBusinessLogic) {
        this.commentsBusinessLogic = commentsBusinessLogic;
    }
}
