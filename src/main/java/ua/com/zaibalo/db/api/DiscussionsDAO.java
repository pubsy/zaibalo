package ua.com.zaibalo.db.api;

import java.util.List;

import ua.com.zaibalo.model.Discussion;
import ua.com.zaibalo.model.Message;
import ua.com.zaibalo.model.User;

public interface DiscussionsDAO {

	List<Discussion> getAllDiscussions(User user);
	Discussion getDisscussionIdForUsers(User first, User second);
	int insert(Discussion discussion);
	boolean isDiscussionAccessible(Discussion discussion, User user);
	void updateExistingDiscussion(Discussion discussion, Message message);
	Discussion getDiscussionById(Integer discussionId);

}
