package ua.com.zaibalo.servlets.pages.secure;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import ua.com.zaibalo.business.InboxBusinessLogic;
import ua.com.zaibalo.constants.ZaibaloConstants;
import ua.com.zaibalo.model.Discussion;
import ua.com.zaibalo.model.User;

@Controller
public class InboxPage{

	@Autowired
	private InboxBusinessLogic inboxBusinessLogic;

	public String run(HttpServletRequest request, HttpServletResponse response) {
	
		User user = (User)request.getSession().getAttribute(ZaibaloConstants.USER_PARAM_NAME);
		List<Discussion> discussions =inboxBusinessLogic.getDiscussionsList(user);
		request.setAttribute("discussions", discussions);
		
		return "inbox";
	}
}
