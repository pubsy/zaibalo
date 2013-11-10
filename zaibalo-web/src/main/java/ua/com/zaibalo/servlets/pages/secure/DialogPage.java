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
import ua.com.zaibalo.helper.ServletHelper;
import ua.com.zaibalo.helper.StringHelper;
import ua.com.zaibalo.model.Message;
import ua.com.zaibalo.model.User;
import ua.com.zaibalo.servlets.pages.ServletPage;

@WebServlet(urlPatterns = { "/secure/dialog.do" }, name = "MessageDialogPage")
public class DialogPage extends ServletPage {

	private static final long serialVersionUID = 1L;
	
	
	
	@Override
	public String run(HttpServletRequest request, HttpServletResponse response, PrintWriter out)
			throws ServletException, IOException{
		
		String discussionIdVal = request.getParameter("discussion_id");
		
		DataAccessFactory factory = new DataAccessFactory(request);
		if(StringHelper.isBlank(discussionIdVal)){
			List<String> names = factory.getUsersAccessInstance().getAllUserNamesList();
			request.setAttribute("names", names);
		}else{
			User user = (User)request.getSession().getAttribute(ZaibaloConstants.USER_PARAM_NAME);
			int discussionId = Integer.parseInt(discussionIdVal);
			boolean accessible  = factory.getDiscussionsAccessInstance().isDiscussionAccessible(discussionId, user.getId());
		
			if(!accessible){
				ServletHelper.logMessage("Unauthorise access.", request);
				response.sendRedirect("/secure/dialog.do");
				return null;
			}
		
			List<Message> messages = factory.getMessageAccessInstance().getAllUserDiscussionMessages(discussionId, user.getId());
			request.setAttribute("messages", messages);		
			
			Message m = messages.get(0);
			String otherUserName;
			if(user.getId() == m.getAuthorId()){
				otherUserName = m.getRecipient().getDisplayName();
			}else{
				otherUserName = m.getAuthor().getDisplayName();
			}
			request.setAttribute("other_user_name", otherUserName);
			
			factory.getMessageAccessInstance().setDialogMessagesRead(discussionId, user.getId());
			
			ServletHelper.updateUnreadMessagesStatus(request);
			
		}
		
		return "/jsp/dialog.jsp";
	}

}