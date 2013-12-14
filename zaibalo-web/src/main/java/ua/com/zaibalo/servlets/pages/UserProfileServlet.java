package ua.com.zaibalo.servlets.pages;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.com.zaibalo.db.DataAccessFactory;
import ua.com.zaibalo.helper.ServletHelperService;
import ua.com.zaibalo.helper.StringHelper;
import ua.com.zaibalo.model.Post;
import ua.com.zaibalo.model.User;
import ua.com.zaibalo.model.UserRating;

@WebServlet(urlPatterns={"/userProfile.do", "/user"}, name="UserProfilePage")
public class UserProfileServlet extends ServletPage {

	private static final long serialVersionUID = 1L;

	@Override
	public String run(HttpServletRequest request, HttpServletResponse response,
			PrintWriter out) throws ServletException, IOException{
		

		String userParam = request.getParameter("user");
		String idParam = request.getParameter("id");
		
		if(StringHelper.isBlank(userParam)){
			userParam = idParam;
		}
		
		if(StringHelper.isBlank(userParam) || !StringHelper.isDecimal(userParam)){
			response.sendRedirect("/");
			ServletHelperService.logMessage("User Id is not passed as a parameter.", request);
			return null;
		}
		
		int userId = Integer.parseInt(userParam);
		
		DataAccessFactory factory = new DataAccessFactory(request);
		User user = factory.getUsersAccessInstance().getUserById(userId);
		if(user == null){
			response.sendRedirect("/");
			ServletHelperService.logMessage("User with id: " + userId + " doesn't exist", request);
			return null;
		}
		
		List<Post> posts = factory.getPostsAccessInstance().getLatestUserPosts(userId, 10);
		//List<Comment> commentList = factory.getCommentsAccessInstance().getUserComments(userId, 50);
		//List<PostRating> postRatingList = factory.getPostRatingsAccessInstance().getUserVotes(userId);
		//List<CommentRating> commentRatingList = factory.getCommentRatingAccessInstance().getUserVotes(userId);

		List<Post> entrys = new ArrayList<Post>();
		entrys.addAll(posts);
		//entrys.addAll(commentList);
		//entrys.addAll(postRatingList);
		//entrys.addAll(commentRatingList);
		//Collections.sort(entrys);
		
		int postCount = factory.getPostsAccessInstance().getUserPostCount(userId);
		int commentCount = factory.getCommentsAccessInstance().getUserCommentCount(userId);
		UserRating postRating = factory.getPostRatingsAccessInstance().getUserPostRatingSum(userId);
		UserRating commentRating = factory.getCommentRatingAccessInstance().getUserCommentRatingSum(userId);
		
		request.setAttribute("post_count", postCount);
		request.setAttribute("comment_count", commentCount);
		request.setAttribute("post_rating", postRating);
		request.setAttribute("comment_rating", commentRating);
		request.setAttribute("user", user);
		request.setAttribute("entrys", entrys);
		request.setAttribute("pageTitle", getPageTitle(user.getDisplayName()));
		
		return "/jsp/profile.jsp";
	}

}
