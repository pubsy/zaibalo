package ua.com.zaibalo.actions.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ua.com.zaibalo.actions.Action;
import ua.com.zaibalo.db.api.CommentRatingsDAO;
import ua.com.zaibalo.db.api.PostRatingsDAO;
import ua.com.zaibalo.helper.CharArrayWriterResponse;
import ua.com.zaibalo.helper.ajax.AjaxResponse;
import ua.com.zaibalo.helper.ajax.SuccessResponse;
import ua.com.zaibalo.model.CommentRating;
import ua.com.zaibalo.model.PostRating;

/**
 * Servlet implementation class ShowRatingAction
 */

@Component
public class ShowRatingAction implements Action {

	@Autowired
	private PostRatingsDAO postRatingsDAO;
	@Autowired
	private CommentRatingsDAO commentRatingsDAO;
	
	
	@Override
	public AjaxResponse run(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String typeParam = request.getParameter("type");
		String idParam = request.getParameter("id");
		
		int id = Integer.parseInt(idParam);
		
		CharArrayWriterResponse customResponse  = new CharArrayWriterResponse(response);
		if("post".equals(typeParam)){
			List<PostRating> postRatings = postRatingsDAO.getPostRatings(id);
			
			request.setAttribute("postRatings", postRatings);
			
			request.getRequestDispatcher("/jsp/post_ratings.jsp").forward(request, customResponse);
		}else if("comment".equals(typeParam)){
			List<CommentRating> commentRatings = commentRatingsDAO.getUserCommentRatings(id);
			
			request.setAttribute("commentRatings", commentRatings);
			
			request.getRequestDispatcher("/jsp/comment_ratings.jsp").forward(request, customResponse);
		}
		
	    
	    return new SuccessResponse(customResponse.getOutput());
	}

}
