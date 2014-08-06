package ua.com.zaibalo.servlets.pages.secure;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import ua.com.zaibalo.business.InboxBusinessLogic;
import ua.com.zaibalo.business.UserBusinessLogic;
import ua.com.zaibalo.constants.ZaibaloConstants;
import ua.com.zaibalo.model.Message;
import ua.com.zaibalo.model.User;

@Controller
public class DialogPage {

	@Autowired
	private UserBusinessLogic userBusinessLogic;
	
	@Autowired
	private InboxBusinessLogic inboxBusinessLogic;
	
	public ModelAndView run(Integer discussionId, HttpServletRequest request) throws IOException, ServletException{

		ModelAndView mav = new ModelAndView("dialog");
		if(discussionId == null){
			List<String> names = userBusinessLogic.getAllUserNamesList();
			mav.addObject("names", names);
		}else{
			User user = (User)request.getSession().getAttribute(ZaibaloConstants.USER_PARAM_NAME);
			List<Message> messages = inboxBusinessLogic.getDiscussionMessages(discussionId, request, user);
			mav.addObject("messages", messages);		
			
			Message m = messages.get(0);
			String otherUserName = user.getId() == m.getAuthorId() ?
					m.getRecipient().getDisplayName() :
					m.getAuthor().getDisplayName();			
			mav.addObject("other_user_name", otherUserName);
			
			int count = inboxBusinessLogic.getUnreadMessagesCount(user.getId());

			if(count != 0){			
				request.getSession().setAttribute("unreadMailCount", " [" + count + "]");
			}else{
				request.getSession().setAttribute("unreadMailCount", "");
			}
					
			inboxBusinessLogic.onDialogShown(discussionId, user.getId());
		}
		
		return mav;
	}

}