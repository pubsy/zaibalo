package ua.com.zaibalo.business;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ua.com.zaibalo.db.api.DiscussionsDAO;
import ua.com.zaibalo.db.api.MessagesDAO;
import ua.com.zaibalo.helper.ServletHelperService;
import ua.com.zaibalo.model.Discussion;
import ua.com.zaibalo.model.Message;
import ua.com.zaibalo.model.User;

@Service
@Transactional(propagation=Propagation.REQUIRED)
public class InboxBusinessLogic {
	
	@Autowired
	public DiscussionsDAO discussionsDAO;

	@Autowired
	private MessagesDAO messagesDAO;
	
	@Autowired
	private ServletHelperService servletHelperService;
	
	public List<Discussion> getDiscussionsList(User user){
		return discussionsDAO.getAllDiscussions(user.getId());
	}
	
	public List<Message> getDiscussionMessages(Integer discussionId,
			HttpServletRequest request, User user) throws ServletException {
		boolean accessible  = discussionsDAO.isDiscussionAccessible(discussionId, user.getId());

		if(!accessible){
			ServletHelperService.logMessage("Unauthorise access.", request);
			throw new ServletException("Unauthorised access.");
		}

		return messagesDAO.getAllUserDiscussionMessages(discussionId, user.getId());
	}

	public void onDialogShown(int discussionId, int userId) {
		messagesDAO.setDialogMessagesRead(discussionId, userId);
	}
	
	public int getUnreadMessagesCount(int userId){
		return messagesDAO.getUnreadMessagesCount(userId);
	}
}