package ua.com.zaibalo.business;

import java.util.List;

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
		return discussionsDAO.getAllDiscussions(user);
	}
	
	public List<Message> getDiscussionMessages(Discussion discussion, User user) {
		boolean accessible  = discussionsDAO.isDiscussionAccessible(discussion, user);

		if(!accessible){
			throw new RuntimeException("Unauthorised access.");
		}

		return messagesDAO.getAllUserDiscussionMessages(discussion, user);
	}

	public void onDialogShown(Discussion discussion, User user) {
		messagesDAO.setDialogMessagesRead(discussion, user);
	}
	
	public long getUnreadMessagesCount(User user){
		return messagesDAO.getUnreadMessagesCount(user);
	}
}