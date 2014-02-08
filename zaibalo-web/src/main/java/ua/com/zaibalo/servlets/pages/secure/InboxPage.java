package ua.com.zaibalo.servlets.pages.secure;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import ua.com.zaibalo.constants.ZaibaloConstants;
import ua.com.zaibalo.db.api.DiscussionsDAO;
import ua.com.zaibalo.model.Discussion;
import ua.com.zaibalo.model.User;
import ua.com.zaibalo.servlets.pages.ServletPage;

//@WebServlet(urlPatterns={"/secure/inbox.do"}, name="InboxPage")
@Controller
public class InboxPage extends ServletPage{

	@Autowired
	private DiscussionsDAO discussionsDAO;
	
	@Override
	public String runInternal(HttpServletRequest request, HttpServletResponse response) {
	
		User user = (User)request.getSession().getAttribute(ZaibaloConstants.USER_PARAM_NAME);
		List<Discussion> discussions = discussionsDAO.getAllDiscussions(user.getId());

		request.setAttribute("discussions", discussions);
		
		return "inbox";
	}
}
