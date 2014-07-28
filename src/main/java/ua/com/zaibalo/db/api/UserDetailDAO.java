package ua.com.zaibalo.db.api;

import java.util.List;

import ua.com.zaibalo.model.User;
import ua.com.zaibalo.model.UserDetail;

public interface UserDetailDAO {

	int saveIfNotExists(UserDetail userDetail);
	List<UserDetail> getUserDetails(User user);
}
