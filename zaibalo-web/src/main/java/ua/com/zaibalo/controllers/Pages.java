package ua.com.zaibalo.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import ua.com.zaibalo.actions.AuthorisedActionServlet;
import ua.com.zaibalo.actions.UnauthorisedActionServlet;
import ua.com.zaibalo.db.api.PostsDAO;
import ua.com.zaibalo.helper.ServletHelperService;
import ua.com.zaibalo.helper.ajax.AjaxResponse;
import ua.com.zaibalo.model.Post;
import ua.com.zaibalo.servlets.pages.IndexServlet;
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
	private IndexServlet indexServlet;
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
	
	@RequestMapping(value={"/", "/category"}, method = RequestMethod.GET)
	public String latestPosts(HttpServletRequest request, HttpServletResponse response) {
		checkAutenticated(request, response);
		return indexServlet.run(request, response);
	}

	@RequestMapping(value={"/logout.do"}, method = RequestMethod.GET)
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		return logoutRedirect.run(request, response);
	}
	
	@RequestMapping(value={"/post", "/post.do"}, method = RequestMethod.GET)
	public String singlePosts(HttpServletRequest request, HttpServletResponse response) {
		checkAutenticated(request, response);
		return singlePostServlet.run(request, response);
	}
	
	@RequestMapping(value={"/userProfile.do", "/user"}, method = RequestMethod.GET)
	public String user(HttpServletRequest request, HttpServletResponse response) {
		checkAutenticated(request, response);
		return userProfileServlet.run(request, response);
	}

	@RequestMapping(value={"/feed"}, method = RequestMethod.GET)
	public ModelAndView feed(HttpServletRequest request, HttpServletResponse response) {
	    List<Post> items = postsDAO.getOrderedList(-1, 10, Post.PostOrder.ID);
 
		ModelAndView mav = new ModelAndView();
		mav.setViewName("rssViewer");
		mav.addObject("feedContent", items);
 
		return mav;
	}
	
	@RequestMapping(value={"/secure/profileSettings.do"}, method = RequestMethod.GET)
	public String profileSettings(HttpServletRequest request, HttpServletResponse response) {
		return profileSettingsServlet.run(request, response);
	}
	
	@RequestMapping(value={"/secure/update_profile.do"}, method = RequestMethod.POST)
	public String updateProfile(HttpServletRequest request, HttpServletResponse response) {
		return updateProfileRedirect.run(request, response);
	}
	
	@RequestMapping(value={"/secure/inbox.do", "/secure/inbox"}, method = RequestMethod.GET)
	public String inbox(HttpServletRequest request, HttpServletResponse response) {
		return inboxPage.run(request, response);
	}

	@RequestMapping(value={"/secure/dialog.do"}, method = RequestMethod.GET)
	public String dialog(HttpServletRequest request, HttpServletResponse response) {
		return dialogPage.run(request, response);
	}
	
	@RequestMapping(value={"/action.do"}, method = RequestMethod.POST)
	@ResponseBody
	public AjaxResponse action(HttpServletRequest request, HttpServletResponse response) {
		checkAutenticated(request, response);
		return unauthorisedActionServlet.doPost(request, response);
	}
	
	@RequestMapping(value={"/secure_action/action.do", "/secure/action.do"}, method = RequestMethod.POST)
	@ResponseBody
	public AjaxResponse secureAction(HttpServletRequest request, HttpServletResponse response) {
		return authorisedActionServlet.doPost(request, response);
	}
	

	private void checkAutenticated(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			servletHelperService.checkUserAuthorised(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
