package ua.com.zaibalo.db.hibernate;

import java.util.List;

import org.hibernate.classic.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import ua.com.zaibalo.db.api.UsersAccessInterface;
import ua.com.zaibalo.helper.MD5Helper;
import ua.com.zaibalo.model.User;

public class HibernateUsersFacade extends HibernateFacade implements UsersAccessInterface {

	public HibernateUsersFacade(Session session) {
		this.session = session;
	}

	@Override
	public User getUserByEmail(String email) {

		User user = (User) session.createCriteria(User.class).add(Restrictions.eq("email", email)).uniqueResult();
		
		return user;
	}

	@Override
	public User getUserByName(String name) {

		User user = (User) session.createCriteria(User.class).add(Restrictions.eq("loginName", name)).uniqueResult();
		
		return user;
	}

	@Override
	public void updateUserPassword(int userId, String newPassword) {
		updateUserPassword(userId, newPassword, true);
	}

	@Override
	public void updateUserPassword(int userId, String newPassword, boolean encode) {

		User user = (User)session.get(User.class, userId);
		if(encode){
			newPassword = MD5Helper.getMD5Of(newPassword);
		}
		user.setPassword(newPassword);
		session.update(user);
		
	}

	@Override
	public int insert(User user) {

		session.save(user);
		int id = user.getId();
		
		return id;
	}

	@Override
	public void updateUserToken(int userId, String newToken) {

		User user = (User)session.get(User.class, userId);
		user.setToken(newToken);
		session.update(user);
		
	}

	@Override
	public User getUserById(int userId) {

		User user = (User)session.get(User.class, userId);
		
		return user;
	}

	@Override
	public void updateUserDisplayName(int userId, String newDisplayName) {

		
		User user = (User)session.get(User.class, userId);
		user.setDisplayName(newDisplayName);
		session.update(user);
		
		String hql = "update from Post set cli_name = 'Mike' where cli_status = 'A'";
		
		
	}

	@Override
	public void updateUserImage(int id, String bigImg, String smallImg) {

		User user = (User)session.get(User.class, id);
		user.setBigImgPath(bigImg);
		user.setSmallImgPath(smallImg);
		session.update(user);
		
	}

	@Override
	public void updateUserAbout(int id, String about) {

		User user = (User)session.get(User.class, id);
		user.setAbout(about);
		session.update(user);
		
	}

	@Override
	public List<String> getAllUserNamesList() {

		List<String> list = (List<String>) session.createCriteria(User.class).setProjection(Projections.property("displayName")).list();
		
		return list;
	}

	@Override
	public User getUserByDisplayName(String value) {

		User user = (User)session.createCriteria(User.class).add(Restrictions.eq("displayName", value)).uniqueResult();
		
		return user;
	}

	@Override
	public List<User> getAllUsers() {

		List<User> list = session.createCriteria(User.class).list();
		
		return list;
	}

	@Override
	public void updateUserNotifyOnPM(int id, boolean notifyOnPM) {

		User user = (User)session.get(User.class, id);
		user.setNotifyOnPM(notifyOnPM);
		session.update(user);
		
	}

}
