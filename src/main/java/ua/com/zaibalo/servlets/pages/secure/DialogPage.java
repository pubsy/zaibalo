package ua.com.zaibalo.servlets.pages.secure;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import ua.com.zaibalo.constants.ZaibaloConstants;
import ua.com.zaibalo.db.api.DiscussionsDAO;
import ua.com.zaibalo.db.api.MessagesDAO;
import ua.com.zaibalo.db.api.UsersDAO;
import ua.com.zaibalo.helper.ServletHelperService;
import ua.com.zaibalo.helper.StringHelper;
import ua.com.zaibalo.model.Message;
import ua.com.zaibalo.model.User;

@Controller
public class DialogPage {

	@Autowired
	private UsersDAO usersDAO;
	@Autowired
	private MessagesDAO messagesDAO;
	@Autowired
	private DiscussionsDAO discussionsDAO;
	@Autowired
	private ServletHelperService servletHelperService;
	
	public ModelAndView run(String discussionIdStr, HttpServletRequest request) throws IOException, ServletException{

		ModelAndView mav = new ModelAndView("dialog");
		if(StringHelper.isBlank(discussionIdStr)){
			List<String> names = usersDAO.getAllUserNamesList();
			mav.addObject("names", names);
		}else{
			User user = (User)request.getSession().getAttribute(ZaibaloConstants.USER_PARAM_NAME);
			int discussionId = Integer.parseInt(discussionIdStr);
			boolean accessible  = discussionsDAO.isDiscussionAccessible(discussionId, user.getId());
		
			if(!accessible){
				ServletHelperService.logMessage("Unauthorise access.", request);
				throw new ServletException("Unauthorised access.");
			}
		
			List<Message> messages = messagesDAO.getAllUserDiscussionMessages(discussionId, user.getId());
			mav.addObject("messages", messages);		
			
			Message m = messages.get(0);
			String otherUserName;
			if(user.getId() == m.getAuthorId()){
				otherUserName = m.getRecipient().getDisplayName();
			}else{
				otherUserName = m.getAuthor().getDisplayName();
			}
			mav.addObject("other_user_name", otherUserName);
			
			messagesDAO.setDialogMessagesRead(discussionId, user.getId());
			
			servletHelperService.updateUnreadMessagesStatus(request);
			
		}
		
		return mav;
	}

}