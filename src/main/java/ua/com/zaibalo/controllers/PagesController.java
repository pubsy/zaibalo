package ua.com.zaibalo.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import ua.com.zaibalo.actions.AuthorisedActionServlet;
import ua.com.zaibalo.actions.UnauthorisedActionServlet;
import ua.com.zaibalo.business.InboxBusinessLogic;
import ua.com.zaibalo.business.PostsBusinessLogic;
import ua.com.zaibalo.constants.ZaibaloConstants;
import ua.com.zaibalo.helper.ServletHelperService;
import ua.com.zaibalo.helper.StringHelper;
import ua.com.zaibalo.helper.ajax.AjaxResponse;
import ua.com.zaibalo.helper.ajax.FailResponse;
import ua.com.zaibalo.model.Discussion;
import ua.com.zaibalo.model.Post;
import ua.com.zaibalo.model.User;
import ua.com.zaibalo.servlets.pages.SinglePostServlet;
import ua.com.zaibalo.servlets.pages.UpdateProfileRedirect;
import ua.com.zaibalo.servlets.pages.UserProfileServlet;
import ua.com.zaibalo.servlets.pages.secure.DialogPage;
import ua.com.zaibalo.servlets.pages.secure.InboxPage;

@Controller
public class PagesController {
	
	private final static Logger LOGGER = Logger.getLogger(PagesController.class);

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
	private PostsBusinessLogic postsBusinessLogic;
	@Autowired
	private UpdateProfileRedirect updateProfileRedirect;
	@Autowired
	private ServletHelperService servletHelperService;
	@Autowired
	private InboxBusinessLogic inboxBusinessLogic;
	
	@RequestMapping(value = { "/logout.do", "/logout" }, method = RequestMethod.GET)
	public String logout(HttpServletRequest request,
			HttpServletResponse response) {
		request.getSession().removeAttribute(ZaibaloConstants.USER_PARAM_NAME);
		
		for (Cookie cookie : request.getCookies()) {
			if (cookie.getName().equals(ZaibaloConstants.USER_NAME_TOKEN)) {
				cookie.setMaxAge(0);
				response.addCookie(cookie);
			}else if (cookie.getName().equals(ZaibaloConstants.REMEBER_ME)) {
				cookie.setMaxAge(0);
				response.addCookie(cookie);
			}
		}
		
		return "redirect:/";
	}

	@RequestMapping(value = "/post/{postId}", method = RequestMethod.GET)
	public ModelAndView singlePostWithPathParameter(@PathVariable Integer postId) throws Exception {
		return singlePostServlet.getPost(postId);
	}

	@RequestMapping(value = { "/user/{userId}" }, method = RequestMethod.GET)
	public ModelAndView userWithPathParameter(@PathVariable String userId) {
		return userProfileServlet.getUser(userId);
	}

	@RequestMapping(value = { "/feed" }, method = RequestMethod.GET)
	public ModelAndView feed() {
		List<Post> items = postsBusinessLogic.getOrderedList(-1, 10,
				Post.PostOrder.ID);

		ModelAndView mav = new ModelAndView();
		mav.setViewName("rssViewer");
		mav.addObject("feedContent", items);

		return mav;
	}

	@RequestMapping(value = { "/secure/profileSettings.do", "/secure/settings" }, method = RequestMethod.GET)
	public String profileSettings(HttpServletRequest request) {
		User user = (User)request.getSession().getAttribute(ZaibaloConstants.USER_PARAM_NAME);
		
		request.setAttribute("profilePiscturePath", User.USERPHOTO_DIR_PATH + user.getBigImgPath());
		request.setAttribute("pageTitle",
				StringHelper.getLocalString("zaibalo_blog") + " " +
				StringHelper.getLocalString("profile_settings"));
		
		return "profile_settings";
	}

	@RequestMapping(value = { "/secure/update_profile.do" }, method = RequestMethod.POST)
	public String updateProfile(HttpServletRequest request) throws ServletException, FileUploadException, IOException {
		if (!ServletFileUpload.isMultipartContent(request)) {
			throw new ServletException(StringHelper.getLocalString("internal_server_error"));
		}
		
		User user = (User)request.getSession().getAttribute(ZaibaloConstants.USER_PARAM_NAME);
		
		DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
		diskFileItemFactory.setSizeThreshold(1*1024*1024);
		
		ServletFileUpload upload = new ServletFileUpload(diskFileItemFactory);
		
		@SuppressWarnings("unchecked")
		List<FileItem> fileItemsList = upload.parseRequest(request);

		String status = updateProfileRedirect.run(user, fileItemsList);
		request.setAttribute("update_status", status);
		
		return "redirect:/secure/settings";
	}

	@RequestMapping(value = { "/secure/inbox.do", "/secure/inbox" }, method = RequestMethod.GET)
	public ModelAndView inbox(HttpServletRequest request) {
		User user = (User)request.getSession().getAttribute(ZaibaloConstants.USER_PARAM_NAME);
		return inboxPage.run(user);
	}

	@RequestMapping(value = { "/secure/dialog/{discussionId}" }, method = RequestMethod.GET)
	public ModelAndView dialogPath(@PathVariable Integer discussionId,
			HttpServletRequest request) throws IOException, ServletException {
		User user = (User)request.getSession().getAttribute(ZaibaloConstants.USER_PARAM_NAME);
		Discussion discussion = inboxBusinessLogic.getDiscussionById(discussionId);
		
		return dialogPage.run(discussion, user);
	}

	@RequestMapping(value = { "/action.do" }, method = RequestMethod.POST)
	@ResponseBody
	public AjaxResponse action(HttpServletRequest request,
			HttpServletResponse response) {
		checkAutenticated(request, response);

		AjaxResponse ajaxResponse = null;
		try {
			ajaxResponse = unauthorisedActionServlet.doPost(request, response);
		} catch (Exception e) {
			LOGGER.error("Unathorised Action Exception", e);
			ajaxResponse = new FailResponse(e.getMessage());
		}
		return ajaxResponse;
	}

	@RequestMapping(value = { "/secure_action/action.do", "/secure/action.do" }, method = RequestMethod.POST)
	@ResponseBody
	public AjaxResponse secureAction(HttpServletRequest request,
			HttpServletResponse response) {
		AjaxResponse ajaxResponse = null;
		try {
			ajaxResponse = authorisedActionServlet.doPost(request, response);
		} catch (Exception e) {
			LOGGER.error("Secure Action Exception", e);
			ajaxResponse = new FailResponse(e.getMessage());
		}
		return ajaxResponse;
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
