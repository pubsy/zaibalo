package ua.com.zaibalo.servlets.pages.secure;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import ua.com.zaibalo.business.InboxBusinessLogic;
import ua.com.zaibalo.business.UserBusinessLogic;
import ua.com.zaibalo.model.Discussion;
import ua.com.zaibalo.model.Message;
import ua.com.zaibalo.model.User;

@Controller
public class DialogPage {

	@Autowired
	private UserBusinessLogic userBusinessLogic;
	
	@Autowired
	private InboxBusinessLogic inboxBusinessLogic;
	
	public ModelAndView run(Discussion discussion, User user) {

		ModelAndView mav = new ModelAndView("dialog");
		if(discussion == null){
			List<String> names = userBusinessLogic.getAllUserNamesList();
			mav.addObject("names", names);
		}else{
			List<Message> messages = inboxBusinessLogic.getDiscussionMessages(discussion, user);
			mav.addObject("messages", messages);		
			
			Message m = messages.get(0);
			String otherUserName = user.equals(m.getAuthor()) ?
					m.getRecipient().getDisplayName() :
					m.getAuthor().getDisplayName();			
			mav.addObject("other_user_name", otherUserName);
			
			inboxBusinessLogic.onDialogShown(discussion, user);
		}
		
		return mav;
	}

}