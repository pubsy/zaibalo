package ua.com.zaibalo.servlets.pages;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import ua.com.zaibalo.db.api.CommentRatingsDAO;
import ua.com.zaibalo.db.api.CommentsDAO;
import ua.com.zaibalo.db.api.PostRatingsDAO;
import ua.com.zaibalo.db.api.PostsDAO;
import ua.com.zaibalo.db.api.UsersDAO;
import ua.com.zaibalo.helper.ServletHelperService;
import ua.com.zaibalo.helper.StringHelper;
import ua.com.zaibalo.model.Post;
import ua.com.zaibalo.model.User;
import ua.com.zaibalo.model.UserRating;

@Controller
public class UserProfileServlet extends ServletPage {

	@Autowired
	private UsersDAO usersDAO;
	@Autowired
	private CommentsDAO commentsDAO;
	@Autowired
	private PostsDAO postsDAO;
	@Autowired
	private PostRatingsDAO postRatingsDAO;
	@Autowired
	private CommentRatingsDAO commentRatingsDAO;
	
	@Override
	public String runInternal(HttpServletRequest request, HttpServletResponse response){

		String userParam = request.getParameter("user");
		String idParam = request.getParameter("id");
		
		if(StringHelper.isBlank(userParam)){
			userParam = idParam;
		}
		
		if(StringHelper.isBlank(userParam) || !StringHelper.isDecimal(userParam)){
			ServletHelperService.redirectHome(response);
			ServletHelperService.logMessage("User Id is not passed as a parameter.", request);
			return null;
		}
		
		int userId = Integer.parseInt(userParam);
		
		User user = usersDAO.getUserById(userId);
		if(user == null){
			ServletHelperService.redirectHome(response);
			ServletHelperService.logMessage("User with id: " + userId + " doesn't exist", request);
			return null;
		}
		
		List<Post> posts = postsDAO.getLatestUserPosts(userId, 10);
		
		List<Post> entrys = new ArrayList<Post>();
		entrys.addAll(posts);
		
		int postCount = postsDAO.getUserPostCount(userId);
		int commentCount = commentsDAO.getUserCommentCount(userId);
		UserRating postRating = postRatingsDAO.getUserPostRatingSum(userId);
		UserRating commentRating = commentRatingsDAO.getUserCommentRatingSum(userId);
		
		request.setAttribute("post_count", postCount);
		request.setAttribute("comment_count", commentCount);
		request.setAttribute("post_rating", postRating);
		request.setAttribute("comment_rating", commentRating);
		request.setAttribute("user", user);
		request.setAttribute("entrys", entrys);
		request.setAttribute("pageTitle", getPageTitle(user.getDisplayName()));
		
		return "profile";
	}

}
