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
import ua.com.zaibalo.model.User;

@Repository
@Transactional(propagation=Propagation.MANDATORY)
public class DiscussionsDAOImpl implements DiscussionsDAO {

	@Autowired
    private SessionFactory sessionFactory;

	@Override
	@SuppressWarnings("unchecked")
	public List<Discussion> getAllDiscussions(User user) {
		SimpleExpression authorE =  Restrictions.eq("author", user);
		SimpleExpression recipientE =  Restrictions.eq("recipient", user);
		
		Criteria criteria = this.sessionFactory.getCurrentSession().
				createCriteria(Discussion.class).
				add(Restrictions.or(authorE, recipientE)).
				addOrder(Order.desc("latestMessageDate"));
		return criteria.list();
	}

	@Override
	public Discussion getDisscussionIdForUsers(User first, User second) {
		SimpleExpression authorE =  Restrictions.eq("author", first);
		SimpleExpression recipientE =  Restrictions.eq("recipient", second);
		LogicalExpression firstExpression = Restrictions.and(authorE, recipientE);
		
		SimpleExpression authorES =  Restrictions.eq("author", second);
		SimpleExpression recipientES =  Restrictions.eq("recipient", first);
		LogicalExpression secondExpression = Restrictions.and(authorES, recipientES);

		Criteria criteria = this.sessionFactory.getCurrentSession()
				.createCriteria(Discussion.class)
				.add(Restrictions.or(firstExpression, secondExpression));
		criteria.setMaxResults(1);
		return (Discussion) criteria.uniqueResult();
	}

	@Override
	public int insert(Discussion discussion) {
		this.sessionFactory.getCurrentSession().save(discussion);
		return discussion.getId();
	}

	@Override
	public boolean isDiscussionAccessible(Discussion discussion, User user) {
		if(discussion == null){
			return false;
		}
		if(discussion.getAuthor().equals(user) || discussion.getRecipient().equals(user)){
			return true;
		}
		return false;
	}

	@Override
	public void updateExistingDiscussion(Discussion discussion, Message message) {
		discussion.setLatestMessageDate(message.getDate());
		discussion.setExtract(message.getText());
		discussion.setAuthor(message.getAuthor());
		discussion.setRecipient(message.getRecipient());
		discussion.setHasUnreadMessages(true);
		this.sessionFactory.getCurrentSession().update(discussion);
		
	}

	@Override
	public Discussion getDiscussionById(Integer discussionId) {
		return (Discussion) this.sessionFactory.getCurrentSession().get(Discussion.class, discussionId);
	}

}
