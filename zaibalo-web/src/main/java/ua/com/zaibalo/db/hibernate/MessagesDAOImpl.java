package ua.com.zaibalo.db.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ua.com.zaibalo.db.api.MessagesDAO;
import ua.com.zaibalo.model.Message;

@Repository
public class MessagesDAOImpl implements MessagesDAO {

	@Autowired
    private SessionFactory sessionFactory;

	@Override
	public int insert(Message message) {

		this.sessionFactory.getCurrentSession().save(message);
		int id = message.getId();
		
		return id;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Message> getAllUserDiscussionMessages(int discussionId, int userId) {
		SimpleExpression authorE =  Restrictions.eq("authorId", userId);
		SimpleExpression recipientE =  Restrictions.eq("recipientId", userId);
		

		Criteria criteria = this.sessionFactory.getCurrentSession().createCriteria(Message.class);
		criteria.add(Restrictions.or(authorE, recipientE));
		criteria.add(Restrictions.eq("discussionId", discussionId));
		criteria.addOrder(Order.desc("id"));
		List<Message> list = criteria.list();
		
		return list;
	}

	@Override
	public int getUnreadMessagesCount(int recipientId) {

		Criteria criteria = this.sessionFactory.getCurrentSession().createCriteria(Message.class);
		criteria.add(Restrictions.eq("recipientId", recipientId));
		criteria.add(Restrictions.eq("read", false));
		int size = (Integer)criteria.setProjection(Projections.rowCount()).uniqueResult();
		
		return size;
	}

	@Override
	public void setDialogMessagesRead(int discussionId, int recipientId) {

		String hqlUpdate = "update Message set read = :read where discussionId = :discussionId and recipientId = :recipientId"; 
		this.sessionFactory.getCurrentSession().createQuery( hqlUpdate ) 
		.setBoolean("read", true)
		.setInteger("discussionId", discussionId)
		.setInteger("recipientId", recipientId)
		.executeUpdate(); 

		hqlUpdate = "update Discussion set hasUnreadMessages = :hasUnreadMessages where id = :discussionId and recipientId = :recipientId"; 
		this.sessionFactory.getCurrentSession().createQuery( hqlUpdate ) 
		.setInteger("hasUnreadMessages", 0)
		.setInteger("discussionId", discussionId)
		.setInteger("recipientId", recipientId)
		.executeUpdate();
		
	}

	@Override
	public Message getMessageById(int messageId) {

		Message message = (Message)this.sessionFactory.getCurrentSession().get(Message.class, messageId);
		
		return message;
	}

}
