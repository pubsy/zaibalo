package ua.com.zaibalo.actions.impl;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.com.zaibalo.actions.Action;
import ua.com.zaibalo.db.DataAccessFactory;
import ua.com.zaibalo.model.CommentRating;
import ua.com.zaibalo.model.PostRating;

/**
 * Servlet implementation class ShowRatingAction
 */
public class ShowRatingAction implements Action {

	@Override
	public void run(HttpServletRequest request, HttpServletResponse response,
			PrintWriter out) throws Exception {
		String typeParam = request.getParameter("type");
		String idParam = request.getParameter("id");
		
		int id = Integer.parseInt(idParam);
		
		DataAccessFactory factory = new DataAccessFactory(request);
		
		if("post".equals(typeParam)){
			List<PostRating> postRatings = factory.getPostRatingsAccessInstance().getPostRatings(id);
			
			request.setAttribute("postRatings", postRatings);
			
			RequestDispatcher view = request.getRequestDispatcher("/jsp/post_ratings.jsp");
			view.forward(request, response);
		}else if("comment".equals(typeParam)){
			List<CommentRating> commentRatings = factory.getCommentRatingsAccessInstance().getUserCommentRatings(id);
			
			request.setAttribute("commentRatings", commentRatings);
			
			RequestDispatcher view = request.getRequestDispatcher("/jsp/comment_ratings.jsp");
			view.forward(request, response);
		}
	}

}
