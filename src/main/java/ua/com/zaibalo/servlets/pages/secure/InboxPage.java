package ua.com.zaibalo.servlets.pages.secure;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import ua.com.zaibalo.business.InboxBusinessLogic;
import ua.com.zaibalo.model.Discussion;
import ua.com.zaibalo.model.User;

@Controller
public class InboxPage{

	@Autowired
	private InboxBusinessLogic inboxBusinessLogic;

	public ModelAndView run(User user) {
		ModelAndView mav = new ModelAndView();
		
		List<Discussion> discussions =inboxBusinessLogic.getDiscussionsList(user);
		mav.addObject("discussions", discussions);
		mav.setViewName("inbox");
		
		return mav;
	}
}
