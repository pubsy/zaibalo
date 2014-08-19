package ua.com.zaibalo.servlets.pages;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import ua.com.zaibalo.db.api.CommentRatingsDAO;
import ua.com.zaibalo.db.api.CommentsDAO;
import ua.com.zaibalo.db.api.PostRatingsDAO;
import ua.com.zaibalo.db.api.PostsDAO;
import ua.com.zaibalo.db.api.UsersDAO;
import ua.com.zaibalo.helper.StringHelper;
import ua.com.zaibalo.model.Post;
import ua.com.zaibalo.model.User;
import ua.com.zaibalo.model.UserRating;

@Service
public class UserProfileServlet {
	
	private static final Logger LOGGER = Logger.getLogger(UserProfileServlet.class);

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

	@Transactional(propagation=Propagation.REQUIRED)
	public ModelAndView getUser(String userId) {
		
		ModelAndView mav = new ModelAndView();

		if (StringHelper.isBlank(userId) || !StringHelper.isDecimal(userId)) {
			mav.setViewName("redirect:/");
			LOGGER.error("User Id is not passed as a parameter.");
			return mav;
		}

		int id = Integer.parseInt(userId);

		User user = usersDAO.getUserById(id);
		if (user == null) {
			mav.setViewName("redirect:/");
			LOGGER.error("User with id: " + id + " doesn't exist");
			return mav;
		}

		List<Post> posts = postsDAO.getLatestUserPosts(id, 10);

		List<Post> entrys = new ArrayList<Post>();
		entrys.addAll(posts);

		int postCount = postsDAO.getUserPostCount(id);
		int commentCount = commentsDAO.getUserCommentCount(id);
		UserRating postRating = postRatingsDAO.getUserPostRatingSum(id);
		UserRating commentRating = commentRatingsDAO
				.getUserCommentRatingSum(id);

		mav.addObject("post_count", postCount);
		mav.addObject("comment_count", commentCount);
		mav.addObject("post_rating", postRating);
		mav.addObject("comment_rating", commentRating);
		mav.addObject("user", user);
		mav.addObject("entrys", entrys);
		mav.addObject(
				"pageTitle",
				StringHelper.getLocalString("zaibalo_blog") + " "
						+ user.getDisplayName());

		mav.setViewName("profile");
		return mav;
	}
}
