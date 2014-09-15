package ua.com.zaibalo.db.hibernate;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ua.com.zaibalo.db.api.MessagesDAO;
import ua.com.zaibalo.model.Discussion;
import ua.com.zaibalo.model.Message;
import ua.com.zaibalo.model.User;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
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
	public List<Message> getAllUserDiscussionMessages(Discussion discussion, User user) {
		SimpleExpression authorE = Restrictions.eq("author", user);
		SimpleExpression recipientE = Restrictions.eq("recipient", user);

		return this.sessionFactory.getCurrentSession()
				.createCriteria(Message.class)
				.add(Restrictions.or(authorE, recipientE))
				.add(Restrictions.eq("discussion", discussion))
				.addOrder(Order.desc("id"))
				.list();
	}

	@Override
	public long getUnreadMessagesCount(User recipient) {
		return (Long) this.sessionFactory.getCurrentSession()
				.createCriteria(Message.class)
				.add(Restrictions.eq("recipient", recipient))
				.add(Restrictions.eq("read", false))
				.setProjection(Projections.rowCount())
				.uniqueResult();
	}

	@Override
	public void setDialogMessagesRead(Discussion discussion, User recipient) {
		String hqlUpdate = "update Message set read = :read where discussion = :discussion and recipient = :recipient";

		this.sessionFactory.getCurrentSession()
				.createQuery(hqlUpdate)
				.setBoolean("read", true)
				.setEntity("discussion", discussion)
				.setEntity("recipient", recipient)
				.executeUpdate();

		hqlUpdate = "update Discussion set hasUnreadMessages = :hasUnreadMessages where id = :discussion and recipient = :recipient";

		this.sessionFactory.getCurrentSession()
				.createQuery(hqlUpdate)
				.setInteger("hasUnreadMessages", 0)
				.setEntity("discussion", discussion)
				.setEntity("recipient", recipient)
				.executeUpdate();
	}
}
