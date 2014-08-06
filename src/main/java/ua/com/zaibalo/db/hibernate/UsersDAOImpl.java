package ua.com.zaibalo.db.hibernate;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
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

		User user = (User) this.sessionFactory.getCurrentSession().createCriteria(User.class).add(Restrictions.eq("email", email)).uniqueResult();
		
		return user;
	}

	@Override
	public User getUserByName(String name) {

		User user = (User) this.sessionFactory.getCurrentSession().createCriteria(User.class).add(Restrictions.eq("loginName", name)).uniqueResult();
		
		return user;
	}

	@Override
	public void updateUserPassword(int userId, String newPassword) {
		updateUserPassword(userId, newPassword, true);
	}

	@Override
	public void updateUserPassword(int userId, String newPassword, boolean encode) {

		User user = (User)this.sessionFactory.getCurrentSession().get(User.class, userId);
		if(encode){
			newPassword = MD5Helper.getMD5Of(newPassword);
		}
		user.setPassword(newPassword);
		this.sessionFactory.getCurrentSession().update(user);
		
	}

	@Override
	public int insert(User user) {

		this.sessionFactory.getCurrentSession().save(user);
		int id = user.getId();
		
		return id;
	}

	@Override
	public void updateUserToken(int userId, String newToken) {

		User user = (User)this.sessionFactory.getCurrentSession().get(User.class, userId);
		user.setToken(newToken);
		this.sessionFactory.getCurrentSession().update(user);
		
	}

	@Override
	public User getUserById(int userId) {

		User user = (User)this.sessionFactory.getCurrentSession().get(User.class, userId);
		
		return user;
	}

	@Override
	public void updateUserDisplayName(int userId, String newDisplayName) {
		
		User user = (User)this.sessionFactory.getCurrentSession().get(User.class, userId);
		user.setDisplayName(newDisplayName);
		this.sessionFactory.getCurrentSession().update(user);
		
	}

	@Override
	public void updateUserImage(int id, String bigImg, String smallImg) {

		User user = (User)this.sessionFactory.getCurrentSession().get(User.class, id);
		user.setBigImgPath(bigImg);
		user.setSmallImgPath(smallImg);
		this.sessionFactory.getCurrentSession().update(user);
		
	}

	@Override
	public void updateUserAbout(int id, String about) {

		User user = (User)this.sessionFactory.getCurrentSession().get(User.class, id);
		user.setAbout(about);
		this.sessionFactory.getCurrentSession().update(user);
		
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<String> getAllUserNamesList() {

		List<String> list = (List<String>) this.sessionFactory.getCurrentSession().createCriteria(User.class).setProjection(Projections.property("displayName")).list();
		
		return list;
	}

	@Override
	public User getUserByDisplayName(String value) {

		User user = (User)this.sessionFactory.getCurrentSession().createCriteria(User.class).add(Restrictions.eq("displayName", value)).uniqueResult();
		
		return user;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<User> getAllUsers() {

		List<User> list = this.sessionFactory.getCurrentSession().createCriteria(User.class).list();
		
		return list;
	}

	@Override
	public void updateUserNotifyOnPM(int id, boolean notifyOnPM) {

		User user = (User)this.sessionFactory.getCurrentSession().get(User.class, id);
		user.setNotifyOnPM(notifyOnPM);
		this.sessionFactory.getCurrentSession().update(user);
		
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
