package ua.com.zaibalo.servlets.pages.secure;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.com.zaibalo.constants.ZaibaloConstants;
import ua.com.zaibalo.db.DataAccessFactory;
import ua.com.zaibalo.model.Discussion;
import ua.com.zaibalo.model.User;
import ua.com.zaibalo.servlets.pages.ServletPage;

@WebServlet(urlPatterns={"/secure/inbox.do"}, name="InboxPage")
public class InboxPage extends ServletPage{

	private static final long serialVersionUID = 1L;

	@Override
	public String run(HttpServletRequest request, HttpServletResponse response, PrintWriter out)
			throws ServletException, IOException{
	
		User user = (User)request.getSession().getAttribute(ZaibaloConstants.USER_PARAM_NAME);
		DataAccessFactory factory = new DataAccessFactory(request);
		List<Discussion> discussions = factory.getDiscussionsAccessInstance().getAllDiscussions(user.getId());

		request.setAttribute("discussions", discussions);
		
		return "/jsp/inbox.jsp";
	}
}
