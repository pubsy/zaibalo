package ua.com.zaibalo.servlets.pages.secure;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import ua.com.zaibalo.constants.ZaibaloConstants;
import ua.com.zaibalo.db.api.DiscussionsDAO;
import ua.com.zaibalo.db.api.MessagesDAO;
import ua.com.zaibalo.db.api.UsersDAO;
import ua.com.zaibalo.helper.ServletHelperService;
import ua.com.zaibalo.helper.StringHelper;
import ua.com.zaibalo.model.Message;
import ua.com.zaibalo.model.User;
import ua.com.zaibalo.servlets.pages.ServletPage;

@Controller
public class DialogPage extends ServletPage {

	@Autowired
	private UsersDAO usersDAO;
	@Autowired
	private MessagesDAO messagesDAO;
	@Autowired
	private DiscussionsDAO discussionsDAO;
	@Autowired
	private ServletHelperService servletHelperService;
	
	@Override
	public String runInternal(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		
		String discussionIdVal = request.getParameter("discussion_id");
		
		if(StringHelper.isBlank(discussionIdVal)){
			List<String> names = usersDAO.getAllUserNamesList();
			request.setAttribute("names", names);
		}else{
			User user = (User)request.getSession().getAttribute(ZaibaloConstants.USER_PARAM_NAME);
			int discussionId = Integer.parseInt(discussionIdVal);
			boolean accessible  = discussionsDAO.isDiscussionAccessible(discussionId, user.getId());
		
			if(!accessible){
				ServletHelperService.logMessage("Unauthorise access.", request);
				response.sendRedirect("/secure/dialog.do");
				return null;
			}
		
			List<Message> messages = messagesDAO.getAllUserDiscussionMessages(discussionId, user.getId());
			request.setAttribute("messages", messages);		
			
			Message m = messages.get(0);
			String otherUserName;
			if(user.getId() == m.getAuthorId()){
				otherUserName = m.getRecipient().getDisplayName();
			}else{
				otherUserName = m.getAuthor().getDisplayName();
			}
			request.setAttribute("other_user_name", otherUserName);
			
			messagesDAO.setDialogMessagesRead(discussionId, user.getId());
			
			servletHelperService.updateUnreadMessagesStatus(request);
			
		}
		
		return "dialog";
	}

}