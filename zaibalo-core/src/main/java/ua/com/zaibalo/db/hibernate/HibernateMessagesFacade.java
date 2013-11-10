package ua.com.zaibalo.db.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;

import ua.com.zaibalo.db.api.MessageAccessInterface;
import ua.com.zaibalo.model.Message;

public class HibernateMessagesFacade extends HibernateFacade implements MessageAccessInterface {

	public HibernateMessagesFacade(Session session) {
		this.session = session;
	}

	@Override
	public int insert(Message message) {

		session.save(message);
		int id = message.getId();
		
		return id;
	}

	@Override
	public List<Message> getAllUserDiscussionMessages(int discussionId, int userId) {
		SimpleExpression authorE =  Restrictions.eq("authorId", userId);
		SimpleExpression recipientE =  Restrictions.eq("recipientId", userId);
		

		Criteria criteria = session.createCriteria(Message.class);
		criteria.add(Restrictions.or(authorE, recipientE));
		criteria.add(Restrictions.eq("discussionId", discussionId));
		criteria.addOrder(Order.desc("id"));
		List<Message> list = criteria.list();
		
		return list;
	}

	@Override
	public int getUnreadMessagesCount(int recipientId) {

		Criteria criteria = session.createCriteria(Message.class);
		criteria.add(Restrictions.eq("recipientId", recipientId));
		criteria.add(Restrictions.eq("read", false));
		int size = (Integer)criteria.setProjection(Projections.rowCount()).uniqueResult();
		
		return size;
	}

	@Override
	public void setDialogMessagesRead(int discussionId, int recipientId) {

		String hqlUpdate = "update Message set read = :read where discussionId = :discussionId and recipientId = :recipientId"; 
		session.createQuery( hqlUpdate ) 
		.setBoolean("read", true)
		.setInteger("discussionId", discussionId)
		.setInteger("recipientId", recipientId)
		.executeUpdate(); 

		hqlUpdate = "update Discussion set hasUnreadMessages = :hasUnreadMessages where id = :discussionId and recipientId = :recipientId"; 
		session.createQuery( hqlUpdate ) 
		.setInteger("hasUnreadMessages", 0)
		.setInteger("discussionId", discussionId)
		.setInteger("recipientId", recipientId)
		.executeUpdate();
		
	}

	@Override
	public Message getMessageById(int messageId) {

		Message message = (Message)session.get(Message.class, messageId);
		
		return message;
	}

}
