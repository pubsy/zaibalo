package ua.com.zaibalo.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import ua.com.zaibalo.actions.AuthorisedActionServlet;
import ua.com.zaibalo.actions.UnauthorisedActionServlet;
import ua.com.zaibalo.db.api.PostsDAO;
import ua.com.zaibalo.helper.ServletHelperService;
import ua.com.zaibalo.helper.ajax.AjaxResponse;
import ua.com.zaibalo.model.Post;
import ua.com.zaibalo.servlets.pages.LogoutRedirect;
import ua.com.zaibalo.servlets.pages.SinglePostServlet;
import ua.com.zaibalo.servlets.pages.UpdateProfileRedirect;
import ua.com.zaibalo.servlets.pages.UserProfileServlet;
import ua.com.zaibalo.servlets.pages.secure.DialogPage;
import ua.com.zaibalo.servlets.pages.secure.InboxPage;
import ua.com.zaibalo.servlets.pages.secure.ProfileSettingsServlet;

@Controller
@Transactional
public class Pages {

	@Autowired
	private SinglePostServlet singlePostServlet;
	@Autowired
	private UserProfileServlet userProfileServlet;
	@Autowired
	private InboxPage inboxPage;
	@Autowired
	private DialogPage dialogPage;
	@Autowired
	private UnauthorisedActionServlet unauthorisedActionServlet;
	@Autowired
	private AuthorisedActionServlet authorisedActionServlet;
	@Autowired
	private LogoutRedirect logoutRedirect;
	@Autowired
	private PostsDAO postsDAO;
	@Autowired
	private ProfileSettingsServlet profileSettingsServlet;
	@Autowired
	private UpdateProfileRedirect updateProfileRedirect;
	@Autowired
	private ServletHelperService servletHelperService;

	@RequestMapping(value = { "/logout.do", "/logout" }, method = RequestMethod.GET)
	public String logout(HttpServletRequest request,
			HttpServletResponse response) {
		return logoutRedirect.run(request, response);
	}

	@RequestMapping(value = { "/post", "/post.do" }, method = RequestMethod.GET)
	public String singlePost(@RequestParam("id") String postId,
			HttpServletRequest request, HttpServletResponse response) {
		return singlePostServlet.getPost(postId, request, response);
	}

	@RequestMapping(value = "/post/{postId}", method = RequestMethod.GET)
	public String singlePostWithPathParameter(@PathVariable String postId,
			HttpServletRequest request, HttpServletResponse response) {
		return singlePostServlet.getPost(postId, request, response);
	}

	@RequestMapping(value = { "/userProfile.do", "/user" }, method = RequestMethod.GET)
	public String user(@RequestParam("id") String userId,
			HttpServletRequest request, HttpServletResponse response) {
		return userProfileServlet.getUser(userId, request, response);
	}

	@RequestMapping(value = { "/user/{userId}" }, method = RequestMethod.GET)
	public String userWithPathParameter(@PathVariable String userId,
			HttpServletRequest request, HttpServletResponse response) {
		return userProfileServlet.getUser(userId, request, response);
	}

	@RequestMapping(value = { "/feed" }, method = RequestMethod.GET)
	public ModelAndView feed(HttpServletRequest request,
			HttpServletResponse response) {
		List<Post> items = postsDAO.getOrderedList(-1, 10, Post.PostOrder.ID);

		ModelAndView mav = new ModelAndView();
		mav.setViewName("rssViewer");
		mav.addObject("feedContent", items);

		return mav;
	}

	@RequestMapping(value = { "/secure/profileSettings.do", "/secure/settings" }, method = RequestMethod.GET)
	public String profileSettings(HttpServletRequest request,
			HttpServletResponse response) {
		return profileSettingsServlet.run(request, response);
	}

	@RequestMapping(value = { "/secure/update_profile.do" }, method = RequestMethod.POST)
	public String updateProfile(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		return updateProfileRedirect.run(request, response);
	}

	@RequestMapping(value = { "/secure/inbox.do", "/secure/inbox" }, method = RequestMethod.GET)
	public String inbox(HttpServletRequest request, HttpServletResponse response) {
		return inboxPage.run(request, response);
	}

	@RequestMapping(value = { "/secure/dialog.do", "/secure/dialog" }, method = RequestMethod.GET)
	public ModelAndView dialog(
			@RequestParam(value="discussion_id", required=false) String discussionId, HttpServletRequest request) throws IOException, ServletException {
		return dialogPage.run(discussionId, request);
	}
	
	@RequestMapping(value = { "/secure/dialog/{discussionId}" }, method = RequestMethod.GET)
	public ModelAndView dialogPath(
			@PathVariable String discussionId, HttpServletRequest request) throws IOException, ServletException {
		return dialogPage.run(discussionId, request);
	}

	@RequestMapping(value = { "/action.do" }, method = RequestMethod.POST)
	@ResponseBody
	public AjaxResponse action(HttpServletRequest request,
			HttpServletResponse response) {
		checkAutenticated(request, response);
		return unauthorisedActionServlet.doPost(request, response);
	}

	@RequestMapping(value = { "/secure_action/action.do", "/secure/action.do" }, method = RequestMethod.POST)
	@ResponseBody
	public AjaxResponse secureAction(HttpServletRequest request,
			HttpServletResponse response) {
		return authorisedActionServlet.doPost(request, response);
	}

	private void checkAutenticated(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			servletHelperService.updateUserAuthenication(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
