package ua.com.zaibalo.db.api;

import java.util.List;

import ua.com.zaibalo.model.Discussion;
import ua.com.zaibalo.model.Message;

public interface DiscussionsAccessInterface {

	List<Discussion> getAllDiscussions(int id);

	int getDisscussionIdForUsers(int firstId, int secondId);

	int insert(Discussion discussion);

	boolean isDiscussionAccessible(int discussionId, int id);

	void updateExistingDiscussion(int discussionId, Message message);

}
