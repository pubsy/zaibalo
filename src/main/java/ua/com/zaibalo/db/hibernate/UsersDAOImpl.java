package ua.com.zaibalo.db.hibernate;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ua.com.zaibalo.db.api.UsersDAO;
import ua.com.zaibalo.helper.MD5Helper;
import ua.com.zaibalo.model.User;

@Repository
@Transactional(propagation=Propagation.MANDATORY)
public class UsersDAOImpl implements UsersDAO {

	@Autowired
    private SessionFactory sessionFactory;

	@Override
	public User getUserByEmail(String email) {
		return (User) this.sessionFactory.getCurrentSession()
				.createCriteria(User.class)
				.add(Restrictions.eq("email", email))
				.uniqueResult();
	}

	@Override
	public User getUserByLoginName(String name) {
		return (User) this.sessionFactory.getCurrentSession()
				.createCriteria(User.class)
				.add(Restrictions.eq("loginName", name))
				.uniqueResult();
	}

	@Override
	public void updateUserPassword(User user, String newPassword) {
		updateUserPassword(user, newPassword, true);
	}

	@Override
	public void updateUserPassword(User user, String newPassword, boolean encode) {
		if(encode){
			newPassword = MD5Helper.getMD5Of(newPassword);
		}
		user.setPassword(newPassword);
		this.sessionFactory.getCurrentSession().update(user);
	}

	@Override
	public User insert(User user) {
		this.sessionFactory.getCurrentSession().save(user);
		return user;
	}

	@Override
	public User getUserById(int userId) {
		return (User)this.sessionFactory.getCurrentSession().get(User.class, userId);
	}

	@Override
	public void updateUserDisplayName(User user, String newDisplayName) {
		user.setDisplayName(newDisplayName);
		this.sessionFactory.getCurrentSession().update(user);
	}

	@Override
	public void updateUserImage(User user, String bigImg, String smallImg) {
		user.setBigImgPath(bigImg);
		user.setSmallImgPath(smallImg);
		this.sessionFactory.getCurrentSession().update(user);
	}

	@Override
	public void updateUserAbout(User user, String about) {
		user.setAbout(about);
		this.sessionFactory.getCurrentSession().update(user);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<String> getAllUserNamesList() {
		return (List<String>) this.sessionFactory.getCurrentSession()
				.createCriteria(User.class)
				.setProjection(Projections.property("displayName"))
				.list();
	}

	@Override
	public User getUserByDisplayName(String value) {
		return (User)this.sessionFactory.getCurrentSession()
				.createCriteria(User.class)
				.add(Restrictions.eq("displayName", value))
				.uniqueResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<User> getAllUsers() {
		return this.sessionFactory.getCurrentSession()
				.createCriteria(User.class)
				.list();
	}

	@Override
	public void updateUserNotifyOnPM(User user, boolean notifyOnPM) {
		user.setNotifyOnPM(notifyOnPM);
		this.sessionFactory.getCurrentSession().update(user);
	}

	@Override
	public User getUserByLoginOrDisplayName(String name) {
		SimpleExpression loginNameRestriction = Restrictions.eq("loginName", name);
		SimpleExpression displayNameRestriction = Restrictions.eq("displayName", name);
		
		return (User) this.sessionFactory.getCurrentSession().
				createCriteria(User.class).
				add(Restrictions.or(loginNameRestriction, displayNameRestriction)).
				uniqueResult();
	}

}
