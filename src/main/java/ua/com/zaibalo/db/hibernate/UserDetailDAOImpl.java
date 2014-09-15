package ua.com.zaibalo.db.hibernate;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ua.com.zaibalo.db.api.UserDetailDAO;
import ua.com.zaibalo.model.User;
import ua.com.zaibalo.model.UserDetail;

@Repository
@Transactional(propagation=Propagation.MANDATORY)
public class UserDetailDAOImpl implements UserDetailDAO{
	
	@Autowired
    protected SessionFactory sessionFactory;

	@Override
	public int saveIfNotExists(UserDetail userDetail) {
		 UserDetail existing = (UserDetail) this.sessionFactory.getCurrentSession()
			.createCriteria(UserDetail.class)
			.add(Restrictions.eq("user", userDetail.getUser()))
			.add(Restrictions.eq("detailType", userDetail.getDetailType()))
			.add(Restrictions.eq("value", userDetail.getValue()))
			.uniqueResult();
		 
		 if(existing == null){
			 return (Integer) this.sessionFactory.getCurrentSession().save(userDetail);			 
		 }
		 
		 return existing.getId();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<UserDetail> getUserDetails(User user) {
		return this.sessionFactory.getCurrentSession()
				.createCriteria(UserDetail.class)
				.add(Restrictions.eq("user", user))
				.list();
	}

}
