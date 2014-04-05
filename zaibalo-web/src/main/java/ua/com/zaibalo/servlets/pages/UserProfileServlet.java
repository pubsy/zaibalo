package ua.com.zaibalo.servlets.pages;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

@Service
public class UserProfileServlet {

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

	public String getUser(String userId, HttpServletRequest request,
			HttpServletResponse response) {

		if (StringHelper.isBlank(userId) || !StringHelper.isDecimal(userId)) {
			ServletHelperService.redirectHome(response);
			ServletHelperService.logMessage(
					"User Id is not passed as a parameter.", request);
			return null;
		}

		int id = Integer.parseInt(userId);

		User user = usersDAO.getUserById(id);
		if (user == null) {
			ServletHelperService.redirectHome(response);
			ServletHelperService.logMessage("User with id: " + id
					+ " doesn't exist", request);
			return null;
		}

		List<Post> posts = postsDAO.getLatestUserPosts(id, 10);

		List<Post> entrys = new ArrayList<Post>();
		entrys.addAll(posts);

		int postCount = postsDAO.getUserPostCount(id);
		int commentCount = commentsDAO.getUserCommentCount(id);
		UserRating postRating = postRatingsDAO.getUserPostRatingSum(id);
		UserRating commentRating = commentRatingsDAO
				.getUserCommentRatingSum(id);

		request.setAttribute("post_count", postCount);
		request.setAttribute("comment_count", commentCount);
		request.setAttribute("post_rating", postRating);
		request.setAttribute("comment_rating", commentRating);
		request.setAttribute("user", user);
		request.setAttribute("entrys", entrys);
		request.setAttribute(
				"pageTitle",
				StringHelper.getLocalString("zaibalo_blog") + " "
						+ user.getDisplayName());

		return "profile";
	}
}
