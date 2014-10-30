package ua.com.zaibalo.actions.impl;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ua.com.zaibalo.actions.Action;
import ua.com.zaibalo.db.api.CommentRatingsDAO;
import ua.com.zaibalo.db.api.CommentsDAO;
import ua.com.zaibalo.db.api.PostRatingsDAO;
import ua.com.zaibalo.db.api.PostsDAO;
import ua.com.zaibalo.helper.CharArrayWriterResponse;
import ua.com.zaibalo.helper.ajax.AjaxResponse;
import ua.com.zaibalo.helper.ajax.SuccessResponse;
import ua.com.zaibalo.model.Comment;
import ua.com.zaibalo.model.CommentRating;
import ua.com.zaibalo.model.Post;
import ua.com.zaibalo.model.PostRating;

/**
 * Servlet implementation class ShowRatingAction
 */

@Component
public class ShowRatingAction implements Action {

	@Autowired
	private PostRatingsDAO postRatingsDAO;
	@Autowired
	private PostsDAO postsDAO;
	@Autowired
	private CommentsDAO commentssDAO;
	@Autowired
	private CommentRatingsDAO commentRatingsDAO;
	
	
	@Override
	public AjaxResponse run(HttpServletRequest request, HttpServletResponse response) {
		String typeParam = request.getParameter("type");
		String idParam = request.getParameter("id");
		
		int id = Integer.parseInt(idParam);
		
		CharArrayWriterResponse customResponse  = new CharArrayWriterResponse(response);
		if("post".equals(typeParam)){
			Post post = postsDAO.getObjectById(id);
			List<PostRating> postRatings = postRatingsDAO.getPostRatings(post);
			
			request.setAttribute("postRatings", postRatings);
			
			try {
				request.getRequestDispatcher("/WEB-INF/jsp/post_ratings.jsp").forward(request, customResponse);
			} catch (ServletException e) {
				throw new RuntimeException();
			} catch (IOException e) {
				throw new RuntimeException();
			}
		}else if("comment".equals(typeParam)){
			Comment comment = commentssDAO.getObjectById(id);
			List<CommentRating> commentRatings = commentRatingsDAO.getUserCommentRatings(comment);
			
			request.setAttribute("commentRatings", commentRatings);
			
			try {
				request.getRequestDispatcher("/WEB-INF/jsp/comment_ratings.jsp").forward(request, customResponse);
			} catch (ServletException e) {
				throw new RuntimeException();
			} catch (IOException e) {
				throw new RuntimeException();
			}
		}
		
	    
	    return new SuccessResponse(customResponse.getOutput());
	}

}
