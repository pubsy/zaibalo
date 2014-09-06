package ua.com.zaibalo.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ua.com.zaibalo.business.CommentsBusinessLogic;
import ua.com.zaibalo.model.Comment;

@Controller
@RequestMapping("/api/comments")
public class CommentsRestController {
	
	@Autowired
	private CommentsBusinessLogic commentsBusinessLogig;
	
	@RequestMapping(value = "/{commentId}", method = RequestMethod.PUT)
	@ResponseBody
	public Comment updateComment(@RequestBody Comment comment, @PathVariable int commentId) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return commentsBusinessLogig.updateComment(auth.getName(), commentId, comment.getContent());
	}
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	@ResponseBody
	public Comment addComment(@RequestBody Comment comment) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return commentsBusinessLogig.saveComment(auth.getName(), comment.getContent(), comment.getPost().getId());
	}

	@RequestMapping(value = "/{commentId}", method = RequestMethod.GET)
	@ResponseBody
	public Comment getComment(@PathVariable Integer commentId) {
		return commentsBusinessLogig.getCommentById(commentId);
	}

}
