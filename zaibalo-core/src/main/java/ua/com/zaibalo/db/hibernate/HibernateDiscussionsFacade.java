package ua.com.zaibalo.db.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.classic.Session;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;

import ua.com.zaibalo.db.api.DiscussionsAccessInterface;
import ua.com.zaibalo.model.Discussion;
import ua.com.zaibalo.model.Message;

public class HibernateDiscussionsFacade extends HibernateFacade implements DiscussionsAccessInterface {

	public HibernateDiscussionsFacade(Session session) {
		this.session = session;
	}

	@Override
	public List<Discussion> getAllDiscussions(int id) {
		SimpleExpression authorE =  Restrictions.eq("authorId", id);
		SimpleExpression recipientE =  Restrictions.eq("recipientId", id);
		

		Criteria criteria = session.createCriteria(Discussion.class).add(Restrictions.or(authorE, recipientE));
		criteria.addOrder(Order.desc("latestMessageDate"));
		List<Discussion> list =  criteria.list();
		
		return list;
	}

	@Override
	public int getDisscussionIdForUsers(int firstId, int secondId) {
		SimpleExpression authorE =  Restrictions.eq("authorId", firstId);
		SimpleExpression recipientE =  Restrictions.eq("recipientId", secondId);
		LogicalExpression first = Restrictions.and(authorE, recipientE);
		
		SimpleExpression authorES =  Restrictions.eq("authorId", secondId);
		SimpleExpression recipientES =  Restrictions.eq("recipientId", firstId);
		LogicalExpression second = Restrictions.and(authorES, recipientES);
		

		Criteria criteria = session.createCriteria(Discussion.class).add(Restrictions.or(first, second));
		criteria.setMaxResults(1);
		List<Discussion> list = criteria.list();
		
		
		if(list.size() > 0){
			return list.get(0).getId();
		}else{
			return -1;
		}
	}

	@Override
	public int insert(Discussion discussion) {

		session.save(discussion);
		int id = discussion.getId();
		
		return id;
	}

	@Override
	public boolean isDiscussionAccessible(int discussionId, int userId) {

		Discussion discussion = (Discussion)session.get(Discussion.class, discussionId);
		

		if(discussion == null){
			return false;
		}
		if(discussion.getAuthorId() == userId || discussion.getRecipientId() == userId){
			return true;
		}
		return false;
	}

	@Override
	public void updateExistingDiscussion(int discussionId, Message message) {

		Discussion discussion = (Discussion)session.get(Discussion.class, discussionId);
		discussion.setLatestMessageDate(message.getDate());
		discussion.setExtract(message.getText());
		discussion.setAuthorId(message.getAuthorId());
		discussion.setRecipientId(message.getRecipientId());
		discussion.setHasUnreadMessages(true);
		session.update(discussion);
		
	}

}
