package ua.com.zaibalo.db.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ua.com.zaibalo.db.api.DiscussionsDAO;
import ua.com.zaibalo.model.Discussion;
import ua.com.zaibalo.model.Message;

@Repository
@Transactional(propagation=Propagation.MANDATORY)
public class DiscussionsDAOImpl implements DiscussionsDAO {

	@Autowired
    private SessionFactory sessionFactory;

	@Override
	@SuppressWarnings("unchecked")
	public List<Discussion> getAllDiscussions(int id) {
		SimpleExpression authorE =  Restrictions.eq("authorId", id);
		SimpleExpression recipientE =  Restrictions.eq("recipientId", id);
		

		Criteria criteria = this.sessionFactory.getCurrentSession().createCriteria(Discussion.class).add(Restrictions.or(authorE, recipientE));
		criteria.addOrder(Order.desc("latestMessageDate"));
		List<Discussion> list =  criteria.list();
		
		return list;
	}

	@Override
	@SuppressWarnings("unchecked")
	public int getDisscussionIdForUsers(int firstId, int secondId) {
		SimpleExpression authorE =  Restrictions.eq("authorId", firstId);
		SimpleExpression recipientE =  Restrictions.eq("recipientId", secondId);
		LogicalExpression first = Restrictions.and(authorE, recipientE);
		
		SimpleExpression authorES =  Restrictions.eq("authorId", secondId);
		SimpleExpression recipientES =  Restrictions.eq("recipientId", firstId);
		LogicalExpression second = Restrictions.and(authorES, recipientES);
		

		Criteria criteria = this.sessionFactory.getCurrentSession().createCriteria(Discussion.class).add(Restrictions.or(first, second));
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

		this.sessionFactory.getCurrentSession().save(discussion);
		int id = discussion.getId();
		
		return id;
	}

	@Override
	public boolean isDiscussionAccessible(int discussionId, int userId) {

		Discussion discussion = (Discussion)this.sessionFactory.getCurrentSession().get(Discussion.class, discussionId);
		

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

		Discussion discussion = (Discussion)this.sessionFactory.getCurrentSession().get(Discussion.class, discussionId);
		discussion.setLatestMessageDate(message.getDate());
		discussion.setExtract(message.getText());
		discussion.setAuthorId(message.getAuthorId());
		discussion.setRecipientId(message.getRecipientId());
		discussion.setHasUnreadMessages(true);
		this.sessionFactory.getCurrentSession().update(discussion);
		
	}

}
