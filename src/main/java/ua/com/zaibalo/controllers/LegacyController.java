package ua.com.zaibalo.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ua.com.zaibalo.constants.ZaibaloConstants;
import ua.com.zaibalo.model.User;
import ua.com.zaibalo.servlets.pages.secure.DialogPage;

@Controller
public class LegacyController {
	
	@Autowired
	private DialogPage dialogPage;

	@RequestMapping(value = { "/post", "/post.do" }, method = RequestMethod.GET)
	public String singlePost(@RequestParam("id") Integer postId) {
		return "redirect:/post/" + postId;
	}

	@RequestMapping(value = { "/userProfile.do", "/user" }, method = RequestMethod.GET)
	public String user(@RequestParam("id") Integer userId) {
		return "redirect:/user/" + userId;
	}

	@RequestMapping(value = { "/secure/dialog" }, method = RequestMethod.GET)
	public ModelAndView dialogTo(@RequestParam(value = "to", required = false) String to, HttpServletRequest request) {
		User user = (User)request.getSession().getAttribute(ZaibaloConstants.USER_PARAM_NAME);
		
		return dialogPage.run(null, user);
	}
}
