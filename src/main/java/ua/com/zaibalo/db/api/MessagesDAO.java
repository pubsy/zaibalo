package ua.com.zaibalo.db.api;

import java.util.List;

import ua.com.zaibalo.model.Discussion;
import ua.com.zaibalo.model.Message;
import ua.com.zaibalo.model.User;

public interface MessagesDAO {
	int insert(Message message);
	List<Message> getAllUserDiscussionMessages(Discussion discussion, User user);
	long getUnreadMessagesCount(User recipient);
	void setDialogMessagesRead(Discussion discussion, User recipient);
}
