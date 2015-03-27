package ua.com.zaibalo.db.api;

import java.util.List;

import ua.com.zaibalo.model.User;

public interface UsersDAO{
	User getUserByEmail(String email);
	User getUserByLoginName(String loginName);
	User getUserByLoginOrDisplayName(String displayName);
	User getUserByDisplayName(String value);
	void updateUserPassword(User user, String newPassword);
	void updateUserPassword(User user, String newPassword, boolean encode);
	User insert(User user);
	User getUserById(int userId);
	void updateUserDisplayName(User user, String newDisplayName);
	void updateUserImage(User user, String bigImg);
	void updateUserAbout(User user, String about);
	List<String> getAllUserNamesList();
	List<User> getAllUsers();
	void updateUserNotifyOnPM(User user, boolean notifyOnPM);
}
